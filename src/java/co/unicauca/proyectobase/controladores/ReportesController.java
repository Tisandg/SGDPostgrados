package co.unicauca.proyectobase.controladores;

import co.unicauca.proyectobase.dao.EstudianteFacade;
import co.unicauca.proyectobase.dao.PublicacionFacade;
import co.unicauca.proyectobase.entidades.Estudiante;
import co.unicauca.proyectobase.entidades.Publicacion;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileInputStream;
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
import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * @author santilopez94
 * @version 1.0
 * 
 * @author tisandg
 * @version 2.0
 */

@Named(value = "reportesController")
@ManagedBean
@SessionScoped
public class ReportesController implements Serializable {

    @EJB
    private EstudianteFacade daoEstudiante;
    @EJB
    private PublicacionFacade daoPublicacion;
    
    private String tipoReporte = "";
    private String tipoTiempo = "";
    private String codigoEstudiante;
    private String anios="";
    private String semestre="";
    private int tiposPublicaciones = 4;

    List<Publicacion> listaPublicaciones;
    ArrayList<Publicacion> listaRevista;
    ArrayList<Publicacion> listaLibro;
    ArrayList<Publicacion> listaCongreso;
    ArrayList<Publicacion> listaCapituloLibro;
    
    /**
     * Procedimiento que se encarga de decidir que tipo de reporte generar.
     * Dependiendo de la opcion elegida ejecuta los respectivos procedimiento
     * para generar el reporte ya sea global o por estudiante
     * @throws FileNotFoundException 
     */
    public void generarReporte() throws FileNotFoundException{
        /*   Averiguamos el tipo de reporte   */
        if(tipoReporte.equals("global")){
            reporteGlobal();
        }
        if(tipoReporte.equals("porEstudiante")){
            reportePorEstudiante();
        }
    }
    
