package application;

public class Usuario {
	
    public final int id;
    public final String username;
    public final String email;
    public final String password;
    public final String name_surname;
    public final String fridge_adress;
    
    public Usuario(int id, String username, String email, String password, String name_surname, String fridge_adress) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.name_surname = name_surname;
        this.fridge_adress = fridge_adress;
    }
    
    public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getName_surname() {
		return name_surname;
	}

	public String getFridge_adress() {
		return fridge_adress;
	}

	public String toString() {
    	String salida = "Usuario: ( "+id+", "+username+", "+email+", "+password+", "+name_surname+", "+fridge_adress+")";
    	return salida;
    }
}