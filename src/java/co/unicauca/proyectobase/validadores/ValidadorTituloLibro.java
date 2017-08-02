/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
@FacesValidator(value="validadorTituloLibro")
public class ValidadorTituloLibro implements Validator {
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String nombre = String.valueOf(value);
        
        if(nombre.length() == 0) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El título del libro es obligatorio");
            throw new ValidatorException(msg);
        }        
        if(nombre.length() < 3 || nombre.length() > 200) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El título del libro debe contener entre 3 y 200 caracteres");
            throw new ValidatorException(msg);
        } 
        
      

    }
    
    
}

