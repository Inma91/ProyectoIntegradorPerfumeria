package com.goldentale.model.db;

/**
 * Entidad Stock. Representa el stock de un perfume en el almacén.
 */
public class Stock {

	private String id;
	private String idPerfume;
	private int cantidad;
	private String localizacion;

	public Stock(String id, String idPerfume, int cantidad, String localizacion) {
		this.id = id;
		this.idPerfume = idPerfume;
		this.cantidad = cantidad;
		this.localizacion = localizacion;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdPerfume() {
		return idPerfume;
	}

	public void setIdPerfume(String ip) {
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
