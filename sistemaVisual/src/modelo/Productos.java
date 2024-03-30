package modelo;

import java.util.ArrayList;

public class Productos {

	private String Codigo;
	private String Nombre;
	private Integer Cantidad;
	private Double Precio;
	private Boolean Exento;
	private String Descripcion;
	private Double PrecioBs;
	
	private static ArrayList<Productos> ListaProductos = new ArrayList<Productos>();
	
	public String getCodigo() {
		return Codigo;
	}
	public void setCodigo(String codigo) {
		Codigo = codigo;
	}
	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	public Integer getCantidad() {
		return Cantidad;
	}
	public void setCantidad(Integer cantidad) {
		Cantidad = cantidad;
	}
	public Double getPrecio() {
		return Precio;
	}
	public void setPrecio(Double precio) {
		Precio = precio;
	}
	public Boolean getExento() {
		return Exento;
	}
	public void setExento(Boolean exento) {
		Exento = exento;
	}
	
	public String getDescripcion() {
		return Descripcion;
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}
	
	public static ArrayList<Productos> getListaProductos() {
		return ListaProductos;
	}
	public static void setListaProductos(ArrayList<Productos> listaProductos) {
		ListaProductos = listaProductos;
	}
	
	/**
	 * Constructor vacio
	 */
	public Productos() {
		
	}
	
	/**
	 * Constructor de productos
	 * @param codigo
	 * @param nombre
	 * @param cantidad
	 * @param precio
	 * @param exento
	 * @param descripcion
	 */
	public Productos(String codigo, String nombre, Integer cantidad, Double precio, Boolean exento, String descripcion) {
		
		Codigo = codigo;
		Nombre = nombre;
		Cantidad = cantidad;
		Precio = precio;
		Exento = exento;
		setDescripcion(descripcion);
		PrecioBs = precio;
		
		if(!Exento) {
			
			Double Resta = (Precio * 0.16); 
			
			Precio = (Precio + Resta);
		}
		
	}
	
	/**
	 * Coloca un nuevo producto en el arraylist
	 * @param producto
	 */
	public void NuevoProducto(Productos producto) {
		
		getListaProductos().add(producto);
	}
	public Double getPrecioBs() {
		return PrecioBs;
	}
	public void setPrecioBs(Double precioBs) {
		PrecioBs = precioBs;
	}
	
	@Override
	public String toString() {
	    return getCodigo() + "," + getNombre() + "," + getCantidad() + "," + getPrecio() + "," + getExento() + "," + getDescripcion();
	}
	
}
