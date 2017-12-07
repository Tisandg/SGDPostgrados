package co.unicauca.proyectobase.validadores;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Clase que permite hacer las validaciones para el registro de horas práctica docente.
 * @author Danilo López - dlopezs@unicauca.edu.co
 */
@FacesValidator(value="validadorHorasPD")
public class ValidadorHorasPD implements Validator
{
    /**
     * Método que es implementado de la clase Validator para realizar las validaciones del objeto value
     * que en este caso se obtiene la cantidad de horas para el registro de práctica docente.
     * y es transformado en una cadena (String)
     * @param context
     * @param component
     * @param value
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if(value == null){            
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Campo obligatorio.","Debe ingresar la cantidad de horas.");
            throw new ValidatorException(msg);
        }else{
            String horas = value.toString();        
            horas = horas.trim();        
            if(horas.equals("")){                            
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Campo obligatorio.","Debe ingresar la cantidad de horas.");
                throw new ValidatorException(msg);
            }
            if(!esNumero(horas)){                            
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error.","Sólo se admiten valores númericos.");
                throw new ValidatorException(msg);
            }
            if(horas.length() > 3){                
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error.","Sólo se admiten valores menores a 1000.");
                throw new ValidatorException(msg);
            } 
        }
    }
    
    /**
     * valida si el string ingresado es un numero o no
     * @param horas string que se desea verificar
     * @return true si el valor es un numero, fasle de lo contrario
     */
    public boolean esNumero(String horas){
        try{
            Integer.parseInt(horas);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
}
