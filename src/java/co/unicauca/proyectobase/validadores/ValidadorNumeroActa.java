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
 * @author Sebastian
 */
@FacesValidator(value="validadorNumeroActa")
public class ValidadorNumeroActa implements Validator{
    
 @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String acta = String.valueOf(value);

        if(acta.length() == 0) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El número del acta es obligatorio");
            throw new ValidatorException(msg);
        }    
        
        if(!validarActa(acta)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El número de acta debe ser una cadena numérica de mínimo 1 caracter y máximo 20 caracteres");
            throw new ValidatorException(msg); 
        }            
        
        if(acta.equals("0")) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El número del acta no debe ser cero");
            throw new ValidatorException(msg);
        }
        
        if(acta.length() > 20){
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El número de acta debe ser una cadena numérica de mínimo 1 caracter y máximo 20 caracteres");
            throw new ValidatorException(msg);  
        }
        
    }
    
    //valida que el valor ingresado para el numero de acta sea numerico
    public boolean validarActa(String numActa) {
        Pattern p = Pattern.compile("^[0-9]*$");
        Matcher m = p.matcher(numActa);
        return m.find();
    }

}
