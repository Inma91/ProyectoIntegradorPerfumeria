package com.goldentale.model.db;

/**
 * Entidad {@link Usuario} que representa a los usuarios registrados en la
 * aplicación Golden Tale.
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

	/**
	 * Inicializa un usuario con toda su información personal y de acceso.
	 *
	 * @param idUsuario Identificador del usuario.
	 * @param nombre    Nombre del usuario.
	 * @param apellido  Apellido del usuario.
	 * @param direccion Dirección del usuario.
	 * @param telefono  Número de teléfono.
	 * @param email     Correo electrónico.
	 * @param password  Contraseña de acceso.
	 * @param rol       Rol asignado dentro de la aplicación.
	 */
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

	/**
	 * Devuelve el identificador del usuario.
	 *
	 * @return ID del usuario.
	 */
	public int getIdUsuario() {
		return idUsuario;
	}

	/**
	 * Modifica el identificador del usuario.
	 *
	 * @param id Nuevo ID.
	 */
	public void setIdUsuario(int id) {
		this.idUsuario = id;
	}

	/**
	 * Devuelve el nombre del usuario.
	 *
	 * @return Nombre del usuario.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Modifica el nombre del usuario.
	 *
	 * @param nombre Nuevo nombre.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Devuelve el apellido del usuario.
	 *
	 * @return Apellido del usuario.
	 */
	public String getApellido() {
		return apellido;
	}

	/**
	 * Modifica el apellido del usuario.
	 *
	 * @param a Nuevo apellido.
	 */
	public void setApellido(String a) {
		this.apellido = a;
	}

	/**
	 * Devuelve la dirección del usuario.
	 *
	 * @return Dirección registrada.
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * Modifica la dirección del usuario.
	 *
	 * @param d Nueva dirección.
	 */
	public void setDireccion(String d) {
		this.direccion = d;
	}

	/**
	 * Devuelve el teléfono del usuario.
	 *
	 * @return Número de teléfono.
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * Modifica el teléfono del usuario.
	 *
	 * @param t Nuevo teléfono.
	 */
	public void setTelefono(String t) {
		this.telefono = t;
	}

	/**
	 * Devuelve el correo electrónico del usuario.
	 *
	 * @return Correo electrónico.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Modifica el correo electrónico del usuario.
	 *
	 * @param e Nuevo correo electrónico.
	 */
	public void setEmail(String e) {
		this.email = e;
	}

	/**
	 * Devuelve la contraseña del usuario.
	 *
	 * @return Contraseña del usuario.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Modifica la contraseña del usuario.
	 *
	 * @param p Nueva contraseña.
	 */
	public void setPassword(String p) {
		this.password = p;
	}

	/**
	 * Devuelve el rol del usuario dentro de la aplicación.
	 *
	 * @return Rol asignado.
	 */
	public String getRol() {
		return rol;
	}

	/**
	 * Modifica el rol del usuario.
	 *
	 * @param rol Nuevo rol.
	 */
	public void setRol(String rol) {
		this.rol = rol;
	}

	/**
	 * Devuelve el nombre completo del usuario.
	 *
	 * @return Nombre y apellido concatenados.
	 */
	public String getNombreCompleto() {
		return "Nombre: " + nombre + " " + "Apellido: " + apellido;
	}

	/**
	 * Devuelve una representación textual del usuario.
	 *
	 * @return Información resumida del usuario.
	 */
	@Override
	public String toString() {
		return "Usuario " + "\n Nombre: " + nombre + "\n Apellido: " + apellido + "\n Dirección: " + direccion
				+ "\n Telefono: " + telefono + "\n email: " + email + "\n password: " + password + "\n rol: " + rol;
	}

}