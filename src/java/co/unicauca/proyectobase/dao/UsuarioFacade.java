package co.unicauca.proyectobase.dao;

import co.unicauca.proyectobase.entidades.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase utilizada para las operaciones sobre la tabla de la base de datos usuario
 * @author Sahydo
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "ProyectoDoctoradoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }
    
    public List<Usuario> findAllByNombreUsuario(String nombreUsuario)
    {
        //System.out.println("buscando usuario: " + nombreUsuario);
        Query query= em.createNamedQuery("Usuario.findByNombreUsuario");
        query.setParameter("nombreUsuario", nombreUsuario);
        List<Usuario> findUsuario= query.getResultList();
        System.out.println("Datos de la lista"+ findUsuario);
        System.out.println("buscando usuario: " + nombreUsuario+ " resultado: " + findUsuario.toString());
        return findUsuario;
    }
    
    public Usuario findByUserName(String nombreUsuario){
        Usuario user = new Usuario();
        Query query= em.createNamedQuery("Usuario.findByNombreUsuario");
        query.setParameter("nombreUsuario", nombreUsuario);
        List<Usuario> findUsuario= query.getResultList();
        if(findUsuario.size() >0 ){
            user = findUsuario.get(0);
        }
        return user;
    } 
    
}
