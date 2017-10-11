package co.unicauca.proyectobase.controladores;

import co.unicauca.proyectobase.dao.EstudianteFacade;
import co.unicauca.proyectobase.entidades.Estudiante;
import co.unicauca.proyectobase.entidades.GrupoTipoUsuario;
import co.unicauca.proyectobase.entidades.GrupoTipoUsuarioPK;
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
import javax.ejb.EJBException;
import javax.el.ELContext;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Named(value = "estudianteController")
@ManagedBean
@SessionScoped
public class EstudianteController implements Serializable {

    @EJB
    private EstudianteFacade ejbFacade;
    private Estudiante actual;
    private String cohorte;
    private String variableFiltrado;
    private List<Estudiante> listadoEncontrado;
    private List<String> Estado;
    private String credi;
//    private String codigoEdicion;

    private CargarVistaCoordinador cvc;

    public String getCredi() {
        credi = "" + actual.getEstCreditos();

        if (credi.equalsIgnoreCase("null")) {
            credi = "0";
        }
        return credi;
    }

    public void setCredi(String credi) {
        this.credi = credi;
    }

    public String getCohorte() {
        if ((cohorte == null) || (cohorte.equals("null")) || (cohorte.equals(""))) {
            return "";
        }
        cohorte = String.valueOf(actual.getEstCohorte());
        return cohorte;
    }

    public void setCohorte(String cohorte) {
        this.cohorte = cohorte;
    }

    public List<Estudiante> getListadoEncontrado() {
        return listadoEncontrado;
    }

    public void setListadoEncontrado(List<Estudiante> listadoEncontrado) {
        this.listadoEncontrado = listadoEncontrado;
    }

