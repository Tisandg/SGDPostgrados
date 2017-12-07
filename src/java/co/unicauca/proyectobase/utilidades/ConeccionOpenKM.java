package co.unicauca.proyectobase.utilidades;

import com.openkm.sdk4j.OKMWebservices;
import com.openkm.sdk4j.OKMWebservicesFactory;

/**
 * Clase que utiliza el patrón Singleton para mantener la conexión con OPENKM
 * @author Danilo López - dlopezs@unicauca.edu.co
 */
public class ConeccionOpenKM 
{    
    private OKMWebservices ws;
    private static ConeccionOpenKM con;
    private final String host = "http://localhost:8083/OpenKM";
    private final String username = "okmAdmin";
    private final String password = "admin";    

    /* Controladores */
    private ConeccionOpenKM() 
    {     
        ws = OKMWebservicesFactory.newInstance(host, username, password);
    }
    
    /**
     * Método que permite obtener una instancia de la clase
     * @return con: objeto para acceder a la coneccion de openKM
     */
    public static ConeccionOpenKM getInstance() {
        if (con == null){
            con = new ConeccionOpenKM();
        }     
        return con;
    }

    /**
     * Método que permite obtener la coneccion con openKM
     * @return ws: objeto para manejar el programa de openKM
     */
    public OKMWebservices getWs() {
        return ws;
    }
}
