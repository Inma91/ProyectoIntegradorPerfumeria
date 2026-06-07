package com.goldentale.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

// Importamos tus dos archivos de configuración y datos de sesión
import com.goldentale.model.data.ConstantesTablas;

public class PedidosDAO {

	private AccesoDBProp acc;

	public PedidosDAO() {
		acc = new AccesoDBProp();
	}

	/**
	 * Inserta un nuevo pedido con todas sus líneas, descuenta del stock las
	 * cantidades vendidas y registra el pago, todo dentro de una sola transacción.
	 * <p>
	 * Excepción al estilo Michelin (void en insertar/eliminar): este método
	 * devuelve boolean porque es una operación crítica donde el controlador
	 * necesita saber si la transacción se confirmó, para informar al cliente
	 * y no vaciar el carrito en caso de fallo.
	 *
	 * @param idUsuario        ID del cliente que realiza el pedido.
	 * @param total            Total de la compra (se guarda en la tabla pago).
	 * @param formaPago        Forma de pago elegida (Tarjeta, Bizum, Efectivo).
	 * @param direccionEntrega Dirección a la que se enviará el pedido.
	 * @param listaCarrito     Lista de líneas del carrito a insertar.
	 * @return true si la transacción se confirmó correctamente, false si falló.
	 */
	public boolean insertarPedido(int idUsuario, double total, String formaPago, String direccionEntrega,
			ArrayList<CarritoCompra> listaCarrito) {

		// 1. Sentencia SQL para insertar el pedido (ahora con dirección de entrega)
		String queryPedido = "INSERT INTO " + ConstantesTablas.TABLA_PEDIDO + " ("
				+ ConstantesTablas.COL_PEDIDO_ID_USUARIO + ", "
				+ ConstantesTablas.COL_PEDIDO_DIRECCION_ENTREGA + ") VALUES (?, ?)";

		// 2. Sentencia SQL para insertar cada línea
		String queryLinea = "INSERT INTO " + ConstantesTablas.TABLA_LINEA_PEDIDO + " ("
				+ ConstantesTablas.COL_LINEA_ID_PEDIDO + ", "
				+ ConstantesTablas.COL_LINEA_ID_PERFUME + ", "
				+ ConstantesTablas.COL_LINEA_CANTIDAD + ", "
				+ ConstantesTablas.COL_LINEA_PRECIO_UNITARIO + ", "
				+ ConstantesTablas.COL_LINEA_SUBTOTAL + ") VALUES (?, ?, ?, ?, ?)";

		// 3. Sentencia SQL para descontar el stock vendido
		String queryUpdateStock = "UPDATE " + ConstantesTablas.TABLA_STOCK + " SET "
				+ ConstantesTablas.COL_STOCK_CANTIDAD + " = " + ConstantesTablas.COL_STOCK_CANTIDAD + " - ? "
				+ "WHERE " + ConstantesTablas.COL_STOCK_ID_PERFUME + " = ?";

		// 4. Sentencia SQL para insertar el pago
		String queryPago = "INSERT INTO " + ConstantesTablas.TABLA_PAGO + " ("
				+ ConstantesTablas.COL_PAGO_ID_PEDIDO + ", "
				+ ConstantesTablas.COL_PAGO_ID_USUARIO + ", "
				+ ConstantesTablas.COL_PAGO_TOTAL + ", "
				+ ConstantesTablas.COL_PAGO_FORMA_PAGO + ") VALUES (?, ?, ?, ?)";

		Connection con = null;
		PreparedStatement stmtPedido = null;
		PreparedStatement stmtLinea = null;
		PreparedStatement stmtUpdateStock = null;
		PreparedStatement stmtPago = null;
		ResultSet rsGeneratedKeys = null;

		try {
			con = acc.getConexion();

			// Transacción segura
			con.setAutoCommit(false);

			// 5. Insertar el pedido y recuperar su ID generado
			stmtPedido = con.prepareStatement(queryPedido, Statement.RETURN_GENERATED_KEYS);
			stmtPedido.setInt(1, idUsuario);
			stmtPedido.setString(2, direccionEntrega);
			stmtPedido.executeUpdate();

			rsGeneratedKeys = stmtPedido.getGeneratedKeys();
			int idPedidoGenerado = 0;
			if (rsGeneratedKeys.next()) {
				idPedidoGenerado = rsGeneratedKeys.getInt(1);
			} else {
				throw new SQLException("No se pudo recuperar el ID del pedido generado.");
			}

			// 6. Insertar cada línea y descontar del stock
			stmtLinea = con.prepareStatement(queryLinea);
			stmtUpdateStock = con.prepareStatement(queryUpdateStock);

			for (CarritoCompra item : listaCarrito) {
				double subtotalLinea = item.calcularSubtotal();

				// Línea de pedido
				stmtLinea.setInt(1, idPedidoGenerado);
				stmtLinea.setInt(2, item.getPerfume().getIdPerfume());
				stmtLinea.setInt(3, item.getCantidad());
				stmtLinea.setDouble(4, item.getPrecioUnidad());
				stmtLinea.setDouble(5, subtotalLinea);
				stmtLinea.executeUpdate();

				// Descontar del stock
				stmtUpdateStock.setInt(1, item.getCantidad());
				stmtUpdateStock.setInt(2, item.getPerfume().getIdPerfume());
				stmtUpdateStock.executeUpdate();
			}

			// 7. Insertar el pago
			stmtPago = con.prepareStatement(queryPago);
			stmtPago.setInt(1, idPedidoGenerado);
			stmtPago.setInt(2, idUsuario);
			stmtPago.setDouble(3, total);
			stmtPago.setString(4, formaPago);
			stmtPago.executeUpdate();

			// 8. Confirmar la transacción
			con.commit();
			return true;

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			try { if (con != null) con.rollback(); } catch (SQLException se) { se.printStackTrace(); }
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			try { if (con != null) con.rollback(); } catch (SQLException se) { se.printStackTrace(); }
			return false;
		} finally {
			try {
				if (rsGeneratedKeys != null) rsGeneratedKeys.close();
				if (stmtPedido != null) stmtPedido.close();
				if (stmtLinea != null) stmtLinea.close();
				if (stmtUpdateStock != null) stmtUpdateStock.close();
				if (stmtPago != null) stmtPago.close();
				if (con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Recupera todos los pedidos de un cliente con sus datos completos (incluyendo
	 * total y forma de pago vía JOIN con la tabla pago).
	 * <p>
	 * Los pedidos se devuelven ordenados por fecha descendente (los más recientes
	 * primero).
	 *
	 * @param idUsuario ID del cliente.
	 * @return Lista de pedidos. Vacía si el cliente no tiene pedidos.
	 */
	public ArrayList<Pedido> getPedidosPorUsuario(int idUsuario) {
		ArrayList<Pedido> resultado = new ArrayList<Pedido>();

		String query = "SELECT p." + ConstantesTablas.COL_PEDIDO_ID + ", "
				+ "p." + ConstantesTablas.COL_PEDIDO_FECHA + ", "
				+ "p." + ConstantesTablas.COL_PEDIDO_ESTADO + ", "
				+ "p." + ConstantesTablas.COL_PEDIDO_DIRECCION_ENTREGA + ", "
				+ "pg." + ConstantesTablas.COL_PAGO_TOTAL + ", "
				+ "pg." + ConstantesTablas.COL_PAGO_FORMA_PAGO + " "
				+ "FROM " + ConstantesTablas.TABLA_PEDIDO + " p "
				+ "LEFT JOIN " + ConstantesTablas.TABLA_PAGO + " pg "
				+ "ON p." + ConstantesTablas.COL_PEDIDO_ID + " = pg." + ConstantesTablas.COL_PAGO_ID_PEDIDO + " "
				+ "WHERE p." + ConstantesTablas.COL_PEDIDO_ID_USUARIO + " = ? "
				+ "ORDER BY p." + ConstantesTablas.COL_PEDIDO_FECHA + " DESC";

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rslt = null;

		try {
			con = acc.getConexion();
			stmt = con.prepareStatement(query);
			stmt.setInt(1, idUsuario);
			rslt = stmt.executeQuery();

			while (rslt.next()) {
				int idPedido = rslt.getInt(ConstantesTablas.COL_PEDIDO_ID);
				String fecha = rslt.getString(ConstantesTablas.COL_PEDIDO_FECHA);
				String estado = rslt.getString(ConstantesTablas.COL_PEDIDO_ESTADO);
				String direccionEntrega = rslt.getString(ConstantesTablas.COL_PEDIDO_DIRECCION_ENTREGA);
				double total = rslt.getDouble(ConstantesTablas.COL_PAGO_TOTAL);
				String formaPago = rslt.getString(ConstantesTablas.COL_PAGO_FORMA_PAGO);

				// El usuario se queda como null (no lo necesitamos en la vista de Mis pedidos)
				Pedido pedido = new Pedido(idPedido, null, fecha, estado, formaPago, total, direccionEntrega);
				resultado.add(pedido);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rslt != null) rslt.close();
				if (stmt != null) stmt.close();
				if (con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return resultado;
	}

	/**
	 * Recupera los pedidos de un cliente filtrados por estado.
	 *
	 * @param idUsuario ID del cliente.
	 * @param estado    Estado del pedido (Pendiente, Procesando, Enviado, etc.).
	 * @return Lista de pedidos con ese estado. Vacía si no hay coincidencias.
	 */
	public ArrayList<Pedido> getPedidosPorUsuarioYEstado(int idUsuario, String estado) {
		ArrayList<Pedido> resultado = new ArrayList<Pedido>();

		String query = "SELECT p." + ConstantesTablas.COL_PEDIDO_ID + ", "
				+ "p." + ConstantesTablas.COL_PEDIDO_FECHA + ", "
				+ "p." + ConstantesTablas.COL_PEDIDO_ESTADO + ", "
				+ "p." + ConstantesTablas.COL_PEDIDO_DIRECCION_ENTREGA + ", "
				+ "pg." + ConstantesTablas.COL_PAGO_TOTAL + ", "
				+ "pg." + ConstantesTablas.COL_PAGO_FORMA_PAGO + " "
				+ "FROM " + ConstantesTablas.TABLA_PEDIDO + " p "
				+ "LEFT JOIN " + ConstantesTablas.TABLA_PAGO + " pg "
				+ "ON p." + ConstantesTablas.COL_PEDIDO_ID + " = pg." + ConstantesTablas.COL_PAGO_ID_PEDIDO + " "
				+ "WHERE p." + ConstantesTablas.COL_PEDIDO_ID_USUARIO + " = ? "
				+ "AND p." + ConstantesTablas.COL_PEDIDO_ESTADO + " = ? "
				+ "ORDER BY p." + ConstantesTablas.COL_PEDIDO_FECHA + " DESC";

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rslt = null;

		try {
			con = acc.getConexion();
			stmt = con.prepareStatement(query);
			stmt.setInt(1, idUsuario);
			stmt.setString(2, estado);
			rslt = stmt.executeQuery();

			while (rslt.next()) {
				int idPedido = rslt.getInt(ConstantesTablas.COL_PEDIDO_ID);
				String fecha = rslt.getString(ConstantesTablas.COL_PEDIDO_FECHA);
				String estadoBD = rslt.getString(ConstantesTablas.COL_PEDIDO_ESTADO);
				String direccionEntrega = rslt.getString(ConstantesTablas.COL_PEDIDO_DIRECCION_ENTREGA);
				double total = rslt.getDouble(ConstantesTablas.COL_PAGO_TOTAL);
				String formaPago = rslt.getString(ConstantesTablas.COL_PAGO_FORMA_PAGO);

				Pedido pedido = new Pedido(idPedido, null, fecha, estadoBD, formaPago, total, direccionEntrega);
				resultado.add(pedido);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rslt != null) rslt.close();
				if (stmt != null) stmt.close();
				if (con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return resultado;
	}

	/**
	 * Recupera todas las líneas de un pedido concreto, con el perfume completo
	 * para mostrar nombre y datos del producto.
	 *
	 * @param idPedido ID del pedido.
	 * @return Lista de líneas del pedido. Vacía si el pedido no tiene líneas.
	 */
	public ArrayList<LineaPedido> getLineasPorPedido(int idPedido) {
		ArrayList<LineaPedido> resultado = new ArrayList<LineaPedido>();

		String query = "SELECT l." + ConstantesTablas.COL_LINEA_ID_PERFUME + ", "
				+ "l." + ConstantesTablas.COL_LINEA_CANTIDAD + ", "
				+ "l." + ConstantesTablas.COL_LINEA_PRECIO_UNITARIO + ", "
				+ "l." + ConstantesTablas.COL_LINEA_SUBTOTAL + ", "
				+ "p." + ConstantesTablas.COL_PERFUME_NOMBRE + ", "
				+ "p." + ConstantesTablas.COL_PERFUME_MARCA + ", "
				+ "p." + ConstantesTablas.COL_PERFUME_ML + " "
				+ "FROM " + ConstantesTablas.TABLA_LINEA_PEDIDO + " l "
				+ "JOIN " + ConstantesTablas.TABLA_PERFUME + " p "
				+ "ON l." + ConstantesTablas.COL_LINEA_ID_PERFUME + " = p." + ConstantesTablas.COL_PERFUME_ID + " "
				+ "WHERE l." + ConstantesTablas.COL_LINEA_ID_PEDIDO + " = ?";

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rslt = null;

		try {
			con = acc.getConexion();
			stmt = con.prepareStatement(query);
			stmt.setInt(1, idPedido);
			rslt = stmt.executeQuery();

			while (rslt.next()) {
				int idPerfume = rslt.getInt(ConstantesTablas.COL_LINEA_ID_PERFUME);
				int cantidad = rslt.getInt(ConstantesTablas.COL_LINEA_CANTIDAD);
				double precioUnitario = rslt.getDouble(ConstantesTablas.COL_LINEA_PRECIO_UNITARIO);
				double subtotal = rslt.getDouble(ConstantesTablas.COL_LINEA_SUBTOTAL);
				String nombrePerfume = rslt.getString(ConstantesTablas.COL_PERFUME_NOMBRE);
				String marca = rslt.getString(ConstantesTablas.COL_PERFUME_MARCA);
				int ml = rslt.getInt(ConstantesTablas.COL_PERFUME_ML);

				// Creamos el Perfume con los campos que necesitamos para mostrar
				Perfumes perfume = new Perfumes(idPerfume, nombrePerfume, marca, null, null, precioUnitario, ml, null);

				// El pedido se queda como null (no lo necesitamos para mostrar la línea)
				LineaPedido linea = new LineaPedido(null, perfume, cantidad, precioUnitario, subtotal);
				resultado.add(linea);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rslt != null) rslt.close();
				if (stmt != null) stmt.close();
				if (con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return resultado;
	}
	
}