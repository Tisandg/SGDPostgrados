package co.unicauca.proyectobase.validadores;

import co.unicauca.proyectobase.controladores.ReportesJasperController;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "validadorSeleccionOpcion")
public class ValidadorSeleccionOpcion implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null) {
            enviarException(context);
        }
        String texto = String.valueOf(value).trim();
        if (texto == null || texto.equals("")) {
            enviarException(context);
        }
    }

    private void enviarException(FacesContext context) {
        ReportesJasperController controller = (ReportesJasperController) context.getApplication().getELResolver().
                getValue(context.getELContext(), null, "reportesJasperController");
        controller.setTipoReporte(null);
        String mensaje = "Debe seleccionar un campo de la lista.";
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, mensaje);
        throw new ValidatorException(msg);
    }
}
