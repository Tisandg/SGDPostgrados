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
import java.awt.event.ActionEvent;
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
import org.primefaces.context.RequestContext ;


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
         try{
             req.login(this.username, this.password);
             System.out.println("Login Exitoso");
            
         }catch(ServletException e){
             fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fallo el inicio de sesion",null));
             return;
         }
         Principal principal = req.getUserPrincipal();
         this.usuario = ejbactual.findAllByNombreUsuario(principal.getName()).get(0);
         ExternalContext external= FacesContext.getCurrentInstance().getExternalContext();
         Map<String,Object> sessionMap= external.getSessionMap();
         sessionMap.put("user",this.usuario);
         
         System.out.println("Informacion 1"+ ejbactual.findAllByNombreUsuario(username).get(0));
         
         List<GrupoTipoUsuario> lista = ejbgtu.findAllByNombreUsuario(this.username);
         int id_tipo= lista.get(0).getGrupoTipoUsuarioPK().getIdTipo();
         
         
         
         switch(id_tipo)
         {
            
             case 3:
                 Utilidades.redireccionar("/ProyectoII/faces/componentes/gestionUsuarios/ListarEstudiantes.xhtml");
             break;
             
         }
         // Principal=Java.Security.Principal, manejo de seguridad
         //Principal principal = req.getUserPrincipal(); 
         
         //this.usuario = ejbactual.findAllByNombreUsuario(principal.getName()).get(0);
                
//         ExternalContext external= FacesContext.getCurrentInstance().getExternalContext();
//         Map<String,Object> sessionMap= external.getSessionMap();
//         sessionMap.put("user",this.usuario);
         
         
        
      
//        RequestContext context = RequestContext.getCurrentInstance();
//        FacesContext fc= FacesContext.getCurrentInstance();
//        HttpServletRequest req=(HttpServletRequest) fc.getExternalContext().getRequest();
//        req.login(this.username,this.password);
//        FacesMessage message = null;
//        boolean loggedIn = false;
//        req.getServletContext().log("Autenticacion exitossa");
//        if(username != null && username.equals("admin") && password != null && password.equals("admin")) {
//            loggedIn = true;
//            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome user", username);
//            Utilidades.redireccionar("/ProyectoII/faces/componentes/gestionUsuarios/ListarEstudiantes.xhtml");
//            //
//        }
//         if(username != null && username.equals("est") && password != null && password.equals("est")) {
//            loggedIn = true;
//            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome user", username);
//            Utilidades.redireccionar("/ProyectoII/faces/componentes/gestionPublicaciones/ListarPublicaciones.xhtml");
//            //
//        }
//        else {
//            loggedIn = false;
//            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Invalid credentials");
//        }
//         
//        FacesContext.getCurrentInstance().addMessage(null, message);
//        context.addCallbackParam("loggedIn", loggedIn);
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
