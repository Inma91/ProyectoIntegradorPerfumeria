package com.goldentale.model.db;

/**
 * Entidad Stock. Representa el stock de un perfume en el almacén.
 */
public class Stock {

	//Atributos
	private int id;
	private int idPerfume;
	private int cantidad;
	private String localizacion;

	//Constructor
	public Stock(int id, int idPerfume, int cantidad, String localizacion) {
		this.id = id;
		this.idPerfume = idPerfume;
		this.cantidad = cantidad;
		this.localizacion = localizacion;
	}

	//Getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdPerfume() {
		return idPerfume;
	}

	public void setIdPerfume(int ip) {
		this.idPerfume = ip;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(String loc) {
		this.localizacion = loc;
	}

	//toString
	@Override
	public String toString() {
		return "Stock del perfume " + idPerfume + " = " + cantidad + " unidades en " + localizacion + " del almacén.";
	}
}
