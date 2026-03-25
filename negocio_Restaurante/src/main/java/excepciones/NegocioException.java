package excepciones;

/**
 * Clase de excepción personalizada utilizada para manejar errores en la lógica
 * de negocio del sistema.
 *
 * Esta excepción representa problemas que ocurren en la capa de negocio, como
 * validaciones, reglas del sistema o inconsistencias en los datos,
 * diferenciándose de los errores de persistencia.
 *
 * Permite encapsular mensajes claros para el usuario o sistema, así como
 * conservar la causa original del error para fines de depuración.
 *
 * @author Brian Kaleb Sandoval Rodriguez - 00000262741
 * @author Alejandra Leal Armenta - 00000262719
 * @author Maria Jose Valdez Iglesias - 00000262775
 */
public class NegocioException extends Exception {

    /**
     * Constructor que crea una excepción con un mensaje descriptivo del error
     * de negocio.
     *
     * @param message mensaje que describe el error
     */
    public NegocioException(String message) {
        super(message);
    }

    /**
     * Constructor que crea una excepción con un mensaje y la causa original del
     * error.
     *
     * Permite conservar la excepción original para facilitar la depuración del
     * problema.
     *
     * @param message mensaje que describe el error
     * @param cause causa original del error
     */
    public NegocioException(String message, Throwable cause) {
        super(message, cause);
    }
}
