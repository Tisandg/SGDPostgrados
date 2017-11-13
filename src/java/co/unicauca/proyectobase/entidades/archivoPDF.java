package co.unicauca.proyectobase.entidades;

import java.io.InputStream;

/**
 *
 * @author Juan
 */
public class archivoPDF {
    
    private String nombreArchivo;
    private InputStream archivo; 

    public archivoPDF() {
       this.nombreArchivo = "";
    }

    public archivoPDF(String nombreArchivo, InputStream archivo) {
        this.nombreArchivo = nombreArchivo;
        this.archivo = archivo;
    }

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
   