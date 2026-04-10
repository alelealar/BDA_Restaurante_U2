package objetosNegocio;

import adaptadores.ComandaAdapter;
import adaptadores.MesaAdapter;
import daos.ClienteDAO;
import daos.ComandaDAO;
import dtos.ClienteDTO;
import dtos.ComandaDTO;
import dtos.DetalleComandaDTO;
import dtos.MesaDTO;
import entidades.Cliente;
import entidades.Comanda;
import entidades.Mesa;
import enumerators.EstadoComandaDTO;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import interfaces.IClienteDAO;
import interfaces.IComandaBO;
import interfaces.IComandaDAO;
import interfaces.IMesaBO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

/**
 * Clase de negocio encargada de gestionar las operaciones relacionadas con las
 * comandas.
 *
 * Contiene la lógica de negocio para la creación, actualización, eliminación y
 * consulta de comandas, así como la validación de datos y generación de folios.
 *
 * Implementa el patrón Singleton para asegurar una única instancia en el
 * sistema.
 *
 * @author Brian Kaleb Sandoval Rodríguez - 00000262741
 */
public class ComandaBO implements IComandaBO {

    /**
     * Instancia única de la clase.
     */
    private static ComandaBO instancia;

    /**
     * DAO utilizado para operaciones de persistencia.
     */
    private final IComandaDAO comandaDAO = ComandaDAO.getInstance();

    /**
     * DAO utilizado para operaciones con clientes.
     */
    private final IClienteDAO clienteDAO = ClienteDAO.getInstance();

    /**
     * Logger para registrar eventos del sistema.
     */
    private static final Logger LOG = Logger.getLogger(ComandaBO.class.getName());

    /**
     * Constructor privado para patrón Singleton.
     */
    public ComandaBO() {
    }

    /**
     * Obtiene la instancia única de ComandaBO.
     *
     * @return instancia de ComandaBO.
     */
    public static ComandaBO getInstance() {
        if (instancia == null) {
            instancia = new ComandaBO();
        }
        return instancia;
    }

    /**
     * Guarda una nueva comanda en el sistema.
     *
     * Inicializa valores como fecha, estado, total y folio antes de persistir.
     *
     * @param comandaDTO Comanda a registrar.
     * @throws NegocioException Si ocurre un error de validación o persistencia.
     */
    @Override
    public void guardarComanda(ComandaDTO comandaDTO) throws NegocioException {
        try {
            validarCreacion(comandaDTO);
            validarDetalles(comandaDTO.getDetalles());

            comandaDTO.setFechaHora(LocalDateTime.now());
            comandaDTO.setEstadoComanda(EstadoComandaDTO.ABIERTA);
            comandaDTO.setTotal(0.0);

            Long consecutivo = comandaDAO.obtenerComandasDia() + 1;
            comandaDTO.setFolio(generarFolio(consecutivo));
            Cliente clienteEntidad;

            if (comandaDTO.getCliente() == null) {
                clienteEntidad = clienteDAO.obtenerOcrearClienteGeneral();
            } else {
                clienteEntidad = clienteDAO.buscarClientePorId(
                        comandaDTO.getCliente().getId()
                );
            }

            Comanda comanda = ComandaAdapter.dtoAEntidad(comandaDTO);

            comanda.setCliente(clienteEntidad);

            Comanda registrada = comandaDAO.guardarComanda(comanda);

            if (registrada.getId() != null) {
                LOG.info("Comanda registrada exitosamente.");
                IMesaBO mesaBO = MesaBO.getInstance();
                mesaBO.cambiarEstadoMesa(comandaDTO.getMesa().getId(), "OCUPADA");
            }

        } catch (PersistenciaException ex) {
            LOG.severe(() -> "Error al guardar la comanda: " + ex.getMessage());
            throw new NegocioException("No se pudo guardar la comanda", ex);
        }
    }

    /**
     * Elimina una comanda del sistema.
     *
     * @param idComanda Identificador de la comanda.
     * @throws NegocioException Si ocurre un error o no se elimina.
     */
    @Override
    public void eliminarComanda(Long idComanda) throws NegocioException {
        try {
            if (idComanda == null) {
                throw new NegocioException("El id de la comanda no puede ser nulo.");
            }

            ComandaDTO comanda = buscarComandaPorId(idComanda);

            boolean eliminada = comandaDAO.eliminarComanda(idComanda);

            if (!eliminada) {
                throw new NegocioException("No se pudo eliminar la comanda.");
            }

            LOG.info("Comanda eliminada correctamente.");

            IMesaBO mesaBO = MesaBO.getInstance();
            mesaBO.cambiarEstadoMesa(comanda.getMesa().getId(), "DISPONIBLE");

        } catch (PersistenciaException e) {
            LOG.warning(e.getMessage());
            throw new NegocioException("Error al eliminar la comanda", e);
        }
    }

