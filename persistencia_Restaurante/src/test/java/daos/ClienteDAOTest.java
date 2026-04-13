package daos;

import entidades.Cliente;
import entidades.ClienteFrecuente;
import excepciones.PersistenciaException;
import java.util.List;
import static org.testng.Assert.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Clase de pruebas unitarias para ClienteDAO.
 *
 * Se validan las operaciones CRUD básicas: - Guardar cliente - Actualizar
 * cliente - Eliminar cliente - Buscar cliente por ID - Obtener lista de
 * clientes
 *
 * Cada prueba sigue la estructura: 1. Caso exitoso 2. Caso de error o
 * comportamiento esperado
 *
 * @author Brian Kaleb Sandoval Rodriguez - 00000262741
 * @author Alejandra Leal Armenta - 00000262719
 * @author Maria Jose Valdez Iglesias - 00000262775
 */
public class ClienteDAOTest {

    /**
     * Método para que borré todo y no tengamos que modificando las pruebas cada
     * vez que las queramos correr dado a los atributos que son únicos en la
     * base de datos.
     */
    @BeforeEach
    public void limpiarDatos() throws PersistenciaException {
        ClienteDAO dao = new ClienteDAO();
        List<Cliente> clientes = dao.obtenerClientes();

        for (Cliente c : clientes) {
            dao.eliminarCliente(c.getId());
        }
    }

    /**
     * Prueba del método guardarCliente.
     *
     * Verifica que: - Se guarde correctamente un cliente - Se lance excepción
     * al intentar guardar un cliente con teléfono duplicado
     */
    @Test
    public void testGuardarCliente() throws Exception {
        ClienteDAO dao = new ClienteDAO();

        // Asumiendo que es un cliente que se acaba de registrar en el programa de lealtad
        ClienteFrecuente nuevoFrecuente = new ClienteFrecuente(
                0,
                0.0,
                0,
                null,
                "Brian Kaleb",
                "Sandoval",
                "Rodríguez",
                "6441234567",
                "brian.sandoval@gmail.com"
        );

        Cliente clienteObtenido = dao.guardarCliente(nuevoFrecuente);

        assertNotNull(clienteObtenido.getId());
        assertEquals("Brian Kaleb", clienteObtenido.getNombres());
        assertEquals("brian.sandoval@gmail.com", clienteObtenido.getCorreo());

        // Caso que debe fallar (teléfono duplicado)
        Cliente cliente2 = new Cliente("Alejandra", "Leal", "Armenta", "6441234567", "aleale2@gmail.com");

        assertThrows(PersistenciaException.class,
                () -> dao.guardarCliente(cliente2)
        );
    }

    /**
     * Prueba del método actualizarCliente.
     *
     * Verifica que: - Se actualicen correctamente los datos de un cliente
     *
     */
    @Test
    public void testActualizarCliente() throws Exception {
        ClienteDAO dao = new ClienteDAO();

        // 1. Caso de actualización normal
        Cliente cliente = new Cliente("Brian", "Sandoval", "Rodriguez", "6441568789", "kaleb@gmail.com");
        Cliente clienteGuardado = dao.guardarCliente(cliente);

        clienteGuardado.setNombres("Kaleb");
        Cliente clienteActualizado = dao.actualizarCliente(clienteGuardado);

        assertEquals("Kaleb", clienteActualizado.getNombres());

        // 2. Caso de cliente inexistente 
        Cliente clienteInvalido = new Cliente("Maria Jose", "Valdez", "Iglesias", "1234567890", "maria@gmail.com");
        clienteInvalido.setId(999L);

        assertThrows(PersistenciaException.class, () -> dao.actualizarCliente(clienteInvalido));
    }

    /**
     * Prueba del método eliminarCliente.
     *
     * Verifica que: - Se elimine correctamente un cliente existente - Regrese
     * false cuando el cliente no existe
     */
    @Test
    public void testEliminarCliente() throws Exception {
        ClienteDAO dao = new ClienteDAO();

        Cliente cliente = new Cliente("Brian", "Sandoval", "Rodriguez", "6441556877", "brian3@gmail.com");
        Cliente clienteGuardado = dao.guardarCliente(cliente);

        boolean eliminado = dao.eliminarCliente(clienteGuardado.getId());

        assertTrue(eliminado);

        // Caso inexistente
        boolean eliminadoInvalido = dao.eliminarCliente(999L);

        assertFalse(eliminadoInvalido);
    }

