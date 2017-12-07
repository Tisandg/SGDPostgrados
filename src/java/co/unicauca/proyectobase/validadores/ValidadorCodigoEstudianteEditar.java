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
 * Clase que permite hacer las validaciones para el código del estudiante
 * Esta clase es usada para la edición de un estudiante.
 */

@FacesValidator(value="validadorCodigoEstudianteEditar")
public class ValidadorCodigoEstudianteEditar implements Validator
{    
    /**
     * Método que es implementado de la clase Validator para realizar las validaciones del objeto value
     * que en este caso se obtiene el código del estudiante desde la vista de edición Estudiante
     * y es transformado en una cadena (String)
     * @param context
     * @param component
     * @param value
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String codigo = String.valueOf(value);
        
        if(codigo.length() == 0) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El código del estudiante es obligatorio.");
            throw new ValidatorException(msg);  
        }
        
        if((codigo.length() < 10) || (codigo.length() > 14)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El código debe contener de 10 a 14 caracteres.");
            throw new ValidatorException(msg);  
        }
        
        if(!validarInicioCodigo(codigo)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El código debe iniciar por 70_");
            throw new ValidatorException(msg); 
        }
        
        if(!validarFormato(codigo)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El código no cumple con el formato 70_cedulaestudiante");
            throw new ValidatorException(msg); 
        }
        
        if(!validarCedula(codigo)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El código debe ser numérico. No se permiten letras, caracteres especiales o espacios");
            throw new ValidatorException(msg); 
        }
    }
    
    /**
     * Validar que el codigo tenga el formato xx_xxxxxx
     * @param codigo
     * @return True si el código contiene un solo guión al piso (_).
     */
    public boolean validarFormato(String codigo) {
        return codigo.split("_").length == 2;
    }
    
    /**
     * valida que el codigo empiece por 70
     * @param codigo
     * @return True si antes de guión al piso (_) hay un 70.
     */
    public boolean validarInicioCodigo(String codigo) {
        return codigo.split("_")[0].equals("70");
    }
    
    /**
     * valida que la cedula, la segunda parte del codigo, sea numerica
     * @param codigo
     * @return True si es válida la expresión regular
     */
    public boolean validarCedula(String codigo) {
        String aux = codigo.split("_")[1];
        Pattern p = Pattern.compile("^[0-9]*$");
     //   Matcher m = p.matcher(codigo.split("_")[1]);
      //  return m.find();
        
        Matcher m = p.matcher(aux);
        return m.matches();
    }
}