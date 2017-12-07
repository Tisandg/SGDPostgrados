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
 * Clase que permite hacer las validaciones para la lista de los autores secundarios
 * para el registro de libro, revista, evento, publicación.
 * @author Juan 
 */
@FacesValidator(value = "validadorISSN")
public class ValidadorISSN implements Validator 
{
    /**
     * Método que es implementado de la clase Validator para realizar las validaciones del objeto value
     * que en este caso se obtiene el ISSN y es transformado en una cadena (String).
     * @param context
     * @param component
     * @param value
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String issn = value.toString();
        if (isVacio(issn) == false) {
            if (!validarFormato(issn)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Formato del ISSN es incorrecto. Intente de nuevo");
                throw new ValidatorException(msg);
            }
        }

    }

    /**
     * Metodo para comprobar si se han ingresado datos en el inputMask
     * @param issn
     * @return 
     */
    private boolean isVacio(String issn){
        boolean vacio = true;
        if(!issn.contains("_") && !issn.contains("-")){
            vacio = false;
        }
        return vacio;
    }
    /**
     * Valida el formato del DOI
     * @param issn
     * @return 
     */
    public boolean validarFormato(String issn) {
        Pattern p = Pattern.compile("(^([0-9]{4})+([-]{1})+([0-9]{3})+([0-9X]{1}))$");
        //http://www.issn.org/es/comprender-el-issn/que-es-el-numero-issn/
        //https://goo.gl/jQFjlJ
        Matcher m = p.matcher(issn);
        return m.find();
    }

}
