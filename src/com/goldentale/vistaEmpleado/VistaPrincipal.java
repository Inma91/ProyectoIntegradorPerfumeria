package com.goldentale.vistaEmpleado;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import com.goldentale.controlador.Controlador;

	public class VistaPrincipal extends JFrame {

		public static final String INGRESAR_EN_APLICACION = "Ingresar en la aplicación";
		
		public static final int ANCHO = 800; 
		public static final int ALTO = 600;

		private int insetsR;

		private int insetsL;

		private int insetsB;

		private int insetsT;

		private JScrollPane scrpContenedor;

		private JLabel lblBienvenida;

		private JButton btnIngresar;
		
		public VistaPrincipal() {
			configurarVentana();
			
			crearComponentes();
			
		}
		
		public void configurarVentana() {
			setTitle("** G O L D E N_T A L E **"); 
			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			
			setSize(ANCHO, ALTO); 
			
			insetsR = this.getInsets().right; 
			insetsL = this.getInsets().left; 
			insetsT = this.getInsets().top;
			insetsB = this.getInsets().bottom;
			
			centrarVentana(); 


		}
		
		public void centrarVentana() {
			Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension ventana = new Dimension(ANCHO, ALTO); 
			setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
		}
		
		public void crearComponentes() {
			getContentPane().setLayout(null);
			scrpContenedor = new JScrollPane(); 
			scrpContenedor.setBounds(0, 0, ANCHO, ALTO);
			getContentPane().add(scrpContenedor);	
			
			//PANEL PARA MENSAJE Y LOGO Y BOTÓN INGRESAR
			JPanel bienvenida = new JPanel();
			bienvenida.setLayout(null);
			
			lblBienvenida = new JLabel("Bienvenido a GoldenTale"); 
			lblBienvenida.setFont(new Font("Tahoma", Font.BOLD, 20));
		    lblBienvenida.setBounds(250, 220, 300, 30);
		    bienvenida.add(lblBienvenida);
			
		    btnIngresar = new JButton (INGRESAR_EN_APLICACION);
			btnIngresar.setBounds(300, 270, 200, 30);
			bienvenida.add(btnIngresar);
			
			scrpContenedor.setViewportView(bienvenida); 
		}


		public void setControlador(Controlador c) {
			btnIngresar.addActionListener(c);

		}


		public void cargarPanel(JPanel panel) {
			scrpContenedor.setViewportView(panel);

		}


		public void hacerVisible() {
			setVisible(true);

		}
	}
