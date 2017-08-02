/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.proyectobase.entidades;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
 
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
    import java.io.IOException; 
import java.io.InputStream;
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException; 
import java.util.Random;

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
   