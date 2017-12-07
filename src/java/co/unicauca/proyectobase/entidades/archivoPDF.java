package co.unicauca.proyectobase.entidades;

import java.io.InputStream;

/**
 * Clase que representa un archivo tipo PDF
 * @author Juan
 */
public class archivoPDF {
    
    private String nombreArchivo;
    private InputStream archivo; 

    /* Constructores */
    public archivoPDF() {
       this.nombreArchivo = "";
    }

    public archivoPDF(String nombreArchivo, InputStream archivo) {
        this.nombreArchivo = nombreArchivo;
        this.archivo = archivo;
    }

    /* Getters y Setters */
    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public InputStream getArchivo() {
        return archivo;
    }

    public void setArchivo(InputStream archivo) {
        this.archivo = archivo;
    }

  
}
   