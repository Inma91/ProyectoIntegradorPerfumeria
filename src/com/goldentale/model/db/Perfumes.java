package com.goldentale.model.db;

/**
 * 
 * Entidad {@link Perfumes} que representa un perfume disponible en el catálogo
 * de Golden Tale.
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

	/**
	 * 
	 * Inicializa un perfume con toda su información principal.
	 *
	 * @param idPerfume   Identificador único del perfume.
	 * @param nombre      Nombre comercial del perfume.
	 * @param marca       Marca o casa de perfumería.
	 * @param categoria   Categoría olfativa del perfume.
	 * @param descripcion Descripción y notas olfativas.
	 * @param precio      Precio del perfume en euros.
	 * @param ml          Capacidad del frasco en mililitros.
	 * @param publico     Público objetivo del perfume.
	 */
	public Perfumes(int idPerfume, String nombre, String marca, String categoria, String descripcion, double precio,
			int ml, String publico) {

		this.idPerfume = idPerfume;
		this.nombre = nombre;
		this.marca = marca;
		this.categoria = categoria;
		this.descripcion = descripcion;
		this.precio = precio;
		this.ml = ml;
		this.publico = publico;
	}

	/**
	 * 
	 * Devuelve el identificador del perfume.
	 *
	 * @return ID del perfume.
	 */
	public int getIdPerfume() {
		return idPerfume;
	}

	/**
	 * 
	 * Modifica el identificador del perfume.
	 *
	 * @param id Nuevo ID.
	 */
	public void setIdPerfume(int id) {
		this.idPerfume = id;
	}

	/**
	 * 
	 * Devuelve el nombre del perfume.
	 *
	 * @return Nombre comercial.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * 
	 * Modifica el nombre del perfume.
	 *
	 * @param nombre Nuevo nombre.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * 
	 * Devuelve la marca del perfume.
	 *
	 * @return Marca del perfume.
	 */
	public String getMarca() {
		return marca;
	}

	/**
	 * 
	 * Modifica la marca del perfume.
	 *
	 * @param marca Nueva marca.
	 */
	public void setMarca(String marca) {
		this.marca = marca;
	}

	/**
	 * 
	 * Devuelve la categoría olfativa.
	 *
	 * @return Categoría del perfume.
	 */
	public String getCategoria() {
		return categoria;
	}

	/**
	 * 
	 * Modifica la categoría olfativa.
	 *
	 * @param c Nueva categoría.
	 */
	public void setCategoria(String c) {
		this.categoria = c;
	}

	/**
	 * 
	 * Devuelve la descripción del perfume.
	 *
	 * @return Descripción del perfume.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * 
	 * Modifica la descripción del perfume.
	 *
	 * @param d Nueva descripción.
	 */
	public void setDescripcion(String d) {
		this.descripcion = d;
	}

	/**
	 * 
	 * Devuelve el precio del perfume.
	 *
	 * @return Precio en euros.
	 */
	public double getPrecio() {
		return precio;
	}

	/**
	 * 
	 * Modifica el precio del perfume.
	 *
	 * @param precio Nuevo precio.
	 */
	public void setPrecio(double precio) {
		this.precio = precio;
	}

	/**
	 * 
	 * Devuelve la capacidad del perfume en mililitros.
	 *
	 * @return Capacidad del frasco.
	 */
	public int getMl() {
		return ml;
	}

	/**
	 * 
	 * Modifica la capacidad del perfume.
	 *
	 * @param ml Nueva capacidad en mililitros.
	 */
	public void setMl(int ml) {
		this.ml = ml;
	}

	/**
	 * 
	 * Devuelve el público objetivo del perfume.
	 *
	 * @return Público objetivo.
	 */
	public String getPublico() {
		return publico;
	}

	/**
	 * 
	 * Modifica el público objetivo del perfume.
	 *
	 * @param publico Nuevo público objetivo.
	 */
	public void setPublico(String publico) {
		this.publico = publico;
	}

	/**
	 * 
	 * Devuelve una representación textual del perfume.
	 *
	 * @return Información resumida del perfume.
	 */
	@Override
	public String toString() {
		return "Perfumes: " + nombre + ", " + marca + ", " + categoria + ", " + ml + "ml" + ", " + publico + ", "
				+ precio + "€";
	}
}
