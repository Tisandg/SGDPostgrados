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

    
@FacesValidator(value="validadorCorreo")
public class ValidadorCorreo implements Validator {
    @EJB
    private EstudianteFacade dao;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        String correo = String.valueOf(value);

        if(correo.length() == 0) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El correo del estudiante es obligatorio.");
            throw new ValidatorException(msg);  
        }

        if((correo.length()<10) ||(correo.length()>30)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El correo debe contener de 10 a 30 caracteres");
            throw new ValidatorException(msg);  
        }
        
        if(validarFormato(correo)) {
            if(!siTieneArroba(correo)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El correo no tiene @");
                throw new ValidatorException(msg);
            }
//            else {
//                if(!validarDominio(correo.split("@")[1])) {
//                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El correo debe ser: gmail.com, unicuauca.edu.co o hotmail.com");
//                    throw new ValidatorException(msg);
//                }
//            }
        }
        else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El formato del correo es incorrecto");
            throw new ValidatorException(msg);
        }
        
        if(!validarInicioCorreo(correo)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El inicio del correo es incorrecto");
            throw new ValidatorException(msg);
        }
        
        if(validarInicioCorreo2(correo)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El correo no debe iniciar por www");
            throw new ValidatorException(msg);
        }
        
        if(!validarCaracteresEspeciales(correo)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El correo tiene caracteres errados");
            throw new ValidatorException(msg);
        }
        
        if(validarExistencia(correo))
        {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Ya existe un estudiante con este correo.");
            throw new ValidatorException(msg); 
        }
    }
    
    
    //valida que el dominio del correo sea correcto
    public boolean validarDominio(String dominio) {
        return !(!dominio.equals("gmail.com") && 
                 !dominio.equals("unicauca.edu.co") &&
                 !dominio.equals("hotmail.com"));
    }
    
    //valida que el correo no empieze por un caracter especial
    public boolean validarInicioCorreo(String correo) {
        Pattern p = Pattern.compile("^[a-z]");
        Matcher m = p.matcher(correo);
        return m.find();
    }
    
    //valida que no empieze por www.
    public boolean validarInicioCorreo2(String correo) {
        Pattern p = Pattern.compile("^www.");
        Matcher m = p.matcher(correo);
        return m.find();
    }
    
    //valida que no contenga caracteres prohibidos
    public boolean validarCaracteresEspeciales(String correo) {
        Pattern p = Pattern.compile("^[A-Za-z0-9.@_-~#]+$");
        Matcher m = p.matcher(correo);
        
        return m.matches();
    }
    
    //valida si el correo ingresado tiene el caracter @
    public boolean siTieneArroba(String texto) {
        Pattern p = Pattern.compile("@");
        Matcher m = p.matcher(texto); 
        return m.find();
    }
    
    //valida si el correo cumple con el formato xxx@xxx
    public boolean validarFormato(String texto) {
        return texto.split("@").length == 2;
    }
    
    public boolean validarExistencia(String correo)
    {
        return dao.findByEstCorreo(correo);
    }
    
}