    public EstudianteController() {
        this.Estado = new ArrayList<>();
        this.Estado.add("Activo");
        this.Estado.add("Inactivo");
        this.Estado.add("Egresado");
        cohorte = "";
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
            cohorte = "";
        }
        return actual;
    }

    public String getVariableFiltrado() {
        return variableFiltrado;
    }

    public void setVariableFiltrado(String variableFiltrado) {
        this.variableFiltrado = variableFiltrado;
    }

    public List<Estudiante> listado() {

        if ((variableFiltrado == null) || (variableFiltrado.equals(""))) {
            listadoEncontrado = ejbFacade.findAll();
            return listadoEncontrado;
        } else {
            listadoEncontrado = ejbFacade.findAllByString(variableFiltrado);
            return listadoEncontrado;
        }
    }

    public void agregar() 
    {
        try {
            System.out.println("est:"+actual);
            String contrasena = Utilidades.sha256(actual.getEstCodigo());
            String[] nombreusuario = actual.getEstCorreo().split("@"); // para un unico dominio
            actual.setEstUsuario(nombreusuario[0]);
            actual.setEstEstado("activo");
            actual.setEstContrasena("contrasena");

            // configuracion de estudiante como usuario del sistema
            Usuario user = new Usuario(actual.getEstNombre(), actual.getEstApellido(), actual.getEstUsuario(), actual.getEstContrasena());
            //user.setApellidos(actual.getEstApellido());
            //user.setContrasena(actual.getEstContrasena());
            user.setEstado("activo");
            //user.setNombreUsuario(actual.getEstUsuario());
            //user.setNombres(actual.getEstNombre());
            //user.setContrasena(contrasena);
            
            // controlador usuario del contexto actual            
            UsuarioController uc = getUsuarioController();
            uc.setCurrent(user);
            uc.create();
                               

            // definir tipo de usuario para el estudiante 
            TipoUsuario tu = new TipoUsuario(2, "ESTUDIANTE");
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
            System.out.println("id de ususario: "+ uc.getCurrent().toString());     
            actual.setUsuarioId(user);
                        
            getFacade().create(actual);
            getFacade().flush();
            mensajeconfirmarRegistro();
            Utilidades.enviarCorreo("" + actual.getEstCorreo(), "Registro en Doctorados de Ciencias de la Elecrónica ", "Cordial Saludo " + "\n" + "El registro en el sistema de Doctorados de Ciencias de la Electrónica fue exitoso,para ingresar sírvase usar los siguientes datos: " + "\n" + "Nombre de Usuario: " + actual.getEstUsuario() + "\n" + "Clave Ingreso: " + actual.getEstCodigo());            
            
            limpiarCampos();
            redirigirAlistar();
        } catch (EJBException e) {
            System.out.println("Error -- EstudianteController -- mensaje: "+ e.getMessage());
        }
    }

    public void limpiarCampos() {
        actual = new Estudiante();
        cohorte = "";
    }

    public void guardarEdicion() {
        try {
            actual.setEstCohorte(Integer.parseInt(cohorte));
            ejbFacade.edit(actual);
            ejbFacade.flush();

            String usuario= actual.getEstCorreo();
            String delimitador="@";
            String[] username= usuario.split(delimitador);
            
            if(actual.getEstEstado().equalsIgnoreCase("Inactivo"))
            {
                Utilidades.enviarCorreo(""+actual.getEstCorreo(), "Sistema de doctorado en ciencias de la electronica - Eliminacion de cuenta de estudiante ", "Cordial Saludo. "+ "\n" + "La eliminacion de sus Datos en el sistema de doctorado en ciencias de la electrónica se ha completado correctamente");
            }
            else
            {
                Utilidades.enviarCorreo(""+actual.getEstCorreo(), "Sistema de doctorado en ciencias de la electronica - Edición de Datos en cuenta de estudiante ", "Cordial Saludo. "+ "\n" + "\n" +"La edición de datos en el sistema de doctorado en ciencias de la electrónica se ha completado correctamente."+"\n"+"Los detalles de su cuenta son los siguientes: " +"\n" + "\n" +"Datos: " +"\n" + "Codigo: "+actual.getEstCodigo()+ "\n" +"Nombres: "+actual.getEstNombre() + "\n" + "Apellidos: "+actual.getEstApellido()+ "\n" +"Correo Institucional: "+actual.getEstCorreo()+ "\n" +"Cohorte: "+actual.getEstCohorte()  + "\n" + "Nombre del Tutor: "+actual.getEstTutor() +"\n" + "Semestre: "+actual.getEstSemestre()  + "\n" +"Estado: "+actual.getEstEstado() +"\n"+ "\n" +"Datos para iniciar sesión: " +"\n"+ "Usuario: " + username[0]+ "\n" +"Contrasenia: "+ actual.getEstCodigo());

            }   

            mensajeEditar();
            redirigirAlistar();
        } catch (EJBException e) {

        }
    }

    public void cambiarEstado(int id) {
        try {
            actual = ejbFacade.find(id);
            actual.setEstEstado("Inactivo");
            ejbFacade.edit(actual);
            ejbFacade.flush();
            mensajeDeshabilitar();
        } catch (EJBException e) {

        }
    }

    public void habilitarEstudiante(int id) {
        try {
            actual = ejbFacade.find(id);
            actual.setEstEstado("Activo");
            ejbFacade.edit(actual);
            ejbFacade.flush();
            mensajeConfirmacionHabilitacion();
        } catch (EJBException e) {

        }
    }

    public boolean estudianteRegistrado(String codigo) {
        try {
            Estudiante estudiante = ejbFacade.find(codigo);
            ejbFacade.flush();

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
        cohorte = "" + est.getEstCohorte();
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
        return ejbFacade.existByEstCodigo(codigo);
    }

    /**
     *
     * @param estCodigo
     * @param id
     * @return
     */
    public Estudiante buscarPorCodigoExceptoConId(String estCodigo, Integer id) {
        return ejbFacade.buscarPorCodigoExceptoConId(estCodigo, id);

    }
    /**
     *
     * @param estCodigo
     * @param id
     * @return
     */
    public Estudiante buscarPorCorreoExceptoConId(String estCodigo, Integer id) {
        return ejbFacade.buscarPorCorreoExceptoConId(estCodigo, id);

    }

    /*redireccionamiento para boton cancelar*/
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

    public UsuarioController getUsuarioController() {
        FacesContext context = FacesContext.getCurrentInstance();
        ELContext contextoEL = context.getELContext();
        Application appli = context.getApplication();
        UsuarioController usuarioController = (UsuarioController) appli.evaluateExpressionGet(context, "#{usuarioController}", UsuarioController.class);
        return usuarioController;
    }

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
        return this.ejbFacade;
    }
}
