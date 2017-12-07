package co.unicauca.proyectobase.controladores;

import co.unicauca.proyectobase.utilidades.Utilidades;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ComboBox;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.AbstractXlsReportConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

/**
 * Clase controlador que permite crear reportes basados en la libreria
 * jasperreport para los formatos pdf y xls. Controlador utilizado en la vista:
 * GraficaPubReg.xhtml
 *
 */
@Named(value = "reportesJasperController")
@ManagedBean
@SessionScoped
public class ReportesJasperController implements Serializable {

    @PersistenceContext(unitName = "ProyectoDoctoradoPU")

    /**
     * Campos String para getReporte y getReportePdf que indica el formato pdf
     * del reporte a generar
     */
    private static final String TIPO_DOC_PDF = "PDF";

    /**
     * Campos String para getReporte y getReporteExcel que indica el formato
     * excel del reporte a generar
     */
    private static final String TIPO_DOC_EXCEL = "EXCEL";
    /**
     * Campos String para setTipoReporte y getTipoReporte que indica el tipo
     * global de reporte a generar, es decir, para todos los estudiantes
     */
    private static final String TIPO_REPORTE_GLOBAL = "GLOBAL";

    /**
     * Campos String para setTipoReporte y getTipoReporte que indica el tipo
     * especifico para un estudiante de reporte a generar.
     */
    private static final String TIPO_REPORTE_ESTUDIANTE = "POR ESTUDIANTE";

    /**
     * Campos String para setTiempo y getTiempo que indica el rango de tiempo de
     * un año del reporte a generar.
     */
    private static final String RANGO_TIEMPO_ANIO = "POR AÑO";

    /**
     * Campos String para setTiempo y getTiempo que indica el rango de tiempo de
     * año y semestre del reporte a generar.
     */
    private static final String RANGO_TIEMPO_SEMESTRE = "POR SEMESTRE";

    /**
     * Campos String para setTiempo y getTiempo que indica el rango de tiempo no
     * especifico del reporte a generar, es decir, desde el incio de los tiempos
     * hasta la actualidad.
     */
    private static final String RANGO_TIEMPO_TODO = "TODO";

    /**
     * Permite obtener todos los tipos de reportes posibles
     *
     * @return
     */
    public String[] listaTiposReporte() {
        return new String[]{TIPO_REPORTE_GLOBAL, TIPO_REPORTE_ESTUDIANTE};
    }

    /**
     * Permite obtener todos los tipos de rangos de tiempos posibles para
     * reportes
     *
     * @return
     */
    public String[] listaTiempo() {
        return new String[]{RANGO_TIEMPO_TODO, RANGO_TIEMPO_ANIO, RANGO_TIEMPO_SEMESTRE};
    }

    private String tipoReporte;
    private String tiempo;
    private String codEstudiante;
    private int anio;
    private int semestre;
    /**
     * Almacena, en forma de matriz, las rutas de las plantillas de reportes
     */
    String[][] plantillasReportes;

    /**
     * permite crear una nueva instancia de ReportesJasperController
     */
    public ReportesJasperController() {
        tipoReporte = "";
        tiempo = "";
        codEstudiante = "";
        plantillasReportes = new String[][]{
            {"/reporteDocumentacionGlobalTodo.jasper", "/reporteDocumentacionGlobalAnio.jasper", "/reporteDocumentacionGlobalSemestre.jasper"},
            {"/reporteDocumentacionEstudianteTodo.jasper", "/reporteDocumentacionEstudianteAnio.jasper", "/reporteDocumentacionEstudianteSemestre.jasper"}};
    }

