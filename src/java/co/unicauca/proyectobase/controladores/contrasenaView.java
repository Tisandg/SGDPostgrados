package co.unicauca.proyectobase.controladores;
import javax.faces.bean.ManagedBean;

/**
 * @author Santiago Garcia
 */
 
@ManagedBean
public class contrasenaView {
    private String contrasenaActual;
    private String nuevaContrasena;
    private String nuevaContrasenaR;

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
