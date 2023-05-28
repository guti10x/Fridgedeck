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

public class controladorChatUser {
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
	        	actualizarChat(user_id);
	        }
	    };
	    timer.scheduleAtFixedRate(task, 0, 3000);
	}
	
	@FXML
	void enviarTextoAlChat(ActionEvent e) {
	    int sender_id = user_id;
	    int receiver_id = 7;
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
	    actualizarChat(sender_id);
	    txtTextoParaEnviar.clear();
	}
	
	@FXML
	void actualizarChat(int sender_id) {
		txtChat.clear();
	    try {
	        String sql = "SELECT * FROM Messages WHERE sender_id = ? OR receiver_id = ? ORDER BY send_time DESC";

	        Connection conn = connectBBDD.connect();
	        PreparedStatement statement = conn.prepareStatement(sql);

	        statement.setInt(1, sender_id);
	        statement.setInt(2, sender_id);

	        ResultSet resultSet = statement.executeQuery();

	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	        while (resultSet.next()) {
	            String text = resultSet.getString("text");
	            int receiver_id = resultSet.getInt("receiver_id");
	            LocalDateTime send_time = resultSet.getTimestamp("send_time").toLocalDateTime();
	            
	            String formattedTime = send_time.format(formatter);
	            if(receiver_id == 7) {
	            	txtChat.appendText(formattedTime + " | User: " + text + "\n");
	            }else {
	            	txtChat.appendText(formattedTime + " | Tecnico: " + text + "\n");
	            }
	        }
	        conn.close();
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	}
	public void setUserId(int user_id) {
	    this.user_id = user_id;
	}
}