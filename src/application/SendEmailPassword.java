package application;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class SendEmailPassword {
	
public static void enviarCorreo(String nombre, String password, String correo) throws MessagingException{
		
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
            message.setSubject("Recuperación de contraseña");

            // Now set the actual message
            message.setText("Buenas días " + nombre + ".\n\nContraseña: " + password + "\n\nUn saludo.");

            // Send message
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
	}
}