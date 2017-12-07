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
 * Clase que permite hacer las validaciones para la contraseña del estudiante.
 * Esta clase es usada en el registro Estudiante.
 * @author Santiago
 */

@FacesValidator(value="validarContrasena")
public class ValidadorContrasenaEstudiante implements Validator
{
    /**
     * Método que es implementado de la clase Validator para realizar las validaciones del objeto value
     * que en este caso se obtiene la contraseña del estudiante desde la vista de registro de Estudiante
     * y es transformado en una cadena (String)
     * @param context
     * @param component
     * @param value
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        System.out.println("Validando contraseña");
        String contrasena = String.valueOf(String.valueOf(value));
        if (contrasena == null || contrasena.equals("")) {
            String message = "La contraseña no puede ir vacia";
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message,message);
            throw new ValidatorException(msg);
        }else{
            if(contrasena.length() <8 ){
                String message = "La contraseña debe tener minimo 8 caracteres";
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
                throw new ValidatorException(msg);
            }
            if(!validarCaracteres(contrasena)){
                String message = "Caracteres invalidos para contraseña";
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
                throw new ValidatorException(msg);
            }
        }
    }
    
    /**
     * Método auziliar de la clase que permite validar por medio de expresiones regulares
     * la contraseña del estudiante.
     * @param contrasena
     * @return True si s válida la expresión regular.
     */
    public boolean validarCaracteres(String contrasena){
        Pattern p = Pattern.compile("[A-Za-z0-9.,!#=)(/&%$+@+-_]");
        Matcher m = p.matcher(contrasena);
        return m.find();
    }
    
}
