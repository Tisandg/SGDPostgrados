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
@FacesValidator(value="validadorNombreEvento")
public class ValidadorNombreEvento implements Validator {
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String nombre = String.valueOf(value);
        
        if(nombre.length() == 0) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El nombre del evento es obligatorio");
            throw new ValidatorException(msg);
        }        
        
        if(nombre.length() < 3 || nombre.length() > 100) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El nombre del evento debe contener entre 3 y 100 caracteres");
            throw new ValidatorException(msg);
        } 
        
        if(!validadorNombreEvento(nombre)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El nombre del evento es incorrecto");
            throw new ValidatorException(msg);
        } 

    }
    
    //valida que el nombre de la revista no contenga caracteres especiales
    public boolean validadorNombreEvento(String nomRevista) {
        Pattern p = Pattern.compile("^[a-zA-ZñÑáÁéÉíÍóÓúÚ\\s0-9]");
        Matcher m = p.matcher(nomRevista);
        return m.find();
    }
    
    
}
