package interfaces;

import entidades.Comanda;
import entidades.Mesa;
import excepciones.PersistenciaException;
import java.util.List;

/**
 * Interfaz que define las operaciones de persistencia para la entidad Comanda.
 *
 * Establece los métodos necesarios para realizar operaciones CRUD sobre las
 * comandas.
 *
 * @author Brian Kaleb Sandoval Rodríguez - 00000262741
 */
public interface IComandaDAO {

    /**
     * Guarda una nueva comanda en la base de datos.
     *
     * @param comanda Comanda a guardar.
     * @return Comanda persistida.
     * @throws PersistenciaException Si ocurre un error durante la operación.
     */
    public Comanda guardarComanda(Comanda comanda) throws PersistenciaException;

    /**
     * Elimina una comanda por su identificador.
     *
     * @param idComanda Identificador de la comanda.
     * @return true si se eliminó correctamente, false si no existe.
     * @throws PersistenciaException Si ocurre un error.
     */
    public boolean eliminarComanda(Long idComanda) throws PersistenciaException;

    /**
     * Actualiza una comanda existente en la base de datos.
     *
     * @param comanda Comanda con datos actualizados.
     * @return Comanda actualizada.
     * @throws PersistenciaException Si ocurre un error.
     */
    public Comanda actualizarComanda(Comanda comanda) throws PersistenciaException;

    /**
     * Busca una comanda por su identificador.
     *
     * @param idComanda Identificador de la comanda.
     * @return Comanda encontrada.
     * @throws PersistenciaException Si ocurre un error.
     */
    public Comanda buscarComandaPorId(Long idComanda) throws PersistenciaException;

    /**
     * Obtiene todas las comandas registradas.
     *
     * @return Lista de comandas.
     * @throws PersistenciaException Si ocurre un error.
     */
    public List<Comanda> obtenerComandas() throws PersistenciaException;

    /**
     * Obtiene todas las comandas registradas en el dia actual.
     *
     * @return Cantidad de comandas registradas en el dia.
     * @throws PersistenciaException Si ocurre un error.
     */
    public Long obtenerComandasDia() throws PersistenciaException;

    /**
     * Obtiene todas las mesas registradas.
     *
     * @return Cantidad de mesas registradas.
     * @throws PersistenciaException Si ocurre un error.
     */
    public List<Mesa> obtenerMesas() throws PersistenciaException;
}
