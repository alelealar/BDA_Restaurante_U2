package objetosNegocio;

import adaptadores.ComandaAdapter;
import adaptadores.MesaAdapter;
import daos.ClienteDAO;
import daos.ComandaDAO;
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
 * Implementa la lógica de negocio para la gestión de comandas.
 *
 * Se encarga de validar datos, comunicarse con la capa DAO y transformar
 * entidades a DTOs para la capa de presentación.
 *
 * Implementa patrón Singleton.
 *
 * @author Brian Kaleb Sandoval Rodríguez
 */
public class ComandaBO implements IComandaBO {

    private static ComandaBO instancia;

    private final IComandaDAO comandaDAO = ComandaDAO.getInstance();
    private final IClienteDAO clienteDAO = ClienteDAO.getInstance();

    private static final Logger LOG = Logger.getLogger(ComandaBO.class.getName());

    /**
     * Constructor privado para Singleton.
     */
    private ComandaBO() {
    }

    /**
     * Obtiene la única instancia de la clase.
     *
     * @return instancia de ComandaBO
     */
    public static ComandaBO getInstance() {
        if (instancia == null) {
            instancia = new ComandaBO();
        }
        return instancia;
    }

    /**
     * Registra una nueva comanda.
     *
     * Asigna fecha, folio, estado inicial y cliente correspondiente.
     *
     * @param comandaDTO datos de la comanda
     * @return comanda registrada
     * @throws NegocioException si ocurre un error
     */
    @Override
    public ComandaDTO guardarComanda(ComandaDTO comandaDTO) throws NegocioException {
        try {
            validarCreacion(comandaDTO);
            validarDetalles(comandaDTO.getDetalles());

            comandaDTO.setFechaHora(LocalDateTime.now());
            comandaDTO.setEstadoComanda(EstadoComandaDTO.ABIERTA);

            comandaDTO.setTotal(null);

            Long consecutivo = comandaDAO.obtenerComandasDia() + 1;
            comandaDTO.setFolio(generarFolio(consecutivo));

            Cliente cliente = obtenerClienteComanda(comandaDTO);

            Comanda entidad = ComandaAdapter.dtoAEntidad(comandaDTO);
            entidad.setCliente(cliente);

            Comanda guardada = comandaDAO.guardarComanda(entidad);

            cambiarMesaOcupada(comandaDTO.getMesa().getId());

            LOG.info("Comanda registrada correctamente.");

            return ComandaAdapter.entidadADTO(guardada);

        } catch (PersistenciaException ex) {
            throw new NegocioException("No fue posible guardar la comanda.", ex);
        }
    }

    /**
     * Elimina una comanda existente.
     *
     * @param idComanda identificador de la comanda
     * @throws NegocioException si ocurre un error
     */
    @Override
    public void eliminarComanda(Long idComanda) throws NegocioException {
        try {
            if (idComanda == null) {
                throw new NegocioException("El id de la comanda es obligatorio.");
            }

            ComandaDTO comanda = buscarComandaPorId(idComanda);

            boolean eliminada = comandaDAO.eliminarComanda(idComanda);

            if (!eliminada) {
                throw new NegocioException("No fue posible eliminar la comanda.");
            }

            cambiarMesaDisponible(comanda.getMesa().getId());

        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al eliminar la comanda.", ex);
        }
    }

    /**
     * Actualiza una comanda existente.
     *
     * @param comandaDTO nuevos datos
     * @return comanda actualizada
     * @throws NegocioException si ocurre un error
     */
    @Override
    public ComandaDTO actualizarComanda(ComandaDTO comandaDTO) throws NegocioException {
        try {
            validarActualizacion(comandaDTO);
            validarDetalles(comandaDTO.getDetalles());

            ComandaDTO actual = buscarComandaPorId(comandaDTO.getId());

            if (actual.getEstadoComanda() != EstadoComandaDTO.ABIERTA) {
                throw new NegocioException("Solo se pueden modificar comandas abiertas.");
            }

            Cliente cliente = obtenerClienteComanda(comandaDTO);

            Comanda entidad = ComandaAdapter.dtoAEntidad(comandaDTO);
            entidad.setCliente(cliente);

            Comanda actualizada = comandaDAO.actualizarComanda(entidad);

            if (comandaDTO.getEstadoComanda() != EstadoComandaDTO.ABIERTA) {
                cambiarMesaDisponible(comandaDTO.getMesa().getId());
            }

            LOG.info("Comanda actualizada correctamente.");

            return ComandaAdapter.entidadADTO(actualizada);

        } catch (PersistenciaException ex) {
            throw new NegocioException("No fue posible actualizar la comanda.", ex);
        }
    }

