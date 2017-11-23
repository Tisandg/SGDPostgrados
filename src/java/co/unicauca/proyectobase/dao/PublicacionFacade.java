package co.unicauca.proyectobase.dao;

import co.unicauca.proyectobase.entidades.Estudiante;
import co.unicauca.proyectobase.entidades.Publicacion;
import co.unicauca.proyectobase.entidades.TipoDocumento;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import java.util.List;
import javax.persistence.Query;

@Stateless
public class PublicacionFacade extends AbstractFacade<Publicacion> {

    @PersistenceContext(unitName = "ProyectoDoctoradoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PublicacionFacade() {
        super(Publicacion.class);
    }

    /**
     * Funcion para buscar todas las publicaciones que han sido registradas 
     * en un determinado año. El listado esta ordenado por tipo de publicacion
     * de forma descendente
     * @param anio
     * @return Lista de publicaciones
     */
    public List<Publicacion> publicacionesPorAnio(int anio){
        javax.persistence.Query query = getEntityManager().createNamedQuery("Publicacion.findAllByYear");
        query.setParameter("anio", anio);
        List<Publicacion> lista = null;
        try {
            //System.out.println("Buscando publicaciones en el año "+anio);
            lista = query.getResultList();
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
            System.out.println(e);
        }
        return lista;
    }
    
    /**
     * Funcion para buscar todas las publicaciones que han registradas 
     * en un determinado semestre. El listado esta ordenado por tipo de 
     * publicacion de forma descendente
     * @param anio
     * @param semestre
     * @return Lista de publicaciones
     */
    public List<Publicacion> publicacionesPorSemestre(int anio, int semestre){
        Query query = getEntityManager().createNamedQuery("Publicacion.findAllBySemester");
        query.setParameter("anio", anio);
        if(semestre == 1){
            query.setParameter("inicio",1);
            query.setParameter("fin",6);
        }else{
            query.setParameter("inicio",7);
            query.setParameter("fin",12);
        }
        List<Publicacion> lista = null;
        try {
            //System.out.println("Buscando publicaciones en el semestre "+anio+"-"+semestre);
            lista = query.getResultList();
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
            System.out.println(e);
        }
        return lista;
    }
    
    public int getnumFilasPubRev() {
        try {
            String queryStr;
            queryStr = "SELECT AUTO_INCREMENT FROM  INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'doctorado' AND TABLE_NAME = 'publicacion'";
            javax.persistence.Query query = getEntityManager().createNativeQuery(queryStr);
            List results = query.getResultList();
            int autoIncrement = ((BigInteger) results.get(0)).intValue();
            return autoIncrement;
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
            System.out.println(e);
            return -1;
        }
    }

    public int getIdArchivo() {
        try {
            String queryStr;
            queryStr = "SELECT AUTO_INCREMENT FROM  INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'doctorado' AND TABLE_NAME = 'archivo'";
            javax.persistence.Query query = getEntityManager().createNativeQuery(queryStr);
            List results = query.getResultList();
            int autoIncrement = ((BigInteger) results.get(0)).intValue();
            return autoIncrement;
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
            System.out.println(e);
            return -1;
        }
    }

    public Estudiante getEst() {
        int estIden = 1;
        Estudiante est;
        try {
            est = em.find(Estudiante.class, estIden);
            return est;
        } catch (Exception e) {
            return null;
        }
    }

    /*
    public Estudiante obtenerEstudiante(String nombreUsuario) {

        Query consulta = getEntityManager().createNamedQuery("Estudiante.findByEstUsuario");
        consulta.setParameter("estUsuario", nombreUsuario);
        try{
            List<Estudiante> resultado = consulta.getResultList();
            if(resultado.size()>0){
                return resultado.get(0);
            }else{
                System.out.println("No se puede obtener estudiante");
            }
        }catch(Exception e){
            System.out.println("Error " + e.getMessage());
        }
        return null;
    }*/
    
    public int CountByMonthYear(String anio, String mes) {

        String comSimple = "\'";
        String queryStr;
        queryStr =  "SELECT COUNT(*) FROM doctorado.publicacion WHERE YEAR(doctorado.publicacion.pub_fecha_registro) =" + comSimple + anio + comSimple + "  AND Month(doctorado.publicacion.pub_fecha_registro) = "+ comSimple + mes + comSimple;
        
        javax.persistence.Query query = getEntityManager().createNativeQuery(queryStr);
        List results = query.getResultList();
        int numeroPub = ((Long) results.get(0)).intValue();
        return numeroPub;
   
    }
        
