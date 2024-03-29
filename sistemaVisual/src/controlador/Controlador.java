package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

                // Mostrar un cuadro de diálogo con la tasa de cambio actual y preguntar si se quiere modificar
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

                // Obtener la tasa de cambio
                double tasaDeCambio = 0; // TODO: reemplazar con la tasa de cambio actual

                // Obtener la lista de productos
                List<Productos> listaProductos = model.getListaProductos();

                // Iterar sobre cada producto
                for (Productos producto : listaProductos) {
                    // Actualizar el precio del producto según la tasa de cambio
                    double nuevoPrecio = producto.getPrecio() * TasaCambio;
                    producto.setPrecioBs(nuevoPrecio);
                }

                // Actualizar la tabla
                DefaultTableModel tableModel = (DefaultTableModel) view.getTablaProductos().getModel();
                tableModel.setRowCount(0); // Limpiar la tabla
                for (Productos producto : listaProductos) {
                    // Añadir cada producto a la tabla
                    Object[] rowData = new Object[] {
                        producto.getCodigo(),
                        producto.getNombre(),
                        producto.getCantidad(),
                        "Bs " + producto.getPrecioBs(), // Mostrar el precio en Bs
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

                List<Productos> listaProductos = model.getListaProductos();

                // Actualizar la tabla
                DefaultTableModel tableModel = (DefaultTableModel) view.getTablaProductos().getModel();
                tableModel.setRowCount(0); // Limpiar la tabla
                for (Productos producto : listaProductos) {
                    // Añadir cada producto a la tabla
                    Object[] rowData = new Object[] {
                        producto.getCodigo(),
                        producto.getNombre(),
                        producto.getCantidad(),
                        "$ " + producto.getPrecio(), // Mostrar el precio en dólares
                        producto.getExento(),
                        producto.getDescripcion()
                    };
                    tableModel.addRow(rowData);
                }

                // Marcar que ya hemos convertido a dólares
                isConvertedToBs = false;
            }
        });
        
     // Agregar MouseListener para la funcionalidad de doble clic
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
        int cantidad = view.getCantidad();
        double precio = view.getPrecio();
        boolean exento = view.isExento();
        String descripcion = view.getDescripcion();

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
        
        // Crear un nuevo producto con los valores obtenidos de la vista
        Productos NuevoProducto = new Productos(codigo, nombre, cantidad, precio, exento, descripcion);

        // Agregar el símbolo de la moneda al precio antes de agregarlo a la tabla
        Object[] rowData = new Object[] {
            NuevoProducto.getCodigo(),
            NuevoProducto.getNombre(),
            NuevoProducto.getCantidad(),
            (isConvertedToBs ? "Bs " : "$ ") + (isConvertedToBs ? NuevoProducto.getPrecioBs() : NuevoProducto.getPrecio()), // Mostrar el precio en Bs o en dólares dependiendo del estado
            NuevoProducto.getExento(),
            NuevoProducto.getDescripcion()
        };
        view.addProductoToTable(rowData);

        // Aquí puedes agregar el nuevo producto a tu modelo
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

         // Actualizar la tabla
            DefaultTableModel tableModel = (DefaultTableModel) view.getTablaProductos().getModel();
            tableModel.setRowCount(0); // Limpiar la tabla
            for (Productos productoActualizado : listaProductos) {
                // Añadir cada producto a la tabla
                Object[] rowData = new Object[] {
                    productoActualizado.getCodigo(),
                    productoActualizado.getNombre(),
                    productoActualizado.getCantidad(),
                    (isConvertedToBs ? "Bs " : "$ ") + (isConvertedToBs ? productoActualizado.getPrecioBs() : productoActualizado.getPrecio()), // Mostrar el precio en Bs o en dólares dependiendo del estado
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getBotonCrear()) {
            crearProducto();
        }
    }
}