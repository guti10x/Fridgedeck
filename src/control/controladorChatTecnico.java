package control;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import application.connectBBDD;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class controladorChatTecnico {
	@FXML
    private TextArea txtChat;
	
	@FXML
    private TextField txtTextoParaEnviar;
	
	@FXML
    private Button btnEnviarChat;
		
	public void initialize() throws InterruptedException{
		Timer timer = new Timer();
	    TimerTask task = new TimerTask() {
	        @Override
	        public void run() {
	        	actualizarChat();
	        }
	    };
	    timer.scheduleAtFixedRate(task, 0, 3000);
	}
	
	int leerId() {
		int user_id = 0;
		try {
		      File myObj = new File("user_id.txt");
		      Scanner myReader = new Scanner(myObj);
		      while (myReader.hasNextLine()) {
		        String id = myReader.nextLine();
		        user_id = Integer.valueOf(id);
		      }
		      myReader.close();
	    } catch (FileNotFoundException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	    }
		return user_id;
	}
	
	@FXML
	void enviarTextoAlChat(ActionEvent e) {
	    int sender_id = leerId();
	    int receiver_id = controladorVentanaTecnico.idUser;
	    String textParaEnviar = txtTextoParaEnviar.getText();

	    try {
	        String sql = "INSERT INTO Messages (sender_id, receiver_id, text) VALUES (?, ?, ?)";

	        Connection conn = connectBBDD.connect();
	        PreparedStatement statement = conn.prepareStatement(sql);

	        statement.setInt(1, sender_id);
	        statement.setInt(2, receiver_id);
	        statement.setString(3, textParaEnviar);

	        statement.executeUpdate();

	        conn.close();
	    } catch (SQLException e2) {
	        System.out.println(e2.getMessage());
	    }
	    actualizarChat();
	    txtTextoParaEnviar.clear();
	}
	
	@FXML
	void actualizarChat() {
		txtChat.clear();
	    try {
	        String sql = "SELECT * FROM Messages WHERE sender_id = ? or receiver_id = ? ORDER BY send_time DESC";

	        Connection conn = connectBBDD.connect();
	        PreparedStatement statement = conn.prepareStatement(sql);

	        statement.setInt(1, controladorVentanaTecnico.idUser);
	        statement.setInt(2, controladorVentanaTecnico.idUser);
	        ResultSet resultSet = statement.executeQuery();

	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	        while (resultSet.next()) {
	            String text = resultSet.getString("text");
	            int sender_id = resultSet.getInt("sender_id");
	            int receiver_id = resultSet.getInt("receiver_id");
	            LocalDateTime send_time = resultSet.getTimestamp("send_time").toLocalDateTime();
	            
	            String formattedTime = send_time.format(formatter);System.out.println(text);
	            if(sender_id == 7) {
	            	txtChat.appendText(formattedTime + " | Tecnico: " + text + "\n");
	            }else {
	            	txtChat.appendText(formattedTime + " | User: " + text + "\n");
	            }
	        }
	        conn.close();
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	}
}