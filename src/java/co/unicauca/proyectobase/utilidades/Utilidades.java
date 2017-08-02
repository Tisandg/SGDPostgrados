package co.unicauca.proyectobase.utilidades;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Utilidades {

    public static void redireccionar(String pagina) {

        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extcontext = context.getExternalContext();
        extcontext.getFlash().setKeepMessages(true);
        try {
            extcontext.redirect(pagina);
        } catch (IOException ex) {
            Logger.getLogger("Error al redireccionar a " + pagina);
        }
    }

    public static boolean enviarCorreo(String destinatario, String asunto, String mensaje) 
    {
        // String de = "elcorreodelprofe@gmail.com";
        // String clave = "lacontraseñadelcorreodelprofe";
        String de = "posgradoselectunic@gmail.com";
        String clave = "posgrados22";
        String para = destinatario;

        
        boolean resultado = false;

        try {
            Properties prop = new Properties();
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.port", 587);
            
            Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(de, clave);
                    }
                });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(de));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(para));
            message.setSubject(asunto);
            message.setText(mensaje);
            Transport.send(message);
            resultado = true;
            System.out.println("========== CORREO ENVIADO CON ÉXITO ============");
        }
        catch(Exception e)
        {
            System.out.println("========== ERROR AL ENVIAR CORREO ============ " + e.getMessage());
        }
        
        return resultado;
    }
}
