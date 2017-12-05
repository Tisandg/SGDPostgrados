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
 *
 * @author Juan
 */
@FacesValidator(value="validadorPdf")
public class VelidadorArchivosPDF implements Validator {
    @EJB
    private EstudianteFacade dao;
    
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
    
    //valida la extension .pdf de la publicacion a subir
    public boolean validarExtension(String nombreArchivo) {
        //Pattern p = Pattern.compile("^([^\\s][A-ZÁÉÍÓÚa-zñáéíóú0-9*¿?!¡#$%&+=_-’~{}( )^<> ]+(\\.(?i)(pdf|PDF)))+$");
        Pattern p = Pattern.compile("([^\\s]+(\\.(?i)(pdf|PDF))$)"); 
        Matcher m = p.matcher(nombreArchivo); 
        return m.find();
    }
    
}
 
