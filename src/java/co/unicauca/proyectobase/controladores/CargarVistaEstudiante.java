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
    /**
     * Atributo donde se almacena la ruta a direccionar
     */
    private String ruta;
    
    public String getRuta()
    {
        return this.ruta;
    }
    
    /**
    * Los siguientes metodos son el establecimiento de las rutas en la variable ruta
    */
    public CargarVistaEstudiante() 
    {        
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/estudiante/VerEstudiante_Est.xhtml";
    }
    
    /**
     * Fija la ruta hacia la vista de un estudiante en particular
     */
    public void verDatosPersonales()
    {
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/estudiante/VerEstudiante_Est.xhtml";
    }
    
    /**
     * Fija la ruta hacia la publicacion en particular de un estudiante
     */
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
    
    /**
     * ver reportes rol estudiante
     */
    public void verReportes()
    {
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/estudiante/Reportes.xhtml";
    }
    /**
     * Editar documentos rol estudiante
     */
    public void editarDocumentacion(){
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/estudiante/EditarPublicacion_Est.xhtml";
    }
    
    /**
     * Registrar publicación rol estudiante
     */
    public void registrarPublicacion()
    {        
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/estudiante/registrar_documentos/RegistrarPublicacion.xhtml";        
    }

    /**
     * Registrar publicación de forma general rol estudiante
     */
    public void registrarPublicacion2()
    {
        this.ruta = "/ProyectoII/faces/plantillas/General.xhtml";
    }
    
    /**
     * Registrar documento rol estudiante
     */
    public void registrarDocumento(){
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/estudiante/registrar_documentos/Tab_registro_documentos.xhtml";
    }
    
    /**
     * Registrar práctica rol estudiante
     */
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

    /**
     * Editar constraseña rol estudiante
     */
    public void irEditarContrasena(){
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/estudiante/perfil/EditarContrasena.xhtml";
    }

    /**
     * Registrar libro rol estudiante
     */
    public void registrarLibro() //Cambio
    {
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/estudiante/registrar_documentos/RegistrarLibro.xhtml";
    }
    
    /**
     * Editar práctica docente rol estudiante
     */
    public void IrEditarPracticaDocente(){
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/estudiante/editarDocumentos/EditarPracticaDocente_Est.xhtml";
    }

}
