 package co.unicauca.proyectobase.validadores;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Juan
 */
@FacesValidator(value="validadorISBN")
public class ValidadorISBN implements Validator{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String isbn = value.toString();
        
        if(isbn.length() == 0) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El ISBN de la publicación es obligatorio");
            throw new ValidatorException(msg);
        }
        
        if(!validarFormato(isbn)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El ISBN debe ser numérico.");
            throw new ValidatorException(msg);
        }
        
        if(isbn.length() != 13) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El ISBN debe ser de trece dígitos.");
            throw new ValidatorException(msg);
        }


    }
    
    //valida que el ISBN contenga solo numeros
    public boolean validarFormato(String isbn) {
        Pattern p = Pattern.compile("^[0-9]*$");
        Matcher m = p.matcher(isbn); 
        return m.find();
    }
    
}
 
