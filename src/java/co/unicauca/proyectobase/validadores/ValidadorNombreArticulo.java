package co.unicauca.proyectobase.validadores;

import co.unicauca.proyectobase.controladores.PublicacionController;
import co.unicauca.proyectobase.dao.PublicacionFacade;
import co.unicauca.proyectobase.entidades.Publicacion;
import co.unicauca.proyectobase.entidades.Revista;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * @author Juan
 */
@FacesValidator(value="validadorNombreArticulo")
public class ValidadorNombreArticulo implements Validator {
    
    @EJB
    private PublicacionFacade dao;
    private Publicacion actual;
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        
        String nombre = String.valueOf(value);
        nombre= nombre.trim();
        /*Validando que el campo no este vacio*/
        if(nombre.length() == 0) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El título del artículo es obligatorio.");
            throw new ValidatorException(msg);
        }

        /*Validando el tamaño del campo*/
        if(nombre.length() < 10 || nombre.length()>200) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El título del artículo debe contener entre 10 y 200 caracteres.");
            throw new ValidatorException(msg);
        }
        
        /*Validando que solo halla espacios entre palabras*/
        if(nombre.length()!=0)
        {
            if(nombre.charAt(0) == ' ' || nombre.charAt(nombre.length()-1) == ' '){
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Solo se permiten espacios entre las palabras.");
                throw new ValidatorException(msg);
            }
                
        }
        
        if(isRegistradoTituloArticulo(nombre, context)){
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El título del articulo ya se encuentrado registrado.");
            throw new ValidatorException(msg);
        }
            
    }
    
    /**
     * Funcion que determina si el nombre del articulo ya ha sido registrado.
     * @param tituloArticulo
     * @param context
     * @return 
     */
     public boolean isRegistradoTituloArticulo(String tituloArticulo, FacesContext context){
        
        boolean variable = false;
        PublicacionController controller = (PublicacionController) context.getApplication().getELResolver().
                    getValue(context.getELContext(), null, "publicacionController");
        Revista rev = controller.buscarTituloArticulo(tituloArticulo);
        if (rev != null && !rev.getPubIdentificador().equals(controller.getActual().getPubIdentificador())) {
            variable = true;
        }
        return variable;
    }
    
}

