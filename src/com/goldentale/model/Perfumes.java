package com.goldentale.model;

public class Perfumes {

	//ATRIBUTO DEL PERFUME
	private String id; 
	private String nombre; 
	private String marca; 
	private String categoria; 
	private String descripcion;
	private double precio; 
	private int ml; 
	private String publico; 
	private String localizacion;
    private int stock;

	//CONSTRUCTOR
    public Perfumes(String id, String nombre, String marca, String categoria, String descripcion, double precio, int ml,
			String publico, String localizacion, int stock) {
		this.id = id;
		this.nombre = nombre;
		this.marca = marca;
		this.categoria = categoria;
		this.descripcion = descripcion;
		this.precio = precio;
		this.ml = ml;
		this.publico = publico;
		this.localizacion = localizacion;
		this.stock = stock;
	}	
    
	//Getters y setters por si acaso
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public int getMl() {
		return ml;
	}

	public void setMl(int ml) {
		this.ml = ml;
	}

	public String getPublico() {
		return publico;
	}

	public void setPublico(String publico) {
		this.publico = publico;
	}

	public String getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	//toString
	@Override
	public String toString() {
		return "Perfumes [id=" + id + ", nombre=" + nombre + ", marca=" + marca + ", categoria=" + categoria
				+ ", descripcion=" + descripcion + ", precio=" + precio + ", ml=" + ml + ", publico=" + publico
				+ ", localizacion=" + localizacion + ", stock=" + stock + "]";
	}

	
}
