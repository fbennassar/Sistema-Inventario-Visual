package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import modelo.Productos;
import vista.Vista;

public class Controlador implements ActionListener {

    private Vista view;
    private Productos model;
    private static Double TasaCambio = 38.0;
    private boolean isConvertedToBs = false;
    
    private List<Productos> TablaTemporal = new ArrayList<>();

    public Controlador(Vista view, Productos model) {
        this.view = view;
        this.model = model;
        
        this.view.getButtonLimpiar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.LimpiarCampos();
            }
        });
        
        this.view.getTasaCambio().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // Muestra un cuadro de diálogo con la tasa de cambio actual y preguntar si se quiere modificar
                Object[] options = {"Modificar", "Cancelar"};
                int option = JOptionPane.showOptionDialog(null, "La tasa de cambio actual es: " + TasaCambio + ". ¿Desea modificarla?", "Tasa de cambio", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

                if (option == 0) { 
                    JTextField tasaDeCambioField = new JTextField();
                    Object[] message = {
                        "Nueva tasa de cambio:", tasaDeCambioField
                    };

                    int option2 = JOptionPane.showConfirmDialog(null, message, "Cambiar tasa de cambio", JOptionPane.OK_CANCEL_OPTION);
                    if (option2 == JOptionPane.OK_OPTION) {
                        
                        TasaCambio = Double.parseDouble(tasaDeCambioField.getText());
                    }
                }
            }
        });
        
        

        this.view.getBolivares().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Si ya hemos convertido a Bs, no hacemos nada
                if (isConvertedToBs) {
                    return;
                }

                List<Productos> ListaProductos = model.getListaProductos();

                for (Productos producto : ListaProductos) {
                    double nuevoPrecio = producto.getPrecio() * TasaCambio;
                    producto.setPrecioBs(nuevoPrecio);
                }

                // Actualizar la tabla
                DefaultTableModel tableModel = (DefaultTableModel) view.getTablaProductos().getModel();
                tableModel.setRowCount(0); // Limpiar la tabla
                for (Productos producto : ListaProductos) {
                    // Añadir cada producto a la tabla
                    Object[] rowData = new Object[] {
                        producto.getCodigo(),
                        producto.getNombre(),
                        producto.getCantidad(),
                        "Bs " + producto.getPrecioBs(),
                        producto.getExento(),
                        producto.getDescripcion()
                    };
                    tableModel.addRow(rowData);
                }

                // Marcar que ya hemos convertido a Bs
                isConvertedToBs = true;
            }
        });
        
        this.view.getDolares().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Si ya hemos convertido a dólares, no hacemos nada
                if (!isConvertedToBs) {
                    return;
                }

                List<Productos> ListaProductos = model.getListaProductos();

                
                DefaultTableModel tableModel = (DefaultTableModel) view.getTablaProductos().getModel();
                tableModel.setRowCount(0); 
                for (Productos producto : ListaProductos) {
                    Object[] rowData = new Object[] {
                        producto.getCodigo(),
                        producto.getNombre(),
                        producto.getCantidad(),
                        "$ " + producto.getPrecio(),
                        producto.getExento(),
                        producto.getDescripcion()
                    };
                    tableModel.addRow(rowData);
                }

                // Marcar que ya hemos convertido a dólares
                isConvertedToBs = false;
            }
        });
        
        this.view.getAcercaDe().addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		JOptionPane.showMessageDialog(null, "Nombre: Francisco Bennassar" + "\nCédula: 30.474.123");

        	}
        });
        
        this.view.getButtonGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GuardarTabla();
            }
        });
        
        this.view.getGuardarArchivo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarArchivo();
            }
        });
        
        this.view.getAbrirArchivo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LeerArchivo();
            }
        });
        
     // MouseListener lo use para que detecte el doble clic
     		view.getTablaProductos().addMouseListener(new MouseAdapter() {
     		    public void mouseClicked(MouseEvent e) {
     		        if (e.getClickCount() == 2) {
     		            int filaSeleccionada = view.getTablaProductos().getSelectedRow();
     		            if (filaSeleccionada != -1) {
     		                Productos productoSeleccionado = Productos.getListaProductos().get(filaSeleccionada);
     		                view.setCodigo(productoSeleccionado.getCodigo());
     		                view.setNombre(productoSeleccionado.getNombre());
     		                view.setCantidad(productoSeleccionado.getCantidad());
     		                view.setPrecio(productoSeleccionado.getPrecio());
     		                view.setExento(productoSeleccionado.getExento());
     		                view.setDescripcion(productoSeleccionado.getDescripcion());
     		            }
     		        }
     		    }
     		});
     		
     		view.getBotonActualizar().addActionListener(new ActionListener() {
     		    @Override
     		    public void actionPerformed(ActionEvent e) {
     		        actualizarProducto();
     		    }
     		});
     		
     		
    }

    public void crearProducto() {
        String codigo = view.getCodigo();
        String nombre = view.getNombre();
        Integer cantidad = view.getCantidad();
        Double precio = view.getPrecio();
        boolean exento = view.isExento();
        String descripcion = view.getDescripcion();

        if (codigo.isEmpty() || nombre.isEmpty() || descripcion.isEmpty() || precio == null || cantidad == null) {
        	view.MostrarError("Todos los campos deben estar llenos");
        	return;
        }
        
        if(cantidad < 0) {
            view.MostrarError("La cantidad no puede ser negativa");
            return;
        }
        
        for(Productos producto : model.getListaProductos()) {
            if(producto.getCodigo().equals(codigo)) {
                view.MostrarError("Este codigo ya existe");
                return;
            }
        }
        
        if(precio <= 0) {
            view.MostrarError("El precio debe ser mayor a $0");
            return;
        }
        
        
        Productos NuevoProducto = new Productos(codigo, nombre, cantidad, precio, exento, descripcion);

        Object[] rowData = new Object[] {
            NuevoProducto.getCodigo(),
            NuevoProducto.getNombre(),
            NuevoProducto.getCantidad(),
            (isConvertedToBs ? "Bs " : "$ ") + (isConvertedToBs ? NuevoProducto.getPrecioBs() : NuevoProducto.getPrecio()),
            NuevoProducto.getExento(),
            NuevoProducto.getDescripcion()
        };
        view.addProductoToTable(rowData);
        
        model.NuevoProducto(NuevoProducto);
    }
    
    public void actualizarProducto() {
        int FilaSeleccionada = view.getFilaSeleccionada();

        if(FilaSeleccionada != -1) {
            String Nombre = view.getNombre();
            int Cantidad = view.getCantidad();
            double Precio = view.getPrecio();
            boolean Exento = view.isExento();
            String Codigo = view.getCodigo();
            String Descripcion = view.getDescripcion();
            
            if(Cantidad < 0) {
                view.MostrarError("La cantidad no puede ser negativa");
                return;
            }
            
            List<Productos> listaProductos = model.getListaProductos();
            for(int i = 0; i < listaProductos.size(); i++) {
                if(i != FilaSeleccionada && listaProductos.get(i).getCodigo().equals(Codigo)) {
                    view.MostrarError("Este codigo ya existe");
                    return;
                }
            }
            
            if(Precio <= 0) {
                view.MostrarError("El precio debe ser mayor a $0");
                return;
            }
            
            Productos producto = listaProductos.get(FilaSeleccionada);

            // Verifica si el precio ha cambiado antes de aplicar el IVA
            if(Precio != producto.getPrecio() && !Exento) {
                Double Resta = (Precio * 0.16); 
                Precio = (Precio + Resta);
            }
            
            producto.setCodigo(Codigo);
            producto.setNombre(Nombre);
            producto.setCantidad(Cantidad);
            producto.setPrecio(Precio);
            producto.setExento(Exento);
            producto.setDescripcion(Descripcion);

            DefaultTableModel tableModel = (DefaultTableModel) view.getTablaProductos().getModel();
            tableModel.setRowCount(0);
            for (Productos productoActualizado : listaProductos) {
                Object[] rowData = new Object[] {
                    productoActualizado.getCodigo(),
                    productoActualizado.getNombre(),
                    productoActualizado.getCantidad(),
                    (isConvertedToBs ? "Bs " : "$ ") + (isConvertedToBs ? productoActualizado.getPrecioBs() : productoActualizado.getPrecio()),
                    productoActualizado.getExento(),
                    productoActualizado.getDescripcion()
                };
                tableModel.addRow(rowData);
            }
            
            view.deseleccionarFila();
        } else {
            view.MostrarError("No se seleccionó ningún producto");
        }
    }
    
    public void GuardarTabla() {
        TablaTemporal.clear();  // Limpia la tabla temporal/borrador

        for (Productos producto : model.getListaProductos()) {
            TablaTemporal.add(producto);
        }
        
        JOptionPane.showMessageDialog(null, "Guardado exitoso");
    }
    
    public void guardarArchivo() {
        String NombreArchivo = JOptionPane.showInputDialog("Ingrese el nombre de archivo");

        // Salir si el nombre del archivo es null o está vacío
        if (NombreArchivo == null || NombreArchivo.trim().isEmpty()) {
            return;
        }

        try (PrintWriter out = new PrintWriter(new FileWriter("C:\\Users\\franb\\OneDrive\\Desktop\\" + NombreArchivo + ".txt"))) {
            for (Productos producto : TablaTemporal) {
                out.println(producto.getCodigo() + "," + producto.getNombre() + "," + producto.getCantidad() + "," + producto.getPrecio() + "," + producto.getExento() + "," + producto.getDescripcion());
            }

            JOptionPane.showMessageDialog(null, "Archivo creado exitosamente");
        } catch (IOException e) {
            e.printStackTrace();
            view.MostrarError("Se produjo un error");
        }
    }
    
    public void LeerArchivo() {
    	
    	String NombreArchivo = JOptionPane.showInputDialog("Ingrese el nombre de archivo");
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\franb\\OneDrive\\Desktop\\" + NombreArchivo + ".txt"))) {
            String line;
            model.clearProductos(); 
            view.clearTablaProductos();

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String codigo = parts[0];
                String nombre = parts[1];
                int cantidad = Integer.parseInt(parts[2]);
                double precio = Double.parseDouble(parts[3]);
                boolean exento = Boolean.parseBoolean(parts[4]);
                String descripcion = parts[5];

                Productos producto = new Productos(codigo, nombre, cantidad, precio, exento, descripcion, false);
                model.NuevoProducto(producto); 

                view.addProductoToTable(new Object[]{producto.getCodigo(), producto.getNombre(), producto.getCantidad(), producto.getPrecio(), producto.getExento(), producto.getDescripcion()});
            }
        } catch (IOException e) {
            e.printStackTrace();
            view.MostrarError("Archivo no encontrado");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getBotonCrear()) {
            crearProducto();
        }
    }
}