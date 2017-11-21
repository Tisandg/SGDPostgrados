package co.unicauca.proyectobase.utilidades;

import com.openkm.sdk4j.OKMWebservices;
import com.openkm.sdk4j.OKMWebservicesFactory;

/**
 * calse para obtener una coneccion a openKM
 * @author Danilo López - dlopezs@unicauca.edu.co
 */
public class ConeccionOpenKM {
    
    private OKMWebservices ws;
    private static ConeccionOpenKM con;
    
    private String host = "http://localhost:8083/OpenKM";
    private String username = "okmAdmin";
    private String password = "admin";    

    private ConeccionOpenKM() {        
        ws = OKMWebservicesFactory.newInstance(host, username, password);
    }
    
    /**
     * metodo que permite obtener una instancia de la clase
     * @return objeto para poder acceder a la coneccion de openKM
     */
    public static ConeccionOpenKM getInstance() {
        if (con == null){
            con = new ConeccionOpenKM();
        }     
        return con;
    }

    /**
     * obtener la coneccion con openKM
     * @return objeto para manejar el programa de openKM
     */
    public OKMWebservices getWs() {
        return ws;
    }
}
