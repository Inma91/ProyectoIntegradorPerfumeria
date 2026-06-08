package com.goldentale.test;

import java.io.*;
import java.sql.*;
import java.util.Properties;

import com.goldentale.model.db.AccesoDBProp;

/**
 * Lee la configuración de conexión desde {@code ConfiguracionDB.properties} y
 * proporciona conexiones a la base de datos listas para usar.
 * <p>
 * El fichero de propiedades debe contener las claves {@code DRIVER} y
 * {@code URL}.
 * </p>
 *
 * @author Brandon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
 */
public class AccesoDBPropTest extends AccesoDBProp {

	private String driver;
	private String url;

	/**
	 * Carga el fichero {@code ConfiguracionDB.properties} y extrae el driver JDBC y
	 * la URL de conexión.
	 * <p>
	 * Si el fichero no existe o no puede leerse, muestra un mensaje de error por
	 * consola y deja los atributos sin inicializar.
	 * </p>
	 */
	public AccesoDBPropTest() {
		Properties prop = new Properties();
		InputStream is = null;
		try {
			is = new FileInputStream("BBDDTest/ConfiguracionDB.properties");
			prop.load(is);
			driver = prop.getProperty("DRIVER");
			url = prop.getProperty("URL");
		} catch (FileNotFoundException e) {
			System.out.println("El fichero de configuración no ha sido encontrado.");
		} catch (IOException e) {
			System.out.println("No se ha podido leer el fichero de configuración.");
		}
	}

	/**
	 * Crea y devuelve una nueva conexión a la base de datos usando el driver y la
	 * URL cargados desde el fichero de propiedades.
	 *
	 * @return conexión JDBC lista para usar
	 * @throws ClassNotFoundException si el driver JDBC no se encuentra en el
	 *                                classpath
	 * @throws SQLException           si la conexión con la base de datos falla
	 */
	public Connection getConexion() throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		return DriverManager.getConnection(url);
	}
}