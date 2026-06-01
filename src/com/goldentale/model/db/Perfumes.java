package com.goldentale.model.db;

public class Perfumes {
	
	//ATRIBUTO DEL PERFUME
	private int idPerfume;
	private String nombre;
	private String marca;
	private String categoria;
	private String publico;
	private int ml;
	private double precio;
	private String descripcion;
	private String stock;
	private String localizacion;

	//CONSTRUCTOR
	public Perfumes(int idPerfume, String nombre, String marca, String categoria, String publico, double precio2, double precio,
			String descripcion, String localizacion2, String stock2) {
		this.idPerfume = idPerfume;
		this.nombre = nombre;
		this.marca = marca;
		this.categoria = categoria;
		this.publico = publico;
		this.ml = (int) precio2;
		this.precio = precio;
		this.descripcion = descripcion;
		this.stock = localizacion2;
		this.localizacion = stock2;
	}
	//Getters y setters por si acaso
	
	public Perfumes(Object object, String nombre2, String marca2, String categoria2, String descripcion2,
			double precio2, int ml2, String publico2, String localizacion2, int stock2) {
		// TODO Auto-generated constructor stub
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

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}
}
