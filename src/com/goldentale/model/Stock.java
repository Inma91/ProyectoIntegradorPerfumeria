package com.goldentale.model;

public class Stock {

	//ATRIBUTOS
	private String id;
    private String idPerfume;
    private int cantidad;
    private String localizacion;
	
    //CONSTRUCTOR
    public Stock(String id, String idPerfume, int cantidad, String localizacion) {
		this.id = id;
		this.idPerfume = idPerfume;
		this.cantidad = cantidad;
		this.localizacion = localizacion;
	}

    //Getters y Setters por si acaso
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdPerfume() {
		return idPerfume;
	}

	public void setIdPerfume(String idPerfume) {
		this.idPerfume = idPerfume;
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

	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}

	//toString
	@Override
	public String toString() {
		return "Stock [id=" + id + ", idPerfume=" + idPerfume + ", cantidad=" + cantidad + ", localizacion="
				+ localizacion + "]";
	}
    
    
}