    /**
     * Prueba del método buscarClientePorId.
     *
     * Verifica que: - Se encuentre un cliente existente - Regrese null si el
     * cliente no existe
     */
    @Test
    public void testBuscarClientePorId() throws Exception {
        ClienteDAO dao = new ClienteDAO();

        Cliente cliente = new Cliente("Alejandra", "Leal", "Armenta", "6441556875", "ale3@gmail.com");
        Cliente clienteGuardado = dao.guardarCliente(cliente);

        Cliente encontrado = dao.buscarClientePorId(clienteGuardado.getId());

        assertNotNull(encontrado);

        // Caso inexistente
        Cliente noEncontrado = dao.buscarClientePorId(999L);

        assertNull(noEncontrado);
    }

    /**
     * Prueba del método obtenerClientes.
     *
     * Verifica que: - Se obtenga una lista de clientes - La lista contenga al
     * menos los clientes insertados
     */
    @Test
    public void testObtenerClientes() throws Exception {
        ClienteDAO dao = new ClienteDAO();

        Cliente cliente1 = new Cliente("Brian", "Sandoval", "Rodriguez", "6441556882", "brian4@gmail.com");
        Cliente cliente2 = new Cliente("Maria Jose", "Valdez", "Iglesias", "6441556883", "maria2@gmail.com");

        dao.guardarCliente(cliente1);
        dao.guardarCliente(cliente2);

        List<Cliente> clientes = dao.obtenerClientes();

        assertTrue(clientes.size() >= 2);
    }

    /**
     * Prueba del método obtenerClientes vacio.
     */
    @Test
    public void testObtenerClientesVacio() throws Exception {
        ClienteDAO dao = new ClienteDAO();

        List<Cliente> clientes = dao.obtenerClientes();

        assertTrue(clientes.isEmpty());
    }

    /**
     * Prueba del método existeTelefono.
     *
     * Verifica que: - No se considere duplicado cuando el teléfono pertenece al
     * mismo cliente - No se considere duplicado cuando el teléfono es único -
     * Se detecte correctamente cuando otro cliente tiene el mismo teléfono
     */
    @Test
    public void testExisteTelefono() throws PersistenciaException {
        ClienteDAO dao = new ClienteDAO();
        Cliente cliente1 = new Cliente("Brian", "Sandoval", "Rodriguez", "6444576879", "brian30@gmail.com");
        Cliente guardado1 = dao.guardarCliente(cliente1);

        assertFalse(dao.existeTelefono("6444576879", guardado1.getId()));

        Cliente cliente2 = new Cliente("Maria Jose", "Valdez", "Iglesias", "6441556883", "maria2@gmail.com");
        Cliente guardado2 = dao.guardarCliente(cliente2);

        assertFalse(dao.existeTelefono("6441556883", guardado2.getId()));

        assertTrue(dao.existeTelefono("6441556883", guardado1.getId()));
    }

    /**
     * Prueba del método existeCorreo.
     *
     * Verifica que: - No se considere duplicado cuando el correo pertenece al
     * mismo cliente - No se considere duplicado cuando el correo es único - Se
     * detecte correctamente cuando otro cliente tiene el mismo correo
     */
    @Test
    public void testExisteCorreo() throws PersistenciaException {
        ClienteDAO dao = new ClienteDAO();
        Cliente cliente1 = new Cliente("Brian", "Sandoval", "Rodriguez", "6444576879", "brian30@gmail.com");
        Cliente guardado1 = dao.guardarCliente(cliente1);

        assertFalse(dao.existeCorreo("brian30@gmail.com", guardado1.getId()));

        Cliente cliente2 = new Cliente("Maria Jose", "Valdez", "Iglesias", "6441556883", "maria2@gmail.com");
        Cliente guardado2 = dao.guardarCliente(cliente2);

        assertFalse(dao.existeCorreo("maria2@gmail.com", guardado2.getId()));

        assertTrue(dao.existeCorreo("maria2@gmail.com", guardado1.getId()));
    }
}
