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
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * @author debian
 *
 */
@Named(value = "reportesJasperController")
@ManagedBean
@SessionScoped
public class ReportesJasperController implements Serializable {

    @PersistenceContext(unitName = "ProyectoDoctoradoPU")
    private EntityManager em;

    private static final String TIPO_DOC_PDF = "PDF";
    private static final String TIPO_DOC_EXCEL = "EXCEL";
    private static final String TIPO_REPORTE_GLOBAL = "GLOBAL";
    private static final String TIPO_REPORTE_ESTUDIANTE = "POR ESTUDIANTE";
    private static final String RANGO_TIEMPO_ANIO = "POR AÑO";
    private static final String RANGO_TIEMPO_SEMESTRE = "POR SEMESTRE";
    private static final String RANGO_TIEMPO_TODO = "TODO";

    public String[] listaTiposReporte() {
        return new String[]{TIPO_REPORTE_GLOBAL, TIPO_REPORTE_ESTUDIANTE};
    }

    public String[] listaTiempo() {
        return new String[]{RANGO_TIEMPO_TODO, RANGO_TIEMPO_ANIO, RANGO_TIEMPO_SEMESTRE};
    }
    private String tipoReporte;
    private String tiempo;
    private String codEstudiante;
    private int anio;
    private int semestre;
    String[][] plantillasReportes;

    public ReportesJasperController() {
        tipoReporte = TIPO_REPORTE_GLOBAL;
        tiempo = RANGO_TIEMPO_TODO;
        codEstudiante = "";
        plantillasReportes = new String[][]{
            {"/reporteDocumentacionGlobalTodo.jasper", "/reporteDocumentacionGlobalAnio.jasper", "/reporteDocumentacionGlobalSemestre.jasper"},
            {"/reporteDocumentacionEstudianteTodo.jasper", "/reporteDocumentacionEstudianteAnio.jasper", "/reporteDocumentacionEstudianteSemestre.jasper"}};
    }

    //<editor-fold defaultstate="collapsed" desc="getters and getters">
    public void setTipoReporte(String tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public String getTipoReporte() {
        return tipoReporte;
    }

    public String getCodEstudiante() {
        return codEstudiante;
    }

    public void setCodEstudiante(String codEstudiante) {
        this.codEstudiante = codEstudiante;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getTipoDocPdf() {
        return tiempo;
    }

    //</editor-fold> 
    public boolean pedirAnio() {
        return tiempo.equals(RANGO_TIEMPO_ANIO) || tiempo.equals(RANGO_TIEMPO_SEMESTRE);
    }

    public boolean pedirCodEstudiante() {
        return tipoReporte.equals(TIPO_REPORTE_ESTUDIANTE);
    }

    public boolean pedirSemestre() {
        return tiempo.equals(RANGO_TIEMPO_SEMESTRE);
    }

    public int[] getListaAnios() {
        return Utilidades.getListaAnios();
    }

    JasperPrint jasperPrint;

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

    public void getReportePdf() {
        getReporte(TIPO_DOC_PDF);
    }

    public void getReporteExcel() {
        getReporte(TIPO_DOC_EXCEL);
    }

    private void getReporte(String tipoDoc) {
        try (Connection conn = getConnection()) {
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
                    break;
                case RANGO_TIEMPO_SEMESTRE:
                    columna = 2;
                    reportParameters.put("anio", anio);
                    reportParameters.put("semestre", semestre);
                    break;
            }
            URL url = this.getClass().getResource(plantillasReportes[fila][columna]);
            JasperReport report = (JasperReport) JRLoader.loadObject(url);
            jasperPrint = JasperFillManager.fillReport(report, reportParameters, conn);
            System.out.println("anio: " + anio + " tipoDoc:" + tipoDoc);

            switch (tipoDoc) {
                case TIPO_DOC_PDF:
                    pdf();
                    break;
                case TIPO_DOC_EXCEL:
                    exportXls();
                    break;
            }
//            conn.close();
        } catch (JRException ex) {
            Logger.getLogger(ReportesJasperController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ReportesJasperController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void pdf() {
        try {
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.addHeader("Content-disposition", "attachment; filename=report.pdf");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
            FacesContext.getCurrentInstance().responseComplete();
        } catch (JRException | IOException ex) {
            Logger.getLogger(ReportesJasperController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    //exporta a xls

    private void exportXls() {
        try {
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

            OutputStream out = response.getOutputStream();
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            JRXlsExporter exporterXLS = new JRXlsExporter();

            exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
            exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, arrayOutputStream);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            exporterXLS.exportReport();

            response.setHeader("Content-disposition", "attachment; filename=reporte.xls");
            response.setContentType("application/vnd.ms-excel");
            response.setContentLength(arrayOutputStream.toByteArray().length);
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
