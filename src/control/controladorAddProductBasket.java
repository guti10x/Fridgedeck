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
import model.Compras;
import model.FridgeDate;
import model.ListaCompras;
import model.Productos;
import model.Usuario;

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
            	
    			Reader reader = Files.newBufferedReader(Paths.get("listas_compras.json"));
    			ListaCompras[] lcomp = new Gson().fromJson(reader, ListaCompras[].class);
    			/*
    			List<Compras> listaActualizada = new ArrayList<>();
    			for(int i = 0; i < lcomp.length; i++) {
    				//System.out.println("lista: " + listaActualizada);
    				listaActualizada.add(new Compras(lcomp[controladorLogin.user_id-1].lista_compras.get(i).name, lcomp[controladorLogin.user_id-1].lista_compras.get(i).cantidad)); 
    				
    			}
    			listaActualizada.add(new Compras(name, count));
    			for(int i = 0; i < lcomp.length+1; i++) {
    				System.out.println(listaActualizada.get(i).name); 
    			}
    			*/
    			reader.close();
    			
    			Writer writer = new FileWriter("listas_compras.json");
    			//lcomp[controladorLogin.user_id-1].lista_compras.clear();
    			lcomp[controladorDatos.user_id].lista_compras.add(new Compras(name, count));
    			new Gson().toJson(lcomp, writer);
    			writer.close();
    			
    			final Node source = (Node) e.getSource();
    		    final Stage stage = (Stage) source.getScene().getWindow();
    		    stage.close();
    			
    			controladorVentanaUsuario cvu = new controladorVentanaUsuario();
    			cvu.leer_datos();
    			
    		} catch (IOException e2) {
    			// TODO Auto-generated catch block
    			e2.printStackTrace();
    		}  
        }
	}
}