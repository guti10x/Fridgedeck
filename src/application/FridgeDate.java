package application;

import java.util.List;

public class FridgeDate {
	public int id_user;
    public int temperatura;
    public int humedad;
    public List<Productos> productos;
    public List<ListaCompras> lista_compras;
    public String estado;
    
	public FridgeDate(int id_user, int temperatura, int humedad, List<Productos> productos, List<ListaCompras> lista_compras,
			String estado) {
		super();
		this.id_user = id_user;
		this.temperatura = temperatura;
		this.humedad = humedad;
		this.productos = productos;
		this.lista_compras = lista_compras;
		this.estado = estado;
	}  
}