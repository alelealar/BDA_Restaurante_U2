package daos;

import conexion.ConexionBD;
import entidades.Comanda;
import entidades.DetalleComanda;
import entidades.Mesa;
import excepciones.PersistenciaException;
import interfaces.IComandaDAO;
import java.util.List;
import javax.persistence.EntityManager;

/**
 * DAO encargado de la persistencia de la entidad Comanda.
 *
 * Proporciona operaciones CRUD utilizando JPA, incluyendo:
 * <ul>
 * <li>Guardar comandas</li>
 * <li>Actualizar comandas y sus detalles</li>
 * <li>Eliminar comandas</li>
 * <li>Consultas generales</li>
 * <li>Calcula automáticamente el total de la comanda</li>
 * <li>Mantiene la relación entre Comanda y DetalleComanda</li>
 * <li>Realiza rollback en caso de error</li>
 * </ul>
 *
 * Implementa patrón Singleton.
 *
 * @author Kaleb
 */
public class ComandaDAO implements IComandaDAO {

    private static ComandaDAO instancia;

    /**
     * Obtiene la instancia única del DAO.
     *
     * @return instancia de ComandaDAO
     */
    public static ComandaDAO getInstance() {
        if (instancia == null) {
            instancia = new ComandaDAO();
        }
        return instancia;
    }

    /**
     * Guarda una nueva comanda en la base de datos.
     *
     * Calcula el total automáticamente antes de persistir.
     *
     * @param comanda comanda a guardar
     * @return comanda persistida
     * @throws PersistenciaException si ocurre un error en la transacción
     */
    @Override
    public Comanda guardarComanda(Comanda comanda) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            em.getTransaction().begin();

            for (DetalleComanda d : comanda.getDetalles()) {
                comanda.agregarDetalle(d);
            }

            comanda.calcularTotal();

            em.persist(comanda);

            em.getTransaction().commit();
            return comanda;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al guardar comanda", e);
        } finally {
            em.close();
        }
    }

    /**
     * Actualiza una comanda existente.
     *
     * Sincroniza:
     * <ul>
     * <li>Datos generales</li>
     * <li>Detalles (agregar, actualizar, eliminar)</li>
     * </ul>
     *
     * También recalcula el total antes de guardar.
     *
     * @param comanda comanda con nuevos datos
     * @return comanda actualizada
     * @throws PersistenciaException si ocurre un error
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

            // Guardamos estado anterior
            var estadoAnterior = comandaRecuperada.getEstadoComanda();

            // Datos básicos
            comandaRecuperada.setCliente(comanda.getCliente());
            comandaRecuperada.setEstadoComanda(comanda.getEstadoComanda());
            comandaRecuperada.setFechaHora(comanda.getFechaHora());
            comandaRecuperada.setFolio(comanda.getFolio());

            List<DetalleComanda> actuales = comandaRecuperada.getDetalles();

            // Eliminar
            for (DetalleComanda existente : actuales.toArray(new DetalleComanda[0])) {
                boolean existe = comanda.getDetalles().stream()
                        .anyMatch(d -> d.getId() != null && d.getId().equals(existente.getId()));

                if (!existe) {
                    comandaRecuperada.removerDetalle(existente);
                    em.remove(existente);
                }
            }

            // Agregar / Actualizar
            for (DetalleComanda d : comanda.getDetalles()) {

                if (d.getId() == null) {
                    comandaRecuperada.agregarDetalle(d);
                    em.persist(d);

                } else {
                    DetalleComanda existente = em.find(DetalleComanda.class, d.getId());

                    if (existente != null) {
                        existente.setCantidad(d.getCantidad());
                        existente.setComentario(d.getComentario());
                        existente.setPrecioUnitario(d.getPrecioUnitario());
                    }
                }
            }

            //Calcular
            if (estadoAnterior != comandaRecuperada.getEstadoComanda()
                    && comandaRecuperada.getEstadoComanda().name().equals("ENTREGADA")) {

                comandaRecuperada.calcularTotal();
            }

            em.getTransaction().commit();
            return comandaRecuperada;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al actualizar comanda", e);
        } finally {
            em.close();
        }
    }

    /**
     * Elimina una comanda por su identificador.
     *
     * @param idComanda id de la comanda
     * @return true si se eliminó, false si no existe
     * @throws PersistenciaException si ocurre un error
     */
    @Override
    public boolean eliminarComanda(Long idComanda) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            em.getTransaction().begin();

            Comanda c = em.find(Comanda.class, idComanda);

            if (c == null) {
                return false;
            }

            em.remove(c);

            em.getTransaction().commit();
            return true;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al eliminar comanda", e);
        } finally {
            em.close();
        }
    }

    /**
     * Busca una comanda por su ID.
     *
     * @param id identificador
     * @return comanda encontrada o null
     * @throws PersistenciaException si ocurre error
     */
    @Override
    public Comanda buscarComandaPorId(Long id) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            return em.find(Comanda.class, id);
        } catch (Exception e) {
            throw new PersistenciaException("Error al buscar comanda", e);
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene todas las comandas registradas.
     *
     * @return lista de comandas
     * @throws PersistenciaException si ocurre error
     */
    @Override
    public List<Comanda> obtenerComandas() throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            return em.createQuery("SELECT c FROM Comanda c", Comanda.class)
                    .getResultList();
        } catch (Exception e) {
            throw new PersistenciaException("Error al obtener comandas", e);
        } finally {
            em.close();
        }
    }

    /**
     * Cuenta las comandas registradas en el día actual.
     *
     * @return número de comandas del día
     * @throws PersistenciaException si ocurre error
     */
    @Override
    public Long obtenerComandasDia() throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            return em.createQuery(
                    "SELECT COUNT(c) FROM Comanda c WHERE FUNCTION('DATE', c.fechaHora) = CURRENT_DATE",
                    Long.class
            ).getSingleResult();
        } catch (Exception e) {
            throw new PersistenciaException("Error al contar comandas", e);
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene todas las mesas registradas.
     *
     * @return lista de mesas
     * @throws PersistenciaException si ocurre error
     */
    @Override
    public List<Mesa> obtenerMesas() throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            return em.createQuery("SELECT m FROM Mesa m", Mesa.class)
                    .getResultList();
        } catch (Exception e) {
            throw new PersistenciaException("Error al obtener mesas", e);
        } finally {
            em.close();
        }
    }
}
