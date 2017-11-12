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
import co.unicauca.proyectobase.entidades.Pais;
//import static co.unicauca.proyectobase.entidades.GrupoTipoUsuario_.nombreUsuario;
import co.unicauca.proyectobase.entidades.archivoPDF;
import co.unicauca.proyectobase.utilidades.Autor;
import co.unicauca.proyectobase.utilidades.Utilidades;
import com.openkm.sdk4j.exception.LockException;
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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
/*import java.nio.charset.StandardCharsets;
import java.util.Base64;*/
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
    private Estudiante auxEstudiante;

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
    
    /*Datos para conectar al openKm*/
    /*String host = "http://localhost:8083/OpenKM";
    String username = "okmAdmin";
    String password = "admin";*/

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

    public void onComplete() {  
      FacesContext.getCurrentInstance().addMessage(null, new  FacesMessage(FacesMessage.SEVERITY_INFO, "Progress Completed", "Progress Completed"));  
    } 
  
    public void visPdfPub() throws IOException {

        archivoPDF archivoPublic = actual.descargaPublicacion();
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

    public StreamedContent getStreamedContent() throws IOException {
        if (streamedContent != null) {
            streamedContent.getStream().reset(); //reset stream to the start position!
        }
        return streamedContent;
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

    public String obtenerNombreUsuario(String nombreUsuario){
        return daoEst.findNombre(nombreUsuario);        
    }
    
    /**
     * metodo para buscar el nombre de usuario de cada publicacion que desea ver el coordinador
     * @return nombre de usuario
     */
    public String obtenerNombreUsuarioCoor(){
        return daoEst.findNombreById(actual.getPubEstIdentificador());        
    }
        
    public String obtenerNombreUsuarioById(Estudiante nombreUsuario){
        return daoEst.findNombreById(nombreUsuario);
        
    }
    public String getCreditos() {
        creditos = "" + daoEst.findCreditosByNombreUsuario(nombreAutor);
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

    String INICIO = "index";
    String CREAR = "new";
    String EDITAR = "editar";

    public PublicacionController() {
        cve = new CargarVistaEstudiante();
        cvc = new CargarVistaCoordinador();
        this.listaPaises = new ArrayList<>();
    }

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
    
    public List<Publicacion> listadoPublicaciones(String nombreUsuario) {
        Estudiante est = daoPublicacion.obtenerEstudiante(nombreUsuario);
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

    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("MM/yyyy");
        facesContext.addMessage("event", new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }
    
    public void pdfCartaAprob() throws FileNotFoundException, IOException, IOException, IOException {
        archivoPDF archivoPublic = actual.descargaCartaAprobac();
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
            // response.getOutputStream().write(buf);
            response.getOutputStream().flush();
            response.getOutputStream().close();
            FacesContext.getCurrentInstance().responseComplete();
        }
    }

    public void pdfPub() throws FileNotFoundException, IOException, IOException, IOException {
        archivoPDF archivoPublic = actual.descargaPublicacion();
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

    public void pdfPubTC() throws FileNotFoundException, IOException, IOException, IOException {
        archivoPDF archivoPublic = actual.descargaPubTC();
        if (archivoPublic.getNombreArchivo().equals("")) {
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

    public void descargarCartaAprobac() throws FileNotFoundException, IOException {
        archivoPDF archivoPublic = actual.descargaCartaAprobac();
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

    public void descargarPublicacion() throws FileNotFoundException, IOException {
        archivoPDF archivoPublic = actual.descargaPublicacion();
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

    public void descargarPubTC() throws FileNotFoundException, IOException {
        archivoPDF archivoPubTC = actual.descargaPubTC();
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
     * agregar una publicacion a la base de datos y openkm
     * @throws IOException 
     */
    public void agregar() throws IOException {
        System.out.println("Registrando documentacion");
        /* formatoValido -> se utiliza para verificar que el usario
           suba unicamente archivos en formato pdf*/
        boolean formatoValido = true;
        String tituloMensaje = "";
        String mensaje = "";
        
        if (!publicacionPDF.getFileName().equalsIgnoreCase("") && !"application/pdf".equals(publicacionPDF.getContentType())) {
            tituloMensaje = "valPublicacion";
            mensaje = "Debe subir la publicación o la evidencia de la publicación en formato PDF";
            formatoValido = false;
        }
        if (!TablaContenidoPDF.getFileName().equalsIgnoreCase("") && !"application/pdf".equals(TablaContenidoPDF.getContentType())) {
            tituloMensaje = "valTContenido";
            mensaje = "Debe subir la Tabla de Contenido en formato PDF";
            formatoValido = false;
        }
        if (!cartaAprobacionPDF.getFileName().equalsIgnoreCase("") && !"application/pdf".equals(cartaAprobacionPDF.getContentType())) {
            tituloMensaje = "cartaAprobacion";
            mensaje = "Debe subir la carta de aprobación en formato PDF";
            formatoValido = false;
        }

        if (formatoValido == true) {
            /* puedeSubir  ->  se utiliza para comprobar que el usuario ha seleccionado 
                el PDF de la publicacion o en su defecto la carta de aprobacion*/
            boolean puedeSubir = false;
            if (publicacionPDF.getFileName().equalsIgnoreCase("")) {
                if (cartaAprobacionPDF.getFileName().equalsIgnoreCase("")) {
                    FacesContext.getCurrentInstance().addMessage("cartaAprobacion", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe subir la publicación o la evidencia de la publicación", ""));
                } else {
                    puedeSubir = true;
                }
            } else {
                puedeSubir = true;
            }

            if (puedeSubir) {                
                System.out.println("Agregando documentacion");
                Estudiante est = getAuxEstudiante();
                try {

                    actual.setPubEstIdentificador(est);
                    String nombreAut = est.getEstNombre() + " " + est.getEstApellido();
                    
                    int pub_identificador = daoPublicacion.getnumFilasPubRev();
                    actual.setPubIdentificador(pub_identificador);                    

                    //<editor-fold defaultstate="collapsed" desc="adicion de campos dependiendo tipo de publicacion">                   
                    /* Dependiendo de si se adiciona una revista, un congreso,un libro o un  capitulo de un libro se crea el objeto respectivo*/
                    if (getTipoPublicacion().equals("revista")) {
                        actual.getRevista().setPubIdentificador(pub_identificador);
                        actual.getRevista().setPublicacion(actual);
                        actual.getRevista().setRevDoi(actual.getRevista().getRevDoi());
                        actual.setCongreso(null);
                        actual.setCapituloLibro(null);
                        actual.setLibro(null);
                    }
                    if (getTipoPublicacion().equals("congreso")) {
                        actual.getCongreso().setPubIdentificador(pub_identificador);
                        actual.getCongreso().setPublicacion(actual);
                        actual.getCongreso().setCongIssn(actual.getCongreso().getCongIssn());
                        actual.getCongreso().setCongDoi(actual.getCongreso().getCongDoi());
                        actual.getCongreso().setCiudadId(ejbCiudad.getCiudadPorId(idCiudad));
                        actual.setRevista(null);
                        actual.setCapituloLibro(null);
                        actual.setLibro(null);
                    }
                    if (getTipoPublicacion().equals("libro")) {                        
                        actual.getLibro().setPubIdentificador(pub_identificador);
                        actual.getLibro().setPublicacion(actual);
                        actual.getLibro().setLibIsbn(actual.getLibro().getLibIsbn());
                        actual.getLibro().setCiudadId(ejbCiudad.getCiudadPorId(idCiudad));
                        actual.setRevista(null);
                        actual.setCongreso(null);
                        actual.setCapituloLibro(null);
                    }
                    if (getTipoPublicacion().equals("capitulo libro")) {                        
                        actual.getCapituloLibro().setPubIdentificador(pub_identificador);
                        actual.getCapituloLibro().setPublicacion(actual);
                        actual.getCapituloLibro().setCaplibIsbn(actual.getCapituloLibro().getCaplibIsbn());
                        actual.setRevista(null);
                        actual.setCongreso(null);
                        actual.setLibro(null);
                    }
                    actual.setIdTipoDocumento(daoPublicacion.obtenerIdTipoDocumento(getTipoPublicacion()));
                    
                    //</editor-fold>

                    ArrayList<Archivo> CollArchivo = new ArrayList<>();
                    int numArchivos = daoPublicacion.getIdArchivo();

                    Archivo archCartaAprob = new Archivo();
                    archCartaAprob.setArcPubIdentificador(actual);
                    archCartaAprob.setArcIdentificador(numArchivos);
                    archCartaAprob.setArctipoPDFcargar("cartaAprobacion");
                    CollArchivo.add(archCartaAprob);

                    
                    if (!publicacionPDF.getFileName().equalsIgnoreCase("")) {
                        Archivo archArt = new Archivo();
                        numArchivos = numArchivos + 1;
                        archArt.setArcPubIdentificador(actual);
                        archArt.setArcIdentificador(numArchivos);
                        archArt.setArctipoPDFcargar("tipoPublicacion");
                        CollArchivo.add(archArt);
                    }
                    if (!TablaContenidoPDF.getFileName().equalsIgnoreCase("")) {
                        Archivo arcTablaC = new Archivo();
                        numArchivos = numArchivos + 1;
                        arcTablaC.setArcPubIdentificador(actual);
                        arcTablaC.setArcIdentificador(numArchivos);
                        arcTablaC.setArctipoPDFcargar("tablaContenido");
                        CollArchivo.add(arcTablaC);
                    }
                    actual.setArchivoCollection(CollArchivo);
                    actual.setPubEstado("Activo");                    
                    actual.setPubVisado("espera");                    
                    fijarAutoresSecundarios();
                    
                    /*Aqui se suben los archivos al OpenKm*/
                    actual.agregarMetadatos(publicacionPDF, TablaContenidoPDF, cartaAprobacionPDF);
                    
                    /*Crear registro en la bd*/
                    daoPublicacion.create(actual);
                    daoPublicacion.flush();
                    mensajeconfirmarRegistro();

                    Date date = new Date();
                    DateFormat datehourFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    String estampaTiempo = "" + datehourFormat.format(date);
                    //Utilidades.enviarCorreo("posgradoselectunic@gmail.com", "Mensaje Sistema Doctorados - Registro Publicación", "Estudiante " + nombreAut + " ha regitrado una publicación del tipo " + actual.getPubTipoPublicacion() + " en la siguiente fecha y hora: " + estampaTiempo);
                    Utilidades.enviarCorreo("posgradoselectunic@gmail.com", "Notificación registro de publicación DCE", "Estimado estudiante." + nombreAut + "\n" + "Se acaba de regitrar una publicación del tipo " + actual.getIdTipoDocumento().getNombre()+ " en la siguiente fecha y hora: " + estampaTiempo);
                    
                } catch (EJBException ex) {
                    mensajeRegistroFallido();
                    Logger.getLogger(PublicacionController.class.getName()).log(Level.SEVERE, null, ex);
                }
                limpiarCampos();
                //redirigirPublicacionesEst(est.getEstUsuario());
                redirigirPublicacionesEst();
            }
        }else{
            FacesContext.getCurrentInstance().addMessage(tituloMensaje, new FacesMessage(FacesMessage.SEVERITY_ERROR,mensaje, ""));
        }

    }
    
    /***
     * Metodo para eliminar una documentacion que se halla registrado.
     * Con la publicacion que recibo se verifica que no halla sido visada por
     * el coordinador. Si no ha sido visada, se pasa a borrar la informacion 
     * registrada de esta documentacion en openkm y en la base de datos
     * @param pub
     * @throws LockException 
     */
    public void eliminarDocumentacion(Publicacion pub) throws LockException{
        actual = pub;
        /*Comprobamos que no halla sido visada*/
        if (actual.getPubVisado().equalsIgnoreCase("aprobado") ||
                actual.getPubVisado().equalsIgnoreCase("no aprobado") ) {
            /*No se puede eliminar la publicacion*/
            System.out.println("No se puede eliminar. La documentacion ya ha sido revisada");
            addMessage("La publicación no se puede eliminar por que ha sido aceptada por el coordinador", "");
        }else{
            try{
                /*Eliminamos primero los documentos que estan en el openKm
                  y luego eliminamos los registros de la base de datos*/
                actual.eliminarDocOpenkm();
                daoPublicacion.remove(actual);
                daoPublicacion.flush();
                System.out.println("Documentacion eliminada");
                
            }catch(LockException e){
                addMessage("La publicación no se ha podido eliminar", "");
                System.out.println("PublicacionController: "+e.getMessage());
            }
        }
        redirigirPublicacionesEst();
    }

    /**
     * vuelve a inicializar todos los objetos de la clase pubicacion
     */
    public void limpiarCampos() {        
        actual = new Publicacion();
        Revista revi = new Revista();
        Congreso cong = new Congreso();
        Libro libr = new Libro();
        CapituloLibro caplib = new CapituloLibro();
        listaAutores.clear();
        actual.setRevista(revi);
        actual.setCongreso(cong);
        actual.setLibro(libr);
        actual.setCapituloLibro(caplib);
        tipoPublicacion = "";
    }

    /**
     * vuelve a inicializar todos los objetos de la clase pubicacion
     * @param nombreUsuario nombre de usuario con el cual se desea reiniciar las variables
     */
    public void limpiarCampos(String nombreUsuario) {
        actual = new Publicacion();
        Revista revi = new Revista();
        Congreso cong = new Congreso();
        Libro libr = new Libro();
        CapituloLibro caplib = new CapituloLibro();
        actual.setRevista(revi);
        actual.setCongreso(cong);
        actual.setLibro(libr);
        actual.setCapituloLibro(caplib);
        Estudiante est = daoPublicacion.obtenerEstudiante(nombreUsuario);
        setAuxEstudiante(est);
        tipoPublicacion = "";
    }

    public void fijarEstudiante(String nombreUsuario) {
        Estudiante est = daoPublicacion.obtenerEstudiante(nombreUsuario);
        setAuxEstudiante(est);
    }

    public String getnombreAut() {
        Estudiante est = getAuxEstudiante();
        return est.getEstNombre() + " " + est.getEstApellido();        
    }

    public String guardarEdicion() {
        daoPublicacion.edit(actual);
        mensajeEditar();        
        redirigirPublicacionesEst();
        return INICIO;
    }

    //<editor-fold defaultstate="collapsed" desc="metodos para rediriguir">    
    public void verPublicacion(Publicacion pub) {
        actual = pub;
        cvc.listarPublicacionesEstudiante();
        Utilidades.redireccionar(cvc.getRuta());
    }

    public void verPublicacionEst(Publicacion pub) {
        actual = pub;
        cve.verPublicacion();
        Utilidades.redireccionar(cve.getRuta());
    }

    public void irAEditar(Publicacion pub) {
        actual = pub;
        cve.editarDocumentacion();
        extraerAutoresSecundarios();
        Utilidades.redireccionar(cve.getRuta());
    }
    
    /**
     * redireccionamiento a listar publicacion     
     */
    public void redirigirAlistarPublicionesEst() 
    {
        limpiarCampos();//Cambio               
        cve.verPublicaciones();
        Utilidades.redireccionar(cve.getRuta());
    }
    
    public void redirigirPublicacionesEst() 
    {        
        System.out.println("Redirigiendo a publicaciones");
        cve.verPublicaciones();
        Utilidades.redireccionar(cve.getRuta());
    }
    
    public void redirigirAlistarCoord(String nombreUsuario) 
    {
        limpiarCampos();
        System.out.println("si esta pasando por aqui");
        
        cvc.listarPublicaciones();
        Utilidades.redireccionar(cvc.getRuta());
    }

    public void redirigirAlistar() {

        limpiarCampos();
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
     * redireccion para volver a registrar
     * @param nombreUsuario 
     */    
    public void redirigirARegistrar(String nombreUsuario) {
        limpiarCampos(nombreUsuario);
        cve.registrarPublicacion();
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

    public void redirigirGraficaPubReg() 
    {
        cvc.verGraficaPubReg();
        Utilidades.redireccionar(cvc.getRuta());
    }
    
     public void redirigirPracticaDocente() 
    {
        cvc.listarPracticaDocente();
        Utilidades.redireccionar(cvc.getRuta());
    }

    public void redirigirGraficaPubVis() 
    {
        cvc.verGraficaPubVis();
        Utilidades.redireccionar(cvc.getRuta());
    }
    //</editor-fold>

    /*mensajes de confirmacion */
    public void mensajeEditar() {
        addMessage("ha editado satisfactoriamente la publicacion", "");
    }

    public void mensajeconfirmarRegistro() {
        addMessage("Publicacion Registrada con exito ", "");
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

    public Estudiante getAuxEstudiante() {
        return auxEstudiante;
    }

    public void setAuxEstudiante(Estudiante auxEstudiante) {
        this.auxEstudiante = auxEstudiante;
    }

    public String getVariableFiltrado() {
        return variableFiltrado;
    }

    public void setVariableFiltrado(String variableFiltrado) {
        this.variableFiltrado = variableFiltrado;
    }

    public void seleccionarArchivo(FileUploadEvent event) {
        String nombreArchivo = event.getFile().getFileName();
        UploadedFile Archivo = event.getFile();
        FacesMessage message = new FacesMessage("El archivo", event.getFile().getFileName() + " se selecciono exitosamente");
        FacesContext.getCurrentInstance().addMessage(null, message);
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update("filemessage");
    }

    /**Metodo repetido con documento Controller*/
    public boolean renderizarRevista() {
        return actual.getIdTipoDocumento().getIdentificador() == 4;
    }

    public boolean renderizarCongreso() {
        return actual.getIdTipoDocumento().getIdentificador() == 3;
    }

    public boolean renderizarLibro() {
        return actual.getIdTipoDocumento().getIdentificador() == 1;
    }

    public boolean renderizarCapLibro() {
        return actual.getIdTipoDocumento().getIdentificador() == 2;
    }

    /*public void asignarCreditos() {
        // Obtiene la fecha correspondiente al moemento en el que se 
            //realiza el visado de la publicacion
       Date date = new Date();

        int auxCreditos = Integer.parseInt(creditos);        
        actual.setPubFechaVisado(date);
        daoPublicacion.edit(actual);
        daoPublicacion.flush();
        redirigirAlistar();
    }*/

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
     * obtiene la cantidad de creditos que se deben asignar dependiendo del tipo de publicacion
     * @param tipoPub tipo de publicacion que se registro
     * @return numero de creditos correspondientes al tipo de publicacion
     */
    public int getCreditosByTipoPub(String tipoPub){
        return 1;
    }

    public void mensajeRechazar() {
        addMessage("El visado de la publicacion ha sido rechazado", "");
    }

    public void RechazarPublicacion() {
        actual.setPubVisado("rechazada");
        daoPublicacion.edit(actual);
        daoPublicacion.flush();
        Utilidades.enviarCorreo("" + actual.getPubEstIdentificador().getEstCorreo(), "Mensaje Sistema Doctorados Electronica Unicauca - Revisión de publicación", "" + "\n" + "\n" + "Cordial Saludo " + "\n" + "\n" +"La publicación de nombre " + actual.obtenerNombrePub() + " ha sido revisada y se determino que no se aprueba, el motivo es el siguiente: " + "\n" + motivoRechazo);
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

    public void cambiarEstado(int id) {
        try {
            actual = daoPublicacion.find(id);
            actual.setPubEstado("Inactivo");
            daoPublicacion.edit(actual);
            daoPublicacion.flush();
            mensajeDeshabilitar();
        } catch (EJBException e) {
            System.out.println("error cambiando el estado");
            System.out.println("error. " + e.getMessage());
        }
    }

    public void mensajeDeshabilitar() {
        addMessage("Ha deshabilitado satisfactoriamente la publicacion indicada.", "");
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
     * Método que permite modificar los créditos de la publicación según el tipo de
     * documento, y suma dichos créditos a los créditos actuales del estudiente.
    */
    private void cambiarCreditos()
    {
        int idTipoDocumento = actual.getIdTipoDocumento().getIdentificador();
        int creditosPub = daoPublicacion.getCreditosTipoPubicacionPorID(idTipoDocumento);
        int creditosEst = actual.getPubEstIdentificador().getEstCreditos();
        actual.setPubCreditos(creditosPub);
        actual.getPubEstIdentificador().setEstCreditos(creditosEst + creditosPub);
        daoEst.edit(actual.getPubEstIdentificador());
        daoPublicacion.edit(actual);
    }
    
    /**
     * Método que permite cambia el estado de visado de una publicación en la base de datos.
     */    
    public void cambiarEstadoVisado(){
        if (!visado.equals("")){
            actual.setPubVisado(visado);
            daoPublicacion.edit(actual);
            String correo = actual.getPubEstIdentificador().getEstCorreo();
            
            if(visado.equalsIgnoreCase("Aprobado")){
                cambiarCreditos();
                Utilidades.enviarCorreo(correo, "Notificación revisión de documentos DCE", "Estimado estudiante, " 
                        + actual.getPubEstIdentificador().getEstNombre() + " "
                        + actual.getPubEstIdentificador().getEstApellido()
                        + "\n\nSe acaba de APROBAR la publicación " 
                        + actual.obtenerNombrePub() + "."
                        + "\nQue fue registrada previamente en el sistema de Doctorado en Ciencias de la Electrónica"
                        + "\nNúmero de creditos actuales: " + actual.getPubEstIdentificador().getEstCreditos()
                        + "\n\n\n"+ "Servicio notificación DCE.");
            }
            if(visado.equalsIgnoreCase("No Aprobado")){
                String mensaje = "Estimado estudiante." 
                        + actual.getPubEstIdentificador().getEstNombre() + " " 
                        + actual.getPubEstIdentificador().getEstApellido()
                        + "\n\n Se acaba de RECHAZAR la publicación " 
                        + actual.obtenerNombrePub() + "que previamente fue registrada en el sistema de Doctorado en Ciencias de la Electrónica"
                        + "\n\n"+ "Servicio notificación DCE.";
                if(!valorTexto.equals("")){
                    mensaje = mensaje + "\n\n Observaciones: " + valorTexto;
                    valorTexto = "";
                }                        
                Utilidades.enviarCorreo(correo,"Notificación revisión de documentos DCE", mensaje);                
            }
            if(visado.equalsIgnoreCase("espera")){
                Utilidades.enviarCorreo(correo, "Notificación revisión de documentos DCE", "Estimado estudiante." 
                        + actual.getPubEstIdentificador().getEstNombre()+" "
                        + actual.getPubEstIdentificador().getEstApellido()
                        + "\n\n Se acaba de PONER EN ESPERA la publicación " 
                        + actual.obtenerNombrePub() + "que previamente fue registrada en el sistema de Doctorado en Ciencias de la Electrónica"
                        + "\n\n"+ "Servicio notificación DCE.");
            }
            //dao.cambia1rEstadoVisado(this.actual.getPubIdentificador(),this.visado);
        }    
        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="adicionar autores secundarios dinamicamente">    
    List<Autor> listaAutores = new ArrayList<>();    
    
    /**
     * obtiene la lista de autores secudarios que se han ingresado
     * @return lista de autores secundarios ingresados
     */
    public List<Autor> getListaAutores() {
        return listaAutores;
    }
    
    /**
     * fija una lista de autores a la lista listaAutores de la clase publicacion
     * @param listaAutores lista de autores que se desea fijar
     */
    public void setListaAutores(List<Autor> listaAutores) {
        this.listaAutores = listaAutores;
    }
    
    /**
     * agrega un autor secundario a la lista de autores secundarios
     */
    public void agregarAutorSecundario(){
        System.out.print("adicionando autor");
        if(!nombreAutor.equals("")){
            if(!listaAutores.contains(new Autor(this.getNombreAutor()))){
                listaAutores.add(new Autor(this.getNombreAutor()));
                System.out.println("autor adicionado");
                //System.out.println("  tamaño: " + listaAutores.size());
                //mostrarLista();
            }                        
        }
        else{
            //FacesContext.getCurrentInstance().addMessage("msjValAutores", new FacesMessage(FacesMessage.SEVERITY_ERROR, " not a text file", ""));
            System.out.println("nombre autor repetido");            
        }
        nombreAutor = "";
    }
    
    /**
     * elimina un autor secundario de la lista de autores secundarios
     * @param nombre nombre del autor a eliminar
     */
    public void eliminarAutorSecundario(String nombre){                
        for (int i = 0; i < listaAutores.size(); i++) {
            if(listaAutores.get(i).getNombre().equals(nombre)){
                listaAutores.remove(i);
                break;
            }
        }                
    }        
         
    private String nombreAutor = "";
    /**
     * obtener nombre de autor almacenado en la variable nomreAutor
     * @return contenido de variable nombreAutor
     */
    public String getNombreAutor() {
        return nombreAutor;
    }
    
    /**
     * fijar el contienido de la variable nombreAutor en la variable local 
     * nombreAutor
     * @param nombreAutor nombre que se desea fijar
     */
    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }
    
    /**
     * almacena el texto digitado, en la variable nombreAutor
     * @param evt evento ajax
     */
    public void recibirTextoAutor(AjaxBehaviorEvent evt){
        String texto = "" + ((UIOutput)evt.getSource()).getValue();
        this.nombreAutor = texto;        
    }   
    
    /**
     * saca los elemntos de la lista e autores secundarios, les da formato
     * camelType, concatena los nombres de cada autor e ingresa la cadena
     * en la variable autores secundarios de la entidad publicacion
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
        if(!listaAutores.isEmpty()){
            autores = autores.substring(0, autores.length() - 2);
        }        
        actual.setPubAutoresSecundarios(autores);
    }
    
    private void extraerAutoresSecundarios(){
        listaAutores = new ArrayList<>();
        String cadenaAutores = actual.getPubAutoresSecundarios();
        if(!cadenaAutores.isEmpty()){
            String[] autores = cadenaAutores.split(",");
            for(String nombreAutor : autores){
                listaAutores.add(new Autor(nombreAutor));
            }
        }
        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="adicionar comentario por publicacion no aprobada">
    private String valorTexto = "";
    
    /**
     * almacena el texto digitado, en la variable valorTexto
     * @param evt evento ajax
     */
    public void recibirTexto(AjaxBehaviorEvent evt){
        String texto = "" + ((UIOutput)evt.getSource()).getValue();
        this.valorTexto = texto;
        System.out.println("en recibir texto: " + texto);
    }        
    //</editor-fold>              
    
    //<editor-fold defaultstate="collapsed" desc="validar que no se encuentre almacenada previamente la informacion de un recurso por estudiante">        
    //revista
    public Revista buscarDoiRevista(String doi){
        return daoRevista.findByDoiRevista(doi);
    }
    public Revista buscarTituloArticulo(String titulo){
        return daoRevista.findByTituloArticulo(titulo);
    }
    
    //congreso
    public Congreso buscarIssnCongreso(String issn){
        return daoCongreso.findByIssnCongreso(issn);
    }
    public Congreso buscarDoiCongreso(String doi){
        return daoCongreso.findByDoiCongreso(doi);
    }    
    public Congreso buscarPonenciaPorTitulo(String tituloPonencia) {
        return daoCongreso.findByTituloPonencia(tituloPonencia);
    }
    
    //libro
    public Libro buscarIsbnLibro(String isbn){
        return daoLibro.findByIsbnLibro(isbn);
    }
    public Libro buscarLibroPorTitulo(String tituloLibro) {
        return daoLibro.findByTituloLibro(tituloLibro);
    }
    
    //capituloLibro
    public CapituloLibro buscarTituloCapituloLibro(String tituloLibro) {
        return daoCapituloLibro.findByTituloCapituloLibro(tituloLibro);
    }
    public CapituloLibro buscarIsbnCapituloLibro(String issn){
        return daoCapituloLibro.findByIsbnLibro(issn);
    }            
    //</editor-fold>   

    //<editor-fold defaultstate="collapsed" desc="País y Ciudad">
    
    @PostConstruct
    public void init()
    {
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
    
    public void actualizarCiudades()
    {
        this.listaCiudades = this.ejbCiudad.getCiudadPorPais(idPais);
    }
    //</editor-fold>
}