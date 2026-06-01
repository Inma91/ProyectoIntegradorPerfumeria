package com.goldentale.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PerfumesDAO {

	//CONSTANTES PARA TABLAS Y COLUMNAS
    static final String NOM_TABLA_PERFUME = "perfume";
    static final String NOM_TABLA_STOCK   = "stock";

    static final String COL_ID_PERFUME    = "id_perfume";
    static final String COL_NOMBRE        = "nombre_perfume";
    static final String COL_MARCA         = "marca";
    static final String COL_CATEGORIA     = "categoria";
    static final String COL_DESCRIPCION   = "descripcion";
    static final String COL_PRECIO        = "precio";
    static final String COL_ML            = "ml";
    static final String COL_PUBLICO       = "publico_objetivo";
    static final String COL_IMAGEN        = "imagen_url";

    static final String COL_ID_STOCK      = "id_stock";
    static final String COL_CANTIDAD      = "cantidad";
    static final String COL_LOCALIZACION  = "localizacion";
    
    //ATRIBUTO QUE REPRESENTA LA CLASE QUE ME VA A FACILITAR LA CONEXIÓN
    private AccesoDBProp acc;

    public PerfumesDAO() {
        acc = new AccesoDBProp();
    }
 
        
}
