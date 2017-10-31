package co.unicauca.proyectobase.entidades;

import com.openkm.sdk4j.OKMWebservices;
import com.openkm.sdk4j.OKMWebservicesFactory;
import com.openkm.sdk4j.bean.QueryParams;
import com.openkm.sdk4j.bean.QueryResult;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Danilo López - dlopezs@unicauca.edu.co
 */
@Entity
@Table(name = "practica_docente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PracticaDocente.findAll", query = "SELECT p FROM PracticaDocente p"),
    @NamedQuery(name = "PracticaDocente.misPracticas", query = "SELECT p FROM PracticaDocente p "),    
    
    @NamedQuery(name = "PracticaDocente.buscarPracticasByNombreUsuario", query = "select pr from PracticaDocente pr where pr.pubIdentificador IN (SELECt p.pubIdentificador FROM Publicacion p where P.pubEstIdentificador = (select e.estIdentificador from Estudiante e where e.estUsuario = :nombreUsuario))"),
        
    @NamedQuery(name = "PracticaDocente.findByPubIdentificador", query = "SELECT p FROM PracticaDocente p WHERE p.pubIdentificador = :pubIdentificador"),
    @NamedQuery(name = "PracticaDocente.findByFechaInicio", query = "SELECT p FROM PracticaDocente p WHERE p.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "PracticaDocente.findByFechaTerminacion", query = "SELECT p FROM PracticaDocente p WHERE p.fechaTerminacion = :fechaTerminacion"),
    @NamedQuery(name = "PracticaDocente.findByLugarPractica", query = "SELECT p FROM PracticaDocente p WHERE p.lugarPractica = :lugarPractica"),
        
    @NamedQuery(name = "PracticaDocente.findByLugarPracticaLike", query = "SELECT p FROM PracticaDocente p WHERE p.lugarPractica like :lugarPractica"),        
    
    @NamedQuery(name = "PracticaDocente.findIdTipoDocumento", query = "SELECT td FROM TipoDocumento td WHERE td.nombre like 'Practica docente'")
                         
})
public class PracticaDocente implements Serializable {
//@NamedQuery(name = "PracticaDocente.findByNombreAutor", query = "SELECT p FROM PracticaDocente p WHERE p.lugarPractica like :lugarPractica"),
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pub_identificador")
    private Integer pubIdentificador;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_terminacion")
    @Temporal(TemporalType.DATE)
    private Date fechaTerminacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "lugar_practica")
    private String lugarPractica;
    @JoinColumn(name = "pub_identificador", referencedColumnName = "pub_identificador", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Publicacion publicacion;

    public PracticaDocente() {
        publicacion= new Publicacion();
    }

    public PracticaDocente(Integer pubIdentificador) {
        this.pubIdentificador = pubIdentificador;
    }

    public PracticaDocente(Integer pubIdentificador, Date fechaInicio, Date fechaTerminacion, String lugarPractica) {
        this.pubIdentificador = pubIdentificador;
        this.fechaInicio = fechaInicio;
        this.fechaTerminacion = fechaTerminacion;
        this.lugarPractica = lugarPractica;
    }

    public Integer getPubIdentificador() {
        return pubIdentificador;
    }

    public void setPubIdentificador(Integer pubIdentificador) {
        this.pubIdentificador = pubIdentificador;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }
    
     public String getFechaIn() {        
        return new SimpleDateFormat("dd-MMMM-yyyy").format(fechaInicio);
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaTerminacion() {
        return fechaTerminacion;
    }
    
     public String getFechaTer() {
        return new SimpleDateFormat("dd-MMMM-yyyy").format(fechaTerminacion);
    }

    public void setFechaTerminacion(Date fechaTerminacion) {
        this.fechaTerminacion = fechaTerminacion;
    }

    public String getLugarPractica() {
        return lugarPractica;
    }

    public void setLugarPractica(String lugarPractica) {
        this.lugarPractica = lugarPractica;
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
    
    public archivoPDF descargaPubPrac() {
        archivoPDF archivo = new archivoPDF();
        String tipoPDF = "practicaDocente";

        String host = "http://localhost:8083/OpenKM";
         //String host = "http://wmyserver.sytes.net:8083/OpenKM";
        String username = "okmAdmin";
        String password = "admin";
        OKMWebservices ws = OKMWebservicesFactory.newInstance(host, username, password);

        try {

            Map<String, String> properties = new HashMap();
            /* Se comprueba el tipo de publicacion: revista congreso , un libro 
                o un capitulo de un libro que se devolvera como resultado*/
           
                properties.put("okp:practica.identPublicacion", "" + publicacion.getPubIdentificador());
                properties.put("okp:practica.tipoPDFCargar", "" + tipoPDF);

            
            
            // properties.put("okp:revista.identPublicacion", "" + this.pubIdentificador);
            QueryParams qParams = new QueryParams();
            qParams.setProperties(properties);
            int posPub = 0;
            for (QueryResult qr : ws.find(qParams)) {
                if (posPub == 0) {
                    String auxDoc = qr.getDocument().getPath();
                    String[] arrayNombre = auxDoc.split("/");
                    int pos = arrayNombre.length;
                    String nombreDoc = arrayNombre[pos - 1];
                    System.out.println("nombreDocPUB: " + nombreDoc);
                    InputStream initialStream = ws.getContent(qr.getDocument().getPath());
                    archivo.setArchivo(initialStream);
                    archivo.setNombreArchivo(nombreDoc);
                }
                posPub = posPub + 1;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("datos"+ archivo.getArchivo());
        return archivo;
    }

}

