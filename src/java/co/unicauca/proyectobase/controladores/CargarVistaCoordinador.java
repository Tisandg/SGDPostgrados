package co.unicauca.proyectobase.controladores;

import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author sperez
 */
@Named(value = "cargarVistaCoordinadorController")
@ManagedBean
@RequestScoped
public class CargarVistaCoordinador implements Serializable
{
    private String ruta;
    
    public String getRuta()
    {
        return this.ruta;
    }

    /**
     * Los siguientes metodos son el establecimiento de las rutas en la variable
     * ruta
     */
    
    /**
     * Ruta hacia la vista principal del coordinador
     */
    public CargarVistaCoordinador() {
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/coordinador/estudiantes/ListarEstudiantes.xhtml";
    }
    
    /**
     * Ruta hacia la vista de un estudiante en particular
     */
    public void verEstudiante(){
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/coordinador/estudiantes/VerEstudiante.xhtml";
    }
    
    /**
     * Ruta hacia la vista de edicion de la informacion de un estudiante
     */
    public void editarEstudiante()
    {
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/coordinador/estudiantes/EditarEstudiante.xhtml";
    }
    
    /**
     * Ruta hacia vista del listado de estudiantes
     */
    public void listarEstudiantes()
    {
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/coordinador/estudiantes/ListarEstudiantes.xhtml";
    }
    
    /**
     * Ruta hacia la vista de registro de estudiante
     */
    public void registrarEstudiante()
    {
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/coordinador/estudiantes/RegistrarEstudiante.xhtml";
    }
    
    /**
     * Ruta hacia la vista de la grafica de las publicaciones registradas
     */
    public void verGraficaPubReg()
    {
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/coordinador/graficaPub/GraficaPubReg.xhtml";
    }
    
    /**
     * Rutas hacia la grafica de las publicaciones visadas
     */
    public void verGraficaPubVis()
    {
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/coordinador/graficaPub/GraficaPubVis.xhtml";
    }
    
    /**
     * Ruta hacia la vista del listado de publicaciones registradas
     */
    public void listarPublicaciones()
    {
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/coordinador/listarPub/ListarPublicaciones_Coord.xhtml";
    }
    
    /**
     * Ruta hacia la vista del listado de publicaciones en espera
     */
    public void listarPublicacionesEspera()
    {
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/coordinador/listarPub/ListarPublicaciones_Espera.xhtml";
    }
    
    /**
     * Ruta hacia la vista del listado de publicaciones revisadas
     */
    public void listarPublicacionesRevisadas()
    {
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/coordinador/listarPub/ListarPublicaciones_Rev.xhtml";
    }
    
    /**
     * Ruta hacia la vista una publicacion en particular
     */
    public void verPublicacionCoordinador()
    {
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/coordinador/listarPub/VerPublicacion_Coord.xhtml";
    }
    
    /**
     * Ruta hacia la vista del listado de practicas docentes
     */
    public void listarPracticaDocente()
    {
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/coordinador/listarPrac/ListarPractica_Docente.xhtml";
        
    }
    
    /**
     * Ruta hacia la vista de una practica docente en particular
     */
    public void listarPracticaDocenteVer()
    {
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/coordinador/listarPrac/VerPractica_Coord.xhtml";
        
    }
    
}
