package co.unicauca.proyectobase.validadores;

import co.unicauca.proyectobase.dao.EstudianteFacade;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Clase que permite hacer las validaciones para el correo electrónico del estudiante.
 * Esta clase es usada para el registro Estudiante.
 */

@FacesValidator(value="validadorCorreo")
public class ValidadorCorreo implements Validator 
{
    @EJB
    private EstudianteFacade dao;
    
    private String expCorreo = "([_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,}))";

    public String getExpCorreo() {
        return expCorreo;
    }

    public void setExpCorreo(String expCorreo) {
        this.expCorreo = expCorreo;
    }
    
    /**
     * Método que es implementado de la clase Validator para realizar las validaciones del objeto value
     * que en este caso se obtiene el correo electrónico desde la vista de registro Estudiante
     * y es transformado en una cadena (String)
     * @param context
     * @param component
     * @param value
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        String correo = String.valueOf(value);

        if(correo.length() == 0) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El correo del estudiante es obligatorio.");
            throw new ValidatorException(msg);  
        }

        if((correo.length()<10) ||(correo.length()>30)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El correo debe contener de 10 a 30 caracteres.");
            throw new ValidatorException(msg);  
        }
        
        Pattern patron = Pattern.compile(expCorreo);
        Matcher validar = patron.matcher(correo);
        
        if(!validar.find())
        {
            FacesMessage msg= new FacesMessage(FacesMessage.SEVERITY_ERROR, "","Formato de correo invalido.");
            throw new ValidatorException(msg);
        }
        
    }
    
    /**
     * valida que el dominio del correo sea correcto
     * @param dominio
     * @return 
     */
    public boolean validarDominio(String dominio) {
        return !(!dominio.equals("gmail.com") && 
                 !dominio.equals("unicauca.edu.co") &&
                 !dominio.equals("hotmail.com"));
    }
    
    /**
     * valida que el correo no empieze por un caracter especial
     * @param correo
     * @return 
     */
    public boolean validarInicioCorreo(String correo) {
        Pattern p = Pattern.compile("^[a-z]");
        Matcher m = p.matcher(correo);
        return m.find();
    }
    
    /**
     * valida que no empieze por www.
     * @param correo
     * @return 
     */
    public boolean validarInicioCorreo2(String correo) {
        Pattern p = Pattern.compile("^www.");
        Matcher m = p.matcher(correo);
        return m.find();
    }
    
    /**
     * valida que no contenga caracteres prohibidos
     * @param correo
     * @return 
     */
    public boolean validarCaracteresEspeciales(String correo) {
        Pattern p = Pattern.compile("^[A-Za-z0-9.@_-~#]+$");
        Matcher m = p.matcher(correo);
        
        return m.matches();
    }
    
    /**
     * valida si el correo ingresado tiene el caracter @
     * @param texto
     * @return 
     */
    public boolean siTieneArroba(String texto) {
        Pattern p = Pattern.compile("@");
        Matcher m = p.matcher(texto); 
        return m.find();
    }
    
    /**
     * valida si el correo cumple con el formato xxx@xxx
     * @param texto
     * @return 
     */
    public boolean validarFormato(String texto) {
        return texto.split("@").length == 2;
    }

    
}