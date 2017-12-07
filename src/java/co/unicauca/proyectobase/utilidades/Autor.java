/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.proyectobase.utilidades;

import java.util.Objects;

/**
 * Clase que representa un autor y permite definir los atributos propios de este.
 * @author Danilo
 */
public class Autor {
    
    String nombre ;

    /* Controladores */
    public Autor(String autor) {
        this.nombre = autor;
    }
    
    public Autor() {
        this.nombre = "";
    }

    /* Getters y Setters */
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Método que permite obtener el hash del autor
     * @return hash
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.nombre);
        return hash;
    }

    /**
     * Método que permite verificar si un objeto es igual a un autor
     * @param obj: objeto para comparar
     * @return true si es igual, false si el objeto es nulo, o es un objeto de otra clase, 
     * o si es un autor pero no coicide el nombre
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Autor other = (Autor) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return true;
    }    
}
