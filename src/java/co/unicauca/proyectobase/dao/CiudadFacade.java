/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.proyectobase.dao;

import co.unicauca.proyectobase.entidades.Ciudad;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author sperez
 */
@Stateless
public class CiudadFacade extends AbstractFacade<Ciudad> {

    @PersistenceContext(unitName = "ProyectoDoctoradoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CiudadFacade() {
        super(Ciudad.class);
    }
    
    public List<Ciudad> getCiudadPorPais(Integer idPais)
    {
        Query query = em.createQuery("SELECT c FROM Ciudad c JOIN FETCH c.paisId WHERE c.paisId.paisId = :idPais ORDER BY c.ciudNombre ASC");
        query.setParameter("idPais", idPais);
        //List<Ciudad> resultList = (List<Ciudad>) em.createQuery("SELECT c FROM Ciudad c JOIN FETCH c.paisId WHERE c.paisId.paisId = :idPais").getResultList();
        //System.out.println("CIUDAD "+resultList);
        List<Ciudad> resultList = query.getResultList();
        
        if (resultList.size() > 0) 
        {
            return resultList;
        }
        else 
        {
            return null;
        }
    }
    
    public Ciudad getCiudadPorId(Integer idCiudad)
    {
        Query query = em.createNamedQuery("Ciudad.findByCiudId");
        query.setParameter("ciudId", idCiudad);
        Ciudad ciud = (Ciudad) query.getSingleResult();
        
        return ciud;
    }
}