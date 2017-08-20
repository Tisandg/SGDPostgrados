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
    
    public void registrarPublicacion()
    {
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/estudiante/RegistrarPublicacion.xhtml";
    }
    
    public void verPublicaciones()
    {
        this.ruta = "/ProyectoII/faces/usuariosdelsistema/estudiante/ListarPublicaciones_Est.xhtml";
    }
}
