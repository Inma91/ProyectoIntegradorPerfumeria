package com.goldentale.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.goldentale.model.db.AccesoDBProp;
import com.goldentale.model.db.PedidosDAO;
import com.goldentale.model.db.Perfumes;
import com.goldentale.model.db.PerfumesDAO;
import com.goldentale.model.db.Usuario;
import com.goldentale.model.db.UsuarioDAO;

public class DBTest {

	private static UsuarioDAO usuarioDAO;
	private static PerfumesDAO perfumesDAO;
	private static PedidosDAO pedidosDAO;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		AccesoDBPropTest a = new AccesoDBPropTest();
		usuarioDAO = new UsuarioDAO(a);
		perfumesDAO = new PerfumesDAO(a);
		pedidosDAO = new PedidosDAO(a);
	}
	
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}
	

	@After
	public void tearDown() throws Exception {
	}

	//usuario
	
	@Test
	public void testUsuarioCorrecto() {
		Usuario usuario = usuarioDAO.autenticar("roberto.iglesias@goldentale.com", "hash_74829301");
		assertNotNull("LogIn Correcto", usuario);
	}
	
	@Test
	public void testUsuarioIncorrecto() {
		Usuario usuario = usuarioDAO.autenticar("roberto.iglesias@goldentale.com", "hash_cli001");
		assertNull("LogIn Incorrecto", usuario);
	}
	
	@Test
	public void testEmailCorrecto() {
		boolean comprobar = usuarioDAO.existeEmail("roberto.iglesias@goldentale.com");
		assertTrue("Existe email", comprobar);
	}
	
	@Test
	public void testEmailIncorrecto() {
		boolean comprobar = usuarioDAO.existeEmail("amapola.delcampo@goldentale.com");
		assertFalse("No existe email", comprobar);
	}
	
	@Test
	public void testRegistrarUsuario() {
		Usuario usuario = new Usuario (65, "Prueba", "Test", "Calle", "Numero telf 6 ", "prueba@test.com",
				"78910", "empleado"); 
				
		usuarioDAO.registrar(usuario);
		
		Usuario usuario2 = usuarioDAO.autenticar("prueba@test.com", "78910");
		assertNotNull("Registrar Usuario", usuario2);
	}
	
	//perfumes
	@Test
	public void testSelecTodosPerfumes() {
		ArrayList<Perfumes> listaPerfumes = perfumesDAO.getAll();
		assertTrue("NumPerfumes Correcto", listaPerfumes.size() > 1);
		System.out.println(listaPerfumes);
	}

	

}
