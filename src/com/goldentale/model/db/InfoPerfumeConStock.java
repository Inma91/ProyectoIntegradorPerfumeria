//Para poder meter todo junto la información del perfume y la cantidad de Stock
package com.goldentale.model.db;

public class InfoPerfumeConStock {

	//Atributos
	private Perfumes perfume;
	private Stock stock;

	//Constructor
	public InfoPerfumeConStock(Perfumes perfume, Stock stock) {
		this.perfume = perfume;
		this.stock = stock;
	}

	//Getters y setters
	public Perfumes getPerfume() {
		return perfume;
	}

	public void setPerfume(Perfumes perfume) {
		this.perfume = perfume;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	//toString
	@Override
	public String toString() {
		return "Perfume: " + perfume.getNombre() + 
				"\n Marca: " + perfume.getMarca() + 
				"\n Categoría: " + perfume.getCategoria() +
				"\n Precio: " + perfume.getPrecio() + "€" +
				"\n Volumen: " + perfume.getMl() + "ml" +
				"\n Público: " + perfume.getPublico() +
				"\n Stock disponible: " + stock.getCantidad() + " unidades" +
				"\n Localización: " + stock.getLocalizacion();
	}
}