package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
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
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Point;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.AbstractButton;
import javax.swing.JButton;

import modelo.Productos;
import controlador.Controlador;
import javax.swing.JMenuItem;

public class Vista {

	private JFrame frmSistemaDeInventario;
	private JTable TablaProductos;
	private JTextField TextCodigo;
	private JTextField TextNombre;
	private JTextField TextCantidad;
	private JTextField TextPrecio;
	private JTextArea TextDescripcion;
	private JRadioButton ButtonExento;
	private JButton ButtonLimpiar;
	private JButton BotonCrear;
	private JButton BotonActualizar;
	private JMenuItem TasaCambio;
	private JButton Dolares;
	private JButton Bolivares;
	private JMenuItem AcercaDe;
	private JButton ButtonGuardar;
	private JMenuItem GuardarArchivo;
	private Controlador controlador;
	private JMenuItem AbrirArchivo;
	
	public String getCodigo() {
        return TextCodigo.getText();
    }

    public String getNombre() {
        return TextNombre.getText();
    }

    public int getCantidad() {
        return Integer.parseInt(TextCantidad.getText());
    }

    public double getPrecio() {
        return Double.parseDouble(TextPrecio.getText());
    }

    public boolean isExento() {
        return ButtonExento.isSelected();
    }

    public String getDescripcion() {
    	return TextDescripcion.getText();
    }
    
    public void addCrearProductoListener(ActionListener listener) {
        BotonCrear.addActionListener(listener);
    }

    public void addProductoToTable(Object[] producto) {
        DefaultTableModel model = (DefaultTableModel) TablaProductos.getModel();
        model.addRow(producto);
    }
    
    public void updateProductosTable(Productos[] productos) {
        DefaultTableModel model = (DefaultTableModel) TablaProductos.getModel();
        model.setRowCount(0);
        for (Productos producto : productos) {
            model.addRow(new Object[]{producto.getCodigo(), producto.getNombre(), producto.getCantidad(), producto.getPrecio()});
            }
        }
    

        
	/**
	 * Launch the application.
	 */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Productos model = new Productos();
                    Vista window = new Vista(model);
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
	public Vista(Productos model) {
        initialize();
        controlador = new Controlador(this, model);
        BotonCrear.addActionListener(controlador);
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
		
		JMenu MenuArchivo = new JMenu("Archivo");
		menuBar.add(MenuArchivo);
		
		GuardarArchivo = new JMenuItem("Guardar Archivo");
		MenuArchivo.add(GuardarArchivo);
		
		AbrirArchivo = new JMenuItem("Abrir Archivo");
		MenuArchivo.add(AbrirArchivo);
		
		JMenu MenuAyuda = new JMenu("Ayuda");
		menuBar.add(MenuAyuda);
		
		TasaCambio = new JMenuItem("Tasa de cambio");
		MenuAyuda.add(TasaCambio);
		
		AcercaDe = new JMenuItem("Acerca de");
		MenuAyuda.add(AcercaDe);
		frmSistemaDeInventario.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 298, 850, 187);
		frmSistemaDeInventario.getContentPane().add(scrollPane);
		
		TablaProductos = new JTable();
		TablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		TablaProductos.setEnabled(true);
		scrollPane.setViewportView(TablaProductos);
		
