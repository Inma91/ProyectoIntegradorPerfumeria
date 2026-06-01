package com.goldentale.model.db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class AccesoDBProp {
	//ATRIBUTO - inicializados en el constructor
	private String driver; 
	private String url;
	
	//CONSTRUCTOR - definir driver y url -- ESTA INFORMACIÓN LA HABÍAMOS EXTRAIDO  ASI QUE AHORA TENEMOS QUE LIGARLO AL LUGAR EN QUE ESTÉ GUARDADA LA INFORMACIÓN
	public AccesoDBProp() {
		//en lugar de acceder a los datos directamente accedemos al archivo donde está la información 
		Properties prop = new Properties(); 
		//flujo de entrada de información
		InputStream is = null; 
		try {
		//flujo o canal de comunicación con algo externo
		is = new FileInputStream("BBDD_GoldenTale/ConfiguracionDB.properties"); 
		prop.load(is);
		
		//LA CLAVE TIENE QUE COINCIDIR TAL COMO ESTÁ ESCRITA EN EL ARCHIVO.PROPERTIES
		driver = prop.getProperty("DRIVER"); 
		url = prop.getProperty("URL"); 
		
		}catch (FileNotFoundException e) {
			System.out.println("El fichero no ha sido encontrado");
		}catch (IOException e) {
			System.out.println("No se ha podido leer el fichero");
		}
	} 
	
	//CREAR UN METODO QUE RETORNE UN OBJETO DE TIPO CONEXION
	public Connection getConexion() throws ClassNotFoundException, SQLException {
		//especificar el driver - el error que me da es de excepciones para evitarlo propagamos la excepción (Adds throws declaration)
		Class.forName(driver); 
		//establecer conexión - usar DriverManager - también se provoca una excepción y vamos a hacer lo mismo (Adds throws declaration, se añadde en la línea 19)
		Connection con = DriverManager.getConnection(url); 
		//conexión que retornar
		return con; 
	}
}
