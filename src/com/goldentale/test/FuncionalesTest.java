package com.goldentale.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.goldentale.controlador.Controlador;
import com.goldentale.model.data.Constantes;
import com.goldentale.model.db.CarritoCompra;
import com.goldentale.model.db.InfoPerfumeConStock;
import com.goldentale.model.db.Pedido;
import com.goldentale.model.db.Perfumes;
import com.goldentale.model.db.Stock;
import com.goldentale.model.db.Usuario;
import com.goldentale.model.db.UsuarioDAO;
import com.goldentale.vistaPrincipalLyR.VPGoldenTale;
import com.goldentale.vistaPrincipalLyR.VRegistroUsuario;

public class FuncionalesTest {

	private static VRegistroUsuario vistaRegistro;
	private static Controlador controlador;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		vistaRegistro = new VRegistroUsuario();
		VPGoldenTale ventana = new VPGoldenTale();
		controlador = new Controlador(ventana);
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

	// En usuario
	@Test
	public void testNombreCompleto() {
		Usuario usuario = new Usuario(65, "Prueba", "Test", "Calle", "Numero telf 6 ", "prueba@test.com", "78910",
				"empleado");

		assertEquals("Nombre: Prueba Apellido: Test", usuario.getNombreCompleto());
	}

	// En CarritoCompra
	@Test
	public void testCalcularSubotal() {
		Usuario usuario = new Usuario(65, "Prueba", "Test", "Calle", "Numero telf 6 ", "prueba@test.com", "78910",
				"empleado");
		Perfumes perfume = new Perfumes(96, "Prueba", "Prueba", "Floral", "Huela a rosas", 25, 50, "Unisex");

		CarritoCompra compra = new CarritoCompra(85, usuario, perfume, 5, 25);
		assertEquals(125, compra.calcularSubtotal(), 0.05);
	}

	// En VRegistroUsuario
	@Test
	public void testValidarRegistroU() {
		assertEquals("Todos los campos son obligatorios.", vistaRegistro.validar("", "", "", "", "", ""));

		assertEquals("Todos los campos son obligatorios.",
				vistaRegistro.validar("Antonio", "García", "Arbusto", "665841256", "arbusto@gmail.com", ""));

		assertEquals("Todos los campos son obligatorios.",
				vistaRegistro.validar("", "García", "Arbusto", "665841256", "arbusto@gmail.com", "0123"));

		assertNotEquals("Todos los campos son obligatorios.",
				vistaRegistro.validar("Antonio", "García", "Arbusto", "665841256", "arbusto@gmail.com", "0123"));

	}

	@Test
	public void testValidarEmailU() {
		assertEquals("El formato del correo electrónico no es válido.",
				vistaRegistro.validar("Antonio", "García", "Arbusto", "665841256", "arbustogmail.com", "0123"));

		assertEquals("El formato del correo electrónico no es válido.",
				vistaRegistro.validar("Antonio", "García", "Arbusto", "665841256", "arbusto@gmail@com.es", "0123"));

		assertEquals("El formato del correo electrónico no es válido.",
				vistaRegistro.validar("Antonio", "García", "Arbusto", "665841256", "@gmail.com", "0123"));

		assertEquals("El formato del correo electrónico no es válido.",
				vistaRegistro.validar("Antonio", "García", "Arbusto", "665841256", "antonio@gmail", "0123"));

		assertEquals("El formato del correo electrónico no es válido.",
				vistaRegistro.validar("Antonio", "García", "Arbusto", "665841256", "antonio@.com", "0123"));

		assertEquals("El formato del correo electrónico no es válido.",
				vistaRegistro.validar("Antonio", "García", "Arbusto", "665841256", "antonio@gmail.", "0123"));

		assertNull("El método debería retornar null si el email y los campos son válidos",
				vistaRegistro.validar("Antonio", "García", "Arbusto", "665841256", "antonio@gmail.com", "0123"));

	}

	@Test
	public void testValidarNombreU() {
		assertEquals("El nombre solo puede contener letras, espacios o guiones.",
				vistaRegistro.validar("Mª Antonia", "García", "Arbusto", "665841256", "arbusto@gmail.com", "0123"));

		assertEquals("El nombre solo puede contener letras, espacios o guiones.",
				vistaRegistro.validar("Antonia 55", "García", "Arbusto", "665841256", "arbusto@gmail.com", "0123"));

		assertNull("El método debería retornar null porque todos los campos son válidos.",
				vistaRegistro.validar("María Antonia", "García", "Arbusto", "665841256", "arbusto@gmail.com", "0123"));

		assertNull("El método debería retornar null porque todos los campos son válidos.",
				vistaRegistro.validar("María-Antonia", "García", "Arbusto", "665841256", "arbusto@gmail.com", "0123"));

		assertNull("El método debería retornar null porque todos los campos son válidos.",
				vistaRegistro.validar("Iñigo", "García", "Arbusto", "665841256", "arbusto@gmail.com", "0123"));

	}

	@Test
	public void testValidarApellidosU() {
		assertEquals("El apellido solo puede contener letras, espacios o guiones.",
				vistaRegistro.validar("Antonia", "D.García", "Arbusto", "665841256", "arbusto@gmail.com", "0123"));

		assertEquals("El apellido solo puede contener letras, espacios o guiones.",
				vistaRegistro.validar("Antonia", "García 55", "Arbusto", "665841256", "arbusto@gmail.com", "0123"));

		assertNull("El método debería retornar null porque todos los campos son válidos.", vistaRegistro
				.validar("María Antonia", "García López", "Arbusto", "665841256", "arbusto@gmail.com", "0123"));

		assertNull("El método debería retornar null porque todos los campos son válidos.", vistaRegistro
				.validar("María-Antonia", "García-Nieto", "Arbusto", "665841256", "arbusto@gmail.com", "0123"));

		assertNull("El método debería retornar null porque todos los campos son válidos.",
				vistaRegistro.validar("Iñigo", "García Núñez", "Arbusto", "665841256", "arbusto@gmail.com", "0123"));

	}

