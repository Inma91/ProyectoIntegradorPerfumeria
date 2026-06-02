package com.goldentale.model.db;

import java.io.*;
import java.sql.*;
import java.util.Properties;

/**
 * Lee la configuración de conexión desde ConfiguracionDB.properties
 * y devuelve una conexión con la Base de Datos lista para usar.
 */
public class AccesoDBProp {

    private String driver;
    private String url;

    public AccesoDBProp() {
        Properties prop = new Properties();
        InputStream is  = null;
        try {
            is = new FileInputStream("BBDD_GoldenTale/ConfiguracionDB.properties");
            prop.load(is);
            driver = prop.getProperty("DRIVER");
            url    = prop.getProperty("URL");
        } catch (FileNotFoundException e) {
            System.out.println("El fichero de configuración no ha sido encontrado.");
        } catch (IOException e) {
            System.out.println("No se ha podido leer el fichero de configuración.");
        }
    }

    public Connection getConexion() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        return DriverManager.getConnection(url);
    }
}
