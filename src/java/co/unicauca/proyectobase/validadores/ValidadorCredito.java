package co.unicauca.proyectobase.validadores;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Clase que permite hacer las validaciones para el número de créditos del estudiante.
 */
@FacesValidator(value = "validadorCredito")
public class ValidadorCredito implements Validator 
{
    /**
     * Método que es implementado de la clase Validator para realizar las validaciones del objeto value
     * que en este caso se obtiene el número de créditos del estudiante
     * y es transformado en una cadena (String)
     * @param context
     * @param component
     * @param value
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String credito = String.valueOf(value);

        if (!validarCredito(credito)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El valor del numero de creditos debe ser una cadena numérica de 1 caracter cuyo valor sea mayor a 0 y menor a 10.");
            throw new ValidatorException(msg);
        }

        if (credito.length() == 0) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El valor del numero de creditos es obligatorio");
            throw new ValidatorException(msg);
        }

        int auxCredito = Integer.parseInt(credito);
        if ((auxCredito > 9) || (auxCredito < 1)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El valor del numero de creditos debe ser una cadena numérica de 1 caracter cuyo valor sea mayor a 0 y menor a 10.");
            throw new ValidatorException(msg);
        }

    }

    /**
     * Método auziliar que permite validar si el número de créditos e un valor numérico.
     * @param numCreditos
     * @return True si es válida la expresión regular.
     */
    public boolean validarCredito(String numCreditos) {
        Pattern p = Pattern.compile("^[0-9]*$");
        Matcher m = p.matcher(numCreditos);
        return m.find();
    }

}
