package co.unicauca.proyectobase.entidades;

import java.io.InputStream;

/**
 * Clase que permite definir los atributos necesarios para cargar archivos PDF
 * @author 
 */
public class tipoPDF_cargar{

    private String nombreArchivo;
    private String rutaArchivo;
    private String tipoPDF;
    private InputStream archivoIS;

    /* Constructores*/
    public tipoPDF_cargar() {
    }  

    public tipoPDF_cargar(String nombreArchivo, String rutaArchivo, String tipoPDF, InputStream archivoIS) {
        this.nombreArchivo = nombreArchivo;
        this.rutaArchivo = rutaArchivo;
        this.tipoPDF = tipoPDF;
        this.archivoIS = archivoIS;
    }

    /* Getters y Setters */
    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public String getTipoPDF() {
        return tipoPDF;
    }

    public void setTipoPDF(String tipoPDF) {
        this.tipoPDF = tipoPDF;
    }

    public InputStream getArchivoIS() {
        return archivoIS;
    }

    public void setArchivoIS(InputStream archivoIS) {
        this.archivoIS = archivoIS;
    }

}
