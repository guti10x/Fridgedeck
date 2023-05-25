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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class controladorVentanaUsuario {
	@FXML
    private Label lblTime;
	
	@FXML
    private Label lblTemperatura;
	
	@FXML
    private Label lblHumedad;
	
	@FXML
    private ImageView imgPuertaAbierta;
	
	@FXML
    private ImageView imgPuertaCerrada;
	
	@FXML
    private Label lblPuerta;
	
	@FXML
    private ListView listaCompras;
	
	@FXML
    private ListView listaProductos;
	
	@FXML
    private Button btnAddProductBasket;
	
	@FXML
	private Button bttnInfoUsuario;
	
	@FXML
	private Button btnAbrirChat;
	
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
	        
	        double minWidth = 480.0;
	        double minHeight = 850.0;
	        stage.setMinWidth(minWidth);
	        stage.setMinHeight(minHeight);
	        
	        stage.setScene(new Scene(root2));
	        stage.initModality(Modality.WINDOW_MODAL);
	        
	        if (event.getSource() instanceof Node) {
	            Node sourceNode = (Node) event.getSource();
	            Scene sourceScene = sourceNode.getScene();
	            Window ownerWindow = sourceScene.getWindow();
	            stage.initOwner(ownerWindow);
	        }
	        
	        stage.setResizable(true);
	        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
	            double scaleFactor = newVal.doubleValue() / oldVal.doubleValue();
	            stage.setHeight(stage.getHeight() * scaleFactor);
	        });
	        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
	            double scaleFactor = newVal.doubleValue() / oldVal.doubleValue();
	            stage.setWidth(stage.getWidth() * scaleFactor);
	        });
	        stage.show();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void initialize() throws InterruptedException{
		Timer timer = new Timer("Display Timer");
		TimerTask task = new TimerTask() {
		    @Override
		    public void run() {
		        	try {
						Platform.runLater(new Runnable() {
						    @Override
						    public void run() {
						        DateFormat timeFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss", new Locale("es, ES"));
						        Calendar cali = Calendar.getInstance();
						        cali.getTime();
						        String time = timeFormat.format(cali.getTimeInMillis());
						        //System.out.println(timeFormat.format(cali.getTimeInMillis()));
						        lblTime.setText(time);

						    }
						});
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    }
		};
		timer.scheduleAtFixedRate(task, 1000, 1000);
		
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
        
        lblTemperatura.setText(temperatura);
		lblHumedad.setText(humedad);
		if((listaCompras).getItems().isEmpty()) {
			listaCompras.getItems().add("Tenemos todos los productos, gracias");
		}
		if(puerta==0) {
			imgPuertaAbierta.setVisible(true);
			lblPuerta.setText("open");
		}else {
			imgPuertaCerrada.setVisible(true);
			lblPuerta.setText("close");
		}
	}
	
	@FXML
    void openAddProductBasket(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/add_product_basket.fxml"));
			
			controladorAddProductBasket control = new controladorAddProductBasket();
			
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
	public void setUserId(int user_id) {
	    this.user_id = user_id;
	}
}