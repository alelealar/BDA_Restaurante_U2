package excepciones;

/**
 * Clase de excepción personalizada utilizada para manejar errores relacionados
 * con la capa de persistencia.
 *
 * Su propósito es encapsular cualquier error técnico que ocurra al interactuar
 * con la base de datos, ocultando la complejidad de JPA o SQL y proporcionando
 * mensajes más claros a las capas superiores del sistema.
 *
 * @author Brian Kaleb Sandoval Rodriguez - 00000262741
 * @author Alejandra Leal Armenta - 00000262719
 * @author Maria Jose Valdez Iglesias - 00000262775
 */
public class PersistenciaException extends Exception {

    /**
     * Constructor que crea una excepción con un mensaje descriptivo del error
     * ocurrido.
     *
     * @param message mensaje que describe el error
     */
    public PersistenciaException(String message) {
        super(message);
    }

    /**
     * Constructor que crea una excepción con un mensaje y la causa original del
     * error.
     *
     * @param message mensaje que describe el error
     * @param cause causa original del error
     */
    public PersistenciaException(String message, Throwable cause) {
        super(message, cause);
    }
}
