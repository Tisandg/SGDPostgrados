package co.unicauca.proyectobase.entidades;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/* Librerias Itext y sdk Openkm*/
import com.openkm.sdk4j.bean.Folder;
import org.apache.commons.io.IOUtils;
import com.openkm.sdk4j.OKMWebservices;
import com.openkm.sdk4j.OKMWebservicesFactory;
import com.openkm.sdk4j.bean.QueryResult;
import com.openkm.sdk4j.bean.form.FormElement;
import com.openkm.sdk4j.bean.form.Input;
import java.util.List;
import java.io.InputStream;
import com.itextpdf.text.pdf.PdfAConformanceLevel;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfAWriter;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.openkm.sdk4j.bean.QueryParams;
import com.openkm.sdk4j.exception.AccessDeniedException;
import com.openkm.sdk4j.exception.AutomationException;
import com.openkm.sdk4j.exception.DatabaseException;
import com.openkm.sdk4j.exception.ExtensionException;
import com.openkm.sdk4j.exception.FileSizeExceededException;
import com.openkm.sdk4j.exception.ItemExistsException;
import com.openkm.sdk4j.exception.LockException;
import com.openkm.sdk4j.exception.NoSuchGroupException;
import com.openkm.sdk4j.exception.NoSuchPropertyException;
import com.openkm.sdk4j.exception.ParseException;
import com.openkm.sdk4j.exception.PathNotFoundException;
import com.openkm.sdk4j.exception.RepositoryException;
import com.openkm.sdk4j.exception.UnknowException;
import com.openkm.sdk4j.exception.UnsupportedMimeTypeException;
import com.openkm.sdk4j.exception.UserQuotaExceededException;
import com.openkm.sdk4j.exception.VirusDetectedException;
import com.openkm.sdk4j.exception.WebserviceException;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author DELL7
 */
