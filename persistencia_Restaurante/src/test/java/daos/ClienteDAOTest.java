package daos;

import entidades.Cliente;
import entidades.ClienteFrecuente;
import excepciones.PersistenciaException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas unitarias para {@link ClienteDAO}.
 *
 * Esta clase valida el comportamiento de los métodos principales del DAO,
 * cubriendo rutas exitosas y rutas de error en operaciones CRUD y validaciones.
 *
 * Se prueban los siguientes casos:
 * <ul>
 * <li>Guardar clientes válidos e inválidos.</li>
 * <li>Actualizar clientes existentes e inexistentes.</li>
 * <li>Eliminar registros existentes e inexistentes.</li>
 * <li>Búsquedas por identificador.</li>
 * <li>Obtención de listas generales y específicas.</li>
 * <li>Validación de teléfonos y correos duplicados.</li>
 * </ul>
 *
 * Cada prueba se ejecuta con datos aislados para evitar interferencias entre
 * ejecuciones.
 *
 * @author Brian Kaleb Sandoval Rodríguez - 00000262741
 * @author Alejandra Leal Armenta - 00000262719
 * @author Maria Jose Valdez Iglesias - 00000262775
 */
public class ClienteDAOTest {

    /**
     * Instancia del objeto de acceso a datos.
     */
    private ClienteDAO dao;

    /**
     * Configuración inicial antes de cada prueba.
     *
     * Obtiene la instancia del DAO y elimina los clientes frecuentes existentes
     * para garantizar independencia entre pruebas.
     *
     * @throws Exception si ocurre un error al limpiar la base de datos.
     */
    @BeforeEach
    public void setup() throws Exception {
        dao = ClienteDAO.getInstance();

        List<ClienteFrecuente> frecuentes = dao.obtenerClientesFrecuentes();

        for (ClienteFrecuente c : frecuentes) {
            dao.eliminarCliente(c.getId());
        }
    }

    /**
     * Crea una instancia válida de {@link ClienteFrecuente} con datos únicos.
     *
     * Los valores generados evitan conflictos con restricciones únicas como
     * teléfono y correo electrónico.
     *
     * @return cliente frecuente listo para persistirse.
     */
    private ClienteFrecuente crearFrecuenteUnico() {
        long n = System.nanoTime();

        String telefono = "6" + String.format("%09d", n % 1_000_000_000);
        String correo = "correo" + n + "@test.com";

        return new ClienteFrecuente(
                0,
                0.0,
                0L,
                null,
                "Cliente" + n,
                "ApellidoP",
                "ApellidoM",
                telefono,
                correo
        );
    }

    /**
     * Verifica la ruta exitosa del guardado de un cliente frecuente.
     *
     * Debe asignarse un identificador automáticamente al persistir.
     *
     * @throws Exception si ocurre un error inesperado.
     */
    @Test
    public void testGuardarCliente_OK() throws Exception {
        ClienteFrecuente cliente = crearFrecuenteUnico();

        Cliente guardado = dao.guardarCliente(cliente);

        assertNotNull(guardado);
        assertNotNull(guardado.getId());
    }

