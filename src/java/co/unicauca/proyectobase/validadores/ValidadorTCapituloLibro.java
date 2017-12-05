package co.unicauca.proyectobase.validadores;

import co.unicauca.proyectobase.controladores.PublicacionController;
import co.unicauca.proyectobase.entidades.CapituloLibro;
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
@FacesValidator(value="validadorTCapituloLibro")
public class ValidadorTCapituloLibro implements Validator {
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String nombre = String.valueOf(value);
                
       if(nombre.length() == 0) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El título del capítulo del libro es obligatorio");
            throw new ValidatorException(msg);
        }        
        if(nombre.length() < 12 || nombre.length() > 200) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El título del capítulo del libro debe contener entre 12 y 200 caracteres");
            throw new ValidatorException(msg);
        }         
        
        if(isRegistradoTituloCapLibro(nombre, context)){
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El título del capítulo libro ya se encuentra registrado. Por favor, verifique la información.");
            throw new ValidatorException(msg);
        }
        
    }
    
    /**
     * Funcion que comprueba si el titulo del capitulo ya ha sido registrado 
     * en el sistema. Comprueba que otros capitulos registrados no cuente con
     * el mismo nombre con el que se esta ingresando.
     * @param titulo
     * @param context
     * @return 
     */
    public boolean isRegistradoTituloCapLibro(String titulo, FacesContext context){
        /*Buscar en la bd si esta registrado*/
        boolean variable = false;
        PublicacionController controller = (PublicacionController) context.getApplication().getELResolver().
                    getValue(context.getELContext(), null, "publicacionController");
        CapituloLibro cap = controller.buscarTituloCapituloLibro(titulo);
        if (cap != null && controller.getActual().getPubIdentificador() != cap.getPubIdentificador()) {
            /*Esta colocando el mismo nombre del capitulo de libro de otro registro*/
            variable = true;
        }
        return variable;
    }
    
    
}

