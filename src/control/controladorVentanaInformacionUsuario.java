package control;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class controladorVentanaInformacionUsuario {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField tfUsernameLogin;

    @FXML
    private Button btnLogin;

    @FXML
    private TextField tfUsernameLogin1;

    @FXML
    private TextField tfUsernameLogin11;

    @FXML
    private TextField tfUsernameLogin111;

    @FXML
    private TextField tfUsernameLogin1111;

    @FXML
    void entrar(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert tfUsernameLogin != null : "fx:id=\"tfUsernameLogin\" was not injected: check your FXML file 'ventana_Informacion_usuario.fxml'.";
        assert btnLogin != null : "fx:id=\"btnLogin\" was not injected: check your FXML file 'ventana_Informacion_usuario.fxml'.";
        assert tfUsernameLogin1 != null : "fx:id=\"tfUsernameLogin1\" was not injected: check your FXML file 'ventana_Informacion_usuario.fxml'.";
        assert tfUsernameLogin11 != null : "fx:id=\"tfUsernameLogin11\" was not injected: check your FXML file 'ventana_Informacion_usuario.fxml'.";
        assert tfUsernameLogin111 != null : "fx:id=\"tfUsernameLogin111\" was not injected: check your FXML file 'ventana_Informacion_usuario.fxml'.";
        assert tfUsernameLogin1111 != null : "fx:id=\"tfUsernameLogin1111\" was not injected: check your FXML file 'ventana_Informacion_usuario.fxml'.";

    }
}
