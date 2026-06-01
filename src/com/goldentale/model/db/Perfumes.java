package com.goldentale.model.db;

public class Perfumes {

	private int idPerfume;
	private String nombre;
	private String marca;
	private String categoria;
	private String publico;
	private int ml;
	private double precio;
	private String descripcion;
	private int stock;
	private String localizacion;

	public Perfumes() {
		// TODO conectar con DAO
	}

	public Perfumes(int idPerfume, String nombre, String marca, String categoria, String publico, int ml, double precio,
			String descripcion, int stock, String localizacion) {
		this.idPerfume = idPerfume;
		this.nombre = nombre;
		this.marca = marca;
		this.categoria = categoria;
		this.publico = publico;
		this.ml = ml;
		this.precio = precio;
		this.descripcion = descripcion;
		this.stock = stock;
		this.localizacion = localizacion;
	}

	public int getIdPerfume() {
		return idPerfume;
	}

	public void setIdPerfume(int idPerfume) {
		this.idPerfume = idPerfume;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getPublico() {
		return publico;
	}

	public void setPublico(String publico) {
		this.publico = publico;
	}

	public int getMl() {
		return ml;
	}

	public void setMl(int ml) {
		this.ml = ml;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}
}
