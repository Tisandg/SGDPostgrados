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
 * Clase que permite hacer las validaciones del nombre de la revita. Esta clase
 * se usa para el registro revista.
 * @author Juan
 */
@FacesValidator(value="validadorNombreRevista")
public class ValidadorNombreRevista implements Validator 
{
    /**
     * Método que es implementado de la clase Validator para realizar las validaciones del objeto value
     * que en este caso es el nombre de la revista y es transformado en una cadena (String)
     * @param context
     * @param component
     * @param value
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String nombre = String.valueOf(value);
        
        if(nombre.length() == 0) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El nombre de la revista es obligatorio");
            throw new ValidatorException(msg);
        }        
        
        if(nombre.length() < 10 || nombre.length() > 80) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El nombre de la revista debe contener entre 10 y 80 caracteres");
            throw new ValidatorException(msg);
        } 
        
        if(!validarNombreRevista(nombre)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Nombre de la revista incorrecto");
            throw new ValidatorException(msg);
        } 

    }
    
    
    /**
     * valida que el nombre de la revista no contenga caracteres especiales.
     * @param nomRevista
     * @return True si es válida la expresión regular.
     */
    public boolean validarNombreRevista(String nomRevista) {
        Pattern p = Pattern.compile("^[a-zA-ZñÑáÁéÉíÍóÓúÚ\\s0-9]");
        Matcher m = p.matcher(nomRevista);
        return m.find();
    }
    
    
}
