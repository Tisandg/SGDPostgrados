package co.unicauca.proyectobase.validadores;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Clase que permite hacer las validaciones de la descrición de la práctica docente. Esta clase
 * se usa para el registro y edición de práctica docente
 * @author Danilo López - dlopezs@unicauca.edu.co
 */
@FacesValidator(value = "validadorCampoVacioPD")
public class validadorCampoVacioPD implements Validator
{
    /**
     * Método que es implementado de la clase Validator para realizar las validaciones del objeto value
     * que en este caso es la descripción de la práctica docente y es transformado en una cadena (String)
     * @param context
     * @param component
     * @param value
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        try{    
            String valor = value.toString();        
            valor = valor.trim();        
            if(valor.equals("")){            
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campo obligatorio.","Debe ingresar la descripcion de la actividad.");
                throw new ValidatorException(msg);
            }            
        }catch(Exception e){
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error.","Debe ingresar la descripción de actividad.");
            throw new ValidatorException(msg);
        }        
    }
}
