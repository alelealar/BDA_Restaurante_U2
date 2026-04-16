package interfaces;

import dtos.ComandaDTO;
import dtos.MesaDTO;
import excepciones.NegocioException;
import java.util.List;

/**
 * Interfaz que define las operaciones de lógica de negocio para la gestión de comandas.
 *
 * Esta capa actúa como intermediaria entre la presentación y la persistencia,
 * validando reglas de negocio, transformando entidades y coordinando el flujo
 * de información mediante DTOs.
 *
 * @author Brian Kaleb Sandoval Rodríguez
 */
public interface IComandaBO {

    /**
     * Registra una nueva comanda en el sistema.
     *
     * @param comandaDTO información de la comanda a registrar
     * @return comanda registrada con datos actualizados
     * @throws NegocioException si ocurre un error de validación o negocio
     */
    ComandaDTO guardarComanda(ComandaDTO comandaDTO) throws NegocioException;

    /**
     * Elimina una comanda del sistema por su identificador.
     *
     * @param idComanda identificador único de la comanda
     * @throws NegocioException si ocurre un error o la comanda no existe
     */
    void eliminarComanda(Long idComanda) throws NegocioException;

    /**
     * Actualiza la información de una comanda existente.
     *
     * @param comandaDTO comanda con los datos actualizados
     * @return comanda actualizada
     * @throws NegocioException si ocurre un error de validación o persistencia
     */
    ComandaDTO actualizarComanda(ComandaDTO comandaDTO) throws NegocioException;

    /**
     * Busca una comanda por su identificador único.
     *
     * @param idComanda identificador de la comanda
     * @return comanda encontrada o null si no existe
     * @throws NegocioException si ocurre un error durante la búsqueda
     */
    ComandaDTO buscarComandaPorId(Long idComanda) throws NegocioException;

    /**
     * Obtiene todas las comandas registradas en el sistema.
     *
     * @return lista de comandas registradas
     * @throws NegocioException si ocurre un error durante la consulta
     */
    List<ComandaDTO> obtenerComandas() throws NegocioException;

    /**
     * Obtiene todas las mesas registradas en el sistema.
     *
     * @return lista de mesas disponibles
     * @throws NegocioException si ocurre un error durante la consulta
     */
    List<MesaDTO> obtenerMesas() throws NegocioException;

    /**
     * Actualiza la información del cliente frecuente asociado a una comanda.
     *
     * Este método se encarga de actualizar los datos de fidelización como
     * visitas, puntos acumulados y total gastado.
     *
     * @param comanda comanda utilizada para actualizar el cliente frecuente
     * @throws NegocioException si ocurre un error de negocio o validación
     */
    void actualizarClienteFrecuente(ComandaDTO comanda) throws NegocioException;
}