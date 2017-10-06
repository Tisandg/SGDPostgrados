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
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    
    private String tipoReporte;
    private String tipoTiempo;
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
        
        //ArrayList<Publicacion> publicaciones = new ArrayList();
        String nombreTipoPub="";
        int i,j,tamPublicaciones;
        
        obtenerPublicaciones();
        
        /*Nombre del archivo pdf*/
        String nombreReporte = "Reporte_global_"+anios;
        if(tipoTiempo.equals("semestre")){
            nombreReporte += "_"+semestre;
        }
        nombreReporte += ".pdf";
        
        Document documento = new Document();
        String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        System.out.println("path" + realPath);
        String rutaReporte = realPath + "resources\\pdf\\" + nombreReporte;
        FileOutputStream archivoReporte = new FileOutputStream(rutaReporte);
        
        try {
            
            tamPublicaciones = listaPublicaciones.size();
            if(tamPublicaciones > 0){
                
                PdfWriter.getInstance(documento, archivoReporte);
                documento.open();
                documento.add(new Paragraph("Consolidado de Publicaciones\n"));
                DateFormat formatter = new SimpleDateFormat("dd/MM/yy '-' hh:mm:ss");
                String date = formatter.format(new Date());
                documento.add(new Paragraph("Fecha Generado: " + date));
                documento.add(new Paragraph("\n"));

                PdfPTable table = new PdfPTable(6);
                //float[] arreglo = {50,50,50,50,50,50};
                
                //table.setTotalWidth(arreglo);
                //table.setLockedWidth(true)//
                table.addCell("Autor");
                table.addCell("Titulo de la publicacion");
                table.addCell("Publicado en");
                table.addCell("Categoria/Tipo");
                table.addCell("Estado de la Publicacion");
                table.addCell("Fecha de Registro");
                //table.addCell("Creditos");
                
                j=0;
                String nombreTipo="";
                String tipoCategoria="";
                String publicadoEn="";
                
                for ( i = 0; i < tiposPublicaciones; i++) {
                 
                    /*Este orden lo determina la forma en que mysql 
                    ordena por tipo de publicacion de forma descendente*/
                    if(i==0){ 
                        nombreTipoPub = "Revista";
                        nombreTipo="revista";
                        //publicaciones = listaRevista;
                    }
                    if(i==1){ 
                        nombreTipoPub = "Libro"; 
                        nombreTipo="libro";
                        //publicaciones = listaLibro;
                    }
                    if(i==2){ 
                        nombreTipoPub = "Congreso";
                        nombreTipo="congreso";
                        //publicaciones = listaCongreso;
                    }
                    if(i==3){ 
                        nombreTipoPub = "Capitulo libro";
                        nombreTipo="capitulo_libro";
                        //publicaciones = listaCapituloLibro;
                    }
                    //tamPublicaciones = publicaciones.size();

                    PdfPCell encabezadoTipoPub = new PdfPCell(new Paragraph(nombreTipoPub,
                            FontFactory.getFont("arial", // fuente
                                    8, // tamaño
                                    Font.BOLD, // estilo
                                    BaseColor.WHITE)));
                    encabezadoTipoPub.setHorizontalAlignment(Element.ALIGN_CENTER);
                    encabezadoTipoPub.setBackgroundColor(BaseColor.BLUE);
                    encabezadoTipoPub.setColspan(9);
                    table.addCell(encabezadoTipoPub);
                    
                    
                    while(listaPublicaciones.get(j).getPubTipoPublicacion().equals(nombreTipo)){
                        table.addCell(listaPublicaciones.get(j).getPubNombreAutor());
                        table.addCell(listaPublicaciones.get(j).obtenerNombrePub());
                        if(i==0){ 
                            tipoCategoria = listaPublicaciones.get(j).getRevista().getRevCategoria();
                            publicadoEn = listaPublicaciones.get(j).getRevista().getRevNombreRevista();
                        }
                        if(i==1){ 
                            tipoCategoria = "Sin categoria";
                            publicadoEn = listaPublicaciones.get(j).getLibro().getLibTituloLibro();
                        }
                        if(i==2){ 
                            tipoCategoria = listaPublicaciones.get(j).getCongreso().getCongTipoCongreso();
                            publicadoEn = listaPublicaciones.get(j).getCongreso().getCongNombreEvento();
                        }
                        if(i==3){ 
                            tipoCategoria = "Sin categoria";
                            publicadoEn = listaPublicaciones.get(j).getCapituloLibro().getCaplibTituloLibro();
                        }
                        table.addCell(publicadoEn);
                        table.addCell(tipoCategoria);
                        table.addCell(listaPublicaciones.get(j).getPubEstado());
                        table.addCell(listaPublicaciones.get(j).getPubFechaPublicacion().toString());
                        
                        /*if(listaPublicaciones.get(j).getPubVisado().equals("aprobado")){
                            table.addCell(""+listaPublicaciones.get(j).getPubCreditos());
                        }else{
                            table.addCell("No asignados");
                        }*/
                        j++;
                    }
                    /*Añadimos los datos de cada publicacion en la tabla*/
                    /*for ( j = 0; j < tamPublicaciones; j++) {
                        Publicacion publicacion = listaPublicaciones.get(j);
                        if(publicacion.getPubTipoPublicacion().equals("revista")){
                        }
                        if(publicacion.getPubTipoPublicacion().equals("congreso")){
                        }
                        if(publicacion.getPubTipoPublicacion().equals("revista")){
                        }
                        if(publicacion.getPubTipoPublicacion().equals("revista")){
                        }
                        table.addCell(listaPublicaciones.get(j).getPubNombreAutor());
                        table.addCell(listaPublicaciones.get(j).obtenerNombrePub());
                        if(i==0){ 
                            table.addCell(listaPublicaciones.get(j).getRevista().getRevNombreRevista());
                            table.addCell(listaPublicaciones.get(j).getRevista().getRevCategoria());
                        }
                        if(i==1){ 
                            table.addCell(listaPublicaciones.get(j).getCongreso().getCongNombreEvento());
                            table.addCell(listaPublicaciones.get(j).getCongreso().getCongTipoCongreso());
                        }
                        if(i==2){ 
                            table.addCell(listaPublicaciones.get(j).getLibro().getLibTituloLibro());
                            table.addCell("Sin tipo de libro");
                        }
                        if(i==3){ 
                            table.addCell(listaPublicaciones.get(j).getCapituloLibro().getCaplibTituloLibro());
                            table.addCell("Sin tipo de libro");
                        }
                        table.addCell(listaPublicaciones.get(j).getPubEstado());
                        table.addCell(listaPublicaciones.get(j).getPubFechaPublicacion().toString());
                        if(listaPublicaciones.get(j).getPubVisado().equals("aprobado")){
                            table.addCell(""+listaPublicaciones.get(j).getPubCreditos());
                        }else{
                            table.addCell("No asignados");
                        }   
                    }*/
                }
                documento.add(table);
            }
            
        } catch (Exception ex) {
            System.out.println("Error " + ex.getMessage());
        }
        documento.close();
        System.out.println("Reporte global generado");
    }
    
    
    /**
     * Procedimiento que genera el reporte de las publicaciones registradas por
     * un estudiante en particular en un determinado tiempo. Se busca todas las 
     * publicaciones registradas en el tiempo especificado y luego se agregan en 
     * un archivo pdf.
     * @throws FileNotFoundException 
     */
    public void reportePorEstudiante() throws FileNotFoundException{
        int i, tamListaPublicaciones = 0;
        System.out.println("Codigo estudiante" + "\t" + codigoEstudiante);
        Estudiante estudiante = daoEstudiante.buscarPorCodigo(codigoEstudiante);
        int idEstudiante = estudiante.getEstIdentificador();
        List<Publicacion> listaPublicaciones = obtenerPublicacionesEstudiante(idEstudiante);

        /*Nombre del archivo pdf*/
        String nombreReporte = "Reporte_estudiante_"+anios;
        if(tipoTiempo.equals("semestre")){
            nombreReporte += "_"+semestre;
        }
        nombreReporte += ".pdf";
        
        Document document = new Document();
        String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        System.out.println("path" + realPath);
        String rutaReporte = realPath + "resources\\pdf\\" + nombreReporte;
        FileOutputStream archivo = new FileOutputStream(rutaReporte);
        
        try {
            PdfWriter.getInstance(document, archivo);
            document.open();
            document.add(new Paragraph("Reporte personal de publicaciones\n"));
            document.add(new Paragraph("Estudiante: " + estudiante.getEstNombre() + " " + estudiante.getEstApellido()));
            DateFormat formatter = new SimpleDateFormat("dd/MM/yy '-' hh:mm:ss");
            String date = formatter.format(new Date());
            document.add(new Paragraph("Fecha Generado: " + date));
            document.add(new Paragraph("\n"));
            
            PdfPTable table = new PdfPTable(4);
            table.setTotalWidth(new float[]{70, 72, 110, 95});
            table.setLockedWidth(true);
            table.addCell("Autor");
            table.addCell("Titulo de la publicacion");
            //table.addCell("Publicado en");
            //table.addCell("Categoria/Tipo");
            table.addCell("Estado de la Publicacion");
            table.addCell("Fecha de Registro");
            //table.addCell("Creditos");
                         
            PdfPCell reporte = new PdfPCell(new Paragraph("Publicaciones",
                    FontFactory.getFont("arial", // fuente
                            8, // tamaño
                            Font.BOLD, // estilo
                            BaseColor.WHITE)));
            reporte.setHorizontalAlignment(Element.ALIGN_CENTER);
            reporte.setBackgroundColor(BaseColor.BLUE);
            reporte.setColspan(9);
            table.addCell(reporte);

            tamListaPublicaciones = listaPublicaciones.size();
            for ( i = 0; i < tamListaPublicaciones; i++) {


                table.addCell(listaPublicaciones.get(i).getPubTipoPublicacion());
                table.addCell(listaPublicaciones.get(i).obtenerNombrePub());
                table.addCell(listaPublicaciones.get(i).getPubVisado());
                table.addCell(listaPublicaciones.get(i).getPubFechaPublicacion().toString());
            }
            document.add(table);

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
        document.close();
        System.out.println("Reporte por estudiante generado");
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
        
        /*int tamLista = publicaciones.size();
        String tipoPublicacion ;

        for (int i = 0; i < tamLista; i++) {
            tipoPublicacion = publicaciones.get(i).getPubTipoPublicacion();
            if (tipoPublicacion.equals("revista")) {
                listaRevista.add(publicaciones.get(i));
            }
            if (tipoPublicacion.equals("libro")) {
                listaLibro.add(publicaciones.get(i));
            }
            if (tipoPublicacion.equals("congreso")) {
                listaCongreso.add(publicaciones.get(i));
            }
            if (tipoPublicacion.equals("capitulo_libro")) {
                listaCapituloLibro.add(publicaciones.get(i));
            }
        }
        System.out.println("publicaciones rev "+listaRevista.size());
        System.out.println("publicaciones cong "+listaCongreso.size());
        System.out.println("publicaciones Lib "+listaLibro.size());
        System.out.println("publicaciones Cap "+listaCapituloLibro.size());*/
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

}
