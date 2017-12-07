package co.unicauca.proyectobase.validadores;

import java.util.Date;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Clase que permite hacer las validaciones para el cohorte (año de ingreso) del estudiante.
 * Esta clase es uasa en el registro Estudiante.
 */

@FacesValidator(value="validadorCohorteEstudiante")
public class ValidadorCohorteEstudiante implements Validator
{
    /**
     * Método que es implementado de la clase Validator para realizar las validaciones del objeto value
     * que en este caso se obtiene el cohorte del estudiante a registrar la vista de registro Estudiante.
     * y es transformado en una cadena (String)
     * @param context
     * @param component
     * @param value
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException 
    {
        String texto = String.valueOf(value);
        Date fecha = new Date();
        int anio = fecha.getYear() + 1900;
        int cohorte;
        texto = texto.trim();
        
        if(texto.length() == 0)
        {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El cohorte del estudiante es obligatorio.");
            throw new ValidatorException(msg);  
        }
        
        boolean cumplePatron = Pattern.matches("^[0-9]*$", texto);
        if(!cumplePatron)
        {            
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El cohorte contiene caracteres no válidos.");
            throw new ValidatorException(msg);  
        }
        
        cohorte = Integer.parseInt(texto);       
        if((cohorte > anio) || (cohorte <= 2000))
        {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El cohorte debe ser un año mayor al 2000 y menor o igual al año actual.");
            throw new ValidatorException(msg);  
        }
    }
}