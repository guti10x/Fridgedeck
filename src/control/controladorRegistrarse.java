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

import application.SendEmail;
import application.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class controladorRegistrarse {
	@FXML
    private Button btnRegistrarUsuario;
	
	@FXML
    private TextField tfUsername;
	
	@FXML
    private TextField tfEmail;
	
	@FXML
    private TextField tfPassword;
	
	@FXML
    private TextField tfName;
	
	@FXML
    private TextField tfAddress;
	
	@FXML
    void registrarUsuario(ActionEvent e) {
		JFrame jFrame = new JFrame();
        JOptionPane.showMessageDialog(jFrame, "Has registrado");
        
        String username = tfUsername.getText().toString();
        String email = tfEmail.getText().toString();
        String password = tfPassword.getText().toString();
        String name = tfName.getText().toString();
        String address = tfAddress.getText().toString();
        
        Usuario user = new Usuario(1, username, email, password, name, address);
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
			Reader reader = Files.newBufferedReader(Paths.get("userbase.json"));
			List<Usuario> users = new Gson().fromJson(reader, new TypeToken<List<Usuario>>() {}.getType());
			users.add(user);
			users.forEach(System.out::println);
			reader.close();
			
			Writer writer = new FileWriter("userbase.json");
		    new Gson().toJson(users, writer);
			writer.close();
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