    /**
     * Busca una comanda por su identificador.
     *
     * @param idComanda id de la comanda
     * @return comanda encontrada
     * @throws NegocioException si no existe o hay error
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
            throw new NegocioException("Error al buscar la comanda.", ex);
        }
    }

    /**
     * Obtiene todas las comandas registradas.
     *
     * @return lista de comandas
     * @throws NegocioException si ocurre un error
     */
    @Override
    public List<ComandaDTO> obtenerComandas() throws NegocioException {
        try {
            return ComandaAdapter.listaEntidadADTO(
                    comandaDAO.obtenerComandas()
            );
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al obtener comandas.", ex);
        }
    }

    /**
     * Obtiene todas las mesas registradas.
     *
     * @return lista de mesas
     * @throws NegocioException si ocurre un error
     */
    @Override
    public List<MesaDTO> obtenerMesas() throws NegocioException {
        try {
            List<Mesa> mesas = comandaDAO.obtenerMesas();
            return MesaAdapter.listaEntidadADTO(mesas);

        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al obtener mesas.", ex);
        }
    }

    /**
     * Genera un folio único con formato OB-YYYYMMDD-XXX.
     *
     * @param consecutivo número consecutivo diario
     * @return folio generado
     */
    public String generarFolio(Long consecutivo) {
        String fecha = LocalDate.now().toString().replace("-", "");
        return String.format("OB-%s-%03d", fecha, consecutivo);
    }

    /**
     * Obtiene el cliente que tendrá la comanda.
     *
     * @param dto comanda recibida
     * @return cliente entidad
     * @throws PersistenciaException si ocurre un error
     */
    private Cliente obtenerClienteComanda(ComandaDTO dto) throws PersistenciaException {
        if (dto.getCliente() == null) {
            return clienteDAO.obtenerOcrearClienteGeneral();
        }

        return clienteDAO.buscarClientePorId(dto.getCliente().getId());
    }

    /**
     * Cambia una mesa a estado ocupada.
     *
     * @param idMesa id de la mesa
     * @throws NegocioException si ocurre error
     */
    private void cambiarMesaOcupada(Long idMesa) throws NegocioException {
        IMesaBO mesaBO = MesaBO.getInstance();
        mesaBO.cambiarEstadoMesa(idMesa, "OCUPADA");
    }

    /**
     * Cambia una mesa a estado disponible.
     *
     * @param idMesa id de la mesa
     * @throws NegocioException si ocurre error
     */
    private void cambiarMesaDisponible(Long idMesa) throws NegocioException {
        IMesaBO mesaBO = MesaBO.getInstance();
        mesaBO.cambiarEstadoMesa(idMesa, "DISPONIBLE");
    }

    /**
     * Valida datos mínimos para registrar.
     *
     * @param dto comanda a validar
     * @throws NegocioException si es inválida
     */
    private void validarCreacion(ComandaDTO dto) throws NegocioException {
        if (dto == null) {
            throw new NegocioException("La comanda no puede ser nula.");
        }

        if (dto.getMesa() == null) {
            throw new NegocioException("La mesa es obligatoria.");
        }
    }

    /**
     * Valida datos mínimos para actualizar.
     *
     * @param dto comanda a validar
     * @throws NegocioException si es inválida
     */
    private void validarActualizacion(ComandaDTO dto) throws NegocioException {
        validarCreacion(dto);

        if (dto.getId() == null) {
            throw new NegocioException("El id es obligatorio.");
        }
    }

    /**
     * Valida la lista de detalles de una comanda.
     *
     * @param detalles lista de detalles
     * @throws NegocioException si algún detalle es inválido
     */
    private void validarDetalles(List<DetalleComandaDTO> detalles) throws NegocioException {
        if (detalles == null || detalles.isEmpty()) {
            return;
        }

        for (DetalleComandaDTO detalle : detalles) {

            if (detalle == null) {
                throw new NegocioException("Existe un detalle nulo.");
            }

            if (detalle.getIdProducto() == null) {
                throw new NegocioException("El producto es obligatorio.");
            }

            if (detalle.getCantidad() <= 0) {
                throw new NegocioException("La cantidad debe ser mayor a cero.");
            }

            if (detalle.getPrecioUnitario() < 0) {
                throw new NegocioException("El precio no puede ser negativo.");
            }
        }
    }
}
