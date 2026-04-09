package interfaces;

import excepciones.NegocioException;

/**
 * Interfaz de lógica de negocio para Mesero.
 *
 * Permite validar la existencia de un mesero en el sistema.
 *
 * @author Brian Kaleb Sandoval Rodríguez - 00000262741
 *
 */
public interface IMeseroBO {

    /**
     * Verifica si un mesero existe en el sistema.
     *
     * @param usuario Nombre de usuario.
     * @return true si existe, false si no.
     * @throws NegocioException Si el usuario es inválido o ocurre un error.
     */
    public boolean existeMesero(String usuario) throws NegocioException;

    /**
     * Registra un nuevo mesero en el sistema.
     *
     * @param usuario Nombre de usuario.
     * @throws NegocioException Si el usuario es inválido o ya existe.
     */
    public void registrarMesero(String usuario) throws NegocioException;

}
