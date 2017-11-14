package co.unicauca.proyectobase.controladores;

import co.unicauca.proyectobase.entidades.PracticaDocente;
import co.unicauca.proyectobase.controladores.util.JsfUtil;
import co.unicauca.proyectobase.controladores.util.JsfUtil.PersistAction;
import co.unicauca.proyectobase.dao.EstudianteFacade;
import co.unicauca.proyectobase.dao.PracticaDocenteFacade;
import co.unicauca.proyectobase.dao.PublicacionFacade;
import co.unicauca.proyectobase.entidades.ActividadPd;
import co.unicauca.proyectobase.entidades.Archivo;
import co.unicauca.proyectobase.entidades.Estudiante;
import co.unicauca.proyectobase.entidades.Publicacion;
import co.unicauca.proyectobase.entidades.archivoPDF;
import co.unicauca.proyectobase.utilidades.PropiedadesOS;
import co.unicauca.proyectobase.utilidades.Utilidades;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

@Named("practicaDocenteController")
@SessionScoped
public class PracticaDocenteController implements Serializable {

    @EJB
    private PracticaDocenteFacade ejbFacade;
    @EJB
    private PublicacionFacade ejbFacadePub;
    @EJB
    private EstudianteFacade daoEst;
    
    private List<PracticaDocente> items = null;
    private PracticaDocente actual;
    private Publicacion publicacionEntity;
    private UploadedFile documento;
    private Estudiante auxEstudiante;
    private CargarVistaEstudiante cve;
    private CargarVistaCoordinador cvc;
    private String variableFiltrado;
    private String nombrePD;

    
    private boolean renderizarHorasVar;
    private boolean renderizarOtrosVar;    
    
    public boolean isRenderizarHorasVar() {
        return renderizarHorasVar;
    }

    public boolean isRenderizarOtrosVar() {
        return renderizarOtrosVar;
    }

    public void setRenderizarOtrosVar(boolean renderizarOtrosVar) {
        this.renderizarOtrosVar = renderizarOtrosVar;
    }

    public void setRenderizarHorasVar(boolean renderizarHorasVar) {        
        this.renderizarHorasVar = renderizarHorasVar;
    }
 
    public String getNombrePD() {
        return nombrePD;
    }

    public void setNombrePD(String nombrePD) {
        this.nombrePD = nombrePD;
    }

    public String getVariableFiltrado() {
        return variableFiltrado;
    }

    public void setVariableFiltrado(String variableFiltrado) {
        this.variableFiltrado = variableFiltrado;
    }
    
    public PracticaDocenteController() {       
        cve = new CargarVistaEstudiante();
        cvc= new CargarVistaCoordinador();
    }  
    
    public Estudiante getAuxEstudiante() {
        return auxEstudiante;
    }

    public void setAuxEstudiante(Estudiante auxEstudiante) {
        this.auxEstudiante = auxEstudiante;
    }
    
    public UploadedFile getDocumento() {
        return documento;
    }

    public void setDocumento(UploadedFile documento) {
        this.documento = documento;
    }
    
    public PracticaDocente getSelected() {
        return actual;
    }

