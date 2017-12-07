package co.unicauca.proyectobase.validadores;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value="validadorUsuario")
public class ValidadorUsuario implements Validator{
     @EJB
     @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        String usuario = String.valueOf(value);

        if(usuario.length() == 0) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El usuario  es obligatorio.");
            throw new ValidatorException(msg);  
        }
    }
}
