package control;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
	
	private int userId;
	
	@FXML
    void entrar(ActionEvent e) throws IOException {
		int user_id = -1;
		boolean checkUser = false;

		JFrame jFrame = new JFrame();
        
        String username = tfUsernameLogin.getText().toString();
        String password = pfPasswordLogin.getText().toString();
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Rellene todos los campos", AlertType.ERROR);
        }else {            
    		String role = "";
    		String sql = "SELECT id, role FROM Usuarios where username ='" + username + "' and password ='" + password + "';";
            
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
				userId = user_id;
			}

			if(role.equals("user")) {
				openVentanaUsuario(e);
			}else if(role.equals("técnico")) {
				openVentanaTecnico(e);
			}else if(role.equals("repartidor")) {
				openVentanaRepartidor(e);
			}
				
			if(!checkUser) {
				JOptionPane.showMessageDialog(jFrame, "Has introducido login o contraseña erroneo");
			}
        }
	}
	
	private void openVentanaUsuario(ActionEvent e) throws IOException {
		controladorVentanaUsuario control = new controladorVentanaUsuario();
		control.setUserId(userId);

		Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		currentStage.close();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ventana_usuario.fxml"));

		loader.setController(control);

		Parent root = loader.load();

		Stage stage = new Stage();

		stage.setScene(new Scene(root));

		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(((Node) (e.getSource())).getScene().getWindow());
		stage.setResizable(false);
		stage.show();
	}
	
	private void openVentanaTecnico(ActionEvent e) throws IOException {
		controladorVentanaTecnico control = new controladorVentanaTecnico();
		control.setUserId(userId);
		
		Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		currentStage.close();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ventana_Tecnico.fxml"));

		

		loader.setController(control);

		Parent root = loader.load();

		Stage stage = new Stage();

		stage.setScene(new Scene(root));

		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(((Node) (e.getSource())).getScene().getWindow());
		stage.setResizable(false);
		stage.show();
	}
	
	private void openVentanaRepartidor(ActionEvent e) throws IOException {
		Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		currentStage.close();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ventana_repartidor.fxml"));

		controladorVentanaRepartidor control = new controladorVentanaRepartidor();

		loader.setController(control);

		Parent root = loader.load();

		Stage stage = new Stage();

		stage.setScene(new Scene(root));

		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(((Node) (e.getSource())).getScene().getWindow());
		stage.setResizable(false);
		stage.show();
	}

	private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}