	@Test
	public void testValidarTelfU() {
		assertEquals("El teléfono solo puede contener números y opcionalmente un + al inicio.",
				vistaRegistro.validar("Antonia", "García", "Arbusto", "ABCED41256", "arbusto@gmail.com", "0123"));

		assertEquals("El teléfono solo puede contener números y opcionalmente un + al inicio.",
				vistaRegistro.validar("Antonia", "García", "Arbusto", "66+5841256", "arbusto@gmail.com", "0123"));

		assertNull("El método debería retornar null porque todos los campos son válidos.", vistaRegistro
				.validar("María Antonia", "García López", "Arbusto", "665841256", "arbusto@gmail.com", "0123"));

		assertNull("El método debería retornar null porque todos los campos son válidos.", vistaRegistro
				.validar("María-Antonia", "García-Nieto", "Arbusto", "+34665841256", "arbusto@gmail.com", "0123"));

	}

	// Controlador
	@Test
	public void testFiltrarEstado() {
		ArrayList<InfoPerfumeConStock> filtrarPorEstado = new ArrayList<>();

		Stock stockSin = new Stock(1, 101, 0, "Pasillo A");
		Stock stockBajo = new Stock(2, 101, 4, "Pasillo B");
		Stock stockOk = new Stock(3, 101, 14, "Pasillo B");

		Perfumes perfume = new Perfumes(96, "Prueba", "Prueba", "Floral", "Huela a rosas", 25, 50, "Unisex");

		InfoPerfumeConStock infoSin = new InfoPerfumeConStock(perfume, stockSin);
		InfoPerfumeConStock infoBajo = new InfoPerfumeConStock(perfume, stockBajo);
		InfoPerfumeConStock infoOk = new InfoPerfumeConStock(perfume, stockOk);

		filtrarPorEstado.add(infoSin);
		filtrarPorEstado.add(infoBajo);
		filtrarPorEstado.add(infoOk);

		ArrayList<InfoPerfumeConStock> resultadoSin = controlador.filtrarPorEstado(filtrarPorEstado, "Sin stock");
		ArrayList<InfoPerfumeConStock> resultadoBajo = controlador.filtrarPorEstado(filtrarPorEstado, "Stock bajo");
		ArrayList<InfoPerfumeConStock> resultadoOk = controlador.filtrarPorEstado(filtrarPorEstado, "Con stock");

		assertEquals(resultadoSin.size(), 1);
		assertEquals(resultadoBajo.size(), 1);
		assertEquals(resultadoOk.size(), 2);

	}

	@Test
	public void calcularEstadoTest() {

		String calcularBajo = controlador.calcularEstado(4);
		assertEquals(calcularBajo, "Stock bajo");

		String limiteBajo2 = controlador.calcularEstado(5);
		assertEquals(limiteBajo2, "OK");

		String calcularOk = controlador.calcularEstado(10);
		assertEquals(calcularOk, "OK");

		String calcularSin = controlador.calcularEstado(0);
		assertEquals(calcularSin, "Sin stock");

	}

	@Test
	public void filasCatalogoTest() {

		ArrayList<InfoPerfumeConStock> filas = new ArrayList<>();

		Stock stockSin = new Stock(1, 101, 0, "Pasillo A");
		Stock stockBajo = new Stock(2, 101, 4, "Pasillo B");
		Stock stockOk = new Stock(3, 101, 14, "Pasillo B");

		Perfumes perfume = new Perfumes(96, "Prueba", "Prueba", "Floral", "Huela a rosas", 25, 50, "Unisex");

		InfoPerfumeConStock infoSin = new InfoPerfumeConStock(perfume, stockSin);
		InfoPerfumeConStock infoBajo = new InfoPerfumeConStock(perfume, stockBajo);
		InfoPerfumeConStock infoOk = new InfoPerfumeConStock(perfume, stockOk);

		filas.add(infoSin);
		filas.add(infoBajo);
		filas.add(infoOk);

		String filasString = "";
		Object[][] tabla = controlador.construirFilasCatalogo(filas);

		for (int i = 0; i < tabla.length; i++) {
			for (int j = 0; j < tabla[0].length; j++) {
				// System.out.print(tabla [i][j]);
			}
			// System.out.println();
		}

		assertEquals(tabla.length, 3);
	}

	@Test
	public void filasPedidoTest() {
		ArrayList<Pedido> filas = new ArrayList<>();

		Usuario usuario = new Usuario(65, "Prueba", "Test", "Calle", "Numero telf 6 ", "prueba@test.com", "78910",
				"empleado");

		Pedido pedido1 = new Pedido(1, usuario, "2026-06-08", "Entregado", "Tarjeta", 45.50, "Calle Falsa 123");
		Pedido pedido2 = new Pedido(2, usuario, "2026-06-09", "Pendiente", "Paypal", 120.00, "Avenida Libertad 45");
		Pedido pedido3 = new Pedido(3, usuario, "2026-06-10", "Cancelado", "Efectivo", 0.00, "Plaza Mayor 9");
	
		filas.add(pedido1);
	    filas.add(pedido2);
	    filas.add(pedido3);
	    
	    String filasString = ""; 
	    Object[][] tabla = controlador.construirFilasPedidos(filas);
	    
	    for (int i = 0; i < tabla.length; i++) {
	        for (int j = 0; j < tabla[0].length; j++) {
	            //System.out.print(tabla [i][j]);
	        }
	        //System.out.println();
	    }
	    
	    assertEquals(tabla.length, 3);
	}

}