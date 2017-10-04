package co.unicauca.proyectobase.controladores;

import co.unicauca.proyectobase.dao.EstudianteFacade;
import co.unicauca.proyectobase.dao.PublicacionFacade;
import co.unicauca.proyectobase.entidades.Estudiante;
import co.unicauca.proyectobase.entidades.Publicacion;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 * @author santilopez94
 */

@Named(value = "reportesController")
@ManagedBean
@SessionScoped
public class ReportesController implements Serializable {
    
    @EJB
    private EstudianteFacade daoEst;
    @EJB
    private PublicacionFacade daoPublicacion;
    
    ArrayList<Publicacion> rev;
    ArrayList<Publicacion> lib;
    ArrayList<Publicacion> con;
    ArrayList<Publicacion> cap;
    ArrayList<Publicacion> anio;
    ArrayList<Publicacion> semestre;
   
    String nombre;
    String anios;
    String aniose;
    String nombree;

    public void ReportesController(){
        
    }
    
    public String getNombree() {
        return nombree;
    }

    public void setNombree(String nombree) {
        this.nombree = nombree;
    }

    public String getAniose() {
        return aniose;
    }

    public void setAniose(String aniose) {
        this.aniose = aniose;
    }

    public String getAnios() {
        return anios;
    }

    public void setAnios(String anios) {
        this.anios = anios;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
    
    public List<Publicacion> listadoPdf() throws FileNotFoundException, IOException {
       
        llenarListas();
        Document document= new Document();
        String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        System.out.println("path"+ realPath); 
        String conso = realPath + "resources\\pdf\\" + "Consolidado" + ".pdf";
        FileOutputStream archivo = new FileOutputStream(conso);
        try{
         PdfWriter.getInstance(document, archivo);
         document.open();
         document.add(new Paragraph("Consolidado de Publicaciones\n"));
         DateFormat formatter= new SimpleDateFormat("dd/MM/yy '-' hh:mm:ss");
         Date currentDate = new Date();
         String date = formatter.format(currentDate);
         document.add(new Paragraph("Fecha Generado: "+date)); 
         document.add(new Paragraph("\n"));
         PdfPTable table = new PdfPTable(4);
         table.setTotalWidth(new float[]{ 70,72, 110, 95});
         table.setLockedWidth(true);
         table.addCell("Nombre de Autor");
         table.addCell("Nombre Publicacion");
         table.addCell("Estado de la Publicacion");
         table.addCell("Fecha de Registro");
         
         PdfPCell revista = new PdfPCell(new Paragraph("Revista" ,
                       FontFactory.getFont("arial",   // fuente
                         8,                            // tamaño
                     Font.BOLD,                   // estilo
                               BaseColor.WHITE)));
                  revista.setHorizontalAlignment(Element.ALIGN_CENTER);
                  revista.setBackgroundColor(BaseColor.GRAY);
                  revista.setColspan(9);
                  table.addCell(revista);
         
         for (int i = 0; i < rev.size(); i++) {
             
                        
            table.addCell(rev.get(i).getPubNombreAutor());
            table.addCell(rev.get(i).obtenerNombrePub());
            table.addCell(rev.get(i).getPubEstado());
            table.addCell(rev.get(i).getPubFechaPublicacion().toString());
                        
           }
         
       PdfPCell libro = new PdfPCell(new Paragraph("Libro" ,
                       FontFactory.getFont("arial",   // fuente
                         8,                            // tamaño
                     Font.BOLD,                   // estilo
                               BaseColor.WHITE)));
                  libro.setHorizontalAlignment(Element.ALIGN_CENTER);
                  libro.setBackgroundColor(BaseColor.GRAY);
                  libro.setColspan(9);
                  table.addCell(libro);

           for (int i = 0; i < lib.size(); i++) {
                        
            table.addCell(lib.get(i).getPubNombreAutor());
            table.addCell(lib.get(i).obtenerNombrePub());
            table.addCell(lib.get(i).getPubEstado());
            table.addCell(lib.get(i).getPubFechaPublicacion().toString());
                        
           }
           
           PdfPCell congreso = new PdfPCell(new Paragraph("Congreso" ,
                       FontFactory.getFont("arial",   // fuente
                         8,                            // tamaño
                     Font.BOLD,                   // estilo
                               BaseColor.WHITE)));
                  congreso.setHorizontalAlignment(Element.ALIGN_CENTER);
                  congreso.setBackgroundColor(BaseColor.GRAY);
                  congreso.setColspan(9);
                  table.addCell(congreso);
                  
                   for (int i = 0; i < con.size(); i++) {
                        
            table.addCell(con.get(i).getPubNombreAutor());
            table.addCell(con.get(i).obtenerNombrePub());
            table.addCell(con.get(i).getPubEstado());
            table.addCell(con.get(i).getPubFechaPublicacion().toString());
                        
           }
                    PdfPCell capitulo = new PdfPCell(new Paragraph("Capitulo Libro" ,
                       FontFactory.getFont("arial",   // fuente
                         8,                            // tamaño
                     Font.BOLD,                   // estilo
                               BaseColor.WHITE)));
                  capitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
                  capitulo.setBackgroundColor(BaseColor.GRAY);
                  capitulo.setColspan(9);
                  table.addCell(capitulo);

           for (int i = 0; i < cap.size(); i++) {
                        
            table.addCell(cap.get(i).getPubNombreAutor());
            table.addCell(cap.get(i).obtenerNombrePub());
            table.addCell(cap.get(i).getPubEstado());
            table.addCell(cap.get(i).getPubFechaPublicacion().toString());
                        
           }
        
          document.add(table);
           
        
           }catch(Exception ex)
           {
            System.out.println("Error " + ex.getMessage());
           }
          document.close();
          
           return daoPublicacion.findAll();
    }
    
     public void llenarListas()
    {
        rev= new ArrayList();
        lib= new ArrayList();
        con= new ArrayList();
        cap= new ArrayList();
        int tamanoPublicaciones = daoPublicacion.findAll().size();
        List<Publicacion> listaPublicaciones = daoPublicacion.findAll();
        
        for (int i = 0; i < tamanoPublicaciones; i++) {
            
            if(listaPublicaciones.get(i).getPubTipoPublicacion().equals("revista"))
                 rev.add(listaPublicaciones.get(i));
            if(listaPublicaciones.get(i).getPubTipoPublicacion().equals("libro"))
                 lib.add(listaPublicaciones.get(i));
            if(listaPublicaciones.get(i).getPubTipoPublicacion().equals("congreso"))
                 con.add(listaPublicaciones.get(i));
            if(listaPublicaciones.get(i).getPubTipoPublicacion().equals("capitulo_libro"))
                 cap.add(listaPublicaciones.get(i));
        }
        
    }
     

     
  
     
     public void PublicacionesEst() throws DocumentException, FileNotFoundException
     {
         System.out.println("Nombre"+"\t"+nombre);
         String[] usuario= nombre.split("@");
         String username=usuario[0];
         Estudiante est = daoPublicacion.obtenerEstudianteP(username);
         List<Publicacion> ret = est.getPublicacionList();
         for (int i = 0; i < ret.size(); i++) {
             System.out.println("Publicaciones"+"\t"+ i+"\t"+"Nombre"+"\t"+ret.get(i).obtenerNombrePub());
         }
         Document document= new Document();
        String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        System.out.println("path"+ realPath); 
        String conso = realPath + "resources\\pdf\\" + "ReportePersonal"  +".pdf";
        FileOutputStream archivo = new FileOutputStream(conso);
        try{
         PdfWriter.getInstance(document, archivo);
         document.open();
         document.add(new Paragraph("Reporte personal de publicaciones\n"));
         document.add(new Paragraph("Estudiante: "+est.getEstNombre() +" "+est.getEstApellido())); 
         DateFormat formatter= new SimpleDateFormat("dd/MM/yy '-' hh:mm:ss");
         Date currentDate = new Date();
         String date = formatter.format(currentDate);
         document.add(new Paragraph("Fecha Generado: "+date)); 
         document.add(new Paragraph("\n"));
         PdfPTable table = new PdfPTable(4);
         table.setTotalWidth(new float[]{ 80,72, 110, 95});
         table.setLockedWidth(true);
         table.addCell("Tipo Publicacion");
         table.addCell("Nombre Publicacion");
         table.addCell("Estado de visado");
         table.addCell("Fecha de Registro");
             PdfPCell reporte = new PdfPCell(new Paragraph("Publicaciones" ,
                       FontFactory.getFont("arial",   // fuente
                         8,                            // tamaño
                     Font.BOLD,                   // estilo
                               BaseColor.WHITE)));
                  reporte.setHorizontalAlignment(Element.ALIGN_CENTER);
                  reporte.setBackgroundColor(BaseColor.GRAY);
                  reporte.setColspan(9);
                  table.addCell(reporte);
         
         for (int i = 0; i < ret.size(); i++) {
             
                        
            table.addCell(ret.get(i).getPubTipoPublicacion());
            table.addCell(ret.get(i).obtenerNombrePub());
            table.addCell(ret.get(i).getPubVisado());
            table.addCell(ret.get(i).getPubFechaPublicacion().toString());
                        
           }
          document.add(table);
         
         
     }catch(Exception e)
     {
         System.out.println("Error " + e.getMessage());
     }
        
         document.close();
}
     
     public void ArticulosAnio()
     {
         
         
         for (int i = 0; i < daoPublicacion.findAll().size(); i++) {
             
             String an = daoPublicacion.findAll().get(i).getPubFechaRegistro().toLocaleString();
             String a[]= an.split(" ");
             String fecha= a[0];
             String consul[]= fecha.split("/");
             String retorno= consul[2];
             System.out.println("anio"+ retorno);
             
             if(anios.equals(retorno))
             {
                 System.out.println("Publicacion"+daoPublicacion.findAll().get(i).obtenerNombrePub()+"\t"+i);
             }
             
             
            
         }
     }
     
     public void obtenerEstudianteAnio()
     {
         System.out.println("Nombre"+"\t"+nombree);
         System.out.println("Anio"+"\t"+aniose);
         String[] usuario= nombree.split("@");
         String username=usuario[0];
         Estudiante est = daoPublicacion.obtenerEstudianteAnio(username);
         List<Publicacion> ret = est.getPublicacionList();
         
         for (int i = 0; i < ret.size(); i++) {
             
         
             String an = daoPublicacion.findAll().get(i).getPubFechaRegistro().toLocaleString();
             String a[]= an.split(" ");
             String fecha= a[0];
             String consul[]= fecha.split("/");
             String retorno= consul[2];
             System.out.println("anio"+ retorno);
              if(aniose.equals(retorno))
             {
                 System.out.println("Publicacion"+ret.get(i).obtenerNombrePub()+"\t"+i+"Publicador"+"\t"+est.getEstNombre()+"\t"+est.getEstApellido());
             }
             
         }
     }
     
   
  
     
    
    
}
