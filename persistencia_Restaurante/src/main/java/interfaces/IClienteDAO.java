package interfaces;

import entidades.Cliente;
import excepciones.PersistenciaException;
import java.util.List;

/**
 * Interfaz que define las operaciones básicas para la gestión de clientes en la
 * capa de acceso a datos (DAO).
 *
 * Esta interfaz establece los métodos que deben ser implementados para realizar
 * operaciones CRUD (Crear, Leer, Actualizar y Eliminar) sobre la entidad
 * Cliente en la base de datos.
 *
 * Su uso permite desacoplar la lógica de negocio de la lógica de persistencia,
 * facilitando el mantenimiento y la escalabilidad del sistema.
 *
 * @author Brian Kaleb Sandoval Rodriguez - 00000262741
 * @author Alejandra Leal Armenta - 00000262719
 * @author Maria Jose Valdez Iglesias - 00000262775
 */
public interface IClienteDAO {

    /**
     * Guarda un nuevo cliente en la base de datos.
     *
     * Este método implementa la lógica necesaria para insertar un registro de
     * cliente en el sistema.
     *
     * @param cliente objeto Cliente que contiene la información a guardar
     * @return el cliente guardado con los cambios aplicados (por ejemplo, ID
     * generado)
     * @throws PersistenciaException si ocurre un error durante la operación de
     * persistencia
     */
    public Cliente guardarCliente(Cliente cliente) throws PersistenciaException;

    /**
     * Actualiza la información de un cliente existente en la base de datos.
     *
     * Este método modifica los datos de un cliente previamente registrado.
     *
     * @param cliente objeto Cliente con la información actualizada
     * @return el cliente actualizado
     * @throws PersistenciaException si ocurre un error durante la actualización
     */
    public Cliente actualizarCliente(Cliente cliente) throws PersistenciaException;

    /**
     * Elimina un cliente de la base de datos.
     *
     * Este método se encarga de remover un cliente existente del sistema.
     *
     * @param id ID del cliente a eliminar
     * @return verdadero si el cliente fue eliminado, falso en caso contrario
     * @throws PersistenciaException si ocurre un error durante la eliminación
     */
    public boolean eliminarCliente(Long id) throws PersistenciaException;

    /**
     * Busca un cliente en la base de datos a partir de su identificador.
     *
     * Este método recupera la información de un cliente específico utilizando
     * su ID.
     *
     * @param id ID del cliente a actualizar
     * @return el cliente encontrado
     * @throws PersistenciaException si ocurre un error durante la búsqueda
     */
    public Cliente buscarClientePorId(Long id) throws PersistenciaException;
    
    
    /**
    * Obtiene la lista completa de clientes registrados en la base de datos.
    *
    * Este método permite recuperar todos los clientes almacenados en el sistema,
    * facilitando su visualización en interfaces como tablas o listados dentro
    * de la aplicación.
    *
    * @return lista de clientes registrados
    * @throws PersistenciaException si ocurre un error durante la consulta
    */
    public List<Cliente> obtenerClientes() throws PersistenciaException;
    
    /**
     * Valida que el telefono no exista en alguno de los regitros
     * @param telefono
     * @param ID
     * @return 
     * @throws PersistenciaException 
     */
    public boolean existeTelefono(String telefono, Long ID) throws PersistenciaException;
    
    /**
     * Valida que el correo no exista en alguno de los regitros
     * @param correo
     * @param ID
     * @return 
     * @throws PersistenciaException 
     */
    public boolean existeCorreo(String correo, Long ID) throws PersistenciaException;
}
