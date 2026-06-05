package com.goldentale.main;

import com.goldentale.model.db.Usuario;
import com.goldentale.model.db.UsuarioDAO;

/**
 * Clase temporal para verificar el funcionamiento del UsuarioDAO.
 * BORRAR cuando ya no se necesite.
 */
public class TestDAO {

	public static void main(String[] args) {

		UsuarioDAO dao = new UsuarioDAO();

		// Caso 1: usuario CLIENTE válido
		System.out.println("── Caso 1: cliente con credenciales correctas ──");
		Usuario u1 = dao.autenticar("ana.garcia@email.com", "hash_cli001");
		System.out.println(u1 != null ? u1 : "NULL (login fallido)");

		// Caso 2: usuario EMPLEADO válido
		System.out.println("\n── Caso 2: empleado con credenciales correctas ──");
		Usuario u2 = dao.autenticar("roberto.iglesias@goldentale.com", "hash_74829301");
		System.out.println(u2 != null ? u2 : "NULL (login fallido)");

		// Caso 3: contraseña incorrecta
		System.out.println("\n── Caso 3: contraseña incorrecta ──");
		Usuario u3 = dao.autenticar("ana.garcia@email.com", "contraseña_mala");
		System.out.println(u3 != null ? u3 : "NULL (login fallido)");

		// Caso 4: email que no existe
		System.out.println("\n── Caso 4: email inexistente ──");
		Usuario u4 = dao.autenticar("nadie@nada.com", "loquesea");
		System.out.println(u4 != null ? u4 : "NULL (login fallido)");
	}
}