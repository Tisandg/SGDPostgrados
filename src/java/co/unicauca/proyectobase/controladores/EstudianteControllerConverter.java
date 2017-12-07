package co.unicauca.proyectobase.controladores;

/**
 * Clase Converter que permite asegurar que el tipo de un dato introducido en un
 * formulario JSF sea el correcto, es decir, que el dato tipo cadena del
 * formulario corresponde con el tipo JAVA esperado, y que está especificado en
 * la propiedad correspondiente del bean. Los Conversores (implementaciones de
 * la interfaz Converter) son los componentes que se encargan de hacer estas
 * transformaciones (cadena>Tipo JAVA y viceversa). JSF invoca a los Conversores
 * antes de efectuar las validaciones y por lo tanto antes de aplicar los
 * valores introducidos a las propiedades del bean. En el caso de que un dato
 * tipo cadena no se corresponda con el tipo JAVA apropiado, el Conversor
 * correspondiente lanzará un ConversionException y el componente se marcará
 * como invalidado.
 *
 * @author debian
 */
import co.unicauca.proyectobase.entidades.Estudiante;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("themeConverter")
public class EstudianteControllerConverter implements Converter {

    /**
     * A partir de una cadena con el formato correspondiente la convierte a tipo
     * Estudiante.
     *
     * @param facesContext
     * @param component
     * @param value
     * @return
     */
    @Override
    public Estudiante getAsObject(FacesContext facesContext, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        EstudianteController controller = (EstudianteController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "estudianteController");
        return controller.getEstudiante(getKey(value));
    }

    /**
     * Convierte una String a entero
     *
     * @param value
     * @return
     */
    java.lang.Integer getKey(String value) {
        java.lang.Integer key;
        key = Integer.valueOf(value);
        return key;
    }

    /**
     * Obtiene, a partir de un entero, su correspondiente String.
     *
     * @param value
     * @return
     */
    String getStringKey(java.lang.Integer value) {
        StringBuilder sb = new StringBuilder();
        sb.append(value);
        return sb.toString();
    }

    /**
     * Convierte un Objeto Estudiante a un cadena con formato
     *
     * @param facesContext
     * @param component
     * @param object
     * @return
     */
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