    /**
     * Procedimiento para generar un reporte global de todas las publicaciones
     * registradas por los estudiantes en un determinado tiempo. Se buscan las 
     * publicaciones registradas en el tiempo especificado y luego se agregan 
     * a un archivo pdf.
     * @throws FileNotFoundException 
     */
    public void reporteGlobal() throws FileNotFoundException{
        
        String nombreTipoPub="";
        int i,j,tamPublicaciones;
        obtenerPublicaciones();
        
        /*Nombre del archivo pdf*/
        String nombreReporte = "Reporte_global_"+anios;
        if(tipoTiempo.equals("semestre")){
            nombreReporte += "_"+semestre;
        }
        nombreReporte += ".pdf";
        
        String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        System.out.println("path" + realPath);
        String rutaReporte = realPath + "resources\\pdf\\" + nombreReporte;
        
        try {
            
            tamPublicaciones = listaPublicaciones.size();
            System.out.println("tamPublicaciones: "+tamPublicaciones);
            
            if(tamPublicaciones > 0){
                FileOutputStream archivoReporte = new FileOutputStream(rutaReporte);
                Document documento = new Document(PageSize.A4.rotate(), 10, 10, 10, 10);
                PdfWriter.getInstance(documento, archivoReporte);
                documento.open();
                documento.add(new Paragraph("Consolidado de Publicaciones\n"));
                DateFormat formatter = new SimpleDateFormat("dd/MM/yy '-' hh:mm:ss");
                String date = formatter.format(new Date());
                documento.add(new Paragraph("Fecha Generado: " + date));
                documento.add(new Paragraph("\n"));

                PdfPTable table = new PdfPTable(7);
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                table.setTotalWidth(new float[]{1, 1, 1, 1, 1, 1, 1});
                
                table.addCell("Autor");
                table.addCell("Titulo de la publicacion");
                table.addCell("Publicado en");
                table.addCell("Categoria/Tipo");
                table.addCell("Estado de la Publicacion");
                table.addCell("Fecha de Registro");
                table.addCell("Creditos");
                
                table.setHeaderRows(1);
                PdfPCell[] cells = table.getRow(0).getCells(); 
                for (j=0;j<cells.length;j++){
                   cells[j].setBackgroundColor(BaseColor.LIGHT_GRAY);
                }
                
                String nombreTipo="";
                String tipoCategoria="";
                String publicadoEn="";
                j=0;
                
                for ( i = 0; i < tiposPublicaciones; i++) {
                    /*Este orden lo determina la forma en que mysql 
                    ordena por tipo de publicacion de forma descendente*/
                    if(i==0){ 
                        nombreTipoPub = "Revista";
                        nombreTipo="revista";
                    }
                    if(i==1){ 
                        nombreTipoPub = "Libro"; 
                        nombreTipo="libro";
                    }
                    if(i==2){ 
                        nombreTipoPub = "Congreso";
                        nombreTipo="congreso";
                    }
                    if(i==3){ 
                        nombreTipoPub = "Capitulo libro";
                        nombreTipo="capitulo_libro";
                    }
                    
                    PdfPCell encabezadoTipoPub = new PdfPCell(new Paragraph(nombreTipoPub,
                            FontFactory.getFont("arial", // fuente
                                    8, // tamaño
                                    Font.BOLD, // estilo
                                    BaseColor.WHITE)));
                    encabezadoTipoPub.setHorizontalAlignment(Element.ALIGN_CENTER);
                    encabezadoTipoPub.setBackgroundColor(BaseColor.BLUE);
                    encabezadoTipoPub.setColspan(9);
                    table.addCell(encabezadoTipoPub);
                    
                    do{
                        tipoCategoria = "Sin categoria";
                        table.addCell(listaPublicaciones.get(j).getPubNombreAutor());
                        table.addCell(listaPublicaciones.get(j).obtenerNombrePub());
                        if(i==0){ 
                            tipoCategoria = listaPublicaciones.get(j).getRevista().getRevCategoria();
                            publicadoEn = listaPublicaciones.get(j).getRevista().getRevNombreRevista();
                        }
                        if(i==1){ 
                            publicadoEn = listaPublicaciones.get(j).getLibro().getLibTituloLibro();
                        }
                        if(i==2){ 
                            tipoCategoria = listaPublicaciones.get(j).getCongreso().getCongTipoCongreso();
                            publicadoEn = listaPublicaciones.get(j).getCongreso().getCongNombreEvento();
                        }
                        if(i==3){ 
                            publicadoEn = listaPublicaciones.get(j).getCapituloLibro().getCaplibTituloLibro();
                        }
                        table.addCell(publicadoEn);
                        table.addCell(tipoCategoria);
                        table.addCell(listaPublicaciones.get(j).getPubEstado());
                        table.addCell(listaPublicaciones.get(j).getPubFechaPublicacion().toString());
                        
                        if(listaPublicaciones.get(j).getPubVisado().equals("aprobado")){
                            table.addCell(""+listaPublicaciones.get(j).getPubCreditos());
                        }else{
                            table.addCell("No asignados");
                        }
                        j++;
                    }while(j<tamPublicaciones && listaPublicaciones.get(j).getPubTipoPublicacion().equals(nombreTipo));
                }
                documento.add(table);
                documento.close();
                System.out.println("Reporte global generado");
                //descargarPdf(nombreReporte);
            }else{
                System.out.println("No hay datos para generar el reporte");
            }

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    /**
     * Procedimiento que genera el reporte de las publicaciones registradas por
     * un estudiante en particular en un determinado tiempo. Se busca todas las 
     * publicaciones registradas en el tiempo especificado y luego se agregan en 
     * un archivo pdf.
     * @throws FileNotFoundException 
     */
    public void reportePorEstudiante() throws FileNotFoundException{
        int i, j, tamListaPublicaciones = 0;
        System.out.println("Codigo estudiante" + "\t" + codigoEstudiante);
        Estudiante estudiante = daoEstudiante.buscarPorCodigo(codigoEstudiante);
        int idEstudiante = estudiante.getEstIdentificador();
        
        List<Publicacion> listaPublicaciones = obtenerPublicacionesEstudiante(idEstudiante);
        tamListaPublicaciones = listaPublicaciones.size();
        
        /*Nombre del archivo pdf*/
        String nombreReporte = "Reporte_estudiante_"+anios;
        if(tipoTiempo.equals("semestre")){
            nombreReporte += "_"+semestre;
        }
        nombreReporte += ".pdf";
        
        
        String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        System.out.println("path" + realPath);
        String rutaReporte = realPath + "resources\\pdf\\" + nombreReporte;
        try {
            if(tamListaPublicaciones>0){
                
                FileOutputStream archivo = new FileOutputStream(rutaReporte);
                Document document = new Document(PageSize.A4, 10, 10, 10, 10);
                PdfWriter.getInstance(document, archivo);
                document.open();
                document.add(new Paragraph("Reporte personal de publicaciones\n"));
                document.add(new Paragraph("Estudiante: " + estudiante.getEstNombre() + " " + estudiante.getEstApellido()));
                DateFormat formatter = new SimpleDateFormat("dd/MM/yy '-' hh:mm:ss");
                String date = formatter.format(new Date());
                document.add(new Paragraph("Fecha Generado: " + date));
                document.add(new Paragraph("\n"));

                PdfPTable table = new PdfPTable(7);
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                table.setTotalWidth(new float[]{1, 1, 1, 1, 1, 1, 1});

                table.addCell("Autor");
                table.addCell("Titulo de la publicacion");
                table.addCell("Publicado en");
                table.addCell("Categoria/Tipo");
                table.addCell("Estado de la Publicacion");
                table.addCell("Fecha de Registro");
                table.addCell("Creditos");

                table.setHeaderRows(1);
                PdfPCell[] cells = table.getRow(0).getCells(); 
                for (j=0;j<cells.length;j++){
                   cells[j].setBackgroundColor(BaseColor.LIGHT_GRAY);
                }

                String nombreTipo="";
                String tipoCategoria="";
                String publicadoEn="";
                String nombreTipoPub="";
                j=0;


                for ( i = 0; i < tiposPublicaciones; i++) {
                    /*Este orden lo determina la forma en que mysql 
                    ordena por tipo de publicacion de forma descendente*/
                    if(i==0){ 
                        nombreTipoPub = "Revista";
                        nombreTipo="revista";
                    }
                    if(i==1){ 
                        nombreTipoPub = "Libro"; 
                        nombreTipo="libro";
                    }
                    if(i==2){ 
                        nombreTipoPub = "Congreso";
                        nombreTipo="congreso";
                    }
                    if(i==3){ 
                        nombreTipoPub = "Capitulo libro";
                        nombreTipo="capitulo_libro";
                    }

                    PdfPCell encabezadoTipoPub = new PdfPCell(new Paragraph(nombreTipoPub,
                            FontFactory.getFont("arial", // fuente
                                    8, // tamaño
                                    Font.BOLD, // estilo
                                    BaseColor.WHITE)));
                    encabezadoTipoPub.setHorizontalAlignment(Element.ALIGN_CENTER);
                    encabezadoTipoPub.setBackgroundColor(BaseColor.BLUE);
                    encabezadoTipoPub.setColspan(9);
                    table.addCell(encabezadoTipoPub);

                    while(j<tamListaPublicaciones && listaPublicaciones.get(j).getPubTipoPublicacion().equals(nombreTipo)){
                        tipoCategoria = "Sin categoria";
                        table.addCell(listaPublicaciones.get(j).getPubNombreAutor());
                        table.addCell(listaPublicaciones.get(j).obtenerNombrePub());
                        if(i==0){ 
                            tipoCategoria = listaPublicaciones.get(j).getRevista().getRevCategoria();
                            publicadoEn = listaPublicaciones.get(j).getRevista().getRevNombreRevista();
                        }
                        if(i==1){ 
                            publicadoEn = listaPublicaciones.get(j).getLibro().getLibTituloLibro();
                        }
                        if(i==2){ 
                            tipoCategoria = listaPublicaciones.get(j).getCongreso().getCongTipoCongreso();
                            publicadoEn = listaPublicaciones.get(j).getCongreso().getCongNombreEvento();
                        }
                        if(i==3){ 
                            publicadoEn = listaPublicaciones.get(j).getCapituloLibro().getCaplibTituloLibro();
                        }
                        table.addCell(publicadoEn);
                        table.addCell(tipoCategoria);
                        table.addCell(listaPublicaciones.get(j).getPubEstado());
                        table.addCell(listaPublicaciones.get(j).getPubFechaPublicacion().toString());

                        if(listaPublicaciones.get(j).getPubVisado().equals("aprobado")){
                            table.addCell(""+listaPublicaciones.get(j).getPubCreditos());
                        }else{
                            table.addCell("No asignados");
                        }
                        j++;
                    }
                }
                document.add(table);
                document.close();
                System.out.println("Reporte por estudiante generado");
            }else{
                System.out.println("No hay datos para generar el reporte");
            }

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
        
    }
    
    /**
     * Funcion que devuelve el listado de publicaciones registradas por un
     * estudiante en un determinado tiempo. Este tiempo depende de las opciones
     * escogidas(año, semestre o inicio estudios) y el tiempo especificado.
     * @return lista publicaciones
     */
    public List<Publicacion> obtenerPublicacionesEstudiante(int idEstudiante){
        List<Publicacion> publicaciones = new ArrayList();
        if(tipoTiempo.equals("año")){
            publicaciones = daoPublicacion.publicacionesEstudiantePorAnio(idEstudiante, 
                    Integer.parseInt(anios));
        }
        if(tipoTiempo.equals("semestre")){
            publicaciones = daoPublicacion.publicacionesEstudiantePorSemestre(idEstudiante, 
                    Integer.parseInt(anios),Integer.parseInt(semestre));
        }
        if(tipoTiempo.equals("inicioEstudios")){
            publicaciones = daoPublicacion.ListadoPublicacionEst(idEstudiante);
        }
        return publicaciones;
    }
    
    /**
     * Metodo para obtener las publicaciones registradas por los estudiantes
     * en un determinado tiempo. Estos publicaciones se almacenan en los 
     * ArrayList declarados como atributos. Para cada categoria, un ArrayList
     */
    public void obtenerPublicaciones() {
        
        listaRevista = new ArrayList();
        listaLibro = new ArrayList();
        listaCongreso = new ArrayList();
        listaCapituloLibro = new ArrayList();
        
        System.out.println("Años son "+anios);
        if(tipoTiempo.equals("año")){
            listaPublicaciones = daoPublicacion.publicacionesPorAnio(Integer.parseInt(anios));
        }
        if(tipoTiempo.equals("semestre")){
            listaPublicaciones = daoPublicacion.publicacionesPorSemestre(Integer.parseInt(anios), 
                    Integer.parseInt(semestre));
        }
    }
    
    public void descargarPdf(String nombre) throws FileNotFoundException, IOException{
        String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        /*Pendiente lo del separador segun el SO*/
        realPath += "\\resources\\"+nombre;
        File ficheroPDF = new File(realPath);
        FacesContext ctx = FacesContext.getCurrentInstance();
        FileInputStream fis = new FileInputStream(ficheroPDF);
        byte[] bytes = new byte[1000];
        int read = 0;

        if (!ctx.getResponseComplete()) {
            String fileName = ficheroPDF.getName();
            String contentType = "application/pdf";
            HttpServletResponse response
                    = (HttpServletResponse) ctx.getExternalContext().getResponse();

            response.setContentType(contentType);

            response.setHeader("Content-Disposition",
                    "attachment;filename=\"" + nombre + "\"");

            ServletOutputStream out = response.getOutputStream();

            while ((read = fis.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
            System.out.println("\nDescargado\n");
            ctx.responseComplete();
        }
    }
    
    /**
     * Getters and Setters 
     */
    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public String getAnios() {
        return anios;
    }

    public void setAnios(String anios) {
        this.anios = anios;
    }

    public String getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(String tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public String getTipoTiempo() {
        return tipoTiempo;
    }

    public void setTipoTiempo(String tipoTiempo) {
        this.tipoTiempo = tipoTiempo;
    }

    public String getCodigoEstudiante() {
        return codigoEstudiante;
    }

    public void setCodigoEstudiante(String codigoEstudiante) {
        this.codigoEstudiante = codigoEstudiante;
    }

    public void verificarDatos(){
        System.out.println("----------------------------------");
        System.out.println("año: "  + anios);
        System.out.println("semestre: " + semestre);
        System.out.println("codigo: " + codigoEstudiante);  
        System.out.println("----------------------------------");
                
    }
    
    
    public void limpiarTiempo(AjaxBehaviorEvent event){
        tipoTiempo = "";
    }
}