    /**
     * Actualiza una comanda existente.
     *
     * @param comandaDTO Datos actualizados.
     * @throws NegocioException Si ocurre un error.
     */
    @Override
    public void actualizarComanda(ComandaDTO comandaDTO) throws NegocioException {
        try {
            validarActualizacion(comandaDTO);

            ComandaDTO comandaGuardada = buscarComandaPorId(comandaDTO.getId());
            if (comandaGuardada.getEstadoComanda() != EstadoComandaDTO.ABIERTA) {
                throw new NegocioException("No es posible modificar una comanda que ya fue cerrada o pagada.");
            }

            Comanda comanda = ComandaAdapter.dtoAEntidad(comandaDTO);
            Comanda actualizada = comandaDAO.actualizarComanda(comanda);

            if (actualizada != null) {
                LOG.info("Comanda actualizada correctamente.");

                if (comandaDTO.getEstadoComanda() != EstadoComandaDTO.ABIERTA) {
                    IMesaBO mesaBO = MesaBO.getInstance();
                    mesaBO.cambiarEstadoMesa(comandaDTO.getMesa().getId(), "DISPONIBLE");
                }
            }

        } catch (PersistenciaException e) {
            LOG.warning(e.getMessage());
            throw new NegocioException("Error al actualizar la comanda", e);
        }
    }

    /**
     * Busca una comanda por su identificador.
     *
     * @param idComanda Identificador.
     * @return Comanda encontrada.
     * @throws NegocioException Si no existe o hay error.
     */
    @Override
    public ComandaDTO buscarComandaPorId(Long idComanda) throws NegocioException {
        try {
            Comanda comanda = comandaDAO.buscarComandaPorId(idComanda);

            if (comanda == null) {
                throw new NegocioException("Comanda no encontrada.");
            }

            return ComandaAdapter.entidadADTO(comanda);

        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al buscar la comanda", ex);
        }
    }

    /**
     * Obtiene todas las comandas del sistema.
     *
     * @return Lista de comandas.
     * @throws NegocioException Si ocurre un error.
     */
    @Override
    public List<ComandaDTO> obtenerComandas() throws NegocioException {
        try {
            List<Comanda> comandas = comandaDAO.obtenerComandas();
            return ComandaAdapter.listaEntidadADTO(comandas);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al obtener comandas", e);
        }
    }

    /**
     * Obtiene todas las mesas del sistema.
     *
     * @return Lista de mesas.
     * @throws NegocioException Si ocurre un error.
     */
    @Override
    public List<MesaDTO> obtenerMesas() throws NegocioException {
        try {
            List<Mesa> mesas = comandaDAO.obtenerMesas();
            return MesaAdapter.listaEntidadADTO(mesas);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al obtener mesas", e);
        }
    }

    /**
     * Genera un folio único con formato OB-YYYYMMDD-XXX.
     *
     * @param consecutivo Número consecutivo del día.
     * @return Folio generado.
     */
    public String generarFolio(Long consecutivo) {
        String fecha = LocalDate.now().toString().replace("-", "");
        return String.format("OB-%s-%03d", fecha, consecutivo);
    }

    /**
     * Valida los datos para la creación de una comanda.
     *
     * @param dto Comanda a validar.
     * @throws NegocioException Si los datos son inválidos.
     */
    private void validarCreacion(ComandaDTO dto) throws NegocioException {
        if (dto == null) {
            throw new NegocioException("La comanda no puede ser nula");
        }

        if (dto.getMesa() == null) {
            throw new NegocioException("La mesa es obligatoria");
        }
    }

    /**
     * Valida los datos para actualizar una comanda.
     *
     * @param dto Comanda a validar.
     * @throws NegocioException Si los datos son inválidos.
     */
    private void validarActualizacion(ComandaDTO dto) throws NegocioException {
        if (dto == null) {
            throw new NegocioException("La comanda no puede ser nula");
        }

        if (dto.getId() == null) {
            throw new NegocioException("El ID es obligatorio para actualizar");
        }
    }

    /**
     * Valida un detalle de comanda.
     *
     * @param detalle Detalle a validar.
     * @throws NegocioException Si los datos son inválidos.
     */
    private void validarDetalles(List<DetalleComandaDTO> detalles) throws NegocioException {

        if (detalles == null || detalles.isEmpty()) {
            return; // ✅ permitido: comanda sin consumo
        }

        for (DetalleComandaDTO detalle : detalles) {

            if (detalle == null) {
                throw new NegocioException("El detalle no puede ser nulo");
            }

            if (detalle.getCantidad() <= 0) {
                throw new NegocioException("La cantidad debe ser mayor a 0");
            }

            if (detalle.getPrecioUnitario() < 0) {
                throw new NegocioException("El precio no puede ser negativo");
            }

            if (detalle.getIdProducto() == null) {
                throw new NegocioException("El producto es obligatorio en el detalle");
            }
        }
    }
}
