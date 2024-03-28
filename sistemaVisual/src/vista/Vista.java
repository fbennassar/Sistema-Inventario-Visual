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
import javax.swing.JButton;

import modelo.Productos;
import controlador.Controlador;

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
	private Controlador controlador;
	
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

    public void addProductoToTable(Productos producto) {
        DefaultTableModel model = (DefaultTableModel) TablaProductos.getModel();
        model.addRow(new Object[]{producto.getCodigo(), producto.getNombre(), producto.getCantidad(), producto.getPrecio(), producto.getExento()});
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
		
		JMenu mnNewMenu = new JMenu("Archivo");
		menuBar.add(mnNewMenu);
		
		JMenu mnNewMenu_1 = new JMenu("Ayuda");
		menuBar.add(mnNewMenu_1);
		frmSistemaDeInventario.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 298, 850, 187);
		frmSistemaDeInventario.getContentPane().add(scrollPane);
		
		TablaProductos = new JTable();
		TablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		TablaProductos.setEnabled(true);
		scrollPane.setViewportView(TablaProductos);
		// Crear un modelo de tabla personalizado
		DefaultTableModel model = new DefaultTableModel() {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        // Todas las celdas son no editables
		        return false;
		    }
		};

		// Añade las columnas a tu modelo
		model.addColumn("Codigo");
		model.addColumn("Nombre");
		model.addColumn("Cantidad");
		model.addColumn("Precio");

		// Añade los datos a tu modelo
		for (Productos producto : Productos.getListaProductos()) {
		    model.addRow(new Object[] { producto.getCodigo(), producto.getNombre(), producto.getCantidad(), producto.getPrecio() });
		}

		// Aplica el modelo a tu tabla
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
		
		ButtonExento = new JRadioButton("Exento");
		ButtonExento.setFont(new Font("Tahoma", Font.PLAIN, 13));
		ButtonExento.setBounds(657, 57, 111, 23);
		frmSistemaDeInventario.getContentPane().add(ButtonExento);
		
		JLabel lblDescripcion = new JLabel("Descripcion");
		lblDescripcion.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDescripcion.setBounds(25, 100, 92, 25);
		frmSistemaDeInventario.getContentPane().add(lblDescripcion);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(25, 135, 867, 68);
		frmSistemaDeInventario.getContentPane().add(scrollPane_1);
		
		TextDescripcion = new JTextArea();
		scrollPane_1.setViewportView(TextDescripcion);
		
		BotonCrear = new JButton("Crear Producto");
		BotonCrear.setFont(new Font("Tahoma", Font.PLAIN, 13));
		BotonCrear.setBounds(25, 214, 850, 32);
		frmSistemaDeInventario.getContentPane().add(BotonCrear);
		
		ButtonLimpiar = new JButton("Limpiar");
		ButtonLimpiar.setBounds(803, 11, 89, 23);
		frmSistemaDeInventario.getContentPane().add(ButtonLimpiar);
		
		BotonActualizar = new JButton("Actualizar Producto");
		BotonActualizar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		BotonActualizar.setBounds(25, 255, 850, 32);
		frmSistemaDeInventario.getContentPane().add(BotonActualizar);
		
		
		
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
	        // Actualiza también cualquier otra columna que necesites
	    }
	}
	
	public void deseleccionarFila() {
	    TablaProductos.clearSelection();
	}
}
