package com.goldentale.model.db;

/**
 * Representa el pago asociado a un pedido concreto.
 * <p>
 * Almacena el importe total abonado, la forma de pago utilizada y las
 * referencias al {@link Pedido} y al {@link Usuario} que realizó la compra.
 * </p>
 *
 * @author Brandon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
 */
public class Pago {

	private int idPago;
	private Pedido pedido;
	private Usuario usuario;
	private double total;
	private String formaPago;

	/**
	 * Crea un nuevo pago con todos sus datos.
	 *
	 * @param idPago    identificador del pago
	 * @param pedido    pedido al que corresponde este pago
	 * @param usuario   usuario que realizó el pago
	 * @param total     importe total abonado
	 * @param formaPago forma de pago utilizada
	 */
	public Pago(int idPago, Pedido pedido, Usuario usuario, double total, String formaPago) {
		this.idPago = idPago;
		this.pedido = pedido;
		this.usuario = usuario;
		this.total = total;
		this.formaPago = formaPago;
	}

	/**
	 * Devuelve el identificador del pago.
	 *
	 * @return identificador del pago
	 */
	public int getIdPago() {
		return idPago;
	}

	/**
	 * Establece el identificador del pago.
	 *
	 * @param id nuevo identificador
	 */
	public void setIdPago(int id) {
		this.idPago = id;
	}

	/**
	 * Devuelve el pedido al que corresponde este pago.
	 *
	 * @return pedido asociado
	 */
	public Pedido getPedido() {
		return pedido;
	}

	/**
	 * Establece el pedido al que corresponde este pago.
	 *
	 * @param pedido nuevo pedido
	 */
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	/**
	 * Devuelve el usuario que realizó el pago.
	 *
	 * @return usuario asociado
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * Establece el usuario que realizó el pago.
	 *
	 * @param u nuevo usuario
	 */
	public void setUsuario(Usuario u) {
		this.usuario = u;
	}

	/**
	 * Devuelve el importe total abonado.
	 *
	 * @return total del pago
	 */
	public double getTotal() {
		return total;
	}

	/**
	 * Establece el importe total del pago.
	 *
	 * @param total nuevo importe total
	 */
	public void setTotal(double total) {
		this.total = total;
	}

	/**
	 * Devuelve la forma de pago utilizada.
	 *
	 * @return forma de pago
	 */
	public String getFormaPago() {
		return formaPago;
	}

	/**
	 * Establece la forma de pago utilizada.
	 *
	 * @param fp nueva forma de pago
	 */
	public void setFormaPago(String fp) {
		this.formaPago = fp;
	}

	/**
	 * Devuelve una representación legible del pago, incluyendo el número de pedido,
	 * el nombre del usuario, el importe total y la forma de pago.
	 *
	 * @return cadena con los datos del pago
	 */
	@Override
	public String toString() {
		return "Pago: " + "\n Pedido nº: " + pedido.getIdPedido() + "\n Usuario: " + usuario.getNombreCompleto()
				+ "\n Total: " + total + "€" + "\n Forma de pago: " + formaPago;
	}
}