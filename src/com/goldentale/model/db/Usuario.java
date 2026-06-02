package com.goldentale.model.db;

/**
 * Entidad Usuario. Base para Cliente y Empleado (herencia por rol).
 *
 * @author Brandon Gaviria
 * @author Inmaculada Gil
 * @author David Moreno
 */
public class Usuario {

	private int idUsuario;
	private String nombre;
	private String apellido;
	private String direccion;
	private String telefono;
	private String email;
	private String password;
	private String rol;

	public Usuario(int idUsuario, String nombre, String apellido, String direccion, String telefono, String email,
			String password, String rol) {
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.apellido = apellido;
		this.direccion = direccion;
		this.telefono = telefono;
		this.email = email;
		this.password = password;
		this.rol = rol;
	}

	// ── Getters y setters ─────────────────────────────────────────────

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int id) {
		this.idUsuario = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String a) {
		this.apellido = a;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String d) {
		this.direccion = d;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String t) {
		this.telefono = t;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String e) {
		this.email = e;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String p) {
		this.password = p;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	/** Nombre completo: nombre + apellido. */
	public String getNombreCompleto() {
		return nombre + " " + apellido;
	}
}
