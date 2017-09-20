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
 * @author Danilo
 */
@FacesValidator(value="ValidadorListaVacia")
public class ValidadorListaVacia implements Validator{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
         String valor = value.toString();        
        if(valor.equals("")) {            
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Debe seleccionar un campo de la lista");
            throw new ValidatorException(msg);
        }                
    }
    
}
