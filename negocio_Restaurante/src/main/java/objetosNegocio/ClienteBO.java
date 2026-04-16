package objetosNegocio;

import adaptadores.ClienteAdapter;
import daos.ClienteDAO;
import dtos.ClienteDTO;
import dtos.ClienteNuevoDTO;
import entidades.Cliente;
import entidades.ClienteFrecuente;
import entidades.ClienteGeneral;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import interfaces.IClienteBO;
import interfaces.IClienteDAO;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

/**
 * Implementación de la interfaz IClienteBO que contiene la lógica de negocio
 * para la gestión de clientes.
 *
 * Esta clase se encarga de validar los datos recibidos desde las capas
 * superiores, transformar DTOs a entidades y coordinar las operaciones con la
 * capa de acceso a datos (DAO).
 *
 * También gestiona reglas de negocio, validaciones, control de duplicados y
 * manejo de excepciones.
 *
 * Además, registra eventos relevantes del sistema mediante Logger.
 *
 * @author Brian Kaleb Sandoval Rodriguez - 00000262741
 * @author Alejandra Leal Armenta - 00000262719
 * @author Maria Jose Valdez Iglesias - 00000262775
 */
public class ClienteBO implements IClienteBO {

    private static ClienteBO instancia;

    /**
     * Constructor por defecto.
     */
    public ClienteBO() {
    }

    /**
     * Obtiene la instancia única de ClienteBO (Singleton).
     *
     * @return instancia de ClienteBO
     */
    public static ClienteBO getInstance() {
        if (instancia == null) {
            instancia = new ClienteBO();
        }
        return instancia;
    }

    /**
     * DAO utilizado para interactuar con la capa de persistencia de clientes.
     */
    private final IClienteDAO clienteDAO = ClienteDAO.getInstance();

    /**
     * Logger utilizado para registrar eventos y errores del sistema.
     */
    private static final Logger LOG = Logger.getLogger(ClienteBO.class.getName());

    /**
     * Registra un nuevo cliente en el sistema.
     *
     * Valida los datos del cliente, aplica reglas de negocio como unicidad de
     * correo y teléfono, convierte el DTO a entidad y delega la persistencia
     * al DAO.
     *
     * @param clienteDTO objeto ClienteNuevoDTO con los datos del cliente a registrar
     * @throws NegocioException si los datos no son válidos o ocurre un error en persistencia
     */
    @Override
    public void registrarCliente(ClienteNuevoDTO clienteDTO) throws NegocioException {
        try {
            validarDatosNuevoDTO(clienteDTO);

            if (clienteDTO.getCorreo() == null || clienteDTO.getCorreo().trim().isEmpty()) {
                clienteDTO.setCorreo("sin correo");
            }

            Cliente clienteEntidad = ClienteAdapter.dtoAEntidad(clienteDTO);
            clienteEntidad.setFechaRegistro(LocalDate.now());

            if (clienteDAO.existeCorreo(clienteEntidad.getCorreo(), clienteEntidad.getId())) {
                throw new NegocioException("El correo ya está registrado");
            }

            if (clienteDAO.existeTelefono(clienteEntidad.getTelefono(), clienteEntidad.getId())) {
                throw new NegocioException("El teléfono ya está registrado");
            }

            clienteDAO.guardarCliente(clienteEntidad);

            LOG.info(() -> "Cliente registrado correctamente: " + clienteDTO);

        } catch (PersistenciaException ex) {
            LOG.warning(() -> "Error al registrar cliente: " + clienteDTO);
            throw new NegocioException("No fue posible registrar el cliente", ex);
        }
    }

    /**
     * Registra un cliente frecuente en el sistema.
     *
     * Incluye validaciones adicionales relacionadas con fidelización como
     * puntos, visitas y total gastado.
     *
     * @param clienteDTO objeto ClienteNuevoDTO con los datos del cliente frecuente
     * @throws NegocioException si los datos no son válidos o ocurre un error en persistencia
     */
    @Override
    public void registrarClienteFrecuente(ClienteNuevoDTO clienteDTO) throws NegocioException {
        try {
            validarDatosNuevoDTO(clienteDTO);

            if (clienteDTO.getCorreo() == null || clienteDTO.getCorreo().trim().isEmpty()) {
                clienteDTO.setCorreo("sin correo");
            }

            if (clienteDTO.getPuntos() == null) clienteDTO.setPuntos(0L);
            if (clienteDTO.getNumVisitas() == null) clienteDTO.setNumVisitas(0);
            if (clienteDTO.getTotalGastado() == null) clienteDTO.setTotalGastado(0.0);

            ClienteFrecuente clienteEntidad = ClienteAdapter.dtoAFrecuente(clienteDTO);
            clienteEntidad.setFechaRegistro(LocalDate.now());

            if (clienteDAO.existeCorreo(clienteEntidad.getCorreo(), clienteEntidad.getId())) {
                throw new NegocioException("El correo ya está registrado");
            }

            if (clienteDAO.existeTelefono(clienteEntidad.getTelefono(), clienteEntidad.getId())) {
                throw new NegocioException("El teléfono ya está registrado");
            }

            clienteDAO.guardarCliente(clienteEntidad);

            LOG.info(() -> "Cliente frecuente registrado correctamente: " + clienteDTO);

        } catch (PersistenciaException ex) {
            LOG.warning(() -> "Error al registrar cliente frecuente: " + clienteDTO);
            throw new NegocioException("No fue posible registrar el cliente frecuente", ex);
        }
    }

