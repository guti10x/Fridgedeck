package control;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.mail.MessagingException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Usuario;

public class controladorRegistrarse {
	@FXML
    private Button btnRegistrarUsuario;
	
	@FXML
    private TextField tfUsername;
	
	@FXML
    private TextField tfEmail;
	
	@FXML
    private PasswordField pfPassword;
	
	@FXML
    private TextField tfName;
	
	@FXML
    private TextField tfAddress;
	
	@FXML
    private ComboBox cmbRole;
	
	
	public void initialize() throws InterruptedException{
		cmbRole.getItems().add("Usuario");
		cmbRole.getItems().add("Tecnico");
		cmbRole.getItems().add("Proveedor");
	}
		
	@FXML
    void registrarUsuario(ActionEvent e) {
		JFrame jFrame = new JFrame();
		String address="", tipo="", username="", email="", password="", name="";
		if(cmbRole.getValue().equals("Elegir role")) {
			JOptionPane.showMessageDialog(jFrame, "Necesita elegir role");
		}else {	
			username = tfUsername.getText().toString();
            email = tfEmail.getText().toString();
            password = pfPassword.getText().toString();
            name = tfName.getText().toString();
        	if(cmbRole.getValue().equals("Usuario")) {
        		tipo = "user";
        		address = tfAddress.getText().toString();
        		//tfAddress.setDisable(false);
        	}if(cmbRole.getValue().equals("Tecnico")) {
        		tipo = "tecnico";
        		address = "-";
        		//tfAddress.setDisable(true);
        	}if(cmbRole.getValue().equals("Proveedor")) {
        		tipo = "proveedor";
        		address = "-";
        		//tfAddress.setDisable(true);
        	}
		}
            
        if(username.equals("")||email.equals("")||password.equals("")||
        		name.equals("")|address.equals("")) {
        	JOptionPane.showMessageDialog(jFrame, "Necesita rellenar todos los campos");
        }else {
            try {
            	Gson gson = new GsonBuilder().setPrettyPrinting().create();
            	Reader reader = Files.newBufferedReader(Paths.get("userbase.json"));
    			Usuario[] usersArray = new Gson().fromJson(reader, Usuario[].class);
    			System.out.println("usersArray " +usersArray);
    			reader.close();
    			Reader reader2 = Files.newBufferedReader(Paths.get("userbase.json"));
    			List<Usuario> users = new Gson().fromJson(reader2, new TypeToken<List<Usuario>>() {}.getType());
    			System.out.println("users " +users);
    			Usuario user = new Usuario(usersArray.length, tipo, username, email, password, name, address);
    			System.out.println("user "+user);
    	
    			boolean checkUser = true;
    			for(int i=0; i<usersArray.length; i++) {
    				/*if((usersArray[i].username!=username)&&(usersArray[i].email!=email)) {
    					checkUser=true;
    				}else {*/
    					if(usersArray[i].username.equals(username)) {
        					JOptionPane.showMessageDialog(jFrame, "Username ya existe");
        					checkUser=false;
        				}
        				if(usersArray[i].email.equals(email)) {
        					JOptionPane.showMessageDialog(jFrame, "Existe usuario con este correo");
        					checkUser=false;
        				}
    				//}	
    			}
    			
    			
    			users.add(user);
    			users.forEach(System.out::println);
    			reader.close();
    			
    			if(checkUser==true) {
    				Writer writer = new FileWriter("userbase.json");
    		    new Gson().toJson(users, writer);
    			writer.close();
    			JOptionPane.showMessageDialog(jFrame, "Has registrado");
	    			try {
						SendEmail.enviarCorreo(username, password, email);
					} catch (MessagingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
    			}	
    		} catch (IOException e2) {
    			// TODO Auto-generated catch block
    			e2.printStackTrace();
    		}
            
            
            /*
            try {
    			SendEmail.enviarCorreo(username, password, email);
    		} catch (MessagingException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
            */	
        }
	}
}