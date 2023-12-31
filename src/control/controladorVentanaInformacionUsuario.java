package control;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.Scanner;

import application.connectBBDD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class controladorVentanaInformacionUsuario {

	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField tfUIUsername;
	
	@FXML
    private TextField tfUINombre;
	
	@FXML
    private TextField tfUISurname1;
	
	@FXML
    private TextField tfUISurname2;
	
	@FXML
    private TextField tfUIEmail;
	
	@FXML
    private PasswordField pfUIPassword;
	
	@FXML
    private DatePicker dpUIBirthday;
	
	@FXML
    private TextField tfUICreditCard;
	
	@FXML
    private TextField tfUITelephone;
	
    @FXML
    private Button btnCerrarSesion;
	@FXML
    private Text textUsername;
    private int user_id;

    @FXML
    void btnCerrar_Click(ActionEvent event) {
    	System.exit(0);
    }
   
    @FXML
    void initialize() {
		String username = "", nombre = "", surname1 = "", surname2 = "", email = "", password = "", name_surname = "", fridge_adress = "";
		long credit_card = 0;
		int telephone_number = 0;
		Timestamp bdayTimestamp = null;
		String sql = "SELECT username, nombre, surname1, surname2, email, password, birthdate, credit_card, telephone_number FROM Usuarios where id ='" + user_id + "';";
        
        try (Connection conn = connectBBDD.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){      
            while (rs.next()) {
            	username = rs.getString("username");
            	nombre = rs.getString("nombre");
            	surname1 = rs.getString("surname1");
            	surname2 = rs.getString("surname2");
            	email = rs.getString("email");
            	password = rs.getString("password");
            	//bdayTimestamp = rs.getTimestamp("birthdate");
            	credit_card = rs.getLong("credit_card");
            	telephone_number = rs.getInt("telephone_number");
            }
            tfUIUsername.setText(username);
            tfUINombre.setText(nombre);
            tfUISurname1.setText(surname1);
            tfUISurname2.setText(surname2);
            tfUIEmail.setText(email);
            pfUIPassword.setText(password);
            //dpUIBirthday.setValue(bdayTimestamp.toLocalDateTime().toLocalDate());
            tfUICreditCard.setText(String.valueOf(credit_card));
            tfUITelephone.setText(String.valueOf(telephone_number));
			
			rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e2) {
            System.out.println(e2.getMessage());
        }
    }
    public void setUserId(int user_id) {
	    this.user_id = user_id;
	}
}