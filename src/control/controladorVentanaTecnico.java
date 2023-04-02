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
import java.util.Vector;


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
import model.Usuario;

public class controladorVentanaTecnico {
	public static final Stage stage  = new Stage();
	@FXML
    private ImageView imgDoorOpen;
	@FXML
    private ImageView imgDoorClose;
	@FXML
	private Label lblTimeTecnico;
	@FXML
	private Label lblEstado;
	@FXML
	private Label lblHum;
	@FXML
	private Label lblTemp;
	@FXML
    private ListView listaUsuarios;
	@FXML
	private Button bttnInfoTecnico;
	@FXML
    void mostrarInfoTecnico(ActionEvent event) {
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
		
		imgDoorOpen.setVisible(false);
		imgDoorClose.setVisible(false);
		
		leer_datos();
	}

	public void leer_datos() {
		String sql = "SELECT id, nombre, surname1, surname2 from usuarios where role = 'user';";
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
            	nombreTotal = "" + nombre + " " + surname1 + " " + surname2;
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
	public void selectUser() {/*
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try {
			Reader reader = Files.newBufferedReader(Paths.get("userbase.json"));
			Reader readerFridgedate = Files.newBufferedReader(Paths.get("fridgedate.json"));
			Usuario[] users = new Gson().fromJson(reader, Usuario[].class);
			FridgeDate[] fd = new Gson().fromJson(readerFridgedate, FridgeDate[].class);
			for(int i=0; i<users.length; i++) {
				if(users[i].tipo.equals("user")) {
					us.add(users[i]);
				}
			}
			id = us.get(selectedId).id;
			if(fd[id].estado.equals("abierta")) {
				imgDoorOpen.setVisible(true);
				lblEstado.setText("open");
			}else {
				imgDoorClose.setVisible(true);
				lblEstado.setText("close");
			}
			lblTemp.setText(fd[id].temperatura + "C");
			lblHum.setText(fd[id].humedad + "%");
			reader.close();
			readerFridgedate.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		lblEstado.setText("");
		imgDoorOpen.setVisible(false);
		imgDoorClose.setVisible(false);
		int id = -1;
		Vector<Usuario> us = new Vector<Usuario>();
		int selectedId = listaUsuarios.getSelectionModel().getSelectedIndex();
		String name_surname = (String) listaUsuarios.getSelectionModel().getSelectedItem();
		String sqlTemperatura = "SELECT valor FROM temperatura where id_nevera = (select id_nevera from subscribe where id_user ='" + id + "') "
				+ "and fecha = (SELECT MAX(fecha) FROM temperatura WHERE id_nevera = (SELECT id_nevera FROM subscribe WHERE id_user = '" + id + "'));";
		String sqlHumedad = "SELECT valor FROM humedad where id_nevera = (select id_nevera from subscribe where id_user ='" + id + "') "
				+ "and fecha = (SELECT MAX(fecha) FROM humedad WHERE id_nevera = (SELECT id_nevera FROM subscribe WHERE id_user = '" + id + "'));";
		String sqlPuerta = "SELECT valor FROM puerta where id_nevera = (select id_nevera from subscribe where id_user ='" + id + "') "
				+ "and fecha = (SELECT MAX(fecha) FROM puerta WHERE id_nevera = (SELECT id_nevera FROM subscribe WHERE id_user = '" + id + "'));";
		int temperatura = -999, humedad = -999, puerta = -999;
		
		Connection conn = null;
        Statement stmt  = null;
        ResultSet rs    = null;
        try {
             conn = connectBBDD.connect();
             stmt = conn.createStatement();
             rs = stmt.executeQuery(sqlTemperatura);
            while (rs.next()) {
            	temperatura = rs.getInt("valor");
            	lblTemp.setText(String.valueOf(temperatura));
            }
            rs.close();
            stmt.close();
            
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlHumedad);
            while (rs.next()) {
            	humedad = rs.getInt("valor");
            	lblHum.setText(humedad + "");
            }
            rs.close();
            stmt.close();
            
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlPuerta);
            while (rs.next()) {
            	puerta = rs.getInt("valor");
            	if(puerta==0) {
    				imgDoorOpen.setVisible(true);
    				lblEstado.setText("open");
    			}else {
    				imgDoorClose.setVisible(true);
    				lblEstado.setText("close");
    			}
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e2) {
            System.out.println(e2.getMessage());
        }
	}
}