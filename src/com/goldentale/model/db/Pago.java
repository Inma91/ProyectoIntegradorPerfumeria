package com.goldentale.model.db;

/**
 * Entidad Pago. Representa el pago de un pedido en concreto
 *
 * @author Brandon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
 */
public class Pago {

	//Atributos
	private int idPago;
	private Pedido pedido;
	private Usuario usuario;
	private double total;
	private String formaPago;

	//Constructor
	public Pago(int idPago, Pedido pedido, Usuario usuario, double total, String formaPago) {
		this.idPago = idPago;
		this.pedido = pedido;
		this.usuario = usuario;
		this.total = total;
		this.formaPago = formaPago;
	}

	//Getters y setters
	public int getIdPago() {
		return idPago;
	}

	public void setIdPago(int id) {
		this.idPago = id;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario u) {
		this.usuario = u;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String fp) {
		this.formaPago = fp;
	}

	//toString
	@Override
	public String toString() {
		return "Pago: " +
				"\n Pedido nº: " + pedido.getIdPedido() +
				"\n Usuario: " + usuario.getNombreCompleto() +
				"\n Total: " + total + "€" +
				"\n Forma de pago: " + formaPago;
	}
}