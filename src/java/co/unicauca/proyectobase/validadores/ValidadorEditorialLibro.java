package co.unicauca.proyectobase.validadores;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author sperez
 */
@FacesValidator(value="validadorEditorialLibro")
public class ValidadorEditorialLibro implements Validator
{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException 
    {
        String editorial = value.toString();
        editorial = editorial.trim();
        
        if(editorial.equals(""))
        {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Debe ingresar la Editorial del libro. Campo obligatorio");
            throw new ValidatorException(msg);
        }
    }
    
}
