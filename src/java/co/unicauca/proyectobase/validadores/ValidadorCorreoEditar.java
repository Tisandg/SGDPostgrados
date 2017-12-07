package co.unicauca.proyectobase.validadores;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Clase que permite hacer las validaciones para el correo electrónico del estudiante.
 * Esta clase es usada para la edición Estudiante.
 */

@FacesValidator(value="validadorCorreoEditar")
public class ValidadorCorreoEditar implements Validator 
{    
    
    /**
     * Método que es implementado de la clase Validator para realizar las validaciones del objeto value
     * que en este caso se obtiene el correo electrónico desde la vista de edición Estudiante
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
        
        if(validarFormato(correo)) {
            if(!siTieneArroba(correo)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El correo no tiene @.");
                throw new ValidatorException(msg);
            }
            else {
                if(!validarDominio(correo)) 
                {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El correo debe tener un dominio después del @.  Ejemplo: @gmail.com .");
                    throw new ValidatorException(msg);
                }
                else 
                {
                    if(!validarNoTerminaConPunto(correo))
                    {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El correo no puede finalizar con punto.");
                        throw new ValidatorException(msg);
                    }
                }
            }
        }
        else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El formato del correo es incorrecto.");
            throw new ValidatorException(msg);
        }
        
        if(!validarInicioCorreo(correo)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El inicio del correo es incorrecto.");
            throw new ValidatorException(msg);
        }
        
        if(validarInicioCorreo2(correo)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El correo no debe iniciar por www.");
            throw new ValidatorException(msg);
        }
        
        if(!validarCaracteresEspeciales(correo)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El correo tiene caracteres errados. Se permiten letras, números, - , _ .");
            throw new ValidatorException(msg);
        }
    }
    
    /**
     * Funcion para saber si la el dominio contiene el punto
     * @param texto
     * @return 
     */
    public boolean validarDominio(String texto) 
    {
        String cadena[] = texto.split("@");
        return cadena[1].contains(".");
    }
    
    /**
     * Funcion para saber si la cadena termina en punto
     * @param texto
     * @return 
     */
    public boolean validarNoTerminaConPunto(String texto) 
    {
        String cadena[] = texto.split("@");
        return !cadena[1].endsWith(".");
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