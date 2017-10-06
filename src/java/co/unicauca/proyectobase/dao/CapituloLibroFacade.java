package co.unicauca.proyectobase.dao;

import co.unicauca.proyectobase.entidades.CapituloLibro;
import co.unicauca.proyectobase.entidades.Libro;
import co.unicauca.proyectobase.entidades.Revista;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Danilo - Unicauca
 */
@Stateless
public class CapituloLibroFacade extends AbstractFacade<CapituloLibro> {

    @PersistenceContext(unitName = "ProyectoDoctoradoPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public CapituloLibroFacade() {
        super(CapituloLibro.class);
    }

    public CapituloLibro findByTituloCapituloLibro(String tituloCapitulo) {
        //System.out.println("Titulo: "+tituloLibro);
        Query query= em.createNamedQuery("CapituloLibro.findByCaplibTituloCapitulo");
        query.setParameter("revTitulo", tituloCapitulo);
        List<CapituloLibro> resultList= query.getResultList();
        if (resultList.size() > 0) {
            return resultList.get(0);
        } else {
            return null;
        }
    }

    public CapituloLibro findByIsbnLibro(String sn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    

}