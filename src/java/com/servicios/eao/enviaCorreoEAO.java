
package com.servicios.eao;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;

import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author AMORAN
 */
@Stateless
@LocalBean

public class enviaCorreoEAO 
{
   
    private Properties mailProperties;
    private String Username = "";
    private String PassWord = "";
    
    public void enviaMail(String usuario,String correo,String plantilla,String referencia)
    {
        if(mailProperties == null) 
        {
            System.out.println("Ingreso");
            mailProperties = new Properties();
            
            boolean sendGmail   = correo.contains("gmail");
            boolean sendHotmail = correo.contains("hotmail");
            
            try {
                
                if(sendGmail)
                {
                    System.out.println("Envia Correo por Gmail");
                    mailProperties.load(enviaCorreoEAO.class.getClassLoader().getResourceAsStream("gmail.properties"));
                }
                else
                {
                    if(sendHotmail)
                    {
                        System.out.println("Envia Correo por Hotmail");
                        mailProperties.load(enviaCorreoEAO.class.getClassLoader().getResourceAsStream("hotmail.properties"));
                    }
                }
                
                
            } catch (IOException ex) 
            {
                Logger.getLogger(enviaCorreoEAO.class.getName()).log(Level.SEVERE, null, ex);
            }


            System.out.println("Enviando Mensaje!");
            SendMail(mailProperties , plantilla , correo, referencia);
        }
    }
    
    public void SendMail(Properties props , String Mensage , String To , String Subject) 
    {

        Username    =   props.getProperty("mail.smtp.user");
        PassWord    =   props.getProperty("mail.smtp.password");
        System.out.println("Usuario:    "+Username);
        System.out.println("Password:    "+PassWord);

        Session session = Session.getInstance(props,
        new javax.mail.Authenticator() 
        {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Username, PassWord);
            }
        });
        System.out.println("Sesion: "+session);
        try {

            Message message = new MimeMessage(session);
            System.out.println("message: "+message.getContentType());
            message.setFrom(new InternetAddress(Username));
            message.setRecipients(Message.RecipientType.TO,
            InternetAddress.parse(To));
            message.setSubject(Subject);
            message.setText(Mensage);

            Transport.send(message);
       
            System.out.println("Su mensaje ha sido enviado");
            
        } catch (MessagingException e) 
        {
            System.out.println("No tiene conexion a Internet y no se puede enviar correo");
            throw new RuntimeException(e);
        }
    }
}
