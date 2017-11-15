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
@FacesValidator(value="validadorHorasPD")
public class ValidadorHorasPD implements Validator{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if(value == null){
            System.out.println("vacio");
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Debe ingresar la cantidad de horas . Campo obligatorio.");
            throw new ValidatorException(msg);
        }else{
            String horas = value.toString();        
            horas = horas.trim();        
            if(horas.equals("")){            
                System.out.println("vacio");
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Debe ingresar la cantidad de horas . Campo obligatorio.");
                throw new ValidatorException(msg);
            }
            if(!esNumero(horas)){            
                System.out.println("numero");
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Sólo se admiten valores númericos.");
                throw new ValidatorException(msg);
            }
            if(horas.length() > 3){
                System.out.println("longitud");
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Sólo se admiten valores menores a 1000.");
                throw new ValidatorException(msg);
            } 
        }
    }
    
    public boolean esNumero(String horas){
        try{
            Integer.parseInt(horas);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
}
