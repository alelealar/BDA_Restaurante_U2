package interfaces;

import dtos.ComandaDTO;
import dtos.MesaDTO;
import excepciones.NegocioException;
import java.util.List;

/**
 * Define las operaciones de negocio para la gestión de comandas.
 *
 * La capa BO valida datos, aplica reglas de negocio y transforma entidades
 * provenientes del DAO en objetos DTO para la capa de presentación.
 *
 * @author Brian Kaleb Sandoval Rodríguez
 */
public interface IComandaBO {

    /**
     * Registra una nueva comanda en el sistema.
     *
     * @param comandaDTO información de la comanda a guardar
     * @return comanda registrada con datos actualizados
     * @throws NegocioException si ocurre un error de negocio
     */
    ComandaDTO guardarComanda(ComandaDTO comandaDTO) throws NegocioException;

    /**
     * Elimina una comanda por su identificador.
     *
     * @param idComanda identificador de la comanda
     * @throws NegocioException si ocurre un error
     */
    void eliminarComanda(Long idComanda) throws NegocioException;

    /**
     * Actualiza una comanda existente.
     *
     * @param comandaDTO comanda con nuevos datos
     * @return comanda actualizada
     * @throws NegocioException si ocurre un error
     */
    ComandaDTO actualizarComanda(ComandaDTO comandaDTO) throws NegocioException;

    /**
     * Busca una comanda por su identificador.
     *
     * @param idComanda identificador de la comanda
     * @return comanda encontrada o null si no existe
     * @throws NegocioException si ocurre un error
     */
    ComandaDTO buscarComandaPorId(Long idComanda) throws NegocioException;

    /**
     * Obtiene todas las comandas registradas.
     *
     * @return lista de comandas
     * @throws NegocioException si ocurre un error
     */
    List<ComandaDTO> obtenerComandas() throws NegocioException;

    /**
     * Obtiene todas las mesas registradas.
     *
     * @return lista de mesas
     * @throws NegocioException si ocurre un error
     */
    List<MesaDTO> obtenerMesas() throws NegocioException;
}
