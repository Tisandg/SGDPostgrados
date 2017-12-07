package co.unicauca.proyectobase.controladores;

import co.unicauca.proyectobase.dao.EstudianteFacade;
import co.unicauca.proyectobase.dao.PublicacionFacade;
import co.unicauca.proyectobase.entidades.Estudiante;
import co.unicauca.proyectobase.entidades.GrupoTipoUsuario;
import co.unicauca.proyectobase.entidades.GrupoTipoUsuarioPK;
import co.unicauca.proyectobase.entidades.Publicacion;
import co.unicauca.proyectobase.entidades.TipoUsuario;
import co.unicauca.proyectobase.entidades.Usuario;
import co.unicauca.proyectobase.utilidades.Utilidades;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.el.ELContext;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Clase controlador que permite la gestión de estudiantes.
 * Controlador de las vistas: EditarPublicacion, menu, Estudiante, EditarEstudiante, ListarEstudiante, 
 * RegistrarEstudiante, VerEstudiante, GraficaPubReg, Reportes, VerEstudiante_Est, y EditarConstrasena.
 * @author Carolina
 */

@Named(value = "estudianteController")
@ManagedBean
@SessionScoped
public class EstudianteController implements Serializable {

    @EJB
    private EstudianteFacade estFacade;
    @EJB
    private PublicacionFacade daoPublicacion;

    
    private Estudiante actual;
    private String variableFiltrado;
    private List<Estudiante> listadoEncontrado;
    private List<String> Estado;
    private String credi;
//    private String codigoEdicion;

    private CargarVistaCoordinador cvc;

