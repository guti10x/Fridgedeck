package control;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingUtilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import application.FridgeDate;
import application.Usuario;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;

public class controladorVentanaUsuario {
	@FXML
    private Label lblTime;
	
	@FXML
    private Label lblTemperatura;
	
	@FXML
    private Label lblHumedad;
	
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

	private void leer_datos() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try {
			Reader reader = Files.newBufferedReader(Paths.get("fridgedate.json"));
			FridgeDate[] datos = new Gson().fromJson(reader, FridgeDate[].class);
			lblTemperatura.setText(String.valueOf(datos[controladorLogin.user_id-1].temperatura) + "C");
			lblHumedad.setText(String.valueOf(datos[controladorLogin.user_id-1].humedad) + "%");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}