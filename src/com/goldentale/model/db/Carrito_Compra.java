package com.goldentale.model.db;

public class Carrito_Compra {

	private int idCarrito;
	private Usuario usuario;
	private Perfumes perfume;
	private int cantidad;
	private double precioUnidad;

	public Carrito_Compra() {
		// TODO cargar datos reales
	}

	public Carrito_Compra(int idCarrito, Usuario usuario, Perfumes perfume, int cantidad, double precioUnidad) {
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

	public void setIdCarrito(int idCarrito) {
		this.idCarrito = idCarrito;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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

	public double getPrecioUnidad() {
		return precioUnidad;
	}

	public void setPrecioUnidad(double precioUnidad) {
		this.precioUnidad = precioUnidad;
	}
}