    /**
     * Actualiza la información de un cliente existente.
     *
     * Valida los datos, verifica duplicados y actualiza la entidad en la base de datos.
     *
     * @param clienteDTO objeto ClienteDTO con la información actualizada
     * @throws NegocioException si ocurre un error en validación o persistencia
     */
    @Override
    public void actualizarCliente(ClienteDTO clienteDTO) throws NegocioException {
        try {
            validarDatosDTO(clienteDTO);

            if (clienteDTO.getCorreo() == null || clienteDTO.getCorreo().trim().isEmpty()) {
                clienteDTO.setCorreo(null);
            }

            Cliente clienteEntidad = ClienteAdapter.dtoAEntidad(clienteDTO);

            if (clienteDAO.existeCorreo(clienteEntidad.getCorreo(), clienteEntidad.getId())) {
                throw new NegocioException("El correo ya está registrado");
            }

            if (clienteDAO.existeTelefono(clienteEntidad.getTelefono(), clienteEntidad.getId())) {
                throw new NegocioException("El teléfono ya está registrado");
            }

            clienteDAO.actualizarCliente(clienteEntidad);

            LOG.info(() -> "Cliente actualizado correctamente: " + clienteDTO);

        } catch (PersistenciaException ex) {
            LOG.warning(() -> "Error al actualizar cliente: " + clienteDTO);
            throw new NegocioException("No fue posible actualizar el cliente", ex);
        }
    }

    /**
     * Busca un cliente por su identificador único.
     *
     * @param id identificador del cliente
     * @return ClienteDTO con la información del cliente encontrado
     * @throws NegocioException si no se encuentra el cliente o ocurre un error
     */
    @Override
    public ClienteDTO buscarClientePorId(Long id) throws NegocioException {
        try {
            Cliente cliente = clienteDAO.buscarClientePorId(id);

            if (cliente == null) {
                throw new NegocioException("Cliente no encontrado con ID: " + id);
            }

            LOG.info(() -> "Cliente encontrado con ID: " + id);

            return ClienteAdapter.entidadADTO(cliente);

        } catch (PersistenciaException ex) {
            LOG.warning(() -> "Error al buscar cliente ID: " + id);
            throw new NegocioException("No fue posible buscar el cliente", ex);
        }
    }

    /**
     * Elimina un cliente del sistema.
     *
     * @param id identificador del cliente
     * @throws NegocioException si ocurre un error o el cliente no existe
     */
    @Override
    public void eliminarCliente(Long id) throws NegocioException {
        try {
            boolean eliminado = clienteDAO.eliminarCliente(id);

            if (!eliminado) {
                throw new NegocioException("El cliente no existe o ya fue eliminado");
            }

            LOG.info(() -> "Cliente eliminado con ID: " + id);

        } catch (PersistenciaException ex) {
            LOG.warning(() -> "Error al eliminar cliente ID: " + id);
            throw new NegocioException("No fue posible eliminar el cliente", ex);
        }
    }

    /**
     * Obtiene el cliente general del sistema.
     *
     * Este cliente se utiliza cuando una comanda no está asociada a un cliente registrado.
     *
     * @return ClienteDTO del cliente general
     * @throws NegocioException si ocurre un error al obtenerlo
     */
    @Override
    public ClienteDTO obtenerClienteGeneral() throws NegocioException {
        try {
            ClienteGeneral clienteGeneral = clienteDAO.obtenerOcrearClienteGeneral();

            if (clienteGeneral == null) {
                throw new NegocioException("No existe cliente general en el sistema");
            }

            return ClienteAdapter.entidadADTO(clienteGeneral);

        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al obtener cliente general", ex);
        }
    }

    /**
     * Obtiene todos los clientes registrados.
     *
     * @return lista de ClienteDTO
     * @throws NegocioException si ocurre un error en la consulta
     */
    @Override
    public List<ClienteDTO> obtenerClientes() throws NegocioException {
        try {
            List<Cliente> clientes = clienteDAO.obtenerClientes();
            return ClienteAdapter.listaEntidadADTO(clientes);

        } catch (PersistenciaException ex) {
            throw new NegocioException("No fue posible obtener los clientes", ex);
        }
    }

    /**
     * Valida los datos de un cliente nuevo antes de persistirlo.
     *
     * @param clienteDto objeto ClienteNuevoDTO
     * @throws NegocioException si algún dato obligatorio es inválido
     */
    private void validarDatosNuevoDTO(ClienteNuevoDTO clienteDto) throws NegocioException {
        if (clienteDto.getNombres() == null || clienteDto.getNombres().trim().isEmpty()) {
            throw new NegocioException("El nombre es obligatorio");
        }

        if (clienteDto.getApellidoPaterno() == null || clienteDto.getApellidoPaterno().trim().isEmpty()) {
            throw new NegocioException("El apellido paterno es obligatorio");
        }

        if (clienteDto.getTelefono() == null || clienteDto.getTelefono().trim().isEmpty()) {
            throw new NegocioException("El teléfono es obligatorio");
        }
    }

    /**
     * Valida los datos de un cliente existente.
     *
     * @param clienteDto objeto ClienteDTO
     * @throws NegocioException si los datos no son válidos
     */
    private void validarDatosDTO(ClienteDTO clienteDto) throws NegocioException {
        if (clienteDto.getNombres() == null || clienteDto.getNombres().trim().isEmpty()) {
            throw new NegocioException("El nombre es obligatorio");
        }

        if (clienteDto.getApellidoPaterno() == null || clienteDto.getApellidoPaterno().trim().isEmpty()) {
            throw new NegocioException("El apellido paterno es obligatorio");
        }

        if (clienteDto.getTelefono() == null || clienteDto.getTelefono().trim().isEmpty()) {
            throw new NegocioException("El teléfono es obligatorio");
        }
    }
}