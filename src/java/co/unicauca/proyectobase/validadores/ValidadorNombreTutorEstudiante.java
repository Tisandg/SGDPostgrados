package co.unicauca.proyectobase.validadores;

import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


@FacesValidator(value="validadorNombreTutorEstudiante")
public class ValidadorNombreTutorEstudiante implements Validator
{
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException 
    {
        String texto = String.valueOf(value);
        
        if(texto.length() == 0)
        {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El nombre del tutor es obligatorio.");
            throw new ValidatorException(msg);  
        }
        
        if((texto.length() < 3) || (texto.length() > 45))
        {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El nombre del tutor debe contener de 3 a 45 caracteres.");
            throw new ValidatorException(msg);  
        }
        
        boolean cumplePatron = Pattern.matches("^[a-zA-ZñÑáÁéÉíÍóÓúÚ\\s]*$", texto);
        if(!cumplePatron)
        {            
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El nombre del tutor contiene caracteres no válidos.");
            throw new ValidatorException(msg);  
        }        
    }
}