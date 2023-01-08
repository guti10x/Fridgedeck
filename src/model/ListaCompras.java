package model;

import java.util.List;

public class ListaCompras {
	public int id_user;
	public List<Compras> lista_compras;
	
	public ListaCompras(int id_user, List<Compras> lista_compras) {
		super();
		this.id_user = id_user;
		this.lista_compras = lista_compras;
	}   
}