/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.proyectobase.validadores;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Sebastian
 */
@FacesValidator(value="validadorFechaVisado")
public class ValidadorFechaVisado implements Validator{
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        if(value == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "La fecha de visado es obligatoria");
            throw new ValidatorException(msg);
        }
        
    }
    
}
