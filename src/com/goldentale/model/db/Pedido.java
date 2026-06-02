package com.goldentale.model.db;

/**
 * Entidad Pedido.
 */
public class Pedido {

	private int idPedido;
	private Usuario usuario;
	private String fecha;
	private String estado;
	private String metodoPago;
	private double total;

	public Pedido(int idPedido, Usuario usuario, String fecha, String estado, String metodoPago, double total) {
		this.idPedido = idPedido;
		this.usuario = usuario;
		this.fecha = fecha;
		this.estado = estado;
		this.metodoPago = metodoPago;
		this.total = total;
	}

	public int getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(int id) {
		this.idPedido = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario u) {
		this.usuario = u;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(String mp) {
		this.metodoPago = mp;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
}
