package objetosNegocio;

import adaptadores.ClienteAdapter;
import daos.ClienteDAO;
import dtos.ClienteDTO;
import dtos.ClienteNuevoDTO;
import entidades.Cliente;
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
 * Esta clase se encarga de validar los datos recibidos desde capas superiores,
 * convertir DTOs a entidades y delegar las operaciones a la capa de acceso a
 * datos (DAO).
 *
 * Además, maneja excepciones de negocio y registra eventos mediante Logger.
 *
 * @author Brian Kaleb Sandoval Rodriguez - 00000262741
 * @author Alejandra Leal Armenta - 00000262719
 * @author Maria Jose Valdez Iglesias - 00000262775
 */
public class ClienteBO implements IClienteBO {

    private static ClienteBO instancia;

    public ClienteBO() {
    }

    public static ClienteBO getInstance() {
        if (instancia == null) {
            instancia = new ClienteBO();
        }
        return instancia;
    }

    /**
     * Objeto DAO utilizado para interactuar con la base de datos.
     */
    private final IClienteDAO clienteDAO = ClienteDAO.getInstance();

    /**
     * Logger para registrar eventos y errores del sistema.
     */
    private static final Logger LOG = Logger.getLogger(ClienteBO.class.getName());

    /**
     * Registra un nuevo cliente en el sistema.Valida los datos del cliente,
     * convierte el DTO a entidad y delega la operación al DAO.
     *
     * Maneja errores de persistencia y los transforma en excepciones de
     * negocio.
     *
     * @param clienteDTO objeto ClienteNuevoDTO con los datos del cliente
     * @throws NegocioException si ocurre un error en la validación o
     * persistencia
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

            LOG.info(() -> "El cliente fue agregado correctamente: " + clienteDTO.toString());

        } catch (PersistenciaException ex) {
            LOG.warning(() -> "No fue posible agregar al cliente: " + clienteDTO.toString());
            throw new NegocioException("No fue posible agregar al cliente", ex);
        }
    }

    /**
     * Actualiza la información de un cliente existente.
     *
     * @param clienteDTO objeto ClienteNuevoDTO con la información actualizada
     * @throws NegocioException si ocurre un error durante la operación
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

            LOG.info(() -> "El cliente fue actualizado correctamente: " + clienteDTO.toString());

        } catch (PersistenciaException ex) {
            LOG.warning(() -> "No fue posible actualizar al cliente: " + clienteDTO.toString());
            throw new NegocioException("ERROR: " + ex.getMessage());
        }
    }

    /**
     * Busca un cliente por su identificador.
     *
     * @param id identificador del cliente
     * @return ClienteNuevoDTO con la información del cliente
     * @throws NegocioException si ocurre un error durante la búsqueda
     */
    @Override
    public ClienteDTO buscarClientePorId(Long id) throws NegocioException {
        try {

            Cliente cliente = clienteDAO.buscarClientePorId(id);

            if (cliente == null) {
                throw new NegocioException("El cliente con ID=" + id + " no fue encontrado en el sistema.");
            }

            LOG.info(() -> "Cliente con ID=" + id + " encontrado.");
            return ClienteAdapter.entidadADTO(cliente);

        } catch (PersistenciaException ex) {
            LOG.warning(() -> "No fue posible encontrar al cliente con ID: " + id);
            throw new NegocioException("No fue posible encontrar al cliente con ID: " + id, ex);
        }
    }

    /**
     * Elimina un cliente del sistema.
     *
     * @param id identificador del cliente
     * @throws NegocioException si ocurre un error durante la eliminación
     */
    @Override
    public void eliminarCliente(Long id) throws NegocioException {
        try {
            boolean eliminado = clienteDAO.eliminarCliente(id);

            if (!eliminado) {
                throw new NegocioException("El cliente que quiere eliminar ya no se encuentra en el sistema.");
            }

            LOG.info(() -> "El cliente con ID=" + id + " fue eliminado correctamente.");

        } catch (PersistenciaException ex) {
            LOG.warning(() -> "No se pudo eliminar al cliente con ID: " + id);
            throw new NegocioException("No se pudo eliminar al cliente con ID: " + id, ex);
        }
    }

