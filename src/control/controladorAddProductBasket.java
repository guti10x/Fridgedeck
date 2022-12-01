package control;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import application.FridgeDate;
import application.ListaCompras;
import application.Productos;
import application.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class controladorAddProductBasket {
	@FXML
    private TextField tfNombre;
	
	@FXML
    private TextField tfCantidad;

	@FXML
    private Button btnAddProduct;
	
	public static Stage stage  = new Stage();
	
	@FXML
    void addProduct(ActionEvent e) {
		JFrame jFrame = new JFrame();
        
        String name = tfNombre.getText().toString();
        String count = tfCantidad.getText().toString();
        
        if(tfNombre.equals("")||count.equals("")) {
        	JOptionPane.showMessageDialog(jFrame, "Necesita rellenar todos los campos");
        }else {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            try {
    			Reader reader = Files.newBufferedReader(Paths.get("fridgedate.json"));
    			FridgeDate[] datos = new Gson().fromJson(reader, FridgeDate[].class);
    			
    			List<ListaCompras> listaActualizada = new ArrayList<>();
    			for(int i = 0; i < datos[controladorLogin.user_id-1].lista_compras.size(); i++) {
    				//System.out.println("lista: " + listaActualizada);
    				//listaActualizada.add(new ListaCompras(datos[controladorLogin.user_id-1].lista_compras.get(i).name, datos[controladorLogin.user_id-1].productos.get(i).cantidad)); 
    				listaActualizada.add(new ListaCompras(name, count));
    			}
    			listaActualizada.add(new ListaCompras(name, count));
    			System.out.println("Lista Act:" + listaActualizada.toArray());
    			reader.close();
    			
    			Writer writer = new FileWriter("fridgedate.json");
    			datos[controladorLogin.user_id-1].lista_compras.clear();
    			datos[controladorLogin.user_id-1].lista_compras.addAll(listaActualizada);
    			new Gson().toJson(datos, writer);
    			writer.close();
    			
    			final Node source = (Node) e.getSource();
    			stage = (Stage) source.getScene().getWindow();
    			stage.close();
    			
    			controladorVentanaUsuario cvu = new controladorVentanaUsuario();
    			//cvu.initialize();
    			
    		} catch (IOException e2) {
    			// TODO Auto-generated catch block
    			e2.printStackTrace();
    		}  
        }
	}
}