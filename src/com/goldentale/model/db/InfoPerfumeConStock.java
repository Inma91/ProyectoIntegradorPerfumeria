
package com.goldentale.model.db;

/**
 * Agrupa un {@link Perfumes} con su {@link Stock} asociado.
 * <p>
 * Se utiliza como objeto de transferencia entre los DAOs y el controlador para
 * trabajar con los datos de un perfume y su stock de forma conjunta.
 * </p>
 *
 * @author Brandon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
 */
public class InfoPerfumeConStock {

	private Perfumes perfume;
	private Stock stock;

	/**
	 * Crea una nueva instancia asociando un perfume con su stock.
	 *
	 * @param perfume datos del perfume
	 * @param stock   datos de stock del perfume
	 */
	public InfoPerfumeConStock(Perfumes perfume, Stock stock) {
		this.perfume = perfume;
		this.stock = stock;
	}

	/**
	 * Devuelve el perfume asociado.
	 *
	 * @return perfume
	 */
	public Perfumes getPerfume() {
		return perfume;
	}

	/**
	 * Establece el perfume asociado.
	 *
	 * @param perfume nuevo perfume
	 */
	public void setPerfume(Perfumes perfume) {
		this.perfume = perfume;
	}

	/**
	 * Devuelve el stock asociado al perfume.
	 *
	 * @return stock
	 */
	public Stock getStock() {
		return stock;
	}

	/**
	 * Establece el stock asociado al perfume.
	 *
	 * @param stock nuevo stock
	 */
	public void setStock(Stock stock) {
		this.stock = stock;
	}

	/**
	 * Devuelve una representación legible del perfume junto con su información de
	 * stock, incluyendo nombre, marca, categoría, precio, volumen, público
	 * objetivo, cantidad disponible y localización en almacén.
	 *
	 * @return cadena con los datos del perfume y su stock
	 */
	@Override
	public String toString() {
		return "Perfume: " + perfume.getNombre() + "\n Marca: " + perfume.getMarca() + "\n Categoría: "
				+ perfume.getCategoria() + "\n Precio: " + perfume.getPrecio() + "€" + "\n Volumen: " + perfume.getMl()
				+ "ml" + "\n Público: " + perfume.getPublico() + "\n Stock disponible: " + stock.getCantidad()
				+ " unidades" + "\n Localización: " + stock.getLocalizacion();
	}
}