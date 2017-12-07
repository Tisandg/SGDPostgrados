package co.unicauca.proyectobase.controladores;

import co.unicauca.proyectobase.dao.CapituloLibroFacade;
import co.unicauca.proyectobase.dao.CiudadFacade;
import co.unicauca.proyectobase.dao.CongresoFacade;
import co.unicauca.proyectobase.dao.EstudianteFacade;
import co.unicauca.proyectobase.dao.LibroFacade;
import co.unicauca.proyectobase.dao.PaisFacade;
import co.unicauca.proyectobase.dao.PublicacionFacade;
import co.unicauca.proyectobase.dao.RevistaFacade;
import co.unicauca.proyectobase.entidades.Archivo;
import co.unicauca.proyectobase.entidades.Congreso;
import co.unicauca.proyectobase.entidades.Estudiante;
import co.unicauca.proyectobase.entidades.Publicacion;
import co.unicauca.proyectobase.entidades.Revista;
import co.unicauca.proyectobase.entidades.Libro;
import co.unicauca.proyectobase.entidades.CapituloLibro;
import co.unicauca.proyectobase.entidades.Ciudad;
import co.unicauca.proyectobase.entidades.MetodosPDF;
import co.unicauca.proyectobase.entidades.Pais;
import co.unicauca.proyectobase.entidades.archivoPDF;
import co.unicauca.proyectobase.entidades.tipoPDF_cargar;
import co.unicauca.proyectobase.utilidades.Autor;
import co.unicauca.proyectobase.utilidades.ConeccionOpenKM;
import co.unicauca.proyectobase.utilidades.PropiedadesOS;
import co.unicauca.proyectobase.utilidades.Utilidades;
import com.openkm.sdk4j.OKMWebservices;
import com.openkm.sdk4j.bean.QueryParams;
import com.openkm.sdk4j.bean.QueryResult;
import com.openkm.sdk4j.exception.AccessDeniedException;
import com.openkm.sdk4j.exception.AutomationException;
import com.openkm.sdk4j.exception.DatabaseException;
import com.openkm.sdk4j.exception.ExtensionException;
import com.openkm.sdk4j.exception.FileSizeExceededException;
import com.openkm.sdk4j.exception.LockException;
import com.openkm.sdk4j.exception.ParseException;
import com.openkm.sdk4j.exception.PathNotFoundException;
import com.openkm.sdk4j.exception.RepositoryException;
import com.openkm.sdk4j.exception.UnknowException;
import com.openkm.sdk4j.exception.UserQuotaExceededException;
import com.openkm.sdk4j.exception.VersionException;
import com.openkm.sdk4j.exception.VirusDetectedException;
import com.openkm.sdk4j.exception.WebserviceException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.component.UIOutput;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 * Clase controlador que permite realizar la gestión de una publicación.
 * Controlador usado por las vistas: VerPublicacion, menu, VerEstudiante, GraficaPubReg, ListarPublicaciones_Coord, 
 * ListarPublicaciones_Espera, ListarPublicaciones_Rev, VerPublicacion_Coord, EditarPublicacion_Est, ListarPracticas, 
 * ListarPublicaciones_Est, TabListar, EditarContrasena, RegistrarPracticaDocente, RegistrarPublicacion, TabRegistrar, 
 * VerPublicacion_Est, verPracticaDocente_est.
 * @author Carolina
 */

@Named(value = "publicacionController")
@ManagedBean
@SessionScoped
public class PublicacionController implements Serializable {

    @EJB
    private EstudianteFacade daoEst;

    @EJB
    private PublicacionFacade daoPublicacion;

    @EJB
    private RevistaFacade daoRevista;

    @EJB
    private CongresoFacade daoCongreso;

    @EJB
    private LibroFacade daoLibro;

    @EJB
    private CapituloLibroFacade daoCapituloLibro;

    @EJB
    private CiudadFacade ejbCiudad;

    @EJB
    private PaisFacade ejbPais;

    private Publicacion actual;
    private List<Publicacion> listaPublicaciones;
    private UploadedFile publicacionPDF;
    private UploadedFile TablaContenidoPDF;
    private UploadedFile cartaAprobacionPDF;
    private byte[] exportContent;
    private String pdfUrl;
    private StreamedContent streamedContent;
    private InputStream stream;
    private Estudiante estudianteActual;
    private String numActa;
    private String creditos;
    private String variableFiltrado;
    private String tipoPublicacion;
    private String motivoRechazo;
    private String uploadedFileName;
    private CargarVistaEstudiante cve;
    private CargarVistaCoordinador cvc;
    private List<Ciudad> listaCiudades;
    private List<Pais> listaPaises;
    private int idPais;
    private int idCiudad;
    private int numeroDocumentos;

    /* Controladores */
    public PublicacionController() {
        cve = new CargarVistaEstudiante();
        cvc = new CargarVistaCoordinador();
        this.listaPaises = new ArrayList<>();
    }
    public PublicacionController(Publicacion pub) {
        actual = pub;
        cve = new CargarVistaEstudiante();
        cvc = new CargarVistaCoordinador();
        this.listaPaises = new ArrayList<>();
    }
    
    /* Getters y Setters */
    public String getTipoPublicacion() {
        return tipoPublicacion;
    }

    public void setTipoPublicacion(String tipoPublicacion) {
        this.tipoPublicacion = tipoPublicacion;
    }

    public String getMotivoRechazo() {
        return motivoRechazo;
    }

    public void setMotivoRechazo(String motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }
    
    public String getNumActa() {
        numActa = "" + actual.getPubNumActa();
        if (numActa.equalsIgnoreCase("null")) {
            numActa = "0";
        }
        return numActa;
    }

    public void setNumActa(String numActa) {
        this.numActa = numActa;
    }
    
    public String getCreditos() {
        creditos = "" + daoEst.findCreditosByNombreUsuario(estudianteActual.getEstUsuario());
        if (creditos.equalsIgnoreCase("null")) {
            creditos = "0";
        }
        return creditos;
    }

    public void setCreditos(String creditos) {
        this.creditos = creditos;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public UploadedFile getPublicacionPDF() {
        return publicacionPDF;
    }

    public void setPublicacionPDF(UploadedFile publicacionPDF) {

        this.publicacionPDF = publicacionPDF;
    }

    public UploadedFile getTablaContenidoPDF() {

        return TablaContenidoPDF;
    }

    public void setTablaContenidoPDF(UploadedFile TablaContenidoPDF) {
        this.TablaContenidoPDF = TablaContenidoPDF;
    }

    public UploadedFile getCartaAprobacionPDF() {
        return cartaAprobacionPDF;
    }

    public void setCartaAprobacionPDF(UploadedFile cartaAprobacionPDF) {
        this.cartaAprobacionPDF = cartaAprobacionPDF;
    }

    public List<Publicacion> getListaPublicaciones() {
        return listaPublicaciones;
    }

    public void setListaEstudiantes(List<Publicacion> listaPublicacion) {
        this.listaPublicaciones = listaPublicacion;
    }
    
    public Estudiante getAuxEstudiante() {
        return estudianteActual;
    }

    public void setAuxEstudiante(Estudiante auxEstudiante) {
        this.estudianteActual = auxEstudiante;
    }

    public String getVariableFiltrado() {
        return variableFiltrado;
    }

    public void setVariableFiltrado(String variableFiltrado) {
        this.variableFiltrado = variableFiltrado;
    }

    /* Métodos */
    public void onComplete() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Progress Completed", "Progress Completed"));
    }

