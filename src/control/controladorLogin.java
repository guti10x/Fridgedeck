package control;

import java.io.File;
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

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Usuario;

public class controladorLogin {
	@FXML
    private TextField tfUsernameLogin;
	
	@FXML
    private PasswordField pfPasswordLogin;

	@FXML
    private Button btnLogin;
	
	@FXML
    private Button btnRecContr;
	
	public static final Stage stage  = new Stage();
	
	@FXML
    void entrar(ActionEvent e) {
		int user_id = 0;

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
    					user_id = users[i].id;
    					try {
					        FileWriter myWriter = new FileWriter("user_id.txt");
					        myWriter.write(String.valueOf(user_id));
					        myWriter.close();
					        System.out.println("Successfully wrote to the file.");
					    } catch (IOException e2) {
					      System.out.println("An error occurred.");
					      e2.printStackTrace();
					    }
    					
    					if(users[i].tipo.equals("user")) {
    						try {
        						FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ventana_usuario.fxml"));
        						
        						controladorVentanaUsuario control = new controladorVentanaUsuario();
        						
        						loader.setController(control);
        				
        						Parent root = loader.load();
      						
        						stage.setScene(new Scene(root));
        						
        						stage.initModality(Modality.WINDOW_MODAL);
        						stage.initOwner(((Node) (e.getSource())).getScene().getWindow());
        						stage.setResizable(false);
        						stage.show();
        						
        						controladorMain cm = new controladorMain();
        						//cm.cancelLogin();
        					} catch(Exception e3) {
        						e3.printStackTrace();
        					}
    					}else if(users[i].tipo.equals("tecnico")) {
    						try {
        						FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ventana_tecnico.fxml"));
        						
        						controladorVentanaTecnico control = new controladorVentanaTecnico();
        						
        						loader.setController(control);
        				
        						Parent root = loader.load();
				
        						stage.setScene(new Scene(root));
        						
        						stage.initModality(Modality.WINDOW_MODAL);
        						stage.initOwner(((Node) (e.getSource())).getScene().getWindow());
        						stage.setResizable(false);
        						stage.show();
        						
        						controladorMain cm = new controladorMain();
        						//cm.cancelLogin();
        					} catch(Exception e3) {
        						e3.printStackTrace();
        					}
    					}else if(users[i].tipo.equals("proveedor")) {
    						try {
        						FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ventana_repartidor.fxml"));
        						
        						controladorVentanaRepartidor control = new controladorVentanaRepartidor();
        						
        						loader.setController(control);
        				
        						Parent root = loader.load();
				
        						stage.setScene(new Scene(root));
        						
        						stage.initModality(Modality.WINDOW_MODAL);
        						stage.initOwner(((Node) (e.getSource())).getScene().getWindow());
        						stage.setResizable(false);
        						stage.show();
        						
        						controladorMain cm = new controladorMain();
        						//cm.cancelLogin();
        					} catch(Exception e3) {
        						e3.printStackTrace();
        					}
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