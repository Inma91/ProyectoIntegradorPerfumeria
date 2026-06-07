package com.goldentale.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.goldentale.model.data.ConstantesTablas;

public class UsuarioDAO {

	// ATRIBUTO QUE REPRESENTA LA CLASE QUE ME VA A FACILITAR LA CONEXIÓN
	private AccesoDBProp acc;

	public UsuarioDAO() {
		acc = new AccesoDBProp();
	}

	// SENTENCIAS Y MÉTODOS QUE VOY A NECESITAR

	// 1. AUTENTICAR USUARIO: busca en la BBDD un email y contraseña concretos
	public Usuario autenticar(String email, String password) {
		Usuario usuario = null;

		String query = "SELECT " + "*" + " FROM " + ConstantesTablas.TABLA_USUARIO + " WHERE "
				+ ConstantesTablas.COL_USUARIO_EMAIL + " = ? " + " AND " + ConstantesTablas.COL_USUARIO_PASSWD
				+ " = ? ";

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rslt = null;

		try {
			con = acc.getConexion();

			stmt = con.prepareStatement(query);
			stmt.setString(1, email);
			stmt.setString(2, password);
			rslt = stmt.executeQuery();

			if (rslt.next()) {
				usuario = new Usuario(rslt.getInt(ConstantesTablas.COL_USUARIO_ID),
						rslt.getString(ConstantesTablas.COL_USUARIO_NOMBRE),
						rslt.getString(ConstantesTablas.COL_USUARIO_APELLIDO),
						rslt.getString(ConstantesTablas.COL_USUARIO_DIRECCION),
						rslt.getString(ConstantesTablas.COL_USUARIO_TELEFONO),
						rslt.getString(ConstantesTablas.COL_USUARIO_EMAIL),
						rslt.getString(ConstantesTablas.COL_USUARIO_PASSWD),
						rslt.getString(ConstantesTablas.COL_USUARIO_ROL));
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				if (rslt != null)
					rslt.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return usuario;
	}
	
	// 2. PARA REGISTRAR UN NUEVO USUARIO COMPROBAR PRIMERO SI YA EXISTE UN USUARIO CON ESE EMAIL
		public boolean existeEmail(String email) {

			boolean existe = false;

			String query = "SELECT " + ConstantesTablas.COL_USUARIO_ID
					+ " FROM " + ConstantesTablas.TABLA_USUARIO
					+ " WHERE " + ConstantesTablas.COL_USUARIO_EMAIL + " = ? ";

			Connection con = null;
			PreparedStatement stmt = null;
			ResultSet rslt = null;

			try {
				con = acc.getConexion();

				stmt = con.prepareStatement(query);
				stmt.setString(1, email);
				rslt = stmt.executeQuery();

				if (rslt.next()) {
					existe = true;
				}

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();

			} finally {
				try {
					if (rslt != null)
						rslt.close();
					if (stmt != null)
						stmt.close();
					if (con != null)
						con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			return existe;
		}

		// REGISTRAR NUEVO USUARIO
		public void registrar(Usuario usuario) {

			String query = "INSERT INTO " + ConstantesTablas.TABLA_USUARIO + " ( "
					+ ConstantesTablas.COL_USUARIO_PASSWD + " , "
					+ ConstantesTablas.COL_USUARIO_NOMBRE + " , "
					+ ConstantesTablas.COL_USUARIO_APELLIDO + " , "
					+ ConstantesTablas.COL_USUARIO_DIRECCION + " , "
					+ ConstantesTablas.COL_USUARIO_TELEFONO + " , "
					+ ConstantesTablas.COL_USUARIO_EMAIL + " , "
					+ ConstantesTablas.COL_USUARIO_ROL
					+ " ) VALUES (?, ?, ?, ?, ?, ?, ?) ";

			Connection con = null;
			PreparedStatement stmt = null;

			try {
				con = acc.getConexion();

				stmt = con.prepareStatement(query);
				stmt.setString(1, usuario.getPassword());
				stmt.setString(2, usuario.getNombre());
				stmt.setString(3, usuario.getApellido());
				stmt.setString(4, usuario.getDireccion());
				stmt.setString(5, usuario.getTelefono());
				stmt.setString(6, usuario.getEmail());
				stmt.setString(7, usuario.getRol());

				stmt.executeUpdate();

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();

			} finally {
				try {
					if (stmt != null)
						stmt.close();
					if (con != null)
						con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	
	
	
}