		// Aqui creo una tabla personalizada para que las celdas NO sean editables
		DefaultTableModel model = new DefaultTableModel() {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        
		        return false;
		    }
		};
		
		model.addColumn("Codigo");
		model.addColumn("Nombre");
		model.addColumn("Cantidad");
		model.addColumn("Precio");

		for (Productos producto : Productos.getListaProductos()) {
		    model.addRow(new Object[] { producto.getCodigo(), producto.getNombre(), producto.getCantidad(), producto.getPrecio() });
		}

		TablaProductos.setModel(model);
		
		
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
		
		JLabel lblNewLabel_1_2 = new JLabel("Precio ($)");
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
		
		ButtonExento = new JRadioButton("Exento");
		ButtonExento.setFont(new Font("Tahoma", Font.PLAIN, 13));
		ButtonExento.setBounds(657, 57, 111, 23);
		frmSistemaDeInventario.getContentPane().add(ButtonExento);
		
		JLabel lblDescripcion = new JLabel("Descripcion");
		lblDescripcion.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDescripcion.setBounds(25, 84, 92, 25);
		frmSistemaDeInventario.getContentPane().add(lblDescripcion);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(25, 109, 867, 68);
		frmSistemaDeInventario.getContentPane().add(scrollPane_1);
		
		TextDescripcion = new JTextArea();
		scrollPane_1.setViewportView(TextDescripcion);
		
		BotonCrear = new JButton("Crear Producto");
		BotonCrear.setFont(new Font("Tahoma", Font.PLAIN, 13));
		BotonCrear.setBounds(25, 188, 850, 32);
		frmSistemaDeInventario.getContentPane().add(BotonCrear);
		
		ButtonLimpiar = new JButton("Limpiar");
		ButtonLimpiar.setBounds(803, 11, 89, 23);
		frmSistemaDeInventario.getContentPane().add(ButtonLimpiar);
		
		BotonActualizar = new JButton("Actualizar Producto");
		BotonActualizar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		BotonActualizar.setBounds(25, 231, 850, 32);
		frmSistemaDeInventario.getContentPane().add(BotonActualizar);
		
		Dolares = new JButton("$");
		Dolares.setBounds(733, 274, 89, 23);
		frmSistemaDeInventario.getContentPane().add(Dolares);
		
		Bolivares = new JButton("Bs");
		Bolivares.setBounds(634, 274, 89, 23);
		frmSistemaDeInventario.getContentPane().add(Bolivares);
		
		ButtonGuardar = new JButton("Guardar");
		ButtonGuardar.setBounds(803, 41, 89, 23);
		frmSistemaDeInventario.getContentPane().add(ButtonGuardar);
		
		
		
	}
	
	public JButton getButtonLimpiar() {
	    return ButtonLimpiar;
	}
	
	public JButton getBotonCrear() {
		return BotonCrear;
	}
	
	public JButton getBotonActualizar() {
		return BotonActualizar;
	}
	
	public void MostrarError(String Mensaje) {
        JOptionPane.showMessageDialog(null, Mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
	
	public void LimpiarCampos() {
	    TextCodigo.setText("");
	    TextNombre.setText("");
	    TextCantidad.setText("");
	    TextPrecio.setText("");
	    ButtonExento.setSelected(false);
	    TextDescripcion.setText("");
	}
	
	public void setCodigo(String codigo) {
	    TextCodigo.setText(codigo);
	}

	public void setNombre(String nombre) {
	    TextNombre.setText(nombre);
	}

	public void setCantidad(int cantidad) {
	    TextCantidad.setText(String.valueOf(cantidad));
	}

	public void setPrecio(double precio) {
	    TextPrecio.setText(String.valueOf(precio));
	}

	public void setExento(boolean exento) {
	    ButtonExento.setSelected(exento);
	}

	public void setDescripcion(String descripcion) {
	    TextDescripcion.setText(descripcion);
	}
	
	public JTable getTablaProductos() {
	    return TablaProductos;
	}
	
	public int getFilaSeleccionada() {
	    return TablaProductos.getSelectedRow();
	}
	
	public void updateProductoInTable(Productos producto, int fila) {
	    if (fila != -1) {
	        TablaProductos.setValueAt(producto.getCodigo(), fila, 0);
	        TablaProductos.setValueAt(producto.getNombre(), fila, 1);
	        TablaProductos.setValueAt(producto.getCantidad(), fila, 2);
	        TablaProductos.setValueAt(producto.getPrecio(), fila, 3);
	    }
	}
	
	public void deseleccionarFila() {
	    TablaProductos.clearSelection();
	}

	public JMenuItem getTasaCambio() {
		return TasaCambio;
	}
	
	public JMenuItem getAcercaDe() {
		return AcercaDe;
	}
	
	public JButton getBolivares() {
		return Bolivares;
	}
	
	public JButton getDolares() {
		return Dolares;
	}
	
	public JButton getButtonGuardar() {
		return ButtonGuardar;
	}
	
	public JMenuItem getGuardarArchivo() {
		return GuardarArchivo;
	}
	
	public JMenuItem getAbrirArchivo() {
		return AbrirArchivo;
	}
	
    public void clearTablaProductos() {
        DefaultTableModel dtm = (DefaultTableModel) TablaProductos.getModel();
        dtm.setRowCount(0);
    }
}
