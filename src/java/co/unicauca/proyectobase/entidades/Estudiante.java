package co.unicauca.proyectobase.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entidad que representa al estudiante
 * @author Sahydo
 */
@Entity
@Table(name = "estudiante")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estudiante.findAll", query = "SELECT e FROM Estudiante e"),
    @NamedQuery(name = "Estudiante.findByEstIdentificador", query = "SELECT e FROM Estudiante e WHERE e.estIdentificador = :estIdentificador"),
    @NamedQuery(name = "Estudiante.buscarPorCodigoExceptoConId", query = "SELECT e FROM Estudiante e WHERE e.estCodigo = :estCodigo  and e.estIdentificador != :estIdentificador "),
    @NamedQuery(name = "Estudiante.buscarPorCodigo", query = "SELECT e FROM Estudiante e WHERE e.estCodigo = :estCodigo"),
    @NamedQuery(name = "Estudiante.buscarPorCorreoExceptoConId", query = "SELECT e FROM Estudiante e WHERE e.estCorreo = :estCorreo  and e.estIdentificador != :estIdentificador "),
    @NamedQuery(name = "Estudiante.findByEstNombre", query = "SELECT e FROM Estudiante e WHERE e.estNombre = :estNombre"),
    @NamedQuery(name = "Estudiante.findByEstApellido", query = "SELECT e FROM Estudiante e WHERE e.estApellido = :estApellido"),
    @NamedQuery(name = "Estudiante.findByEstCorreo", query = "SELECT e FROM Estudiante e WHERE e.estCorreo = :estCorreo"),
    @NamedQuery(name = "Estudiante.findByEstCohorte", query = "SELECT e FROM Estudiante e WHERE e.estCohorte = :estCohorte"),
    @NamedQuery(name = "Estudiante.findByEstTutor", query = "SELECT e FROM Estudiante e WHERE e.estTutor = :estTutor"),
    @NamedQuery(name = "Estudiante.findByEstSemestre", query = "SELECT e FROM Estudiante e WHERE e.estSemestre = :estSemestre"),
    @NamedQuery(name = "Estudiante.findByEstEstado", query = "SELECT e FROM Estudiante e WHERE e.estEstado = :estEstado"),
    @NamedQuery(name = "Estudiante.findByEstUsuario", query = "SELECT e FROM Estudiante e WHERE e.estUsuario = :estUsuario"),
    @NamedQuery(name = "Estudiante.findAllByString", query = "SELECT e FROM Estudiante e WHERE e.estCodigo LIKE :texto OR e.estNombre LIKE :texto OR e.estApellido LIKE :texto"),
    @NamedQuery(name = "Estudiante.findCreditosByNomUsu", query = "SELECT e.estCreditos FROM Estudiante e WHERE e.estUsuario = :nombreUsuario"),        
    
    @NamedQuery(name = "Estudiante.findNombreById", query = "SELECT e FROM Estudiante e WHERE e.estIdentificador = :id") 
})
public class Estudiante implements Serializable {

    @OneToMany(mappedBy = "pubEstIdentificador")
    private List<Publicacion> documentoList;

    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioId;

