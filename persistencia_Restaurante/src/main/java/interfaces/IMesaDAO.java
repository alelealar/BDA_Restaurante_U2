package interfaces;

import entidades.Mesa;
import enumerators.EstadoMesa;
import excepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author Kaleb
 */
public interface IMesaDAO {

    /**
     * Crea una mesa en la base de datos.
     *
     * @param mesa Mesa a crear.
     * @throws PersistenciaException Si ocurre un error durante la persistencia.
     */
    public void registrarMesa(Mesa mesa) throws PersistenciaException;

    /**
     * Obtiene todas las mesas registradas en la base de datos.
     *
     * @return Lista de mesas.
     * @throws PersistenciaException Si ocurre un error durante la consulta.
     */
    public List<Mesa> obtenerMesas() throws PersistenciaException;

    /**
     * Busca una mesa por su identificador.
     *
     * @param id Identificador de la mesa.
     * @return Mesa encontrada o null si no existe.
     * @throws PersistenciaException Si ocurre un error durante la búsqueda.
     */
    public Mesa buscarMesaPorId(Long id) throws PersistenciaException;

    /**
     * Cambia el estado de una mesa existente.
     *
     * Busca la mesa por su ID y actualiza su estado en la base de datos.
     *
     * @param idMesa Identificador de la mesa.
     * @param estado Nuevo estado a asignar.
     * @throws PersistenciaException Si ocurre un error o la mesa no existe.
     */
    public void cambiarEstadoMesa(Long idMesa, EstadoMesa estado) throws PersistenciaException;
}
