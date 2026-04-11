/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package daos;

import conexion.ConexionBD;
import entidades.Ingrediente;
import entidades.Producto;
import entidades.ProductoIngrediente;
import excepciones.PersistenciaException;
import interfaces.IProductoIngredienteDAO;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;


/**
 *
 * @author Alejandra Leal Armenta, 262719
 * @author María José Valdez Iglesias - 262775
 */

public class ProductoIngredienteDAO implements IProductoIngredienteDAO {
    
    /**
     * Instancia única del DAO (patrón Singleton).
     */
    private static IProductoIngredienteDAO instance;
    
    /**
     * Constructor privado para evitar la creación directa de instancias.
     */
    private ProductoIngredienteDAO(){}
    
    /**
     * Obtiene la instancia única del DAO.
     * @return 
     */
    public static IProductoIngredienteDAO getInstance(){
        if(instance == null){
            instance = new ProductoIngredienteDAO();
        }
        return instance;
    }

    /**
     * Verifica si un ingrediente está siendo utilizado en alguna receta.
     * @param idIngrediente
     * @return
     * @throws PersistenciaException 
     */
    @Override
    public boolean existeIngredienteEnRecetas(Long idIngrediente) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            String JPQL = "SELECT COUNT(pi) FROM ProductoIngrediente pi WHERE pi.ingrediente.id = :id";
            TypedQuery<Long> query = em.createQuery(JPQL, Long.class);

            query.setParameter("id", idIngrediente);

            Long conteo = query.getSingleResult();
            return conteo > 0;

        } catch (Exception e) {
            throw new PersistenciaException("Error al verificar si el ingrediente está en recetas", e);
        } finally {
            em.close();
        }
        
    }
    
    /**
     * Inserta una relación producto-ingrediente en la base de datos.
     * @param pi
     * @return
     * @throws PersistenciaException 
     */
    @Override
    public ProductoIngrediente agregarProductoIngrediente(ProductoIngrediente pi) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            em.getTransaction().begin();

            String JPQL = "SELECT COUNT(pi) FROM ProductoIngrediente pi WHERE pi.producto.id = :idProd AND pi.ingrediente.id = :idIng";
            TypedQuery<Long> query = em.createQuery(JPQL, Long.class);
            query.setParameter("idProd", pi.getProducto().getId());
            query.setParameter("idIng", pi.getIngrediente().getId());
            Long conteo = query.getSingleResult();

            if (conteo > 0) {
                throw new PersistenciaException("Ya existe este ingrediente en la receta");
            }

            pi.setProducto(em.find(Producto.class, pi.getProducto().getId()));
            pi.setIngrediente(em.find(Ingrediente.class, pi.getIngrediente().getId()));

            em.persist(pi);
            
            em.getTransaction().commit();

            return pi;
        } catch (PersistenciaException e) {
            throw new PersistenciaException("Error al agregar ingrediente a la receta", e);
        } finally {
            em.close();
        }
    }
    
    /**
     * Elimina la relación entre un producto y un ingrediente.
     * @param idProducto
     * @param idIngrediente
     * @return
     * @throws PersistenciaException 
     */
    @Override
    public ProductoIngrediente eliminarProductoIngrediente(Long idProducto, Long idIngrediente) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            em.getTransaction().begin();

            String JPQL = "SELECT pi FROM ProductoIngrediente pi WHERE pi.producto.id = :idProd AND pi.ingrediente.id = :idIng";
            TypedQuery<ProductoIngrediente> query = em.createQuery(JPQL, ProductoIngrediente.class);
            query.setParameter("idProd", idProducto);
            query.setParameter("idIng", idIngrediente);

            ProductoIngrediente pi = query.getSingleResult();
            em.remove(pi);
            
            em.getTransaction().commit();

            return pi;

        } catch (Exception e) {
            throw new PersistenciaException("Error al eliminar ingrediente de la receta", e);
        } finally {
            em.close();
        }
    }

    
    
}
