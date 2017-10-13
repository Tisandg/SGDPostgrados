package co.unicauca.proyectobase.controladores;

import co.unicauca.proyectobase.entidades.GrupoTipoUsuario;
import co.unicauca.proyectobase.controladores.util.JsfUtil;
import co.unicauca.proyectobase.controladores.util.PaginationHelper;
import co.unicauca.proyectobase.dao.GrupoTipoUsuarioFacade;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@Named("grupoTipoUsuarioController")
@SessionScoped
public class GrupoTipoUsuarioController implements Serializable {

    private GrupoTipoUsuario current;
    private DataModel items = null;
    @EJB
    private co.unicauca.proyectobase.dao.GrupoTipoUsuarioFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public GrupoTipoUsuarioController() {
    }

    public void setCurrent(GrupoTipoUsuario current) {
        this.current = current;
    }
    
    public GrupoTipoUsuario getSelected() {
        if (current == null) {
            current = new GrupoTipoUsuario();
            current.setGrupoTipoUsuarioPK(new co.unicauca.proyectobase.entidades.GrupoTipoUsuarioPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private GrupoTipoUsuarioFacade getFacade() {
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
        current = (GrupoTipoUsuario) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new GrupoTipoUsuario();
        current.setGrupoTipoUsuarioPK(new co.unicauca.proyectobase.entidades.GrupoTipoUsuarioPK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.getGrupoTipoUsuarioPK().setIdTipo(current.getTipoUsuario().getId());
            current.getGrupoTipoUsuarioPK().setIdUsuario(current.getUsuario().getId());
            getFacade().create(current);
            //JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/BundleUsuarios").getString("GrupoTipoUsuarioCreated"));
            return prepareCreate();
        } catch (Exception e) {
            //JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/BundleUsuarios").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (GrupoTipoUsuario) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            current.getGrupoTipoUsuarioPK().setIdTipo(current.getTipoUsuario().getId());
            current.getGrupoTipoUsuarioPK().setIdUsuario(current.getUsuario().getId());
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/BundleUsuarios").getString("GrupoTipoUsuarioUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/BundleUsuarios").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (GrupoTipoUsuario) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/BundleUsuarios").getString("GrupoTipoUsuarioDeleted"));
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

    public GrupoTipoUsuario getGrupoTipoUsuario(co.unicauca.proyectobase.entidades.GrupoTipoUsuarioPK id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = GrupoTipoUsuario.class)
    public static class GrupoTipoUsuarioControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            GrupoTipoUsuarioController controller = (GrupoTipoUsuarioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "grupoTipoUsuarioController");
            return controller.getGrupoTipoUsuario(getKey(value));
        }

        co.unicauca.proyectobase.entidades.GrupoTipoUsuarioPK getKey(String value) {
            co.unicauca.proyectobase.entidades.GrupoTipoUsuarioPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new co.unicauca.proyectobase.entidades.GrupoTipoUsuarioPK();
            key.setId(Integer.parseInt(values[0]));
            key.setIdTipo(Integer.parseInt(values[1]));
            key.setIdUsuario(Integer.parseInt(values[2]));
            return key;
        }

        String getStringKey(co.unicauca.proyectobase.entidades.GrupoTipoUsuarioPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getId());
            sb.append(SEPARATOR);
            sb.append(value.getIdTipo());
            sb.append(SEPARATOR);
            sb.append(value.getIdUsuario());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof GrupoTipoUsuario) {
                GrupoTipoUsuario o = (GrupoTipoUsuario) object;
                return getStringKey(o.getGrupoTipoUsuarioPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + GrupoTipoUsuario.class.getName());
            }
        }

    }

}