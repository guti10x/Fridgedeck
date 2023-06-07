package control;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import application.connectBBDD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

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

	private int user_id;
		
	@SuppressWarnings("unused")
	@FXML
    void addProduct(ActionEvent e) {        
        String name = tfNombre.getText().toString();
        String cantidad = tfCantidad.getText().toString();
        String descripcion = tfDesc.getText().toString();
        String code = tfCode.getText().toString();
        LocalDate fechaProducto = dpFechaAddPr.getValue();
		Date fechaProductoSql = null;
        
        if(name.equals("")||cantidad.equals("") || descripcion.equals("") || code.equals("") || fechaProducto == null) {
        	showAlert("Error", "Necesita rellenar todos los campos", AlertType.ERROR);
        }else {
    		fechaProductoSql = Date.valueOf(fechaProducto);
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
                showAlert("Success", "Se ha añadido el producto!", AlertType.INFORMATION);
           } catch (SQLException e2) {
               System.out.println(e2.getMessage());
           }
        }
	}
	private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
	public void setUserId(int user_id) {
	    this.user_id = user_id;
	}
}