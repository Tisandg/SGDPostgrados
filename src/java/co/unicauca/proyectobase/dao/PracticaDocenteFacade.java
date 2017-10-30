package co.unicauca.proyectobase.dao;

import co.unicauca.proyectobase.entidades.Estudiante;
import co.unicauca.proyectobase.entidades.PracticaDocente;
import co.unicauca.proyectobase.entidades.Publicacion;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Danilo López - dlopezs@unicauca.edu.co
 */
@Stateless
public class PracticaDocenteFacade extends AbstractFacade<PracticaDocente> {

    @PersistenceContext(unitName = "ProyectoDoctoradoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PracticaDocenteFacade() {
        super(PracticaDocente.class);
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
    public List<PracticaDocente> practicaDocente(String nombreUsuario){
        System.out.println("EL nombre es  "+nombreUsuario);
        javax.persistence.Query query = getEntityManager().createNamedQuery("PracticaDocente.buscarPracticasByNombreUsuario");
        query.setParameter("nombreUsuario", nombreUsuario);
        List<PracticaDocente> lista = null;
        try {
            
            lista = query.getResultList();
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
            System.out.println(e);
        }
        return lista;
    }
}
