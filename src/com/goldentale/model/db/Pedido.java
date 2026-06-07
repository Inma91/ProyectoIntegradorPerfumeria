package com.goldentale.model.db;

/**
 * Representa un pedido realizado por un {@link Usuario}.
 * <p>
 * Contiene la información completa del pedido: fecha, estado, método de pago,
 * importe total y dirección de entrega.
 * </p>
 *
 * @author Brandon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
 */
public class Pedido {

	private int idPedido;
	private Usuario usuario;
	private String fecha;
	private String estado;
	private String metodoPago;
	private double total;
	private String direccionEntrega;

	/**
	 * Crea un nuevo pedido con todos sus datos.
	 *
	 * @param idPedido         identificador del pedido
	 * @param usuario          usuario que realizó el pedido
	 * @param fecha            fecha en que se realizó el pedido
	 * @param estado           estado actual del pedido
	 * @param metodoPago       método de pago utilizado
	 * @param total            importe total del pedido
	 * @param direccionEntrega dirección de entrega indicada por el cliente
	 */
	public Pedido(int idPedido, Usuario usuario, String fecha, String estado, String metodoPago, double total,
			String direccionEntrega) {
		this.idPedido = idPedido;
		this.usuario = usuario;
		this.fecha = fecha;
		this.estado = estado;
		this.metodoPago = metodoPago;
		this.total = total;
		this.direccionEntrega = direccionEntrega;
	}

	/**
	 * Devuelve el identificador del pedido.
	 *
	 * @return identificador del pedido
	 */
	public int getIdPedido() {
		return idPedido;
	}

	/**
	 * Establece el identificador del pedido.
	 *
	 * @param id nuevo identificador
	 */
	public void setIdPedido(int id) {
		this.idPedido = id;
	}

	/**
	 * Devuelve el usuario que realizó el pedido.
	 *
	 * @return usuario asociado
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * Establece el usuario que realizó el pedido.
	 *
	 * @param u nuevo usuario
	 */
	public void setUsuario(Usuario u) {
		this.usuario = u;
	}

	/**
	 * Devuelve la fecha en que se realizó el pedido.
	 *
	 * @return fecha del pedido
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * Establece la fecha del pedido.
	 *
	 * @param fecha nueva fecha
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * Devuelve el estado actual del pedido.
	 *
	 * @return estado del pedido
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * Establece el estado del pedido.
	 *
	 * @param estado nuevo estado
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * Devuelve el método de pago utilizado en el pedido.
	 *
	 * @return método de pago
	 */
	public String getMetodoPago() {
		return metodoPago;
	}

	/**
	 * Establece el método de pago del pedido.
	 *
	 * @param mp nuevo método de pago
	 */
	public void setMetodoPago(String mp) {
		this.metodoPago = mp;
	}

	/**
	 * Devuelve el importe total del pedido.
	 *
	 * @return total del pedido
	 */
	public double getTotal() {
		return total;
	}

	/**
	 * Establece el importe total del pedido.
	 *
	 * @param total nuevo importe total
	 */
	public void setTotal(double total) {
		this.total = total;
	}

	/**
	 * Devuelve la dirección de entrega del pedido.
	 *
	 * @return dirección de entrega
	 */
	public String getDireccionEntrega() {
		return direccionEntrega;
	}

	/**
	 * Establece la dirección de entrega del pedido.
	 *
	 * @param direccionEntrega nueva dirección de entrega
	 */
	public void setDireccionEntrega(String direccionEntrega) {
		this.direccionEntrega = direccionEntrega;
	}

	/**
	 * Devuelve una representación legible del pedido, incluyendo el usuario, la
	 * fecha, el estado, el método de pago, el total y la dirección de entrega.
	 *
	 * @return cadena con los datos del pedido
	 */
	@Override
	public String toString() {
		return "Pedido: " + "\n Usuario: " + usuario + "\n Fecha: " + fecha + "\n Estado: " + estado
				+ "\n Metodo de Pago: " + metodoPago + "\n Total: " + total + "\n Dirección de entrega: "
				+ direccionEntrega;
	}
}