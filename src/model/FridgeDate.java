package model;

import java.util.List;

public class FridgeDate {
	public int id_user;
    public int temperatura;
    public int humedad;
    public String estado;
    
	public FridgeDate(int id_user, int temperatura, int humedad, String estado) {
		super();
		this.id_user = id_user;
		this.temperatura = temperatura;
		this.humedad = humedad;
		this.estado = estado;
	}  
}