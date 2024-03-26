package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JTable;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import java.awt.Panel;
import java.awt.FlowLayout;
import java.awt.ScrollPane;
import java.awt.Scrollbar;
import java.awt.Point;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;

public class Vista {

	private JFrame frmSistemaDeInventario;
	private JTable TablaProductos;
	private JTextField TextCodigo;
	private JTextField TextNombre;
	private JTextField TextCantidad;
	private JTextField TextPrecio;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Vista window = new Vista();
					window.frmSistemaDeInventario.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Vista() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSistemaDeInventario = new JFrame();
		frmSistemaDeInventario.setTitle("Sistema de inventario");
		frmSistemaDeInventario.setBounds(100, 100, 916, 555);
		frmSistemaDeInventario.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmSistemaDeInventario.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Archivo");
		menuBar.add(mnNewMenu);
		
		JMenu mnNewMenu_1 = new JMenu("Ayuda");
		menuBar.add(mnNewMenu_1);
		frmSistemaDeInventario.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 298, 850, 187);
		frmSistemaDeInventario.getContentPane().add(scrollPane);
		
		TablaProductos = new JTable();
		scrollPane.setViewportView(TablaProductos);
		TablaProductos.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Codigo", "Nombre", "Cantidad", "Precio"
			}
		));
		
		JLabel lblNewLabel = new JLabel("Codigo");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(25, 23, 92, 25);
		frmSistemaDeInventario.getContentPane().add(lblNewLabel);
		
		TextCodigo = new JTextField();
		TextCodigo.setBounds(21, 59, 96, 20);
		frmSistemaDeInventario.getContentPane().add(TextCodigo);
		TextCodigo.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Nombre");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(187, 28, 96, 14);
		frmSistemaDeInventario.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Cantidad");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1_1.setBounds(344, 30, 96, 14);
		frmSistemaDeInventario.getContentPane().add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Precio");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1_2.setBounds(516, 30, 96, 14);
		frmSistemaDeInventario.getContentPane().add(lblNewLabel_1_2);
		
		TextNombre = new JTextField();
		TextNombre.setColumns(10);
		TextNombre.setBounds(187, 59, 96, 20);
		frmSistemaDeInventario.getContentPane().add(TextNombre);
		
		TextCantidad = new JTextField();
		TextCantidad.setColumns(10);
		TextCantidad.setBounds(344, 59, 96, 20);
		frmSistemaDeInventario.getContentPane().add(TextCantidad);
		
		TextPrecio = new JTextField();
		TextPrecio.setColumns(10);
		TextPrecio.setBounds(516, 59, 96, 20);
		frmSistemaDeInventario.getContentPane().add(TextPrecio);
		
		JRadioButton ButtonExento = new JRadioButton("Exento");
		ButtonExento.setFont(new Font("Tahoma", Font.PLAIN, 13));
		ButtonExento.setBounds(21, 131, 111, 23);
		frmSistemaDeInventario.getContentPane().add(ButtonExento);
		
		JLabel lblDescripcion = new JLabel("Descripcion");
		lblDescripcion.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDescripcion.setBounds(21, 186, 92, 25);
		frmSistemaDeInventario.getContentPane().add(lblDescripcion);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(25, 222, 867, 68);
		frmSistemaDeInventario.getContentPane().add(scrollPane_1);
		
		JTextArea TextDescripcion = new JTextArea();
		scrollPane_1.setViewportView(TextDescripcion);
	}
}
