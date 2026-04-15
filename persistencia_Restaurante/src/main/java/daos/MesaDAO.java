package daos;

import conexion.ConexionBD;
import entidades.Mesa;
import enumerators.EstadoMesa;
import excepciones.PersistenciaException;
import interfaces.IMesaDAO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Implementación del DAO para la entidad Mesa.
 *
 * Proporciona métodos para la gestión de mesas en la base de datos, incluyendo
 * inicialización, consulta, búsqueda y actualización de estado.
 *
 * Utiliza JPA para la persistencia de datos.
 *
 * @author Kaleb
 */
public class MesaDAO implements IMesaDAO {

    private static MesaDAO instancia;

    public MesaDAO() {

    }

    public static MesaDAO getInstance() {
        if (instancia == null) {
            instancia = new MesaDAO();
        }
        return instancia;
    }

    /**
     * Crea una mesa en la base de datos.
     *
     * @param mesa Mesa a crear.
     * @throws PersistenciaException Si ocurre un error durante la persistencia.
     */
    @Override
    public void registrarMesa(Mesa mesa) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            em.getTransaction().begin();

            em.persist(mesa);

            em.getTransaction().commit();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("No fue posible registrar la mesa", e);

        } finally {
            em.close();
        }
    }

    /**
     * Elimina una mesa en la base de datos.
     *
     * @param idMesa id de la mesa a eliminar.
     * @return true si la mesa fue eliminada correctamente, false en caso
     * contrario.
     * @throws PersistenciaException Si ocurre un error durante la persistencia.
     */
    @Override
    public boolean eliminarMesa(Long idMesa) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            em.getTransaction().begin();

            Mesa m = em.find(Mesa.class, idMesa);

            if (m == null) {
                return false;
            }

            em.remove(m);

            em.getTransaction().commit();
            return true;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al eliminar la mesa", e);
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene todas las mesas registradas en la base de datos.
     *
     * @return Lista de mesas.
     * @throws PersistenciaException Si ocurre un error durante la consulta.
     */
    @Override
    public List<Mesa> obtenerMesas() throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            String jpql = "SELECT m FROM Mesa m";
            TypedQuery<Mesa> query = em.createQuery(jpql, Mesa.class);
            return query.getResultList();

        } catch (Exception e) {
            throw new PersistenciaException("No fue posible obtener las mesas", e);

        } finally {
            em.close();
        }
    }

    /**
     * Busca una mesa por su identificador.
     *
     * @param id Identificador de la mesa.
     * @return Mesa encontrada o null si no existe.
     * @throws PersistenciaException Si ocurre un error durante la búsqueda.
     */
    @Override
    public Mesa buscarMesaPorId(Long id) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            return em.find(Mesa.class, id);

        } catch (Exception e) {
            throw new PersistenciaException("No fue posible buscar la mesa con id: " + id, e);

        } finally {
            em.close();
        }
    }

    /**
     * Cambia el estado de una mesa existente.
     *
     * Busca la mesa por su ID y actualiza su estado en la base de datos.
     *
     * @param idMesa Identificador de la mesa.
     * @param estado Nuevo estado a asignar.
     * @throws PersistenciaException Si ocurre un error o la mesa no existe.
     */
    @Override
    public void cambiarEstadoMesa(Long idMesa, EstadoMesa estado) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            em.getTransaction().begin();

            Mesa mesa = em.find(Mesa.class, idMesa);

            if (mesa == null) {
                throw new PersistenciaException("La mesa con ID " + idMesa + " no existe");
            }

            mesa.setEstado(estado);

            em.getTransaction().commit();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("No fue posible actualizar el estado de la mesa", e);

        } finally {
            em.close();
        }
    }

}
