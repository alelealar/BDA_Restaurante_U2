package interfaces;

import entidades.Mesero;
import excepciones.PersistenciaException;

/**
 * Interfaz DAO para Mesero.
 *
 * @author Brian Kaleb Sandoval Rodríguez - 00000262741
 */
public interface IMeseroDAO {

    /**
     * Busca un mesero por su usuario.
     *
     * @param usuario Nombre de usuario.
     * @return Mesero encontrado o null si no existe.
     * @throws PersistenciaException Si ocurre un error.
     */
    Mesero buscarPorUsuario(String usuario) throws PersistenciaException;

    /**
     * Registra un nuevo mesero.
     *
     * @param mesero Mesero a registrar.
     * @throws PersistenciaException Si ocurre un error.
     */
    Mesero registrarMesero(Mesero mesero) throws PersistenciaException;
}
