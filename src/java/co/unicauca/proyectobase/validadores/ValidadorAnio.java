package co.unicauca.proyectobase.validadores;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

 
@FacesValidator(value="validadorAnio")
public class ValidadorAnio implements Validator {
 
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String anio = String.valueOf(value);
        
        if(!validarAnio(anio)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El valor para el año debe ser numérico.");
            throw new ValidatorException(msg); 
        }
        
        if(anio.length() == 0) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El valor para el años no puede estar vacio");
            throw new ValidatorException(msg);  
        }
        
        int auxCredito = Integer.parseInt(anio);
        if((auxCredito > 9999) || (auxCredito < 1000)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El valor para el año debe contener 4 digitos");
            throw new ValidatorException(msg);  
        }
 
    }
    
   //valida que el valor ingresado para el numero de creditos  sea numerico
    public boolean validarAnio(String anio) {
        Pattern p = Pattern.compile("^[0-9]*$");
        Matcher m = p.matcher(anio);
        return m.find();
    }
  
    
 
}