package co.unicauca.proyectobase.dao;

import co.unicauca.proyectobase.entidades.Estudiante;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 *
 * @author Sahydo
 */
@Stateless
public class EstudianteFacade extends AbstractFacade<Estudiante> {

    @PersistenceContext(unitName = "ProyectoDoctoradoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EstudianteFacade() {
        super(Estudiante.class);
    }

    public boolean existByEstCorreo(String estCorreo) {
        Query query = em.createNamedQuery("Estudiante.findByEstCorreo");
        query.setParameter("estCorreo", estCorreo);
        return !query.getResultList().isEmpty();
    }

    public boolean existByEstCodigo(String estCodigo) {
        Query query = em.createNamedQuery("Estudiante.findByEstCodigo");
        query.setParameter("estCodigo", estCodigo);
        return !query.getResultList().isEmpty();
    }

    public Estudiante buscarPorCodigoExceptoConId(String estCodigo, Integer id) {
        Query query = em.createNamedQuery("Estudiante.buscarPorCodigoExceptoConId");
        query.setParameter("estCodigo", estCodigo);
        query.setParameter("estIdentificador", id);

        List<Estudiante> resultList = query.getResultList();
        if (resultList.size() > 0) {
            return resultList.get(0);
        } else {
            return null;
        }
    }
    
    /**
     * Funcion para buscar un estudiante por su codigo
     * @param codigo
     * @return Estudiante
     */
    public Estudiante buscarPorCodigo(String codigo){
        Query query = em.createNamedQuery("Estudiante.buscarPorCodigo");
        query.setParameter("estCodigo", codigo);
        List<Estudiante> resultList = query.getResultList();
        if (resultList.size() > 0) {
            return resultList.get(0);
        } else {
            return null;
        }
    }
    
    public Estudiante buscarPorCorreoExceptoConId(String correo, Integer id) {
        Query query = em.createNamedQuery("Estudiante.buscarPorCorreoExceptoConId");
        query.setParameter("estCorreo", correo);
        query.setParameter("estIdentificador", id);

        List<Estudiante> resultList = query.getResultList();
        if (resultList.size() > 0) {
            return resultList.get(0);
        } else {
            return null;
        }
    }
    public List<Estudiante> findAllByString(String texto) {
        Query query = em.createNamedQuery("Estudiante.findAllByString");
        query.setParameter("texto", "%" + texto + "%");
        List<Estudiante> findEst = query.getResultList();
        return findEst;
    }
    
    /**
     * busca la cantidad de creditos segun el nombre de usuario
     * @param nomUsuario nombre de usuario a saber creditos
     * @return cantidad de creditos del usuario
     */
    public int findCreditosByNombreUsuario(String nomUsuario){                           
        Query query = em.createNamedQuery("Estudiante.findCreditosByNomUsu");
        query.setParameter("nombreUsuario", nomUsuario);
        List<Integer> resultList = query.getResultList();
        if (resultList.size() > 0) {
            if(resultList.get(0) != null){
                return resultList.get(0);
            }
        }
        return 0;
    }

    public String findNombre(String nombreUsuario) {
        System.out.println("buscando nombre: " + nombreUsuario);
        Query query = em.createNamedQuery("Estudiante.findNombreByUsuario");
        query.setParameter("nombreUsuario", nombreUsuario);
        List<Estudiante> resultList = query.getResultList();        
        if(!resultList.isEmpty()){    
            System.out.println("encontrado: " + resultList.get(0).toString());
            return resultList.get(0).getEstNombre() + " "+ resultList.get(0).getEstApellido();
        }
        return "";
    }

    public String findNombreById(Estudiante nombreUsuario) {        
        Query query = em.createNamedQuery("Estudiante.findNombreById");
        query.setParameter("id", nombreUsuario.getEstIdentificador());
        List<Estudiante> resultList = query.getResultList();        
        if(!resultList.isEmpty()){                
            return resultList.get(0).getEstNombre() + " "+ resultList.get(0).getEstApellido();
        }
        return "";
    }
}
