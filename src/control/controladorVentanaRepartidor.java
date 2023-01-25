package control;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class controladorVentanaRepartidor {
	public static final Stage stage  = new Stage();
	@FXML
	private Button bttnInfoRepartidor;
	@FXML
	private Label lblTimeRepatridor;
	 @FXML
	    void mostrarInfoRepartidor(ActionEvent event) {
			 try {
				 FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/view/ventana_Informacion_usuario.fxml"));
					
					controladorVentanaInformacionUsuario control2 = new controladorVentanaInformacionUsuario();
					
					loader2.setController(control2);
					
					Parent root2 = loader2.load();
					
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
		// TODO Auto-generated method stub
		
	}
}