    /**
     * Método que permite visar una publicación
     * @throws IOException 
     */
    public void visPdfPub() throws IOException {
        /* 1 publicacion, 2 evidencia, 3 tabla de contenido */
        archivoPDF archivoPublic = actual.descargarDocumento(1);
        InputStream fis = archivoPublic.getArchivo();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[0xFFFF];
        for (int len; (len = fis.read(buffer)) != -1;) {
            os.write(buffer, 0, len);
        }
        os.flush();
        byte[] b = os.toByteArray();
        stream = new ByteArrayInputStream(b);
        stream.mark(0); //remember to this position!
        streamedContent = new DefaultStreamedContent(stream, "application/pdf");
    }

    /**
     * Método que permite obtener el contenido transmitido
     * @return streamedContent: contenido resultante
     * @throws IOException 
     */
    public StreamedContent getStreamedContent() throws IOException {
        if (streamedContent != null) {
            streamedContent.getStream().reset(); //reset stream to the start position!
        }
        return streamedContent;
    }


    /**
     * metodo para buscar el nombre de usuario de cada publicacion que desea ver
     * el coordinador
     *
     * @return nombre de usuario
     */
    public String obtenerNombreUsuarioCoor() {
        return daoEst.findNombreById(actual.getPubEstIdentificador());
    }

    /**
     * Obtener el nombre com
     * @param nombreUsuario
     * @return 
     */
    public String obtenerNombreUsuarioById(Estudiante nombreUsuario) {
        return daoEst.findNombreById(nombreUsuario);
    }

   
    String INICIO = "index";
    String CREAR = "new";
    String EDITAR = "editar";

    public Publicacion getActual() {
        if (actual == null) {
            actual = new Publicacion();
        }
        return actual;
    }

    public String index() {
        return INICIO;
    }

    //<editor-fold defaultstate="collapsed" desc="listado de publicaciones">   
    public List<Publicacion> listado() {
        if ((variableFiltrado == null) || (variableFiltrado.equals(""))) {
            return daoPublicacion.ListadoSoloPublicacion();
        } else {
            return daoPublicacion.ListadoPublicacionFilt(variableFiltrado);
        }
    }

    public List<Publicacion> listadoEspera() {
        if ((variableFiltrado == null) || (variableFiltrado.equals(""))) {
            return listaPublicacionVisadoEspera(daoPublicacion.ListadoSoloPublicacion());
        } else {
            return listaPublicacionVisadoEspera(daoPublicacion.ListadoPublicacionFilt(variableFiltrado));
        }
    }

    public List<Publicacion> listadoRevisadas() {
        if ((variableFiltrado == null) || (variableFiltrado.equals(""))) {
            return listaPublicacionVisadoRevisada(daoPublicacion.ListadoSoloPublicacion());
        } else {
            return listaPublicacionVisadoRevisada(daoPublicacion.ListadoPublicacionFilt(variableFiltrado));
        }
    }

    /**
     * Funcion para buscar las publicaciones que ha registrado un estudiante.
     * Con el nombre de usuario se busca en la base de datos las publicaciones
     * que esten a registradas por ese estudiante. Las publicaciones encontradas
     * se retornan en una lista.
     * @param nombreUsuario
     * @return 
     */
    public List<Publicacion> listadoPublicaciones(String nombreUsuario) {
        
        //Estudiante est = daoPublicacion.obtenerEstudiante(nombreUsuario);
        Estudiante est = daoEst.findByUsername(nombreUsuario);
        setAuxEstudiante(est);
        int idEstudiante = est.getEstIdentificador();
        if ((variableFiltrado == null) || (variableFiltrado.equals(""))) {
            return daoPublicacion.ListadoPublicacionEst(idEstudiante);
        } else {
            return daoPublicacion.ListadoPublicacionEstFilt(idEstudiante, variableFiltrado);
        }
    }
    //</editor-fold>