     public int CountByMonthYearVis(String anio, String mes) {

        String comSimple = "\'";
        String queryStr;
        queryStr =  "SELECT COUNT(*) FROM doctorado.publicacion WHERE YEAR(doctorado.publicacion.pub_fecha_visado) =" + comSimple + anio + comSimple + "  AND Month(doctorado.publicacion.pub_fecha_visado) = "+ comSimple + mes + comSimple;
        
        javax.persistence.Query query = getEntityManager().createNativeQuery(queryStr);
        List results = query.getResultList();
        int numeroPub = ((Long) results.get(0)).intValue();
        return numeroPub;
   
    }

    public List<Publicacion> ListadoPublicacionEst(int estudianteId) {
        javax.persistence.Query query = getEntityManager().createNamedQuery("findAllPub_Est");
        query.setParameter("identificacion", estudianteId);
        try {
            //System.out.println("Buscando publicaciones por estudiante");
            return query.getResultList();
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
            System.out.println(e);
            return null;
        }
    }
    
    /**
     * Funcion que me retorna la lista de publicaciones que ha registrado un 
     * estudiante en un determinado año. El listado esta ordenado por tipo de 
     * publicacion de forma descendente
     * @param estudianteId
     * @param anio
     * @return Lista publicaciones
     */
    public List<Publicacion> publicacionesEstudiantePorAnio(int estudianteId,int anio){
        javax.persistence.Query query = getEntityManager().createNamedQuery("Publicacion.StudentPublications_Year");
        query.setParameter("identificador", estudianteId);
        query.setParameter("anio", anio);
        try {
            //System.out.println("Buscando publicaciones de estudiante por año");
            return query.getResultList();
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
            System.out.println(e);
            return null;
        }
    }
    
    /**
     * Funcion que me retorna la lista de publicaciones que ha registrado un
     * estudiante en un determinado semestre. El listado esta ordenado por tipo de 
     * publicacion de forma descendente
     * @param estudianteId
     * @param anio
     * @param semestre
     * @return Lista publicaciones
     */
    public List<Publicacion> publicacionesEstudiantePorSemestre(int estudianteId, int anio, int semestre){
        javax.persistence.Query query = getEntityManager().createNamedQuery("Publicacion.StudentPublications_Semester");
        query.setParameter("identificador", estudianteId);
        query.setParameter("anio", anio);
        if(semestre == 1){
            query.setParameter("inicio",1);
            query.setParameter("fin",6);
        }else{
            query.setParameter("inicio",7);
            query.setParameter("fin",12);
        }
        try {
            //System.out.println("Buscando publicaciones de estudiante por semestre");
            return query.getResultList();
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
            System.out.println(e);
            return null;
        }
    }
    
    public List<Publicacion> ListadoPublicacionFilt(String variableFiltrado) {

        javax.persistence.Query query = em.createNamedQuery("Publicacion.findAllEst");
        query.setParameter("variableFiltro", "%" + variableFiltrado + "%");
        List<Publicacion> findPub = query.getResultList();
        
       if (findPub.isEmpty()) {
            query = em.createNamedQuery("Publicacion.findAllRev");
            query.setParameter("variableFiltro", "%" + variableFiltrado + "%");
            findPub = query.getResultList();
        }
        if (findPub.isEmpty()) {
            query = em.createNamedQuery("Publicacion.findAllCong");
            query.setParameter("variableFiltro", "%" + variableFiltrado + "%");
            findPub = query.getResultList();
        }
        if (findPub.isEmpty()) {
            query = em.createNamedQuery("Publicacion.findAllLib");
            query.setParameter("variableFiltro", "%" + variableFiltrado + "%");
            findPub = query.getResultList();
        }
        if (findPub.isEmpty()) {
            query = em.createNamedQuery("Publicacion.findAllCapLib");
            query.setParameter("variableFiltro", "%" + variableFiltrado + "%");
            findPub = query.getResultList();
        }
        return findPub;

    }
    