    // Versión de la base de datos
    private static final long serialVersionUID = 1L;
    // Clave identificadora del estudiante
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "est_identificador")
    private Integer estIdentificador;
    // Código del estudiante
    @Size(max = 20)
    @Column(name = "est_codigo")
    private String estCodigo;
    // Nombre del estudiante
    @Size(max = 20)
    @Column(name = "est_nombre")
    private String estNombre;
    // Apellido del estudiante
    @Size(max = 20)
    @Column(name = "est_apellido")
    private String estApellido;
    // Correo del estudiante
    @Size(max = 30)
    @Column(name = "est_correo")
    private String estCorreo;
    // Cohorte del estudiante
    @Column(name = "est_cohorte")
    private Integer estCohorte;
    // Tutor del estudiante
    @Size(max = 45)
    @Column(name = "est_tutor")
    private String estTutor;
    // Semestre del estudiante
    @Column(name = "est_semestre")
    private Integer estSemestre;
    // Estado del estudiante
    @Size(max = 12)
    @Column(name = "est_estado")
    private String estEstado;
    // Usuario del estudiante
    @Size(max = 20)
    @Column(name = "est_usuario")
    private String estUsuario;
    // Créditos del estudiante
    @Column(name = "est_creditos")
    private Integer estCreditos;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estudiante")
    private List<Doctorado> doctoradoList;
    // Publicaciones asociadas al estudiante
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pubEstIdentificador")
    private List<Publicacion> publicacionList;

    /* Constructores */
    public Estudiante() {
    }

    public Estudiante(Integer estIdentificador) {
        this.estIdentificador = estIdentificador;
    }

    /*Setters y Getters */
    public Integer getEstCreditos() {
        return estCreditos;
    }

    public void setEstCreditos(Integer estCreditos) {
        this.estCreditos = estCreditos;
    }
    

    public Integer getEstIdentificador() {
        return estIdentificador;
    }

    public void setEstIdentificador(Integer estIdentificador) {
        this.estIdentificador = estIdentificador;
    }

    public String getEstCodigo() {
        return estCodigo;
    }

    public void setEstCodigo(String estCodigo) {
        this.estCodigo = estCodigo;
    }

    public String getEstNombre() {
        return estNombre;
    }

    public void setEstNombre(String estNombre) {
        this.estNombre = estNombre;
    }

    public String getEstApellido() {
        return estApellido;
    }

    public void setEstApellido(String estApellido) {
        this.estApellido = estApellido;
    }

    public String getEstCorreo() {
        return estCorreo;
    }

    public void setEstCorreo(String estCorreo) {
        this.estCorreo = estCorreo;
    }

    public Integer getEstCohorte() {
        return estCohorte;
    }

    public void setEstCohorte(Integer estCohorte) {
        this.estCohorte = estCohorte;
    }

    public String getEstTutor() {
        return estTutor;
    }

    public void setEstTutor(String estTutor) {
        this.estTutor = estTutor;
    }

    public Integer getEstSemestre() {
        return estSemestre;
    }

    public void setEstSemestre(Integer estSemestre) {
        this.estSemestre = estSemestre;
    }

    public String getEstEstado() {
        return estEstado;
    }

    public void setEstEstado(String estEstado) {
        this.estEstado = estEstado;
    }

    public String getEstUsuario() {
        return estUsuario;
    }

    public void setEstUsuario(String estUsuario) {
        this.estUsuario = estUsuario;
    }
    
    public String getNombreCompleto(){
        return ""+estNombre+" "+estApellido;
    }
    
    @XmlTransient
    public List<Doctorado> getDoctoradoList() {
        return doctoradoList;
    }

    public void setDoctoradoList(List<Doctorado> doctoradoList) {
        this.doctoradoList = doctoradoList;
    }

    @XmlTransient
    public List<Publicacion> getPublicacionList() {
        return publicacionList;
    }

    public void setPublicacionList(List<Publicacion> publicacionList) {
        this.publicacionList = publicacionList;
    }
    
    public Usuario getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Usuario usuarioId) {
        this.usuarioId = usuarioId;
    }

    @XmlTransient
    public List<Publicacion> getDocumentoList() {
        return documentoList;
    }

    public void setDocumentoList(List<Publicacion> documentoList) {
        this.documentoList = documentoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (estIdentificador != null ? estIdentificador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estudiante)) {
            return false;
        }
        Estudiante other = (Estudiante) object;
        if ((this.estIdentificador == null && other.estIdentificador != null) || (this.estIdentificador != null && !this.estIdentificador.equals(other.estIdentificador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() 
    {
        return ""+estCodigo; 
    }
}
