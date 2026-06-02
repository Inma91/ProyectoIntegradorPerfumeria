package com.goldentale.model.db;

/**
 * Entidad Perfume. Representa un perfume del catálogo.
 *
 * @author Brandon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
 */
public class Perfumes {

	private int idPerfume;
	private String nombre;
	private String marca;
	private String categoria;
	private String descripcion;
	private double precio;
	private int ml;
	private String publico;
	private String localizacion;
	private int stock;

	/**
	 * Constructor completo.
	 *
	 * @param idPerfume    ID en la base de datos (null si es nuevo y el ID lo
	 *                     genera la BD).
	 * @param nombre       Nombre comercial del perfume.
	 * @param marca        Casa de perfumería.
	 * @param categoria    Categoría olfativa (Floral, Oriental, etc.).
	 * @param descripcion  Notas olfativas y descripción (puede ser vacía).
	 * @param precio       Precio en euros.
	 * @param ml           Volumen del frasco en mililitros.
	 * @param publico      Público objetivo (Mujer, Hombre, Unisex).
	 * @param localizacion Estante asignado en el almacén.
	 * @param stock        Unidades disponibles.
	 */
	public Perfumes(Object idPerfume, String nombre, String marca, String categoria, String descripcion, double precio,
			int ml, String publico, String localizacion, int stock) {
		this.idPerfume = (idPerfume instanceof Integer) ? (Integer) idPerfume : 0;
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

	// ── Getters y setters ─────────────────────────────────────────────

	public int getIdPerfume() {
		return idPerfume;
	}

	public void setIdPerfume(int id) {
		this.idPerfume = id;
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

	public void setCategoria(String c) {
		this.categoria = c;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String d) {
		this.descripcion = d;
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

	public void setLocalizacion(String loc) {
		this.localizacion = loc;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	@Override
	public String toString() {
		return nombre + " (" + marca + ") - " + ml + "ml - " + String.format("%.2f€", precio);
	}
}
