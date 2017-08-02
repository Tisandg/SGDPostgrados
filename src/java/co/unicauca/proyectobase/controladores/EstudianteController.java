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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
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
    private EstudianteFacade dao;
    private Estudiante actual;
    private String cohorte;
    private String variableFiltrado;
    private List<Estudiante> listadoEncontrado;
    private List<String> Estado;
    private String credi;

    public String getCredi(){
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
        if((cohorte == null) || (cohorte.equals("null")) || (cohorte.equals("")))
        {
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
        this.Estado = new ArrayList<String>();
        this.Estado.add("Activo");
        this.Estado.add("Inactivo");
        this.Estado.add("Egresado");
        cohorte = "";
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
        
        if((variableFiltrado == null) || (variableFiltrado.equals("")))
        {
            listadoEncontrado = dao.findAll();
            return listadoEncontrado;
        }
        else
        {
            listadoEncontrado = dao.findAllByString(variableFiltrado);
            return listadoEncontrado;
        }
    }

    public void agregar() {        

        try
        {
            String contraseña = cifrarBase64(actual.getEstCodigo());
            actual.setEstCohorte(Integer.parseInt(cohorte));
            actual.setEstContrasena(contraseña);
            String[] nombreusuario = actual.getEstCorreo().split("@");
            actual.setEstUsuario(nombreusuario[0]);
            actual.setEstEstado("activo");
            
            Usuario user = new Usuario();
            user.setApellidos(actual.getEstApellido());
            user.setContrasena(actual.getEstContrasena());
            user.setEstado("activo");
            user.setNombreUsuario(actual.getEstUsuario());
            user.setNombres(actual.getEstNombre());
            
            UsuarioController uc = getUsuarioController();
            uc.setCurrent(user);
            uc.create();
            
           TipoUsuario tu = new TipoUsuario(2, "ESTUDIANTE");
            
            GrupoTipoUsuario gtu = new GrupoTipoUsuario();
            gtu.setNombreUsuario(user.getNombreUsuario());
            gtu.setTipoUsuario(tu);
            gtu.setUsuario(user);
            
            GrupoTipoUsuarioPK grpPK = new GrupoTipoUsuarioPK();
            grpPK.setIdTipo(tu.getId());
            grpPK.setIdUsuario(user.getId());
            gtu.setGrupoTipoUsuarioPK(grpPK);
            
            GrupoTipoUsuarioController gtuc = getGrupoTipoUsuarioController();
            gtuc.setCurrent(gtu);
            gtuc.create();
            
            
            dao.create(actual);
            dao.flush();
            mensajeconfirmarRegistro();
            Utilidades.enviarCorreo(""+actual.getEstCorreo(), "Mensaje Sistema Doctorados Electronica Unicauca - Registro de cuenta de estudiante ", "Cordial Saludo "+ "\n" + "El registro en el sistema de Doctorados de Electronica se ha completado correctamente,los detalles de su cuenta son los siguientes: " + "\n" + "Nombre de Usuario: "+actual.getEstUsuario()+ "\n" +"Clave Ingreso: "+actual.getEstCodigo());
            limpiarCampos();
            redirigirAlistar();
        }
        catch(EJBException e)
        {
            
        }
    }

    public void limpiarCampos() {
        actual = new Estudiante();
        cohorte = "";
    }

    
    public void guardarEdicion()
    {
        try
        {
            actual.setEstCohorte(Integer.parseInt(cohorte));
            dao.edit(actual);
            dao.flush();
            
            if(actual.getEstEstado().equalsIgnoreCase("Inactivo"))
            {
                Utilidades.enviarCorreo(""+actual.getEstCorreo(), "Mensaje Sistema Doctorados Electronica Unicauca - Eliminacion de cuenta de estudiante ", "Cordial Saludo "+ "\n" + "La eliminacion de sus Datos en el sistema de Doctorados de Electronica se ha completado correctamente");
            }
            else
            {
                Utilidades.enviarCorreo(""+actual.getEstCorreo(), "Mensaje Sistema Doctorados Electronica Unicauca - Edicion de Datos en cuenta de estudiante ", "Cordial Saludo "+ "\n" + "La edicion de Datos en el sistema de Doctorados de Electronica se ha completado correctamente,los detalles de su cuenta son los siguientes: " + "\n" + "Codigo: "+actual.getEstCodigo()+ "\n" +"Nombres: "+actual.getEstNombre() + "\n" + "Apellidos: "+actual.getEstApellido()+ "\n" +"Correo Institucional: "+actual.getEstCorreo()+ "\n" +"Cohorte: "+actual.getEstCohorte()  + "\n" + "Nombre del Tutor: "+actual.getEstTutor() +   "\n" + "Semestre: "+actual.getEstSemestre()  + "\n" +"Estado: "+actual.getEstEstado());
            }
            
            mensajeEditar();
            redirigirAlistar();          
        }
        catch(EJBException e)
        {
            
        }
    }    
    
    public void cambiarEstado(int id) {
        try
        {
            actual = dao.find(id);
            actual.setEstEstado("Inactivo");
            dao.edit(actual);
            dao.flush();
            mensajeDeshabilitar();
        }
        catch(EJBException e)
        {
            
        }
    }
    
    public void habilitarEstudiante(int id) {
        try
        {
            actual = dao.find(id);
            actual.setEstEstado("Activo");
            dao.edit(actual);
            dao.flush();
            mensajeConfirmacionHabilitacion();
        }
        catch(EJBException e)
        {
            
        }
    }

    public boolean estudianteRegistrado(String codigo) {
        try
        {
            Estudiante estudiante = dao.find(codigo);
            dao.flush();
            
            if (estudiante != null) {
                return true;
            }
        }
        catch(EJBException e)
        {
            
        }
        return false;
    }

    public String cifrarBase64(String a) {
        Base64.Encoder encoder = Base64.getEncoder();
        String b = encoder.encodeToString(a.getBytes(StandardCharsets.UTF_8));
        return b;
    }

    public String descifrarBase64(String a) {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedByteArray = decoder.decode(a);

        String b = new String(decodedByteArray);
        return b;
    }
    //jquery-3.1.1//

    public void verEstudiante(Estudiante est) {
        actual = est;
        Utilidades.redireccionar("/ProyectoII/faces/componentes/gestionUsuarios/VerEstudiante.xhtml");
    }

    public void editarEstudiante(Estudiante est) {
        actual = est;
        cohorte = "" + est.getEstCohorte();
        Utilidades.redireccionar("/ProyectoII/faces/componentes/gestionUsuarios/EditarEstudiante.xhtml");
    }

    /*redireccionamiento para boton cancelar*/
    public void redirigirAlistar() {
        limpiarCampos();
        Utilidades.redireccionar("/ProyectoII/faces/componentes/gestionUsuarios/ListarEstudiantes.xhtml");
    }

    /*redireccion para volver a registrar */
    public void redirigirARegistrar() {
        limpiarCampos();
        Utilidades.redireccionar("/ProyectoII/faces/componentes/gestionUsuarios/RegistrarEstudiante.xhtml");
    }

    /*Redireccion para volver a editar*/
    public void redirigirAEditar() {
        Utilidades.redireccionar("/ProyectoII/faces/componentes/gestionUsuarios/EditarEstudiante.xhtml");
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
    
    public UsuarioController getUsuarioController(){
        FacesContext context = FacesContext.getCurrentInstance();
        ELContext contextoEL = context.getELContext( );
        Application appli = context.getApplication( );
        UsuarioController usuarioController = (UsuarioController) appli.evaluateExpressionGet(context, "#{usuarioController}",UsuarioController.class);
        return usuarioController;
    }
    
        public GrupoTipoUsuarioController getGrupoTipoUsuarioController()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        ELContext contextoEL = context.getELContext( );
        Application appli = context.getApplication( );
        GrupoTipoUsuarioController grupoTipoUsuarioController = (GrupoTipoUsuarioController) appli.evaluateExpressionGet(context, "#{grupoTipoUsuarioController}",GrupoTipoUsuarioController.class);
        return grupoTipoUsuarioController;
    }
        
}