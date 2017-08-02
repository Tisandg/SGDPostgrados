package co.unicauca.proyectobase.validadores;
import co.unicauca.proyectobase.dao.EstudianteFacade;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


//@FacesValidator(value="validadorCodigoEstudiante")
//public class ValidadorCodigoEstudiante implements Validator
//{
//    @EJB
//    private EstudianteFacade dao;
//    
//    @Override
//    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException 
//    {
//        String texto = String.valueOf(value);
//        
//        if(texto.length() == 0)
//        {
//            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El código del estudiante es obligatorio.");
//            throw new ValidatorException(msg);  
//        }
//        
//        if((texto.length() < 7) || (texto.length() > 20))
//        {
//            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El código debe contener de 7 a 20 caracteres.");
//            throw new ValidatorException(msg);  
//        }
//        
//        boolean cumplePatron = Pattern.matches(".*[a-zA-Z0-9_]", texto);
//        if(!cumplePatron)
//        {            
//            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El código contiene caracteres no válidos.");
//            throw new ValidatorException(msg);  
//        }    
//        
//        boolean existe = dao.findByEstCodigo(texto);
//        if(existe)
//        {
//            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Ya existe un estudiante con este código.");
//            throw new ValidatorException(msg);  
//        }
//    }
//}

@FacesValidator(value="validadorCodigoEstudiante")
public class ValidadorCodigoEstudiante implements Validator {
    @EJB
    private EstudianteFacade dao;
    
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
        
        boolean existe = dao.findByEstCodigo(codigo);
        if(existe) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Ya existe un estudiante con este código.");
            throw new ValidatorException(msg);  
        }
        
        if(!validarFormato(codigo)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El código no cumple con el formato xx_xxxxxxxxxx");
            throw new ValidatorException(msg); 
        }
        
        if(!validarInicioCodigo(codigo)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El código debe iniciar por 70_");
            throw new ValidatorException(msg); 
        }
        
        if(!validarCedula(codigo)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El código debe ser numérico.");
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
}