    /**
     * Obtiene el cliente general del sistema.
     *
     * Este cliente se utiliza cuando una comanda no está asociada a un cliente
     * registrado.
     *
     * @return ClienteDTO correspondiente al cliente general
     * @throws NegocioException si no existe o hay error
     */
    @Override
    public ClienteDTO obtenerClienteGeneral() throws NegocioException {
        try {
            ClienteGeneral clienteGeneral = clienteDAO.obtenerOcrearClienteGeneral();

            if (clienteGeneral == null) {
                LOG.warning("No existe cliente general en la BD");
                throw new NegocioException("No existe un cliente general configurado en el sistema");
            }

            LOG.info("Cliente general obtenido correctamente");

            return ClienteAdapter.entidadADTO(clienteGeneral);

        } catch (PersistenciaException ex) {
            LOG.warning("Error al obtener cliente general");
            throw new NegocioException("No fue posible obtener el cliente general", ex);
        }
    }

    /**
     * Obtiene a todos los clientes registrados en la BD
     *
     * @return lista con todos los clientes
     * @throws NegocioException
     */
    @Override
    public List<ClienteDTO> obtenerClientes() throws NegocioException {
        try {
            List<Cliente> clientes = clienteDAO.obtenerClientes();

            LOG.info("Los clientes se obtuvieron correctamente");

            return ClienteAdapter.listaEntidadADTO(clientes);

        } catch (PersistenciaException ex) {
            LOG.warning("No se encontraron los clientes");
            throw new NegocioException("No fue posible obtener a los clientes", ex);
        }
    }

    /**
     * Valida los datos del cliente antes de ser procesados.Verifica que los
     * campos obligatorios no estén vacíos y que el correo tenga un formato
     * válido.
     *
     *
     * @param clienteDto objeto ClienteNuevoDTO a validar
     * @throws NegocioException si algún dato no cumple con las reglas de
     * negocio
     */
    private void validarDatosNuevoDTO(ClienteNuevoDTO clienteDto) throws NegocioException {
        if (clienteDto.getNombres() == null || clienteDto.getNombres().trim().isEmpty()) {
            throw new NegocioException("El nombre del cliente es obligatorio.");
        }

        if (clienteDto.getApellidoPaterno() == null || clienteDto.getApellidoPaterno().trim().isEmpty()) {
            throw new NegocioException("El apellido paterno del cliente es obligatorio.");
        }

        if (clienteDto.getTelefono() == null || clienteDto.getTelefono().trim().isEmpty()) {
            throw new NegocioException("El teléfono del cliente es obligatorio.");
        }

        if (clienteDto.getApellidoMaterno() == null || clienteDto.getApellidoMaterno().trim().isEmpty()) {
            clienteDto.setApellidoMaterno(null);
        }

        if (clienteDto.getCorreo() != null && !clienteDto.getCorreo().trim().isEmpty()) {
            if (!clienteDto.getCorreo().trim().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                throw new NegocioException("El formato del correo no es válido.");
            }
        }

    }

    /**
     * Valida los datos del cliente antes de ser procesados.Verifica que los
     * campos obligatorios no estén vacíos y que el correo tenga un formato
     * válido.
     *
     *
     * @param clienteDto objeto ClienteNuevoDTO a validar
     * @throws NegocioException si algún dato no cumple con las reglas de
     * negocio
     */
    private void validarDatosDTO(ClienteDTO clienteDto) throws NegocioException {
        if (clienteDto.getNombres() == null || clienteDto.getNombres().trim().isEmpty()) {
            throw new NegocioException("El nombre del cliente es obligatorio.");
        }

        if (clienteDto.getApellidoPaterno() == null || clienteDto.getApellidoPaterno().trim().isEmpty()) {
            throw new NegocioException("El apellido paterno del cliente es obligatorio.");
        }

        if (clienteDto.getTelefono() == null || clienteDto.getTelefono().trim().isEmpty()) {
            throw new NegocioException("El teléfono del cliente es obligatorio.");
        }

        if (clienteDto.getApellidoMaterno() == null || clienteDto.getApellidoMaterno().trim().isEmpty()) {
            clienteDto.setApellidoMaterno(null);
        }

        if (clienteDto.getCorreo() != null && !clienteDto.getCorreo().trim().isEmpty()) {
            if (!clienteDto.getCorreo().trim().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                throw new NegocioException("El formato del correo no es válido.");
            }
        }
    }

}