    /**
     * Verifica la ruta de error al intentar guardar un cliente nulo.
     *
     * Debe lanzarse una excepción de persistencia.
     */
    @Test
    public void testGuardarCliente_Error() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        
        assertThrows(PersistenciaException.class,
                () -> dao.guardarCliente(cliente));
    }

    /**
     * Verifica la ruta exitosa de actualización de un cliente existente.
     *
     * Se modifican atributos heredados y propios de la subclase.
     *
     * @throws Exception si ocurre un error inesperado.
     */
    @Test
    public void testActualizarCliente_OK() throws Exception {
        ClienteFrecuente cliente = crearFrecuenteUnico();

        ClienteFrecuente guardado
                = (ClienteFrecuente) dao.guardarCliente(cliente);

        guardado.setNombres("Actualizado");
        guardado.setPuntos(100L);

        Cliente actualizado = dao.actualizarCliente(guardado);

        assertEquals("Actualizado", actualizado.getNombres());
        assertEquals(100,
                ((ClienteFrecuente) actualizado).getPuntos());
    }

    /**
     * Verifica la ruta de error al intentar actualizar un cliente inexistente.
     *
     * Debe lanzarse una excepción de persistencia.
     */
    @Test
    public void testActualizarCliente_Error() {
        ClienteFrecuente cliente = crearFrecuenteUnico();
        cliente.setId(999999L);

        assertThrows(PersistenciaException.class,
                () -> dao.actualizarCliente(cliente));
    }

    /**
     * Verifica ambas rutas de eliminación.
     *
     * Ruta buena:
     * <ul>
     * <li>Eliminar un cliente previamente registrado.</li>
     * </ul>
     *
     * Ruta mala:
     * <ul>
     * <li>Intentar eliminar un identificador inexistente.</li>
     * </ul>
     *
     * @throws Exception si ocurre un error inesperado.
     */
    @Test
    public void testEliminarCliente() throws Exception {
        Cliente guardado = dao.guardarCliente(crearFrecuenteUnico());

        assertTrue(dao.eliminarCliente(guardado.getId()));
        assertFalse(dao.eliminarCliente(999999L));
    }

    /**
     * Verifica ambas rutas de búsqueda por identificador.
     *
     * Ruta buena:
     * <ul>
     * <li>Encontrar un cliente existente.</li>
     * </ul>
     *
     * Ruta mala:
     * <ul>
     * <li>No encontrar un identificador inexistente.</li>
     * </ul>
     *
     * @throws Exception si ocurre un error inesperado.
     */
    @Test
    public void testBuscarClientePorId() throws Exception {
        Cliente guardado = dao.guardarCliente(crearFrecuenteUnico());

        assertNotNull(dao.buscarClientePorId(guardado.getId()));
        assertNull(dao.buscarClientePorId(999999L));
    }

    /**
     * Verifica la obtención general de clientes registrados.
     *
     * La lista devuelta no debe ser nula y debe contener al menos un elemento
     * después de insertar datos de prueba.
     *
     * @throws Exception si ocurre un error inesperado.
     */
    @Test
    public void testObtenerClientes() throws Exception {
        dao.guardarCliente(crearFrecuenteUnico());

        List<Cliente> lista = dao.obtenerClientes();

        assertNotNull(lista);
        assertTrue(lista.size() >= 1);
    }
    

    /**
     * Verifica la obtención exclusiva de clientes frecuentes.
     *
     * La lista devuelta no debe ser nula y debe contener registros.
     *
     * @throws Exception si ocurre un error inesperado.
     */
    @Test
    public void testObtenerClientesFrecuentes() throws Exception {
        dao.guardarCliente(crearFrecuenteUnico());

        List<ClienteFrecuente> lista = dao.obtenerClientesFrecuentes();

        assertNotNull(lista);
        assertTrue(lista.size() >= 1);
    }

    /**
     * Verifica ambas rutas de búsqueda específica de cliente frecuente.
     *
     * Ruta buena:
     * <ul>
     * <li>Encontrar un cliente frecuente existente.</li>
     * </ul>
     *
     * Ruta mala:
     * <ul>
     * <li>Retornar nulo cuando el identificador no existe.</li>
     * </ul>
     *
     * @throws Exception si ocurre un error inesperado.
     */
    @Test
    public void testObtenerClienteFrecuentePorId() throws Exception {
        Cliente guardado = dao.guardarCliente(crearFrecuenteUnico());

        ClienteFrecuente obtenido
                = dao.obtenerClienteFrecuentePorId(guardado.getId());

        assertNotNull(obtenido);
        assertNull(dao.obtenerClienteFrecuentePorId(999999L));
    }

    /**
     * Verifica la validación de teléfonos duplicados.
     *
     * Ruta buena:
     * <ul>
     * <li>El mismo cliente no debe detectarse como duplicado.</li>
     * </ul>
     *
     * Ruta mala:
     * <ul>
     * <li>Otro cliente con el mismo teléfono debe detectarse como duplicado.</li>
     * </ul>
     *
     * @throws Exception si ocurre un error inesperado.
     */
    @Test
    public void testExisteTelefono() throws Exception {
        ClienteFrecuente c1 = crearFrecuenteUnico();
        Cliente guardado1 = dao.guardarCliente(c1);

        assertFalse(
                dao.existeTelefono(c1.getTelefono(), guardado1.getId())
        );

        ClienteFrecuente c2 = crearFrecuenteUnico();
        dao.guardarCliente(c2);

        assertTrue(
                dao.existeTelefono(c2.getTelefono(), guardado1.getId())
        );
    }

    /**
     * Verifica la validación de correos duplicados.
     *
     * Ruta buena:
     * <ul>
     * <li>El mismo cliente no debe detectarse como duplicado.</li>
     * </ul>
     *
     * Ruta mala:
     * <ul>
     * <li>Otro cliente con el mismo correo debe detectarse como duplicado.</li>
     * </ul>
     *
     * @throws Exception si ocurre un error inesperado.
     */
    @Test
    public void testExisteCorreo() throws Exception {
        ClienteFrecuente c1 = crearFrecuenteUnico();
        Cliente guardado1 = dao.guardarCliente(c1);

        assertFalse(
                dao.existeCorreo(c1.getCorreo(), guardado1.getId())
        );

        ClienteFrecuente c2 = crearFrecuenteUnico();
        dao.guardarCliente(c2);

        assertTrue(
                dao.existeCorreo(c2.getCorreo(), guardado1.getId())
        );
    }
}