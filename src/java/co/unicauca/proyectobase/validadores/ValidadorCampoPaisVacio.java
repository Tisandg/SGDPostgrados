package co.unicauca.proyectobase.validadores;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Clase que permite hacer las validaciones de la lista desplegable (Combobox), para
 * comprobar si se ha seleccionado un país.
 * Esta clase es utilizada para registrr un evento o libro.
 * @author Danilo López - dlopezs@unicauca.edu.co
 */

@FacesValidator(value="validadorCampoPaisVacio")
public class ValidadorCampoPaisVacio implements Validator
{
    /**
     * Método que es implementado de la clase Validator para realizar las validaciones del objeto value
     * que en este caso se obtiene el país desde la vista de registro de libro o evento
     * y es transformado en una cadena (String)
     * @param context
     * @param component
     * @param value
     */
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
