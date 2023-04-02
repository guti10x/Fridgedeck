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
    
    public static final Stage stage  = new Stage();

    @FXML
    void btnCerrar_Click(ActionEvent event) {
    	System.exit(0);
    }
   
    @FXML
    void initialize() {/*
    	  assert tfUsernameInfo != null : "fx:id=\"tfUsernameInfo\" was not injected: check your FXML file 'ventana_Informacion_usuario.fxml'.";
          assert btnCerrarSesion != null : "fx:id=\"btnCerrarSesion\" was not injected: check your FXML file 'ventana_Informacion_usuario.fxml'.";
          assert tfemailinfo != null : "fx:id=\"tfemailinfo\" was not injected: check your FXML file 'ventana_Informacion_usuario.fxml'.";
          assert tfpasswordInfo != null : "fx:id=\"tfpasswordInfo\" was not injected: check your FXML file 'ventana_Informacion_usuario.fxml'.";
          assert tfNameSurnameInfo != null : "fx:id=\"tfNameSurnameInfo\" was not injected: check your FXML file 'ventana_Informacion_usuario.fxml'.";
          assert tfFridgeaddressInfo != null : "fx:id=\"tfFridgeaddressInfo\" was not injected: check your FXML file 'ventana_Informacion_usuario.fxml'.";
          */
          int user_id = 0;
  		try {
  		      File myObj = new File("user_id.txt");
  		      Scanner myReader = new Scanner(myObj);
  		      while (myReader.hasNextLine()) {
  		        String id = myReader.nextLine();
  		        user_id = Integer.valueOf(id);
  		      }
  		      myReader.close();
  		    } catch (FileNotFoundException e) {
  		      System.out.println("An error occurred.");
  		      e.printStackTrace();
  		    }
  		
		String username = "", nombre = "", surname1 = "", surname2 = "", email = "", password = "", name_surname = "", fridge_adress = "";
		int credit_card = 0, telephone_number = 0;
		Timestamp bdayTimestamp = null;
		String sql = "SELECT username, nombre, surname1, surname2, email, password, birthdate, credit_card, telephone_number FROM usuarios where id ='" + user_id + "';";
        
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
            	credit_card = rs.getInt("credit_card");
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
}
