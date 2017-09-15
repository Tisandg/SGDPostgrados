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
 * @author Juan
 */
@FacesValidator(value="validadorFechaPublicacion")
public class ValidadorFechaPublicacion implements Validator {
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
//        String fecha = value.toString();
//        String[] valores = fecha.split(" ");
//        
        //System.out.println("MIRALOOOOOOO: " + value);
        /*Validando que el campo no este vacio*/
        if(value == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Se debe seleccionar la fecha de publicación");
            throw new ValidatorException(msg);
        }
        
    }
    
//    //valida que el año de publicacion este en un periodo de 5 años
//    public boolean validarAnioPublicacion(int anio) {
//        Calendar c = Calendar.getInstance();
//        int anioLimite = c.get(Calendar.YEAR)-5;
//        return anio < anioLimite;
//    }
    
}

