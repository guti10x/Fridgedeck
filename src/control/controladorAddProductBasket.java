package control;

import java.io.File;
import java.io.FileNotFoundException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import application.connectBBDD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Compras;
import model.FridgeDate;
import model.ListaCompras;
import model.Productos;
import model.Usuario;

public class controladorAddProductBasket {
	@FXML
    private TextField tfNombre;
	
	@FXML
    private TextField tfCantidad;

	@FXML
    private Button btnAddProduct;
	
	//public static Stage stage  = new Stage();
	
	@FXML
    void addProduct(ActionEvent e) {
		int user_id = 0;
		try {
		      File myObj = new File("user_id.txt");
		      Scanner myReader = new Scanner(myObj);
		      while (myReader.hasNextLine()) {
		        String id = myReader.nextLine();
		        user_id = Integer.valueOf(id);
		      }
		      myReader.close();
	    } catch (FileNotFoundException e3) {
	      System.out.println("An error occurred.");
	      e3.printStackTrace();
	    }
		
		JFrame jFrame = new JFrame();
        
        String name = tfNombre.getText().toString();
        String cantidad = tfCantidad.getText().toString();
        
        if(tfNombre.equals("")||cantidad.equals("")) {
        	JOptionPane.showMessageDialog(jFrame, "Necesita rellenar todos los campos");
        }else {
        	try {
        		String sqlInsert = "INSERT INTO lista_compras (name, cantidad, id_user)"
            			+ "VALUES ('" + name + "', '" + cantidad + "', '" + user_id + "')";
        		
        		Connection conn = connectBBDD.connect();
                Statement stmt  = conn.createStatement();
                stmt.executeUpdate(sqlInsert);
                conn.close();
                
                tfNombre.setText("");
                tfCantidad.setText("");
                JOptionPane.showMessageDialog(jFrame, "Has añadido producto!");
           } catch (SQLException e2) {
               System.out.println(e2.getMessage());
           }
        	controladorVentanaUsuario controladorVentanaUsuario = new controladorVentanaUsuario();
        }
	}
}