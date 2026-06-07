package com.goldentale.model.db;

/**
 * Representa una línea de un pedido, formada por un perfume, la cantidad
 * solicitada, el precio unitario en el momento de la compra y el subtotal.
 *
 * @author Brandon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
 */
public class LineaPedido {

	private Pedido pedido;
	private Perfumes perfume;
	private int cantidad;
	private double precioUnitario;
	private double subtotal;

	/**
	 * Crea una nueva línea de pedido con todos sus datos.
	 *
	 * @param pedido         pedido al que pertenece esta línea
	 * @param perfume        perfume comprado
	 * @param cantidad       número de unidades
	 * @param precioUnitario precio por unidad en el momento de la compra
	 * @param subtotal       importe total de la línea (cantidad × precioUnitario)
	 */
	public LineaPedido(Pedido pedido, Perfumes perfume, int cantidad, double precioUnitario, double subtotal) {
		this.pedido = pedido;
		this.perfume = perfume;
		this.cantidad = cantidad;
		this.precioUnitario = precioUnitario;
		this.subtotal = subtotal;
	}

	/**
	 * Devuelve el pedido al que pertenece esta línea.
	 *
	 * @return pedido asociado
	 */
	public Pedido getPedido() {
		return pedido;
	}

	/**
	 * Establece el pedido al que pertenece esta línea.
	 *
	 * @param pedido nuevo pedido
	 */
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	/**
	 * Devuelve el perfume asociado a esta línea.
	 *
	 * @return perfume comprado
	 */
	public Perfumes getPerfume() {
		return perfume;
	}

	/**
	 * Establece el perfume de esta línea.
	 *
	 * @param perfume nuevo perfume
	 */
	public void setPerfume(Perfumes perfume) {
		this.perfume = perfume;
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
	 * Devuelve el precio unitario registrado en el momento de la compra.
	 *
	 * @return precio por unidad
	 */
	public double getPrecioUnitario() {
		return precioUnitario;
	}

	/**
	 * Establece el precio unitario de esta línea.
	 *
	 * @param pu nuevo precio por unidad
	 */
	public void setPrecioUnitario(double pu) {
		this.precioUnitario = pu;
	}

	/**
	 * Devuelve el subtotal de esta línea.
	 *
	 * @return importe total de la línea
	 */
	public double getSubtotal() {
		return subtotal;
	}

	/**
	 * Establece el subtotal de esta línea.
	 *
	 * @param subtotal nuevo subtotal
	 */
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	/**
	 * Devuelve una representación legible de la línea de pedido, incluyendo el
	 * número de pedido, el nombre del perfume, las unidades y el subtotal.
	 *
	 * @return cadena con los datos de la línea
	 */
	@Override
	public String toString() {
		return "Pedido nº " + pedido.getIdPedido() + ": " + "\n Unidades del perfume " + perfume.getNombre() + ": "
				+ cantidad + "\n Subtotal " + subtotal + "€";
	}
}