/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.proyectobase.utilidades;

/**
 *
 * @author Danilo
 */
public class Autor {
    
    String nombre ;

    public Autor(String autor) {
        this.nombre = autor;
    }
    
    public Autor() {
        this.nombre = "";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    
    
    
    
}
