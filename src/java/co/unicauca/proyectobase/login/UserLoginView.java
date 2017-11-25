package co.unicauca.proyectobase.login;

import co.unicauca.proyectobase.dao.GrupoTipoUsuarioFacade;
import co.unicauca.proyectobase.dao.UsuarioFacade;
import co.unicauca.proyectobase.entidades.GrupoTipoUsuario;
import co.unicauca.proyectobase.entidades.Usuario;
import co.unicauca.proyectobase.utilidades.Utilidades;
import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import co.unicauca.proyectobase.controladores.CargarVistaCoordinador;
import co.unicauca.proyectobase.controladores.CargarVistaEstudiante;
import co.unicauca.proyectobase.dao.EstudianteFacade;

/**
 * @hola unicauca
 */

@ManagedBean
@SessionScoped
public class UserLoginView implements Serializable {

    private String username;
    private String password;
    private Usuario usuario;
    private Integer creditos;

    private CargarVistaCoordinador cvc;
    private CargarVistaEstudiante cve;

    @EJB
    private GrupoTipoUsuarioFacade ejbGrupoUsuario;
    @Inject
    private UsuarioFacade ejbUsuarioActual;
    @EJB
    private EstudianteFacade ejbEstudiante;

    /**
     * Metodo para iniciar sesion en el sistema. El sistema si los datos 
     * proporcionados coinciden con los registros en la base de datos. Si coinciden
     * se inicia sesion y se redirige a la vista principal del usuario. En caso
     * contrario, se mostrar una notificacion.
     * @throws ServletException 
     */
    public void login() throws ServletException 
    {
        System.out.println("Verificando datos login");
        FacesContext contexto = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) contexto.getExternalContext().getRequest();
        if (username.length()==0 | password.length()==0) {
            contexto.addMessage(null, new FacesMessage("Error", "Los campos no pueden ir vacios"));
            Utilidades.redireccionar("/ProyectoII/faces/index.xhtml");
        }else{
            if (req.getUserPrincipal() == null){
                try {
                    req.login(this.username, this.password);
                    System.out.println("Login Exitoso");
                }catch (ServletException e) {
                    System.out.println(e.getMessage());
                    contexto.addMessage(null, new FacesMessage("Error", "Usuario o contraseña incorrectos"));
                    Utilidades.redireccionar("/ProyectoII/faces/index.xhtml");
                    return;
                }

                Principal principal = req.getUserPrincipal();
                System.out.println("buscando usuario en control: " + principal.getName());
                this.usuario = ejbUsuarioActual.findAllByNombreUsuario(principal.getName()).get(0);            

                ExternalContext external = contexto.getExternalContext();
                Map<String, Object> sessionMap = external.getSessionMap();
                sessionMap.put("user", this.usuario);

                List<GrupoTipoUsuario> lista = ejbGrupoUsuario.findAllByNombreUsuario(this.username);
                int id_tipo = lista.get(0).getGrupoTipoUsuarioPK().getIdTipo();

                switch (id_tipo) 
                {
                    case 2:
                        cve = new CargarVistaEstudiante();                        
                        Utilidades.redireccionar(cve.getRuta());
                        break;

                    case 3:
                        cvc = new CargarVistaCoordinador();
                        Utilidades.redireccionar(cvc.getRuta());
                        break;
                }
            }else{
                System.out.println("Ya se ha iniciado sesion");
            }
        }
    }

    /**
     * Metodo para cerrar sesion en el sistema
     * @throws IOException 
     */
    public void salir() throws IOException 
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
        try 
        {
            req.logout();
            req.getSession().invalidate();
            fc.getExternalContext().invalidateSession();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/ProyectoII/faces/index.xhtml");

        } catch (ServletException e) 
        {
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "FAILED", "Cerrar Sesion"));
        }
    }
    
    /******* Gets and Sets *******/
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getCreditos() {
        return creditos;
    }

    public void setCreditos(Integer creditos) {
        this.creditos = creditos;
    }

}
