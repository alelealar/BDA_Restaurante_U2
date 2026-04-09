package daos;

import conexion.ConexionBD;
import entidades.Mesero;
import excepciones.PersistenciaException;
import interfaces.IMeseroDAO;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 * DAO para la entidad Mesero.
 *
 * Permite registrar y verificar la existencia de meseros.
 *
 * @author Brian Kaleb Sandoval Rodríguez - 00000262741
 */
public class MeseroDAO implements IMeseroDAO {
    
     /**
     * Instancia única de la clase.
     */
    private static MeseroDAO instancia;

    /**
     * Obtiene la instancia única del DAO.
     *
     * @return instancia de MeseroDAO.
     */
    public static MeseroDAO getInstance() {
        if (instancia == null) {
            instancia = new MeseroDAO();
        }
        return instancia;
    }

    
    
    /**
     * Busca un mesero por su usuario.
     *
     * @param usuario Nombre de usuario.
     * @return Mesero encontrado o null si no existe.
     * @throws PersistenciaException Si ocurre un error.
     */
    @Override
    public Mesero buscarPorUsuario(String usuario) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            String jpql = "SELECT m FROM Mesero m WHERE m.usuario = :usuario";
            TypedQuery<Mesero> query = em.createQuery(jpql, Mesero.class);
            query.setParameter("usuario", usuario);

            return query.getSingleResult();

        } catch (NoResultException e) {
            return null;

        } catch (Exception e) {
            throw new PersistenciaException("Error al buscar mesero", e);

        } finally {
            em.close();
        }
    }

    /**
     * Registra un nuevo mesero.
     *
     * @param mesero Mesero a registrar.
     * @throws PersistenciaException Si ocurre un error.
     */
    @Override
    public void registrarMesero(Mesero mesero) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            em.getTransaction().begin();
            em.persist(mesero);
            em.getTransaction().commit();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("No fue posible registrar el mesero", e);

        } finally {
            em.close();
        }
    }
}
