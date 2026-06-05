package com.goldentale.model.db;

/**
 * Entidad línea de carrito de compra.
 */
public class CarritoCompra {

	private int idCarrito;
	private Usuario usuario;
	private Perfumes perfume;
	private int cantidad;
	private double precioUnidad;

	public CarritoCompra(int idCarrito, Usuario usuario, Perfumes perfume, int cantidad, double precioUnidad) {
		this.idCarrito = idCarrito;
		this.usuario = usuario;
		this.perfume = perfume;
		this.cantidad = cantidad;
		this.precioUnidad = precioUnidad;
	}

	public double calcularSubtotal() {
		return cantidad * precioUnidad;
	}

	public int getIdCarrito() {
		return idCarrito;
	}

	public void setIdCarrito(int id) {
		this.idCarrito = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario u) {
		this.usuario = u;
	}

	public Perfumes getPerfume() {
		return perfume;
	}

	public void setPerfume(Perfumes p) {
		this.perfume = p;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getPrecioUnidad() {
		return precioUnidad;
	}

	public void setPrecioUnidad(double precio) {
		this.precioUnidad = precio;
	}
}
