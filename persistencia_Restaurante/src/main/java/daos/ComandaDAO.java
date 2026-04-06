package daos;

import conexion.ConexionBD;
import entidades.Comanda;
import entidades.DetalleComanda;
import excepciones.PersistenciaException;
import interfaces.IComandaDAO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Implementación de la interfaz IComandaDAO para la gestión de comandas.
 *
 * Esta clase utiliza JPA para realizar operaciones CRUD sobre la entidad
 * Comanda, incluyendo persistencia, eliminación, actualización y consultas.
 *
 * Implementa el patrón Singleton para asegurar una única instancia.
 *
 * @author Brian Kaleb Sandoval Rodríguez - 00000262741
 */
public class ComandaDAO implements IComandaDAO {

    /**
     * Instancia única de la clase.
     */
    private static ComandaDAO instancia;

    /**
     * Obtiene la instancia única del DAO.
     *
     * @return instancia de ComandaDAO.
     */
    public static ComandaDAO getInstance() {
        if (instancia == null) {
            instancia = new ComandaDAO();
        }
        return instancia;
    }

    /**
     * Guarda una comanda en la base de datos asegurando la relación con sus
     * detalles.
     *
     * @param comanda Comanda a guardar.
     * @return Comanda persistida.
     * @throws PersistenciaException Si ocurre un error.
     */
    @Override
    public Comanda guardarComanda(Comanda comanda) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            em.getTransaction().begin();
            if (comanda.getDetalles() != null) {
                for (DetalleComanda d : comanda.getDetalles()) {
                    d.setComanda(comanda);
                }
            }

            em.persist(comanda);
            em.getTransaction().commit();

            return comanda;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("No fue posible guardar la comanda " + comanda.getFolio(), e);
        } finally {
            em.close();
        }
    }

    /**
     * Elimina una comanda por su identificador.
     *
     * @param idComanda Identificador de la comanda.
     * @return true si se eliminó correctamente, false si no existe.
     * @throws PersistenciaException Si ocurre un error.
     */
    @Override
    public boolean eliminarComanda(Long idComanda) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            Comanda comandaObtenida = em.find(Comanda.class, idComanda);

            if (comandaObtenida == null) {
                return false;
            }

            em.getTransaction().begin();
            em.remove(comandaObtenida);
            em.getTransaction().commit();

            return true;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("No fue posible eliminar la comanda con id " + idComanda, e);
        } finally {
            em.close();
        }
    }

    /**
     * Actualiza una comanda existente, gestionando correctamente sus detalles.
     *
     * @param comanda Comanda con información actualizada.
     * @return Comanda actualizada.
     * @throws PersistenciaException Si ocurre un error.
     */
    @Override
    public Comanda actualizarComanda(Comanda comanda) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            em.getTransaction().begin();

            Comanda comandaRecuperada = em.find(Comanda.class, comanda.getId());

            if (comandaRecuperada == null) {
                throw new PersistenciaException("Comanda no encontrada");
            }

            // actualizar datos básicos
            comandaRecuperada.setCliente(comanda.getCliente());
            comandaRecuperada.setEstadoComanda(comanda.getEstadoComanda());
            comandaRecuperada.setFechaHora(comanda.getFechaHora());
            comandaRecuperada.setFolio(comanda.getFolio());
            comandaRecuperada.setTotal(comanda.getTotal());

            // manejar los detalles
            comandaRecuperada.getDetalles().clear();

            if (comanda.getDetalles() != null) {
                for (DetalleComanda d : comanda.getDetalles()) {
                    d.setComanda(comandaRecuperada);
                    comandaRecuperada.getDetalles().add(d);
                }
            }

            em.getTransaction().commit();

            return comandaRecuperada;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("No fue posible actualizar la comanda " + comanda.getFolio(), e);
        } finally {
            em.close();
        }
    }

    /**
     * Busca una comanda por su identificador.
     *
     * @param idComanda Identificador de la comanda.
     * @return Comanda encontrada.
     * @throws PersistenciaException Si ocurre un error.
     */
    @Override
    public Comanda buscarComandaPorId(Long idComanda) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            return em.find(Comanda.class, idComanda);
        } catch (Exception e) {
            throw new PersistenciaException("No fue posible buscar la comanda con id " + idComanda, e);
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene todas las comandas junto con sus detalles.
     *
     * @return Lista de comandas.
     * @throws PersistenciaException Si ocurre un error.
     */
    @Override
    public List<Comanda> obtenerComandas() throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            String JPQL = "SELECT c FROM Comanda c";
            TypedQuery<Comanda> query = em.createQuery(JPQL, Comanda.class);

            return query.getResultList();

        } catch (Exception e) {
            throw new PersistenciaException("No fue posible consultar las comandas", e);
        } finally {
            em.close();
        }
    }
}
