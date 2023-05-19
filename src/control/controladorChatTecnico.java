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
	private int user_id_Tecnico;
	private int user_id;
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
	
	@FXML
	void enviarTextoAlChat(ActionEvent e) {
		int sender_id = user_id_Tecnico;
	    int receiver_id = user_id;
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
	        String sql = "SELECT * FROM Messages WHERE sender_id = ? OR receiver_id = ? ORDER BY send_time DESC";

	        Connection conn = connectBBDD.connect();
	        PreparedStatement statement = conn.prepareStatement(sql);

	        statement.setInt(1, user_id);
	        statement.setInt(2, user_id);
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
	public void setUserIdTecnico(int user_id_Tecnico) {
	    this.user_id_Tecnico = user_id_Tecnico;
	    System.out.println("ID T: " + user_id_Tecnico);
	}
	public void setUserId(int user_id) {
	    this.user_id = user_id;
	    System.out.println("ID U: " + user_id);
	}
}