package control;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import application.connectBBDD;


public class controladorLogin {
	@FXML
    private TextField tfUsernameLogin;
	
	@FXML
    private PasswordField pfPasswordLogin;

	@FXML
    private Button btnLogin;
	
	@FXML
    private Button btnRecContr;
	
	@FXML
    void entrar(ActionEvent e) throws IOException {
		int user_id = -1;
		boolean checkUser = false;

		JFrame jFrame = new JFrame();
        
        String username = tfUsernameLogin.getText().toString();
        String password = pfPasswordLogin.getText().toString();
		if(username.equals("")||password.equals("")) {
        	JOptionPane.showMessageDialog(jFrame, "Necesita rellenar todos los campos");
        }else {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            
            int id = -1;
    		String role = "";
    		String sql = "SELECT id, role FROM usuarios where username ='" + username + "' and password ='" + password + "';";
            
            try (Connection conn = connectBBDD.connect();
                 Statement stmt  = conn.createStatement();
                 ResultSet rs    = stmt.executeQuery(sql)){      
                while (rs.next()) {
                	user_id = rs.getInt("id");
                	role = rs.getString("role");
                }
            } catch (SQLException e2) {
                System.out.println(e2.getMessage());
            }
 
			System.out.println("User id: " + user_id);
			if(user_id!=-1) {
				checkUser = true;
				try {
			        FileWriter myWriter = new FileWriter("user_id.txt");
			        myWriter.write(String.valueOf(user_id));
			        myWriter.close();
			        System.out.println("Successfully wrote to the file.");
			    } catch (IOException e2) {
			      System.out.println("An error occurred.");
			      e2.printStackTrace();
			    }
			}

			if(role.equals("user")) {
				try {
					Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			        currentStage.close();
			        
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ventana_usuario.fxml"));
					
					controladorVentanaUsuario control = new controladorVentanaUsuario();
					
					loader.setController(control);
			
					Parent root = loader.load();
					
					Stage stage  = new Stage();
					
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
			}else if(role.equals("técnico")) {
				try {
					Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			        currentStage.close();
			        
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ventana_tecnico.fxml"));
					
					controladorVentanaTecnico control = new controladorVentanaTecnico();
					
					loader.setController(control);
			
					Parent root = loader.load();
					
					Stage stage  = new Stage();
					
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
			}else if(role.equals("repartidor")) {
				try {
					Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			        currentStage.close();
			        
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ventana_repartidor.fxml"));
					
					controladorVentanaRepartidor control = new controladorVentanaRepartidor();
					
					loader.setController(control);
			
					Parent root = loader.load();
					
					Stage stage  = new Stage();
					
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
				
			if(checkUser == false) {
				JOptionPane.showMessageDialog(jFrame, "Has introducido login o contraseÃ±a erroneo");
			}
        }
	}
}