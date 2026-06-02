package com.goldentale.model.db;

import java.sql.*;
import java.util.ArrayList;

/**
 * DAO de Perfumes. Todas las operaciones JDBC van aquí. Los métodos están
 * declarados con su firma 
 */
public class PerfumesDAO {

	// ── Constantes de tabla y columnas ────────────────────────────────
	static final String NOM_TABLA_PERFUME = "perfume";
	static final String NOM_TABLA_STOCK = "stock";

	static final String COL_ID_PERFUME = "id_perfume";
	static final String COL_NOMBRE = "nombre_perfume";
	static final String COL_MARCA = "marca";
	static final String COL_CATEGORIA = "categoria";
	static final String COL_DESCRIPCION = "descripcion";
	static final String COL_PRECIO = "precio";
	static final String COL_ML = "ml";
	static final String COL_PUBLICO = "publico_objetivo";
	static final String COL_IMAGEN = "imagen_url";

	static final String COL_ID_STOCK = "id_stock";
	static final String COL_CANTIDAD = "cantidad";
	static final String COL_LOCALIZACION = "localizacion";

	private final AccesoDBProp acc;

	public PerfumesDAO() {
		acc = new AccesoDBProp();
	}

	/**
	 * Devuelve todos los perfumes del catálogo con su stock. TODO: implementar
	 * consulta JDBC (JOIN perfume + stock).
	 */
	public ArrayList<Perfumes> getAll() {
		// TODO: implementar consulta JDBC
		return new ArrayList<>();
	}

	/**
	 * Busca un perfume por nombre y mililitros. TODO: implementar consulta JDBC con
	 * PreparedStatement.
	 */
	public Perfumes buscarPorNombreYMl(String nombre, int ml) {
		// TODO: implementar consulta JDBC
		return null;
	}

	/**
	 * Inserta un nuevo perfume en la BD y crea su registro de stock. TODO:
	 * implementar insert en tabla perfume y en tabla stock.
	 */
	public boolean insertar(Perfumes perfume) {
		// TODO: implementar insert JDBC
		return false;
	}

	/**
	 * Actualiza el stock de un perfume existente. TODO: implementar update en tabla
	 * stock.
	 */
	public boolean actualizarStock(int idPerfume, int nuevaCantidad) {
		// TODO: implementar update JDBC
		return false;
	}
}
