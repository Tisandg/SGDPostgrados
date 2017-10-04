/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author santilopez94
 */
@Named(value = "reportesController")
@ManagedBean
@SessionScoped
public class ReportesController implements Serializable {

    @EJB
    private EstudianteFacade daoEst;
    @EJB
    private PublicacionFacade dao;

    ArrayList<Publicacion> rev;
    ArrayList<Publicacion> lib;
    ArrayList<Publicacion> con;
    ArrayList<Publicacion> cap;
    ArrayList<Publicacion> anio;
    ArrayList<Publicacion> primerse;
    ArrayList<Publicacion> segundose;
    ArrayList<Publicacion> primersee;
    ArrayList<Publicacion> segundosee;
    ArrayList<Publicacion> auxiliar;
    ArrayList<Publicacion> auxiliar1;
    ArrayList<Publicacion> auxiliar2;
    ArrayList<Publicacion> auxiliar3;

    String nombre;
    String anios;
    String aniose;
    String nombree;
    String semestre;
    String semestree;
    String estudiante;

    public String getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(String estudiante) {
        this.estudiante = estudiante;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public String getSemestree() {
        return semestree;
    }

    public void setSemestree(String semestree) {
        this.semestree = semestree;
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
        Document document = new Document();
        String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        System.out.println("path" + realPath);
        String conso = realPath + "resources\\pdf\\" + "Consolidado" + ".pdf";
        FileOutputStream archivo = new FileOutputStream(conso);
        try {
            PdfWriter.getInstance(document, archivo);
            document.open();
            document.add(new Paragraph("Consolidado de Publicaciones\n"));
            DateFormat formatter = new SimpleDateFormat("dd/MM/yy '-' hh:mm:ss");
            Date currentDate = new Date();
            String date = formatter.format(currentDate);
            document.add(new Paragraph("Fecha Generado: " + date));
            document.add(new Paragraph("\n"));
            PdfPTable table = new PdfPTable(4);
            table.setTotalWidth(new float[]{70, 72, 110, 95});
            table.setLockedWidth(true);
            table.addCell("Nombre de Autor");
            table.addCell("Nombre Publicacion");
            table.addCell("Estado de la Publicacion");
            table.addCell("Fecha de Registro");

            PdfPCell revista = new PdfPCell(new Paragraph("Revista",
                    FontFactory.getFont("arial", // fuente
                            8, // tamaño
                            Font.BOLD, // estilo
                            BaseColor.WHITE)));
            revista.setHorizontalAlignment(Element.ALIGN_CENTER);
            revista.setBackgroundColor(BaseColor.BLUE);
            revista.setColspan(9);
            table.addCell(revista);

            for (int i = 0; i < rev.size(); i++) {

                table.addCell(rev.get(i).getPubNombreAutor());
                table.addCell(rev.get(i).obtenerNombrePub());
                table.addCell(rev.get(i).getPubEstado());
                table.addCell(rev.get(i).getPubFechaPublicacion().toString());

            }

            PdfPCell libro = new PdfPCell(new Paragraph("Libro",
                    FontFactory.getFont("arial", // fuente
                            8, // tamaño
                            Font.BOLD, // estilo
                            BaseColor.WHITE)));
            libro.setHorizontalAlignment(Element.ALIGN_CENTER);
            libro.setBackgroundColor(BaseColor.BLUE);
            libro.setColspan(9);
            table.addCell(libro);

            for (int i = 0; i < lib.size(); i++) {

                table.addCell(lib.get(i).getPubNombreAutor());
                table.addCell(lib.get(i).obtenerNombrePub());
                table.addCell(lib.get(i).getPubEstado());
                table.addCell(lib.get(i).getPubFechaPublicacion().toString());

            }

            PdfPCell congreso = new PdfPCell(new Paragraph("Congreso",
                    FontFactory.getFont("arial", // fuente
                            8, // tamaño
                            Font.BOLD, // estilo
                            BaseColor.WHITE)));
            congreso.setHorizontalAlignment(Element.ALIGN_CENTER);
            congreso.setBackgroundColor(BaseColor.BLUE);
            congreso.setColspan(9);
            table.addCell(congreso);

            for (int i = 0; i < con.size(); i++) {

                table.addCell(con.get(i).getPubNombreAutor());
                table.addCell(con.get(i).obtenerNombrePub());
                table.addCell(con.get(i).getPubEstado());
                table.addCell(con.get(i).getPubFechaPublicacion().toString());

            }
            PdfPCell capitulo = new PdfPCell(new Paragraph("Capitulo Libro",
                    FontFactory.getFont("arial", // fuente
                            8, // tamaño
                            Font.BOLD, // estilo
                            BaseColor.WHITE)));
            capitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
            capitulo.setBackgroundColor(BaseColor.BLUE);
            capitulo.setColspan(9);
            table.addCell(capitulo);

            for (int i = 0; i < cap.size(); i++) {

                table.addCell(cap.get(i).getPubNombreAutor());
                table.addCell(cap.get(i).obtenerNombrePub());
                table.addCell(cap.get(i).getPubEstado());
                table.addCell(cap.get(i).getPubFechaPublicacion().toString());

            }

            document.add(table);

        } catch (Exception ex) {
            System.out.println("Error " + ex.getMessage());
        }
        document.close();

        return dao.findAll();
    }

    public void llenarListas() {
        rev = new ArrayList();
        lib = new ArrayList();
        con = new ArrayList();
        cap = new ArrayList();

        for (int i = 0; i < dao.findAll().size(); i++) {
            if (dao.findAll().get(i).getPubTipoPublicacion().equals("revista")) {
                rev.add(dao.findAll().get(i));
            }
            if (dao.findAll().get(i).getPubTipoPublicacion().equals("libro")) {
                lib.add(dao.findAll().get(i));
            }
            if (dao.findAll().get(i).getPubTipoPublicacion().equals("congreso")) {
                con.add(dao.findAll().get(i));
            }
            if (dao.findAll().get(i).getPubTipoPublicacion().equals("capitulo_libro")) {
                cap.add(dao.findAll().get(i));
            }
        }

    }

    public void PublicacionesEst() throws DocumentException, FileNotFoundException {
        System.out.println("Nombre" + "\t" + nombre);
        String[] usuario = nombre.split("@");
        String username = usuario[0];
        Estudiante est = dao.obtenerEstudianteP(username);
        List<Publicacion> ret = est.getPublicacionList();
        for (int i = 0; i < ret.size(); i++) {
            System.out.println("Publicaciones" + "\t" + i + "\t" + "Nombre" + "\t" + ret.get(i).obtenerNombrePub());
        }
        Document document = new Document();
        String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        System.out.println("path" + realPath);
        String conso = realPath + "resources\\pdf\\" + "ReportePersonal" + ".pdf";
        FileOutputStream archivo = new FileOutputStream(conso);
        try {
            PdfWriter.getInstance(document, archivo);
            document.open();
            document.add(new Paragraph("Reporte personal de publicaciones\n"));
            document.add(new Paragraph("Estudiante: " + est.getEstNombre() + " " + est.getEstApellido()));
            DateFormat formatter = new SimpleDateFormat("dd/MM/yy '-' hh:mm:ss");
            Date currentDate = new Date();
            String date = formatter.format(currentDate);
            document.add(new Paragraph("Fecha Generado: " + date));
            document.add(new Paragraph("\n"));
            PdfPTable table = new PdfPTable(4);
            table.setTotalWidth(new float[]{80, 72, 110, 95});
            table.setLockedWidth(true);
            table.addCell("Tipo Publicacion");
            table.addCell("Nombre Publicacion");
            table.addCell("Estado de visado");
            table.addCell("Fecha de Registro");
            PdfPCell reporte = new PdfPCell(new Paragraph("Publicaciones",
                    FontFactory.getFont("arial", // fuente
                            8, // tamaño
                            Font.BOLD, // estilo
                            BaseColor.WHITE)));
            reporte.setHorizontalAlignment(Element.ALIGN_CENTER);
            reporte.setBackgroundColor(BaseColor.BLUE);
            reporte.setColspan(9);
            table.addCell(reporte);

            for (int i = 0; i < ret.size(); i++) {

                table.addCell(ret.get(i).getPubTipoPublicacion());
                table.addCell(ret.get(i).obtenerNombrePub());
                table.addCell(ret.get(i).getPubVisado());
                table.addCell(ret.get(i).getPubFechaPublicacion().toString());

            }
            document.add(table);

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }

        document.close();
    }

    public void ArticulosAnio() throws FileNotFoundException {

        auxiliar= new ArrayList();
        for (int i = 0; i < dao.findAll().size(); i++) {

            String an = dao.findAll().get(i).getPubFechaRegistro().toLocaleString();
            String a[] = an.split(" ");
            String fecha = a[0];
            String consul[] = fecha.split("/");
            String retorno = consul[2];
            System.out.println("anio" + retorno);

            if (anios.equals(retorno)) {
                System.out.println("Publicacion" + dao.findAll().get(i).obtenerNombrePub() + "\t" + i);
                auxiliar.add(dao.findAll().get(i));
            }

        }
        Document document = new Document();
        String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        System.out.println("path" + realPath);
        String conso = realPath + "resources\\pdf\\" + "ConsolidadoPorAño" + ".pdf";
        FileOutputStream archivo = new FileOutputStream(conso);
        try {
            PdfWriter.getInstance(document, archivo);
            document.open();
            document.add(new Paragraph("Reporte articulos por año\n"));
            DateFormat formatter = new SimpleDateFormat("dd/MM/yy '-' hh:mm:ss");
            Date currentDate = new Date();
            String date = formatter.format(currentDate);
            document.add(new Paragraph("Fecha Generado: " + date));
            document.add(new Paragraph("\n"));
            PdfPTable table = new PdfPTable(4);
            table.setTotalWidth(new float[]{80, 72, 110, 95});
            table.setLockedWidth(true);
            table.addCell("Tipo Publicacion");
            table.addCell("Nombre Publicacion");
            table.addCell("Estado de visado");
            table.addCell("Fecha de Registro");
            PdfPCell reporte = new PdfPCell(new Paragraph("Publicaciones",
                    FontFactory.getFont("arial", // fuente
                            8, // tamaño
                            Font.BOLD, // estilo
                            BaseColor.WHITE)));
            reporte.setHorizontalAlignment(Element.ALIGN_CENTER);
            reporte.setBackgroundColor(BaseColor.BLUE);
            reporte.setColspan(9);
            table.addCell(reporte);

            for (int i = 0; i < auxiliar.size(); i++) {

                table.addCell(auxiliar.get(i).getPubTipoPublicacion());
                table.addCell(auxiliar.get(i).obtenerNombrePub());
                table.addCell(auxiliar.get(i).getPubVisado());
                table.addCell(auxiliar.get(i).getPubFechaPublicacion().toString());

            }
            document.add(table);

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }

        document.close();
    }

    public void obtenerEstudianteAnio() throws FileNotFoundException {
        auxiliar1= new ArrayList();
        System.out.println("Nombre" + "\t" + nombree);
        System.out.println("Anio" + "\t" + aniose);
        String[] usuario = nombree.split("@");
        String username = usuario[0];
        Estudiante est = dao.obtenerEstudianteAnio(username);
        List<Publicacion> ret = est.getPublicacionList();

        for (int i = 0; i < ret.size(); i++) {

            String an = dao.findAll().get(i).getPubFechaRegistro().toLocaleString();
            String a[] = an.split(" ");
            String fecha = a[0];
            String consul[] = fecha.split("/");
            String retorno = consul[2];
            System.out.println("anio" + retorno);
            if (aniose.equals(retorno)) {
                System.out.println("Publicacion" + ret.get(i).obtenerNombrePub() + "\t" + i + "Publicador" + "\t" + est.getEstNombre() + "\t" + est.getEstApellido());
                auxiliar1.add(ret.get(i));
            }

        }
        Document document = new Document();
        String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        System.out.println("path" + realPath);
        String conso = realPath + "resources\\pdf\\" + "ConsolidadoPorAñoEstudiante" + ".pdf";
        FileOutputStream archivo = new FileOutputStream(conso);
        try {
            PdfWriter.getInstance(document, archivo);
            document.open();
            document.add(new Paragraph("Reporte articulos por año de un Estudiante\n"));
            document.add(new Paragraph("Estudiante: " + est.getEstNombre() + " " + est.getEstApellido()));
            DateFormat formatter = new SimpleDateFormat("dd/MM/yy '-' hh:mm:ss");
            Date currentDate = new Date();
            String date = formatter.format(currentDate);
            document.add(new Paragraph("Fecha Generado: " + date));
            document.add(new Paragraph("\n"));
            PdfPTable table = new PdfPTable(4);
            table.setTotalWidth(new float[]{80, 72, 110, 95});
            table.setLockedWidth(true);
            table.addCell("Tipo Publicacion");
            table.addCell("Nombre Publicacion");
            table.addCell("Estado de visado");
            table.addCell("Fecha de Registro");
            PdfPCell reporte = new PdfPCell(new Paragraph("Publicaciones",
                    FontFactory.getFont("arial", // fuente
                            8, // tamaño
                            Font.BOLD, // estilo
                            BaseColor.WHITE)));
            reporte.setHorizontalAlignment(Element.ALIGN_CENTER);
            reporte.setBackgroundColor(BaseColor.BLUE);
            reporte.setColspan(9);
            table.addCell(reporte);

            for (int i = 0; i < auxiliar1.size(); i++) {

                table.addCell(auxiliar1.get(i).getPubTipoPublicacion());
                table.addCell(auxiliar1.get(i).obtenerNombrePub());
                table.addCell(auxiliar1.get(i).getPubVisado());
                table.addCell(auxiliar1.get(i).getPubFechaPublicacion().toString());

            }
            document.add(table);

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }

        document.close();
        
    }

    //la variable que se debe poner en la vista se llama semestre, y la funcion que se debe llamar es ArticulosSemestre
    public void ArticulosSemestre() throws FileNotFoundException {
        primerse = new ArrayList();
        segundose = new ArrayList();
        System.out.println("SEMESTRE" + semestre);
        for (int i = 0; i < dao.findAll().size(); i++) {

            String an = dao.findAll().get(i).getPubFechaRegistro().toLocaleString();
            String a[] = an.split(" ");
            String fecha = a[0];
            String consul[] = fecha.split("/");
            String retorno = consul[1];

            if (retorno.equals("01") || retorno.equals("02") || retorno.equals("03") || retorno.equals("04")
                    || retorno.equals("05") || retorno.equals("06")) {

                primerse.add(dao.findAll().get(i));
            }
            if (retorno.equals("07") || retorno.equals("08") || retorno.equals("09") || retorno.equals("10")
                    || retorno.equals("11") || retorno.equals("12")) {

                segundose.add(dao.findAll().get(i));
            }

        }
        if (semestre.equals("1")) {
            for (int i = 0; i < primerse.size(); i++) {
                if (primerse.isEmpty()) {
                    System.out.println("No hay nada que mostrar");

                } else {
                    System.out.println("Publicacion primer semestre" + primerse.get(i).obtenerNombrePub());
                }
            }
        }
        if (semestre.equals("2")) {
            for (int i = 0; i < segundose.size(); i++) {
                if (segundose.isEmpty()) {
                    System.out.println("No hay nada que mostrar");

                } else {
                    System.out.println("Publicacion segundo semestre" + segundose.get(i).obtenerNombrePub());
                }
            }
        }
        if(semestre.equals("1")){
        Document document = new Document();
        String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        System.out.println("path" + realPath);
        String conso = realPath + "resources\\pdf\\" + "ConsolidadoPorSemestreUno" + ".pdf";
        FileOutputStream archivo = new FileOutputStream(conso);
        try {
            PdfWriter.getInstance(document, archivo);
            document.open();
            document.add(new Paragraph("Reporte articulos semestre 1 \n"));
            DateFormat formatter = new SimpleDateFormat("dd/MM/yy '-' hh:mm:ss");
            Date currentDate = new Date();
            String date = formatter.format(currentDate);
            document.add(new Paragraph("Fecha Generado: " + date));
            document.add(new Paragraph("\n"));
            PdfPTable table = new PdfPTable(4);
            table.setTotalWidth(new float[]{80, 72, 110, 95});
            table.setLockedWidth(true);
            table.addCell("Tipo Publicacion");
            table.addCell("Nombre Publicacion");
            table.addCell("Estado de visado");
            table.addCell("Fecha de Registro");
            PdfPCell reporte = new PdfPCell(new Paragraph("Publicaciones",
                    FontFactory.getFont("arial", // fuente
                            8, // tamaño
                            Font.BOLD, // estilo
                            BaseColor.WHITE)));
            reporte.setHorizontalAlignment(Element.ALIGN_CENTER);
            reporte.setBackgroundColor(BaseColor.BLUE);
            reporte.setColspan(9);
            table.addCell(reporte);

            for (int i = 0; i < primerse.size(); i++) {

                table.addCell(primerse.get(i).getPubTipoPublicacion());
                table.addCell(primerse.get(i).obtenerNombrePub());
                table.addCell(primerse.get(i).getPubVisado());
                table.addCell(primerse.get(i).getPubFechaPublicacion().toString());

            }
            document.add(table);

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }

        document.close();
        
   }
        if(semestre.equals("2")){
            Document document = new Document();
        String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        System.out.println("path" + realPath);
        String conso = realPath + "resources\\pdf\\" + "ConsolidadoPorSemestreDos" + ".pdf";
        FileOutputStream archivo = new FileOutputStream(conso);
        try {
            PdfWriter.getInstance(document, archivo);
            document.open();
            document.add(new Paragraph("Reporte articulos semeestre 2\n"));
            DateFormat formatter = new SimpleDateFormat("dd/MM/yy '-' hh:mm:ss");
            Date currentDate = new Date();
            String date = formatter.format(currentDate);
            document.add(new Paragraph("Fecha Generado: " + date));
            document.add(new Paragraph("\n"));
            PdfPTable table = new PdfPTable(4);
            table.setTotalWidth(new float[]{80, 72, 110, 95});
            table.setLockedWidth(true);
            table.addCell("Tipo Publicacion");
            table.addCell("Nombre Publicacion");
            table.addCell("Estado de visado");
            table.addCell("Fecha de Registro");
            PdfPCell reporte = new PdfPCell(new Paragraph("Publicaciones",
                    FontFactory.getFont("arial", // fuente
                            8, // tamaño
                            Font.BOLD, // estilo
                            BaseColor.WHITE)));
            reporte.setHorizontalAlignment(Element.ALIGN_CENTER);
            reporte.setBackgroundColor(BaseColor.BLUE);
            reporte.setColspan(9);
            table.addCell(reporte);

            for (int i = 0; i < segundose.size(); i++) {

                table.addCell(segundose.get(i).getPubTipoPublicacion());
                table.addCell(segundose.get(i).obtenerNombrePub());
                table.addCell(segundose.get(i).getPubVisado());
                table.addCell(segundose.get(i).getPubFechaPublicacion().toString());

            }
            document.add(table);

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }

        document.close();
        }
    }

    //la variables que se deben poner en la vista se llaman semestree y estudiante, y la funcion que se debe llamar es PublicacionSemestreEstudiante
    public void PublicacionSemestreEstudiante() throws FileNotFoundException {
        primersee = new ArrayList();
        segundosee = new ArrayList();
        System.out.println("SEMESTRE" + semestree);
        System.out.println("Nombre" + "\t" + estudiante);
        String[] usuario = estudiante.split("@");
        String username = usuario[0];
        Estudiante est = dao.obtenerEstudianteAnio(username);
        List<Publicacion> ret = est.getPublicacionList();

        for (int i = 0; i < ret.size(); i++) {

            String an = ret.get(i).getPubFechaRegistro().toLocaleString();
            String a[] = an.split(" ");
            String fecha = a[0];
            String consul[] = fecha.split("/");
            String retorno = consul[1];
            System.out.println("mes" + retorno);

            if (retorno.equals("01") || retorno.equals("02") || retorno.equals("03") || retorno.equals("04")
                    || retorno.equals("05") || retorno.equals("06")) {

                primersee.add(ret.get(i));
            }
            if (retorno.equals("07") || retorno.equals("08") || retorno.equals("09") || retorno.equals("10")
                    || retorno.equals("11") || retorno.equals("12")) {

                segundosee.add(ret.get(i));
            }

        }
        if (semestree.equals("1")) {
            for (int i = 0; i < primersee.size(); i++) {
                if (primersee.isEmpty()) {
                    System.out.println("No hay nada que mostrar");

                } else {
                    System.out.println("Publicacion primer semestre" + primersee.get(i).obtenerNombrePub());
                }
            }
        }
        if (semestree.equals("2")) {
            for (int i = 0; i < segundosee.size(); i++) {
                if (segundosee.isEmpty()) {
                    System.out.println("No hay nada que mostrar");

                } else {
                    System.out.println("Publicacion segundo semestre" + segundosee.get(i).obtenerNombrePub());
                }
            }
        }
        if(semestree.equals("1")){
        Document document = new Document();
        String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        System.out.println("path" + realPath);
        String conso = realPath + "resources\\pdf\\" + "ConsolidadoPorSemestreUnoPorEstudiante" + ".pdf";
        FileOutputStream archivo = new FileOutputStream(conso);
        try {
            PdfWriter.getInstance(document, archivo);
            document.open();
            document.add(new Paragraph("Reporte articulos semestre 1 por estudiante \n"));
            document.add(new Paragraph("Estudiante: " + est.getEstNombre() + " " + est.getEstApellido()));
            DateFormat formatter = new SimpleDateFormat("dd/MM/yy '-' hh:mm:ss");
            Date currentDate = new Date();
            String date = formatter.format(currentDate);
            document.add(new Paragraph("Fecha Generado: " + date));
            document.add(new Paragraph("\n"));
            PdfPTable table = new PdfPTable(4);
            table.setTotalWidth(new float[]{80, 72, 110, 95});
            table.setLockedWidth(true);
            table.addCell("Tipo Publicacion");
            table.addCell("Nombre Publicacion");
            table.addCell("Estado de visado");
            table.addCell("Fecha de Registro");
            PdfPCell reporte = new PdfPCell(new Paragraph("Publicaciones",
                    FontFactory.getFont("arial", // fuente
                            8, // tamaño
                            Font.BOLD, // estilo
                            BaseColor.WHITE)));
            reporte.setHorizontalAlignment(Element.ALIGN_CENTER);
            reporte.setBackgroundColor(BaseColor.BLUE);
            reporte.setColspan(9);
            table.addCell(reporte);

            for (int i = 0; i < primersee.size(); i++) {

                table.addCell(primersee.get(i).getPubTipoPublicacion());
                table.addCell(primersee.get(i).obtenerNombrePub());
                table.addCell(primersee.get(i).getPubVisado());
                table.addCell(primersee.get(i).getPubFechaPublicacion().toString());

            }
            document.add(table);

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }

        document.close();
        
   }
        if(semestree.equals("2")){
            Document document = new Document();
        String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        System.out.println("path" + realPath);
        String conso = realPath + "resources\\pdf\\" + "ConsolidadoPorSemestreDosPorEstudiante" + ".pdf";
        FileOutputStream archivo = new FileOutputStream(conso);
        try {
            PdfWriter.getInstance(document, archivo);
            document.open();
            document.add(new Paragraph("Reporte articulos semestre 2 por estudiante \n"));
            document.add(new Paragraph("Estudiante: " + est.getEstNombre() + " " + est.getEstApellido()));
            DateFormat formatter = new SimpleDateFormat("dd/MM/yy '-' hh:mm:ss");
            Date currentDate = new Date();
            String date = formatter.format(currentDate);
            document.add(new Paragraph("Fecha Generado: " + date));
            document.add(new Paragraph("\n"));
            PdfPTable table = new PdfPTable(4);
            table.setTotalWidth(new float[]{80, 72, 110, 95});
            table.setLockedWidth(true);
            table.addCell("Tipo Publicacion");
            table.addCell("Nombre Publicacion");
            table.addCell("Estado de visado");
            table.addCell("Fecha de Registro");
            PdfPCell reporte = new PdfPCell(new Paragraph("Publicaciones",
                    FontFactory.getFont("arial", // fuente
                            8, // tamaño
                            Font.BOLD, // estilo
                            BaseColor.WHITE)));
            reporte.setHorizontalAlignment(Element.ALIGN_CENTER);
            reporte.setBackgroundColor(BaseColor.BLUE);
            reporte.setColspan(9);
            table.addCell(reporte);

            for (int i = 0; i < segundosee.size(); i++) {

                table.addCell(segundosee.get(i).getPubTipoPublicacion());
                table.addCell(segundosee.get(i).obtenerNombrePub());
                table.addCell(segundosee.get(i).getPubVisado());
                table.addCell(segundosee.get(i).getPubFechaPublicacion().toString());

            }
            document.add(table);

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }

        document.close();
        }
    }

}
