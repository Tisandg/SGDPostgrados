package co.unicauca.proyectobase.controladores;

import co.unicauca.proyectobase.entidades.Usuario;
import co.unicauca.proyectobase.controladores.util.JsfUtil;
import co.unicauca.proyectobase.controladores.util.PaginationHelper;
import co.unicauca.proyectobase.entidades.Contrasena;
import co.unicauca.proyectobase.dao.UsuarioFacade;
import co.unicauca.proyectobase.utilidades.Utilidades;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
 
/**
 * Clase utilizado que define las funciones para las operaciones
 * sobre el usuario
 * @author Santiago
 */
@Named("usuarioController")
@ManagedBean
@SessionScoped
public class UsuarioController implements Serializable {

    //Usuario que esta actualmente en el sistema
    private Usuario current;
    //Para el almacenamiento de las contrasena
    private Contrasena contrasenas;
    //indica si se ha editado o no la contrasena
    private boolean editarContrasena;
    
    //Para realiza operaciones sobre la tabla usuario
    @EJB
    private UsuarioFacade userFacade;
    
    //Atributos utilizados por los metodos creados por el framework
    private DataModel items = null;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    /**
     * Constructor
     */
    public UsuarioController() {
        this.editarContrasena = false;
        this.contrasenas = new Contrasena();
    }
    
    /**** Gets and Sets *****/
    public boolean isEditarContrasena() {
        return editarContrasena;
    }

    public void setEditarContrasena(boolean editarContrasena) {
        this.editarContrasena = editarContrasena;
    }
    
    public Contrasena getContrasenas() {
        return contrasenas;
    }

    public void setContrasenas(Contrasena contrasenas) {
        this.contrasenas = contrasenas;
    }
    
    public Usuario getUsuario(java.lang.Integer id) {
        return userFacade.find(id);
    }
    
    private UsuarioFacade getFacade() {
        return userFacade;
    }
    
    public Usuario getCurrent() {
        if(current == null){
            current= new Usuario();
        }
        return current;
    }

    public void setCurrent(Usuario current) {
        this.current = current;
    }
    
    /** Metodos principales **/
    
    /***
     * Metodo para validar los campos de la vista editar contraseña.
     * Se verifican que las nuevas contraseñas coincidan y que la contraseña
     * actual coincida con la registrada. Si los datos son validos se 
     * establecera la variable editarContrasena como true para permitir 
     * el cambio de la contrasena.
     * @param actual 
     */
    public void validarCampos(Usuario actual){
        this.editarContrasena = true;
        this.current = actual;
        FacesContext context = FacesContext.getCurrentInstance();
        if(!this.contrasenas.getNuevaContrasena().equals(this.contrasenas.getNuevaContrasenaR())){
            this.editarContrasena = false;
            System.out.println("Contrasenas nuevas no coinciden");
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Contraseñas nuevas no coinciden.","Verifique los valores ingresados"));
            Utilidades.redireccionar("/ProyectoII/faces/usuariosdelsistema/estudiante/perfil/EditarContrasena.xhtml");
        }else{
            String contrasenaActual = Utilidades.sha256(this.contrasenas.getContrasenaActual());
             if(!contrasenaActual.equals(current.getContrasena())){
                 this.editarContrasena = false;
                System.out.println("Contrasena actuales no coinciden");
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Contraseña actual no coincide con la registrada.","Verifique la contraseña"));
                Utilidades.redireccionar("/ProyectoII/faces/usuariosdelsistema/estudiante/perfil/EditarContrasena.xhtml");
             }
        }
        //return respuesta;
    }
    
    /**
     * funcion para cambiar la contraseña de un estudiante. Si los campos son 
     * validos se procede a cambiar la contraseña por la nueva digitada por 
     * el estudiante.
     * @param actual
     * @return 
     */
    
    /* método para el cambio de la contraseña actual de un usuario que ha
    iniciado sesion, en caso de no ser posible el cambio, se alerta al usuario
    con un mensaje de pantalla
    */
    public boolean cambiarContrasena(Usuario actual){
        
        this.current = actual;
        boolean respuesta = false;
        FacesContext context = FacesContext.getCurrentInstance();
        if(this.editarContrasena){
            System.out.println("Cambiando contraseña...");
            String nuevaContrasena = Utilidades.sha256(this.contrasenas.getNuevaContrasena());
            try{
                this.current.setContrasena(nuevaContrasena);
                userFacade.edit(current);
                userFacade.flush();
                respuesta = true;
                System.out.println("Contrasena modificada");
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Su contraseña ha sido cambiada satisfactoriamente",""));
                Utilidades.redireccionar("/ProyectoII/faces/usuariosdelsistema/estudiante/perfil/EditarContrasena.xhtml");
            }catch(EJBException e){
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se ha podido modificar la contraseña. Consulte al administrador","detail"));
                Utilidades.redireccionar("/ProyectoII/faces/usuariosdelsistema/estudiante/perfil/EditarContrasena.xhtml");
            }
        }
        return respuesta;
    }
    
    /* esta función devuelve el objeto de tipo usuario que se haya seleccionado
    en una lista de usuarios
    */
    public Usuario getSelected() {
        if (current == null) {
            current = new Usuario();
            selectedItemIndex = -1;
        }
        return current;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }
    /* funcion que crea un objeto de lista de usuarios para ser usados en vista
    
    */
    public String prepareList() {
        recreateModel();
        return "List";
    }
/* funcion que crea un objeto de tipo "view" que se usa para preparar la vista
    que se va a mostrar
    
    */
    public String prepareView() {
        current = (Usuario) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    /**
     * Metodo que inicializa un nuevo objeto usuario listo para ser utilizado
     * en la creacion de un usuario
     * @return 
     */
    public String prepareCreate() {
        current = new Usuario();
        selectedItemIndex = -1;
        return "Create";
    }

    /**
     * Funcino que crea un registro en la tabla usuario con los datos guardados
     * en el objeto current
     * @return "Create" si se creo el registro, de lo contrario null
     */
    public String create() {
        try {
            getFacade().create(current);
            return prepareCreate();
        } catch (Exception e) {
            return null;
        }
    }
    /* funcion que crea un objeto de usuario, cos sus items por separado, para
    ser usados en vista de edicion    
    */
    public String prepareEdit() {
        current = (Usuario) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

        /* funcion que crea un objeto de vista que actualiza la lista de usuarios
    y se muestran en la pantalla
    */
    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/BundleUsuarios").getString("UsuarioUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/BundleUsuarios").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Usuario) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/BundleUsuarios").getString("UsuarioDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/BundleUsuarios").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(userFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(userFacade.findAll(), true);
    }

    

    @FacesConverter(forClass = Usuario.class)
    public static class UsuarioControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UsuarioController controller = (UsuarioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "usuarioController");
            return controller.getUsuario(getKey(value));
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
            if (object instanceof Usuario) {
                Usuario o = (Usuario) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Usuario.class.getName());
            }
        }

    }
}
