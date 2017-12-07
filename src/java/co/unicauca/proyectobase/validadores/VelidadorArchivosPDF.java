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
import org.primefaces.model.UploadedFile;

/**
 * Clase que permite hacer las validaciones de la extensión del archivo a subir por
 * parte del estudiante. Esta clase se usa para el registro de publicaciones y práctica docente.
 * @author Juan
 */
@FacesValidator(value="validadorPdf")
public class VelidadorArchivosPDF implements Validator 
{
    @EJB
    private EstudianteFacade dao;
    
    /**
     * Método que es implementado de la clase Validator para realizar las validaciones del objeto value
     * que en este caso obtiene el nombre completo y la extensión del archivo para comprobar si es un archivo PDF.
     * @param context
     * @param component
     * @param value
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        
        try{
            UploadedFile documento = (UploadedFile) value;
            System.out.println("Tamaño "+documento);
            
            if(documento.getSize() > 0){
                System.out.println("Nombre de archivo "+documento.getFileName());
                
                if(!validarExtension(documento.getFileName())) {
                    System.out.println("Archivos no es pdf\n");
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Solo se permiten archivos en pdf");
                    throw new ValidatorException(msg); 
                }
            }
        }catch(Exception e){
            System.out.println("\nEste archivo no se puede convertir a UploaddFile");
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Solo se permiten archivos en formato pdf");
            throw new ValidatorException(msg); 
        }

    }
    
    /**
     * valida la extension .pdf de la publicacion a subir. SE RECOMIENDA POR SEGURIDAD VALIDAR CON NÚMEROS MÁGICOS.
     * https://en.wikipedia.org/wiki/List_of_file_signatures
     * 
     * @param nombreArchivo
     * @return 
     */
    public boolean validarExtension(String nombreArchivo) 
    {
        //Pattern p = Pattern.compile("^([^\\s][A-ZÁÉÍÓÚa-zñáéíóú0-9*¿?!¡#$%&+=_-’~{}( )^<> ]+(\\.(?i)(pdf|PDF)))+$");
        Pattern p = Pattern.compile("([^\\s]+(\\.(?i)(pdf|PDF))$)"); 
        Matcher m = p.matcher(nombreArchivo); 
        return m.find();
    }
    
}
 
