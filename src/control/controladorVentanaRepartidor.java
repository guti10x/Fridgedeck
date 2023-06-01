package control;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import application.connectBBDD;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class controladorVentanaRepartidor {
	private int user_id;
	@FXML
    private ListView lwCL;
	@FXML
    private ListView lwD;
	@FXML
    private ListView lwCS;
	@FXML
	private Button bttnInfoRepartidor;
	@FXML
	private Button btnAddProductClient;
	@FXML
	private Label lblTimeRepatridor;
	int id_user = 0;
	@FXML
	    void mostrarInfoRepartidor(ActionEvent event) {
			 try { 
				controladorVentanaInformacionUsuario control2 = new controladorVentanaInformacionUsuario();
				control2.setUserId(user_id);
				System.out.println("E " + user_id);
				FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/view/ventana_Informacion_usuario.fxml"));
					
				loader2.setController(control2);
				
				Parent root2 = loader2.load();
				
				Stage stage  = new Stage();
				
				stage.setScene(new Scene(root2));
				
				stage.initModality(Modality.WINDOW_MODAL);
				stage.initOwner(((Node) (event.getSource())).getScene().getWindow());
				stage.setResizable(true);
				double minWidth = 630.0;
		        double minHeight = 430.0;
		        stage.setMinWidth(minWidth);
		        stage.setMinHeight(minHeight);
				stage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	
	public void initialize() throws InterruptedException{
		leer_datos();
	}

	private void leer_datos() {
		String sql = "SELECT id, nombre, surname1, surname2 from Usuarios where role = 'user';";
		int id;
		String nombre="", surname1 = "", surname2 = "", nombreTotal = "";
		try {
            Connection conn = connectBBDD.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
            	int cont = 0;
            	id = rs.getInt("id");
            	nombre = rs.getString("nombre");
            	surname1 = rs.getString("surname1");
            	surname2 = rs.getString("surname2");
            	nombreTotal = nombre + " " + surname1 + " " + surname2 + ", id: " + id;
            	lwCL.getItems().add(nombreTotal);
            	cont++;
            }
            rs.close();
            stmt.close();
            conn.close();
       } catch (SQLException e2) {
           System.out.println(e2.getMessage());
       }
	}
	public void selectClient() {
		lwD.getItems().clear();
		lwCS.getItems().clear();
		String userId = (String) lwCL.getSelectionModel().getSelectedItem();
		userId = userId.replaceAll("\\D+", "");
		id_user = Integer.parseInt(userId);
		String sqlDelivery = "SELECT nombre, stock FROM Productos where id_nevera = (SELECT id_nevera FROM Subscribe WHERE id_user = '" + id_user + "');";
		String name = "";
		int cantidad = 0;
		Connection conn = null;
        Statement stmt  = null;
        ResultSet rs    = null;
		try {
            conn = connectBBDD.connect();
            stmt  = conn.createStatement();
            rs = stmt.executeQuery(sqlDelivery);
            while (rs.next()) {
            	name = rs.getString("nombre");
            	cantidad = rs.getInt("stock");
            	lwCS.getItems().add("- " + name + ", " + cantidad);
            	if(cantidad < 20) {
            		lwD.getItems().add("- " + name + ", " + cantidad);
            	}    			
            }
            rs.close();
            stmt.close();
            conn.close();
       } catch (SQLException e2) {
           System.out.println(e2.getMessage());
       }
		if((lwD).getItems().isEmpty()) {
			lwD.getItems().add("Tenemos todos los productos, gracias");
		}
	}
	@FXML
    void addProductClient(ActionEvent event) {
		if(id_user == 0) {
			showAlert("Error", "Por favor eliga usuario", AlertType.ERROR);
		}else {
			try {
				controladorAddProductBasket control = new controladorAddProductBasket();
				control.setUserId(id_user);
				
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/add_product_basket.fxml"));
	
				loader.setController(control);
		
				Parent root = loader.load();
				
				Stage stage  = new Stage();
				
				stage.setScene(new Scene(root));
				
				stage.initModality(Modality.WINDOW_MODAL);
				stage.initOwner(((Node) (event.getSource())).getScene().getWindow());
				stage.setResizable(false);
				stage.show();
			} catch(Exception e) {
				e.printStackTrace();
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
