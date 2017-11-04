package co.unicauca.proyectobase.dao;

/**
 * @author Santiago Garcia
 */
 
public class Contrasena {
    
    private String contrasenaActual;
    private String nuevaContrasena;
    private String nuevaContrasenaR;
    
    public Contrasena(){
        this.contrasenaActual = "";
        this.nuevaContrasena = "";
        this.nuevaContrasenaR = "";
    }

    public String getContrasenaActual() {
        return contrasenaActual;
    }

    public void setContrasenaActual(String contrasenaActual) {
        this.contrasenaActual = contrasenaActual;
    }

    public String getNuevaContrasena() {
        return nuevaContrasena;
    }

    public void setNuevaContrasena(String nuevaContrasena) {
        this.nuevaContrasena = nuevaContrasena;
    }

    public String getNuevaContrasenaR() {
        return nuevaContrasenaR;
    }

    public void setNuevaContrasenaR(String nuevaContrasenaR) {
        this.nuevaContrasenaR = nuevaContrasenaR;
    }    
}
