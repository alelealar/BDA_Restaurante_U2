package objetosNegocio;

import adaptadores.MesaAdapter;
import daos.MesaDAO;
import dtos.MesaDTO;
import entidades.Mesa;
import enumerators.EstadoMesa;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import interfaces.IMesaBO;
import interfaces.IMesaDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Implementación de la lógica de negocio para la entidad Mesa.
 *
 * Gestiona la inicialización, consulta y actualización de mesas, validando
 * reglas de negocio antes de interactuar con la capa DAO.
 *
 * @author Brian Kaleb Sandoval Rodríguez - 00000262741
 */
public class MesaBO implements IMesaBO {

    /**
     * Instancia singleton de MesaBO.
     */
    private static MesaBO instancia;

    /**
     * DAO de mesas.
     */
    private final IMesaDAO mesaDAO = new MesaDAO();

    /**
     * Logger para registrar eventos del sistema.
     */
    private static final Logger LOG = Logger.getLogger(MesaBO.class.getName());

    /**
     * Constructor privado para patrón Singleton.
     */
    private MesaBO() {
    }

    /**
     * Obtiene la instancia única de MesaBO.
     *
     * @return instancia de MesaBO.
     */
    public static MesaBO getInstance() {
        if (instancia == null) {
            instancia = new MesaBO();
        }
        return instancia;
    }

    /**
     * Inicializa mesas en el sistema.
     *
     * Solo se ejecuta si no existen mesas previamente registradas.
     *
     * @param cantidad número de mesas a crear.
     * @throws NegocioException si la cantidad es inválida o ya existen mesas.
     */
    @Override
    public void inicializarMesas(int cantidad) throws NegocioException {
        try {
            if (cantidad <= 0) {
                throw new NegocioException("La cantidad de mesas debe ser mayor a 0");
            }

            List<Mesa> existentes = mesaDAO.obtenerMesas();

            if (existentes != null && !existentes.isEmpty()) {
                throw new NegocioException("Las mesas ya han sido inicializadas previamente");
            }

            for (int i = 1; i <= cantidad; i++) {
                Mesa mesa = new Mesa();
                mesa.setNumero(i);
                mesa.setEstado(EstadoMesa.DISPONIBLE);

                mesaDAO.registrarMesa(mesa);
            }

            LOG.info("Mesas inicializadas correctamente: " + cantidad);

        } catch (PersistenciaException e) {
            LOG.severe("Error al inicializar mesas: " + e.getMessage());
            throw new NegocioException("No fue posible inicializar las mesas", e);
        }
    }

    /**
     * Obtiene todas las mesas registradas.
     *
     * @return lista de mesas en formato DTO.
     * @throws NegocioException si ocurre un error.
     */
    @Override
    public List<MesaDTO> obtenerMesas() throws NegocioException {
        try {
            List<Mesa> mesas = mesaDAO.obtenerMesas();
            List<MesaDTO> lista = new ArrayList<>();

            for (Mesa m : mesas) {
                lista.add(MesaAdapter.entidadADTO(m));
            }

            return lista;

        } catch (PersistenciaException e) {
            LOG.warning(() -> "Error al obtener mesas: " + e.getMessage());
            throw new NegocioException("No fue posible obtener las mesas", e);
        }
    }

    /**
     * Cambia el estado de una mesa.
     *
     * @param idMesa identificador de la mesa.
     * @param estado nuevo estado en formato String.
     * @throws NegocioException si los datos son inválidos.
     */
    @Override
    public void cambiarEstadoMesa(Long idMesa, String estado) throws NegocioException {
        try {
            if (idMesa == null) {
                throw new NegocioException("El id de la mesa no puede ser nulo");
            }

            if (estado == null || estado.isBlank()) {
                throw new NegocioException("El estado no puede estar vacío");
            }

            EstadoMesa estadoEnum;
            try {
                estadoEnum = EstadoMesa.valueOf(estado);
            } catch (IllegalArgumentException e) {
                throw new NegocioException("Estado de mesa inválido");
            }

            mesaDAO.cambiarEstadoMesa(idMesa, estadoEnum);

            LOG.info("Estado de mesa actualizado correctamente");

        } catch (PersistenciaException e) {
            LOG.severe("Error al cambiar estado de mesa: " + e.getMessage());
            throw new NegocioException("No fue posible cambiar el estado de la mesa", e);
        }
    }

    /**
     * Busca una mesa por su ID.
     *
     * @param id identificador de la mesa.
     * @return mesa encontrada en formato DTO.
     * @throws NegocioException si no existe.
     */
    @Override
    public MesaDTO buscarMesaPorId(Long id) throws NegocioException {
        try {
            if (id == null) {
                throw new NegocioException("El id no puede ser nulo");
            }

            Mesa mesa = mesaDAO.buscarMesaPorId(id);

            if (mesa == null) {
                throw new NegocioException("La mesa no existe");
            }

            return MesaAdapter.entidadADTO(mesa);

        } catch (PersistenciaException e) {
            LOG.warning(() -> "Error al buscar mesa: " + e.getMessage());
            throw new NegocioException("No fue posible buscar la mesa", e);
        }
    }

    /**
     * Eliimina una mesa por su id
     *
     * Este método elimina una mesa de la base de datos en base a su id.
     *
     * @param id Id de la mesa a eliminar
     * @throws NegocioException Si la cantidad es inválida o ocurre un error.
     */
    @Override
    public void eliminarMesa(Long id) throws NegocioException {
        try {
            if (id == null) {
                throw new NegocioException("El id no puede ser nulo");
            }

            Mesa mesa = mesaDAO.buscarMesaPorId(id);

            if (mesa == null) {
                throw new NegocioException("La mesa no existe");
            }

            mesaDAO.eliminarMesa(id);

        } catch (PersistenciaException e) {
            LOG.warning(() -> "Error al eliminar mesa: " + e.getMessage());
            throw new NegocioException("No fue posible eliminar la mesa", e);
        }
    }

}
