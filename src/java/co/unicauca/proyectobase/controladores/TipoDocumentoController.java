package co.unicauca.proyectobase.controladores;

import co.unicauca.proyectobase.entidades.TipoDocumento;
import co.unicauca.proyectobase.controladores.util.JsfUtil;
import co.unicauca.proyectobase.controladores.util.JsfUtil.PersistAction;
import co.unicauca.proyectobase.dao.TipoDocumentoFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Controlador que define las funciones para el maneja del tipo de documento
 * @author Santiago
 */
@Named("tipoDocumentoController")
@SessionScoped
public class TipoDocumentoController implements Serializable {

    //Utilizada para operaciones sobre la tabla Tipo Documento
    @EJB
    private co.unicauca.proyectobase.dao.TipoDocumentoFacade ejbFacade;
    //lista de los diferentes tipos de documentos
    private List<TipoDocumento> items = null;
    //Tipo de documento que se ha seleccionado
    private TipoDocumento selected;

    /**
     * Constructor
     */
    public TipoDocumentoController() {
    }

    /******* Get and Set *******/
    public TipoDocumento getSelected() {
        return selected;
    }

    public void setSelected(TipoDocumento selected) {
        this.selected = selected;
    }

    private TipoDocumentoFacade getFacade() {
        return ejbFacade;
    }

    /**** Metodos generados por el framework *******/
    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }
    
    public TipoDocumento prepareCreate() {
        selected = new TipoDocumento();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleTipoDocumento").getString("TipoDocumentoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleTipoDocumento").getString("TipoDocumentoUpdated"));
    }

    public void destroy() {        
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleTipoDocumento").getString("TipoDocumentoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
        
    }

    public List<TipoDocumento> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleTipoDocumento").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleTipoDocumento").getString("PersistenceErrorOccured"));
            }
        }
    }


    public TipoDocumento getTipoDocumento(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<TipoDocumento> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<TipoDocumento> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = TipoDocumento.class)
    public static class TipoDocumentoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TipoDocumentoController controller = (TipoDocumentoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tipoDocumentoController");
            return controller.getTipoDocumento(getKey(value));
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
            if (object instanceof TipoDocumento) {
                TipoDocumento o = (TipoDocumento) object;
                return getStringKey(o.getIdentificador());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TipoDocumento.class.getName()});
                return null;
            }
        }

    }

}
