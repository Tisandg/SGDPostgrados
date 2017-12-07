package co.unicauca.proyectobase.validadores;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Clase que permite hacer las validaciones para el ingreso de la editorial del libro.
 * Esta clase es usada para el registro de libro.
 * @author sperez
 */
@FacesValidator(value="validadorEditorialLibro")
public class ValidadorEditorialLibro implements Validator
{
    /**
     * Método que es implementado de la clase Validator para realizar las validaciones del objeto value
     * que en este caso se obtiene la editorial del libro desde la vista de registro de libro.
     * y es transformado en una cadena (String)
     * @param context
     * @param component
     * @param value
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException 
    {
        String editorial = value.toString();
        editorial = editorial.trim();
        
        if(editorial.equals(""))
        {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Debe ingresar la editorial del libro.");
            throw new ValidatorException(msg);
        }
    }
    
}
