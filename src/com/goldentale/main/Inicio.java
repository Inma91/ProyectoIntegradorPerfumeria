package com.goldentale.main;

import java.awt.EventQueue;

import com.goldentale.controlador.Controlador;
import com.goldentale.vistaEmpleado.LoginVista;
import com.goldentale.vistaEmpleado.VistaPrincipal;

public class Inicio {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				//Vista de Bienvenida a la aplicación
				VistaPrincipal vp = new VistaPrincipal(); 
				
				//Vista con el login de cliente/empleado
				LoginVista lgv = new LoginVista(); 
				
				//Vista del empleado con las opciones que puede manejar
				//EmpleadoDashboardVista empdv = new EmpleadoDashboardVista();
				
				//Vista para que el empleado añada un nuevo perfume
				//AniadirPerfumeVista apv = new AniadirPerfumeVista(); 
				
				//Vista para que el empleado modifique los datos de algún perfume
				//ModificarPerfumeVista mpv = new ModificarPerfumeVista();
				
				//Vista para que el empleado consulte el número de existencias
				//StockVista sv = new StockVista();
				
				//Vista para que el empleado gestione los pedidos/vea el estado de los pedidos
				//GestionarPedidosVista gpv = new GestionarPedidosVista();

				//Vista del cliente con las opciones que puede manejar
				//ClienteDashboardVista clidv = new ClienteDashboardVista();
				
				//Controlador c = new Controlador (vp, lgv, empdv, apv, mpv, sv, gvp);
				Controlador c = new Controlador (vp, lgv);
				
				vp.setControlador(c); 
				lgv.setControlador(c); 
				//empdv.setControlador(c);
				//apv.setControlador(c); 
				//mpv.setControlador(c);
				//sv.setControlador(c);
				//gpv.setControlador(c);
				//clidv.setControlador(c);
				
				vp.hacerVisible(); 
				
			}
		});

	}

}
