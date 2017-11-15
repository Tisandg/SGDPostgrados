package co.unicauca.proyectobase.controladores;

import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * Esta clase contiene las diferentes rutas del sistema. Las rutas seran 
 * guardadas en el atributo "ruta".
 * @author sperez
 */

@Named(value = "cargarVistaEstudianteController")
@ManagedBean
@RequestScoped
public class CargarVistaEstudiante implements Serializable
{
    private String ruta;
    
    public String getRuta()
    {
        return this.ruta;
    }

    public CargarVistaEstudiante() 
    {        
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/estudiante/VerEstudiante_Est.xhtml";
    }
    
    public void verDatosPersonales()
    {
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/estudiante/VerEstudiante_Est.xhtml";
    }
    
    public void verPublicacion()
    {
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/estudiante/verDocumentos/VerPublicacion_Est.xhtml";
    }
    
    /**
     * ver practica docente rol estudiante
     */
    public void verPractica()
    {
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/estudiante/verDocumentos/verPracticaDocente_est.xhtml";
    }
    public void verReportes()
    {
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/estudiante/Reportes.xhtml";
    }
    public void editarDocumentacion(){
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/estudiante/EditarPublicacion_Est.xhtml";
    }
    
    public void registrarPublicacion()
    {        
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/estudiante/registrar_documentos/RegistrarPublicacion.xhtml";        
    }
        
    public void registrarPublicacion2()
    {
        this.ruta = "/ProyectoII/faces/plantillas/General.xhtml";
    }
    
    public void registrarDocumento(){
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/estudiante/registrar_documentos/Tab_registro_documentos.xhtml";
    }
    
    public void registrarPractica(){        
        this.ruta =  "/ProyectoII/faces/usuariosdelsistema/estudiante/registrar_documentos/RegistrarPracticaDocente.xhtml";
    }
    
    /**
     * listar publicaciones en el estudiante
     */
    public void verPublicaciones()
    {
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/estudiante/listarDocumentos/ListarPublicaciones_Est.xhtml";
    }
    
    /**
     * listar practicas en el estudiante
     */
    public void verPracticas(){
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/estudiante/listarDocumentos/ListarPracticas.xhtml";
    }

     public void irEditarContrasena(){
         this.ruta = "/ProyectoII/faces/usuariosdelsistema/estudiante/perfil/EditarContrasena.xhtml";
     }

    public void registrarLibro() //Cambio
    {
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/estudiante/registrar_documentos/RegistrarLibro.xhtml";
    }
    
    public void IrEditarPracticaDocente(){
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/estudiante/editarDocumentos/EditarPracticaDocente_Est.xhtml";
    }

}