    public List<Publicacion> ListadoSoloPublicacion() {
        
        javax.persistence.Query query = em.createNamedQuery("Publicacion.findByOnlyPublicacion");
        List<Publicacion> findPub = query.getResultList();
        return findPub;

    }      
              
    public List<Publicacion> ListadoPublicacionEstFilt(int estIdentificador, String variableFiltrado) {

        javax.persistence.Query query = em.createNamedQuery("Publicacion.findAllByRev");
        query.setParameter("identificacion", estIdentificador);
        query.setParameter("variableFiltro", "%" + variableFiltrado + "%");

        List<Publicacion> findPub = query.getResultList();
        if (findPub.isEmpty()) {
            query = em.createNamedQuery("Publicacion.findAllByCong");
            query.setParameter("identificacion", estIdentificador);
            query.setParameter("variableFiltro", "%" + variableFiltrado + "%");
            findPub = query.getResultList();
        }
        if (findPub.isEmpty()) {
            query = em.createNamedQuery("Publicacion.findAllByLib");
            query.setParameter("identificacion", estIdentificador);
            query.setParameter("variableFiltro", "%" + variableFiltrado + "%");
            findPub = query.getResultList();
        }
        if (findPub.isEmpty()) {
            query = em.createNamedQuery("Publicacion.findAllByCapLib");
            query.setParameter("identificacion", estIdentificador);
            query.setParameter("variableFiltro", "%" + variableFiltrado + "%");
            findPub = query.getResultList();
        }
        return findPub;

    }        

    public void cambiarEstadoVisado(Integer pubIdentificador, String valor) {
        String nombre = "Publicacion.updateVisadoById";        
        Query query = getEntityManager().createNamedQuery(nombre);
        query.setParameter("valorVisado", valor);
        query.setParameter("id", pubIdentificador);
        int res = query.executeUpdate();
        
        System.out.println("respuesta: " + res);
        
        
    }
    
    public Estudiante obtenerEstudianteP(String nombre) {

        String comSimple = "\'";
        String queryStr;
        queryStr = " SELECT est_identificador FROM doctorado.estudiante WHERE est_usuario = " + comSimple + nombre + comSimple;
        javax.persistence.Query query = getEntityManager().createNativeQuery(queryStr);
        List results = query.getResultList();
        int estIden = (int) results.get(0);

        Estudiante est;
        try {
            est = em.find(Estudiante.class, estIden);
            return est;
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
            System.out.println(e);
            return null;
        }
    }
    
    public Estudiante obtenerEstudianteAnio(String nombre) {

        String comSimple = "\'";
        String queryStr;
        queryStr = " SELECT est_identificador FROM doctorado.estudiante WHERE est_usuario = " + comSimple + nombre + comSimple;
        javax.persistence.Query query = getEntityManager().createNativeQuery(queryStr);
        List results = query.getResultList();
        int estIden = (int) results.get(0);

        Estudiante est;
        try {
            est = em.find(Estudiante.class, estIden);
            return est;
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
            System.out.println(e);
            return null;
        }
    }
    
    public TipoDocumento obtenerIdTipoDocumento(String tipoDoc){
        javax.persistence.Query query = getEntityManager().createNamedQuery("Publicacion.findIdTipoDocumento");
        query.setParameter("tipoDoc", tipoDoc);
        try {            
            return (TipoDocumento)query.getResultList().get(0);
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
            System.out.println(e);
            return null;
        }
    }
    
    /**
     * Funcion para obtener el numero de creditos de un tipo de publicacion
     * en especifico. Se busca en la tabla tipo_documento de la base de datos
     * con el parametro proporcionado y se devuelve el numero de creditos que
     * se ha establecido en la tabla
     * @param idTipoPublicacion
     * @return numeroCreditos
     */
    public int getCreditosTipoPubicacionPorID(int idTipoPublicacion) {
        String comSimple = "\'";
        String queryStr;
        queryStr = "SELECT creditos FROM tipo_documento "
                 + " WHERE identificador ="+ idTipoPublicacion;
       
        javax.persistence.Query query = getEntityManager().createNativeQuery(queryStr);
        int result = (Integer)query.getSingleResult();
        return result;
    }

}
