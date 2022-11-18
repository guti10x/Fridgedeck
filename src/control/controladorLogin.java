package control;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import application.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class controladorLogin {
	@FXML
    private TextField tfUsernameLogin;
	
	@FXML
    private PasswordField pfPasswordLogin;

	@FXML
    private Button btnLogin;
	
	public static final Stage stage  = new Stage();
	 
	@FXML
    void entrar(ActionEvent e) {
		JFrame jFrame = new JFrame();
        
        String username = tfUsernameLogin.getText().toString();
        String password = pfPasswordLogin.getText().toString();
        
        if(username.equals("")||password.equals("")) {
        	JOptionPane.showMessageDialog(jFrame, "Necesita rellenar todos los campos");
        }else {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            try {
    			Reader reader = Files.newBufferedReader(Paths.get("userbase.json"));
    			Usuario[] users = new Gson().fromJson(reader, Usuario[].class);
    			boolean checkUser = false;
    			for(int i=0; i<users.length; i++) {
    				if(users[i].username.equals(username)&&users[i].password.equals(password)) {
    					//System.out.println("Existe");
    					checkUser = true;
    					try {
    						FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ventana_usuario.fxml"));
    						
    						controladorVentanaUsuario control = new controladorVentanaUsuario();
    						
    						loader.setController(control);
    				
    						Parent root2 = loader.load();
    						
    						stage.setScene(new Scene(root2));
    						
    						stage.initModality(Modality.WINDOW_MODAL);
    						stage.initOwner(((Node) (e.getSource())).getScene().getWindow());
    						stage.setResizable(false);
    						stage.show();
    						
    						//controladorMain cm = new controladorMain();
    						//cm.cancelLogin();
    					} catch(Exception e3) {
    						e3.printStackTrace();
    					}
    				}
    			}
    			if(checkUser == false) {
    				JOptionPane.showMessageDialog(jFrame, "Has introducido login o contraseña erroneo");
    			}
    			reader.close();
    		} catch (IOException e2) {
    			// TODO Auto-generated catch block
    			e2.printStackTrace();
    		}
        }
	}
}