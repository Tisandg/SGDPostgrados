package co.unicauca.proyectobase.utilidades;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
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
        System.out.println("EN REDIRECCIONAR A: " + pagina);
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extcontext = context.getExternalContext();
        extcontext.getFlash().setKeepMessages(true);
        try {
            System.out.println("REDIRIGUIENDO EN TRY");
            extcontext.redirect(pagina);
        } catch (IOException ex) {
            System.out.println("Errror al redireccionar. err: " + ex.getMessage());
            Logger.getLogger("Error al redireccionar a " + pagina);
        }
    }
    
    /***
     * Metodo que envia las notificaciones de correo electronico al estudiante
     * que registro la publicacion y al coordinador del doctorado. Para el envio
     * utiliza la funcion enviarCorreo despues de que se crea el mensaje a enviar
     * @param destino
     * @param autor
     * @param tipoPublicacion
     * @param tiempo 
     */
    public static void correoRegistroPublicaciones(String destino, String autor, String tipoPublicacion, String tiempo){
        String asunto = "Notificación registro de documento";
        String mensajeCoordinador = "Cordial saludo.\n" + "El estudiante "+autor+" acaba de registrar un documento del tipo " 
                                + tipoPublicacion+ " en la siguiente fecha y hora: " + tiempo;
        String mensajeEstudiante = "Estimado "+autor + ".\n" + "Se acaba de registrar un documento del tipo " 
                                + tipoPublicacion+ " en la siguiente fecha y hora: " + tiempo;
        //Correo para el estudiante
        enviarCorreo(destino,asunto, mensajeEstudiante);
        //Correo para el coordinador
        enviarCorreo("posgradoselectunic@gmail.com",asunto, mensajeCoordinador);
    }

    /***
     * Funcion utilizada para enviar la notificaion al correo del destinatario,
     * con su respectivo asunto y mensaje.
     * @param destinatario
     * @param asunto
     * @param mensaje
     * @return true si se envio el mensaje
     */
    public static boolean enviarCorreo(String destinatario, String asunto, String mensaje) 
    {
        String de = "posgradoselectunic@gmail.com";
        String clave = "posgrados22";
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
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(de, clave);
                    }
                });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(de));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(asunto);
            message.setText(mensaje);
            Transport.send(message);
            resultado = true;
            System.out.println("========== CORREO ENVIADO CON ÉXITO ============");
        }
        catch(Exception e)
        {
            System.out.println("========== ERROR AL ENVIAR CORREO ============ \n" + e.getMessage());
        }
        
        return resultado;
    }
    
    public static String sha256(String cadena)
    {
        StringBuilder sb= new StringBuilder();
        try
        {
            MessageDigest md= MessageDigest.getInstance("SHA-256");
            md.update(cadena.getBytes());
            
            byte[] mb=md.digest();
            for(int i=0; i< mb.length;i++)
            {
                sb.append(Integer.toString((mb[i] & 0xff)+ 0x100,16).substring(1));
            }
            
        }catch(NoSuchAlgorithmException ex)
        {
            System.out.println("Error-Utilidades -- "+ex.getMessage());
        }
        return sb.toString();
    }
    
     public static int[] getListaAnios() {
        Calendar cal = Calendar.getInstance();
        int anioInicio = 1999;
        int anioActual = cal.get(Calendar.YEAR);
        int[] vectorA = new int[anioActual - anioInicio];
        for (int i = 0; i < vectorA.length; i++) {
            vectorA[i] = anioActual--;
        }
        return vectorA;
    }

}
