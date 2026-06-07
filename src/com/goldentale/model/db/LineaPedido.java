package com.goldentale.model.db;

/**
 * Entidad LineaPedido. Representa una línea (perfume + cantidad) dentro de un pedido.
 *
 * @author Brandon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
 */
public class LineaPedido {

	//Atributos
	private Pedido pedido;
	private Perfumes perfume;
	private int cantidad;
	private double precioUnitario;
	private double subtotal;

	//Constructor
	public LineaPedido(Pedido pedido, Perfumes perfume, int cantidad, double precioUnitario, double subtotal) {
		this.pedido = pedido;
		this.perfume = perfume;
		this.cantidad = cantidad;
		this.precioUnitario = precioUnitario;
		this.subtotal = subtotal;
	}

	//Getters y setters
	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Perfumes getPerfume() {
		return perfume;
	}

	public void setPerfume(Perfumes perfume) {
		this.perfume = perfume;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(double pu) {
		this.precioUnitario = pu;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	//toString
	@Override
	public String toString() {
		return "Pedido nº " + pedido.getIdPedido() + ": " +
				"\n Unidades del perfume " + perfume.getNombre() + ": " + cantidad + 
				"\n Subtotal " + subtotal + "€";
	}
}