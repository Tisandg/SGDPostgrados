package co.unicauca.proyectobase.validadores;

import co.unicauca.proyectobase.controladores.PublicacionController;
import co.unicauca.proyectobase.entidades.CapituloLibro;
import java.util.Objects;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Clase que permite hacer las validaciones del título del capítulo del libro. Esta clase
 * se usa para el registro de título cápitulo de libro.
 * @author Juan
 */
@FacesValidator(value="validadorTCapituloLibro")
public class ValidadorTCapituloLibro implements Validator 
{
    /**
     * Método que es implementado de la clase Validator para realizar las validaciones del objeto value
     * que en este caso es el título del capítulo del libro y es transformado en una cadena (String)
     * @param context
     * @param component
     * @param value
     */
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
        if (cap != null && !Objects.equals(controller.getActual().getPubIdentificador(), cap.getPubIdentificador())) 
        {
            /*Esta colocando el mismo nombre del capitulo de libro de otro registro*/
            variable = true;
        }
        return variable;
    }
    
    
}

