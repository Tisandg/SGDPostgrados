/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.faces.event.ActionEvent;
import javax.inject.Named;


/**
 *
 * @hola unicauca
 */


@ManagedBean
@SessionScoped
public class UserLoginView implements Serializable{
    private String username;
    private String password;
    private Usuario usuario;
    
    private CargarVistaCoordinador cvc;
    private CargarVistaEstudiante cve;
    
    @EJB
    private GrupoTipoUsuarioFacade ejbgtu;
    @Inject
    private UsuarioFacade ejbactual;
    
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
   
    public void login() throws ServletException {
        
         FacesContext fc= FacesContext.getCurrentInstance();
         HttpServletRequest req=(HttpServletRequest) fc.getExternalContext().getRequest();
         
         if(req.getUserPrincipal() == null);
         {
             try {
                 req.login(this.username, this.password);
                 System.out.println("Login Exitoso");

             } catch (ServletException e) {
                 fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fallo el inicio de sesion", null));
                 Utilidades.redireccionar("/ProyectoII/faces/index.xhtml");
                 return;
             }

             Principal principal = req.getUserPrincipal();
             this.usuario = ejbactual.findAllByNombreUsuario(principal.getName()).get(0);
             ExternalContext external = FacesContext.getCurrentInstance().getExternalContext();
             Map<String, Object> sessionMap = external.getSessionMap();
             sessionMap.put("user", this.usuario);

             List<GrupoTipoUsuario> lista = ejbgtu.findAllByNombreUsuario(this.username);
             int id_tipo = lista.get(0).getGrupoTipoUsuarioPK().getIdTipo();

             switch (id_tipo) {
                 case 2:
                     cve = new CargarVistaEstudiante();
                     Utilidades.redireccionar(cve.getRuta());
                     break;

                 case 3:
                     cvc = new CargarVistaCoordinador();
                     Utilidades.redireccionar(cvc.getRuta());
                     break;

             }
         }
    }
    
    public void salir() throws IOException
    {
        

         FacesContext fc= FacesContext.getCurrentInstance();
       HttpServletRequest req= (HttpServletRequest) fc.getExternalContext().getRequest();
       try
        {
           req.logout();
            req.getSession().invalidate();
            fc.getExternalContext().invalidateSession();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/ProyectoII/faces/index.xhtml");
            
        }catch( ServletException e)
        {
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"FAILED","Cerrar Sesion"));
        }
    }
    
    
    
    
    
}
