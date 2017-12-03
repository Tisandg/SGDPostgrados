package co.unicauca.proyectobase.validadores;

import co.unicauca.proyectobase.controladores.PublicacionController;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * @author Santiago
 */

@FacesValidator(value="validadorTituloLibCap")
public class ValidadorTituloLibCapitulo implements Validator{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String titulo = String.valueOf(value);
        
        if(titulo.length() == 0) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El título del libro es obligatorio");
            throw new ValidatorException(msg);
        }        
        if(titulo.length() < 3 || titulo.length() > 200) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El título del libro debe contener entre 3 y 200 caracteres");
            throw new ValidatorException(msg);
        } 
    }
    
}
