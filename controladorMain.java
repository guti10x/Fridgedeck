package control;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class controladorMain {
	@FXML
    private Button btnLogin;
	
	@FXML
    private Button btnRegistrarse;
	
	@FXML
    void abrirLogin(ActionEvent event) {
		try {
			FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/view/login.fxml"));
			
			controladorLogin control2 = new controladorLogin();
			
			loader2.setController(control2);
	
			Parent root2 = loader2.load();
						
			Stage stage = new Stage();
			
			stage.setScene(new Scene(root2));
			
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(((Node) (event.getSource())).getScene().getWindow());
			stage.setResizable(false);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
    void abrirRegistrarse(ActionEvent event) {
		try {
			FXMLLoader loader3 = new FXMLLoader(getClass().getResource("/view/registrarse.fxml"));
			
			controladorRegistrarse control3 = new controladorRegistrarse();
			
			loader3.setController(control3);
	
			Parent root3 = loader3.load();
						
			Stage stage = new Stage();
			
			stage.setScene(new Scene(root3));
			
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(((Node) (event.getSource())).getScene().getWindow());
			stage.setResizable(false);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}