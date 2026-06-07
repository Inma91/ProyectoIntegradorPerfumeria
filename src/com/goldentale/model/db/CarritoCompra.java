package com.goldentale.model.db;

/**
 * Representa una línea del carrito de compra activo en memoria.
 * <p>
 * Cada instancia asocia un {@link Usuario} y un {@link Perfumes} con la
 * cantidad elegida y el precio unitario en el momento de añadirlo al carrito.
 * </p>
 *
 * @author Brandon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
 */
public class CarritoCompra {

	private int idCarrito;
	private Usuario usuario;
	private Perfumes perfume;
	private int cantidad;
	private double precioUnidad;

	/**
	 * Crea una nueva línea de carrito con todos sus datos.
	 *
	 * @param idCarrito    identificador temporal de la línea en memoria
	 * @param usuario      usuario propietario del carrito
	 * @param perfume      perfume añadido
	 * @param cantidad     número de unidades
	 * @param precioUnidad precio por unidad en el momento de añadir al carrito
	 */
	public CarritoCompra(int idCarrito, Usuario usuario, Perfumes perfume, int cantidad, double precioUnidad) {
		this.idCarrito = idCarrito;
		this.usuario = usuario;
		this.perfume = perfume;
		this.cantidad = cantidad;
		this.precioUnidad = precioUnidad;
	}

	/**
	 * Calcula el subtotal de esta línea multiplicando cantidad por precio unitario.
	 *
	 * @return subtotal de la línea
	 */
	public double calcularSubtotal() {
		return cantidad * precioUnidad;
	}

	/**
	 * Devuelve el identificador temporal de esta línea de carrito.
	 *
	 * @return identificador de la línea
	 */
	public int getIdCarrito() {
		return idCarrito;
	}

	/**
	 * Establece el identificador temporal de esta línea de carrito.
	 *
	 * @param id nuevo identificador
	 */
	public void setIdCarrito(int id) {
		this.idCarrito = id;
	}

	/**
	 * Devuelve el usuario propietario del carrito.
	 *
	 * @return usuario asociado
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * Establece el usuario propietario del carrito.
	 *
	 * @param u nuevo usuario
	 */
	public void setUsuario(Usuario u) {
		this.usuario = u;
	}

	/**
	 * Devuelve el perfume asociado a esta línea.
	 *
	 * @return perfume de la línea
	 */
	public Perfumes getPerfume() {
		return perfume;
	}

	/**
	 * Establece el perfume de esta línea.
	 *
	 * @param p nuevo perfume
	 */
	public void setPerfume(Perfumes p) {
		this.perfume = p;
	}

	/**
	 * Devuelve la cantidad de unidades de esta línea.
	 *
	 * @return número de unidades
	 */
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * Establece la cantidad de unidades de esta línea.
	 *
	 * @param cantidad nueva cantidad
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * Devuelve el precio unitario registrado en el momento de añadir al carrito.
	 *
	 * @return precio por unidad
	 */
	public double getPrecioUnidad() {
		return precioUnidad;
	}

	/**
	 * Establece el precio unitario de esta línea.
	 *
	 * @param precio nuevo precio por unidad
	 */
	public void setPrecioUnidad(double precio) {
		this.precioUnidad = precio;
	}
}