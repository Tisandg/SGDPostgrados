package co.unicauca.proyectobase.entidades;

/**
 * @author Santiago Garcia
 */
 
public class Contrasena {
    
    //Almacena la contraseña actual
    private String contrasenaActual;
    //Almacena la contraseña nueva
    private String nuevaContrasena;
    //Almacena la repeticion de la contraseña
    private String nuevaContrasenaR;
    
    /**
     * Constructor
     */
    public Contrasena(){
        this.contrasenaActual = "";
        this.nuevaContrasena = "";
        this.nuevaContrasenaR = "";
    }

    /**   Get and Set    */
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
