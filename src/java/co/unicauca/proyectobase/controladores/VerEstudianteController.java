package co.unicauca.proyectobase.controladores;

import co.unicauca.proyectobase.dao.EstudianteFacade;
import co.unicauca.proyectobase.dao.PublicacionFacade;
import co.unicauca.proyectobase.entidades.Archivo;
import co.unicauca.proyectobase.entidades.Congreso;
import co.unicauca.proyectobase.entidades.Estudiante;
import co.unicauca.proyectobase.entidades.Publicacion;
import co.unicauca.proyectobase.entidades.Revista;
import co.unicauca.proyectobase.entidades.Libro;
import co.unicauca.proyectobase.entidades.CapituloLibro;
//import static co.unicauca.proyectobase.entidades.GrupoTipoUsuario_.nombreUsuario;
import co.unicauca.proyectobase.entidades.archivoPDF;
import co.unicauca.proyectobase.utilidades.Utilidades;
import com.itextpdf.text.DocumentException;
import com.openkm.sdk4j.exception.AccessDeniedException;
import com.openkm.sdk4j.exception.PathNotFoundException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.ejb.EJBException;
/*import java.nio.charset.StandardCharsets;
import java.util.Base64;*/
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

@Named(value = "verEstudianteController")
@ManagedBean
@SessionScoped
public class VerEstudianteController implements Serializable {

    @EJB
    private EstudianteFacade daoEst;
 
 @EJB
    private PublicacionFacade dao;
    private Estudiante auxEstudiante;
    private String credi;
 

    String INICIO = "index";
    String CREAR = "new";
    String EDITAR = "editar";

    public VerEstudianteController() {
    }

 

    public String index() {
        return INICIO;
    }

 
 
    public String getCredi(){
         credi = "" + auxEstudiante.getEstCreditos();
    
        if (credi.equalsIgnoreCase("null")) {
            credi = "0";
        }
        return credi;
    }
    public void setCredi(String credi) {
        this.credi = credi;
    }

  
 

    public void fijarEstudiante(String nombreUsuario) {

        Estudiante est = dao.obtenerEstudiante(nombreUsuario);
        setAuxEstudiante(est);
    }

    public String getnombreAut() {
        Estudiante est = getAuxEstudiante();
        String nombreAut = "";
        nombreAut = "" + est.getEstNombre() + " " + est.getEstApellido();

        return nombreAut;
    }

  
 
    public void redirigirVerMisDatos(String nombreUsuario) {
        fijarEstudiante(nombreUsuario);
        Utilidades.redireccionar("/ProyectoII/faces/componentes/gestionUsuarios/VerEstudiante_Est.xhtml");
    }

 

    /*mensajes de confirmacion */
    public void mensajeEditar() {
        addMessage("ha editado satisfactoriamente la publicacion", "");
    }
 
    public void mensajeconfirmarRegistro() {
        addMessage("Publicacion Registrada con exito ", "");
    }

    public void mensajeRegistroFallido() {
        addErrorMessage("Ocurrio un error durante el registro de la Publicacion ", "");
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void addErrorMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public Estudiante getAuxEstudiante() {
        return auxEstudiante;
    }

    public void setAuxEstudiante(Estudiante auxEstudiante) {
        this.auxEstudiante = auxEstudiante;
    }
 

}
