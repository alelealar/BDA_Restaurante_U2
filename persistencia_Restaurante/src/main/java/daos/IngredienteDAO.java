/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package daos;

import conexion.ConexionBD;
import entidades.Ingrediente;
import enumerators.Unidad;
import excepciones.PersistenciaException;
import interfaces.IIngredienteDAO;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


/**
 * Objeto de acceso a datos encargado de la gestión de ingredientes.
 * 
 * Se encarga de realizar las operaciones de persistencia en la base de datos,
 * como inserciones, consultas, actualizaciones y eliminaciones de ingredientes.
 * 
 * @author Alejandra Leal Armenta, 262719
 */

public class IngredienteDAO implements IIngredienteDAO{
    
    /**
     * Instancia única del DAO de ingredientes (patrón Singleton).
     */
    private static IngredienteDAO instancia;

    /**
     * Constructor privado para evitar la creación directa de instancias.
     */
    private IngredienteDAO() {
    }
    
    /**
     * Obtiene la instancia única del DAO de ingredientes.
     * @return 
     */
    public static IIngredienteDAO getInstance(){
        if(instancia == null){
            instancia = new IngredienteDAO();
        }
        return instancia;
    }
    
    /**
     * Inserta un nuevo ingrediente en la base de datos.
     * Guarda la información del ingrediente proporcionado.
     * @param ingrediente
     * @return
     * @throws PersistenciaException 
     */
    @Override
    public Ingrediente agregarIngrediente(Ingrediente ingrediente) throws PersistenciaException{
        EntityManager em = ConexionBD.crearConexion();
        
        if(existeIngredienteDuplicado(ingrediente.getId(), ingrediente.getNombre(), ingrediente.getUnidadMedida())){
            throw new PersistenciaException("Ya existe un ingrediente de nombre "+ingrediente.getNombre()+" y unidad de medida "+ingrediente.getUnidadMedida());
        }
        
        try{
            em.getTransaction().begin();
  
            em.persist(ingrediente);
            
            em.getTransaction().commit();
            return ingrediente;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al agregar ingrediente", e);     
        } finally {
            em.close();
        }
    }
    
    /**
     * Actualiza el stock de un ingrediente en la base de datos.
     * Modifica la cantidad disponible registrada.
     * @param ingrediente
     * @return
     * @throws PersistenciaException 
     */
    @Override
    public Ingrediente actualizarStock(Ingrediente ingrediente) throws PersistenciaException{
        EntityManager em = ConexionBD.crearConexion();
        try{
            em.getTransaction().begin();
            
            Ingrediente ingredienteBD = em.find(Ingrediente.class, ingrediente.getId());

            if (ingredienteBD == null) {
                throw new PersistenciaException("Ingrediente no encontrado");
            }

            ingredienteBD.setStockActual(ingrediente.getStockActual());
            
            em.getTransaction().commit();
            return ingredienteBD;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("No fue posible actualizar el ingrediente con id "+ingrediente.getId(), e);             
        } finally {
            em.close();
        }
    }

