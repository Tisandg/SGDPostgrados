package co.unicauca.proyectobase.entidades;

import co.unicauca.proyectobase.controladores.OpenKMController;
import co.unicauca.proyectobase.utilidades.PropiedadesOS;
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
 * @author Unicauca
 */
@Entity
@Table(name = "publicacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Publicacion.findAll", query = "SELECT p FROM Publicacion p"),
    @NamedQuery(name = "Publicacion.findByPubIdentificador", query = "SELECT p FROM Publicacion p WHERE p.pubIdentificador = :pubIdentificador"),
    @NamedQuery(name = "Publicacion.findByPubHash", query = "SELECT p FROM Publicacion p WHERE p.pubHash = :pubHash"),
    @NamedQuery(name = "Publicacion.findByPubDiropkm", query = "SELECT p FROM Publicacion p WHERE p.pubDiropkm = :pubDiropkm"),
    @NamedQuery(name = "Publicacion.findByPubFechaVisado", query = "SELECT p FROM Publicacion p WHERE p.pubFechaVisado = :pubFechaVisado"),
    @NamedQuery(name = "Publicacion.findByPubFechaRegistro", query = "SELECT p FROM Publicacion p WHERE p.pubFechaRegistro = :pubFechaRegistro"),
    @NamedQuery(name = "Publicacion.findByPubEstado", query = "SELECT p FROM Publicacion p WHERE p.pubEstado = :pubEstado"),
    @NamedQuery(name = "Publicacion.findByPubAutoresSecundarios", query = "SELECT p FROM Publicacion p WHERE p.pubAutoresSecundarios = :pubAutoresSecundarios"),
    @NamedQuery(name = "Publicacion.findByPubFechaPublicacion", query = "SELECT p FROM Publicacion p WHERE p.pubFechaPublicacion = :pubFechaPublicacion"),
    @NamedQuery(name = "Publicacion.findByPubNumActa", query = "SELECT p FROM Publicacion p WHERE p.pubNumActa = :pubNumActa"),
    @NamedQuery(name = "Publicacion.findByPubVisado", query = "SELECT p FROM Publicacion p WHERE p.pubVisado = :pubVisado"),
    @NamedQuery(name = "Publicacion.findByOnlyPublicacion", query = "SELECT p FROM Publicacion p WHERE p.idTipoDocumento.identificador IN (1,2,3,4)"),
    //consutas nuevas
    @NamedQuery(name = "Publicacion.findAllByYear", query = "SELECT p FROM Publicacion p "
            + "WHERE FUNC('YEAR',p.pubFechaRegistro) = :anio ORDER BY  p.idTipoDocumento.nombre DESC"),
    
    @NamedQuery(name = "Publicacion.findAllBySemester", query = "SELECT p FROM Publicacion p "
            + "WHERE FUNC('YEAR',p.pubFechaRegistro) = :anio AND FUNC('MONTH',p.pubFechaRegistro) Between :inicio AND :fin "
            + "ORDER BY  p.idTipoDocumento.nombre DESC"),
    
    @NamedQuery(name = "Publicacion.StudentPublications_Year", query = "SELECT p FROM Publicacion p "
            + "WHERE p.pubEstIdentificador.estIdentificador = :identificador AND FUNC('YEAR',p.pubFechaRegistro) = :anio "
            + "ORDER BY  p.idTipoDocumento.nombre DESC"),
    
    @NamedQuery(name = "Publicacion.StudentPublications_Semester", query = "SELECT p FROM Publicacion p "
            + "WHERE p.pubEstIdentificador.estIdentificador = :identificador AND FUNC('YEAR',p.pubFechaRegistro) = :anio "
            + "AND FUNC('MONTH',p.pubFechaRegistro) Between :inicio AND :fin ORDER BY  p.idTipoDocumento.nombre DESC"),

    @NamedQuery(name = "Publicacion.findAllEst", query = "SELECT p FROM Publicacion p WHERE  p.pubEstIdentificador.estNombre LIKE :variableFiltro OR p.idTipoDocumento.nombre LIKE :variableFiltro")
    , @NamedQuery(name = "Publicacion.findAllRev", query = "SELECT p FROM Publicacion p WHERE  p.pubEstIdentificador.estNombre LIKE :variableFiltro OR p.idTipoDocumento.nombre LIKE :variableFiltro OR p.revista.revTituloArticulo LIKE :variableFiltro")
    , @NamedQuery(name = "Publicacion.findAllCong", query = "SELECT p FROM Publicacion p WHERE  p.pubEstIdentificador.estNombre LIKE :variableFiltro OR p.idTipoDocumento.nombre LIKE :variableFiltro OR p.congreso.congTituloPonencia LIKE :variableFiltro")
    , @NamedQuery(name = "Publicacion.findAllLib", query = "SELECT p FROM Publicacion p WHERE  p.pubEstIdentificador.estNombre LIKE :variableFiltro OR p.idTipoDocumento.nombre LIKE :variableFiltro OR p.libro.libTituloLibro LIKE :variableFiltro")
    , @NamedQuery(name = "Publicacion.findAllCapLib", query = "SELECT p FROM Publicacion p WHERE  p.pubEstIdentificador.estNombre LIKE :variableFiltro OR p.idTipoDocumento.nombre LIKE :variableFiltro OR p.capituloLibro.caplibTituloCapitulo LIKE :variableFiltro")

    , @NamedQuery(name = "Publicacion.findAllByRev", query = "SELECT p FROM Publicacion p WHERE  (p.pubEstIdentificador.estIdentificador =:identificacion) AND (p.pubEstIdentificador.estNombre LIKE :variableFiltro OR p.idTipoDocumento.nombre LIKE :variableFiltro OR p.revista.revTituloArticulo LIKE :variableFiltro)")
    , @NamedQuery(name = "Publicacion.findAllByCong", query = "SELECT p FROM Publicacion p WHERE  (p.pubEstIdentificador.estIdentificador =:identificacion) AND (p.pubEstIdentificador.estNombre LIKE :variableFiltro OR p.idTipoDocumento.nombre LIKE :variableFiltro OR p.congreso.congTituloPonencia LIKE :variableFiltro)")
    , @NamedQuery(name = "Publicacion.findAllByLib", query = "SELECT p FROM Publicacion p WHERE  (p.pubEstIdentificador.estIdentificador =:identificacion) AND (p.pubEstIdentificador.estNombre LIKE :variableFiltro OR p.idTipoDocumento.nombre LIKE :variableFiltro OR p.libro.libTituloLibro LIKE :variableFiltro)")
    , @NamedQuery(name = "Publicacion.findAllByCapLib", query = "SELECT p FROM Publicacion p WHERE  (p.pubEstIdentificador.estIdentificador =:identificacion) AND (p.pubEstIdentificador.estNombre LIKE :variableFiltro OR p.idTipoDocumento.nombre LIKE :variableFiltro OR p.capituloLibro.caplibTituloCapitulo LIKE :variableFiltro )"),

    @NamedQuery(name = "Publicacion.findAllFiltPubEst", query = "SELECT p FROM Publicacion p WHERE  "
            + "(p.pubEstIdentificador.estIdentificador =:identificacion) AND "
            + "(p.pubEstIdentificador.estNombre LIKE :variableFiltro OR p.idTipoDocumento.nombre "
            + "LIKE :variableFiltro OR p.revista.revTituloArticulo LIKE :variableFiltro OR p.capituloLibro.caplibTituloCapitulo "
            + "LIKE :variableFiltro)"),
    @NamedQuery(
            name = "findAllPub_Est",
            query = "SELECT p FROM Publicacion p WHERE p.pubEstIdentificador.estIdentificador = :identificacion and p.idTipoDocumento.identificador"
                    + " IN (1,2,3,4)"
    ),
    @NamedQuery(name = "Publicacion.updateVisadoById", query = "UPDATE Publicacion as p SET p.pubVisado = :valorVisado where p.pubIdentificador = :id"),
    @NamedQuery(name = "Publicacion.findIdTipoDocumento", query = "SELECT td FROM TipoDocumento td WHERE td.nombre like :tipoDoc")

})
public class Publicacion implements Serializable {

