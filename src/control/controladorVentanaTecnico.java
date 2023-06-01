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
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class controladorVentanaTecnico {
	public static final Stage stage  = new Stage();
	private int user_id;
	private int id_user_Seleccionado;
	
	@FXML
	private Label lblTimeTecnico;
	@FXML
	private Label lblHum;
	@FXML
	private Label lblTemp;
	@FXML
	private Label lblPuertaTec;
	@FXML
    private ListView listaUsuarios;
	@FXML
    private ListView listWarnings;
	@FXML
	private Button bttnInfoTecnico;
	@FXML
	private Button btnChatTecnico;
	@FXML
	private LineChart<String, Double> graficoTemperatura;
	@FXML
    void mostrarInfoTecnico(ActionEvent event) {
		 try {
			controladorVentanaInformacionUsuario control2 = new controladorVentanaInformacionUsuario();
			control2.setUserId(user_id);
			
			FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/view/ventana_Informacion_usuario.fxml"));

			loader2.setController(control2);
			
			Parent root2 = loader2.load();
			
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
		/*
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
						        lblTimeTecnico.setText(time);

						    }
						});
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    }
		};
		timer.scheduleAtFixedRate(task, 1000, 1000);	
		*/
		leer_datos();
	}

	public void leer_datos() {
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
            	listaUsuarios.getItems().add(nombreTotal);
            	cont++;
            }
            rs.close();
            stmt.close();
            conn.close();
       } catch (SQLException e2) {
           System.out.println(e2.getMessage());
       }
	}
	public void selectUser() {
		listWarnings.getItems().clear();
		graficoTemperatura.getData().clear();
		String idString = (String) listaUsuarios.getSelectionModel().getSelectedItem();
		idString = idString.substring(idString.indexOf(':')+1);
		id_user_Seleccionado = Integer.parseInt(idString.trim());
		System.out.println("id_user_Seleccionado:"+ id_user_Seleccionado);
		String sqlTemperatura = "SELECT valor FROM Temperatura where id_nevera = (select id_nevera from Subscribe where id_user ='" + id_user_Seleccionado + "') "
				+ "and fecha = (SELECT MAX(fecha) FROM Temperatura WHERE id_nevera = (SELECT id_nevera FROM Subscribe WHERE id_user = '" + id_user_Seleccionado + "'));";
		String sqlHumedad = "SELECT valor FROM Humedad where id_nevera = (select id_nevera from Subscribe where id_user ='" + id_user_Seleccionado + "') "
				+ "and fecha = (SELECT MAX(fecha) FROM Humedad WHERE id_nevera = (SELECT id_nevera FROM Subscribe WHERE id_user = '" + id_user_Seleccionado + "'));";
		String sqlPuerta = "SELECT valor FROM Puerta where id_nevera = (select id_nevera from Subscribe where id_user ='" + id_user_Seleccionado + "') "
				+ "and fecha = (SELECT MAX(fecha) FROM Puerta WHERE id_nevera = (SELECT id_nevera FROM Subscribe WHERE id_user = '" + id_user_Seleccionado + "'));";
		String sqlWarnings = "SELECT nombre, fecha FROM Notificaciones where id_nevera = (select id_nevera from Subscribe where id_user ='" + id_user_Seleccionado + "');";
		String sqlGraficoTemperatura = "SELECT fecha, valor FROM Temperatura where id_nevera = (select id_nevera from Subscribe where id_user ='" + id_user_Seleccionado + "');";
		int puerta = -999;
		String temperatura = "", humedad = "";
		double valor;
		String warning = "", fecha = "";
		
		XYChart.Series<String, Double> series = new XYChart.Series<>();
        series.setName("Temperatura");
        
		Connection conn = null;
        Statement stmt  = null;
        ResultSet rs    = null;
        try {
             conn = connectBBDD.connect();
             stmt = conn.createStatement();
             rs = stmt.executeQuery(sqlTemperatura);
            while (rs.next()) {
            	temperatura = rs.getString("valor");
            	lblTemp.setText(String.valueOf(temperatura)+ " ºC");
            }
            rs.close();
            stmt.close();
            
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlHumedad);
            while (rs.next()) {
            	humedad = rs.getString("valor");
            	lblHum.setText(humedad + " %");
            }
            rs.close();
            stmt.close();
            
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlPuerta);
            while (rs.next()) {
            	puerta = rs.getInt("valor");
            	if(puerta==0) {
    				lblPuertaTec.setText("Abierta");
    			}else {
    				lblPuertaTec.setText("Cerrada");
    			}
            }
            rs.close();
            stmt.close();
            
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlWarnings);
            while (rs.next()) {
            	warning = rs.getString("nombre");
            	fecha = rs.getString("fecha");
            	listWarnings.getItems().add(warning + ": " + fecha);
            }
            rs.close();
            stmt.close();
            
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlGraficoTemperatura);
            while (rs.next()) {
            	fecha = rs.getString("fecha");
            	valor = rs.getDouble("valor");
            	series.getData().add(new XYChart.Data<>(fecha, valor));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e2) {
            System.out.println(e2.getMessage());
        }
        graficoTemperatura.getData().add(series);
	}
	@FXML
    void abrirChatTecnico(ActionEvent event) {
		if(id_user_Seleccionado==0) {
            showAlert("Error", "Necesita elegir usuario en lista de usuarios", AlertType.ERROR);
		}else {
			try {
				controladorChatTecnico control = new controladorChatTecnico();
				control.setUserIdTecnico(user_id);
				control.setUserId(id_user_Seleccionado);
							
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