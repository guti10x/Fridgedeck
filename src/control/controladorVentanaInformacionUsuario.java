package control;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class controladorVentanaInformacionUsuario {

	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField tfUsernameInfo;

    @FXML
    private Button btnCerrarSesion;

    @FXML
    private TextField tfemailinfo;

    @FXML
    private TextField tfpasswordInfo;

    @FXML
    private TextField tfNameSurnameInfo;

    @FXML
    private TextField tfFridgeaddressInfo;
    public static final Stage stage  = new Stage();
 

    @FXML
    void btnCerrar_Click(ActionEvent event) {
    	System.exit(0);

    }
   
    @FXML
    void initialize() {
    	  assert tfUsernameInfo != null : "fx:id=\"tfUsernameInfo\" was not injected: check your FXML file 'ventana_Informacion_usuario.fxml'.";
          assert btnCerrarSesion != null : "fx:id=\"btnCerrarSesion\" was not injected: check your FXML file 'ventana_Informacion_usuario.fxml'.";
          assert tfemailinfo != null : "fx:id=\"tfemailinfo\" was not injected: check your FXML file 'ventana_Informacion_usuario.fxml'.";
          assert tfpasswordInfo != null : "fx:id=\"tfpasswordInfo\" was not injected: check your FXML file 'ventana_Informacion_usuario.fxml'.";
          assert tfNameSurnameInfo != null : "fx:id=\"tfNameSurnameInfo\" was not injected: check your FXML file 'ventana_Informacion_usuario.fxml'.";
          assert tfFridgeaddressInfo != null : "fx:id=\"tfFridgeaddressInfo\" was not injected: check your FXML file 'ventana_Informacion_usuario.fxml'.";

    }
}
