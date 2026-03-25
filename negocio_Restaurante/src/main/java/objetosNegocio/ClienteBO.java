package objetosNegocio;

import adaptadores.ClienteAdapter;
import daos.ClienteDAO;
import dtos.ClienteDTO;
import entidades.Cliente;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import interfaces.IClienteBO;
import interfaces.IClienteDAO;
import java.util.logging.Logger;

/**
 * Implementación de la interfaz IClienteBO que contiene la lógica de negocio
 * para la gestión de clientes.
 *
 * Esta clase se encarga de validar los datos recibidos desde capas superiores,
 * convertir DTOs a entidades y delegar las operaciones a la capa de acceso
 * a datos (DAO).
 *
 * Además, maneja excepciones de negocio y registra eventos mediante Logger.
 *
 * @author Brian Kaleb Sandoval Rodriguez - 00000262741
 * @author Alejandra Leal Armenta - 00000262719
 * @author Maria Jose Valdez Iglesias - 00000262775
 */
public class ClienteBO implements IClienteBO {

    /**
     * Objeto DAO utilizado para interactuar con la base de datos.
     */
    private final IClienteDAO clienteDAO = new ClienteDAO();

    /**
     * Logger para registrar eventos y errores del sistema.
     */
    private static final Logger LOG = Logger.getLogger(ClienteBO.class.getName());

    /**
     * Registra un nuevo cliente en el sistema.
     *
     * Valida los datos del cliente, convierte el DTO a entidad y delega
     * la operación al DAO. Maneja errores de persistencia y los transforma
     * en excepciones de negocio.
     *
     * @param clienteDTO objeto ClienteDTO con los datos del cliente
     * @throws NegocioException si ocurre un error en la validación o persistencia
     */
    @Override
    public void registrarCliente(ClienteDTO clienteDTO) throws NegocioException {
        try {
            validarDatos(clienteDTO);

            Cliente clienteEntidad = ClienteAdapter.dtoAEntidad(clienteDTO);
            clienteDAO.guardarCliente(clienteEntidad);

            LOG.info(() -> "El cliente fue agregado correctamente: " + clienteDTO.toString());

        } catch (PersistenciaException ex) {
            LOG.warning(() -> "No fue posible agregar al cliente: " + clienteDTO.toString());
            throw new NegocioException("No fue posible agregar al cliente: " + clienteDTO.toString(), ex);
        }
    }

    /**
     * Actualiza la información de un cliente existente.
     *
     * @param clienteDTO objeto ClienteDTO con la información actualizada
     * @throws NegocioException si ocurre un error durante la operación
     */
    @Override
    public void actualizarCliente(ClienteDTO clienteDTO) throws NegocioException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Busca un cliente por su identificador.
     *
     * @param id identificador del cliente
     * @return ClienteDTO con la información del cliente
     * @throws NegocioException si ocurre un error durante la búsqueda
     */
    @Override
    public ClienteDTO buscarClientePorId(Long id) throws NegocioException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Elimina un cliente del sistema.
     *
     * @param id identificador del cliente
     * @throws NegocioException si ocurre un error durante la eliminación
     */
    @Override
    public void eliminarCliente(Long id) throws NegocioException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Valida los datos del cliente antes de ser procesados.
     *
     * Verifica que los campos obligatorios no estén vacíos y que el
     * correo tenga un formato válido.
     *
     * @param clienteDto objeto ClienteDTO a validar
     * @throws NegocioException si algún dato no cumple con las reglas de negocio
     */
    private void validarDatos(ClienteDTO clienteDto) throws NegocioException {

        if (clienteDto.getNombres() == null || clienteDto.getNombres().trim().isEmpty()) {
            throw new NegocioException("El nombre del cliente es obligatorio.");
        }

        if (clienteDto.getApellidoPaterno() == null || clienteDto.getApellidoPaterno().trim().isEmpty()) {
            throw new NegocioException("El apellido paterno del cliente es obligatorio.");
        }

        if (clienteDto.getApellidoMaterno() == null || clienteDto.getApellidoMaterno().trim().isEmpty()) {
            throw new NegocioException("El apellido materno del cliente es obligatorio.");
        }

        if (clienteDto.getTelefono() == null || clienteDto.getTelefono().trim().isEmpty()) {
            throw new NegocioException("El telefono del cliente es obligatorio.");
        }

        if (clienteDto.getCorreo() == null || 
            !clienteDto.getCorreo().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new NegocioException("El formato del correo no es válido.");
        }
    }
}