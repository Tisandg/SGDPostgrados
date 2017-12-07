package co.unicauca.proyectobase.validadores;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Validador utilizado para la fecha de publicacion
 * @author Juan
 */
@FacesValidator(value="validadorFechaPublicacion")
public class ValidadorFechaPublicacion implements Validator {
    
    /**
     * Método que es implementado de la clase Validator para realizar las validaciones del objeto value
     * que en este caso se obtiene la fecha de publicacion del registro de publicaciones y es 
     * transformado en una cadena (String)
     * @param context
     * @param component
     * @param value
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        if(value == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Se debe seleccionar la fecha de publicación");
            throw new ValidatorException(msg);
        }
        
    }    
}

