/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.proyectobase.controladores;

import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author sperez
 */
@Named(value = "cargarVistaCoordinadorController")
@ManagedBean
@SessionScoped
public class CargarVistaCoordinador implements Serializable
{
    private String ruta;
    
    public String getRuta()
    {
        return this.ruta;
    }

    public CargarVistaCoordinador() 
    {
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/coordinador/estudiantes/ListarEstudiantes.xhtml";
    }
    
    public void verEstudiante()
    {
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/coordinador/estudiantes/VerEstudiante.xhtml";
    }
    
    public void editarEstudiante()
    {
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/coordinador/estudiantes/EditarEstudiante.xhtml";
    }
    
    public void listarEstudiantes()
    {
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/coordinador/estudiantes/ListarEstudiantes.xhtml";
    }
    
    public void registrarEstudiante()
    {
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/coordinador/estudiantes/RegistrarEstudiante.xhtml";
    }
    
    public void verGraficaPubReg()
    {
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/coordinador/graficaPub/GraficaPubReg.xhtml";
    }
    
    public void verGraficaPubVis()
    {
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/coordinador/graficaPub/GraficaPubVis.xhtml";
    }
    
    public void listarPublicaciones()
    {
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/coordinador/listarPub/ListarPublicaciones_Coord.xhtml";
    }
    
    public void listarPublicacionesEspera()
    {
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/coordinador/listarPub/ListarPublicaciones_Espera.xhtml";
    }
    
    public void listarPublicacionesRevisadas()
    {
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/coordinador/listarPub/ListarPublicaciones_Rev.xhtml";
    }
    
    public void listarPublicacionesEstudiante()
    {
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/coordinador/listarPub/VerPublicacion_Coord.xhtml";
    }
    
    public void listarPracticaDocente()
    {
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/coordinador/listarPrac/ListarPractica_Docente.xhtml";
        
    }
}
