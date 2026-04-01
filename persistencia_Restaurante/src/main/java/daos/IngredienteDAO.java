/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package daos;

import conexion.ConexionBD;
import entidades.Ingrediente;
import excepciones.PersistenciaException;
import interfaces.IIngredienteDAO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;


/**
 *
 * @author Alejandra Leal Armenta, 262719
 */

public class IngredienteDAO implements IIngredienteDAO{
    
    private static IngredienteDAO instancia;

    public IngredienteDAO() {
    }
    
    public static IngredienteDAO getInstance(){
        if(instancia == null){
            instancia = new IngredienteDAO();
        }
        return instancia;
    }

    @Override
    public Ingrediente agregarIngrediente(Ingrediente ingrediente) throws PersistenciaException{
        EntityManager em = ConexionBD.crearConexion();
        try{
            em.getTransaction().begin();
            
            em.persist(ingrediente);
            
            em.getTransaction().commit();
            return ingrediente;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("No fue posible guardar el ingrediente " + ingrediente.getNombre(), e);             
        } finally {
            em.close();
        }
    }

    @Override
    public Ingrediente actualizarStock(Ingrediente ingrediente) throws PersistenciaException{
        EntityManager em = ConexionBD.crearConexion();
        try{
            em.getTransaction().begin();
            
            Ingrediente ingredienteBD = em.find(Ingrediente.class, ingrediente.getId());

            if (ingredienteBD == null) {
                throw new PersistenciaException("Ingrediente no encontrado");
            }

            ingredienteBD.setStock(ingrediente.getStock());
            
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
    
}