    public void setSelected(PracticaDocente selected) {
        this.actual = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private PracticaDocenteFacade getFacade() {
        return ejbFacade;
    }

    //<editor-fold defaultstate="collapsed" desc="Codigo generd por jsf">    
    public PracticaDocente prepareCreate() {
        actual = new PracticaDocente();
        initializeEmbeddableKey();
        return actual;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundlePracticaDocente").getString("PracticaDocenteCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundlePracticaDocente").getString("PracticaDocenteUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundlePracticaDocente").getString("PracticaDocenteDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            actual = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<PracticaDocente> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (actual != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(actual);
                } else {
                    getFacade().remove(actual);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundlePracticaDocente").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundlePracticaDocente").getString("PersistenceErrorOccured"));
            }
        }
    }    
    
    public PracticaDocente getPracticaDocente(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<PracticaDocente> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<PracticaDocente> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = PracticaDocente.class)
    public static class PracticaDocenteControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PracticaDocenteController controller = (PracticaDocenteController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "practicaDocenteController");
            return controller.getPracticaDocente(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof PracticaDocente) {
                PracticaDocente o = (PracticaDocente) object;
                return getStringKey(o.getPubIdentificador());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PracticaDocente.class.getName()});
                return null;
            }
        }

    }
    //</editor-fold>
    

    //<editor-fold defaultstate="collapsed" desc="codigo nuevo">
        
    /*redireccion para volver a registrar */
    public void redirigirARegistrarPractica(String nombreUsuario) {
        limpiarCampos(nombreUsuario);
        cve.registrarPractica();
        Utilidades.redireccionar(cve.getRuta());
    }
    
    
    public void limpiarCampos(String nombreUsuario) {        
        this.inicializarVariables();
        Estudiante est = ejbFacade.obtenerEstudiante(nombreUsuario);
        setAuxEstudiante(est);
    }
     
    public void inicializarVariables() {
        actual = new PracticaDocente();
        publicacionEntity = new Publicacion();        
    }
     
    /**
     * metodo que fija el formato para recibir fecha desde la vista
     * @param event 
     */
    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("MM/yyyy");
        facesContext.addMessage("event", new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }
    
    /**
     * agrega un registro de practica docente del estudiante BD 
     * @param user nombre de usuario para buscar el estudiante
     */
    public void AgregarPracticaDocente(String user)
    {
         System.out.println("Registrando practica docente");
         boolean formatoValido = true;
         //validar el formato del docuemtno seleccionado
         if (!documento.getFileName().equalsIgnoreCase("") && !"application/pdf".equals(documento.getContentType())) {            
            FacesContext.getCurrentInstance().addMessage("valPractica", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe subir la evidencia de la práctica docente en formato PDF", ""));
            formatoValido = false;
        }
        if (formatoValido == true) {
             boolean puedeSubir = false;
             if (documento.getFileName().equalsIgnoreCase("")) {                
                FacesContext.getCurrentInstance().addMessage("valPractica", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe subir la evidencia de la práctica docente", ""));
             }
             
             else puedeSubir = true;             
             
             if (puedeSubir) {
                Estudiante est = ejbFacade.obtenerEstudiante(user);
                try{                    
                    publicacionEntity.setPubEstIdentificador(est);
                    String nombreAut = est.getEstNombre() + " " + est.getEstApellido();
                    //gijar el identificador consultando la cantidad de filas
                    int numPubRevis = ejbFacadePub.getnumFilasPubRev();
                    publicacionEntity.setPubIdentificador(numPubRevis);
                    
                    ArrayList<Archivo> CollArchivo = new ArrayList<>();
                    int numArchivos = ejbFacadePub.getIdArchivo();

                    Archivo pd = new Archivo();
                    pd.setArcPubIdentificador(publicacionEntity);
                    pd.setArcIdentificador(numArchivos);
                    pd.setArctipoPDFcargar("practicaDocente");
                    CollArchivo.add(pd);
                    
                    if (!documento.getFileName().equalsIgnoreCase("")) {
                        Archivo archArt = new Archivo();
                        numArchivos = numArchivos + 1;
                        archArt.setArcPubIdentificador(publicacionEntity);
                        archArt.setArcIdentificador(numArchivos);
                        archArt.setArctipoPDFcargar("practicaDocente");
                    }
                    
                    publicacionEntity.setArchivoCollection(CollArchivo);
                    try{
                        publicacionEntity.AgregarPracticaDocente(documento);
                    }catch(IOException e){
                        System.out.println("error agregando documento a practica docente. err: " + e.getMessage());                                
                    }
                    
                    publicacionEntity.setPubEstado("Activo");                    
                    publicacionEntity.setPubVisado("espera");
                    publicacionEntity.setPubTipoPublicacion("Practica docente");
                    publicacionEntity.setIdTipoDocumento(ejbFacade.findTipoDocumento());
                    publicacionEntity.setPubFechaPublicacion(new Date());                    
                    
                    actual.setPubIdentificador(publicacionEntity.getPubIdentificador());
                    
                    publicacionEntity.setLibro(null);
                    publicacionEntity.setCongreso(null);
                    publicacionEntity.setCapituloLibro(null);
                    publicacionEntity.setRevista(null);
                    
                    //almacenar el objeto en la base de datos
                    ejbFacadePub.create(publicacionEntity);                    
                    ejbFacadePub.flush();                    
                    actual.setPublicacion(publicacionEntity);                    
                    
                    //si no se digitaron las horas, se asignan las horas de actividadPd
                    if(actual.getNumeroHoras() == null){
                        actual.setNumeroHoras(actual.getIdActividad().getHorasAsignadas());
                    }
                    //almacenar el contenido del objeto practicaDocente en la base de datos
                    ejbFacade.create(this.actual);
                    
                    mensajeconfirmarRegistro();
                    Date date = new Date();                    
                    DateFormat datehourFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    String estampaTiempo = "" + datehourFormat.format(date);
                    String[] fecha = estampaTiempo.split(" ");
                    //Utilidades.enviarCorreo("posgradoselectunic@gmail.com", "Mensaje sistema doctorados - Registro practica docente", "El estudiante " + nombreAut + " ha regitrado una publicación del tipo " + publicacionEntity.getPubTipoPublicacion() + ". Fecha: " +fecha[0]+ ",  Hora: "+ fecha[1]);
                    //Utilidades.enviarCorreo("posgradoselectunic@gmail.com", "Notificación registro de publicación DCE", "Estimado estudiante " + nombreAut + "\n" + "Se acaba de regitrar una práctica docente" + publicacionEntity.getPubTipoPublicacion() + ". Fecha: " +fecha[0]+ ",  Hora: "+ fecha[1]);
                    Utilidades.correoRegistroPublicaciones(auxEstudiante.getEstCorreo(), nombreAut, 
                                actual.getPublicacion().getIdTipoDocumento().getNombre(), estampaTiempo);
                    redirigirListarPracticasEst();                    
                }catch(EJBException ex)
                {
                    System.out.println("Error: No se pudo registrar la publicacion. error: " + ex.getMessage());
                    redirigirAlistarPublicacionesEst();  
                }
            }                                  
        }         
    }
     
    /**
     * redirigir a listar publicaciones
     */
    public void redirigirAlistarPublicacionesEst() 
    {                
        cve.verPublicaciones();
        Utilidades.redireccionar(cve.getRuta());
    }
    
    /**
     * rediriguir a listar practica docente 
     */
    public void redirigirListarPracticasEst() 
    {        
        cve.verPracticas();
        Utilidades.redireccionar(cve.getRuta());
    }
    
    public void redirigirRegistrarPracticaDocente(String nombreUsuario) {
        limpiarCampos(nombreUsuario);        
        cve.registrarPractica();        
        Utilidades.redireccionar(cve.getRuta());
    }
    
    public void mensajeconfirmarRegistro() {
        System.out.println("Registrada con exito");
    }
      
    public void limpiarcampos()
    {
        actual= new PracticaDocente();
        prepareCreate();
    }

    /**
     * concatena el nombre y el apellido del estudiante
     * @param est estudiante con la informacion nesesaria
     * @return Informacion del estudiante en formato << nombre apellido >>
     */
    public String formatoNombreUsuario(Estudiante est){
        return est.getEstNombre() + " " + est.getEstApellido();
    }
                
    /**
     * retorna una lista que contiene objetos de tipo practicaDocente 
     * @return si variableFiltrado == null retorna todas las praticas, de lo contrario retorna las coincidencias de nombre, fecha inicio fecha fin o lugar
     */
    public List<PracticaDocente> listado(){
        List<PracticaDocente> result = ejbFacade.findAll();

        if ((variableFiltrado == null) || (variableFiltrado.equals(""))) {  return result;  } 
        else
            return result;
//        else {
//            List<PracticaDocente> resultFilter = new ArrayList<>();  
//            //revisar, esta haciendo las consultas tres veces
//            //System.out.println(result.size());
//            for (PracticaDocente item : result) {                                
//                String nombre = item.getPublicacion().getPubEstIdentificador().getEstNombre() + item.getPublicacion().getPubEstIdentificador().getEstApellido();                
//                if(nombre.contains(variableFiltrado) || 
//                        item.getFechaIn().contains(variableFiltrado) || 
//                        item.getFechaTer().contains(variableFiltrado) || 
//                        item.getLugarPractica().contains(variableFiltrado)){
//                    resultFilter.add(item);
//                }                
//            }
//            return resultFilter;           
//        }
    }
     public List<PracticaDocente> listadoDesdeEst(String nombreUsuario){
         //aqui hay que recibir el nombre de usuario como variale
         System.out.println("Nombre de usuario" + nombreUsuario);
         List<PracticaDocente> result = ejbFacade.practicaDocente(nombreUsuario);
         return result; 
        //if ((variableFiltrado == null) || (variableFiltrado.equals(""))) {   } 
      /*  else {
            List<PracticaDocente> resultFilter = new ArrayList<>();  
            //revisar, esta haciendo las consultas tres veces
            //System.out.println(result.size());
            for (PracticaDocente item : result) {                                
                String nombre = item.getPublicacion().getPubEstIdentificador().getEstNombre() + item.getPublicacion().getPubEstIdentificador().getEstApellido();                
                if(nombre.contains(variableFiltrado) || 
                        item.getFechaIn().contains(variableFiltrado) || 
                        item.getFechaTer().contains(variableFiltrado) || 
                        item.getLugarPractica().contains(variableFiltrado)){
                    resultFilter.add(item);
                }                
            }
            return resultFilter;           
        }*/
     // return null;
    }
    
    //</editor-fold>
     
     public void pdfPubPD() throws FileNotFoundException, IOException, IOException, IOException {
        archivoPDF archivoPublic = actual.descargaPubPrac();                   
        if (archivoPublic.getNombreArchivo().equals("")) {
            FacesContext.getCurrentInstance().addMessage("error", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario no ha cargado un PDF de la tabla de contenido", ""));

        } else {
            InputStream fis = archivoPublic.getArchivo();
            String[] nombreArchivo = archivoPublic.getNombreArchivo().split("\\.");
            HttpServletResponse response
                    = (HttpServletResponse) FacesContext.getCurrentInstance()
                            .getExternalContext().getResponse();

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline;filename=" + nombreArchivo[0] + ".pdf");

            byte[] buffer = new byte[8 * 1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                response.getOutputStream().write(buffer, 0, bytesRead);
            }

            response.getOutputStream().write(buffer);
            response.getOutputStream().flush();
            response.getOutputStream().close();
            FacesContext.getCurrentInstance().responseComplete();

            
            
        }
    }
     
      public void verPractica(PracticaDocente prac) {
        actual = prac;
        cvc.listarPracticaDocenteVer();        
        Utilidades.redireccionar(cvc.getRuta());
    }
     
      
      /**
     * se establece si hay que renderizar el input para ingresar las horas 
     * trabajadas en la practica docente y el input de lugar cuando se escoje 
     * "Otrás actividades de apyo al departamento"
     * @param actividades lista de actividades
     * @return TRUE si debe renderizar horas ó lugar de practica, TRUE si debe 
     * renderizar horas y lugar de practica, de lo contrario retorna FALSE.
     */
    public boolean renderizarHoras(List<ActividadPd> actividades){
        ActividadPd aux = new ActividadPd();
        //encontrar la actividad seleccionada
        for (ActividadPd actividad : actividades) {
            if(getSelected().getIdActividad() != null){
                if(getSelected().getIdActividad().equals(actividad)){
                    //sacar una copia de la actividad encontrada                    
                    aux = actividad;
                    break;
                }
            }
        }        
        //verificar que se halla escojido una opcion de la lista
        if(getSelected().getIdActividad() == null){
            renderizarOtrosVar = false;
            renderizarHorasVar = false;
            System.out.println("null actividad");
            return false;
        }
        //si la actividad seleccionada no posee horas y selecciono otras
        if(aux.getHorasAsignadas() == null && 
                getSelected().getIdActividad().getNombreActividad().equals("Otrás actividades de apoyo al departamento")){            
            renderizarHorasVar = true;
            renderizarOtrosVar = true;
            return true;
        }
        //si la actividad seleccionada no posee horas y selecciono un item dierente a otras
        if(aux.getHorasAsignadas() == null && !getSelected().getIdActividad().getNombreActividad().equals("Otrás actividades de apoyo al departamento")){
            renderizarOtrosVar = false;
            renderizarHorasVar = true;
            return true;
        }
        //si no entra en ninguna retorna falase y no renderiza nada
        renderizarHorasVar = false;
        renderizarOtrosVar = false;
        return false;
    }        
    
    /**
     * ver la informacion de una practica docente
     * @param prac practica docente que se desea visualizar
     */
    public void verPracticaDocenteEst(PracticaDocente prac){
        actual = prac;
        cve.verPractica();
        Utilidades.redireccionar(cve.getRuta());
    }
    
    /**
     * redirigir a la informacion del estudiante cuando se le de al boton
     * cancelar o el boton que lo requiera
     */
    public void redirigirInfoEstudiante(){
        cve.verDatosPersonales();
        Utilidades.redireccionar(cve.getRuta());
    }
        
       
    
}
