package co.unicauca.proyectobase.controladores;

import static com.sun.corba.se.impl.util.Utility.printStackTrace;
import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author sperez
 */

@Named(value = "cargarVistaEstudianteController")
@ManagedBean
@SessionScoped
public class CargarVistaEstudiante implements Serializable
{
    private String ruta;
    
    public String getRuta()
    {
        return this.ruta;
    }

    public CargarVistaEstudiante() 
    {
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/estudiante/ListarPublicaciones_Est.xhtml";
    }
    
    public void verDatosPersonales()
    {
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/estudiante/VerEstudiante_Est.xhtml";
    }
    
    public void verPublicacion()
    {
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/estudiante/VerPublicacion_Est.xhtml";
    }
    
    public void editarDocumentacion(){
        this.ruta = "/ProyectoII/faces/componentes/gestionPublicaciones/EditarPublicacion.xhtml";
    }
    
    public void registrarPublicacion()
    {        
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/estudiante/registrar_documentos/RegistrarPublicacion.xhtml";        
    }
    
    public void registrarDocumento(){
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/estudiante/registrar_documentos/Tab_registro_documentos.xhtml";
    }
    
    public void registrarPractica(){        
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/estudiante/registrar_documentos/RegistrarPracticaDocente.xhtml";          
    }
    
    public void verPublicaciones()
    {
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/estudiante/ListarPublicaciones_Est.xhtml";
    }
     public void verPracticas(){
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/estudiante/ListarPracticas.xhtml";
    }
     
     public void irEditarContrasena(){
         this.ruta = "/ProyectoII/faces/usuariosdelsistema/estudiante/perfil/EditarContrasena.xhtml";
     }
     
}
