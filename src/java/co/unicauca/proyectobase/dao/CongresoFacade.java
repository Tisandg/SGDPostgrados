package co.unicauca.proyectobase.dao;

import co.unicauca.proyectobase.entidades.Congreso;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @author Sahydo
 */
@Stateless
public class CongresoFacade extends AbstractFacade<Congreso> {

    @PersistenceContext(unitName = "ProyectoDoctoradoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CongresoFacade() {
        super(Congreso.class);
    }
    
    public Congreso findByTituloPonencia(String tituloPonencia)
    {
        Query query= em.createNamedQuery("Congreso.findByCongTituloPonencia");
        query.setParameter("congTituloPonencia", tituloPonencia);
        List<Congreso> resultList= query.getResultList();
        if (resultList.size() > 0) {
            return resultList.get(0);
        } else {
            return null;
        }
    }

    public Congreso findByIssnCongreso(String sn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Congreso findByDoiCongreso(String doi) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
