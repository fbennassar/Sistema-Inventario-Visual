package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;

import modelo.Productos;
import vista.Vista;

public class Controlador implements ActionListener {

    private Vista view;
    private Productos model;

    public Controlador(Vista view, Productos model) {
        this.view = view;
        this.model = model;

        
        this.view.getButtonLimpiar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.LimpiarCampos();
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

        view.addProductoToTable(NuevoProducto);
        // Aquí puedes agregar el nuevo producto a tu modelo
        model.NuevoProducto(NuevoProducto);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getBotonCrear()) {
            crearProducto();
        }
    }
}