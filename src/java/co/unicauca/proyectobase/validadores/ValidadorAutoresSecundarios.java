package co.unicauca.proyectobase.validadores;

import co.unicauca.proyectobase.controladores.PublicacionController;
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
@FacesValidator(value="validadorAutoresSecundarios")
public class ValidadorAutoresSecundarios implements Validator 
{
    /**
     * Método que es implementado de la clase Validator para realizar las validaciones del objeto value
     * que en este caso se obtiene cada nombre del autor secundario desde la vista de registro de publicación
     * y es transformado en una cadena (String)
     * @param context
     * @param component
     * @param value
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String texto = String.valueOf(value);
        
        if(!texto.equals("")) {
            if(texto.length()<10 || texto.length()>200) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "La longitud debe estar entre 10 y 200 caracteres");
                throw new ValidatorException(msg);
            }
            
            if(validarInicioEspacio(texto) || validarInicioCaracteres(texto)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se puede inicar con un espacio en blanco, número o carácter especial");
                throw new ValidatorException(msg);
            }
            
            if(validarTerminacion(texto.substring(texto.length() - 1))) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No debe terminar con un carácter especial o un número");
                throw new ValidatorException(msg);
            }
                
            if(!validarNombreAutores(texto)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Sólo se permiten letras, espacios y tildes");
                throw new ValidatorException(msg);
            }
            if(isRegistrado(texto, context)){
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El nombre del autor ya se encuentra en la lista");
                throw new ValidatorException(msg);
            }
        }
    }
    
    /**
     * Funcion para comprobar que el autor a ingresar ya se encuentra en la
     * lista
     * @param nombre
     * @param context
     * @return 
     */
    public boolean isRegistrado(String nombre, FacesContext context){
        boolean variable = false;
        PublicacionController controller = (PublicacionController) context.getApplication().getELResolver().
                    getValue(context.getELContext(), null, "publicacionController");
        if (controller.estaAutorSecundario(nombre)) {
            variable = true;
        }
        return variable;        
    }
    
    /**
     * valida que el correo no empieze por un espacio en blanco
     * @param texto
     * @return 
     */
    public boolean validarInicioEspacio(String texto) {
        Pattern p = Pattern.compile("^[\\s_*'¿?!¡#-.$%&+=’~{}^<>;0-9]");
        Matcher m = p.matcher(texto);
        return m.find();
    }
    
    /**
     * valida que el correo no empieze por un caracter especial o numeros
     * @param texto
     * @return 
     */
    public boolean validarInicioCaracteres(String texto) {
        Pattern p = Pattern.compile("^[_*¿?!¡#-.$%&+=’~{},:\\[]^<>;0-9]");
        Matcher m = p.matcher(texto);
        return m.find();
    }
    
    /**
     * valida que el correo no empieze por un caracter especial o numeros
     * @param texto
     * @return 
     */
    public boolean validarFormato(String texto) {
        //Pattern p = Pattern.compile("^([^\\s]+[A-ZÁÉÍÓÚ]{1}[a-zñáéíóú]+[\\s;]*+[^;])+$");
        Pattern p = Pattern.compile("^([A-ZÁÉÍÓÚ]{1}[a-zñáéíóú]+[\\s;]*+[^;])+$");
        Matcher m = p.matcher(texto);
        return m.find();
    }
    
    /**
     * valida que la cadena ingresada no termine en un ;
     * @param texto
     * @return 
     */
    public boolean validarTerminacion(String texto) {
        Pattern p = Pattern.compile("[_*¿?!¡#-.$%;&+=’~{}<>0-9]");
        Matcher m = p.matcher(texto);
        return m.find();
    }
    
    
    /**
     * valida que la cadena ingresada no termine en un ;
     * @param texto
     * @return 
     */
    public boolean validarNombreAutores(String texto) {
        
        String[] nombres;
        nombres = texto.split(";");
        
        for (String nombre : nombres) {
            if (!(nombre).matches("([A-Za-z[ÁÉÍÓÚÑñáéíóú]]+[\\s;]*)+$")) {
                return false; 
            }
        }
        return true;
    }

}
