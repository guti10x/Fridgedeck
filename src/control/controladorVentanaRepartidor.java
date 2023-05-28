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
	private Label lblTimeRepatridor;
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
						        lblTimeRepatridor.setText(time);

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
		
		String id_user = (String) lwCL.getSelectionModel().getSelectedItem();
		id_user = id_user.substring(id_user.indexOf(':')+1);
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
	public void setUserId(int user_id) {
	    this.user_id = user_id;
	}
}
