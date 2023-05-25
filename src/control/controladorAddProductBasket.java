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
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
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
    private TextField tfDesc;
	
	@FXML
    private TextField tfCantidad;
	
	@FXML
    private TextField tfCode;

	@FXML
    private Button btnAddProduct;
	
	@FXML
    private DatePicker dpFechaAddPr;
		
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
        String descripcion = tfDesc.getText().toString();
        String code = tfCode.getText().toString();
        LocalDate fechaProducto = dpFechaAddPr.getValue();
		Date fechaProductoSql = null;
		fechaProductoSql = Date.valueOf(fechaProducto);
        
        if(tfNombre.equals("")||cantidad.equals("")) {
        	JOptionPane.showMessageDialog(jFrame, "Necesita rellenar todos los campos");
        }else {
        	try {
        		String sqlInsert = "INSERT INTO Productos (nombre, descripcion, stock, code, fecha, id_nevera) "
                        + "VALUES ('" + name + "', '" + descripcion + "', '" + cantidad + "', '" + code + "', '" + fechaProductoSql + "', "
                        + "(SELECT id_nevera FROM Subscribe WHERE id_user ='" + user_id + "'))";
        		System.out.println(sqlInsert);
        		Connection conn = connectBBDD.connect();
                Statement stmt  = conn.createStatement();
                stmt.executeUpdate(sqlInsert);
                conn.close();
                
                tfNombre.setText("");
                tfCantidad.setText("");
                tfDesc.setText("");
                tfCode.setText("");
                JOptionPane.showMessageDialog(jFrame, "Has añadido producto!");
           } catch (SQLException e2) {
               System.out.println(e2.getMessage());
           }
        	/*
        	controladorVentanaUsuario controladorVentanaUsuario = new controladorVentanaUsuario();
        	try {
				controladorVentanaUsuario.initialize();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
        }
	}
}