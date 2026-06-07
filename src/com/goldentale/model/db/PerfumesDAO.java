package com.goldentale.model.db;

import java.sql.*;
import java.util.ArrayList;

public class PerfumesDAO {

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

	static final String COL_ID_STOCK = "id_stock";
	static final String COL_CANTIDAD = "cantidad";
	static final String COL_LOCALIZACION = "localizacion";

	private final AccesoDBProp acc;

	public PerfumesDAO() {
		acc = new AccesoDBProp();
	}

	public ArrayList<Perfumes> getAll() {
		// TODO: implementar consulta JDBC
		return new ArrayList<>();
	}

	public Perfumes buscarPorNombreYMl(String nombre, int ml) {
		// TODO: implementar consulta JDBC
		return null;
	}

	public boolean insertar(Perfumes perfume) {
		// TODO: implementar insert JDBC
		return false;
	}

	public boolean actualizarStock(int idPerfume, int nuevaCantidad) {
		// TODO: implementar update JDBC
		return false;
	}
}