package application;

public class Usuario {
	
    private final int id;
    private final String username;
    private final String email;
    private final String password;
    private final String name_surname;
    private final String fridge_adress;
    
    public Usuario(int id, String username, String email, String password, String name_surname, String fridge_adress) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.name_surname = name_surname;
        this.fridge_adress = fridge_adress;
    }
    
    public String toString() {
    	String salida = "Usuario: ( "+id+", "+username+", "+email+", "+password+", "+name_surname+", "+fridge_adress+")";
    	return salida;
    }
}