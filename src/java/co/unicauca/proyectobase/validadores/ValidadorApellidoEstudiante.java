package co.unicauca.proyectobase.validadores;

import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Clase que permite hacer las validaciones del apellido del estudiante. Esta clase
 * se usa para el registro del estudiante.
 */

@FacesValidator(value="validadorApellidoEstudiante")
public class ValidadorApellidoEstudiante implements Validator
{
    /**
     * Método que es implementado de la clase Validator para realizar las validaciones del objeto value
     * que en este caso es el apellido del estudiante y es transformado en una cadena (String)
     * @param context
     * @param component
     * @param value
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException 
    {
        String texto = String.valueOf(value);
        
        if(texto.length() == 0)
        {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Los apellidos del estudiante son obligatorios.");
            throw new ValidatorException(msg);  
        }
        
        if((texto.length() < 3) || (texto.length() > 20))
        {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Los apellidos del estudiante deben contener de 3 a 20 caracteres.");
            throw new ValidatorException(msg);  
        }
        
        boolean cumplePatron = Pattern.matches("^[a-zA-ZñÑáÁéÉíÍóÓúÚ\\s]*$", texto);
        if(!cumplePatron)
        {            
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El apellido sólo permite letras y espacios.");
            throw new ValidatorException(msg);  
        }        
    }
}