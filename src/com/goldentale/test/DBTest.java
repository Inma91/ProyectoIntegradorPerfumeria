package com.goldentale.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.goldentale.model.data.ConstantesTablas;
import com.goldentale.model.db.AccesoDBProp;
import com.goldentale.model.db.CarritoCompra;
import com.goldentale.model.db.InfoPerfumeConStock;
import com.goldentale.model.db.LineaPedido;
import com.goldentale.model.db.Pedido;
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
		//System.out.println(listaPerfumes);
	}
	
	@Test
	public void testSelecTodosInfoPerfumesConStock() {
	    ArrayList<InfoPerfumeConStock> lista = perfumesDAO.getInfoPerfumesConStock();
	    assertTrue("NumInfoPerfumesConStock Correcto", lista.size() > 1);
	    assertNotNull("PrimerElemento no nulo", lista.get(0));
	    //System.out.println(lista);
	}

	@Test
	public void testSelecConStockPorNombre() {
	    ArrayList<InfoPerfumeConStock> lista = perfumesDAO.getInfoTablaPorNombre("Velvet Rose");
	    assertTrue("NumInfoPerfumesConStockPorNombre Correcto", lista.size() > 0);
	    
	}
	
	@Test
	public void testSelecConStockPorNombreNoExiste() {
	    ArrayList<InfoPerfumeConStock> lista = perfumesDAO.getInfoTablaPorNombre("NombreQueNoExiste");
	    assertTrue("Lista vacía si no hay coincidencias", lista.size() == 0);
	}
	
	@Test
	public void testSelecUbicacionPerfume() {
	    ArrayList<InfoPerfumeConStock> lista = perfumesDAO.getInfoTablaPorUbicacion("Estante A");
	    assertTrue("NumInfoPerfumesUbicacion Correcto", lista.size() > 0);
	    
	}
	
	@Test
	public void testSelecAmbosPerfume() {
	    ArrayList<InfoPerfumeConStock> lista = perfumesDAO.getInfoTablaPorAmbos("Velvet Rose", "Estante A");
	    assertTrue("NumInfoPerfumesAmbos Correcto", lista.size() > 0);
	    
	}
	
	@Test
	public void testInsertarPerfume() {
	    Perfumes perfume = new Perfumes(0, "Nombre Test", "Marca Test", "Floral Test", "Descripcion test", 29.99, 50, "Unisex");
	    perfumesDAO.insertar(perfume, 10, "Estante A");
	    
	    ArrayList<Perfumes> lista = perfumesDAO.getAll();
	    assertTrue("Perfume insertado correctamente", lista.size() > 21);
	    
	}
	
	@Test
	public void testBuscarPorNombreYMl() {
	    InfoPerfumeConStock info = perfumesDAO.buscarPorNombreYMl("Velvet Rose", 50);
	    assertNotNull("Perfume encontrado correctamente", info);
	}
	
	@Test
	public void testBuscarPorNombreYMlNoExiste() {
	    InfoPerfumeConStock info = perfumesDAO.buscarPorNombreYMl("PerfumeQueNoExiste", 999);
	    assertNull("Perfume no encontrado", info);
	}
	
	@Test
	public void testObtenerStockDisponible() {
	    int stock = perfumesDAO.obtenerStockDisponible(1);
	    assertTrue("Stock disponible correcto", stock > 0);
	}
	
	@Test
	public void testActualizarPrecioYStock() {
	    perfumesDAO.actualizarPrecioYStock(1, 1, 39.99, 15);
	    
	    InfoPerfumeConStock info = perfumesDAO.buscarPorNombreYMl("Velvet Rose", 50);
	    assertEquals("Precio actualizado correctamente", 39.99, info.getPerfume().getPrecio(), 0.01);
	    assertEquals("Stock actualizado correctamente", 15, info.getStock().getCantidad());
	  
	}
	
	@Test
	public void testGetInfoCatalogoPorCategoria() {
	    ArrayList<InfoPerfumeConStock> lista = perfumesDAO.getInfoCatalogoPorCategoria("Floral");
	    assertTrue("NumInfoCatalogoPorCategoria Correcto", lista.size() > 0);
	    //System.out.println(lista);
	}
	
	@Test
	public void testGetInfoCatalogoPorNombreYCategoria() {
	    ArrayList<InfoPerfumeConStock> lista = perfumesDAO.getInfoCatalogoPorNombreYCategoria("Velvet Rose", "Floral");
	    assertTrue("NumInfoCatalogoPorNombreYCategoria Correcto", lista.size() > 0);
	    //System.out.println(lista);
	}
	
	@Test
	public void testContarStockBajo() {
	    int total = perfumesDAO.contarStockBajo();
	    assertTrue("ContarStockBajo Correcto", total >= 0);
	   
	}
	
	//pedidos
	@Test
	public void testInsertarPedido() {
	    Usuario usuario = new Usuario(65, "Prueba", "Test", "Calle", "Numero telf 6", "prueba@test.com", "78910", "empleado");
	    Perfumes perfume = new Perfumes(16, "Test Pedido", "Marca Test", "Floral Test", "Descripcion test", 50.65, 50, "Unisex");
	    CarritoCompra item = new CarritoCompra(0, usuario, perfume, 1, 50.65);

	    ArrayList<CarritoCompra> carrito = new ArrayList<CarritoCompra>();
	    carrito.add(item);

	    boolean resultado = pedidosDAO.insertarPedido(2, 50.65, "Tarjeta", "Calle Test 1", carrito);
	    assertTrue("Pedido insertado correctamente", resultado);

	}
	
	@Test
	public void testGetPedidosPorUsuario() {
	    ArrayList<Pedido> lista = pedidosDAO.getPedidosPorUsuario(1);
	    assertTrue("PedidosPorUsuario Correcto", lista.size() > 0);
	    
	}
	
	@Test
	public void testGetPedidosPorUsuarioYEstado() {
	    ArrayList<Pedido> lista = pedidosDAO.getPedidosPorUsuarioYEstado(2, "Entregado");
	    assertTrue("PedidosPorUsuarioYEstado Correcto", lista.size() > 0);
	    
	}
	
	@Test
	public void testGetPedidosPorUsuarioYEstadoNoExiste() {
	    ArrayList<Pedido> lista = pedidosDAO.getPedidosPorUsuarioYEstado(2, "Cancelado");
	    assertTrue("Lista vacía si no hay pedidos con ese estado", lista.size() == 0);
	}
	
	@Test
	public void testGetLineasPorPedido() {
	    ArrayList<LineaPedido> lista = pedidosDAO.getLineasPorPedido(11);
	    assertTrue("LineasPorPedido Correcto", lista.size() > 0);
	    
	}
	
	@Test
	public void testGetLineasPorPedidoNoExiste() {
	    ArrayList<LineaPedido> lista = pedidosDAO.getLineasPorPedido(99999);
	    assertTrue("Lista vacía si el pedido no existe", lista.size() == 0);
	}
	
	@Test
	public void testContarPedidosHoy() {
	    int total = pedidosDAO.contarPedidosHoy();
	    assertTrue("ContarPedidosHoy Correcto", total >= 0);
	  
	}
	
	@Test
	public void testContarPedidosPendientes() {
	    int total = pedidosDAO.contarPedidosPendientes();
	    assertTrue("ContarPedidosPendientes Correcto", total >= 0);
	   
	}
	
	
}
