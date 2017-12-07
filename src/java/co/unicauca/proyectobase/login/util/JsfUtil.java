package co.unicauca.proyectobase.login.util;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;

public class JsfUtil {

    /**
     * Método que permite obtener los items seleccionados.
     * @param entities: Lista de entidades.
     * @param selectOne: indicador de si esta seleccionado un elemento
     * @return items seleccionados.
     */
    public static SelectItem[] getSelectItems(List<?> entities, boolean selectOne) {
        int size = selectOne ? entities.size() + 1 : entities.size();
        SelectItem[] items = new SelectItem[size];
        int i = 0;
        if (selectOne) {
            items[0] = new SelectItem("", "---");
            i++;
        }
        for (Object x : entities) {
            items[i++] = new SelectItem(x, x.toString());
        }
        return items;
    }
/**
     * Método que permite obtener el resultado de la validación.
     * @return true si fallo y false en caso contrario.
     */
    public static boolean isValidationFailed() {
        return FacesContext.getCurrentInstance().isValidationFailed();
    }

    /**
     * Método que permite agregar un mensaje de error.
     * @param ex: excepción.
     * @param defaultMsg: mensaje perteneciente a la excepción.
     */
    public static void addErrorMessage(Exception ex, String defaultMsg) {
        String msg = ex.getLocalizedMessage();
        if (msg != null && msg.length() > 0) {
            addErrorMessage(msg);
        } else {
            addErrorMessage(defaultMsg);
        }
    }

    /**
     * Método que permite agregar un mensaje de error a una lista de mensajes de error.
     * @param messages: mensajes de error.
     */
    public static void addErrorMessages(List<String> messages) {
        for (String message : messages) {
            addErrorMessage(message);
        }
    }

     /**
     * Método que permite agregar un mensaje de error.
     * @param msg: mensaje de error.
     */
    public static void addErrorMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

   /**
     * Método que permite agregar un mensaje de éxito.
     * @param msg: mensaje de éxito.
     */
    public static void addSuccessMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }

    /**
     * Método que permite obtener un parámetro de solicitud.
     * @param key: clave.
     * @return cadena con los parametros.
     */
    public static String getRequestParameter(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
    }

    /**
     * Método que permite obtener un objeto del parámetro de solicitud
     * @param requestParameterName: nombre del parámetro de solicitud
     * @param converter: objeto de tipo converter
     * @param component: componente GUI
     * @return objeto del parámetro de solicitud
     */
    public static Object getObjectFromRequestParameter(String requestParameterName, Converter converter, UIComponent component) {
        String theId = JsfUtil.getRequestParameter(requestParameterName);
        return converter.getAsObject(FacesContext.getCurrentInstance(), component, theId);
    }

    /**
     * Ennumeración con las acciones de persistencia.
     */
    public static enum PersistAction {
        CREATE,
        DELETE,
        UPDATE
    }
}
