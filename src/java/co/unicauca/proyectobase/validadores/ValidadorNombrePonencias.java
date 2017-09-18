/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.proyectobase.validadores;

import co.unicauca.proyectobase.controladores.PublicacionController;
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
@FacesValidator(value="validadorNombrePonencia")
public class ValidadorNombrePonencias implements Validator {
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String nombre = String.valueOf(value);
        
        if(nombre.length() == 0) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El título de ponencia es obligatorio.");
            throw new ValidatorException(msg);
        }        
        
        if(nombre.length() < 10 || nombre.length() > 100) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El título de la ponencia debe contener entre 10 y 100 caracteres.");
            throw new ValidatorException(msg);
        } 
        
        if(!validadorNombrePonencia(nombre)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El nombre de la ponencia es incorrecto.");
            throw new ValidatorException(msg);
        } 
        if(isRegistrado(nombre, context)){
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El nombre ya esta registrado. Por favor, verifique el nombre de la ponencia .");
            throw new ValidatorException(msg);
        }

    }
    
    //valida que el nombre de la revista no contenga caracteres especiales
    public boolean validadorNombrePonencia(String nomRevista) {
        Pattern p = Pattern.compile("^[a-zA-ZñÑáÁéÉíÍóÓúÚ\\s0-9]");
        Matcher m = p.matcher(nomRevista);
        return m.find();
    }
    
    public boolean isRegistrado(String ponencia, FacesContext context){
        /*Buscar en la bd si esta registrado*/
        boolean variable = false;
        PublicacionController controller = (PublicacionController) context.getApplication().getELResolver().
                    getValue(context.getELContext(), null, "publicacionController");
        if (controller.buscarPonenciaPorTitulo(ponencia) != null) {
            variable = true;
        }
        return variable;
    }
    
    
}
