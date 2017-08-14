/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.proyectobase.controladores;

import co.unicauca.proyectobase.dao.GrupoTipoUsuarioFacade;
import co.unicauca.proyectobase.entidades.GrupoTipoUsuario;
import co.unicauca.proyectobase.entidades.Usuario;
import co.unicauca.proyectobase.utilidades.Utilidades;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Sahydo
 * Clase encargada de gestionar la información y los eventos relacionados con los usuarios del sistema
 */
@ManagedBean
@SessionScoped
public class UsuariosSessionController implements Serializable{
    @EJB
    private GrupoTipoUsuarioFacade grupoTipoUsuarioEJB;
    
    private String nombreUsuario;
    private String contrasena;
    private boolean haySesion;
    private boolean errorSesion;
    private Usuario usuario_actual;

    public UsuariosSessionController() {
        this.errorSesion = false;
        this.haySesion = false;
        this.nombreUsuario ="";
        this.contrasena = "";
        this.usuario_actual = new Usuario();
    }

    public boolean isHaySesion() {
        return haySesion;
    }

    public void setHaySesion(boolean haySesion) {
        this.haySesion = haySesion;
    }

    public boolean isErrorSesion() {
        return errorSesion;
    }

    public void setErrorSesion(boolean errorSesion) {
        this.errorSesion = errorSesion;
    }

    public Usuario getUsuario_actual() {
        return usuario_actual;
    }

    public void setUsuario_actual(Usuario usuario_actual) {
        this.usuario_actual = usuario_actual;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    /**
    * Método para iniciar sesión en la aplicación
    */
    public void login(){
        List<GrupoTipoUsuario> lista = grupoTipoUsuarioEJB.findAllByNombreUsuario(nombreUsuario);
        haySesion = false;
        try {
            GrupoTipoUsuario grupo = lista.get(0);
            usuario_actual = grupo.getUsuario();
            String tipo = grupo.getTipoUsuario().getNombre();
            String contrasenia=contrasena;
           //String contrasenia=cifrarBase64(contrasena);
            if (usuario_actual.getContrasena().equals(contrasenia)&&usuario_actual.getEstado().equals("activo")) {
                errorSesion =false;
                haySesion = true;
                switch (tipo) {
                    case "COORDINADOR":
                        Utilidades.redireccionar("/ProyectoII/faces/componentes/gestionUsuarios/ListarEstudiantes.xhtml");
                        break;
                    case "ESTUDIANTE":
                        Utilidades.redireccionar("/ProyectoII/faces/componentes/gestionPublicaciones/ListarPublicaciones_Est.xhtml");
                        break;
                    case "PROFESOR":
                        break;
                    default:
                        Utilidades.redireccionar("/ProyectoII/faces/index.xhtml");
                        break;
                }
            }else{
                this.usuario_actual = new Usuario();
                haySesion = false;
                errorSesion =true;
                Utilidades.redireccionar("/ProyectoII/faces/index.xhtml");
            }
        } catch (Exception e) {
            usuario_actual = new Usuario();
            haySesion = false;
            errorSesion =true;
            Utilidades.redireccionar("/ProyectoII/faces/index.xhtml");
        }
    }
    public void logout(){
        usuario_actual = new Usuario();
        haySesion = false;
        errorSesion = false;
        Utilidades.redireccionar("/ProyectoII/faces/index.xhtml");
        
    }
    public String cifrarBase64(String a) {
        Base64.Encoder encoder = Base64.getEncoder();
        String b = encoder.encodeToString(a.getBytes(StandardCharsets.UTF_8));
        return b;
    }
    
}