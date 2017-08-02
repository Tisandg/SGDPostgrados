/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.proyectobase.entidades;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Juan
 */
public class FormatoFechas extends Date {
    private Date formatDate;

    public FormatoFechas(Date formatDate) {
        this.formatDate = formatDate;
    }
    
   @Override
   public String toString() {
       
 
            SimpleDateFormat sdtf = new SimpleDateFormat("yyyy-MM-dd");
            String fecha = sdtf.format(formatDate);
 
     return fecha;
   } 
}
