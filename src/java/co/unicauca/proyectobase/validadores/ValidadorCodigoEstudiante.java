package co.unicauca.proyectobase.validadores;

import co.unicauca.proyectobase.controladores.EstudianteController;
import co.unicauca.proyectobase.controladores.PublicacionController;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


@FacesValidator(value="validadorCodigoEstudiante")
public class ValidadorCodigoEstudiante implements Validator {
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String codigo = String.valueOf(value);
//        System.out.println("validadorCodigoEstudiante-----------------------------------------------");
        
        if(codigo.length() == 0) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El código del estudiante es obligatorio.");
            throw new ValidatorException(msg);  
        }
        
        if((codigo.length() < 10) || (codigo.length() > 14)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El código debe contener de 10 a 14 caracteres.");
            throw new ValidatorException(msg);  
        }
        
        if(!validarFormato(codigo)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El código debe ser numeros en el formato 70_1061...");
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
        
        
        if (isRegistradoEstudianteCodigo(codigo, context)){
            String message = "Ya existe un estudiante registrado con el codigo ingresaso.";
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", message);
            throw new ValidatorException(msg);
        }
        
        
    }
    
    //validar que el codigo tenga el formato xx_xxxxxx
    public boolean validarFormato(String codigo) {
        return codigo.split("_").length == 2;
    }
    
    //valida que el codigo empiece por 70
    public boolean validarInicioCodigo(String codigo) {
        return codigo.split("_")[0].equals("70");
    }
    
    //valida que la cedula, la segunda parte del codigo, sea numerica
    public boolean validarCedula(String codigo) {
        String aux = codigo.split("_")[1];
        Pattern p = Pattern.compile("^[0-9]*$");
     //   Matcher m = p.matcher(codigo.split("_")[1]);
      //  return m.find();
        
        Matcher m = p.matcher(aux);
        return m.matches();
    }
    
    public boolean isRegistradoEstudianteCodigo(String codigo, FacesContext context){
        /*Buscar en la bd si esta registrado*/
        boolean variable = false;
        EstudianteController controller = (EstudianteController) context.getApplication().getELResolver().
                    getValue(context.getELContext(), null, "estudianteController");
                
        Integer identificador = controller.getActual().getEstIdentificador();                                         
                                    
        if (controller.buscarPorCodigoExceptoConId(codigo, identificador) != null) {
            variable =  true;            
        }
        return variable;
    }
}