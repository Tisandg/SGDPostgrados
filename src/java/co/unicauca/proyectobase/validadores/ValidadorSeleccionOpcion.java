package co.unicauca.proyectobase.validadores;

import co.unicauca.proyectobase.controladores.ReportesJasperController;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Clase que permite hacer las validaciones de selección de opciones para la generación 
 * de reportes. Esta clase se usa para la vista de reportes.
 */

@FacesValidator(value = "validadorSeleccionOpcion")
public class ValidadorSeleccionOpcion implements Validator 
{
    /**
     * Método que es implementado de la clase Validator para realizar las validaciones del objeto value
     * que en este caso obtiene la opción seleccionada desde la vista de reportes, si 
     * no contiene nada significa que la opción es incorrecta.
     * @param context
     * @param component
     * @param value
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException 
    {
        if (value == null) {
            enviarException(context);
        }
        String texto = String.valueOf(value).trim();
        if (texto == null || texto.equals("")) {
            enviarException(context);
        }
    }
    
    /**
     * Método auxiliar de la clase que permite envíar un mensaje de excepción si 
     * no se ha escogido valor alguno para generar un reportes.
     * @param context
     */
    private void enviarException(FacesContext context) 
    {
        ReportesJasperController controller = (ReportesJasperController) context.getApplication().getELResolver().
                getValue(context.getELContext(), null, "reportesJasperController");
        controller.setTipoReporte(null);
        String mensaje = "Debe seleccionar un campo de la lista.";
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, mensaje);
        throw new ValidatorException(msg);
    }
}