    //<editor-fold defaultstate="collapsed" desc="getters and getters">
    /**
     * Establece el tipo de reporte a generar.
     *
     * @param tipoReporte indica el tipo de reporte a generar
     * @see TIPO_REPORTE_GLOBAL, TIPO_REPORTE_ESTUDIANTE
     */
    public void setTipoReporte(String tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    /**
     *
     * @return retorno el tipo de reporte a generar
     * @see TIPO_REPORTE_GLOBAL, TIPO_REPORTE_ESTUDIANTE
     */
    public String getTipoReporte() {
        return tipoReporte;
    }

    /**
     *
     * @return
     */
    public String getCodEstudiante() {
        return codEstudiante;
    }

    /**
     * Permite establecer un codigo de un estudiante, si el tipo de reporte lo
     * necesita
     *
     * @param codEstudiante
     */
    public void setCodEstudiante(String codEstudiante) {
        this.codEstudiante = codEstudiante;
    }

    /**
     *
     * @return
     */
    public int getAnio() {
        return anio;
    }

    /**
     * Permite establecer un año, si el tipo de reporte lo necesita
     *
     * @param anio
     */
    public void setAnio(int anio) {
        this.anio = anio;
    }

    /**
     *
     * @return
     */
    public int getSemestre() {
        return semestre;
    }

    /**
     * Permite establecer un semestre, si el tipo de reporte lo necesita
     *
     * @param semestre
     */
    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    /**
     *
     * @return @see RANGO_TIEMPO_TODO, RANGO_TIEMPO_ANIO, RANGO_TIEMPO_SEMESTRE
     */
    public String getTiempo() {
        return tiempo;
    }

    /**
     * Permite establecer un rango de tiempo para un reporte.
     *
     * @param tiempo valor entre RANGO_TIEMPO_TODO, RANGO_TIEMPO_ANIO,
     * RANGO_TIEMPO_SEMESTRE
     */
    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    /**
     *
     * @return
     */
    public String getTipoDocPdf() {
        return tiempo;
    }

    //</editor-fold> 
    /**
     * Permite verificar si es necesario pedir un año para el reporte a generar
     *
     * @return true si es necesario pedir un año, false en otro caso.
     */
    public boolean pedirAnio() {
        return tiempo != null && (tiempo.equals(RANGO_TIEMPO_ANIO) || tiempo.equals(RANGO_TIEMPO_SEMESTRE));
    }

    /**
     * Permite verificar si es necesario pedir un codigo de un estudiante para
     * el reporte a generar
     *
     * @return true si es necesario pedir codigo de estudiante, false en otro
     * caso.
     */
    public boolean pedirCodEstudiante() {
        return tipoReporte != null && tipoReporte.equals(TIPO_REPORTE_ESTUDIANTE);
    }

    /**
     * Permite verificar si es necesario pedir un semestre para el reporte a
     * generar
     *
     * @return true si es necesario pedir un semestre, false en otro caso.
     */
    public boolean pedirSemestre() {
        return tiempo != null && tiempo.equals(RANGO_TIEMPO_SEMESTRE);
    }

    /**
     * permite listar los años desde 1999 hasta elaño actual.
     *
     * @return arreglo de los años entre el año 1999 y el actual.
     */
    public int[] getListaAnios() {
        return Utilidades.getListaAnios();
    }
    /**
     * contiene un reporte a partir de una plantilla de reportes compilada.
     */
    JasperPrint jasperPrint;

    /**
     * permite ontener una connecion a la base de datos doctorado en mysql
     *
     * @return
     * @throws SQLException
     */
    Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/doctorado",
                    "Doctorado", "Doc2017_I");
            //las credenciales que esta en los manuales para la base de datos son:
            //      "Doctorado", "Doc2017_I"
            return con;
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Database.getConnection() Error -->" + ex.getMessage());
            return null;
        }
    }

    /**
     * Genera un reporte de tipo pdf, llamado desde a interfaz de usuario
     */
    public void getReportePdf() {
        if (tipoReporte.equals(" ")) {
            return;
        }
        getReporte(TIPO_DOC_PDF);
    }

    /**
     * Genera un reporte de tipo xls, llamado desde a interfaz de usuario
     */
    public void getReporteExcel() {
        if (tipoReporte.equals(" ")) {
            return;
        }
        getReporte(TIPO_DOC_EXCEL);
    }

    /**
     * A partir del paramtro tipoDoc mas los atributos globales genera un
     * reporte de tipo pdf o xls
     *
     * @param tipoDoc pude ser ReportesJasperController.TIPO_DOC_PDF o
     * ReportesJasperController.TIPO_DOC_EXCEL
     */
    private void getReporte(String tipoDoc) {
        try (Connection conn = getConnection()) {
            // "fila" y "columna" guardan las coordenadas, en la matriz de rutas
            // de las plantillas los reportes, del reporte correspondiente a la configuracion 
            // elegida por el usuario
            int fila = 0, columna = 0;
            Map<String, Object> reportParameters = new HashMap<String, Object>();

            switch (tipoReporte) {
                case TIPO_REPORTE_GLOBAL:
                    fila = 0;
                    break;
                case TIPO_REPORTE_ESTUDIANTE:
                    fila = 1;
                    reportParameters.put("codEst", codEstudiante);
                    break;
            }
            switch (tiempo) {
                case RANGO_TIEMPO_TODO:
                    columna = 0;
                    break;
                case RANGO_TIEMPO_ANIO:
                    columna = 1;
                    reportParameters.put("anio", anio);
                    System.out.println("anio: " + anio + " tipoDoc:" + tipoDoc);
                    break;
                case RANGO_TIEMPO_SEMESTRE:
                    columna = 2;
                    reportParameters.put("anio", anio);
                    reportParameters.put("semestre", semestre);
                    System.out.println("anio: " + anio + " tipoDoc:" + tipoDoc);
                    System.out.println("semestre: " + semestre);
                    break;
            }
            //obtencion de la ruta del archivo .jasper que contiene la plantilla
            //del reporte, de acuerdo a la configuracion elegida por el usuario,
            //que solicita el reporte
            URL url = this.getClass().getResource(plantillasReportes[fila][columna]);
            JasperReport report = (JasperReport) JRLoader.loadObject(url);

            // compilacion de la plantilla jasper
            jasperPrint = JasperFillManager.fillReport(report, reportParameters, conn);
            //confirmacion de datos existentes para generar reporte
            if (!jasperPrint.getPages().isEmpty()) {
                //eleccion del formato de salidad del reporte
                switch (tipoDoc) {
                    case TIPO_DOC_PDF:
                        pdf();
                        break;
                    case TIPO_DOC_EXCEL:
                        exportXls();
                        break;
                }
            } else {
                // cuando no hay datos para generar reporte para la configuracion
                // elegida por el usuario
                String summary = "No hay datos para generar el reporte.";
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, summary);
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
            conn.close();
        } catch (JRException ex) {
            Logger.getLogger(ReportesJasperController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ReportesJasperController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Genera un reporte jaspert en formato pdf y envia este al navegador web
     */
    public void pdf() {
        try {
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

//            response.addHeader("Content-disposition", "attachment; filename=report.pdf");
            //tipo del archivo de descarga
            response.addHeader("Content-disposition", "inline; filename=report.pdf");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            //exportar archivo al formato pdf
            JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
            FacesContext.getCurrentInstance().responseComplete();
        } catch (JRException | IOException ex) {
            Logger.getLogger(ReportesJasperController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Genera un reporte jaspert en formato xls y envia este al navegador web
     */
    private void exportXls() {
        try {
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

            OutputStream out = response.getOutputStream();
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            // definiendo tipo de exportacion xls
            JRXlsExporter exporterXLS = new JRXlsExporter();
            SimpleOutputStreamExporterOutput sreo = new SimpleOutputStreamExporterOutput(arrayOutputStream);
            exporterXLS.setExporterOutput(sreo);
            SimpleExporterInput sei = new SimpleExporterInput(jasperPrint);
            exporterXLS.setExporterInput(sei);
            AbstractXlsReportConfiguration xrc = new SimpleXlsReportConfiguration();

            // parametros para la exportacion a xls
            xrc.setOnePagePerSheet(true);
            xrc.setDetectCellType(true);
            xrc.setWhitePageBackground(true);
            xrc.setRemoveEmptySpaceBetweenColumns(true);
            xrc.setRemoveEmptySpaceBetweenRows(true);
            exporterXLS.setConfiguration(xrc);

            //exportacio a xls
            exporterXLS.exportReport();

            // modo de descarga de archivo
            response.setHeader("Content-disposition", "attachment; filename=reporte.xls");
//            response.setHeader("Content-disposition", "inline; filename=reporte.xls");
            // tipo de del archivo a descargas
            response.setContentType("application/vnd.ms-excel");
            response.setContentLength(arrayOutputStream.toByteArray().length);
            // enviar el archivo al navegador
            out.write(arrayOutputStream.toByteArray());
            out.flush();
            out.close();

        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, e);
        } catch (IOException ex) {
            Logger.getLogger(ReportesJasperController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
