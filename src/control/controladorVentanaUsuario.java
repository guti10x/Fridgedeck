package control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingUtilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.FridgeDate;
import model.ListaCompras;
import model.ListaProductos;
import model.Productos;

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
    void mostrarInfoUsuario(ActionEvent event) {
		 try {
		 FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/view/ventana_Informacion_usuario.fxml"));
			
			controladorVentanaInformacionUsuario control2 = new controladorVentanaInformacionUsuario();
			
			loader2.setController(control2);
			
			Parent root2 = loader2.load();
			
			Stage stage  = new Stage();
			
			stage.setScene(new Scene(root2));
			
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(((Node) (event.getSource())).getScene().getWindow());
			stage.setResizable(false);
			stage.show();
		} catch(Exception e) {
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
/*
	@FXML
	public void logoutWindow() throws IOException {
	    stage.showAndWait();
	}
	*/
	public void leer_datos() {
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
		/*
			Reader reader = Files.newBufferedReader(Paths.get("fridgedate.json"));
			Reader readerListaCompras = Files.newBufferedReader(Paths.get("listas_compras.json"));
			Reader readerListaProductos = Files.newBufferedReader(Paths.get("listas_productos.json"));
			FridgeDate[] datos = new Gson().fromJson(reader, FridgeDate[].class);
			ListaCompras[] listaComp = new Gson().fromJson(readerListaCompras, ListaCompras[].class);
			ListaProductos[] listaProd = new Gson().fromJson(readerListaProductos, ListaProductos[].class);
*/
    		String sqlTemperatura = "SELECT valor FROM temperatura where id_nevera = (select id_nevera from subscribe where id_user ='" + user_id + "') "
    				+ "and fecha = (SELECT MAX(fecha) FROM temperatura WHERE id_nevera = (SELECT id_nevera FROM subscribe WHERE id_user = '" + user_id + "'));";
    		String sqlHumedad = "SELECT valor FROM humedad where id_nevera = (select id_nevera from subscribe where id_user ='" + user_id + "') "
    				+ "and fecha = (SELECT MAX(fecha) FROM humedad WHERE id_nevera = (SELECT id_nevera FROM subscribe WHERE id_user = '" + user_id + "'));";
    		String sqlPuerta = "SELECT valor FROM puerta where id_nevera = (select id_nevera from subscribe where id_user ='" + user_id + "') "
    				+ "and fecha = (SELECT MAX(fecha) FROM puerta WHERE id_nevera = (SELECT id_nevera FROM subscribe WHERE id_user = '" + user_id + "'));";
    		String sqlProductos = "SELECT nombre, stock FROM productos where id_nevera = (SELECT id_nevera FROM subscribe WHERE id_user = '" + user_id + "');";
    		int temperatura = -999, humedad = -999, puerta = -999, cantidad = 0;
    		String name = "";
    		
    		Connection conn = null;
            Statement stmt  = null;
            ResultSet rs    = null;
            try {
                 conn = connectBBDD.connect();
                 stmt = conn.createStatement();
                 rs = stmt.executeQuery(sqlTemperatura);
                while (rs.next()) {
                	temperatura = rs.getInt("valor");
                }
                rs.close();
                stmt.close();
                
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sqlHumedad);
                while (rs.next()) {
                	humedad = rs.getInt("valor");
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
            
            lblTemperatura.setText(String.valueOf(temperatura + "C"));
			lblHumedad.setText(String.valueOf(humedad + "%"));
			if((listaCompras).getItems().isEmpty()) {
				listaCompras.getItems().add("Tenemos todos los productos, gracias");
			}
			if(puerta==0) {
				imgPuertaAbierta.setVisible(true);
				lblPuerta.setText("open");
			}else {
				imgPuertaCerrada.setVisible(true);
				lblPuerta.setText("close");
			}/*
			for(int i=0; i<listaProd.length; i++) {
				if(listaProd[i].id_user==user_id) {
					for(int j=0; j<listaProd[i].lista_productos.size(); j++) {
						System.out.println("Size lc" + listaProd[i].lista_productos.size());
						listaProductos.getItems().add("- " + listaProd[i].lista_productos.get(j).name + ", "
								+ listaProd[i].lista_productos.get(j).cantidad);
					}
				}
			}*/
	}
	
	@FXML
    void openAddProductBasket(ActionEvent event) {
		/*
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
		*/
	}
}