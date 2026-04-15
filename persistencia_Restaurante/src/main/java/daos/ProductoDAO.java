/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import conexion.ConexionBD;
import entidades.Producto;
import entidades.ProductoIngrediente;
import enumerators.EstadoProducto;
import enumerators.TipoProducto;
import excepciones.PersistenciaException;
import interfaces.IProductoDAO;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Implementación de la interfaz IProductoDAO para la gestión de productos en la
 * base de datos utilizando JPA.
 *
 * Esta clase contiene la lógica necesaria para realizar operaciones CRUD sobre
 * la entidad Producto, manejando transacciones y excepciones de persistencia.
 *
 * Se apoya en la clase ConexionBD para obtener instancias de EntityManager.
 * 
 * @author María José Valdez Iglesias - 262775
 */
public class ProductoDAO implements IProductoDAO {

    /**
     * Atributo que representa la instancia única que se usará de la clase.
     */
    private static IProductoDAO instance;
    
    /**
     * Cosntructor vacío y privado, para que no se puedan crear instancias de
     * la clase fuera de ella.
     */
    private ProductoDAO(){}
    
    /**
     * Método estático que regresa el atributo instance, que es la instancia
     * que se usará fuera de la clase. 
     * @return 
     */
    public static IProductoDAO getInstance(){
        if(instance == null){
            instance = new ProductoDAO();
        }
        return instance;
    }
    
    /**
     * Guarda un nuevo producto en la base de datos.
     *
     * Inicia una transacción, persiste el objeto Producto y confirma los
     * cambios. En caso de error, realiza un rollback.
     *
     * @param producto objeto Producto que contiene la información a guardar.
     * @return el producto guardado.
     * @throws PersistenciaException si ocurre un error durante la operación.
     */
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

    /**
     * Actualiza la información de un producto existente en la base de datos.
     *
     * Utiliza el método merge para sincronizar el estado del objeto con la base
     * de datos. Maneja transacciones y rollback en caso de error.
     *
     * @param producto objeto Producto con la información actualizada.
     * @return el producto actualizado.
     * @throws PersistenciaException si ocurre un error durante la actualización.
     */
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

            /*
            para que no se altere a la hora de modificar la lista de ingredientes.
            mejor la limpiamos y agregamos uno por uno.
            */
            if (productoBD.getDetallesIngredientes() != null) {
                productoBD.getDetallesIngredientes().clear();
            }
            if (producto.getDetallesIngredientes() != null) {
                for (ProductoIngrediente nuevoDetalle : producto.getDetallesIngredientes()) {
                    productoBD.agregarProductoIngrediente(nuevoDetalle); 
                }
            }
            
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

    /**
     * Obtiene la lista completa de productos registrados en la base de datos.
     *
     * Este método permite recuperar todos los productos almacenados en el
     * sistema, facilitando su visualización en interfaces como tablas o
     * listados dentro de la aplicación.
     *
     * @return lista de productos registrados.
     * @throws PersistenciaException si ocurre un error durante la consulta.
     */
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

    /**
     * Cambia el estado de un producto a 'ACTIVO' por medio de su ID.
     * @param idProducto ID del producto a modificar.
     * @return true si se cambió, false si falló.
     * @throws PersistenciaException si ocurre un error durante la operación.
     */
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

    /**
     * Cambia el estado de un producto a 'INACTIVO' por medio de su ID.
     * @param idProducto ID del producto a modificar.
     * @return true si se cambió, false si falló.
     * @throws PersistenciaException si ocurre un error durante la operación.
     */
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

    /**
     * Consulta un producto en la base de datos a partir de su identificador.
     *
     * Utiliza el método find de JPA para recuperar la entidad.
     *
     * @param idProducto identificador del producto.
     * @return el producto encontrado o null si no existe.
     * @throws PersistenciaException si ocurre un error durante la búsqueda.
     */
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

