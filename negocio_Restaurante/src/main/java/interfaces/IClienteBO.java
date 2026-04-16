package interfaces;

import dtos.ClienteDTO;
import dtos.ClienteNuevoDTO;
import excepciones.NegocioException;
import java.util.List;

/**
 * Interfaz que define las operaciones de la lógica de negocio (BO) para la
 * gestión de clientes.
 *
 * Esta interfaz actúa como intermediaria entre la capa de presentación y la
 * capa de acceso a datos (DAO), aplicando reglas de negocio, validaciones y
 * controlando el flujo de la información.
 *
 * Utiliza objetos DTO para evitar exponer directamente las entidades de
 * persistencia.
 *
 * @author Brian Kaleb Sandoval Rodriguez - 00000262741
 * @author Alejandra Leal Armenta - 00000262719
 * @author Maria Jose Valdez Iglesias - 00000262775
 */
public interface IClienteBO {

    /**
     * Registra un nuevo cliente en el sistema.
     *
     * Este método valida la información del cliente antes de enviarla a la
     * capa de persistencia.
     *
     * @param clienteDTO objeto ClienteNuevoDTO con los datos del cliente a registrar
     * @throws NegocioException si ocurre un error en las reglas de negocio o validaciones
     */
    void registrarCliente(ClienteNuevoDTO clienteDTO) throws NegocioException;

    /**
     * Registra un nuevo cliente frecuente en el sistema.
     *
     * Este método valida la información del cliente antes de enviarla a la
     * capa de persistencia, considerando datos de fidelización.
     *
     * @param clienteDTO objeto ClienteNuevoDTO con los datos del cliente a registrar
     * @throws NegocioException si ocurre un error en las reglas de negocio o validaciones
     */
    void registrarClienteFrecuente(ClienteNuevoDTO clienteDTO) throws NegocioException;

    /**
     * Actualiza la información de un cliente existente.
     *
     * Este método valida los datos antes de realizar la actualización en la
     * base de datos.
     *
     * @param clienteDTO objeto ClienteDTO con la información actualizada del cliente
     * @throws NegocioException si ocurre un error en las reglas de negocio o validaciones
     */
    void actualizarCliente(ClienteDTO clienteDTO) throws NegocioException;

    /**
     * Busca un cliente en el sistema a partir de su identificador.
     *
     * @param id identificador único del cliente
     * @return objeto ClienteDTO con la información del cliente encontrado
     * @throws NegocioException si ocurre un error durante la búsqueda o el cliente no existe
     */
    ClienteDTO buscarClientePorId(Long id) throws NegocioException;

    /**
     * Elimina un cliente del sistema a partir de su identificador.
     *
     * @param id identificador único del cliente a eliminar
     * @throws NegocioException si ocurre un error durante la eliminación o el cliente no existe
     */
    void eliminarCliente(Long id) throws NegocioException;

    /**
     * Obtiene el cliente general del sistema.
     *
     * Este cliente se utiliza cuando una comanda no está asociada a un cliente
     * registrado.
     *
     * @return ClienteDTO correspondiente al cliente general del sistema
     * @throws NegocioException si no existe o ocurre un error al obtenerlo
     */
    ClienteDTO obtenerClienteGeneral() throws NegocioException;

    /**
     * Obtiene todos los clientes registrados en el sistema.
     *
     * @return lista de objetos ClienteDTO con todos los clientes registrados
     * @throws NegocioException si ocurre un error al consultar los clientes
     */
    List<ClienteDTO> obtenerClientes() throws NegocioException;
}