/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.proyectobase.dao;

import co.unicauca.proyectobase.entidades.Estudiante;
import co.unicauca.proyectobase.entidades.Publicacion;
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

    public Estudiante obtenerEstudiante(String nombreUsuario) {

        String comSimple = "\'";
        String queryStr;
        queryStr = " SELECT est_identificador FROM doctorado.estudiante WHERE est_usuario = " + comSimple + nombreUsuario + comSimple;
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

    public List<Publicacion> ListadoPublicacionEst(int estIdentificador) {
        javax.persistence.Query query = getEntityManager().createNamedQuery("findAllPub_Est");
        query.setParameter("identificacion", estIdentificador);
        try {
            System.out.println("co.unicauca.proyectobase.dao.PublicacionFacade.ListadoPublicacionEst()");

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

}
