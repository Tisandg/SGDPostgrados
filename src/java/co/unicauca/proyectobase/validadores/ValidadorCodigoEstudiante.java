package co.unicauca.proyectobase.validadores;

import co.unicauca.proyectobase.controladores.EstudianteController;
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
 * Esta clase es usada para el registro de un estudiante.
 */

@FacesValidator(value="validadorCodigoEstudiante")
public class ValidadorCodigoEstudiante implements Validator 
{
    /**
     * Método que es implementado de la clase Validator para realizar las validaciones del objeto value
     * que en este caso se obtiene el código del estudiante desde la vista de registro Estudiante
     * y es transformado en una cadena (String)
     * @param context
     * @param component
     * @param value
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        
        
        String codigo = String.valueOf(value);
        System.out.println("Validando codigo estudiante "+codigo);
        
        if(codigo.length() == 0) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El código del estudiante es obligatorio.");
            throw new ValidatorException(msg);  
        }else{
            
            if((codigo.length() < 10) || (codigo.length() > 14)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El código debe contener de 10 a 14 caracteres.");
                throw new ValidatorException(msg);  
            }
            
            if (isRegistradoEstudianteCodigo(codigo, context)){
                String message = "Ya existe un estudiante registrado con este codigo.";
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", message);
                throw new ValidatorException(msg);
            }

            if(!validarFormato(codigo)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El código es un numero en el formato 70_cedulaEstudiante");
                throw new ValidatorException(msg); 
            }

            if(!validarInicioCodigo(codigo)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El código debe iniciar por '70_'");
                throw new ValidatorException(msg); 
            }

            if(!validarCedula(codigo)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El código solo permite numeros y el caracter especial \"_\".");
                throw new ValidatorException(msg); 
            }
        }
        
    }
    
    /**
     * validar que el codigo tenga el formato xx_xxxxxx
     * @param codigo
     * @return 
     */
    public boolean validarFormato(String codigo) {
        return codigo.split("_").length == 2;
    }
    
    /**
     * valida que el codigo empiece por 70
     * @param codigo
     * @return 
     */
    public boolean validarInicioCodigo(String codigo) {
        return codigo.split("_")[0].equals("70");
    }
    
    /**
     * valida que la cedula, la segunda parte del codigo, sea numerica
     * @param codigo
     * @return 
     */
    public boolean validarCedula(String codigo) {
        String aux = codigo.split("_")[1];
        Pattern p = Pattern.compile("^[0-9]*$");
        Matcher m = p.matcher(aux);
        return m.matches();
    }
    
    /**
     * Funcion para saber si ya se encuentra un estudiante registrado con
     * el codigo ingresado
     * @param codigo
     * @param context
     * @return 
     */
    public boolean isRegistradoEstudianteCodigo(String codigo, FacesContext context){
        /*Buscar en la bd si esta registrado*/
        boolean variable = false;
        EstudianteController controller = (EstudianteController) context.getApplication().getELResolver().
                    getValue(context.getELContext(), null, "estudianteController");                                      
        if (controller.existByCodigoEst(codigo)) {
            variable =  true;            
        }
        return variable;
    }
}