package control;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class SendEmail {
	
public static void enviarCorreo(String login, String password, String correo) throws MessagingException{
		
		String host = "smtp.gmail.com";
		
		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
		
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("fridgedeckp@gmail.com", "libviwhcfdeckwux");

            }

        });
		        
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress("fridgedeckp@gmail.com.com"));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(correo));

            // Set Subject: header field
            message.setSubject("Bienvenido a Fridgedeck!");

            // Now set the actual message
            message.setText("Gracias por registrarte en la aplicaci�n.\nLe recordamos sus datos de registro:\n\tUsuario: " + login + "\n\tContrase�a: " + password +
            		"\nUn abrazo de parte del equipo Fridgedeck");

            // Send message
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
	}
}