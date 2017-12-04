package co.unicauca.proyectobase.utilidades;

import co.unicauca.proyectobase.entidades.Estudiante;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Esta clase contiene los metodos utilizados para la la redireccion de las
 * paginas del sistema y el envio de las notificacion por correo.
 * @author Equipo 2017-II
 */
public class Utilidades {

    /**
     * Metodo utilizado para redireccionar a las distintas paginas del sistema.
     * Recibe un String que contiene la url de la pagina a la cual se va a 
     * redirigir.
     * @param pagina 
     */
    public static void redireccionar(String pagina) {
        System.out.println("Redireccionando a: " + pagina);
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extcontext = context.getExternalContext();
        extcontext.getFlash().setKeepMessages(true);
        try {            
            extcontext.redirect(pagina);
        } catch (IOException ex) {
            System.out.println("Errror al redireccionar. err: " + ex.getMessage());
            Logger.getLogger(Utilidades.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /***
     * Metodo que envia las notificaciones de correo electronico al estudiante
     * que registro la publicacion y al coordinador del doctorado. Para el envio
     * utiliza la funcion enviarCorreo, despues de que se crea el mensaje a enviar
     * @param destino
     * @param autor
     * @param tipoPublicacion
     * @param tiempo 
     */
    public static void correoRegistroPublicaciones(String destino, String autor, String tipoPublicacion, String tiempo){
        String asunto = "Notificación registro de documento";
        String mensajeCoordinador = "Cordial saludo.\n" + "El estudiante "+autor+" acaba de registrar un documento del tipo '" 
                                + tipoPublicacion+ "' en la siguiente fecha y hora: " + tiempo;
        String mensajeEstudiante = "Estimado "+autor + ".\n" + "Se acaba de registrar un documento del tipo " 
                                + tipoPublicacion+ " en la siguiente fecha y hora: " + tiempo;
        //Correo para el estudiante
        enviarCorreo(destino,asunto, mensajeEstudiante);
        //Correo para el coordinador
        enviarCorreo("posgradoselectunic@gmail.com",asunto, mensajeCoordinador);
    }
    /**
     * Envio de notificacion por correo al estudiante cuando se haya cambiado
     * el estado de visado de una documentacion en especifico.
     * @param aprobado
     * @param estudiante
     * @param nombreActividad
     * @param observaciones 
     */
    public static void correoVisadoPublicacion(boolean aprobado, Estudiante estudiante,String nombreActividad,String observaciones ){
        String asunto = "Notificación revisión de documentos DCE";
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("Estimado estudiante ").append(estudiante.getNombreCompleto()).append(".");
        if(aprobado){
            mensaje.append("\n\nSe acaba de APROBAR el documento '").append(nombreActividad)
                    .append("' que fue registrado previamente en el sistema de Doctorado en Ciencias de la Electrónica.")
                    .append("\nNúmero de creditos actuales: ").append(estudiante.getEstCreditos());
                    
        }else{
            mensaje.append("\n\nSe acaba de APROBAR el documento '").append(nombreActividad)
                    .append("' que fue registrado previamente en el sistema de Doctorado en Ciencias de la Electrónica.");
            if (!observaciones.equals("")) {
                mensaje.append("\n Observaciones: ").append(observaciones);
            }
        }
        mensaje.append("\n\n\nServicio notificación DCE.");
        enviarCorreo(estudiante.getEstCorreo(), asunto, mensaje.toString());
    }
    
    
    /**
     * Metodo para enviar el correo de notificacion al estudiante para informar
     * que se le ha creado una cuenta en el sistema con sus datos.
     * @param estudiante 
     */
    public static void correoRegistroEstudiante(Estudiante estudiante){
        String destinatario = estudiante.getEstCorreo();
        String asunto = "Notificación registro de usuario DCE";
        String mensaje = "Estimado estudiante.\n"
                + "Se acaba de crear una cuenta de estudiante con sus datos en el sistema de Doctorado en Ciencias de la Electrónica.\n"
                + "Recuerde que a partir de la fecha puede hacer uso del sistema ingresando la siguiente información:"
                + "\nNombre Usuario: " + estudiante.getEstUsuario()
                + "\nContraseña: " + estudiante.getEstCodigo() 
                + "\n\nServicio notificación DCE.";
        
        /*Se envia el correo al estudiante*/
        enviarCorreo(destinatario,asunto,mensaje);
    }
    
    /**
     * Metodo para enviar la notificaion al correo electronico del estudiante
     * informando los cambios realizados en el perfil de estudiante.
     * @param estudiante 
     */
    public static void correoEdicionPerfilEstudiante(Estudiante estudiante){
        String destinatario = estudiante.getEstCorreo();
        String[] username = estudiante.getEstCorreo().split("@");
        String asunto = "Notificación edición de datos de usuario DCE";
        String mensaje = "Estimado estudiante.\n\nSe acaba de editar información respecto a sus datos personales."
                + "\n\nDatos actuales:"
                + "\nCódigo: "+estudiante.getEstCodigo()
                +"\nNombres: "+estudiante.getEstNombre()
                +"\nApellidos: "+estudiante.getEstApellido()
                +"\nCorreo electrónico: "+estudiante.getEstCorreo()
                +"\nCohorte: "+estudiante.getEstCohorte()
                +"\nNombre tutor: "+estudiante.getEstTutor() 
                +"\nSemestre: "+estudiante.getEstSemestre()
                +"\nEstado: "+estudiante.getEstEstado()
                +"\n\nRecuerde que a partir de la fecha puede hacer uso del sistema, ingresando la siguiente información: " 
                +"\nNombre Usuario: " + username[0]+ "\nContraseña: "+ estudiante.getEstCodigo()
                +"\n\nServicio notificación DCE.";
        enviarCorreo(destinatario, asunto, mensaje);
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
            System.out.println("========== ERROR AL ENVIAR CORREO ============ \n"+ e.getMessage());
        }
        return resultado;
    }
    
    /**
     * Metodo para cifrar una cadena con el algoritmo SHA-256
     * @param cadena
     * @return 
     */
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
