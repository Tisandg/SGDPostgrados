package co.unicauca.proyectobase.controladores;

import co.unicauca.proyectobase.entidades.Usuario;
import co.unicauca.proyectobase.controladores.util.JsfUtil;
import co.unicauca.proyectobase.controladores.util.PaginationHelper;
import co.unicauca.proyectobase.dao.Contrasena;
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
 

@Named("usuarioController")
@ManagedBean
@SessionScoped
public class UsuarioController implements Serializable {

    private Usuario current;
    private Contrasena contrasenas;
    private boolean editarContrasena;
    
    private DataModel items = null;
    @EJB
    private UsuarioFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public UsuarioController() {
        this.editarContrasena = false;
        this.contrasenas = new Contrasena();
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

    public void editarContrasena(){
        
        if(this.editarContrasena = false){
            this.editarContrasena = true;
        }else{
            this.editarContrasena = false;
        }
        System.out.println("Cambiando editar contrasena: "+this.editarContrasena);
    }
    
    /**
     * funcion para cambiar la contraseña de un estudiante. Recibe la referencia
     * del estudiante al que se le va a modificar la contraseña
     * @param actual
     * @return 
     */
    public boolean cambiarContrasena(Usuario actual){
        
        this.current = actual;
        System.out.println("Cambiando contraseña...");
        boolean respuesta = false;
        /*Comprobar que la contraseña actual digitada coincida con la que 
          esta guardada*/
        System.out.println("Contraseña actual ingresada: "+this.contrasenas.getContrasenaActual());
        String contrasenaActual = Utilidades.sha256(this.contrasenas.getContrasenaActual());
        System.out.println("Contraseña actual guardada "+ current.getContrasena());
        if(contrasenaActual.equals(current.getContrasena())){
            /*Contraseñas coinciden*/
            System.out.println("Contraseñas actuales son iguales");
            String nuevaContrasena = Utilidades.sha256(this.contrasenas.getNuevaContrasena());
            try{
                this.current.setContrasena(nuevaContrasena);
                ejbFacade.edit(current);
                ejbFacade.flush();
                respuesta = true;
                System.out.println("Contrasena modificada");
                
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Informacion", "Su contraseña ha sido cambiada satisfactoriamente"));
            }catch(EJBException e){
                FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Informacion", "No se ha podido modificar la contraseña. Consulte al administrador"));
            }
            
        }else{
            System.out.println("Contrasena actuales no coinciden");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Informacion", "La contraseña actual digitada no coincide con la registrada"));
        }
        
        return respuesta;
    }
    
    public Usuario getSelected() {
        if (current == null) {
            current = new Usuario();
            selectedItemIndex = -1;
        }
        return current;
    }

    private UsuarioFacade getFacade() {
        return ejbFacade;
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

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Usuario) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Usuario();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            //JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/BundleUsuarios").getString("UsuarioCreated"));
            return prepareCreate();
        } catch (Exception e) {
            //JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/BundleUsuarios").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Usuario) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

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
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Usuario getUsuario(java.lang.Integer id) {
        return ejbFacade.find(id);
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

}
