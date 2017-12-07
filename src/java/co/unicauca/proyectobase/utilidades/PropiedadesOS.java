package co.unicauca.proyectobase.utilidades;

/**
 * Clase encargada de retornar el separdor utilizado en cada sistema operativo.
 * Por separador se entiende el caracter de navegación entre carpetas.
 * @author Danilo López - dlopezs@unicauca.edu.co
 */
public class PropiedadesOS {
    
    public String getSeparator()
    {
        return System.getProperty("file.separator");
    }

}
