package co.unicauca.proyectobase.dao;

import co.unicauca.proyectobase.entidades.Libro;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Clase utilizada para las operaciones sobre la tabla de la base de datos Libro
 * @author Santiago Garcia
 */
@Stateless
public class LibroFacade extends AbstractFacade<Libro>{

    @PersistenceContext(unitName = "ProyectoDoctoradoPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public LibroFacade() {
        super(Libro.class);
    }
    
    public Libro findByTituloLibro(String tituloLibro)
    {
        //System.out.println("Titulo: "+tituloLibro);
        Query query= em.createNamedQuery("Libro.findByLibTituloLibro");
        query.setParameter("libTituloLibro", tituloLibro);
        List<Libro> resultList= query.getResultList();
        if (resultList.size() > 0) {
            return resultList.get(0);
        } else {
            return null;
        }
    }

    public Libro findByIsbnLibro(String sn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Libro findByTituloCapituloLibro(String tituloLibro) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
