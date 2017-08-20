package co.unicauca.proyectobase.validadores;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

 
@FacesValidator(value = "validadorCredito")
public class ValidadorCredito implements Validator {

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

    //valida que el valor ingresado para el numero de creditos  sea numerico
    public boolean validarCredito(String numCreditos) {
        Pattern p = Pattern.compile("^[0-9]*$");
        Matcher m = p.matcher(numCreditos);
        return m.find();
    }

}
