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
@FacesValidator(value="validadorNombreRevista")
public class ValidadorNombreRevista implements Validator {
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String nombre = String.valueOf(value);
        
        if(nombre.length() == 0) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El nombre de la revista es obligatorio");
            throw new ValidatorException(msg);
        }        
        
        if(nombre.length() < 10 || nombre.length() > 80) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El nombre de la revista debe contener entre 10 y 80 caracteres");
            throw new ValidatorException(msg);
        } 
        
        if(!validarNombreRevista(nombre)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Nombre de la revista incorrecto");
            throw new ValidatorException(msg);
        } 

    }
    
    //valida que el nombre de la revista no contenga caracteres especiales
    public boolean validarNombreRevista(String nomRevista) {
        Pattern p = Pattern.compile("^[a-zA-ZñÑáÁéÉíÍóÓúÚ\\s0-9]");
        Matcher m = p.matcher(nomRevista);
        return m.find();
    }
    
    
}
