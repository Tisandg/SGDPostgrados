package co.unicauca.proyectobase.validadores;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Clase que permite hacer las validaciones para alguna lista (Combobox) que se tenga en la vista
 * @author Danilo
 */
@FacesValidator(value="ValidadorListaVacia")
public class ValidadorListaVacia implements Validator
{
    /**
     * Método que es implementado de la clase Validator para realizar las validaciones del objeto value
     * que en este caso se obtiene la opción en la lista (Combobox) y si es la palabra "seleccionar" significa que
     * no ha escogido una opción válida.
     * @param context
     * @param component
     * @param value
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String valor = String.valueOf(value);                     
        if(valor.equalsIgnoreCase("seleccionar")) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Debe seleccionar un campo de la lista");
            throw new ValidatorException(msg);
        }
    }
    
}
