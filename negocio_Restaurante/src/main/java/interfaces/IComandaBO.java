package interfaces;

import dtos.ComandaDTO;
import dtos.MesaDTO;
import excepciones.NegocioException;
import java.util.List;

/**
 * Interfaz que define las operaciones de negocio para la gestión de comandas.
 *
 * Esta interfaz forma parte de la capa de negocio (BO) y encapsula la lógica
 * relacionada con la creación, actualización, eliminación y consulta de
 * comandas en el sistema.
 *
 * Utiliza objetos DTO para desacoplar la capa de negocio de la capa de
 * persistencia.
 *
 * @author Brian Kaleb Sandoval Rodríguez
 */
public interface IComandaBO {

    /**
     * Guarda una nueva comanda en el sistema.
     *
     * @param comanda Comanda a registrar.
     * @throws NegocioException Si ocurre un error en la lógica de negocio.
     */
    void guardarComanda(ComandaDTO comanda) throws NegocioException;

    /**
     * Elimina una comanda por su identificador.
     *
     * @param idComanda Identificador de la comanda.
     * @throws NegocioException Si ocurre un error en la operación.
     */
    void eliminarComanda(Long idComanda) throws NegocioException;

    /**
     * Actualiza una comanda existente en el sistema.
     *
     * @param comanda Comanda con la información actualizada.
     * @throws NegocioException Si ocurre un error en la lógica de negocio.
     */
    void actualizarComanda(ComandaDTO comanda) throws NegocioException;

    /**
     * Busca una comanda por su identificador.
     *
     * @param idComanda Identificador de la comanda.
     * @return Comanda encontrada o null si no existe.
     * @throws NegocioException Si ocurre un error en la operación.
     */
    ComandaDTO buscarComandaPorId(Long idComanda) throws NegocioException;

    /**
     * Obtiene todas las comandas registradas en el sistema.
     *
     * @return Lista de comandas.
     * @throws NegocioException Si ocurre un error en la operación.
     */
    List<ComandaDTO> obtenerComandas() throws NegocioException;

    /**
     * Obtiene todas las mesas registradas en el sistema.
     *
     * @return Lista de mesas.
     * @throws NegocioException Si ocurre un error en la operación.
     */
    List<MesaDTO> obtenerMesas() throws NegocioException;
}
