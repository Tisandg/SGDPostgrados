package co.unicauca.proyectobase.validadores;

import co.unicauca.proyectobase.controladores.EstudianteController;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Clase que permite hacer las validaciones si el código de un estudiante ya está registrado. Esta clase
 * se usa para la edición del estudiante.
 */

@FacesValidator(value = "ValidarCodigoEstudianteNoExistente")
public class ValidarCodigoEstudianteNoExistente implements Validator 
{
    /**
     * Método que es implementado de la clase Validator para realizar las validaciones del objeto value
     * que en este caso es el código del estudiante y es transformado en una cadena (String)
     * @param context
     * @param component
     * @param value
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String codigo = String.valueOf(String.valueOf(value));

        if (value != null) {

            EstudianteController controller = (EstudianteController) context.getApplication().getELResolver().
                    getValue(context.getELContext(), null, "estudianteController");
            Integer identificador = controller.getActual().getEstIdentificador();
            
            if (identificador == null) {
                identificador = -1;
            }
            if (controller.buscarPorCodigoExceptoConId(codigo, identificador) != null) {
                String message = "Ya existe un estudiante registrado con el codigo '"+codigo+"'.";
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
                throw new ValidatorException(msg);
            }
        }
    }

}