    /**
     * Busca el último identificador registrado en la base de datos según el 
     * tipo de producto.
     * 
     * Filtra por tipo y regresa el identificador más alto/último registrado.
     * 
     * @param tipo Tipo de producto a buscar.
     * @return El identificador encontrado o null si no existe.
     * @throws PersistenciaException si ocurre un error durante la búsqueda.
     */
    @Override
    public String obtenerUltimoIdentificador(TipoProducto tipo) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            String jpql = "select p.identificador from Producto p where p.tipo = :tipo order by p.identificador desc";
            TypedQuery<String> query = em.createQuery(jpql, String.class);
            query.setParameter("tipo", tipo);
            query.setMaxResults(1);
            List<String> resultados = query.getResultList();          
            if (resultados.isEmpty()) {
                return null;
            }
            return resultados.get(0);
        } catch (Exception e) {
            throw new PersistenciaException("Error al consultar el último identificador.", e);
        } finally {
            em.close();
        }
    }
    
    /**
     * Obtiene la lista completa de productos registrados de estado 'ACTIVO' en 
     * la base de datos.
     *
     * Este método permite recuperar todos los productos almacenados en el
     * sistema los cuales su estado sea 'ACTIVO'.
     *
     * @return lista de productos activos.
     * @throws PersistenciaException si ocurre un error durante la consulta.
     */
    @Override
    public List<Producto> consultarProductosActivos() throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try{
            String jpql = "select p from Producto p where p.estado = :estado";
            TypedQuery<Producto> query = em.createQuery(jpql, Producto.class);
            query.setParameter("estado", EstadoProducto.ACTIVO);
            List<Producto> resultados = query.getResultList();
            return resultados;
        } catch(Exception e){
            throw new PersistenciaException("");
        } finally {
            em.close();
        }
    }
    
    /**
     * Obtiene una lista con los productos que cumplan con los filtros 
     * especificados en los parámetros.
     * 
     * @param nombre Nombre que se desea buscar.
     * @param tipo Tipo de producto que se desea buscar.
     * @return La lista con los productos que cumplan con dicho filtro.
     * @throws PersistenciaException Si ocurre un error durante la búsqueda.
     */
    @Override
    public List<Producto> buscarProductos(String nombre, TipoProducto tipo) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery query = cb.createQuery(Producto.class);
        Root<Producto> producto = query.from(Producto.class);
        
        List<Predicate> filtros = new ArrayList<>();
        
        if(nombre != null && !nombre.isEmpty() && !nombre.isBlank()){
            filtros.add(cb.like(cb.lower(producto.get("nombre")), "%" + nombre.toLowerCase() + "%"));
        }
        
        if(tipo != null){
            filtros.add(cb.equal(producto.get("tipo"), tipo));
        }
        
        query.where(cb.and(filtros.toArray(Predicate[]::new)));
        
        query.select(producto);
        
        return em.createQuery(query).getResultList();
    }
    
    /**
     * 
     * @param nombre
     * @param tipo
     * @return
     * @throws PersistenciaException 
     */
    @Override
    public List<Producto> buscarProductosActivos(String nombre, TipoProducto tipo) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery query = cb.createQuery(Producto.class);
        Root<Producto> producto = query.from(Producto.class);
        
        List<Predicate> filtros = new ArrayList<>();
        
        if(nombre != null && !nombre.isEmpty() && !nombre.isBlank()){
            filtros.add(cb.like(cb.lower(producto.get("nombre")), "%" + nombre.toLowerCase() + "%"));
        }
        
        if(tipo != null){
            filtros.add(cb.equal(producto.get("tipo"), tipo));
        }
        
        filtros.add(cb.equal(producto.get("estado"), EstadoProducto.ACTIVO));
        
        query.where(cb.and(filtros.toArray(Predicate[]::new)));
        
        query.select(producto);
        
        return em.createQuery(query).getResultList();
    }
}
