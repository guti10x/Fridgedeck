package application;

public class Tecnico {
	
    public final int id;
    public final String username;
    public final String email;
    public final String password;
    public final String name_surname;
   
    
    public Tecnico(int id, String username, String email, String password, String name_surname) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.name_surname = name_surname;
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
}