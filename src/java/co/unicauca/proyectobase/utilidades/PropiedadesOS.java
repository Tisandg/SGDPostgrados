package co.unicauca.proyectobase.utilidades;

/**
 * Clase encargada de retornar el separador utilizado en cada sistema operativo.
 * Por separador se entiende el caracter de navegación entre carpetas.
 * @author Danilo López - dlopezs@unicauca.edu.co
 */
public class PropiedadesOS {
    
    /**
     * Método que retorna el separador utilizado en cada sistema operativo.
     * @return separador o caracter de navegación entre carpetas.
     */
    public String getSeparator()
    {
        return System.getProperty("file.separator");
    }

}
