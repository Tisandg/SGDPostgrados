package co.unicauca.proyectobase.validadores;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Clase que permite hacer las validaciones para la fecha de visado de algún documento
 * subido por un estudiante.
 * @author Sebastian
 */
@FacesValidator(value="validadorFechaVisado")
public class ValidadorFechaVisado implements Validator
{
    /**
     * Método que es implementado de la clase Validator para realizar las validaciones del objeto value
     * que en este caso se obtiene la fecha de visado y es transformado en una cadena (String)
     * @param context
     * @param component
     * @param value
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        if(value == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "La fecha de visado es obligatoria");
            throw new ValidatorException(msg);
        }
        
    }
    
}