    @Column(name = "pub_creditos")
    private Integer pubCreditos;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "publicacion")
    private PracticaDocente practicaDocente;

    @JoinColumn(name = "id_tipo_documento", referencedColumnName = "identificador")
    @ManyToOne
    private TipoDocumento idTipoDocumento;

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
    @Column(name = "pub_fecha_visado")
    @Temporal(TemporalType.DATE)
    private Date pubFechaVisado;
    @Column(name = "pub_fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date pubFechaRegistro;
    @Size(max = 15)
    @Column(name = "pub_estado")
    private String pubEstado;
    @Size(max = 300)
    @Column(name = "pub_autores_secundarios")
    private String pubAutoresSecundarios;
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
        this.libro = new Libro();
        this.congreso = new Congreso();
        this.capituloLibro = new CapituloLibro();
        this.revista = new Revista();
    }

    public Publicacion(Integer pubIdentificador) {
        this.pubIdentificador = pubIdentificador;
        this.pubAutoresSecundarios = "";
    }

    public Publicacion(Integer pubIdentificador, Date pubFechaPublicacion) {
        this.pubIdentificador = pubIdentificador;
        //this.pubTipoPublicacion = pubTipoPublicacion;
        this.pubFechaPublicacion = pubFechaPublicacion;
        this.pubAutoresSecundarios = "";
    }
    

    /***
     * Funcion para crear los documentos pdf con los metadatos
     * @param ArticuloPDF
     * @param TablaContenidoPDF
     * @param cartaAprobacionPDF
     * @return
     * @throws IOException 
     */
    @SuppressWarnings("empty-statement")
    public boolean agregarMetadatos(UploadedFile ArticuloPDF, UploadedFile TablaContenidoPDF, UploadedFile cartaAprobacionPDF) throws IOException {

        boolean archivosSubidos = false;
        /*Nombre de los archivos que se almacenaran en el repositorio*/
        MetodosPDF mpdf = new MetodosPDF();
        String codigoEst = this.pubEstIdentificador.getEstCodigo();
        String codigoFirma = mpdf.codigoFirma(codigoEst);
        codigoFirma = codigoFirma.trim();

        String nombreCartaAprob = "Carta de Aprobacion-" + codigoFirma;
        String nombrePublicacion = "";
        String nombreTablaC = "Tabla de Contenido-" + codigoFirma;

        nombrePublicacion = obtenerNombrePub();
            
        PropiedadesOS os = new PropiedadesOS();
        /*Obtiene la ruta de la ubicacion del servidor donde se almacenaran 
          temporalmente los archivos ,para luego subirlos al Gestgor Documental OpenKm  */
        String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(os.getSeparator());        
        
        String destCartaAprob = realPath + "WEB-INF"+ os.getSeparator() +"temp" + os.getSeparator() + nombreCartaAprob + ".pdf";
        String destNombrePub = realPath + "WEB-INF"+ os.getSeparator() + "temp" + os.getSeparator() + nombrePublicacion + ".pdf";
        String destTablaC = realPath + "WEB-INF"+ os.getSeparator() +"temp" + os.getSeparator() + nombreTablaC + ".pdf";


        /*  Estampa de Tiempo
            Obtiene el dia y hora actual del servidor para posteriormente adicionarlo
            como Metadato en el documento PDF/A y el Gestor Documental*/
        Date date = new Date();
        DateFormat datehourFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String estampaTiempo = "" + datehourFormat.format(date);
        this.setPubFechaRegistro(date);

        /*  Metodo para almacenar los metadatos de la Carte de Aprobacion , Articulo y Tabla de Contenido 
            para almacenarlo en formato PDFA */
        ArrayList<tipoPDF_cargar> archivoParaSubir = new ArrayList<>();

        if (!cartaAprobacionPDF.getFileName().equalsIgnoreCase("")) {
            tipoPDF_cargar cartaAprobacion = new tipoPDF_cargar(nombreCartaAprob,destCartaAprob,
                    "cartaAprobacion",cartaAprobacionPDF.getInputstream());
            archivoParaSubir.add(cartaAprobacion);;
        }

        if (!ArticuloPDF.getFileName().equalsIgnoreCase("")) {
            tipoPDF_cargar articulo = new tipoPDF_cargar(nombrePublicacion,destNombrePub,
                    "tipoPublicacion",ArticuloPDF.getInputstream());
            archivoParaSubir.add(articulo);
        }
        if (!TablaContenidoPDF.getFileName().equalsIgnoreCase("")) {
            tipoPDF_cargar tablaContenido = new tipoPDF_cargar(nombreTablaC,destTablaC,
                    "tablaContenido",TablaContenidoPDF.getInputstream());
            archivoParaSubir.add(tablaContenido);
        }

        /*Creamos los pdf con los metadatos*/
        CrearPDFA_Metadata(archivoParaSubir);

        /*Obtenemos una llave hash para nuesta publicacion*/
        String hash = mpdf.obtenerHash(destNombrePub);

        /* Metodo para almacenar en el Gestor Documental(OPENKM), carta de aprobacion,
           el articulo en formato y la Tabla de Contenido del Articulo, todos en formato
           PDFA*/
        if(SubirOpenKM(archivoParaSubir, codigoFirma, hash)){
            System.out.println("Los archivos se han subido a openKM");
            archivosSubidos = true;
        }else{
            System.out.println("Error al subir los archivos a openKm");
        }        
        return archivosSubidos;
    }
    
    public void AgregarPracticaDocente(UploadedFile ArchivoPD) throws IOException
    {
        MetodosPDF mpdf = new MetodosPDF();
        String codigoEst = this.pubEstIdentificador.getEstCodigo();
        String codigoFirma = mpdf.codigoFirma(codigoEst);
        codigoFirma = codigoFirma.trim();

        PropiedadesOS os = new PropiedadesOS();
        String nombrePD = "Practica Docente-" + codigoFirma;
        String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(os.getSeparator());
        String destPD = realPath + "WEB-INF"+ os.getSeparator() +"temp" + os.getSeparator() + nombrePD + ".pdf";
        Date date = new Date();
        DateFormat datehourFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String estampaTiempo = "" + datehourFormat.format(date);
        this.setPubFechaRegistro(date);
        
        ArrayList<tipoPDF_cargar> subidaArchivos = new ArrayList<>();
         if (!ArchivoPD.getFileName().equalsIgnoreCase("")) {
            tipoPDF_cargar practicadocente = new tipoPDF_cargar();
            practicadocente.setNombreArchivo(nombrePD);
            practicadocente.setRutaArchivo(destPD);
            practicadocente.setTipoPDF("practicaDocente");
            practicadocente.setArchivoIS(ArchivoPD.getInputstream());
            subidaArchivos.add(practicadocente);
        }
         
         CrearPDFA_MetadataPD(subidaArchivos, estampaTiempo);
         String hash = mpdf.obtenerHash(destPD);
         SubirOpenKMPD(subidaArchivos, estampaTiempo, codigoFirma, hash); 
        
        
    }

    /***
     * Metodo para subir los archivos al gestor de documentos(OpenKm). Se crea 
     * la carpeta en el gestor, se crean los documentos y se fijan los metadatos
     * @param subidaArchivos
     * @param codigoFirma
     * @param hash
     * @throws IOException 
     */
    public boolean SubirOpenKM(ArrayList<tipoPDF_cargar> subidaArchivos, String codigoFirma, String hash) throws IOException {

        boolean archivosSubidos = false;
        /* Inicia una instancia del Gestor Documental Openkm*/
        this.setPubHash(hash);
        OKMWebservices ws = OKMWebservicesFactory.newInstance(OpenKMController.host , OpenKMController.username, OpenKMController.password);
        try {
            boolean crearFolder;
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
            } catch (PathNotFoundException | AccessDeniedException | RepositoryException | DatabaseException | UnknowException | WebserviceException e) {
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
            } catch (PathNotFoundException | AccessDeniedException | RepositoryException | DatabaseException | UnknowException | WebserviceException e) {
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
                InputStream is = new FileInputStream("" + subidaArchivos.get(i).getRutaArchivo());
                com.openkm.sdk4j.bean.Document doc = new com.openkm.sdk4j.bean.Document();
                doc.setPath(rutaFolderCrear + "/" + subidaArchivos.get(i).getNombreArchivo() + ".pdf");
                ws.createDocument(doc, is);
                IOUtils.closeQuietly(is);
                
                /* Se Comprubea si los  Metadatos en OpenKm que se van  a llenar son para 
                 una revista, un congreso , un libro o un capitulo de un libro*/
                if (this.idTipoDocumento.getIdentificador() == 4) {
                    
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
                            name.setValue("" + this.pubFechaRegistro);
                        }
                        if (fElement.getName().equals("okp:revista.nombreAutor")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.pubEstIdentificador.getEstNombre());
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
                            name.setValue("" + this.idTipoDocumento.getNombre());
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
                            name.setValue("" + this.revista.getRevDoi());
                        }

                    }
                    ws.setPropertyGroupProperties("" + rutaFolderCrear + "/" + subidaArchivos.get(i).getNombreArchivo() + ".pdf", "okg:revista", fElements);
                }

                if (this.idTipoDocumento.getIdentificador() == 3) {

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
                            name.setValue("" + this.pubFechaRegistro);
                        }
                        if (fElement.getName().equals("okp:congreso.nombreAutor")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.pubEstIdentificador.getEstNombre());
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
                            name.setValue("" + this.idTipoDocumento.getNombre());
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
                            name.setValue("" + this.congreso.getCongDoi());
                        }
                        if (fElement.getName().equals("okp:congreso.ISSN")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.congreso.getCongIssn());
                        }
                        /* Ciudad y País */
                        if (fElement.getName().equals("okp:congreso.ciudadCongreso")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.congreso.getCiudadId().getCiudNombre());
                        }
                        
                        if (fElement.getName().equals("okp:congreso.paisCongreso")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.congreso.getCiudadId().getPaisId().getPaisNombre());
                        }
                        
                        SimpleDateFormat formateado = new SimpleDateFormat("MM-yyyy");
                        String fIni = formateado.format(this.congreso.getFechaInicio());
                        String fFin = formateado.format(this.congreso.getFechaFin());
                        
                        if (fElement.getName().equals("okp:congreso.fechaIniCongreso")) {
                            Input name = (Input) fElement;
                            name.setValue(""+fIni);
                        }
                        
                        if (fElement.getName().equals("okp:congreso.fechaFinCongreso")) {
                            Input name = (Input) fElement;
                            name.setValue(""+fFin);
                        }
                    }
                    ws.setPropertyGroupProperties("" + rutaFolderCrear + "/" + subidaArchivos.get(i).getNombreArchivo() + ".pdf", "okg:congreso", fElements);
                }

                if (this.idTipoDocumento.getIdentificador() == 2) {

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
                            name.setValue("" + this.pubFechaRegistro);
                        }
                        if (fElement.getName().equals("okp:capLibro.nombreAutor")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.pubEstIdentificador.getEstNombre());
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
                            name.setValue("" + this.idTipoDocumento.getNombre());
                        }
                        if (fElement.getName().equals("okp:capLibro.tituloLibro")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.capituloLibro.getCaplibTituloLibro());
                        }
                        if (fElement.getName().equals("okp:capLibro.tituloCapitulo")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.capituloLibro.getCaplibTituloCapitulo());
                        }
                        if (fElement.getName().equals("okp:capLibro.ISBN")) {
                            Input name = (Input) fElement;
                            name.setValue(this.capituloLibro.getCaplibIsbn());
                        }
                    }
                    ws.setPropertyGroupProperties("" + rutaFolderCrear + "/" + subidaArchivos.get(i).getNombreArchivo() + ".pdf", "okg:capLibro", fElements);

                }

                if (this.idTipoDocumento.getIdentificador() == 1) {

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
                            name.setValue("" + this.pubFechaRegistro);
                        }
                        if (fElement.getName().equals("okp:libro.nombreAutor")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.pubEstIdentificador.getEstNombre());
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
                            name.setValue("" + this.idTipoDocumento.getNombre());
                        }
                        if (fElement.getName().equals("okp:libro.tituloLibro")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.libro.getLibTituloLibro());
                        }                                                                        
                        if(fElement.getName().equals("okp:libro.editorialLibro")){
                            Input name = (Input) fElement;
                            name.setValue(this.libro.getEditorial().toUpperCase());
                        }
                        if(fElement.getName().equals("okp:libro.ciudadLibro"))//Cambio
                        {
                            Input name = (Input) fElement;
                            name.setValue("" + this.libro.getCiudadId().getCiudNombre());
                        }
                        if(fElement.getName().equals("okp:libro.paisLibro"))//Cambio
                        {
                            Input name = (Input) fElement;
                            name.setValue("" + this.libro.getCiudadId().getPaisId().getPaisNombre());
                        }
                        if (fElement.getName().equals("okp:libro.ISBN")) {
                            Input name = (Input) fElement;
                            name.setValue(this.libro.getLibIsbn());
                        }
                    }
                    ws.setPropertyGroupProperties("" + rutaFolderCrear + "/" + subidaArchivos.get(i).getNombreArchivo() + ".pdf", "okg:libro", fElements);
                }
                File fichero = new File(subidaArchivos.get(i).getRutaArchivo());
                fichero.delete();
            }
            archivosSubidos = true;

        } catch (AccessDeniedException | AutomationException | DatabaseException | ExtensionException | FileSizeExceededException | ItemExistsException | LockException | NoSuchGroupException | NoSuchPropertyException | ParseException | PathNotFoundException | RepositoryException | UnknowException | UnsupportedMimeTypeException | UserQuotaExceededException | VirusDetectedException | WebserviceException | IOException e) {
            System.out.println("error en subirOpenKM clase publicacion.java "+e.getMessage());
        }
        return archivosSubidos;
    }

    /**
     * Crea los archivos pdf con los metadatos que son subidos al gestor openKm.
     * @param subidaArchivos
     * @param estampaTiempo
     * @param pubDoi
     * @param pubIsbn
     * @param pubIssn 
     */
    private void CrearPDFA_Metadata(ArrayList<tipoPDF_cargar> subidaArchivos) {

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
                document.addHeader("Estampa Tiempo", "" + this.pubFechaRegistro);
                document.addAuthor("" + this.pubEstIdentificador.getEstNombre());
                document.addCreator("" + this.pubEstIdentificador.getEstNombre());
                try{
                    document.addHeader("Autores_Secundarios", this.pubAutoresSecundarios);
                }catch(Exception e){
                    System.out.println("Error agregando autores secundarios a metadatos"+ e.getMessage());
                }
                document.addHeader("Fecha_Publicacion", FechaPublicacion);
                document.addHeader("Tipo_Publicacion", this.idTipoDocumento.getNombre());
                
                /* Se Comprubea si los metadatos a llenar son para una revista
                    un congreso , un libro o un capitulo de un libro*/
                if (this.idTipoDocumento.getIdentificador() == 4) {
                    document.addTitle("" + this.revista.getRevTituloArticulo());
                    document.addHeader("Nombre_Revista", this.revista.getRevNombreRevista());
                    document.addHeader("Categoria", this.revista.getRevCategoria());
                    document.addHeader("DOI", this.revista.getRevDoi());
                }
                if (this.idTipoDocumento.getIdentificador() == 3) {
                    document.addTitle("" + this.congreso.getCongTituloPonencia());
                    document.addHeader("Nombre_Evento", this.congreso.getCongNombreEvento());
                    document.addHeader("Tipo_Congreso", this.congreso.getCongTipoCongreso());
                    document.addHeader("DOI", this.congreso.getCongDoi());
                    document.addHeader("ISSN",this.congreso.getCongIssn());
                }
                
                if (this.idTipoDocumento.getIdentificador() == 2) {
                    document.addTitle("" + this.capituloLibro.getCaplibTituloLibro());
                    document.addHeader("Titulo_Libro", this.capituloLibro.getCaplibTituloLibro());
                    document.addHeader("Titulo_Capitulo", this.capituloLibro.getCaplibTituloCapitulo());
                    document.addHeader("ISBN",this.capituloLibro.getCaplibIsbn());
                }
                
                if (this.idTipoDocumento.getIdentificador() == 1) {
                    document.addTitle("" + this.libro.getLibTituloLibro());
                    document.addHeader("Titulo_Libro", this.libro.getLibTituloLibro());
                    document.addHeader("ISBN",this.libro.getLibIsbn());
                }
                
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
    
    /***
     * Elimina los documentos subidos al gestor de documentos OpenKM.
     * Se obtiene una instancia de openKm con la cual buscamos la carpeta donde
     * estan almacenados los documentos. Si se encuentra se eliminar toda la 
     * carpeta de lo contrario se lanza un mensaje por consola notificando que
     * ocurrio un error al eliminar la documentacion.
     * @return
     * @throws LockException 
     */
    public boolean eliminarDocOpenkm() throws LockException{
        boolean eliminado = false;
        boolean existe = false;
        String rutaFolder="/okm:root/Doctorado_Electronica/" + this.pubEstIdentificador.getEstUsuario() + "/" + this.pubDiropkm;
        Folder folder = new Folder();
        folder.setPath(rutaFolder);
        OKMWebservices ws = OKMWebservicesFactory.newInstance(OpenKMController.host , OpenKMController.username, OpenKMController.password);
        try {
            /* Se valida si el forder a eliminar existe o no*/
            ws.isValidFolder(rutaFolder);
            existe = true;
            if(existe){
                ws.deleteFolder(rutaFolder);
                eliminado = true;
            }
        } catch (PathNotFoundException | AccessDeniedException | RepositoryException | DatabaseException | UnknowException | WebserviceException e) {
            System.out.println("Error al eliminar documento: "+e.getMessage());
        }
        
        return eliminado;
    }
    
    public void SubirOpenKMPD(ArrayList<tipoPDF_cargar> subidaArchivos, String estampaTiempo, String codigoFirma, String hash)
    {        

        OKMWebservices ws = OKMWebservicesFactory.newInstance(OpenKMController.host , OpenKMController.username, OpenKMController.password);
        try{
            boolean crearFolder;
            String rutaFolderCrear;
            /* codigoFirma - en este caso corresponde al nombre de la carpeta que contendra
                el articulo y su tabla de contenido en formato PDFA
                Ruta del folder a crear en el Gestor Openkm*/
            // rutaFolderCrear = "/okm:root/Doctorado_Electronica/" + codigoFirma;
            rutaFolderCrear = "/okm:root/Doctorado_Electronica/" + this.pubEstIdentificador.getEstUsuario();
            this.setPubHash(hash);
            this.setPubDiropkm(codigoFirma);            
            try {
                /* Se valida si el forder a crear existe o no*/
                ws.isValidFolder(rutaFolderCrear);
                crearFolder = false;
            } catch (PathNotFoundException | AccessDeniedException | RepositoryException | DatabaseException | UnknowException | WebserviceException e) {
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
            } catch (PathNotFoundException | AccessDeniedException | RepositoryException | DatabaseException | UnknowException | WebserviceException e) {
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
                  InputStream is = new FileInputStream("" + subidaArchivos.get(i).getRutaArchivo());
                    com.openkm.sdk4j.bean.Document doc = new com.openkm.sdk4j.bean.Document();
                    doc.setPath(rutaFolderCrear + "/" + subidaArchivos.get(i).getNombreArchivo() + ".pdf");
                    ws.createDocument(doc, is);

                    IOUtils.closeQuietly(is);
                    List<FormElement> fElements = ws.getPropertyGroupProperties("" + rutaFolderCrear + "/" + subidaArchivos.get(i).getNombreArchivo() + ".pdf", "okg:practica");
                    for (FormElement fElement : fElements) {
                        if (fElement.getName().equals("okp:practica.identPublicacion")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.pubIdentificador);
                        }
                        if (fElement.getName().equals("okp:practica.identArchivo")) {
                            Input name = (Input) fElement;
                            name.setValue("" + arch.getArcIdentificador());
                        }

                        if (fElement.getName().equals("okp:practica.tipoPDFCargar")) {
                            Input name = (Input) fElement;
                            name.setValue("" + subidaArchivos.get(i).getTipoPDF());
                        }
                        if (fElement.getName().equals("okp:practica.estampaTiempo")) {
                            Input name = (Input) fElement;
                            name.setValue("" + estampaTiempo);
                        }
                        if (fElement.getName().equals("okp:practica.nombreAutor")) {
                            Input name = (Input) fElement;
                            name.setValue("" + this.pubEstIdentificador.getEstNombre());
                        }
                    }
                    ws.setPropertyGroupProperties("" + rutaFolderCrear + "/" + subidaArchivos.get(i).getNombreArchivo() + ".pdf", "okg:practica", fElements);
                    
             }
            
        }catch (AccessDeniedException | AutomationException | DatabaseException | ExtensionException | FileSizeExceededException | ItemExistsException | LockException | NoSuchGroupException | NoSuchPropertyException | ParseException | PathNotFoundException | RepositoryException | UnknowException | UnsupportedMimeTypeException | UserQuotaExceededException | VirusDetectedException | WebserviceException | IOException e) {
            System.out.println("error en metodo SubirOpenKMPD de publicacion.java. error: " + e.getMessage());
                    
        }  
    }
    
    /**
     * agregar metadatos de practica docente a openKM
     * @param subidaArchivos archivos que se suben
     * @param estampaTiempo 
     */
    private void CrearPDFA_MetadataPD(ArrayList<tipoPDF_cargar> subidaArchivos, String estampaTiempo)
    {           
        for (int s = 0; s < subidaArchivos.size(); s++) {
            Document document = new Document();
            PdfReader reader;         
            try
            {
                PdfAWriter writer = PdfAWriter.getInstance(document, new FileOutputStream(
                        subidaArchivos.get(s).getRutaArchivo()), PdfAConformanceLevel.PDF_A_1B);
                writer.setTagged();               

                Archivo arch = (Archivo) archivoCollection.toArray()[s];
                                                        
                document.addHeader("Identificador Publicacion", "" + this.pubIdentificador);
                document.addHeader("Identificador Archivo", "" + arch.getArcIdentificador());
                document.addHeader("tipoPDF_cargar", "" + subidaArchivos.get(s).getTipoPDF());
                document.addHeader("Estampa Tiempo", "" + estampaTiempo);
                document.addAuthor("" + this.pubEstIdentificador.getEstNombre());
                document.addCreator("" + this.pubEstIdentificador.getEstNombre());
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
            }catch(DocumentException | IOException de){
                System.err.println("error en metodo CrearPDFA_MetadataPD() de clase publicacion.java");
                System.out.println(de.getMessage());
            }finally{
               document.close();
            }           
        }
        
    }
    
    /***
     * Metodo que retorna el nombre del tipo de documento a buscar en openKM
     * segun un identificador
     * @param tipo
     * @return nombreTipoDocumento
     */
    private String nombreTipoDocumento(int tipo){
        String nombre = "";
        /*Documento de la publicacion*/
        if(tipo == 1){  nombre="tipoPublicacion";   }
        /*Evidencia de la publicacion*/
        if(tipo == 2){  nombre="cartaAprobacion";   }
        if(tipo == 3){  nombre="tablaContenido";   }
        return nombre;
    }
    
    /***
     * Descarga los documentos subidos a OpenKM para poder visualizarlos.
     * Dependiendo del tipo de documento, se busca en en OpenKM si se encuentra
     * el archivo. Si esta se retorna el archivo, de lo contrario se genera
     * una excepcion notificando que ocurrio un error al encontrar el archivo
     * @param tipo
     * @return archivoPDF
     */
    public archivoPDF descargarDocumento(int tipo){
        
        String tipoPDF = nombreTipoDocumento(tipo);
        archivoPDF archivo = new archivoPDF();
        /*Obtenemos la instancia del openKM*/
        OKMWebservices ws = OKMWebservicesFactory.newInstance(OpenKMController.host , OpenKMController.username, OpenKMController.password);

        try {

            Map<String, String> properties = new HashMap();
            /* Se comprueba el tipo de publicacion: revista congreso , un libro 
                o un capitulo de un libro que se devolvera como resultado*/
            if (this.idTipoDocumento.getIdentificador() == 4) {
                properties.put("okp:revista.identPublicacion", "" + this.pubIdentificador);
                properties.put("okp:revista.tipoPDFCargar", "" + tipoPDF);
            }
            if (this.idTipoDocumento.getIdentificador() == 3) {
                properties.put("okp:congreso.identPublicacion", "" + this.pubIdentificador);
                properties.put("okp:congreso.tipoPDFCargar", "" + tipoPDF);
            }
            if (this.idTipoDocumento.getIdentificador() == 2) {
                properties.put("okp:capLibro.identPublicacion", "" + this.pubIdentificador);
                properties.put("okp:capLibro.tipoPDFCargar", "" + tipoPDF);
            }
            if (this.idTipoDocumento.getIdentificador() == 1) {
                properties.put("okp:libro.identPublicacion", "" + this.pubIdentificador);
                properties.put("okp:libro.tipoPDFCargar", "" + tipoPDF);
            }
            
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
        } catch (IOException | ParseException | RepositoryException | DatabaseException | UnknowException | WebserviceException | PathNotFoundException | AccessDeniedException e) {
            System.out.println("Error al descargar documento "+tipoPDF+". "+e.getMessage());
        }
        return archivo;
    }   
    
    /**
     * Metodo para obtener el titulo de la publicacion segun tu su tipo
     * @return nombrePublicacion
     */
    public String obtenerNombrePub() {        
        String nombrePub = "";
        if (this.idTipoDocumento.getIdentificador() == 1) {
            nombrePub = this.getLibro().getLibTituloLibro();
        }
        if (this.idTipoDocumento.getIdentificador() == 2) {
            nombrePub = this.getCapituloLibro().getCaplibTituloCapitulo();
        }
        if (this.idTipoDocumento.getIdentificador() == 3) {
            nombrePub = this.getCongreso().getCongTituloPonencia();
        }
        if (this.idTipoDocumento.getIdentificador() == 4) {
            nombrePub = this.getRevista().getRevTituloArticulo();
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
        return pubFechaPublicacion;
    }
    
    public String ObtenerFecha()
    {
        String fecha= pubFechaRegistro.toLocaleString();
        String solof[] = fecha.split(" ");
        return solof[0];
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

    public String getPubVisado() {
        return pubVisado;
    }

    public void setPubVisado(String pubVisado) {
        this.pubVisado = pubVisado;
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
    
    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }
    
    public Estudiante getPubEstIdentificador() {
        return pubEstIdentificador;
    }

    public void setPubEstIdentificador(Estudiante pubEstIdentificador) {
        this.pubEstIdentificador = pubEstIdentificador;
    }
    
    @XmlTransient
    public Collection<Archivo> getArchivoCollection() {
        return archivoCollection;
    }

    public void setArchivoCollection(Collection<Archivo> archivoCollection) {
        this.archivoCollection = archivoCollection;
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

    public TipoDocumento getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(TipoDocumento idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public PracticaDocente getPracticaDocente() {
        return practicaDocente;
    }

    public void setPracticaDocente(PracticaDocente practicaDocente) {
        this.practicaDocente = practicaDocente;
    }   

    public void setPubCreditos(int pubCreditos) {
        this.pubCreditos = pubCreditos;
}

    public Integer getPubCreditos() {
        return pubCreditos;
    }

    public void setPubCreditos(Integer pubCreditos) {
        this.pubCreditos = pubCreditos;
    }

}
