package com.goldentale.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.goldentale.model.data.ConstantesTablas;

/**
 * DAO de Perfumes. Todas las operaciones JDBC sobre la tabla perfume van aquí.
 *
 * @author Brandon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
 */
public class PerfumesDAO {


	// ATRIBUTO QUE REPRESENTA LA CLASE QUE ME VA A FACILITAR LA CONEXIÓN
	private AccesoDBProp acc;

	public PerfumesDAO() {
		acc = new AccesoDBProp();
	}
	// SENTENCIAS Y MÉTODOS QUE SE VAN A NECESITAR

	// GET ALL: devuelve todos los perfumes del catálogo (sin info de stock)

	public ArrayList<Perfumes> getAll() {
		ArrayList<Perfumes> listaPerfumes = new ArrayList<Perfumes>();

		String query = "SELECT " + ConstantesTablas.COL_PERFUME_ID + " , "
				+ ConstantesTablas.COL_PERFUME_NOMBRE + " , "
				+ ConstantesTablas.COL_PERFUME_MARCA + " , "
				+ ConstantesTablas.COL_PERFUME_CATEGORIA + " , "
				+ ConstantesTablas.COL_PERFUME_DESCRIPCION + " , "
				+ ConstantesTablas.COL_PERFUME_PRECIO + " , "
				+ ConstantesTablas.COL_PERFUME_ML + " , "
				+ ConstantesTablas.COL_PERFUME_PUBLICO
				+ " FROM " + ConstantesTablas.TABLA_PERFUME;

		Connection con = null;
		Statement stmt = null;
		ResultSet rslt = null;

		try {
			con = acc.getConexion();

			stmt = con.createStatement();
			rslt = stmt.executeQuery(query);

			while (rslt.next()) {
				listaPerfumes.add(new Perfumes(
						rslt.getInt(ConstantesTablas.COL_PERFUME_ID),
						rslt.getString(ConstantesTablas.COL_PERFUME_NOMBRE),
						rslt.getString(ConstantesTablas.COL_PERFUME_MARCA),
						rslt.getString(ConstantesTablas.COL_PERFUME_CATEGORIA),
						rslt.getString(ConstantesTablas.COL_PERFUME_DESCRIPCION),
						rslt.getDouble(ConstantesTablas.COL_PERFUME_PRECIO),
						rslt.getInt(ConstantesTablas.COL_PERFUME_ML),
						rslt.getString(ConstantesTablas.COL_PERFUME_PUBLICO)));
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rslt != null)
					rslt.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return listaPerfumes;
	}

	// GET INFO PERFUMES CON STOCK: devuelve todos los perfumes con info de stock (JOIN)
	public ArrayList<InfoPerfumeConStock> getInfoPerfumesConStock() {
		ArrayList<InfoPerfumeConStock> lista = new ArrayList<InfoPerfumeConStock>();

		String query = "SELECT " + "*" + " FROM " + ConstantesTablas.TABLA_PERFUME + " p "
				+ " INNER JOIN " + ConstantesTablas.TABLA_STOCK + " s "
				+ " ON p." + ConstantesTablas.COL_PERFUME_ID + " = s." + ConstantesTablas.COL_STOCK_ID_PERFUME;

		Connection con = null;
		Statement stmt = null;
		ResultSet rslt = null;

		try {
			con = acc.getConexion();

			stmt = con.createStatement();
			rslt = stmt.executeQuery(query);

			while (rslt.next()) {
				Perfumes perfume = new Perfumes(
						rslt.getInt(ConstantesTablas.COL_PERFUME_ID),
						rslt.getString(ConstantesTablas.COL_PERFUME_NOMBRE),
						rslt.getString(ConstantesTablas.COL_PERFUME_MARCA),
						rslt.getString(ConstantesTablas.COL_PERFUME_CATEGORIA),
						rslt.getString(ConstantesTablas.COL_PERFUME_DESCRIPCION),
						rslt.getDouble(ConstantesTablas.COL_PERFUME_PRECIO),
						rslt.getInt(ConstantesTablas.COL_PERFUME_ML),
						rslt.getString(ConstantesTablas.COL_PERFUME_PUBLICO));

				Stock stock = new Stock(
						rslt.getInt(ConstantesTablas.COL_STOCK_ID),
						rslt.getInt(ConstantesTablas.COL_STOCK_ID_PERFUME),
						rslt.getInt(ConstantesTablas.COL_STOCK_CANTIDAD),
						rslt.getString(ConstantesTablas.COL_STOCK_LOCALIZACION));

				lista.add(new InfoPerfumeConStock(perfume, stock));
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rslt != null)
					rslt.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return lista;
	}

	// GET INFO TABLA POR NOMBRE: filtra por coincidencia parcial en el nombre
	public ArrayList<InfoPerfumeConStock> getInfoTablaPorNombre(String nombre) {
		ArrayList<InfoPerfumeConStock> lista = new ArrayList<InfoPerfumeConStock>();

		String query = "SELECT " + "*" + " FROM " + ConstantesTablas.TABLA_PERFUME + " p "
				+ " INNER JOIN " + ConstantesTablas.TABLA_STOCK + " s "
				+ " ON p." + ConstantesTablas.COL_PERFUME_ID + " = s." + ConstantesTablas.COL_STOCK_ID_PERFUME
				+ " WHERE p." + ConstantesTablas.COL_PERFUME_NOMBRE + " LIKE ? ";

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rslt = null;

		try {
			con = acc.getConexion();

			stmt = con.prepareStatement(query);
			stmt.setString(1, "%" + nombre + "%");
			rslt = stmt.executeQuery();

			while (rslt.next()) {
				Perfumes perfume = new Perfumes(
						rslt.getInt(ConstantesTablas.COL_PERFUME_ID),
						rslt.getString(ConstantesTablas.COL_PERFUME_NOMBRE),
						rslt.getString(ConstantesTablas.COL_PERFUME_MARCA),
						rslt.getString(ConstantesTablas.COL_PERFUME_CATEGORIA),
						rslt.getString(ConstantesTablas.COL_PERFUME_DESCRIPCION),
						rslt.getDouble(ConstantesTablas.COL_PERFUME_PRECIO),
						rslt.getInt(ConstantesTablas.COL_PERFUME_ML),
						rslt.getString(ConstantesTablas.COL_PERFUME_PUBLICO));

				Stock stock = new Stock(
						rslt.getInt(ConstantesTablas.COL_STOCK_ID),
						rslt.getInt(ConstantesTablas.COL_STOCK_ID_PERFUME),
						rslt.getInt(ConstantesTablas.COL_STOCK_CANTIDAD),
						rslt.getString(ConstantesTablas.COL_STOCK_LOCALIZACION));

				lista.add(new InfoPerfumeConStock(perfume, stock));
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rslt != null)
					rslt.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return lista;
	}

	// GET INFO TABLA POR UBICACIÓN: filtra por estante exacto
	public ArrayList<InfoPerfumeConStock> getInfoTablaPorUbicacion(String ubicacion) {
		ArrayList<InfoPerfumeConStock> lista = new ArrayList<InfoPerfumeConStock>();

		String query = "SELECT " + "*" + " FROM " + ConstantesTablas.TABLA_PERFUME + " p "
				+ " INNER JOIN " + ConstantesTablas.TABLA_STOCK + " s "
				+ " ON p." + ConstantesTablas.COL_PERFUME_ID + " = s." + ConstantesTablas.COL_STOCK_ID_PERFUME
				+ " WHERE s." + ConstantesTablas.COL_STOCK_LOCALIZACION + " = ? ";

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rslt = null;

		try {
			con = acc.getConexion();

			stmt = con.prepareStatement(query);
			stmt.setString(1, ubicacion);
			rslt = stmt.executeQuery();

			while (rslt.next()) {
				Perfumes perfume = new Perfumes(
						rslt.getInt(ConstantesTablas.COL_PERFUME_ID),
						rslt.getString(ConstantesTablas.COL_PERFUME_NOMBRE),
						rslt.getString(ConstantesTablas.COL_PERFUME_MARCA),
						rslt.getString(ConstantesTablas.COL_PERFUME_CATEGORIA),
						rslt.getString(ConstantesTablas.COL_PERFUME_DESCRIPCION),
						rslt.getDouble(ConstantesTablas.COL_PERFUME_PRECIO),
						rslt.getInt(ConstantesTablas.COL_PERFUME_ML),
						rslt.getString(ConstantesTablas.COL_PERFUME_PUBLICO));

				Stock stock = new Stock(
						rslt.getInt(ConstantesTablas.COL_STOCK_ID),
						rslt.getInt(ConstantesTablas.COL_STOCK_ID_PERFUME),
						rslt.getInt(ConstantesTablas.COL_STOCK_CANTIDAD),
						rslt.getString(ConstantesTablas.COL_STOCK_LOCALIZACION));

				lista.add(new InfoPerfumeConStock(perfume, stock));
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rslt != null)
					rslt.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return lista;
	}

	// GET INFO TABLA POR AMBOS: filtra por nombre y ubicación a la vez
	public ArrayList<InfoPerfumeConStock> getInfoTablaPorAmbos(String nombre, String ubicacion) {
		ArrayList<InfoPerfumeConStock> lista = new ArrayList<InfoPerfumeConStock>();

		String query = "SELECT " + "*" + " FROM " + ConstantesTablas.TABLA_PERFUME + " p "
				+ " INNER JOIN " + ConstantesTablas.TABLA_STOCK + " s "
				+ " ON p." + ConstantesTablas.COL_PERFUME_ID + " = s." + ConstantesTablas.COL_STOCK_ID_PERFUME
				+ " WHERE p." + ConstantesTablas.COL_PERFUME_NOMBRE + " LIKE ? "
				+ " AND s." + ConstantesTablas.COL_STOCK_LOCALIZACION + " = ? ";

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rslt = null;

		try {
			con = acc.getConexion();

			stmt = con.prepareStatement(query);
			stmt.setString(1, "%" + nombre + "%");
			stmt.setString(2, ubicacion);
			rslt = stmt.executeQuery();

			while (rslt.next()) {
				Perfumes perfume = new Perfumes(
						rslt.getInt(ConstantesTablas.COL_PERFUME_ID),
						rslt.getString(ConstantesTablas.COL_PERFUME_NOMBRE),
						rslt.getString(ConstantesTablas.COL_PERFUME_MARCA),
						rslt.getString(ConstantesTablas.COL_PERFUME_CATEGORIA),
						rslt.getString(ConstantesTablas.COL_PERFUME_DESCRIPCION),
						rslt.getDouble(ConstantesTablas.COL_PERFUME_PRECIO),
						rslt.getInt(ConstantesTablas.COL_PERFUME_ML),
						rslt.getString(ConstantesTablas.COL_PERFUME_PUBLICO));

				Stock stock = new Stock(
						rslt.getInt(ConstantesTablas.COL_STOCK_ID),
						rslt.getInt(ConstantesTablas.COL_STOCK_ID_PERFUME),
						rslt.getInt(ConstantesTablas.COL_STOCK_CANTIDAD),
						rslt.getString(ConstantesTablas.COL_STOCK_LOCALIZACION));

				lista.add(new InfoPerfumeConStock(perfume, stock));
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rslt != null)
					rslt.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return lista;
	}

	// INSERTAR PERFUME: inserta el perfume y crea su registro de stock (transacción)
	public void insertar(Perfumes perfume, int cantidad, String localizacion) {

		String queryPerfume = "INSERT INTO " + ConstantesTablas.TABLA_PERFUME + " ( "
				+ ConstantesTablas.COL_PERFUME_NOMBRE + " , "
				+ ConstantesTablas.COL_PERFUME_MARCA + " , "
				+ ConstantesTablas.COL_PERFUME_CATEGORIA + " , "
				+ ConstantesTablas.COL_PERFUME_DESCRIPCION + " , "
				+ ConstantesTablas.COL_PERFUME_PRECIO + " , "
				+ ConstantesTablas.COL_PERFUME_ML + " , "
				+ ConstantesTablas.COL_PERFUME_PUBLICO
				+ " ) VALUES (?, ?, ?, ?, ?, ?, ?) ";

		String queryStock = "INSERT INTO " + ConstantesTablas.TABLA_STOCK + " ( "
				+ ConstantesTablas.COL_STOCK_ID_PERFUME + " , "
				+ ConstantesTablas.COL_STOCK_CANTIDAD + " , "
				+ ConstantesTablas.COL_STOCK_LOCALIZACION
				+ " ) VALUES (?, ?, ?) ";

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rslt = null;

		try {
			con = acc.getConexion();
			con.setAutoCommit(false); // empieza la transacción

			// ── 1. Insertar el perfume y recuperar el ID autogenerado ──
			int idPerfumeGenerado = 0;
			stmt = con.prepareStatement(queryPerfume, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, perfume.getNombre());
			stmt.setString(2, perfume.getMarca());
			stmt.setString(3, perfume.getCategoria());
			stmt.setString(4, perfume.getDescripcion());
			stmt.setDouble(5, perfume.getPrecio());
			stmt.setInt(6, perfume.getMl());
			stmt.setString(7, perfume.getPublico());
			stmt.executeUpdate();

			rslt = stmt.getGeneratedKeys();
			if (rslt.next()) {
				idPerfumeGenerado = rslt.getInt(1);
			}
			rslt.close();
			stmt.close();

			// ── 2. Insertar el stock asociado a ese id ──
			stmt = con.prepareStatement(queryStock);
			stmt.setInt(1, idPerfumeGenerado);
			stmt.setInt(2, cantidad);
			stmt.setString(3, localizacion);
			stmt.executeUpdate();

			con.commit(); // todo OK, se confirma

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			if (con != null) {
				try {
					con.rollback(); // algo falló, se deshace
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			e.printStackTrace();

		} finally {
			try {
				if (rslt != null)
					rslt.close();
				if (stmt != null)
					stmt.close();
				if (con != null) {
					con.setAutoCommit(true);
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * Busca un perfume por nombre y mililitros.
	 * TODO: implementar consulta JDBC con PreparedStatement.
	 */
	// BUSCAR PERFUME POR NOMBRE Y ML
		public InfoPerfumeConStock buscarPorNombreYMl(String nombre, int ml) {

			InfoPerfumeConStock info = null;

			String query = "SELECT " + "*" + " FROM " + ConstantesTablas.TABLA_PERFUME + " p "
					+ " INNER JOIN " + ConstantesTablas.TABLA_STOCK + " s "
					+ " ON p." + ConstantesTablas.COL_PERFUME_ID + " = s." + ConstantesTablas.COL_STOCK_ID_PERFUME
					+ " WHERE p." + ConstantesTablas.COL_PERFUME_NOMBRE + " = ? "
					+ " AND p." + ConstantesTablas.COL_PERFUME_ML + " = ? ";

			Connection con = null;
			PreparedStatement stmt = null;
			ResultSet rslt = null;

			try {
				con = acc.getConexion();

				stmt = con.prepareStatement(query);
				stmt.setString(1, nombre);
				stmt.setInt(2, ml);
				rslt = stmt.executeQuery();

				if (rslt.next()) {
					Perfumes perfume = new Perfumes(
							rslt.getInt(ConstantesTablas.COL_PERFUME_ID),
							rslt.getString(ConstantesTablas.COL_PERFUME_NOMBRE),
							rslt.getString(ConstantesTablas.COL_PERFUME_MARCA),
							rslt.getString(ConstantesTablas.COL_PERFUME_CATEGORIA),
							rslt.getString(ConstantesTablas.COL_PERFUME_DESCRIPCION),
							rslt.getDouble(ConstantesTablas.COL_PERFUME_PRECIO),
							rslt.getInt(ConstantesTablas.COL_PERFUME_ML),
							rslt.getString(ConstantesTablas.COL_PERFUME_PUBLICO));

					Stock stock = new Stock(
							rslt.getInt(ConstantesTablas.COL_STOCK_ID),
							rslt.getInt(ConstantesTablas.COL_STOCK_ID_PERFUME),
							rslt.getInt(ConstantesTablas.COL_STOCK_CANTIDAD),
							rslt.getString(ConstantesTablas.COL_STOCK_LOCALIZACION));

					info = new InfoPerfumeConStock(perfume, stock);
				}

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (rslt != null)
						rslt.close();
					if (stmt != null)
						stmt.close();
					if (con != null)
						con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			return info;
		}

	/**
	 * Actualiza el stock de un perfume existente y su precio: modifica la cantidad del stock y el precio del perfume.
	 */
		// ACTUALIZAR PRECIO Y STOCK: modifica el precio del perfume y la cantidad de stock (transacción)
		public void actualizarPrecioYStock(int idPerfume, int idStock, Double nuevoPrecio, Integer nuevaCantidad) {

			String queryPrecio = "UPDATE " + ConstantesTablas.TABLA_PERFUME
					+ " SET " + ConstantesTablas.COL_PERFUME_PRECIO + " = ? "
					+ " WHERE " + ConstantesTablas.COL_PERFUME_ID + " = ? ";

			String queryStock = "UPDATE " + ConstantesTablas.TABLA_STOCK
					+ " SET " + ConstantesTablas.COL_STOCK_CANTIDAD + " = ? "
					+ " WHERE " + ConstantesTablas.COL_STOCK_ID + " = ? ";

			Connection con = null;
			PreparedStatement stmt = null;

			try {
				con = acc.getConexion();
				con.setAutoCommit(false); // empieza la transacción

				// ── 1. Actualizar precio (solo si nuevoPrecio no es null) ──
				if (nuevoPrecio != null) {
					stmt = con.prepareStatement(queryPrecio);
					stmt.setDouble(1, nuevoPrecio);
					stmt.setInt(2, idPerfume);
					stmt.executeUpdate();
					stmt.close();
				}

				// ── 2. Actualizar cantidad (solo si nuevaCantidad no es null) ──
				if (nuevaCantidad != null) {
					stmt = con.prepareStatement(queryStock);
					stmt.setInt(1, nuevaCantidad);
					stmt.setInt(2, idStock);
					stmt.executeUpdate();
					stmt.close();
				}

				con.commit(); // todo OK, se confirma

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				if (con != null) {
					try {
						con.rollback(); // algo falló, se deshace
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
				}
				e.printStackTrace();

			} finally {
				try {
					if (stmt != null)
						stmt.close();
					if (con != null) {
						con.setAutoCommit(true);
						con.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		
 //CLIENTES
		
		// GET INFO CATALOGO POR CATEGORÍA: devuelve perfumes filtrados por categoría
		public ArrayList<InfoPerfumeConStock> getInfoCatalogoPorCategoria(String categoria) {
			ArrayList<InfoPerfumeConStock> lista = new ArrayList<InfoPerfumeConStock>();

			String query = "SELECT * FROM " + ConstantesTablas.TABLA_PERFUME + " p "
					+ " INNER JOIN " + ConstantesTablas.TABLA_STOCK + " s "
					+ " ON p." + ConstantesTablas.COL_PERFUME_ID + " = s." + ConstantesTablas.COL_STOCK_ID_PERFUME
					+ " WHERE p." + ConstantesTablas.COL_PERFUME_CATEGORIA + " = ? ";

			Connection con = null;
			PreparedStatement stmt = null;
			ResultSet rslt = null;

			try {
				con = acc.getConexion();
				stmt = con.prepareStatement(query);
				stmt.setString(1, categoria);
				rslt = stmt.executeQuery();

				while (rslt.next()) {
					Perfumes perfume = new Perfumes(
							rslt.getInt(ConstantesTablas.COL_PERFUME_ID),
							rslt.getString(ConstantesTablas.COL_PERFUME_NOMBRE),
							rslt.getString(ConstantesTablas.COL_PERFUME_MARCA),
							rslt.getString(ConstantesTablas.COL_PERFUME_CATEGORIA),
							rslt.getString(ConstantesTablas.COL_PERFUME_DESCRIPCION),
							rslt.getDouble(ConstantesTablas.COL_PERFUME_PRECIO),
							rslt.getInt(ConstantesTablas.COL_PERFUME_ML),
							rslt.getString(ConstantesTablas.COL_PERFUME_PUBLICO));

					Stock stock = new Stock(
							rslt.getInt(ConstantesTablas.COL_STOCK_ID),
							rslt.getInt(ConstantesTablas.COL_STOCK_ID_PERFUME),
							rslt.getInt(ConstantesTablas.COL_STOCK_CANTIDAD),
							rslt.getString(ConstantesTablas.COL_STOCK_LOCALIZACION));

					lista.add(new InfoPerfumeConStock(perfume, stock));
				}
			} catch (ClassNotFoundException | SQLException e) {
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
			return lista;
		}

		// GET INFO CATALOGO POR NOMBRE Y CATEGORÍA: filtra por ambos campos a la vez
		public ArrayList<InfoPerfumeConStock> getInfoCatalogoPorNombreYCategoria(String nombre, String categoria) {
			ArrayList<InfoPerfumeConStock> lista = new ArrayList<InfoPerfumeConStock>();

			String query = "SELECT * FROM " + ConstantesTablas.TABLA_PERFUME + " p "
					+ " INNER JOIN " + ConstantesTablas.TABLA_STOCK + " s "
					+ " ON p." + ConstantesTablas.COL_PERFUME_ID + " = s." + ConstantesTablas.COL_STOCK_ID_PERFUME
					+ " WHERE p." + ConstantesTablas.COL_PERFUME_NOMBRE + " LIKE ? "
					+ " AND p." + ConstantesTablas.COL_PERFUME_CATEGORIA + " = ? ";

			Connection con = null;
			PreparedStatement stmt = null;
			ResultSet rslt = null;

			try {
				con = acc.getConexion();
				stmt = con.prepareStatement(query);
				stmt.setString(1, "%" + nombre + "%");
				stmt.setString(2, categoria);
				rslt = stmt.executeQuery();

				while (rslt.next()) {
					Perfumes perfume = new Perfumes(
							rslt.getInt(ConstantesTablas.COL_PERFUME_ID),
							rslt.getString(ConstantesTablas.COL_PERFUME_NOMBRE),
							rslt.getString(ConstantesTablas.COL_PERFUME_MARCA),
							rslt.getString(ConstantesTablas.COL_PERFUME_CATEGORIA),
							rslt.getString(ConstantesTablas.COL_PERFUME_DESCRIPCION),
							rslt.getDouble(ConstantesTablas.COL_PERFUME_PRECIO),
							rslt.getInt(ConstantesTablas.COL_PERFUME_ML),
							rslt.getString(ConstantesTablas.COL_PERFUME_PUBLICO));

					Stock stock = new Stock(
							rslt.getInt(ConstantesTablas.COL_STOCK_ID),
							rslt.getInt(ConstantesTablas.COL_STOCK_ID_PERFUME),
							rslt.getInt(ConstantesTablas.COL_STOCK_CANTIDAD),
							rslt.getString(ConstantesTablas.COL_STOCK_LOCALIZACION));

					lista.add(new InfoPerfumeConStock(perfume, stock));
				}
			} catch (ClassNotFoundException | SQLException e) {
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
			return lista;
		}
		
		
	// public boolean actualizarStock(int idPerfume, int nuevaCantidad) {
	//     return false;
	// }

}