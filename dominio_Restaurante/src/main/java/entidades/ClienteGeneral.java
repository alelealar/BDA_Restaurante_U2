package entidades;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Representa un cliente de tipo general dentro del sistema.
 *
 * Este tipo de cliente se utiliza cuando un cliente no desea registrarse en el
 * sistema, pero aun así se requiere asociar una comanda a un cliente por
 * motivos de integridad referencial.
 *
 * No contiene atributos adicionales, ya que hereda todos los campos de la clase
 * {@link Cliente}. Su diferenciación se realiza mediante el valor del
 * discriminador en la estrategia de herencia JPA.
 *
 * El cliente general es único en el sistema y se recomienda inicializarlo al
 * iniciar la aplicación.
 *
 * @author Brian Kaleb Sandoval Rodríguez - 00000262741
 */
@Entity
@DiscriminatorValue("GENERAL")
public class ClienteGeneral extends Cliente implements Serializable {

    /**
     * Constructor vacío requerido por JPA.
     */
    public ClienteGeneral() {
        super();
    }

    /**
     * Constructor para inicializar un cliente general con valores básicos.
     *
     * Se recomienda utilizar valores genéricos para evitar conflictos con
     * restricciones únicas como teléfono o correo.
     *
     * @param nombres Nombre del cliente general.
     * @param apellidoPaterno Apellido paterno.
     * @param apellidoMaterno Apellido materno.
     * @param telefono Número telefónico (puede ser genérico).
     * @param correo Correo electrónico (opcional).
     */
    public ClienteGeneral(String nombres, String apellidoPaterno, String apellidoMaterno, String telefono, String correo) {
        super(nombres, apellidoPaterno, apellidoMaterno, telefono, correo);
    }

    /**
     * Constructor completo con identificador.
     *
     * @param id Identificador del cliente.
     * @param nombres Nombre del cliente.
     * @param apellidoPaterno Apellido paterno.
     * @param apellidoMaterno Apellido materno.
     * @param telefono Número telefónico.
     * @param correo Correo electrónico.
     */
    public ClienteGeneral(Long id, String nombres, String apellidoPaterno, String apellidoMaterno, String telefono, String correo) {
        super(id, nombres, apellidoPaterno, apellidoMaterno, telefono, correo);
    }
}
