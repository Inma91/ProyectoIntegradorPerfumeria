package com.goldentale.model.db;

public class Stock {

	private int id;
	private int idPerfume;
	private int cantidad;
	private String localizacion;

	public Stock(int id, int idPerfume, int cantidad, String localizacion) {
		this.id = id;
		this.idPerfume = idPerfume;
		this.cantidad = cantidad;
		this.localizacion = localizacion;
	}

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

	@Override
	public String toString() {
		return "Stock [id=" + id + ", idPerfume=" + idPerfume + ", cantidad=" + cantidad + ", localizacion="
				+ localizacion + "]";
	}
}