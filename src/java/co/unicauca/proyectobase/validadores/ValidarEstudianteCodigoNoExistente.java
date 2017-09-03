package co.unicauca.proyectobase.validadores;

import co.unicauca.proyectobase.controladores.EstudianteController;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "validarEstudianteCodigoNoExistente")
public class ValidarEstudianteCodigoNoExistente implements Validator {

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
            if (controller.findByEstCodigoExceptId(codigo, identificador) != null) {
                String message = "Id ya existe, seleccione otro por favor.";
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
                throw new ValidatorException(msg);
            }
        }

    }

    java.lang.Integer getKey(String value) {
        java.lang.Integer key;
        key = Integer.valueOf(value);
        return key;
    }

}
