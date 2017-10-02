package co.unicauca.proyectobase.controladores;

import co.unicauca.proyectobase.dao.EstudianteFacade;
import co.unicauca.proyectobase.dao.PublicacionFacade;
import co.unicauca.proyectobase.entidades.Estudiante;
import co.unicauca.proyectobase.utilidades.Utilidades;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

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
    
    private CargarVistaEstudiante cve;
 

    String INICIO = "index";
    String CREAR = "new";
    String EDITAR = "editar";

    public VerEstudianteController() 
    {
        cve = new CargarVistaEstudiante();
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
        cve.verDatosPersonales();
        Utilidades.redireccionar(cve.getRuta());
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
