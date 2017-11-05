package co.unicauca.proyectobase.controladores;

import co.unicauca.proyectobase.dao.EstudianteFacade;
import co.unicauca.proyectobase.dao.PublicacionFacade;
import co.unicauca.proyectobase.dao.UsuarioFacade;
import co.unicauca.proyectobase.entidades.Estudiante;
import co.unicauca.proyectobase.entidades.Usuario;
import co.unicauca.proyectobase.utilidades.Utilidades;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Named(value = "verEstudianteController")
@ManagedBean
@SessionScoped
public class VerEstudianteController implements Serializable {

    @EJB
    private EstudianteFacade daoEst;
    @EJB
    private UsuarioFacade daoUsuario;
 
    @EJB
    private PublicacionFacade dao;
    private Estudiante auxEstudiante;
    private Usuario usuario;
    private contrasenaView contrasenas;
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
        Usuario user = daoUsuario.findByUserName(nombreUsuario);
        Estudiante est = dao.obtenerEstudiante(nombreUsuario);
        setAuxEstudiante(est);
        this.usuario = user;
        this.contrasenas = new contrasenaView();
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
    
    public void irEditarContrasena(String nombreUsuario) {
        fijarEstudiante(nombreUsuario);
        cve.irEditarContrasena();
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public contrasenaView getContrasenas() {
        return contrasenas;
    }

    public void setContrasenas(contrasenaView contrasenas) {
        this.contrasenas = contrasenas;
    }
    
    public boolean cambiarContrasena(){
        
        System.out.println("Cambiando contraseña...");
        boolean respuesta = false;
        /*Comprobar que la contraseña actual digitada coincida con la que 
          esta guardada*/
        System.out.println("Contraseña actual ingresada: "+this.contrasenas.getContrasenaActual());
        String contrasenaActual = Utilidades.sha256(this.contrasenas.getContrasenaActual());
        System.out.println("Contraseña actual guardada "+ usuario.getContrasena());
        if(contrasenaActual.equals(usuario.getContrasena())){
            /*Contraseñas coinciden*/
            System.out.println("Contraseñas actuales son iguales");
            String nuevaContrasena = Utilidades.sha256(this.contrasenas.getNuevaContrasena());
            try{
                this.usuario.setContrasena(nuevaContrasena);
                daoUsuario.edit(usuario);
                daoUsuario.flush();
                respuesta = true;
                System.out.println("Contrasena modificada");
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Exito", "Contraseña ha sido cambiada"));
            }catch(EJBException e){
                System.out.println("Error al editar contraseña");
            }
            
        }else{
            System.out.println("Contrasena no modificada");
        }
        
        return respuesta;
    }

}
