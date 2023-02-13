package control;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import application.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.FridgeDate;
import model.ListaCompras;
import model.ListaProductos;

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
  		
  			Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		try {
			Reader reader = Files.newBufferedReader(Paths.get("userbase.json"));
			Usuario[] users = new Gson().fromJson(reader, Usuario[].class);
			for(int i=0; i<users.length; i++) {
				if(users[i].id==user_id) {
					tfUsernameInfo.setText(String.valueOf(users[user_id].username));
					tfemailinfo.setText(String.valueOf(users[user_id].email));
					tfpasswordInfo.setText(String.valueOf(users[user_id].password));
					tfNameSurnameInfo.setText(String.valueOf(users[user_id].name_surname));
					tfFridgeaddressInfo.setText(String.valueOf(users[user_id].fridge_adress));
				}
			}
			reader.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
}
