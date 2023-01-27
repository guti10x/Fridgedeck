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
import java.util.Vector;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
import model.FridgeDate;
import model.ListaCompras;
import model.ListaProductos;
import model.Usuario;

public class controladorVentanaRepartidor {
	public static final Stage stage  = new Stage();
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
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try {
			Reader reader = Files.newBufferedReader(Paths.get("userbase.json"));
			//Reader readerListaCompras = Files.newBufferedReader(Paths.get("listas_compras.json"));
			Usuario[] users = new Gson().fromJson(reader, Usuario[].class);
			
			for(int i=0; i<users.length; i++) {
				if(users[i].tipo.equals("user")) {
					lwCL.getItems().add("- " + users[i].name_surname);
				}
			}
			reader.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public void selectClient() {
		lwD.getItems().clear();
		lwCS.getItems().clear();
		int id = -1;
		Vector<Usuario> us = new Vector<Usuario>();
		int selectedId = lwCL.getSelectionModel().getSelectedIndex();
		System.out.println(selectedId);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try {
			Reader reader = Files.newBufferedReader(Paths.get("userbase.json"));
			Reader readerListaCompras = Files.newBufferedReader(Paths.get("listas_compras.json"));
			Reader readerListaProductos = Files.newBufferedReader(Paths.get("listas_productos.json"));
			Usuario[] users = new Gson().fromJson(reader, Usuario[].class);
			ListaCompras[] listaComp = new Gson().fromJson(readerListaCompras, ListaCompras[].class);
			ListaProductos[] listaProd = new Gson().fromJson(readerListaProductos, ListaProductos[].class);
			for(int i=0; i<users.length; i++) {
				if(users[i].tipo.equals("user")) {
					us.add(users[i]);
				}
			}
			id = us.get(selectedId).id;
			System.out.println(id);
			for(int i=0; i<listaComp.length; i++) {
				if(listaComp[i].id_user==id) {
					for(int j=0; j<listaComp[i].lista_compras.size(); j++) {
						System.out.println("Size lc" + listaComp[i].lista_compras.size());
						lwD.getItems().add("- " + listaComp[i].lista_compras.get(j).name + ", "
								+ listaComp[i].lista_compras.get(j).cantidad);
					}
				}
			}
			for(int i=0; i<listaProd.length; i++) {
				if(listaProd[i].id_user==id) {
					for(int j=0; j<listaProd[i].lista_productos.size(); j++) {
						System.out.println("Size lc" + listaProd[i].lista_productos.size());
						lwCS.getItems().add("- " + listaProd[i].lista_productos.get(j).name + ", "
								+ listaProd[i].lista_productos.get(j).cantidad);
					}
				}
			}

			reader.close();
			readerListaCompras.close();
			readerListaProductos.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
