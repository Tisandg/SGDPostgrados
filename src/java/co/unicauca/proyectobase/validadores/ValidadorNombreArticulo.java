/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.proyectobase.validadores;

import co.unicauca.proyectobase.dao.PublicacionFacade;
import co.unicauca.proyectobase.entidades.Publicacion;
import javax.ejb.EJB;
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
@FacesValidator(value="validadorNombreArticulo")
public class ValidadorNombreArticulo implements Validator {
    @EJB
    private PublicacionFacade dao;
    private Publicacion actual;
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String nombre = String.valueOf(value);
        System.out.println("Nombre"+ nombre);
        
        if(nombre.length() == 0) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El título del artículo es obligatorio");
            throw new ValidatorException(msg);
        }

        if(nombre.length() < 10 || nombre.length()>200) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El título del artículo debe contener entre 10 y 200 caracteres");
            throw new ValidatorException(msg);
           
        }
        if(nombre.length()!=0)
        {
           for (int x=0; x < nombre.length(); x++) {
             if (nombre.charAt(x) != ' '){}
             else{FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Solo se permiten espacios entre las palabras");
            throw new ValidatorException(msg); }
                
            }
        }
        
        
        
        
    }
    
    
}

