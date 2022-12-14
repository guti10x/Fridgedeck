package control;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingUtilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import application.FridgeDate;
import application.ListaCompras;
import application.Usuario;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    private Button btnAddProductBasket;
	
	public static final Stage stage  = new Stage();
	
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
	void leer_datos() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try {
			Reader reader = Files.newBufferedReader(Paths.get("fridgedate.json"));
			Reader readerListaCompras = Files.newBufferedReader(Paths.get("listas_compras.json"));
			FridgeDate[] datos = new Gson().fromJson(reader, FridgeDate[].class);
			ListaCompras[] listaComp = new Gson().fromJson(readerListaCompras, ListaCompras[].class);
			lblTemperatura.setText(String.valueOf(datos[controladorLogin.user_id-1].temperatura) + "C");
			lblHumedad.setText(String.valueOf(datos[controladorLogin.user_id-1].humedad) + "%");
			if((datos[controladorLogin.user_id-1].estado).equals("abierta")) {
				imgPuertaAbierta.setVisible(true);
				lblPuerta.setText("open");
			}else {
				imgPuertaCerrada.setVisible(true);
				lblPuerta.setText("close");
			}
			for(int i=0; i<listaComp.length; i++) {
				System.out.println(listaComp[i].lista_compras.size());
				if(listaComp[i].id_user==controladorLogin.user_id) {
					for(int j=0; j<listaComp[i].lista_compras.size(); j++) {
						System.out.println(listaComp[i].lista_compras.size());
						listaCompras.getItems().add("- " + listaComp[i].lista_compras.get(j).name + ", "
								+ listaComp[i].lista_compras.get(j).cantidad);
					}
				}
			}
			reader.close();
			readerListaCompras.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	@FXML
    void openAddProductBasket(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/add_product_basket.fxml"));
			
			controladorAddProductBasket control = new controladorAddProductBasket();
			
			loader.setController(control);
	
			Parent root = loader.load();
			
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