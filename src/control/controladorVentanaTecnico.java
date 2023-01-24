package control;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.FridgeDate;
import model.ListaCompras;
import model.Usuario;

public class controladorVentanaTecnico {
	@FXML
	private Label lblTimeTecnico;
	
	@FXML
    private ListView listaUsuarios;
	
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
		
		leer_datos();
	}

	public void leer_datos() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try {
			Reader reader = Files.newBufferedReader(Paths.get("userbase.json"));
			//Reader readerListaCompras = Files.newBufferedReader(Paths.get("listas_compras.json"));
			Usuario[] users = new Gson().fromJson(reader, Usuario[].class);
			
			for(int i=0; i<users.length; i++) {
				if(users[i].tipo.equals("user")) {
					listaUsuarios.getItems().add("- " + users[i].name_surname);
				}
			}
			reader.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}