    /* Lista las publicaciones que su estado de Visado sea: espera,
     es decir publicaciones que aun no han sido visadas*/
    public List<Publicacion> listaPublicacionVisadoEspera(List<Publicacion> lista) {
        List<Publicacion> listado = new ArrayList<>();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getPubVisado().equals("espera") && lista.get(i).getPubEstado().equals("Activo")) {
                listado.add(lista.get(i));
            }
        }
        return listado;
    }

    /* Lista las publicaciones que esten revisadas
     es decir que su estado de Visado sea: aceptada o rechazada */
    public List<Publicacion> listaPublicacionVisadoRevisada(List<Publicacion> lista) {
        List<Publicacion> listado = new ArrayList<>();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getPubVisado().equals("aprobado") || lista.get(i).getPubVisado().equals("no aprobado")) {
                listado.add(lista.get(i));
            }
        }
        return listado;
    }  
    
    /**
     * 
     * @param event 
     */
    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("MM/yyyy");
        facesContext.addMessage("event", new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }

    /**
     * Metodo para visualizar la carta de aprobacion(o evidencia) que se ha 
     * registrado con la publicacion. Este archivo se obtiene del gestor OpenKM
     * @throws FileNotFoundException
     * @throws IOException
     * @throws IOException
     * @throws IOException 
     */
    public void pdfCartaAprob() throws FileNotFoundException, IOException, IOException, IOException {
        /* 1 publicacion, 2 evidencia, 3 tabla de contenido */
        archivoPDF archivoPublic = actual.descargarDocumento(2);
        if (archivoPublic.getNombreArchivo().equals("")) {
            FacesContext.getCurrentInstance().addMessage("error", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Para esta publicacion el Usuario no ha cargado un PDF de la carta de aprobacion  ", ""));
        } else {
            String[] nombreArchivo = archivoPublic.getNombreArchivo().split("\\.");
            InputStream fis = archivoPublic.getArchivo();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.setContentType("application/pdf");
            // response.setHeader("Content-Disposition", "inline;filename=" + archivoPublic.getNombreArchivo() + ".pdf");
            response.setHeader("Content-Disposition", "inline;filename=" + nombreArchivo[0] + ".pdf");
            byte[] buffer = new byte[8 * 1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                response.getOutputStream().write(buffer, 0, bytesRead);
            }
            response.getOutputStream().flush();
            response.getOutputStream().close();
            FacesContext.getCurrentInstance().responseComplete();
        }
    }

    /**
     * Metodo para visualizar el documento de la publicacion con el que se
     * ha registrado. El archivo es obtenido desde OpenKM
     * @throws FileNotFoundException
     * @throws IOException
     * @throws IOException
     * @throws IOException 
     */
    public void pdfPub() throws FileNotFoundException, IOException, IOException, IOException {

        archivoPDF archivoPublic = actual.descargarDocumento(1);
        if (archivoPublic.getNombreArchivo().equals("")) {
            FacesContext.getCurrentInstance().addMessage("error", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario no ha cargado un PDF para esta publicacion", ""));
        } else {
            String[] nombreArchivo = archivoPublic.getNombreArchivo().split("\\.");
            InputStream fis = archivoPublic.getArchivo();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline;filename=" + nombreArchivo[0] + ".pdf");
            byte[] buffer = new byte[8 * 1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                response.getOutputStream().write(buffer, 0, bytesRead);
            }
            response.getOutputStream().flush();
            response.getOutputStream().close();
            FacesContext.getCurrentInstance().responseComplete();
        }
    }

    /**
     * Metodo para visualizar la tabla de contenido que se ha registrado junto
     * con la publicacion. El archivo es obtenido desde el OpenKM
     * @throws FileNotFoundException
     * @throws IOException
     * @throws IOException
     * @throws IOException 
     */
    public void pdfPubTC() throws FileNotFoundException, IOException, IOException, IOException {
        
        archivoPDF archivoPublic = actual.descargarDocumento(3);
        if (archivoPublic.getNombreArchivo().equals("")) {
            System.out.println("Error al obtener tabla de contenido de controlador de publicaciones");
            FacesContext.getCurrentInstance().addMessage("error", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario no ha cargado un PDF de la tabla de contenido", ""));
        } else {
            String[] nombreArchivo = archivoPublic.getNombreArchivo().split("\\.");
            InputStream fis = archivoPublic.getArchivo();

            HttpServletResponse response
                    = (HttpServletResponse) FacesContext.getCurrentInstance()
                            .getExternalContext().getResponse();

            response.setContentType("application/pdf");
            // response.setHeader("Content-Disposition", "inline;filename=" + archivoPublic.getNombreArchivo() + ".pdf");
            response.setHeader("Content-Disposition", "inline;filename=" + nombreArchivo[0] + ".pdf");
            byte[] buffer = new byte[8 * 1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                response.getOutputStream().write(buffer, 0, bytesRead);
            }
            // response.getOutputStream().write(buf);
            response.getOutputStream().flush();
            response.getOutputStream().close();
            FacesContext.getCurrentInstance().responseComplete();

        }
    }

    /**
     * Metodo que descarga la carta de aprovacion(o Evidencia) de la publicacion.
     * Este se descarga desde el gestor OpenKM
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void descargarCartaAprobac() throws FileNotFoundException, IOException {
        
        archivoPDF archivoPublic = actual.descargarDocumento(2);
        if (archivoPublic.getNombreArchivo().equals("")) {
            FacesContext.getCurrentInstance().addMessage("error", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Para esta publicacion el Usuario no ha cargado un PDF de la carta de aprobacion  ", ""));

        } else {

            InputStream fis = archivoPublic.getArchivo();
            String[] nombreArchivo = archivoPublic.getNombreArchivo().split("\\.");
            HttpServletResponse response
                    = (HttpServletResponse) FacesContext.getCurrentInstance()
                            .getExternalContext().getResponse();

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment;filename=" + nombreArchivo[0] + ".pdf");

            byte[] buffer = new byte[8 * 1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                response.getOutputStream().write(buffer, 0, bytesRead);
            }

            // response.getOutputStream().write(buf);
            response.getOutputStream().flush();
            response.getOutputStream().close();
            FacesContext.getCurrentInstance().responseComplete();
        }
    }

    /**
     * Metodo para descargar el documento de la publicacion que se ha registrado.
     * Este documento se descarga desde el gestor OpenKM
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void descargarPublicacion() throws FileNotFoundException, IOException {
        /* 1 publicacion, 2 evidencia, 3 tabla de contenido */
        archivoPDF archivoPublic = actual.descargarDocumento(1);
        if (archivoPublic.getNombreArchivo().equals("")) {
            FacesContext.getCurrentInstance().addMessage("error", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario no ha cargado un PDF para esta publicacion", ""));

        } else {
            InputStream fis = archivoPublic.getArchivo();
            String[] nombreArchivo = archivoPublic.getNombreArchivo().split("\\.");
            HttpServletResponse response
                    = (HttpServletResponse) FacesContext.getCurrentInstance()
                            .getExternalContext().getResponse();

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment;filename=" + nombreArchivo[0] + ".pdf");

            byte[] buffer = new byte[8 * 1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                response.getOutputStream().write(buffer, 0, bytesRead);
            }

            // response.getOutputStream().write(buf);
            response.getOutputStream().flush();
            response.getOutputStream().close();
            FacesContext.getCurrentInstance().responseComplete();
        }

    }

    /**
     * Metodo para descargar el documento de la tabla de contenido que
     * se ha registrado con la publicacion. Este se descarga desde openKM
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void descargarPubTC() throws FileNotFoundException, IOException {
        /* 1 publicacion, 2 evidencia, 3 tabla de contenido */
        archivoPDF archivoPubTC = actual.descargarDocumento(3);
        if (archivoPubTC.getNombreArchivo().equals("")) {
            FacesContext.getCurrentInstance().addMessage("error", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario no ha cargado un PDF de la tabla de contenido", ""));

        } else {
            byte[] buf;
            InputStream fis = archivoPubTC.getArchivo();
            String[] nombreArchivo = archivoPubTC.getNombreArchivo().split("\\.");

            HttpServletResponse response
                    = (HttpServletResponse) FacesContext.getCurrentInstance()
                            .getExternalContext().getResponse();

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment;filename=" + nombreArchivo[0] + ".pdf");

            byte[] buffer = new byte[8 * 1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                response.getOutputStream().write(buffer, 0, bytesRead);
            }

            // response.getOutputStream().write(buf);
            response.getOutputStream().flush();
            response.getOutputStream().close();
            FacesContext.getCurrentInstance().responseComplete();
        }

    }

    /**
     * Funcion para comprobar que los archivos de la publicados cargados 
     * esten en formato pdf
     * @return true si son validos los archivos
     */
    public boolean comprobarArchivosPDF() {
        this.numeroDocumentos = 0;
        boolean validos = true;
        String tituloMensaje = "";
        String mensaje = "";
        if (cartaAprobacionPDF != null && !cartaAprobacionPDF.getFileName().equalsIgnoreCase("") && !"application/pdf".equals(cartaAprobacionPDF.getContentType())) {
            tituloMensaje = "Evidencia";
            mensaje = "Debe subir la carta de aprobación en formato PDF";
            this.numeroDocumentos++;
            FacesContext.getCurrentInstance().addMessage(tituloMensaje, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, ""));
        }
        if (publicacionPDF != null && !publicacionPDF.getFileName().equalsIgnoreCase("") && !"application/pdf".equals(publicacionPDF.getContentType())) {
            tituloMensaje = "Publicacion";
            mensaje = "Debe subir la publicación en formato PDF";
            this.numeroDocumentos++;
            FacesContext.getCurrentInstance().addMessage(tituloMensaje, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, ""));
            
        }
        if (TablaContenidoPDF != null && !TablaContenidoPDF.getFileName().equalsIgnoreCase("") && !"application/pdf".equals(TablaContenidoPDF.getContentType())) {
            tituloMensaje = "Tabla de contenido";
            mensaje = "Debe subir la Tabla de Contenido en formato PDF";
            this.numeroDocumentos++;
            FacesContext.getCurrentInstance().addMessage(tituloMensaje, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, ""));
        }
        if(this.numeroDocumentos > 0){
            validos = false;
        }
        System.out.println("Numero de documentos erroneos"+this.numeroDocumentos);
        return validos;
    }

    /**
     * Metodo para registrar la publicacion en la base de datos y subir los
     * documentos al gestor de documentos openKM
     * @throws IOException
     */
    public void registrarPublicacion() throws IOException {
        /* formatoValido -> se utiliza para verificar que el usario
           suba unicamente archivos en formato pdf*/
        boolean formatoValido = comprobarArchivosPDF();
        
        if (formatoValido) {
            /* PuedeSubir  ->  se utiliza para comprobar que el usuario ha seleccionado 
                el PDF de la publicacion o en su defecto la carta de aprobacion*/
            boolean puedeSubir = false;
            String msjDocRequeridos = "";
            if(this.tipoPublicacion.equals("libro") || this.tipoPublicacion.equals("capitulo libro")){
                if (!cartaAprobacionPDF.getFileName().equalsIgnoreCase("")) {
                    puedeSubir = true;
                }else{
                    msjDocRequeridos = "Debe subir la evidencia del documento";
                }
            }else{
                if (!cartaAprobacionPDF.getFileName().equalsIgnoreCase("") && 
                        !publicacionPDF.getFileName().equalsIgnoreCase("")) {
                    puedeSubir = true;
                }else{
                    msjDocRequeridos = "Debe subir la publicación y la evidencia del documento";
                }
            }
            
            if (puedeSubir) {
                System.out.println("Agregando documentacion");
                Estudiante est = getAuxEstudiante();
                actual.setPubEstIdentificador(est);
                String nombreAut = est.getEstNombre() + " " + est.getEstApellido();
                //obtener el identificador de la publicacion
                int pub_identificador = daoPublicacion.getnumFilasPubRev();
                actual.setPubIdentificador(pub_identificador);

                //<editor-fold defaultstate="collapsed" desc="adicion de campos dependiendo tipo de publicacion">                   
                /* Dependiendo de si se adiciona una revista, un congreso,un libro o un  capitulo de un libro se crea el objeto respectivo*/
                if (getTipoPublicacion().equals("revista")) {
                    actual.getRevista().setPubIdentificador(pub_identificador);
                    actual.getRevista().setPublicacion(actual);
                    actual.setCongreso(null);
                    actual.setCapituloLibro(null);
                    actual.setLibro(null);
                }
                if (getTipoPublicacion().equals("congreso")) {
                    actual.getCongreso().setPubIdentificador(pub_identificador);
                    actual.getCongreso().setPublicacion(actual);
                    actual.getCongreso().setCiudadId(ejbCiudad.getCiudadPorId(idCiudad));
                    actual.setRevista(null);
                    actual.setCapituloLibro(null);
                    actual.setLibro(null);
                }
                if (getTipoPublicacion().equals("libro")) {
                    actual.getLibro().setPubIdentificador(pub_identificador);
                    actual.getLibro().setPublicacion(actual);
                    actual.getLibro().setCiudadId(ejbCiudad.getCiudadPorId(idCiudad));
                    actual.setRevista(null);
                    actual.setCongreso(null);
                    actual.setCapituloLibro(null);
                }
                if (getTipoPublicacion().equals("capitulo libro")) {
                    actual.getCapituloLibro().setPubIdentificador(pub_identificador);
                    actual.getCapituloLibro().setPublicacion(actual);
                    actual.setRevista(null);
                    actual.setCongreso(null);
                    actual.setLibro(null);
                }
                actual.setIdTipoDocumento(daoPublicacion.obtenerIdTipoDocumento(getTipoPublicacion()));

                //</editor-fold>
                ArrayList<Archivo> CollArchivo = new ArrayList<>();
                int numArchivos = daoPublicacion.getIdArchivo();

                Archivo archCartaAprob = new Archivo(actual, numArchivos, "cartaAprobacion");
                CollArchivo.add(archCartaAprob);

                if (!publicacionPDF.getFileName().equalsIgnoreCase("")) {
                    numArchivos++;
                    Archivo archArt = new Archivo(actual, numArchivos, "tipoPublicacion");
                    CollArchivo.add(archArt);
                }
                if (!TablaContenidoPDF.getFileName().equalsIgnoreCase("")) {
                    numArchivos++;
                    Archivo arcTablaC = new Archivo(actual, numArchivos, "tablaContenido");
                    CollArchivo.add(arcTablaC);
                }
                actual.setArchivoCollection(CollArchivo);
                actual.setPubEstado("Activo");
                actual.setPubVisado("espera");
                fijarAutoresSecundarios();

                try {

                    /*Aqui se suben los archivos al OpenKm*/
                    if (actual.agregarMetadatos(publicacionPDF, TablaContenidoPDF, cartaAprobacionPDF)) {
                        /*Crear registro en la bd*/
                        daoPublicacion.create(actual);
                        daoPublicacion.flush();
                        mensajeconfirmarRegistro();

                        Date date = new Date();
                        DateFormat datehourFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        String estampaTiempo = "" + datehourFormat.format(date);
                        /*Se envian los correos de confirmacion*/
                        Utilidades.correoRegistroPublicaciones(estudianteActual.getEstCorreo(), nombreAut,
                                actual.getIdTipoDocumento().getNombre(), estampaTiempo);

                    } else {
                        /*No se han podido subir los archivos*/
                        FacesContext.getCurrentInstance().addMessage("Error", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al subir archivos", ""));
                        Utilidades.redireccionar("/ProyectoII/faces/usuariosdelsistema/estudiante/registrar_documentos/RegistrarPublicacion.xhtml");
                    }

                } catch (EJBException ex) {
                    mensajeRegistroFallido();
                    Logger.getLogger(PublicacionController.class.getName()).log(Level.SEVERE, null, ex);
                }
                limpiarCampos();
                //redirigirPublicacionesEst(est.getEstUsuario());
                //redirigirPublicacionesEst(actual);
                redirigirAlistarPublicionesEst();
                //Utilidades.redireccionar("/ProyectoII/faces/usuariosdelsistema/estudiante/listarDocumentos/ListarPublicaciones_Est.xhtml");
            }else{
                System.out.println("Se requiren documentos");
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Documentos requeridos",msjDocRequeridos);
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage("grupoDocs", msg);
            }
        }
    }

    /**
     * *
     * Metodo para eliminar una documentacion que se halla registrado. Con la
     * publicacion que recibo se verifica que no halla sido visada por el
     * coordinador. Si no ha sido visada, se pasa a borrar la informacion
     * registrada de esta documentacion en openkm y en la base de datos
     *
     * @param pub
     * @throws LockException
     */
    public boolean eliminarDocumentacion(Publicacion pub){
        actual = pub;
        boolean eliminado = false;
        /*Comprobamos que no halla sido visada*/
        if (actual.getPubVisado().equalsIgnoreCase("aprobado")
                || actual.getPubVisado().equalsIgnoreCase("no aprobado")) {
            /*No se puede eliminar la publicacion*/
            System.out.println("No se puede eliminar. La documentacion ya ha sido revisada");
            addMessage("La documentacion ya ha sido revisada por el coordinador", "");
        } else {
            /*Eliminamos primero los documentos que estan en el openKm
              y luego eliminamos los registros de la base de datos*/
            if(actual.eliminarDocOpenkm()){
                daoPublicacion.remove(actual);
                daoPublicacion.flush();
                addMessage("Documentacion ha sido eliminada","");
                System.out.println("Documentacion eliminada");
                eliminado = true;
                redirigirPublicacionesEst();
            }else{
                System.out.println("No se ha podido eliminar la documentacion de openkm");
                addMessage("La publicación no se ha podido eliminar", "");
            }
        }
        return eliminado;
    }
    
    /**
     * eliminar una publicacion desde otros controladores     
     */
    public void eliminarPublicacion(){    
        System.out.println("====Pub: " + actual.getPubEstIdentificador().getEstNombre());
        System.out.println("daoPub: " + daoPublicacion);
        daoPublicacion = new PublicacionFacade();
        daoPublicacion.remove(actual);
        daoPublicacion.flush();        
    }

    /**
     * Limpiar y/o inicializa los campos del formulario
     */
    public void limpiarCampos() {
        actual = new Publicacion();
        listaAutores.clear();
        idPais = 0;
        listaCiudades.clear();
        tipoPublicacion = "";
    }

    /**
     * Inicializar un nuevo objeto publicacion donde se van a guardar los datos
     * @param nombreUsuario nombre de usuario con el cual se desea reiniciar las
     * variables
     */
    public void limpiarCampos(String nombreUsuario) {
        actual = new Publicacion();
        setAuxEstudiante(daoEst.findByUsername(nombreUsuario));
        listaAutores.clear();
        tipoPublicacion = "";
    }

    public void fijarEstudiante(String nombreUsuario) {
        Estudiante est = daoEst.findByUsername(nombreUsuario);
        setAuxEstudiante(est);
    }

    /**
     * Funcion para obtener el nombre completo del autor de la publicacion o
     * practica.
     * @return nombre autor completo
     */
    public String getnombreAut() {
        Estudiante est = getAuxEstudiante();
        return est.getEstNombre() + " " + est.getEstApellido();
    }

    /**
     * Metodo para guardar los cambios realizados en una publicacion. Una vez
     * guardados se redirecciona a la lista de publicaciones del estudiante.
     */
    public void guardarEdicion() {
        System.out.println("Editando datos");
        fijarAutoresSecundarios();

        if (actual.getIdTipoDocumento().getIdentificador() == 1) {
            actual.getLibro().setCiudadId(ejbCiudad.getCiudadPorId(idCiudad));
        }
        if (actual.getIdTipoDocumento().getIdentificador() == 3) {
            actual.getCongreso().setCiudadId(ejbCiudad.getCiudadPorId(idCiudad));
        }
        boolean formatoValido = comprobarArchivosPDF();
        if(formatoValido){
            if(this.numeroDocumentos==0){
                /*Editamos los archivos en el openkm*/
                editarAchivosOpenKm();
                daoPublicacion.edit(actual);
                System.out.println("Datos editados");
                mensajeEditar();
                redirigirPublicacionesEst();
            }
        }
    }
    
    /**
     * Funcion que actualiza los documentos previamente registrados en el openkm
     * por los nuevos que quiero subir. Se busca en openKm con el id de la 
     * publicacion y el tipo de documento y se reemplaza la informacion
     * @return 
     */
    public boolean editarAchivosOpenKm(){
        boolean edicion = false;
        int contadorDocumentos = 0;
        OKMWebservices ws = ConeccionOpenKM.getInstance().getWs();
        
        String tipoDoc = "";
        if (this.actual.getIdTipoDocumento().getIdentificador() == 4) {
            tipoDoc = "revista";
        }
        if (this.actual.getIdTipoDocumento().getIdentificador() == 3) {
            tipoDoc = "congreso";
        }
        if (this.actual.getIdTipoDocumento().getIdentificador() == 2) {
            tipoDoc = "capLibro";
        }
        if (this.actual.getIdTipoDocumento().getIdentificador() == 1) {
            tipoDoc = "libro";
        }
        Map<String, String> properties = new HashMap();                        
        properties.put("okp:"+tipoDoc+".identPublicacion", "" + actual.getPubIdentificador()); 
        
        while(contadorDocumentos<3){
            System.out.println("contador "+contadorDocumentos);
            UploadedFile documento = null;
            String tipoPDF = "";
            if(contadorDocumentos == 0){
                if (!publicacionPDF.getFileName().equalsIgnoreCase("")) {
                    tipoPDF = "tipoPublicacion";
                    documento = publicacionPDF;
                }
            }
            if(contadorDocumentos == 1){
                if (!cartaAprobacionPDF.getFileName().equalsIgnoreCase("")) {
                    tipoPDF = "cartaAprobacion";
                    documento = cartaAprobacionPDF;
                }
            }
            if(contadorDocumentos == 2){
                if (!TablaContenidoPDF.getFileName().equalsIgnoreCase("")) {
                    tipoPDF = "tablaContenido";
                    documento = TablaContenidoPDF;
                }
            }     
            properties.put("okp:"+tipoDoc+".tipoPDFCargar",""+tipoPDF); 
            QueryParams qParams = new QueryParams();
            qParams.setProperties(properties);
            
            try {
  
                List<QueryResult> result = ws.find(qParams);
                if(result.size() == 0){
                    /*No se ha subido el documento anteriormente*/
                    if (!documento.getFileName().equalsIgnoreCase("")) {
                        //3 Tabla de contenido
                        //2 tipoPublicacion
                        //1 cartaAprobacion
                        int numArchivos = daoPublicacion.getIdArchivo();
                        if(actual.AgregaMetadatosDocumento(documento, 3, numArchivos)){
                            System.out.println("El archivo se ha subido");
                        }else{
                            System.out.println("Problemas al subir el archivo");
                        }
                    }
                }else{
                    /*Se modifica el documento subido*/
                    String auxDoc = result.get(0).getDocument().getPath();
                    //editar archivo de open km           
                    //primero se debe hacer un checkout enviando la ruta del archivo que se desea editar
                    ws.checkout(auxDoc);
                    //con un checkin se envia la ruta(debe ser la misma), el flujo de string y 
                    //unas observaciones que se envian en blanco
                    ws.checkin(auxDoc, documento.getInputstream(), "");
                }
                edicion = true;
            }catch (FileSizeExceededException | UserQuotaExceededException | VirusDetectedException | LockException | VersionException | 
                    PathNotFoundException | AccessDeniedException | RepositoryException | IOException | DatabaseException | 
                    ExtensionException | AutomationException | UnknowException | WebserviceException | ParseException ex) {
                Logger.getLogger(PracticaDocenteController.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("error en editarArchivoOpenKM "+ ex.getMessage());
            }
            contadorDocumentos++;
        }
        return edicion;
    }

    //<editor-fold defaultstate="collapsed" desc="metodos para rediriguir">  
    /**
     * Metodo que redirige a la vista de una publicacion en particular.
     * Esta vista es propia del rol coordinador
     * @param pub 
     */
    public void verPublicacion(Publicacion pub) {
        actual = pub;
        cvc.verPublicacionCoordinador();
        Utilidades.redireccionar(cvc.getRuta());
    }

    /**
     * Metodo para redirigir a la vista de una publicacion. Esta vista
     * mostrara de forma mas detalla la informacion registrada de la publicacion
     * @param pub 
     */
    public void verPublicacionEst(Publicacion pub) {
        actual = pub;
        cve.verPublicacion();
        Utilidades.redireccionar(cve.getRuta());
    }

    /**
     * Metodo para redirigir a la vista de edicion de la publicacion.
     * Con el parametro se obtiene referencia de la publicacion a editar
     * @param pub 
     */
    public void irAEditar(Publicacion pub) {
        actual = pub;
        cve.editarDocumentacion();
        extraerAutoresSecundarios();
        if (actual.getIdTipoDocumento().getIdentificador() == 3) {
            idPais = actual.getCongreso().getCiudadId().getPaisId().getPaisId();
            idCiudad = actual.getCongreso().getCiudadId().getCiudId();
            actualizarCiudades();
        }
        if (actual.getIdTipoDocumento().getIdentificador() == 1) {
            idPais = actual.getLibro().getCiudadId().getPaisId().getPaisId();
            idCiudad = actual.getLibro().getCiudadId().getCiudId();
            actualizarCiudades();
        }
        Utilidades.redireccionar(cve.getRuta());
    }

    /**
     * redireccionamiento a listar publicacion
     */
    public void redirigirAlistarPublicionesEst() {
        //limpiarCampos();//Cambio               
        cve.verPublicaciones();
        Utilidades.redireccionar(cve.getRuta());
    }
    
    /**
     * Metodo para redirigir a la vista del listado de publicaciones del 
     * estudiante.
     */
    public void redirigirPublicacionesEst() {
        System.out.println("Redirigiendo a publicaciones del estudiante");
        cve.verPublicaciones();
        Utilidades.redireccionar(cve.getRuta());
    }

    public void redirigirAlistarCoord(String nombreUsuario) {
        //limpiarCampos();
        System.out.println("Redirigiendo a coordinador");
        cvc.listarPublicaciones();
        Utilidades.redireccionar(cvc.getRuta());
    }

    public void redirigirAlistar() {

        //limpiarCampos();
        System.out.println("Listando documentacion desde coordinador");
        cvc.listarPublicaciones();
        Utilidades.redireccionar(cvc.getRuta());
    }

    public void redirigirAlistarEspera() {

        limpiarCampos();
        System.out.println("si esta pasando por aqui");

        cvc.listarPublicacionesEspera();
        Utilidades.redireccionar(cvc.getRuta());
    }

    public void redirigirAlistarRevisadas() {

        limpiarCampos();
        System.out.println("Redirigiendo a los documentos revisados");
        cvc.listarPublicacionesRevisadas();
        Utilidades.redireccionar(cvc.getRuta());
    }

    /**
     * Procedimiento para redirigir a la vista de registrar publicaciones
     * @param nombreUsuario
     */
    public void redirigirARegistrar(String nombreUsuario) {
        
        limpiarCampos(nombreUsuario);
        cve.registrarPublicacion();
        Utilidades.redireccionar(cve.getRuta());
    }

    /**
     * Procedimiento para redirigir a la vista de reportes
     */
    public void redirigirAReportes() {
        cve.verReportes();
        Utilidades.redireccionar(cve.getRuta());
    }

    public void redirigirRegistrarPracticaDocente(String nombreUsuario) {
        limpiarCampos(nombreUsuario);
        cve.registrarPractica();
        Utilidades.redireccionar(cve.getRuta());
    }

    public void redirigirALibro(String nombreUsuario) //Cambio
    {
        limpiarCampos(nombreUsuario);
        cve.registrarLibro();
        Utilidades.redireccionar(cve.getRuta());
    }

    /*Redireccion para volver a editar*/
    public void redirigirAEditar() {
        Utilidades.redireccionar("/ProyectoII/faces/componentes/gestionPublicaciones/EditarPublicacion.xhtml");
    }

    public void redirigirGraficaPubReg() {
        cvc.verGraficaPubReg();
        Utilidades.redireccionar(cvc.getRuta());
    }

    public void redirigirPracticaDocente() {
        cvc.listarPracticaDocente();
        Utilidades.redireccionar(cvc.getRuta());
    }

    public void redirigirGraficaPubVis() {
        cvc.verGraficaPubVis();
        Utilidades.redireccionar(cvc.getRuta());
    }
    //</editor-fold>

    /*mensajes de confirmacion */
    public void mensajeEditar() {
        addMessage("Edicion exitosa","Se registrado los cambios de la publicacion satisfactoriamente");
    }

    public void mensajeconfirmarRegistro() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La publicacion se ha registrado exitosamente", ""));
    }

    public void mensajeRegistroFallido() {
        addErrorMessage("Ocurrio un error durante el registro de la Publicacion ", "");
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void addErrorMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }


    public void seleccionarArchivo(FileUploadEvent event) {
        String nombreArchivo = event.getFile().getFileName();
        UploadedFile Archivo = event.getFile();
        FacesMessage message = new FacesMessage("El archivo", event.getFile().getFileName() + " se selecciono exitosamente");
        FacesContext.getCurrentInstance().addMessage(null, message);
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update("filemessage");
    }

    
    //Metodos repetido con documento Controller
     
    /**
     * Funcion para determinar si renderiza los campos de revista 
     * @return 
     */
    public boolean renderizarRevista() {
        return actual.getIdTipoDocumento().getIdentificador() == 4;
    }

    /**
     * Funcion que determina si renderiza los campos de congreso
     * @return 
     */
    public boolean renderizarCongreso() {
        return actual.getIdTipoDocumento().getIdentificador() == 3;
    }

    /**
     * Funcion que determina si renderiza los campos de libro
     * @return 
     */
    public boolean renderizarLibro() {
        return actual.getIdTipoDocumento().getIdentificador() == 1;
    }

    /**
     * Funcion que determina si renderiza los campos de capitulo libro
     * @return 
     */
    public boolean renderizarCapLibro() {
        return actual.getIdTipoDocumento().getIdentificador() == 2;
    }


    public void mensajeVisar() {
        addMessage("Ha visado satisfactoriamente la publicacion", "");
    }

    public void mensajeEditarCreditos() {
        addMessage("Ha editado satisfactoriamente los creditos de la publicacion", "");
    }

    /*
    public void visarPublicacion() {
        int auxCreditos = Integer.parseInt(creditos);
        int acta = Integer.parseInt(numActa);
        if (actual.getPubVisado().equalsIgnoreCase("aceptada")) {
            int creditos_actuales = actual.getPubEstIdentificador().getEstCreditos();           
            //hay que agregar logica de creditos aqui
            int creditos_nuevos = creditos_actuales + getCreditosByTipoPub("tiopPub") ;
            creditos_nuevos = creditos_nuevos + auxCreditos;
            actual.getPubEstIdentificador().setEstCreditos(creditos_nuevos);            
            actual.setPubNumActa(acta);
            daoEst.edit(actual.getPubEstIdentificador());
            Utilidades.enviarCorreo("" + actual.getPubEstIdentificador().getEstCorreo(), "Mensaje Sistema Doctorados Electronica Unicauca - Edición de Creditos de publicacion", "" + "\n" + "\n" + "Cordial Saludo " + "\n" + "\n" + "A la publicación de nombre " + actual.obtenerNombrePub() + " se le han editado los creditos obtenidos, el nuevo número de creditos asignados es: " + auxCreditos);
            daoPublicacion.edit(actual);
            daoPublicacion.flush();
            mensajeEditarCreditos();
            redirigirAlistarRevisadas();
        } // Si no la publicacion no ha sido aceptada 
            // indica que esta en espera  
        else {
            if (actual.getPubEstIdentificador().getEstCreditos() == null) {
                actual.getPubEstIdentificador().setEstCreditos(auxCreditos);                
                actual.setPubNumActa(acta);
                daoEst.edit(actual.getPubEstIdentificador());
                actual.setPubVisado("aceptada");
                Utilidades.enviarCorreo("" + actual.getPubEstIdentificador().getEstCorreo(), "Mensaje Sistema Doctorados Electronica Unicauca - Asignación de Creditos de publicación", "" + "\n" + "\n" + "Cordial Saludo " + "\n" + "\n" + "La publicación de nombre " + actual.obtenerNombrePub() + " ha sido revisada, y se la ha asignado el siguiente número de creditos: " + auxCreditos);
                daoPublicacion.edit(actual);
                daoPublicacion.flush();
                mensajeVisar();
                redirigirAlistarRevisadas();

            } else {
                int creditos_actuales = actual.getPubEstIdentificador().getEstCreditos();
                int creditos_nuevos = creditos_actuales + auxCreditos;
                actual.getPubEstIdentificador().setEstCreditos(creditos_nuevos);                
                actual.setPubNumActa(acta);
                actual.setPubVisado("aceptada");
                Utilidades.enviarCorreo("" + actual.getPubEstIdentificador().getEstCorreo(), "Mensaje Sistema Doctorados Electronica Unicauca - Asignación de Creditos de publicación", "" + "\n" + "\n" + "Cordial Saludo " + "\n" + "\n" + "La publicación de nombre " + actual.obtenerNombrePub() + " ha sido revisada, y se la ha asignado el siguiente número de creditos: " + auxCreditos);
                daoEst.edit(actual.getPubEstIdentificador());
                daoPublicacion.edit(actual);
                daoPublicacion.flush();
                mensajeVisar();
                redirigirAlistarRevisadas();
            }
        }
    }*/

    /**
     * obtiene la cantidad de creditos que se deben asignar dependiendo del tipo
     * de publicacion
     *
     * @param tipoPub tipo de publicacion que se registro
     * @return numero de creditos correspondientes al tipo de publicacion
     */
    public int getCreditosByTipoPub(String tipoPub) {
        return 1;
    }

    public void mensajeRechazar() {
        addMessage("El visado de la publicacion ha sido rechazado", "");
    }

    public void RechazarPublicacion() {
        actual.setPubVisado("rechazada");
        daoPublicacion.edit(actual);
        daoPublicacion.flush();
        Utilidades.enviarCorreo("" + actual.getPubEstIdentificador().getEstCorreo(), "Mensaje Sistema Doctorados Electronica Unicauca - Revisión de publicación", "" + "\n" + "\n" + "Cordial Saludo " + "\n" + "\n" + "La publicación de nombre " + actual.obtenerNombrePub() + " ha sido revisada y se determino que no se aprueba, el motivo es el siguiente: " + "\n" + motivoRechazo);
        mensajeRechazar();
        redirigirAlistarRevisadas();
    }

    public String getUploadedFileName() {
        return uploadedFileName;
    }

    public void setUploadedFileName(String uploadedFileName) {
        this.uploadedFileName = uploadedFileName;
    }

    public void handleFileUpload(FileUploadEvent event) {
        System.out.println(" uploadedFileName=event.getFile().getFileName();");
        uploadedFileName = event.getFile().getFileName();
    }

    public void validateFile(FacesContext ctx, UIComponent comp, Object value) {
        List<FacesMessage> msgs = new ArrayList<>();
        UploadedFile file = (UploadedFile) value;
        if (file.getSize() > 1024) {
            msgs.add(new FacesMessage("file too big"));
        }
        if (!"text/plain".equals(file.getContentType())) {
            msgs.add(new FacesMessage("not a text file"));
            FacesContext.getCurrentInstance().addMessage("valTContenido", new FacesMessage(FacesMessage.SEVERITY_ERROR, " not a text file", ""));
        }
    }

    /**
     * Metodo para obtener los autores secundarios de la publicacion. Si hay
     * autores secundarios se retorn los nombres de lo contrario se retorna
     * "Ninguno"
     * @return 
     */
    public String getAutoresSecundarios(){
        if(!actual.getPubAutoresSecundarios().equals("") || !actual.getPubAutoresSecundarios().isEmpty()){
            return actual.getPubAutoresSecundarios();
        }else{
            return "Ninguno";
        }
    }
    
    public void habilitarPublicacion(int id) {
        try {
            actual = daoPublicacion.find(id);
            actual.setPubEstado("Activo");
            daoPublicacion.edit(actual);
            daoPublicacion.flush();
            mensajeConfirmacionHabilitacion();
        } catch (EJBException e) {
            System.out.println("error habilitando edicion.");
            System.out.println("error." + e.getMessage());

        }
    }

    public void mensajeConfirmacionHabilitacion() {
        addMessage("Ha habilitado satisfactoriamente la publicacion indicada.", "");
    }

    //<editor-fold defaultstate="collapsed" desc="cambiar estado visado">    
    String visado = "";

    public String getVisado() {
        return visado;
    }

    public void setVisado(String visado) {
        this.visado = visado;
    }

    /**
     * Método que permite modificar los créditos de la publicación según el tipo
     * de documento, y suma dichos créditos a los créditos actuales del
     * estudiente.
     */
    private void cambiarCreditos() {
        int idTipoDocumento = actual.getIdTipoDocumento().getIdentificador();
        int creditosPub = daoPublicacion.getCreditosTipoPubicacionPorID(idTipoDocumento);
        int creditosEst = actual.getPubEstIdentificador().getEstCreditos();
        actual.setPubCreditos(creditosPub);
        actual.getPubEstIdentificador().setEstCreditos(creditosEst + creditosPub);
        daoEst.edit(actual.getPubEstIdentificador());
        daoPublicacion.edit(actual);
    }

    /**
     * Método que permite cambia el estado de visado de la publicación que se 
     * esta revisando. Se notifica al estudiante mediante correo que la publicacion
     * ha sido aprobada/rechazada. Solo si se aprobo, se modifica el numero de creditos
     * de la publicacion. Si se rechazo,se envian las observaciones.
     */
    public void cambiarEstadoVisado() {
        if (!visado.equals("")) {
            actual.setPubVisado(visado);
            daoPublicacion.edit(actual);
            boolean aprobo = false;
            if (visado.equalsIgnoreCase("Aprobado")) {
                cambiarCreditos();
                aprobo = true;
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Se ha modificado el estado de la publicacion exitosamente", ""));
            Utilidades.correoVisadoPublicacion(aprobo, actual.getPubEstIdentificador(), actual.obtenerNombrePub(), this.valorTexto);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="adicionar autores secundarios dinamicamente">    
    List<Autor> listaAutores = new ArrayList<>();

    /**
     * obtiene la lista de autores secudarios que se han ingresado
     *
     * @return lista de autores secundarios ingresados
     */
    public List<Autor> getListaAutores() {
        return listaAutores;
    }

    /**
     * fija una lista de autores a la lista listaAutores de la clase publicacion
     *
     * @param listaAutores lista de autores que se desea fijar
     */
    public void setListaAutores(List<Autor> listaAutores) {
        this.listaAutores = listaAutores;
    }

    /**
     * Metodo que agrega temporalmente los autores secundarios en una lista.
     */
    public void agregarAutorSecundario() {
        System.out.println("Agregando autores secundarios");
        if (!nombreAutor.equals("")) {
            if(estaAutorSecundario(this.getNombreAutor())){
                System.out.println("Nombre autor repetido");
            }else{
                listaAutores.add(new Autor(this.getNombreAutor()));
            }
        }
        nombreAutor = "";
    }
    
    /**
     * Funcion para saber si un autor secundario ya esta en la lista de 
     * autores secundarios
     * @param nombre
     * @return 
     */
    public boolean estaAutorSecundario(String nombre){
        if(listaAutores.contains(new Autor(nombre))){
            return true;
        }else{
            return false;
        }
    }
    

    /**
     * Elimina un autor secundario de la lista de autores secundarios
     * @param nombre nombre del autor a eliminar
     */
    public void eliminarAutorSecundario(String nombre) {
        for (int i = 0; i < listaAutores.size(); i++) {
            if (listaAutores.get(i).getNombre().equals(nombre)) {
                listaAutores.remove(i);
                break;
            }
        }
    }

    private String nombreAutor = "";

    /**
     * obtener nombre de autor almacenado en la variable nomreAutor
     *
     * @return contenido de variable nombreAutor
     */
    public String getNombreAutor() {
        return nombreAutor;
    }

    /**
     * fijar el contienido de la variable nombreAutor en la variable local
     * nombreAutor
     *
     * @param nombreAutor nombre que se desea fijar
     */
    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    /**
     * almacena el texto digitado, en la variable nombreAutor
     *
     * @param evt evento ajax
     */
    public void recibirTextoAutor(AjaxBehaviorEvent evt) {
        String texto = "" + ((UIOutput) evt.getSource()).getValue();
        this.nombreAutor = texto;
    }

    /**
     * saca los elemntos de la lista e autores secundarios, les da formato
     * camelType, concatena los nombres de cada autor e ingresa la cadena en la
     * variable autores secundarios de la entidad publicacion
     */
    private void fijarAutoresSecundarios() {
        String autores = "";
        for (Autor autor : listaAutores) {
            //eliminar espacios al inio y al final
            autor.setNombre(autor.getNombre().trim());
            //partir cadena de nombre delimitados por espacio
            String[] nombres = autor.getNombre().split(" ");
            //convertir primera letra a mayuscula y concatenar nombres
            for (String nombre : nombres) {
                autores += Character.toUpperCase(nombre.charAt(0)) + nombre.substring(1) + " ";
            }
            autores = autores.trim();
            autores += ", ";
        }
        if (!listaAutores.isEmpty()) {
            autores = autores.substring(0, autores.length() - 2);
        }
        actual.setPubAutoresSecundarios(autores);
    }

    private void extraerAutoresSecundarios() {
        listaAutores = new ArrayList<>();
        String cadenaAutores = actual.getPubAutoresSecundarios();
        if (!cadenaAutores.isEmpty()) {
            String[] autores = cadenaAutores.split(",");
            for (String nombreAutor : autores) {
                listaAutores.add(new Autor(nombreAutor));
            }
        }

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="adicionar comentario por publicacion no aprobada">
    private String valorTexto = "";

    /**
     * almacena el texto digitado, en la variable valorTexto
     *
     * @param evt evento ajax
     */
    public void recibirTexto(AjaxBehaviorEvent evt) {
        String texto = "" + ((UIOutput) evt.getSource()).getValue();
        this.valorTexto = texto;
        System.out.println("en recibir texto: " + texto);
    }
    //</editor-fold>              

    //<editor-fold defaultstate="collapsed" desc="validar que no se encuentre almacenada previamente la informacion de un recurso por estudiante">        
    //revista
    public Revista buscarDoiRevista(String doi) {
        return daoRevista.findByDoiRevista(doi);
    }

    public Revista buscarTituloArticulo(String titulo) {
        return daoRevista.findByTituloArticulo(titulo);
    }

    //congreso
    public Congreso buscarIssnCongreso(String issn) {
        return daoCongreso.findByIssnCongreso(issn);
    }

    public Congreso buscarDoiCongreso(String doi) {
        return daoCongreso.findByDoiCongreso(doi);
    }

    public Congreso buscarPonenciaPorTitulo(String tituloPonencia) {
        return daoCongreso.findByTituloPonencia(tituloPonencia);
    }

    //libro
    public Libro buscarIsbnLibro(String isbn) {
        return daoLibro.findByIsbnLibro(isbn);
    }

    public Libro buscarLibroPorTitulo(String tituloLibro) {
        return daoLibro.findByTituloLibro(tituloLibro);
    }

    //capituloLibro
    public CapituloLibro buscarTituloCapituloLibro(String tituloLibro) {
        return daoCapituloLibro.findByTituloCapituloLibro(tituloLibro);
    }

    public CapituloLibro buscarIsbnCapituloLibro(String issn) {
        return daoCapituloLibro.findByIsbnLibro(issn);
    }
    //</editor-fold>   

    //<editor-fold defaultstate="collapsed" desc="País y Ciudad">
    @PostConstruct
    public void init() {
        this.listaPaises = this.ejbPais.findAll();
        this.listaCiudades = new ArrayList<>();
    }

    public List<Ciudad> getListaCiudades() {
        return listaCiudades;
    }

    public void setListaCiudades(List<Ciudad> listaCiudades) {
        this.listaCiudades = listaCiudades;
    }

    public List<Pais> getListaPaises() {
        return listaPaises;
    }

    public void setListaPaises(List<Pais> listaPaises) {
        this.listaPaises = listaPaises;
    }

    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }

    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }

    /**
     * Metodo para actualizar la lista de ciudades segun el pais seleccionado.
     */
    public void actualizarCiudades() {
        System.out.println("lista de ciudades de " + idPais);
        this.listaCiudades = this.ejbCiudad.getCiudadPorPais(this.idPais);
    }
    //</editor-fold>
}
