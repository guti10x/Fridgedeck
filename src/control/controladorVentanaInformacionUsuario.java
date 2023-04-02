package control;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.Scanner;

import application.connectBBDD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
  		
		String username = "", email = "", password = "", name_surname = "", fridge_adress = "";
		String sql = "SELECT username, email, password, name_surname, fridge_adress FROM users where id ='" + user_id + "';";
        
        try (Connection conn = connectBBDD.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){      
            while (rs.next()) {
            	username = rs.getString("username");
            	email = rs.getString("email");
            	password = rs.getString("password");
            	name_surname = rs.getString("name_surname");
            	fridge_adress = rs.getString("fridge_adress");
            }
            tfUsernameInfo.setText(String.valueOf(username));
			tfemailinfo.setText(String.valueOf(email));
			tfpasswordInfo.setText(String.valueOf(password));
			tfNameSurnameInfo.setText(String.valueOf(name_surname));
			tfFridgeaddressInfo.setText(String.valueOf(fridge_adress));
			
			rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e2) {
            System.out.println(e2.getMessage());
        }
		
    }
}
