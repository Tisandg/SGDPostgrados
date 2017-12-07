package co.unicauca.proyectobase.entidades;

import co.unicauca.proyectobase.utilidades.ConeccionOpenKM;
import com.openkm.sdk4j.OKMWebservices;
import com.openkm.sdk4j.bean.Folder;
import com.openkm.sdk4j.bean.QueryParams;
import com.openkm.sdk4j.bean.QueryResult;
import com.openkm.sdk4j.exception.AccessDeniedException;
import com.openkm.sdk4j.exception.DatabaseException;
import com.openkm.sdk4j.exception.LockException;
import com.openkm.sdk4j.exception.ParseException;
import com.openkm.sdk4j.exception.PathNotFoundException;
import com.openkm.sdk4j.exception.RepositoryException;
import com.openkm.sdk4j.exception.UnknowException;
import com.openkm.sdk4j.exception.WebserviceException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad que representa una práctica docente
 * @author Danilo López - dlopezs@unicauca.edu.co
 */
@Entity
@Table(name = "practica_docente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PracticaDocente.findAll", query = "SELECT p FROM PracticaDocente p"),
    @NamedQuery(name = "PracticaDocente.findByPubIdentificador", query = "SELECT p FROM PracticaDocente p WHERE p.pubIdentificador = :pubIdentificador"),
    @NamedQuery(name = "PracticaDocente.findByNumeroHoras", query = "SELECT p FROM PracticaDocente p WHERE p.numeroHoras = :numeroHoras"),
    @NamedQuery(name = "PracticaDocente.findByOtros", query = "SELECT p FROM PracticaDocente p WHERE p.otros = :otros"),

    @NamedQuery(name = "PracticaDocente.misPracticas", query = "SELECT p FROM PracticaDocente p "),
    @NamedQuery(name = "PracticaDocente.buscarPracticasByNombreUsuario", query = "select pr from PracticaDocente pr where pr.pubIdentificador IN "
            + "(SELECt p.pubIdentificador FROM Publicacion p where P.pubEstIdentificador = (select e.estIdentificador from Estudiante e where e.estUsuario = :nombreUsuario))"),
    @NamedQuery(name = "PracticaDocente.findByPubIdentificador", query = "SELECT p FROM PracticaDocente p WHERE p.pubIdentificador = :pubIdentificador"),
    @NamedQuery(name = "PracticaDocente.findIdTipoDocumento", query = "SELECT td FROM TipoDocumento td WHERE td.nombre like 'Practica docente'")
})
public class PracticaDocente implements Serializable {
    // Versión de la base de datos
    private static final long serialVersionUID = 1L;
    // Clave identificadora de la publicación
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pub_identificador")
    private Integer pubIdentificador;
    // Número de horas de la práctica docente
    @Column(name = "numero_horas")
    private Integer numeroHoras;
    // Otros en la práctica docente
    @Size(max = 200)
    @Column(name = "otros")
    private String otros;
    // Actividad asociada a la práctica docente
    @JoinColumn(name = "id_actividad", referencedColumnName = "id_actividad")
    @ManyToOne(optional = false)
    private ActividadPd idActividad;
    //Publicación asociada a la práctica docente
    @JoinColumn(name = "pub_identificador", referencedColumnName = "pub_identificador", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Publicacion publicacion;
    
    /* Constructor */
    public PracticaDocente() {
    }

    public PracticaDocente(Integer pubIdentificador) {
        this.pubIdentificador = pubIdentificador;
    }

    /* Getter y Setters */
    public Integer getPubIdentificador() {
        return pubIdentificador;
    }

    public void setPubIdentificador(Integer pubIdentificador) {
        this.pubIdentificador = pubIdentificador;
    }

    public Integer getNumeroHoras() {
        return numeroHoras;
    }

    public void setNumeroHoras(Integer numeroHoras) {
        this.numeroHoras = numeroHoras;
    }

    public String getOtros() {
        return otros;
    }

    public void setOtros(String otros) {
        this.otros = otros;
    }

    public ActividadPd getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(ActividadPd idActividad) {
        this.idActividad = idActividad;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pubIdentificador != null ? pubIdentificador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PracticaDocente)) {
            return false;
        }
        PracticaDocente other = (PracticaDocente) object;
        if ((this.pubIdentificador == null && other.pubIdentificador != null) || (this.pubIdentificador != null && !this.pubIdentificador.equals(other.pubIdentificador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.unicauca.proyectobase.entidades.PracticaDocente[ pubIdentificador=" + pubIdentificador + " ]";
    }

    /**
     * Método que permite descargar la práctica docente en un archivo PDF.
     * @return archivo: archivo PDF de la práctica docente
     */
    public archivoPDF descargaPubPrac() {
        archivoPDF archivo = new archivoPDF();
        String tipoPDF = "practicaDocente";

//        String host = "http://localhost:8083/OpenKM";
//        String host = "http://wmyserver.sytes.net:8083/OpenKM";
//        String username = "okmAdmin";
//        String password = "admin";
//        OKMWebservices ws = OKMWebservicesFactory.newInstance(host, username, password);
        OKMWebservices ws = ConeccionOpenKM.getInstance().getWs();  

        try {
            Map<String, String> properties = new HashMap();            
            //System.out.println("IMPRIMIENDO PUBLICACION: " + publicacion.toString());
            properties.put("okp:practica.identPublicacion", "" + publicacion.getPubIdentificador());
            properties.put("okp:practica.tipoPDFCargar", "" + tipoPDF);
                                    
            QueryParams qParams = new QueryParams();
            qParams.setProperties(properties);
            int posPub = 0;            
            for (QueryResult qr : ws.find(qParams)) {                
                if (posPub == 0) {                    
                    String auxDoc = qr.getDocument().getPath();
                    String[] arrayNombre = auxDoc.split("/");
                    int pos = arrayNombre.length;
                    String nombreDoc = arrayNombre[pos - 1];
                    //System.out.println("nombreDocPUB: " + nombreDoc);
                    InputStream initialStream = ws.getContent(qr.getDocument().getPath());
                    archivo.setArchivo(initialStream);
                    archivo.setNombreArchivo(nombreDoc);
                }                
                posPub = posPub + 1;
            }
        } catch (IOException | ParseException | RepositoryException | DatabaseException | UnknowException | WebserviceException | PathNotFoundException | AccessDeniedException e) {
            System.out.println("error en descargaPubPrac de clase practicaDocente.java");
            System.out.println("error: " + e.getMessage());
        }        
        return archivo;
    }
    
    /***
     * Elimina los documentos subidos al gestor de documentos OpenKM.
     * Se obtiene una instancia de openKm con la cual buscamos la carpeta donde
     * estan almacenados los documentos. Si se encuentra se eliminar toda la 
     * carpeta de lo contrario se lanza un mensaje por consola notificando que
     * ocurrio un error al eliminar la documentacion.
     * @return True si se elimino el documento de openKM, False de lo contrario 
     */
    public boolean eliminarDocOpenkm(){        
        boolean eliminado = false;
        String rutaFolder="/okm:root/Doctorado_Electronica/" + this.publicacion.getPubEstIdentificador().getEstUsuario() + "/" + this.publicacion.getPubDiropkm();
        Folder folder = new Folder();
        folder.setPath(rutaFolder);
        OKMWebservices ws = ConeccionOpenKM.getInstance().getWs();
        try {
            /* Se valida si el forder a eliminar existe o no*/
            if(ws.isValidFolder(rutaFolder)){
                ws.deleteFolder(rutaFolder);                
                eliminado = true;
            }
        } catch (PathNotFoundException | AccessDeniedException | RepositoryException | DatabaseException | UnknowException | WebserviceException e) {
            System.out.println("Error al eliminar documento: "+e.getMessage());
        } catch (LockException ex) {        
            Logger.getLogger(PracticaDocente.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception lockException. err: " + ex.getMessage());
        }
        return eliminado;
    }
}
