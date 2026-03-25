package conexion;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Clase encargada de gestionar la conexión a la base de datos mediante JPA.
 *
 * Esta clase utiliza un EntityManagerFactory para crear instancias de
 * EntityManager, las cuales permiten interactuar con la base de datos.
 *
 *
 * @author Brian Kaleb Sandoval Rodriguez - 00000262741
 * @author Alejandra Leal Armenta - 00000262719
 * @author Maria Jose Valdez Iglesias - 00000262775
 */
public class ConexionBD {

    /**
     * Fábrica de administradores de entidad.
     *
     * Se inicializa una única vez y se utiliza para crear conexiones a la base
     * de datos.
     */
    private static final EntityManagerFactory entityManagerFactory
            = Persistence.createEntityManagerFactory("ConexionPU");

    /**
     * Constructor privado para evitar la instanciación de la clase.
     *
     * Esta clase solo debe utilizar métodos estáticos.
     */
    private ConexionBD() {

    }

    /**
     * Crea y devuelve una nueva instancia de EntityManager.
     *
     * El EntityManager permite realizar operaciones de persistencia como
     * insertar, actualizar, eliminar y consultar datos en la base de datos.
     *
     * @return una instancia de EntityManager para interactuar con la base de
     * datos
     */
    public static EntityManager crearConexion() {
        return entityManagerFactory.createEntityManager();
    }
}
