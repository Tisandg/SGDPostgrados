
package com.unicauca.asae.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

/**
 * @author Davidstl5
 * 
 **/

@ManagedBean
@RequestScoped
public class loginBean 
{  
    public loginBean(){        
    }
    
    
    private String usuario;
    private String contrasena;
    private String mostrar="verdadero";
    private String m="falso";

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
       
    }
    
    public void a()
    {
        System.out.println("kdjsakfhsdkjf");
    }
    
    
    public void mos(String a)
    {
       this.m= a;
       this.mostrar = "falso";
    }

    public String getMostrar() {
        return mostrar;
    }

    public void setMostrar(String mostrar) {
        this.mostrar = mostrar;
    }

   
    
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
        System.out.println("sotelo chupon");
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}