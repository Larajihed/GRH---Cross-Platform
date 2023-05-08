/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package COM.HRSTORMDESKTOP.services.Recrutement;

import COM.HRSTORMDESKTOP.controllers.Recrutement.AjoutCondidatFXMLController;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author usoum
 */
public class SendEmailMailtrap {
    
    private static String Username = "a7ebf35d092cb7";
    private static String Password = "e0ee29bbd4728b";
    
    public static void getSendEmail() throws UnknownHostException {
        
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.protokls", "TLSv1.2");
        properties.put("mail.smtp.host", "sandbox.smtp.mailtrap.io");
        properties.put("mail.smtp.port", "2525");
        
        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Username, Password);
            }
        });
        
        session.setDebug(true);
        
        try {
            
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("hrstorm@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(AjoutCondidatFXMLController.getEmailCan()));
            message.setSubject("Confirmation de candidature");
            message.setText("votre candidature sera traité");

            Transport.send(message);
        } catch (Exception e) {
            e.getMessage();
        }
    }
    
    
}
