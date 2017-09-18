package co.unicauca.proyectobase.validadores;

import co.unicauca.proyectobase.dao.LibroFacade;
import co.unicauca.proyectobase.entidades.Libro;
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
        String titulo = String.valueOf(value);
        
        if(titulo.length() == 0) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El título del libro es obligatorio");
            throw new ValidatorException(msg);
        }        
        if(titulo.length() < 3 || titulo.length() > 200) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El título del libro debe contener entre 3 y 200 caracteres");
            throw new ValidatorException(msg);
        } 
        if(isRegistrado(titulo)){
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El título del libro ya esta registrado. Por favor revise.");
            throw new ValidatorException(msg);
        }
    }
    
    public boolean isRegistrado(String titulo){
        /*Buscar en la bd si esta registrado*/
        LibroFacade libro = new LibroFacade();
        libro.findByTituloLibro(titulo);
        return true;
    }
    
    
    
}

