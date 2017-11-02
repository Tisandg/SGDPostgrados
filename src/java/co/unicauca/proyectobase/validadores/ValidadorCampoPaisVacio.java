package co.unicauca.proyectobase.validadores;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Danilo López - dlopezs@unicauca.edu.co
 */

@FacesValidator(value="validadorCampoPaisVacio")
public class ValidadorCampoPaisVacio implements Validator
{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException 
    {
        String campo = value.toString();
        campo = campo.trim();
        
        if(campo.equals(""))
        {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Debe ingresar el pais de publicación del libro. Campo obligatorio");
            throw new ValidatorException(msg);
        }
    }    
}
