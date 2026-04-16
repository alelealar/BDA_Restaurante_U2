package interfaces;

import dtos.MesaDTO;
import excepciones.NegocioException;
import java.util.List;

/**
 * Interfaz que define las operaciones de negocio para la entidad Mesa.
 *
 * Contiene métodos para inicializar mesas, consultarlas y actualizar su estado.
 *
 * @author Brian Kaleb Sandoval Rodríguez - 00000262741
 */
public interface IMesaBO {
    
    

    /**
     * Inicializa un conjunto de mesas en el sistema.
     *
     * Este método crea mesas numeradas consecutivamente desde 1 hasta la
     * cantidad especificada, todas con estado DISPONIBLE.
     *
     * @param cantidad Número de mesas a crear.
     * @throws NegocioException Si la cantidad es inválida o ocurre un error.
     */
    void inicializarMesas(int cantidad) throws NegocioException;

    /**
     * Eliimina una mesa por su id
     *
     * Este método elimina una mesa de la base de datos en base a su id.
     *
     * @param id Id de la mesa a eliminar
     * @throws NegocioException Si la cantidad es inválida o ocurre un error.
     */
    void eliminarMesa(Long id) throws NegocioException;

    /**
     * Obtiene todas las mesas registradas en el sistema.
     *
     * @return Lista de mesas en formato DTO.
     * @throws NegocioException Si ocurre un error.
     */
    List<MesaDTO> obtenerMesas() throws NegocioException;

    /**
     * Cambia el estado de una mesa.
     *
     * @param idMesa Identificador de la mesa.
     * @param estado Nuevo estado de la mesa.
     * @throws NegocioException Si los datos son inválidos o ocurre un error.
     */
    void cambiarEstadoMesa(Long idMesa, String estado) throws NegocioException;

    /**
     * Busca una mesa por su identificador.
     *
     * @param id Identificador de la mesa.
     * @return Mesa encontrada.
     * @throws NegocioException Si no existe o ocurre un error.
     */
    MesaDTO buscarMesaPorId(Long id) throws NegocioException;
}
