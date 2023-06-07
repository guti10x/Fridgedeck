package control;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import java.util.ArrayList;

public class controladorVentanaUsuario {
	@FXML
    private Label lblTime;
	
	@FXML
    private Label lblTemperatura;
	
	@FXML
    private Label lblHumedad;
	
	@FXML
    private ImageView imgPuerta;
	
	@FXML
    private ListView listaCompras;
	
	@FXML
    private ListView listaProductos;
	
	@FXML
    private Button btnAddProductBasket;
	
	@FXML
	private Button btnInfoUsuario;
	
	@FXML
	private Button btnAbrirChat;
	
	@FXML
	private Label lblEstadoPuerta;
	@FXML
	private Button btnBuscarProducto;
	@FXML
	private TextField txtBuscarProducto;
	
	private int user_id;
	
	@FXML
    void mostrarInfoUsuario(ActionEvent event) {
		try {
	        controladorVentanaInformacionUsuario control2 = new controladorVentanaInformacionUsuario();
	        control2.setUserId(user_id);
	        
	        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/view/ventana_Informacion_usuario.fxml"));
	        loader2.setController(control2);
	        
	        AnchorPane root2 = loader2.load();
	        
	        Stage stage = new Stage();
      
	        stage.setScene(new Scene(root2));
	        stage.initModality(Modality.WINDOW_MODAL);
	        
	        stage.setResizable(true);
			double minWidth = 630.0;
	        double minHeight = 430.0;
	        stage.setMinWidth(minWidth);
	        stage.setMinHeight(minHeight);
	        stage.show();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void initialize() throws InterruptedException{
		leer_datos();
	}
	
	public void leer_datos() {
		String sqlTemperatura = "SELECT valor FROM Temperatura where id_nevera = (select id_nevera from Subscribe where id_user ='" + user_id + "') "
				+ "and fecha = (SELECT MAX(fecha) FROM Temperatura WHERE id_nevera = (SELECT id_nevera FROM Subscribe WHERE id_user = '" + user_id + "'));";
		System.out.println("sql " + sqlTemperatura);
		String sqlHumedad = "SELECT valor FROM Humedad where id_nevera = (select id_nevera from Subscribe where id_user ='" + user_id + "') "
				+ "and fecha = (SELECT MAX(fecha) FROM Humedad WHERE id_nevera = (SELECT id_nevera FROM Subscribe WHERE id_user = '" + user_id + "'));";
		String sqlPuerta = "SELECT valor FROM Puerta where id_nevera = (select id_nevera from Subscribe where id_user ='" + user_id + "') "
				+ "and fecha = (SELECT MAX(fecha) FROM Puerta WHERE id_nevera = (SELECT id_nevera FROM Subscribe WHERE id_user = '" + user_id + "'));";
		String sqlProductos = "SELECT nombre, stock FROM Productos where id_nevera = (SELECT id_nevera FROM Subscribe WHERE id_user = '" + user_id + "');";
		int puerta = -999, cantidad = 0;
		String temperatura = "", humedad = "";
		String name = "";
		
		Connection conn = null;
        Statement stmt  = null;
        ResultSet rs    = null;
        try {
             conn = connectBBDD.connect();
             stmt = conn.createStatement();
             rs = stmt.executeQuery(sqlTemperatura);
            while (rs.next()) {
            	temperatura = rs.getString("valor");
            }
            rs.close();
            stmt.close();
            
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlHumedad);
            while (rs.next()) {
            	humedad = rs.getString("valor");
            }
            rs.close();
            stmt.close();
            
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlPuerta);
            while (rs.next()) {
            	puerta = rs.getInt("valor");
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e2) {
            System.out.println(e2.getMessage());
        }            
        
        try {
            Connection conn2 = connectBBDD.connect();
            Statement stmt2  = conn2.createStatement();
            ResultSet rsPrd = stmt2.executeQuery(sqlProductos);
            while (rsPrd.next()) {
            	name = rsPrd.getString("nombre");
            	cantidad = rsPrd.getInt("stock");
            	listaProductos.getItems().add("- " + name + ", " + cantidad);
            	if(cantidad < 20) {
            		listaCompras.getItems().add("- " + name + ", " + cantidad);
            	}
            }
            rsPrd.close();
            stmt2.close();
            conn2.close();
       } catch (SQLException e2) {
           System.out.println(e2.getMessage());
       }     
        
        lblTemperatura.setText(temperatura+" ºC");
		lblHumedad.setText(humedad+" %");
		if((listaCompras).getItems().isEmpty()) {
			listaCompras.getItems().add("Tenemos todos los productos, gracias");
		}
		if(puerta==0) {
			lblEstadoPuerta.setText("Abierta");
		}else {
			lblEstadoPuerta.setText("Cerrada");
		}
	}
	
	@FXML
    void openAddProductBasket(ActionEvent event) {
		try {
			controladorAddProductBasket control = new controladorAddProductBasket();
			control.setUserId(user_id);
			
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
	
	@FXML
    void functionAbrirChat(ActionEvent event) {
		try {
			controladorChatUser control = new controladorChatUser();
			control.setUserId(user_id);
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ventana_chat.fxml"));

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
	@FXML
    void buscarProducto(ActionEvent event) {
		ArrayList<String> productList = new ArrayList<>();
		productList.addAll(listaProductos.getItems());
		productList.replaceAll(s -> s.replace("- ", ""));
		Collections.sort(productList);
		try {
			String productoIntroducido = txtBuscarProducto.getText();
			if(!productoIntroducido.isEmpty()) {
				int low = 0;
				int high = productList.size() - 1;
				boolean isFound = false;
				int index = -1;
				String nombre = "", cantidad = "";
				
				while (low <= high) {
				    int mid = (low + high) / 2;
				    nombre = (productList.get(mid)).substring(0, productList.get(mid).indexOf(","));
				    if (nombre.equalsIgnoreCase(productoIntroducido)) {
				    	cantidad = productList.get(mid).substring(productList.get(mid).indexOf(",") + 2).trim();
				        isFound = true;
				        index = mid;
				        break;
				    }
				    
				    if (productList.get(mid).compareToIgnoreCase(productoIntroducido) < 0) {
				        low = mid + 1;
				    } else {
				        high = mid - 1;
				    }
				}

				if (isFound) {
					showAlert("Found", "Producto " + productoIntroducido + " está en frigorifico, cantidad: " + cantidad, AlertType.INFORMATION);
				} else {
					showAlert("Not found", "Producto " + productoIntroducido + " no está en la lista", AlertType.INFORMATION);
				}
			}else {
				showAlert("Error", "Por favor introduzca algo", AlertType.ERROR);
			}
		} catch(Exception e) {
			e.printStackTrace();
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