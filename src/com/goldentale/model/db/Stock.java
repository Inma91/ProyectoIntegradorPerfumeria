package com.goldentale.model.db;

/**
 * Entidad {@link Stock} que representa el stock disponible de un perfume dentro
 * del almacén.
 *
 * @author Brandon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
 */
public class Stock {

	private int id;
	private int idPerfume;
	private int cantidad;
	private String localizacion;

	/**
	 * Inicializa un registro de stock asociado a un perfume.
	 *
	 * @param id           Identificador del registro de stock.
	 * @param idPerfume    ID del perfume asociado.
	 * @param cantidad     Cantidad disponible.
	 * @param localizacion Ubicación del perfume en el almacén.
	 */
	public Stock(int id, int idPerfume, int cantidad, String localizacion) {
		this.id = id;
		this.idPerfume = idPerfume;
		this.cantidad = cantidad;
		this.localizacion = localizacion;
	}

	/**
	 * Devuelve el identificador del stock.
	 *
	 * @return ID del stock.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Modifica el identificador del stock.
	 *
	 * @param id Nuevo identificador.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Devuelve el ID del perfume asociado.
	 *
	 * @return ID del perfume.
	 */
	public int getIdPerfume() {
		return idPerfume;
	}

	/**
	 * Modifica el ID del perfume asociado.
	 *
	 * @param ip Nuevo ID del perfume.
	 */
	public void setIdPerfume(int ip) {
		this.idPerfume = ip;
	}

	/**
	 * Devuelve la cantidad disponible.
	 *
	 * @return Cantidad en stock.
	 */
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * Modifica la cantidad disponible.
	 *
	 * @param cantidad Nueva cantidad.
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * Devuelve la ubicación del perfume en el almacén.
	 *
	 * @return Ubicación del stock.
	 */
	public String getLocalizacion() {
		return localizacion;
	}

	/**
	 * Modifica la ubicación del perfume.
	 *
	 * @param loc Nueva ubicación.
	 */
	public void setLocalizacion(String loc) {
		this.localizacion = loc;
	}

	/**
	 * Devuelve una representación textual del stock.
	 *
	 * @return Información resumida del stock.
	 */
	@Override
	public String toString() {
		return "Stock del perfume " + idPerfume + " = " + cantidad + " unidades en " + localizacion + " del almacén.";
	}
}