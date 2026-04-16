package objetosNegocio;

import daos.MeseroDAO;
import entidades.Mesero;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import interfaces.IMeseroBO;
import interfaces.IMeseroDAO;
import java.util.logging.Logger;

/**
 * Implementación de la lógica de negocio para la entidad Mesero.
 *
 * Esta clase permite validar, consultar y registrar meseros en el sistema,
 * aplicando reglas de negocio antes de interactuar con la capa de persistencia.
 *
 * @author Brian Kaleb Sandoval Rodríguez - 00000262741
 */
public class MeseroBO implements IMeseroBO {

    private static final Logger LOG = Logger.getLogger(MeseroBO.class.getName());

    private final IMeseroDAO meseroDAO = MeseroDAO.getInstance();

    /**
     * Instancia única de la clase (patrón Singleton).
     */
    private static MeseroBO instancia;

    /**
     * Obtiene la instancia única de MeseroBO.
     *
     * @return instancia de MeseroBO
     */
    public static MeseroBO getInstance() {
        if (instancia == null) {
            instancia = new MeseroBO();
        }
        return instancia;
    }

    /**
     * Verifica si un mesero existe en el sistema.
     *
     * @param usuario nombre de usuario del mesero
     * @return true si el mesero existe, false en caso contrario
     * @throws NegocioException si el usuario es inválido o ocurre un error en persistencia
     */
    @Override
    public boolean existeMesero(String usuario) throws NegocioException {
        try {
            if (usuario == null || usuario.isBlank()) {
                throw new NegocioException("El usuario no puede estar vacío");
            }

            Mesero mesero = meseroDAO.buscarPorUsuario(usuario);

            return mesero != null;

        } catch (PersistenciaException e) {
            LOG.severe(() -> "Error al verificar mesero: " + e.getMessage());
            throw new NegocioException("No fue posible verificar el mesero", e);
        }
    }

    /**
     * Registra un nuevo mesero en el sistema.
     *
     * @param usuario nombre de usuario del mesero
     * @throws NegocioException si el usuario es inválido o ya existe
     */
    @Override
    public void registrarMesero(String usuario) throws NegocioException {
        try {
            if (usuario == null || usuario.isBlank()) {
                throw new NegocioException("El usuario no puede estar vacío");
            }

            if (meseroDAO.buscarPorUsuario(usuario) != null) {
                throw new NegocioException("El mesero ya existe");
            }

            Mesero mesero = new Mesero(usuario);
            meseroDAO.registrarMesero(mesero);

            LOG.info("Mesero registrado correctamente");

        } catch (PersistenciaException e) {
            LOG.severe(() -> "Error al registrar mesero: " + e.getMessage());
            throw new NegocioException("No fue posible registrar el mesero", e);
        }
    }
}