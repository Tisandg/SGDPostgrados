package co.unicauca.proyectobase.controladores;

import co.unicauca.proyectobase.entidades.Contrasena;
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

/**
 * Clase controlador utilizada para obtener obtener un estudiante para así poder
 * ambiar la contraseña. 
 * Este controlador es utilizado por las vistas: Reportes, VerEstudiante_Est y EditarContraseña.
 */

@Named(value = "verEstudianteController")
@ManagedBean
@SessionScoped
public class VerEstudianteController implements Serializable {

    /**
     * Variables para las operaciones sobre las tablas estudiante, 
     * usuario y publicacion respectivamente
     */
    @EJB
    private EstudianteFacade daoEst;
    @EJB
    private UsuarioFacade daoUsuario;
    @EJB
    private PublicacionFacade daoPublicacion;
    
    
    //Estudiante que esta en el sistema
    private Estudiante auxEstudiante;
    //Usuario que esta en el sistema
    private Usuario usuario;
    //Almacena los datos de los campos contraseñas
    private Contrasena contrasenas;
    private String credi;
    
    //Para la redireccion a las vistas del estudiante
    private CargarVistaEstudiante cve;
 
    String INICIO = "index";
    String CREAR = "new";
    String EDITAR = "editar";

    /**
     * Constructor
     */
    public VerEstudianteController() 
    {
        cve = new CargarVistaEstudiante();
    }

    /**
     * Metodo para buscar y fijar los datos de un determinado estudiante.
     * Con el nombre de usuario recibido se busca en la base de datos y se
     * recupera la informacion de ese usuario para guardarla en este controlador.
     * @param nombreUsuario 
     */
    public void fijarEstudiante(String nombreUsuario) {
        Usuario user = daoUsuario.findByUserName(nombreUsuario);
        Estudiante est = daoEst.findByUsername(nombreUsuario);
        setAuxEstudiante(est);
        this.usuario = user;
        this.contrasenas = new Contrasena();
    }

    /**
     * Carga los datos de un estudiante. Si se ha encontrado el estudiante
     * y se han fijado los datos en este controlador se retorna true, de lo 
     * contrario se retorna false
     * @param user
     * @return 
     */
    public boolean cargarEstudiante(String user){
        try{
            fijarEstudiante(user);            
            return true;
        }catch(Exception e){
            return false;
        }
    }

    /**
     * Redirige a la vista que permite ver los datos del estudiante que ha iniciado sesión.
     * @param nombreUsuario
     */
    public void redirigirVerMisDatos(String nombreUsuario) {
        fijarEstudiante(nombreUsuario);
        cve.verDatosPersonales();
        Utilidades.redireccionar(cve.getRuta());
    }
    
    /**
     * Cuando se invoca este método, se carga la vista que muestra los datos
     * del estudiante, que pueden ser editados.
     * @param nombreUsuario
     */
    public void irEditarContrasena(String nombreUsuario) 
    {
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
    
    /**
     * Método que permite un cambio de contraseña, si se recibe la misma
     * contraseña actual, se alerta al usuario, de ser diferente, se acepta el 
     * cambio en la contraseña
     * @return True si la contraseña ha sido cambida exitosamente.
     */
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
    
    //***************** GETTERS & SETTERS ************
    
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

    public Contrasena getContrasenas() {
        return contrasenas;
    }

    public void setContrasenas(Contrasena contrasenas) {
        this.contrasenas = contrasenas;
    }
    
    public String getnombreAut() {
        Estudiante est = getAuxEstudiante();
        String nombreAut = "";
        nombreAut = "" + est.getEstNombre() + " " + est.getEstApellido();

        return nombreAut;
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
}