@Entity
@Table(name = "publicacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Publicacion.findAll", query = "SELECT p FROM Publicacion p"),
    @NamedQuery(name = "Publicacion.findByPubIdentificador", query = "SELECT p FROM Publicacion p WHERE p.pubIdentificador = :pubIdentificador"),
    @NamedQuery(name = "Publicacion.findByPubHash", query = "SELECT p FROM Publicacion p WHERE p.pubHash = :pubHash"),
    @NamedQuery(name = "Publicacion.findByPubDiropkm", query = "SELECT p FROM Publicacion p WHERE p.pubDiropkm = :pubDiropkm"),
    @NamedQuery(name = "Publicacion.findByPubCreditos", query = "SELECT p FROM Publicacion p WHERE p.pubCreditos = :pubCreditos"),
    @NamedQuery(name = "Publicacion.findByPubFechaVisado", query = "SELECT p FROM Publicacion p WHERE p.pubFechaVisado = :pubFechaVisado"),
    @NamedQuery(name = "Publicacion.findByPubFechaRegistro", query = "SELECT p FROM Publicacion p WHERE p.pubFechaRegistro = :pubFechaRegistro"),
    @NamedQuery(name = "Publicacion.findByPubEstado", query = "SELECT p FROM Publicacion p WHERE p.pubEstado = :pubEstado"),
    @NamedQuery(name = "Publicacion.findByPubNombreAutor", query = "SELECT p FROM Publicacion p WHERE p.pubNombreAutor = :pubNombreAutor"),
    @NamedQuery(name = "Publicacion.findByPubAutoresSecundarios", query = "SELECT p FROM Publicacion p WHERE p.pubAutoresSecundarios = :pubAutoresSecundarios"),
    @NamedQuery(name = "Publicacion.findByPubTipoPublicacion", query = "SELECT p FROM Publicacion p WHERE p.pubTipoPublicacion = :pubTipoPublicacion"),
    @NamedQuery(name = "Publicacion.findByPubFechaPublicacion", query = "SELECT p FROM Publicacion p WHERE p.pubFechaPublicacion = :pubFechaPublicacion"),
    @NamedQuery(name = "Publicacion.findByPubDoi", query = "SELECT p FROM Publicacion p WHERE p.pubDoi = :pubDoi"),
    @NamedQuery(name = "Publicacion.findByPubIsbn", query = "SELECT p FROM Publicacion p WHERE p.pubIsbn = :pubIsbn"),
        
    @NamedQuery(name = "Publicacion.findAllByYear", query = "SELECT p FROM Publicacion p "
            + "WHERE FUNC('YEAR',p.pubFechaRegistro) = :anio ORDER BY  p.pubTipoPublicacion DESC"),
    
    @NamedQuery(name = "Publicacion.findAllBySemester", query = "SELECT p FROM Publicacion p "
            + "WHERE FUNC('YEAR',p.pubFechaRegistro) = :anio AND FUNC('MONTH',p.pubFechaRegistro) Between :inicio AND :fin "
            + "ORDER BY  p.pubTipoPublicacion DESC"),
    
    @NamedQuery(name = "Publicacion.StudentPublications_Year", query = "SELECT p FROM Publicacion p "
            + "WHERE p.pubEstIdentificador.estIdentificador = :identificador AND FUNC('YEAR',p.pubFechaRegistro) = :anio "
            + "ORDER BY  p.pubTipoPublicacion DESC"),
    
    @NamedQuery(name = "Publicacion.StudentPublications_Semester", query = "SELECT p FROM Publicacion p "
            + "WHERE p.pubEstIdentificador.estIdentificador = :identificador AND FUNC('YEAR',p.pubFechaRegistro) = :anio "
            + "AND FUNC('MONTH',p.pubFechaRegistro) Between :inicio AND :fin ORDER BY  p.pubTipoPublicacion DESC"),

    @NamedQuery(name = "Publicacion.findAllEst", query = "SELECT p FROM Publicacion p WHERE  p.pubNombreAutor LIKE :variableFiltro OR p.pubTipoPublicacion LIKE :variableFiltro")
    , @NamedQuery(name = "Publicacion.findAllRev", query = "SELECT p FROM Publicacion p WHERE  p.pubNombreAutor LIKE :variableFiltro OR p.pubTipoPublicacion LIKE :variableFiltro OR p.revista.revTituloArticulo LIKE :variableFiltro")
    , @NamedQuery(name = "Publicacion.findAllCong", query = "SELECT p FROM Publicacion p WHERE  p.pubNombreAutor LIKE :variableFiltro OR p.pubTipoPublicacion LIKE :variableFiltro OR p.congreso.congTituloPonencia LIKE :variableFiltro")
    , @NamedQuery(name = "Publicacion.findAllLib", query = "SELECT p FROM Publicacion p WHERE  p.pubNombreAutor LIKE :variableFiltro OR p.pubTipoPublicacion LIKE :variableFiltro OR p.libro.libTituloLibro LIKE :variableFiltro")
    , @NamedQuery(name = "Publicacion.findAllCapLib", query = "SELECT p FROM Publicacion p WHERE  p.pubNombreAutor LIKE :variableFiltro OR p.pubTipoPublicacion LIKE :variableFiltro OR p.capituloLibro.caplibTituloCapitulo LIKE :variableFiltro")

    , @NamedQuery(name = "Publicacion.findAllByRev", query = "SELECT p FROM Publicacion p WHERE  (p.pubEstIdentificador.estIdentificador =:identificacion) AND (p.pubNombreAutor LIKE :variableFiltro OR p.pubTipoPublicacion LIKE :variableFiltro OR p.revista.revTituloArticulo LIKE :variableFiltro)")
    , @NamedQuery(name = "Publicacion.findAllByCong", query = "SELECT p FROM Publicacion p WHERE  (p.pubEstIdentificador.estIdentificador =:identificacion) AND (p.pubNombreAutor LIKE :variableFiltro OR p.pubTipoPublicacion LIKE :variableFiltro OR p.congreso.congTituloPonencia LIKE :variableFiltro)")
    , @NamedQuery(name = "Publicacion.findAllByLib", query = "SELECT p FROM Publicacion p WHERE  (p.pubEstIdentificador.estIdentificador =:identificacion) AND (p.pubNombreAutor LIKE :variableFiltro OR p.pubTipoPublicacion LIKE :variableFiltro OR p.libro.libTituloLibro LIKE :variableFiltro)")
    , @NamedQuery(name = "Publicacion.findAllByCapLib", query = "SELECT p FROM Publicacion p WHERE  (p.pubEstIdentificador.estIdentificador =:identificacion) AND (p.pubNombreAutor LIKE :variableFiltro OR p.pubTipoPublicacion LIKE :variableFiltro OR p.capituloLibro.caplibTituloCapitulo LIKE :variableFiltro )"),

    @NamedQuery(name = "Publicacion.findAllFiltPubEst", query = "SELECT p FROM Publicacion p WHERE  (p.pubEstIdentificador.estIdentificador =:identificacion) AND (p.pubNombreAutor LIKE :variableFiltro OR p.pubTipoPublicacion LIKE :variableFiltro OR p.revista.revTituloArticulo LIKE :variableFiltro OR p.capituloLibro.caplibTituloCapitulo LIKE :variableFiltro)"),
    @NamedQuery(
            name = "findAllPub_Est",
            query = "SELECT p FROM Publicacion p WHERE p.pubEstIdentificador.estIdentificador= :identificacion"
    ),
    @NamedQuery(name = "Publicacion.findByPubIssn", query = "SELECT p FROM Publicacion p WHERE p.pubIssn = :pubIssn"),
    @NamedQuery(name = "Publicacion.updateVisadoById", query = "UPDATE Publicacion as p SET p.pubVisado = :valorVisado where p.pubIdentificador = :id")
})
public class Publicacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pub_identificador")
    private Integer pubIdentificador;
    @Size(max = 40)
    @Column(name = "pub_hash")
    private String pubHash;
    @Size(max = 30)
    @Column(name = "pub_diropkm")
    private String pubDiropkm;
    @Column(name = "pub_creditos")
    private Integer pubCreditos;
    @Column(name = "pub_fecha_visado")
    @Temporal(TemporalType.DATE)
    private Date pubFechaVisado;
    @Column(name = "pub_fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date pubFechaRegistro;
    @Size(max = 15)
    @Column(name = "pub_estado")
    private String pubEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "pub_nombre_autor")
    private String pubNombreAutor;
    @Size(max = 300)
    @Column(name = "pub_autores_secundarios")
    private String pubAutoresSecundarios;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 22)
    @Column(name = "pub_tipo_publicacion")
    private String pubTipoPublicacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pub_fecha_publicacion")
    @Temporal(TemporalType.DATE)
    private Date pubFechaPublicacion;
    @Column(name = "pub_num_acta")
    private Integer pubNumActa;
    @Size(max = 30)
    @Column(name = "pub_doi")
    private String pubDoi;
    @Size(max = 30)
    @Column(name = "pub_isbn")
    private String pubIsbn;
    @Size(max = 30)
    @Column(name = "pub_issn")
    private String pubIssn;

    @Size(max = 20)
    @Column(name = "pub_visado")
    private String pubVisado;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "publicacion")
    private Libro libro;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "arcPubIdentificador")
    private Collection<Archivo> archivoCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "publicacion")
    private Congreso congreso;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "publicacion")
    private Revista revista;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "publicacion")
    private CapituloLibro capituloLibro;
    @JoinColumn(name = "pub_est_identificador", referencedColumnName = "est_identificador")
    @ManyToOne
    private Estudiante pubEstIdentificador;

    public Publicacion() {
        this.pubAutoresSecundarios = "";
    }

    public Publicacion(Integer pubIdentificador) {
        this.pubIdentificador = pubIdentificador;
        this.pubAutoresSecundarios = "";
    }

    public Publicacion(Integer pubIdentificador, String pubNombreAutor, String pubTipoPublicacion, Date pubFechaPublicacion) {
        this.pubIdentificador = pubIdentificador;
        this.pubNombreAutor = pubNombreAutor;
        this.pubTipoPublicacion = pubTipoPublicacion;
        this.pubFechaPublicacion = pubFechaPublicacion;
        this.pubAutoresSecundarios = "";
    }

    public void agregarMetadatos(UploadedFile ArticuloPDF, UploadedFile TablaContenidoPDF, UploadedFile cartaAprobacionPDF) throws IOException, GeneralSecurityException, DocumentException, PathNotFoundException, AccessDeniedException {

        /*Nombre de los archivos que se almacenaran en el repositorio*/
        MetodosPDF mpdf = new MetodosPDF();
        String codigoEst = this.pubEstIdentificador.getEstCodigo();
        String codigoFirma = mpdf.codigoFirma(codigoEst);
        codigoFirma = codigoFirma.trim();

        String nombreCartaAprob = "Carta de Aprobacion-" + codigoFirma;
        String nombrePublicacion = "";
        String nombreTablaC = "Tabla de Contenido-" + codigoFirma;

        if (this.pubTipoPublicacion.equalsIgnoreCase("revista")) {
            nombrePublicacion = this.revista.getRevTituloArticulo();

        }
        if (this.pubTipoPublicacion.equalsIgnoreCase("congreso")) {
            nombrePublicacion = this.congreso.getCongTituloPonencia();
        }
        if (this.pubTipoPublicacion.equalsIgnoreCase("libro")) {
            nombrePublicacion = this.libro.getLibTituloLibro();

        }
        if (this.pubTipoPublicacion.equalsIgnoreCase("capitulo_libro")) {

            nombrePublicacion = this.capituloLibro.getCaplibTituloCapitulo();
        }


        /*Obtiene la ruta de la ubicacion del servidor donde se almacenaran 
          temporalmente los archivos ,para luego subirlos al Gestor Documental OpenKm  */
        String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        //   String destCartaAprob = realPath + "WEB-INF\\temp\\Tabla de Contenido.pdf";
        String destCartaAprob = realPath + "WEB-INF\\temp\\" + nombreCartaAprob + ".pdf";
        String destArticulo = realPath + "WEB-INF\\temp\\" + nombrePublicacion + ".pdf";
        //  String destTablaC = realPath + "WEB-INF\\temp\\Tabla de Contenido.pdf";
        String destTablaC = realPath + "WEB-INF\\temp\\" + nombreTablaC + ".pdf";


        /*  Estampa de Tiempo
            Obtiene el dia y hora actual del servidor para posteriormente adicionarlo
            como Metadato en el documento PDF/A y el Gestor Documental*/
        Date date = new Date();
        DateFormat datehourFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String estampaTiempo = "" + datehourFormat.format(date);

        this.setPubFechaRegistro(date);

        /*  Metodo para almacenar los metadatos de la Carte de Aprobacion , Articulo y Tabla de Contenido 
            para almacenarlo en formato PDFA */
        ArrayList<tipoPDF_cargar> subidaArchivos = new ArrayList<>();

        /* tipoPDF_cargar cartaAprobacion = new tipoPDF_cargar();
        cartaAprobacion.setNombreArchivo(nombreCartaAprob);
        cartaAprobacion.setRutaArchivo(destCartaAprob);
        cartaAprobacion.setTipoPDF("cartaAprobacion");
        cartaAprobacion.setArchivoIS(cartaAprobacionPDF.getInputstream());
        subidaArchivos.add(cartaAprobacion); */
        if (!cartaAprobacionPDF.getFileName().equalsIgnoreCase("")) {
            tipoPDF_cargar cartaAprobacion = new tipoPDF_cargar();
            cartaAprobacion.setNombreArchivo(nombreCartaAprob);
            cartaAprobacion.setRutaArchivo(destCartaAprob);
            cartaAprobacion.setTipoPDF("cartaAprobacion");
            cartaAprobacion.setArchivoIS(cartaAprobacionPDF.getInputstream());
            subidaArchivos.add(cartaAprobacion);;
        }

        if (!ArticuloPDF.getFileName().equalsIgnoreCase("")) {
            tipoPDF_cargar articulo = new tipoPDF_cargar();
            articulo.setNombreArchivo(nombrePublicacion);
            articulo.setRutaArchivo(destArticulo);
            articulo.setTipoPDF("tipoPublicacion");
            articulo.setArchivoIS(ArticuloPDF.getInputstream());
            subidaArchivos.add(articulo);
        }
        if (!TablaContenidoPDF.getFileName().equalsIgnoreCase("")) {
            tipoPDF_cargar tablaContenido = new tipoPDF_cargar();
            tablaContenido.setNombreArchivo(nombreTablaC);
            tablaContenido.setRutaArchivo(destTablaC);
            tablaContenido.setTipoPDF("tablaContenido");
            tablaContenido.setArchivoIS(TablaContenidoPDF.getInputstream());
            subidaArchivos.add(tablaContenido);
        }

        CrearPDFA_Metadata(subidaArchivos, estampaTiempo);

        String hash = mpdf.obtenerHash(destArticulo);

        /*  Metodo para almacenar en el Gestor Documental(OPENKM), carta de aprobacion,
            el articulo en formato PDFA y la Tabla de Contenido del Articulo (formato PDFA) */
        //   SubirOpenKM(rutasArchivos, nombreArchivos, estampaTiempo, codigoFirma, hash);
        SubirOpenKM(subidaArchivos, estampaTiempo, codigoFirma, hash);
    }

    public void SubirOpenKM(ArrayList<tipoPDF_cargar> subidaArchivos, String estampaTiempo, String codigoFirma, String hash) throws IOException {

        /* Inicia una instancia del Gestor Documental Openkm*/
        this.setPubHash(hash);
        String host = "http://localhost:8083/OpenKM";
         //String host = "http://wmyserver.sytes.net:8083/OpenKM";
        String username = "okmAdmin";
        String password = "admin";
        OKMWebservices ws = OKMWebservicesFactory.newInstance(host, username, password);
        try {
            boolean crearFolder = false;
            String rutaFolderCrear;
            /* codigoFirma - en este caso corresponde al nombre de la carpeta que contendra
                el articulo y su tabla de contenido en formato PDFA
                Ruta del folder a crear en el Gestor Openkm*/
            // rutaFolderCrear = "/okm:root/Doctorado_Electronica/" + codigoFirma;
            rutaFolderCrear = "/okm:root/Doctorado_Electronica/" + this.pubEstIdentificador.getEstUsuario();
            this.setPubDiropkm(codigoFirma);
            try {
                /* Se valida si el forder a crear existe o no*/
                ws.isValidFolder(rutaFolderCrear);
                crearFolder = false;
            } catch (Exception e) {
                crearFolder = true;
            }
            if (crearFolder == true) {
                /* Si no existe el folder, se crea con la ruta(rutaFolderCrear)*/
                Folder fld = new Folder();
                fld.setPath(rutaFolderCrear);
                ws.createFolder(fld);
            }

            rutaFolderCrear = "/okm:root/Doctorado_Electronica/" + this.pubEstIdentificador.getEstUsuario() + "/" + codigoFirma;
            try {
                /* Se valida si el forder a crear existe o no*/
                ws.isValidFolder(rutaFolderCrear);
                crearFolder = false;
            } catch (Exception e) {
                crearFolder = true;
            }
            if (crearFolder == true) {
                /* Si no existe el folder, se crea con la ruta(rutaFolderCrear)*/
                Folder fld = new Folder();
                fld.setPath(rutaFolderCrear);
                ws.createFolder(fld);
            }

            for (int i = 0; i < subidaArchivos.size(); i++) {

                Archivo arch = (Archivo) archivoCollection.toArray()[i];
                /* Se Comprubea si los  Metadatos en OpenKm que se van  a llenar son para 
                 una revista, un congreso , un libro o un capitulo de un libro*/
                if (this.pubTipoPublicacion.equalsIgnoreCase("revista")) {

                    InputStream is = new FileInputStream("" + subidaArchivos.get(i).getRutaArchivo());
                    com.openkm.sdk4j.bean.Document doc = new com.openkm.sdk4j.bean.Document();
                    doc.setPath(rutaFolderCrear + "/" + subidaArchivos.get(i).getNombreArchivo() + ".pdf");
                    ws.createDocument(doc, is);

                    IOUtils.closeQuietly(is);
                    List<FormElement> fElements = ws.getPropertyGroupProperties("" + rutaFolderCrear + "/" + subidaArchivos.get(i).getNombreArchivo() + ".pdf", "okg:revista");
                    for (FormElement fElement : fElements) {
                        if (fElement.getName().equals("okp:revista.identPublicacion")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.pubIdentificador);
                        }
                        if (fElement.getName().equals("okp:revista.identArchivo")) {
                            Input name = (Input) fElement;
                            name.setValue("" + arch.getArcIdentificador());
                        }

                        if (fElement.getName().equals("okp:revista.tipoPDFCargar")) {
                            Input name = (Input) fElement;
                            name.setValue("" + subidaArchivos.get(i).getTipoPDF());
                        }

                        if (fElement.getName().equals("okp:revista.estampaTiempo")) {
                            Input name = (Input) fElement;
                            name.setValue("" + estampaTiempo);
                        }
                        if (fElement.getName().equals("okp:revista.nombreAutor")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.pubNombreAutor);
                        }
                        if (fElement.getName().equals("okp:revista.autoresSecundarios")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.pubAutoresSecundarios);
                        }

                        SimpleDateFormat formateador = new SimpleDateFormat("MM-yyyy");
                        String FechaPublicacion = formateador.format(this.pubFechaPublicacion);

                        if (fElement.getName().equals("okp:revista.fechaPublicacion")) {
                            Input name = (Input) fElement;
                            name.setValue("" + FechaPublicacion);
                        }
                        if (fElement.getName().equals("okp:revista.tipoPublicacion")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.pubTipoPublicacion);
                        }
                        if (fElement.getName().equals("okp:revista.tituloArticulo")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.revista.getRevTituloArticulo());
                        }
                        if (fElement.getName().equals("okp:revista.nombreRevista")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.revista.getRevNombreRevista());
                        }
                        if (fElement.getName().equals("okp:revista.categoria")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.revista.getRevCategoria());
                        }
                        if (fElement.getName().equals("okp:revista.DOI")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.pubDoi);
                        }
                        if (fElement.getName().equals("okp:revista.ISBN")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.pubIsbn);
                        }
                        if (fElement.getName().equals("okp:revista.ISSN")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.pubIssn);
                        }
                    }
                    ws.setPropertyGroupProperties("" + rutaFolderCrear + "/" + subidaArchivos.get(i).getNombreArchivo() + ".pdf", "okg:revista", fElements);

                }

                if (this.pubTipoPublicacion.equalsIgnoreCase("congreso")) {

                    InputStream is = new FileInputStream("" + subidaArchivos.get(i).getRutaArchivo());
                    com.openkm.sdk4j.bean.Document doc = new com.openkm.sdk4j.bean.Document();

                    doc.setPath(rutaFolderCrear + "/" + subidaArchivos.get(i).getNombreArchivo() + ".pdf");
                    ws.createDocument(doc, is);
                    IOUtils.closeQuietly(is);
                    List<FormElement> fElements = ws.getPropertyGroupProperties("" + rutaFolderCrear + "/" + subidaArchivos.get(i).getNombreArchivo() + ".pdf", "okg:congreso");

                    for (FormElement fElement : fElements) {
                        if (fElement.getName().equals("okp:congreso.identPublicacion")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.pubIdentificador);
                        }
                        if (fElement.getName().equals("okp:congreso.identArchivo")) {
                            Input name = (Input) fElement;
                            name.setValue("" + arch.getArcIdentificador());
                        }

                        if (fElement.getName().equals("okp:congreso.tipoPDFCargar")) {
                            Input name = (Input) fElement;
                            name.setValue("" + subidaArchivos.get(i).getTipoPDF());
                        }

                        if (fElement.getName().equals("okp:congreso.estampaTiempo")) {
                            Input name = (Input) fElement;
                            name.setValue("" + estampaTiempo);
                        }
                        if (fElement.getName().equals("okp:congreso.nombreAutor")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.pubNombreAutor);
                        }
                        if (fElement.getName().equals("okp:congreso.autoresSecundarios")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.pubAutoresSecundarios);
                        }

                        SimpleDateFormat formateador = new SimpleDateFormat("MM-yyyy");
                        String FechaPublicacion = formateador.format(this.pubFechaPublicacion);

                        if (fElement.getName().equals("okp:congreso.fechaPublicacion")) {
                            Input name = (Input) fElement;
                            name.setValue("" + FechaPublicacion);
                        }
                        if (fElement.getName().equals("okp:congreso.tipoPublicacion")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.pubTipoPublicacion);
                        }
                        if (fElement.getName().equals("okp:congreso.tituloPonencia")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.congreso.getCongTituloPonencia());
                        }
                        if (fElement.getName().equals("okp:congreso.nombreEvento")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.congreso.getCongNombreEvento());
                        }
                        if (fElement.getName().equals("okp:congreso.tipoCongreso")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.congreso.getCongTipoCongreso());
                        }
                        if (fElement.getName().equals("okp:congreso.DOI")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.pubDoi);
                        }
                        if (fElement.getName().equals("okp:congreso.ISBN")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.pubIsbn);
                        }
                        if (fElement.getName().equals("okp:congreso.ISSN")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.pubIssn);
                        }
                    }
                    ws.setPropertyGroupProperties("" + rutaFolderCrear + "/" + subidaArchivos.get(i).getNombreArchivo() + ".pdf", "okg:congreso", fElements);

                }

                if (this.pubTipoPublicacion.equalsIgnoreCase("capitulo_libro")) {

                    InputStream is = new FileInputStream("" + subidaArchivos.get(i).getRutaArchivo());
                    com.openkm.sdk4j.bean.Document doc = new com.openkm.sdk4j.bean.Document();

                    doc.setPath(rutaFolderCrear + "/" + subidaArchivos.get(i).getNombreArchivo() + ".pdf");
                    ws.createDocument(doc, is);
                    IOUtils.closeQuietly(is);
                    List<FormElement> fElements = ws.getPropertyGroupProperties("" + rutaFolderCrear + "/" + subidaArchivos.get(i).getNombreArchivo() + ".pdf", "okg:capLibro");

                    for (FormElement fElement : fElements) {
                        if (fElement.getName().equals("okp:capLibro.identPublicacion")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.pubIdentificador);
                        }
                        if (fElement.getName().equals("okp:capLibro.identArchivo")) {
                            Input name = (Input) fElement;
                            name.setValue("" + arch.getArcIdentificador());
                        }

                        if (fElement.getName().equals("okp:capLibro.tipoPDFCargar")) {
                            Input name = (Input) fElement;
                            name.setValue("" + subidaArchivos.get(i).getTipoPDF());
                        }
                        if (fElement.getName().equals("okp:capLibro.estampaTiempo")) {
                            Input name = (Input) fElement;
                            name.setValue("" + estampaTiempo);
                        }
                        if (fElement.getName().equals("okp:capLibro.nombreAutor")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.pubNombreAutor);
                        }
                        if (fElement.getName().equals("okp:capLibro.autoresSecundarios")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.pubAutoresSecundarios);
                        }

                        SimpleDateFormat formateador = new SimpleDateFormat("MM-yyyy");
                        String FechaPublicacion = formateador.format(this.pubFechaPublicacion);

                        if (fElement.getName().equals("okp:capLibro.fechaPublicacion")) {
                            Input name = (Input) fElement;
                            name.setValue("" + FechaPublicacion);
                        }
                        if (fElement.getName().equals("okp:capLibro.tipoPublicacion")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.pubTipoPublicacion);
                        }
                        if (fElement.getName().equals("okp:capLibro.tituloLibro")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.capituloLibro.getCaplibTituloLibro());
                        }
                        if (fElement.getName().equals("okp:capLibro.tituloCapitulo")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.capituloLibro.getCaplibTituloCapitulo());
                        }
                        if (fElement.getName().equals("okp:capLibro.DOI")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.pubDoi);
                        }
                        if (fElement.getName().equals("okp:capLibro.ISBN")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.pubIsbn);
                        }
                        if (fElement.getName().equals("okp:capLibro.ISSN")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.pubIssn);
                        }
                    }
                    ws.setPropertyGroupProperties("" + rutaFolderCrear + "/" + subidaArchivos.get(i).getNombreArchivo() + ".pdf", "okg:capLibro", fElements);

                }

                if (this.pubTipoPublicacion.equalsIgnoreCase("libro")) {

                    InputStream is = new FileInputStream("" + subidaArchivos.get(i).getRutaArchivo());
                    com.openkm.sdk4j.bean.Document doc = new com.openkm.sdk4j.bean.Document();

                    doc.setPath(rutaFolderCrear + "/" + subidaArchivos.get(i).getNombreArchivo() + ".pdf");
                    ws.createDocument(doc, is);
                    IOUtils.closeQuietly(is);
                    List<FormElement> fElements = ws.getPropertyGroupProperties("" + rutaFolderCrear + "/" + subidaArchivos.get(i).getNombreArchivo() + ".pdf", "okg:libro");

                    for (FormElement fElement : fElements) {
                        if (fElement.getName().equals("okp:libro.identPublicacion")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.pubIdentificador);
                        }
                        if (fElement.getName().equals("okp:libro.identArchivo")) {
                            Input name = (Input) fElement;
                            name.setValue("" + arch.getArcIdentificador());
                        }

                        if (fElement.getName().equals("okp:libro.tipoPDFCargar")) {
                            Input name = (Input) fElement;
                            name.setValue("" + subidaArchivos.get(i).getTipoPDF());
                        }

                        if (fElement.getName().equals("okp:libro.estampaTiempo")) {
                            Input name = (Input) fElement;
                            name.setValue("" + estampaTiempo);
                        }
                        if (fElement.getName().equals("okp:libro.nombreAutor")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.pubNombreAutor);
                        }
                        if (fElement.getName().equals("okp:libro.autoresSecundarios")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.pubAutoresSecundarios);
                        }

                        SimpleDateFormat formateador = new SimpleDateFormat("MM-yyyy");
                        String FechaPublicacion = formateador.format(this.pubFechaPublicacion);

                        if (fElement.getName().equals("okp:libro.fechaPublicacion")) {
                            Input name = (Input) fElement;
                            name.setValue("" + FechaPublicacion);
                        }
                        if (fElement.getName().equals("okp:libro.tipoPublicacion")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.pubTipoPublicacion);
                        }
                        if (fElement.getName().equals("okp:libro.tituloLibro")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.libro.getLibTituloLibro());
                        }
                        if (fElement.getName().equals("okp:libro.DOI")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.pubDoi);
                        }
                        if (fElement.getName().equals("okp:libro.ISBN")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.pubIsbn);
                        }
                        if (fElement.getName().equals("okp:libro.ISSN")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.pubIssn);
                        }
                    }
                    ws.setPropertyGroupProperties("" + rutaFolderCrear + "/" + subidaArchivos.get(i).getNombreArchivo() + ".pdf", "okg:libro", fElements);

                }

                File fichero = new File(subidaArchivos.get(i).getRutaArchivo());
                fichero.delete();

            }

        } catch (AccessDeniedException | AutomationException | DatabaseException | ExtensionException | FileSizeExceededException | ItemExistsException | LockException | NoSuchGroupException | NoSuchPropertyException | ParseException | PathNotFoundException | RepositoryException | UnknowException | UnsupportedMimeTypeException | UserQuotaExceededException | VirusDetectedException | WebserviceException | IOException e) {
            e.printStackTrace();
        }

    }

    private void CrearPDFA_Metadata(ArrayList<tipoPDF_cargar> subidaArchivos, String estampaTiempo) {

        for (int s = 0; s < subidaArchivos.size(); s++) {

            Document document = new Document();
            PdfReader reader;

            try {
                PdfAWriter writer = PdfAWriter.getInstance(document, new FileOutputStream(
                        subidaArchivos.get(s).getRutaArchivo()), PdfAConformanceLevel.PDF_A_1B);
                writer.setTagged();

                SimpleDateFormat formateador = new SimpleDateFormat("MM-yyyy");
                String FechaPublicacion = formateador.format(this.pubFechaPublicacion);

                Archivo arch = (Archivo) archivoCollection.toArray()[s];

                document.addHeader("Identificador Publicacion", "" + this.pubIdentificador);
                document.addHeader("Identificador Archivo", "" + arch.getArcIdentificador());
                document.addHeader("tipoPDF_cargar", "" + subidaArchivos.get(s).getTipoPDF());
                document.addHeader("Estampa Tiempo", "" + estampaTiempo);
                document.addAuthor("" + this.pubNombreAutor);
                document.addCreator("" + this.pubNombreAutor);
                try{
                document.addHeader("Autores_Secundarios", this.pubAutoresSecundarios);
                }catch(Exception e){
                    int o = 0;
                }
                document.addHeader("Fecha_Publicacion", FechaPublicacion);
                document.addHeader("Tipo_Publicacion", this.pubTipoPublicacion);
                /* Se Comprubea si los metadatos a llenar son para una revista
                    un congreso , un libro o un capitulo de un libro*/
                if (this.pubTipoPublicacion.equalsIgnoreCase("revista")) {
                    document.addTitle("" + this.revista.getRevTituloArticulo());
                    document.addHeader("Nombre_Revista", this.revista.getRevNombreRevista());
                    document.addHeader("Categoria", this.revista.getRevCategoria());

                }
                if (this.pubTipoPublicacion.equalsIgnoreCase("congreso")) {
                    document.addTitle("" + this.congreso.getCongTituloPonencia());
                    document.addHeader("Nombre_Evento", this.congreso.getCongNombreEvento());
                    document.addHeader("Tipo_Congreso", this.congreso.getCongTipoCongreso());

                }
                if (this.pubTipoPublicacion.equalsIgnoreCase("libro")) {
                    document.addTitle("" + this.libro.getLibTituloLibro());
                    document.addHeader("Titulo_Libro", this.libro.getLibTituloLibro());
                }
                if (this.pubTipoPublicacion.equalsIgnoreCase("capitulo_libro")) {
                    document.addTitle("" + this.capituloLibro.getCaplibTituloLibro());
                    document.addHeader("Titulo_Libro", this.capituloLibro.getCaplibTituloLibro());
                    document.addHeader("Titulo_Capitulo", this.capituloLibro.getCaplibTituloCapitulo());
                }

                if (this.pubDoi == null) {
                    this.pubDoi = "";
                }
                if (this.pubIsbn == null) {
                    this.pubIsbn = "";
                }
                if (this.pubIssn == null) {
                    this.pubIssn = "";
                }
                document.addHeader("DOI", this.pubDoi);
                document.addHeader("ISBN", this.pubIsbn);
                document.addHeader("ISSN", this.pubIssn);
                document.addCreationDate();

                writer.setTagged();
                writer.createXmpMetadata();
                writer.setCompressionLevel(9);
                document.open();
                PdfContentByte cb = writer.getDirectContent();
                reader = new PdfReader(subidaArchivos.get(s).getArchivoIS());
                PdfImportedPage page;
                int pageCount = reader.getNumberOfPages();
                for (int i = 0; i < pageCount; i++) {
                    document.newPage();
                    page = writer.getImportedPage(reader, i + 1);
                    cb.addTemplate(page, 0, 0);
                }
            } catch (DocumentException | IOException de) {
                System.err.println(de.getMessage());
            }
            document.close();

        }
    }

    public archivoPDF descargaCartaAprobac() {
        archivoPDF archivo = new archivoPDF();
        String tipoPDF = "cartaAprobacion";

        String host = "http://localhost:8083/OpenKM";
        //String host = "http://wmyserver.sytes.net:8083/OpenKM";
        String username = "okmAdmin";
        String password = "admin";
        OKMWebservices ws = OKMWebservicesFactory.newInstance(host, username, password);

        try {

            Map<String, String> properties = new HashMap();
            /* Se comprueba el tipo de publicacion: revista congreso , un libro 
                o un capitulo de un libro que se devolvera como resultado*/
            if (this.pubTipoPublicacion.equalsIgnoreCase("revista")) {
                properties.put("okp:revista.identPublicacion", "" + this.pubIdentificador);
                properties.put("okp:revista.tipoPDFCargar", "" + tipoPDF);

            }
            if (this.pubTipoPublicacion.equalsIgnoreCase("congreso")) {
                properties.put("okp:congreso.identPublicacion", "" + this.pubIdentificador);
                properties.put("okp:congreso.tipoPDFCargar", "" + tipoPDF);

            }
            if (this.pubTipoPublicacion.equalsIgnoreCase("libro")) {
                properties.put("okp:libro.identPublicacion", "" + this.pubIdentificador);
                properties.put("okp:libro.tipoPDFCargar", "" + tipoPDF);
            }
            if (this.pubTipoPublicacion.equalsIgnoreCase("capitulo_libro")) {
                properties.put("okp:capLibro.identPublicacion", "" + this.pubIdentificador);
                properties.put("okp:capLibro.tipoPDFCargar", "" + tipoPDF);
            }
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
        return archivo;
    }

    public archivoPDF descargaPublicacion() {
        archivoPDF archivo = new archivoPDF();
        String tipoPDF = "tipoPublicacion";

        String host = "http://localhost:8083/OpenKM";
         //String host = "http://wmyserver.sytes.net:8083/OpenKM";
        String username = "okmAdmin";
        String password = "admin";
        OKMWebservices ws = OKMWebservicesFactory.newInstance(host, username, password);

        try {

            Map<String, String> properties = new HashMap();
            /* Se comprueba el tipo de publicacion: revista congreso , un libro 
                o un capitulo de un libro que se devolvera como resultado*/
            if (this.pubTipoPublicacion.equalsIgnoreCase("revista")) {
                properties.put("okp:revista.identPublicacion", "" + this.pubIdentificador);
                properties.put("okp:revista.tipoPDFCargar", "" + tipoPDF);

            }
            if (this.pubTipoPublicacion.equalsIgnoreCase("congreso")) {
                properties.put("okp:congreso.identPublicacion", "" + this.pubIdentificador);
                properties.put("okp:congreso.tipoPDFCargar", "" + tipoPDF);

            }
            if (this.pubTipoPublicacion.equalsIgnoreCase("libro")) {
                properties.put("okp:libro.identPublicacion", "" + this.pubIdentificador);
                properties.put("okp:libro.tipoPDFCargar", "" + tipoPDF);
            }
            if (this.pubTipoPublicacion.equalsIgnoreCase("capitulo_libro")) {
                properties.put("okp:capLibro.identPublicacion", "" + this.pubIdentificador);
                properties.put("okp:capLibro.tipoPDFCargar", "" + tipoPDF);
            }
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
        return archivo;
    }

    public archivoPDF descargaPubTC() {
        archivoPDF archivo = new archivoPDF();
        String tipoPDF = "tablaContenido";

        String host = "http://localhost:8083/OpenKM";
         //String host = "http://wmyserver.sytes.net:8083/OpenKM";
        String username = "okmAdmin";
        String password = "admin";
        OKMWebservices ws = OKMWebservicesFactory.newInstance(host, username, password);

        try {

            Map<String, String> properties = new HashMap();
            /* Se comprueba el tipo de publicacion: revista congreso , un libro 
                o un capitulo de un libro que se devolvera como resultado*/
            if (this.pubTipoPublicacion.equalsIgnoreCase("revista")) {
                properties.put("okp:revista.identPublicacion", "" + this.pubIdentificador);
                properties.put("okp:revista.tipoPDFCargar", "" + tipoPDF);

            }
            if (this.pubTipoPublicacion.equalsIgnoreCase("congreso")) {
                properties.put("okp:congreso.identPublicacion", "" + this.pubIdentificador);
                properties.put("okp:congreso.tipoPDFCargar", "" + tipoPDF);

            }
            if (this.pubTipoPublicacion.equalsIgnoreCase("libro")) {
                properties.put("okp:libro.identPublicacion", "" + this.pubIdentificador);
                properties.put("okp:libro.tipoPDFCargar", "" + tipoPDF);
            }
            if (this.pubTipoPublicacion.equalsIgnoreCase("capitulo_libro")) {
                properties.put("okp:capLibro.identPublicacion", "" + this.pubIdentificador);
                properties.put("okp:capLibro.tipoPDFCargar", "" + tipoPDF);
            }
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
        return archivo;
    }

    public String obtenerNombrePub() {
        String nombrePub = "";

        if (this.pubTipoPublicacion.equalsIgnoreCase("revista")) {
            nombrePub = this.getRevista().getRevTituloArticulo();
        }
        if (this.pubTipoPublicacion.equalsIgnoreCase("congreso")) {
            nombrePub = this.getCongreso().getCongTituloPonencia();
        }
        if (this.pubTipoPublicacion.equalsIgnoreCase("libro")) {
            nombrePub = this.getLibro().getLibTituloLibro();
        }
        if (this.pubTipoPublicacion.equalsIgnoreCase("capitulo_libro")) {
            nombrePub = this.getCapituloLibro().getCaplibTituloCapitulo();
        }

        return nombrePub;
    }

    public Integer getPubIdentificador() {
        return pubIdentificador;
    }

    public void setPubIdentificador(Integer pubIdentificador) {
        this.pubIdentificador = pubIdentificador;
    }

    public String getPubHash() {
        return pubHash;
    }

    public void setPubHash(String pubHash) {
        this.pubHash = pubHash;
    }

    public String getPubDiropkm() {
        return pubDiropkm;
    }

    public void setPubDiropkm(String pubDiropkm) {
        this.pubDiropkm = pubDiropkm;
    }

    public Integer getPubCreditos() {
        return pubCreditos;
    }

    public void setPubCreditos(Integer pubCreditos) {
        this.pubCreditos = pubCreditos;
    }

    public Date getPubFechaVisado() {
        return pubFechaVisado;
    }

    public void setPubFechaVisado(Date pubFechaVisado) {
        this.pubFechaVisado = pubFechaVisado;
    }

    public Date getPubFechaRegistro() {
        return pubFechaRegistro;
    }

    public void setPubFechaRegistro(Date pubFechaRegistro) {
        this.pubFechaRegistro = pubFechaRegistro;
    }

    public String getPubEstado() {
        return pubEstado;
    }

    public void setPubEstado(String pubEstado) {
        this.pubEstado = pubEstado;
    }

    public String getPubNombreAutor() {
        return pubNombreAutor;
    }

    public void setPubNombreAutor(String pubNombreAutor) {
        this.pubNombreAutor = pubNombreAutor;
    }

    public String getPubAutoresSecundarios() {
        return pubAutoresSecundarios;
    }

    public void setPubAutoresSecundarios(String pubAutoresSecundarios) {
        this.pubAutoresSecundarios = pubAutoresSecundarios;
    }

    public String getPubTipoPublicacion() {
        return pubTipoPublicacion;
    }

    public void setPubTipoPublicacion(String pubTipoPublicacion) {
        this.pubTipoPublicacion = pubTipoPublicacion;
    }

    public Date getPubFechaPublicacion() {

        FormatoFechas fecha = new FormatoFechas(pubFechaPublicacion);
        return fecha;
        //       return pubFechaPublicacion;
    }

    public void setPubFechaPublicacion(Date pubFechaPublicacion) {
        this.pubFechaPublicacion = pubFechaPublicacion;
    }

    public Integer getPubNumActa() {
        return pubNumActa;
    }

    public void setPubNumActa(Integer pubNumActa) {
        this.pubNumActa = pubNumActa;
    }

  
 

    
    public String getPubDoi() {
        return pubDoi;
    }

    public void setPubDoi(String pubDoi) {
        this.pubDoi = pubDoi;
    }

    public String getPubIsbn() {
        return pubIsbn;
    }

    public void setPubIsbn(String pubIsbn) {
        this.pubIsbn = pubIsbn;
    }

    public String getPubIssn() {
        return pubIssn;
    }

    public String getPubVisado() {
        return pubVisado;
    }

    public void setPubVisado(String pubVisado) {
        this.pubVisado = pubVisado;
    }

    public void setPubIssn(String pubIssn) {
        this.pubIssn = pubIssn;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    @XmlTransient
    public Collection<Archivo> getArchivoCollection() {
        return archivoCollection;
    }

    public void setArchivoCollection(Collection<Archivo> archivoCollection) {
        this.archivoCollection = archivoCollection;
    }

    public Congreso getCongreso() {
        return congreso;
    }

    public void setCongreso(Congreso congreso) {
        this.congreso = congreso;
    }

    public Revista getRevista() {
        return revista;
    }

    public void setRevista(Revista revista) {
        this.revista = revista;
    }

    public CapituloLibro getCapituloLibro() {
        return capituloLibro;
    }

    public void setCapituloLibro(CapituloLibro capituloLibro) {
        this.capituloLibro = capituloLibro;
    }

    public Estudiante getPubEstIdentificador() {
        return pubEstIdentificador;
    }

    public void setPubEstIdentificador(Estudiante pubEstIdentificador) {
        this.pubEstIdentificador = pubEstIdentificador;
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
        if (!(object instanceof Publicacion)) {
            return false;
        }
        Publicacion other = (Publicacion) object;
        if ((this.pubIdentificador == null && other.pubIdentificador != null) || (this.pubIdentificador != null && !this.pubIdentificador.equals(other.pubIdentificador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.unicauca.proyectobase.entidades.Publicacion[ pubIdentificador=" + pubIdentificador + " ]";
    }

}
