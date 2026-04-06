/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import conexion.ConexionBD;
import entidades.Producto;
import enumerators.EstadoProducto;
import excepciones.PersistenciaException;
import interfaces.IProductoDAO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author María José Valdez Iglesias - 262775
 */
public class ProductoDAO implements IProductoDAO {

    private static IProductoDAO instance;
    
    private ProductoDAO(){}
    
    public static IProductoDAO getInstance(){
        if(instance == null){
            instance = new ProductoDAO();
        }
        return instance;
    }
    
    @Override
    public Producto agregarProducto(Producto producto) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try{
            em.getTransaction().begin();
            em.persist(producto);
            em.getTransaction().commit();
            return producto;
        } catch(Exception e){
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Se revirtió la acción de agregar el producto: " + producto.getNombre());
        } finally {
            em.close();
        }
    }

    @Override
    public Producto actualizarProducto(Producto producto) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try{
            em.getTransaction().begin();
            Producto productoBD = em.find(Producto.class, producto.getId());
            if(productoBD == null){
                throw new PersistenciaException("No se encuentra dicho producto en la base de datos.");
            }
            
            productoBD.setDescripcion(producto.getDescripcion());
            productoBD.setPrecio(producto.getPrecio());
            productoBD.setUrlImagen(producto.getUrlImagen());
            productoBD.setProductos(producto.getProductos());
            
            em.getTransaction().commit();
            return producto;
        } catch(Exception e){
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Se revirtió la acción de actualizar el producto: " + producto.getNombre());
        } finally {
            em.close();
        }
    }

    @Override
    public boolean eliminarProducto(Long idProducto) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try{
            em.getTransaction().begin();
            Producto p = em.find(Producto.class, idProducto);
            if(p == null){
                return false;
            }
            em.remove(p);
            em.getTransaction().commit();
            return true;
        } catch(Exception e){
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Se revirtió la acción de eliminar el producto con ID=" + idProducto);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Producto> consultarTodos() throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try{
            String jpql = "select p from Producto p";
            TypedQuery<Producto> query = em.createQuery(jpql, Producto.class);
            return query.getResultList();
        } catch(Exception e){
            throw new PersistenciaException("No fue posible consultar los productos.");
        } finally {
            em.close();
        }
    }

    @Override
    public boolean activarProducto(Long idProducto) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try{
            em.getTransaction().begin();
            Producto p = em.find(Producto.class, idProducto);
            if(p == null){
                return false;
            }
            p.setEstado(EstadoProducto.ACTIVO);
            em.getTransaction().commit();
            return true;
        } catch(Exception e){
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("No fue posible activar el producto con ID=" + idProducto);
        } finally {
            em.close();
        }
    }

    @Override
    public boolean desactivarProducto(Long idProducto) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try{
            em.getTransaction().begin();
            Producto p = em.find(Producto.class, idProducto);
            if(p == null){
                return false;
            }
            p.setEstado(EstadoProducto.INACTIVO);
            em.getTransaction().commit();
            return true;
        } catch(Exception e){
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("No fue posible desactivar el producto con ID=" + idProducto);
        } finally {
            em.close();
        }
    }

    @Override
    public Producto consultarProductoPorID(Long idProducto) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            return em.find(Producto.class, idProducto);
        } catch (Exception e) {
            throw new PersistenciaException("No fue posible buscar al producto con id: " + idProducto, e);
        } finally {
            em.close();
        }
    }
    
}
