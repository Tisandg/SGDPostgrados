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
@FacesValidator(value = "validadorCampoVacioPD")
public class validadorCampoVacioPD implements Validator{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        try{    
            String valor = value.toString();        
            valor = valor.trim();        
            if(valor.equals("")){            
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Debe ingresar la descripcion de la actividad. Campo obligatorio.");
                throw new ValidatorException(msg);
            }            
        }catch(Exception e){
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Debe ingresar una descripción de actividad valida. Campo obligatorio.");
            throw new ValidatorException(msg);
        }        
    }
}
