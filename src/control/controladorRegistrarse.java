package control;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.mail.MessagingException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;

import application.connectBBDD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Usuario;

public class controladorRegistrarse {
	@FXML
    private Button btnRegistrarUsuario;
	
	@FXML
    private TextField tfUsername;
	
	@FXML
    private TextField tfNombre;
	
	@FXML
    private TextField tfSurname1;
	
	@FXML
    private TextField tfSurname2;
	
	@FXML
    private TextField tfEmail;
	
	@FXML
    private PasswordField pfPassword;
	
	@FXML
    private DatePicker dpBirthday;
	
	@FXML
    private TextField tfCreditCard;
	
	@FXML
    private TextField tfTelephone;
	
	@FXML
    private ComboBox cmbRole;
	
	public void initialize() throws InterruptedException{
		cmbRole.getItems().addAll("Usuario", "Tecnico", "Repartidor");
	    cmbRole.setValue("Usuario");
	}
		
	@FXML
    void registrarUsuario(ActionEvent e) {
		JFrame jFrame = new JFrame();
		String username = "", nombre = "", surname1 = "", surname2 = "", email = "", password = "", role = "", bdateString = "";
		LocalDate bdate = dpBirthday.getValue();
		Date bdateSql = null;
		long creditCard = 0;
		int telephone = 0;
		if(cmbRole.getValue()==null) {
			JOptionPane.showMessageDialog(jFrame, "Necesita elegir role");
		}else {	
			username = tfUsername.getText().toString();
			nombre = tfNombre.getText().toString();
			surname1 = tfSurname1.getText().toString();
			surname2 = tfSurname2.getText().toString();
            email = tfEmail.getText().toString();
            password = pfPassword.getText().toString();
            bdateSql = Date.valueOf(bdate);
            creditCard = Long.parseLong(tfCreditCard.getText().toString());
            telephone = Integer.parseInt(tfTelephone.getText());
            System.out.println(bdate);
        	if(cmbRole.getValue().equals("Usuario")) {
        		role = "user";
        		//address = tfAddress.getText().toString();
        		//tfAddress.setDisable(false);
        	}if(cmbRole.getValue().equals("Tecnico")) {
        		role = "t�cnico";
        		//address = "-";
        		//tfAddress.setDisable(true);
        	}if(cmbRole.getValue().equals("Repartidor")) {
        		role = "repartidor";
        		//address = "-";
        		//tfAddress.setDisable(true);
        	}
		}
            
        if(username.equals("")||email.equals("")||password.equals("")||
        		nombre.equals("")||surname1.equals("")||surname2.equals("")) {
        	JOptionPane.showMessageDialog(jFrame, "Necesita rellenar todos los campos");
        }else {
            try {
            	String sql = "INSERT INTO Usuarios (role, username, nombre, surname1, surname2, email, password, birthdate, credit_card, telephone_number)"
            			+ "VALUES ('" + role + "', '" + username + "', '" + nombre + "', '" + surname1 + "', '" + surname2 + "', '" + email + "', '" + password + "', '" + bdateSql + "', '" + creditCard + "', '" + telephone + "')";
            	System.out.println(sql);
    			/*boolean checkUser = true;
    			for(int i=0; i<usersArray.length; i++) {
    	
    					if(usersArray[i].username.equals(username)) {
        					JOptionPane.showMessageDialog(jFrame, "Username ya existe");
        					checkUser=false;
        				}
        				if(usersArray[i].email.equals(email)) {
        					JOptionPane.showMessageDialog(jFrame, "Existe usuario con este correo");
        					checkUser=false;
        				}
    			}
    			*/

    			//if(checkUser==true) {
            		Connection conn = connectBBDD.connect();
                    Statement stmt  = conn.createStatement();
                    stmt.executeUpdate(sql);
                    conn.close();
               } catch (SQLException e2) {
                   System.out.println(e2.getMessage());
               }

				JOptionPane.showMessageDialog(jFrame, "Has registrado");
	    			/*
				try {
						SendEmail.enviarCorreo(username, password, email);
					} catch (MessagingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}*/
    			//}	
  
				/*
            try {
    			SendEmail.enviarCorreo(username, password, email);
    		} catch (MessagingException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}*/
            	
        }
	}
}