    public String getCredi() {
        credi = "" + actual.getEstCreditos();
        (new java.text.SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new java.util.Locale("es_ES"))).format(new java.util.Date());
        if (credi.equalsIgnoreCase("null")) {
            credi = "0";
        }
        return credi;
    }

    public void setCredi(String credi) {
        this.credi = credi;
    }

    public int getCohorte() {
        return actual.getEstCohorte();
        /*
        if ((cohorte == null) || (cohorte.equals("null")) || (cohorte.equals(""))) {
            return "";
        }
        cohorte = String.valueOf(actual.getEstCohorte());
        return cohorte;*/
    }

    public List<Estudiante> getListadoEncontrado() {
        return listadoEncontrado;
    }

    public void setListadoEncontrado(List<Estudiante> listadoEncontrado) {
        this.listadoEncontrado = listadoEncontrado;
    }

    public Estudiante getEstudiante(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public EstudianteController() {
        this.Estado = new ArrayList<>();
        this.Estado.add("Activo");
        this.Estado.add("Inactivo");
        this.Estado.add("Egresado");
        this.cvc = new CargarVistaCoordinador();
    }

    public CargarVistaCoordinador getCvc() {
        return cvc;
    }

    public void setCvc(CargarVistaCoordinador cvc) {
        this.cvc = cvc;
    }

    public List<String> getEstado() {
        return Estado;
    }

    public void setEstado(List<String> Estado) {
        this.Estado = Estado;
    }

    public Estudiante getActual() {
        if (actual == null) {
            actual = new Estudiante();
        }
        return actual;
    }
    public String getCodigoEstudianteActual() {
        return actual.getEstCodigo();
    }

    public String getVariableFiltrado() {
        return variableFiltrado;
    }

    public void setVariableFiltrado(String variableFiltrado) {
        this.variableFiltrado = variableFiltrado;
    }

    public List<Estudiante> listado() {

        if ((variableFiltrado == null) || (variableFiltrado.equals(""))) {
            listadoEncontrado = estFacade.findAll();
            return listadoEncontrado;
        } else {
            listadoEncontrado = estFacade.findAllByString(variableFiltrado);
            return listadoEncontrado;
        }
    }

    public List<Estudiante> listadoQuery(String query) {
        query = query.toLowerCase();
        if ((variableFiltrado == null) || (variableFiltrado.equals(""))) {
            listadoEncontrado = estFacade.findAll();

            List<Estudiante> listaFiltrada = new ArrayList();
            for (Estudiante estudiante : listadoEncontrado) {
                if (estudiante.getEstCodigo().toLowerCase().startsWith(query)) {
                    listaFiltrada.add(estudiante);
                }
            }
            return listaFiltrada;
        } else {
            listadoEncontrado = estFacade.findAllByString(variableFiltrado);
            return listadoEncontrado;
        }
    }

    /**
     * Metodo para registrar un estudiante en el sistema. Los datos del objeto 
     * estudiante "actual" seran utilizados para el registro en la base de datos
     */
    public void agregar() {
        
        try {
            String contrasena = Utilidades.sha256(actual.getEstCodigo());
            String[] nombreusuario = actual.getEstCorreo().split("@"); // para un unico dominio
            actual.setEstUsuario(nombreusuario[0]);
            actual.setEstEstado("activo");

            Usuario user = new Usuario(actual.getEstNombre(), actual.getEstApellido(), actual.getEstUsuario(),
                    contrasena, "activo");

            // controlador usuario del contexto actual            
            UsuarioController uc = getUsuarioController();
            uc.setCurrent(user);
            uc.create();

            // definir tipo de usuario para el estudiante 
            TipoUsuario tu = new TipoUsuario(2);

            // definir grupo tipo de usuario para el estudiante 
            GrupoTipoUsuario gtu = new GrupoTipoUsuario();
            gtu.setNombreUsuario(user.getNombreUsuario());
            gtu.setTipoUsuario(tu);
            gtu.setUsuario(user);

            // definir relacion entre grupo tipo de usuario y estudiante 
            GrupoTipoUsuarioPK grpPK = new GrupoTipoUsuarioPK();
            grpPK.setIdTipo(tu.getId());
            grpPK.setIdUsuario(user.getId());
            gtu.setGrupoTipoUsuarioPK(grpPK);
            
            // controlador tipo usuario del contexto actual
            GrupoTipoUsuarioController gtuc = getGrupoTipoUsuarioController();
            gtuc.setCurrent(gtu);
            gtuc.create();

            //agregar id de usuario a estudiante    
            actual.setUsuarioId(user);
            actual.setEstCreditos(0);
            getFacade().create(actual);
            getFacade().flush();
            mensajeconfirmarRegistro();
            
            Utilidades.correoRegistroEstudiante(actual);
            limpiarCampos();
            redirigirAlistar();
        } catch (EJBException e) {
            System.out.println("Error -- EstudianteController -- mensaje: "+ e.getMessage());
        }
    }

    /**
     * Metodo para limpiar los campos del formulario de registro de un estudiante
     * Para ello se inicializa de nuevo el objeto estudiante
     */
    public void limpiarCampos() {
        actual = new Estudiante();
    }

    /**
     * Metodo para guardar los cambios realizados en la informacion del
     * perfil de un estudiante.
     */
    public void guardarEdicion() {
        try {
            //actual.setEstCohorte(Integer.parseInt(cohorte));
            estFacade.edit(actual);
            estFacade.flush();

            if(actual.getEstEstado().equalsIgnoreCase("Inactivo"))
            {
                Utilidades.enviarCorreo(""+actual.getEstCorreo(), "Sistema de doctorado en ciencias de la electronica - "
                        + "Eliminacion de cuenta de estudiante ", "Cordial Saludo. "+ "\n" + "La eliminacion de sus Datos "
                        + "en el sistema de doctorado en ciencias de la electrónica se ha completado correctamente");
            }
            else
            {
                Utilidades.correoEdicionPerfilEstudiante(actual);
            }

            mensajeEditar();
            redirigirAlistar();
        } catch (EJBException e) {

        }
    }

    public void cambiarEstado(int id) {
        try {
            actual = estFacade.find(id);
            actual.setEstEstado("Inactivo");
            estFacade.edit(actual);
            estFacade.flush();
            mensajeDeshabilitar();
        } catch (EJBException e) {

        }
    }

    public void habilitarEstudiante(int id) {
        try {
            actual = estFacade.find(id);
            actual.setEstEstado("Activo");
            estFacade.edit(actual);
            estFacade.flush();
            mensajeConfirmacionHabilitacion();
        } catch (EJBException e) {

        }
    }

    public boolean estudianteRegistrado(String codigo) {
        try {
            Estudiante estudiante = estFacade.find(codigo);
            estFacade.flush();

            if (estudiante != null) {
                return true;
            }
        } catch (EJBException e) {

        }
        return false;
    }

    //jquery-3.1.1//

    public void verEstudiante(Estudiante est) {
        actual = est;
        cvc.verEstudiante();
        Utilidades.redireccionar(cvc.getRuta());
    }

    public void editarEstudiante(Estudiante est) {
        actual = est;
        //cohorte = "" + est.getEstCohorte();
        cvc.editarEstudiante();
        Utilidades.redireccionar(cvc.getRuta());
    }

    /**
     * retorna true si existe un estudiante, con {@code codigo}, registrado en
     * el sistema
     *
     * @param codigo
     * @return
     */
    public boolean existByCodigoEst(String codigo) {
        return estFacade.existByEstCodigo(codigo);
    }

    /**
     *
     * @param estCodigo
     * @param id
     * @return
     */
    public Estudiante buscarPorCodigoExceptoConId(String estCodigo, Integer id) {
        return estFacade.buscarPorCodigoExceptoConId(estCodigo, id);

    }
    /**
     *
     * @param estCodigo
     * @param id
     * @return
     */
    public Estudiante buscarPorCorreoExceptoConId(String estCodigo, Integer id) {
        return estFacade.buscarPorCorreoExceptoConId(estCodigo, id);

    }

    /**
     * Procedimiento para redirigir al listado los estudiante registrados en el
     * sistema.
     */
    public void redirigirAlistar() {
        limpiarCampos();
        cvc.listarEstudiantes();
        Utilidades.redireccionar(cvc.getRuta());
    }

    /*redireccion para volver a registrar */
    public void redirigirARegistrar() {
        limpiarCampos();
        cvc.registrarEstudiante();
        Utilidades.redireccionar(cvc.getRuta());
    }

    /*Redireccion para volver a editar*/
    public void redirigirAEditar() {
        cvc.editarEstudiante();
        Utilidades.redireccionar(cvc.getRuta());
    }

    /*mensajes de confirmacion */
    public void mensajeEditar() {
        addMessage("Ha editado satisfactoriamente al estudiante.", "");
    }

    public void mensajeDeshabilitar() {

        addMessage("Ha deshabilitado satisfactoriamente al estudiante.", "");
    }

    public void mensajelistadoEstudiantes() {
        addMessage("Usted abandonó el registro y este es el listado de estudiantes.", "");
    }

    public void mensajeconfirmarRegistro() {
        addMessage("Estudiante registrado con éxito.", "");
    }

    public void mensajeConfirmacionHabilitacion() {
        addMessage("Ha habilitado satisfactoriamente al estudiante.", "");
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Funcion para obtener el controlador "UsuarioController" del contexto
     * de la aplicacion
     * @return UsuarioController
     */
    public UsuarioController getUsuarioController() {
        FacesContext context = FacesContext.getCurrentInstance();
        ELContext contextoEL = context.getELContext();
        Application appli = context.getApplication();
        UsuarioController usuarioController = (UsuarioController) appli.evaluateExpressionGet(context, "#{usuarioController}", UsuarioController.class);
        return usuarioController;
    }

    /**
     * Funcion para obtener el controlador "GrupoTipoUsuarioController" del 
     * contexto de la aplicacion
     * @return GrupoTipoUsuarioController
     */
    public GrupoTipoUsuarioController getGrupoTipoUsuarioController() {
        FacesContext context = FacesContext.getCurrentInstance();
        ELContext contextoEL = context.getELContext();
        Application appli = context.getApplication();
        GrupoTipoUsuarioController grupoTipoUsuarioController = (GrupoTipoUsuarioController) appli.evaluateExpressionGet(context, "#{grupoTipoUsuarioController}", GrupoTipoUsuarioController.class);
        return grupoTipoUsuarioController;
    }

    /**
     * Métodos Privados*/
    
    private EstudianteFacade getFacade()
    {
        return this.estFacade;
    }

    /**
     * Metodo que me retorna el listado de las publicaciones que ha registrado
     * el estudiante.
     * @param codigo
     * @return 
     */
    public List<Publicacion> PublicacionPorEstudiante(String codigo)
    {

        Estudiante estudiante = estFacade.buscarPorCodigo(codigo);
        int idEstudianteConsulta = estudiante.getEstIdentificador();
        List<Publicacion> pub= daoPublicacion.ListadoPublicacionEst(idEstudianteConsulta);
        for (int i = 0; i < pub.size(); i++) {
            System.out.println("Publicacion" + pub.get(i).obtenerNombrePub());
        }

        return pub;

    }

    @FacesConverter(forClass = Estudiante.class)
    public class EstudianteControllerConverter implements Converter {

        @Override
        public Estudiante getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EstudianteController controller = (EstudianteController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "estudianteController");
            return controller.getEstudiante(getKey(value));
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
            if (object instanceof Estudiante) {
                Estudiante o = (Estudiante) object;
                return getStringKey(Integer.parseInt(o.getEstCodigo()));
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Estudiante.class.getName()});
                return null;
            }
        }

    }

}
