package application;

import java.util.List;

public class ListaProductos {
	public int id_user;
	public List<Productos> lista_productos;
	
	public ListaProductos(int id_user, List<Productos> lista_productos) {
		super();
		this.id_user = id_user;
		this.lista_productos = lista_productos;
	}   
}