    /**
     * Busca un ingrediente por su identificador.
     * Recupera el ingrediente correspondiente si existe.
     * @param id
     * @return
     * @throws PersistenciaException 
     */
    @Override
    public Ingrediente buscarPorId(Long id) throws PersistenciaException{
        EntityManager em = ConexionBD.crearConexion();

        try {
            return em.find(Ingrediente.class, id);
        } catch (Exception e) {
            throw new PersistenciaException("No fue posible buscar el ingrediente con id " + id, e);
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene todos los ingredientes registrados en la base de datos.
     * Devuelve la lista completa de ingredientes.
     * @return
     * @throws PersistenciaException 
     */
    @Override
    public List<Ingrediente> obtenerIngredientes() throws PersistenciaException{
        EntityManager em = ConexionBD.crearConexion();

        try {
            String JPQL = "Select c FROM Ingrediente c";
            TypedQuery<Ingrediente> query = em.createQuery(JPQL, Ingrediente.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new PersistenciaException("No se pudieron encontrar los ingredientes", e);
        } finally {
            em.close();
        }
    }
    
    /**
     * Filtra los ingredientes registrados en la base de datos por nombre y unidad de medida.
     * @param nombre
     * @param unidad
     * @return
     * @throws PersistenciaException 
     */
    @Override
    public List<Ingrediente> buscarIngredientes(String nombre, Unidad unidad)throws PersistenciaException{
        EntityManager em = ConexionBD.crearConexion();
        
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Ingrediente> query = cb.createQuery(Ingrediente.class);
        Root<Ingrediente> ingrediente = query.from(Ingrediente.class);
        
        List<Predicate> filtros = new ArrayList<>();
        
        if (nombre != null && !nombre.trim().isEmpty()){
            filtros.add(cb.like(cb.lower(ingrediente.get("nombre")), "%" + nombre.toLowerCase() + "%" ));
        }
        
        if(unidad != null){
            filtros.add(cb.equal(ingrediente.get("unidadMedida"), unidad));
        }
        
        query.where(cb.and(filtros.toArray(Predicate[]::new)));
        
        query.select(ingrediente);
        return em.createQuery(query).getResultList();
    }

    /**
     * Obtiene el último identificador registrado.
     * Permite generar nuevos identificadores consecutivos.
     * @return
     * @throws PersistenciaException 
     */
    @Override
    public String obtenerUltimoIdentificador() throws PersistenciaException{
        EntityManager em = ConexionBD.crearConexion();
        
        try{
            String JPQL = "SELECT i.identificador FROM Ingrediente i ORDER BY i.identificador DESC";
            TypedQuery<String> query = em.createQuery(JPQL, String.class);
            query.setMaxResults(1);
            
            List<String> resultados = query.getResultList();
            
            if (resultados.isEmpty()) {
                return null;
            }
            return resultados.get(0);
        } catch (Exception e) {
            throw new PersistenciaException("Error al consultar el último identificador", e);
        } finally {
            em.close();
        }
    }

    /**
     * Elimina un ingrediente de la base de datos.
     * Remueve el registro correspondiente.
     * @param ingrediente
     * @return
     * @throws PersistenciaException 
     */
    @Override
    public Ingrediente eliminarIngrediente(Ingrediente ingrediente) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        
        try{
            em.getTransaction().begin();
            
            Ingrediente existente = em.find(Ingrediente.class, ingrediente.getId());
            
            if (existente == null) {
                throw new PersistenciaException("El ingrediente no existe, no se puede eliminar");
            }

            em.remove(existente);
            
            em.getTransaction().commit();
            
            return existente;
            
        } catch (PersistenciaException e) {
            throw new PersistenciaException("Error al eliminar el ingrediente", e);
        } finally {
            em.close();
        }
    }
    
    /**
     * Actualiza la información de un ingrediente en la base de datos.
     * Aplica los cambios sobre el registro existente.
     * @param ingrediente
     * @return
     * @throws PersistenciaException 
     */
    @Override
    public Ingrediente actualizarIngrediente(Ingrediente ingrediente) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        
        if(existeIngredienteDuplicado(ingrediente.getId(), ingrediente.getNombre(), ingrediente.getUnidadMedida())){
            throw new PersistenciaException("Ya existe un ingrediente de nombre "+ingrediente.getNombre()+" y unidad de medida "+ingrediente.getUnidadMedida());
        }

        try {
            em.getTransaction().begin();

            Ingrediente existente = em.find(Ingrediente.class, ingrediente.getId());

            if (existente == null) {
                throw new PersistenciaException("El ingrediente no existe, no se puede actualizar");
            }

            existente.setNombre(ingrediente.getNombre());
            existente.setUnidadMedida(ingrediente.getUnidadMedida());
            existente.setStockActual(ingrediente.getStockActual());
            existente.setStockMinimo(ingrediente.getStockMinimo());
            existente.setUrlImagen(ingrediente.getUrlImagen());

            em.merge(existente);

            em.getTransaction().commit();

            return existente;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al actualizar el ingrediente", e);

        } finally {
            em.close();
        }
    }

    /**
     * Verifica si existe un ingrediente con el mismo nombre y unidad.
     * Permite evitar registros duplicados.
     * @param id
     * @param nombre
     * @param unidad
     * @return
     * @throws PersistenciaException 
     */
    @Override
    public boolean existeIngredienteDuplicado(Long id, String nombre, Unidad unidad) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        
        String JPQL;
        TypedQuery<Long> query;

        
        try {
            
            if (id == null) {
                JPQL = "SELECT COUNT(i) FROM Ingrediente i where i.nombre = :nom AND i.unidadMedida = :uni";
                query = em.createQuery(JPQL, Long.class);
                query.setParameter("nom", nombre);
                query.setParameter("uni", unidad);
            } else {
                JPQL = "SELECT COUNT(i) FROM Ingrediente i where i.nombre = :nom AND i.unidadMedida = :uni AND i.id != :ID";
                query = em.createQuery(JPQL, Long.class); 
                query.setParameter("nom", nombre); 
                query.setParameter("uni", unidad); 
                query.setParameter("ID", id);
            }
            
            Long conteo = query.getSingleResult();
            return conteo > 0;
        } catch (Exception e) {
            throw new PersistenciaException("Error al verificar el ingrediente duplicado", e);

        } finally {
            em.close();
        }
    }
}
