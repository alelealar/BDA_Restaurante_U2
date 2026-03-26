package daos;

import entidades.Cliente;
import excepciones.PersistenciaException;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
     * Prueba del método guardarCliente.
     *
     * Verifica que: - Se guarde correctamente un cliente - Se lance excepción
     * al intentar guardar un cliente con teléfono duplicado
     */
    @Test
    public void testGuardarCliente() throws Exception {
        ClienteDAO dao = new ClienteDAO();

        Cliente cliente = new Cliente("Brian", "Sandoval", "Rodriguez", "6441556878", "brian@gmail.com");

        Cliente clienteObtenido = dao.guardarCliente(cliente);

        assertNotNull(clienteObtenido.getId());
        assertEquals("Brian", clienteObtenido.getNombres());
        assertEquals("brian@gmail.com", clienteObtenido.getCorreo());

        // Caso que debe fallar (teléfono duplicado)
        Cliente cliente2 = new Cliente("Alejandra", "Leal", "Armenta", "6441556878", "ale@gmail.com");

        assertThrows(PersistenciaException.class,
                () -> dao.guardarCliente(cliente2)
        );
    }

    /**
     * Prueba del método actualizarCliente.
     *
     * Verifica que: - Se actualicen correctamente los datos de un cliente
     * existente - Se lance excepción al intentar actualizar un cliente
     * inexistente
     */
    @Test
    public void testActualizarCliente() throws Exception {
        ClienteDAO dao = new ClienteDAO();

        Cliente cliente = new Cliente("Brian", "Sandoval", "Rodriguez", "6441568788", "kaleb@gmail.com");
        Cliente clienteGuardado = dao.guardarCliente(cliente);

        clienteGuardado.setNombres("Kaleb");

        Cliente clienteActualizado = dao.actualizarCliente(clienteGuardado);

        assertEquals("Kaleb", clienteActualizado.getNombres());

        // Caso que debe fallar (cliente inexistente)
        Cliente clienteInvalido = new Cliente("Maria Jose", "Valdez", "Iglesias", "1234567890", "maria@gmail.com");
        clienteInvalido.setId(999L);

        assertThrows(PersistenciaException.class,
                () -> dao.actualizarCliente(clienteInvalido)
        );
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

        Cliente cliente = new Cliente("Brian", "Sandoval", "Rodriguez", "6441556878", "brian@gmail.com");
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

        Cliente cliente = new Cliente("Alejandra", "Leal", "Armenta", "6441556879", "ale@gmail.com");
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

        Cliente cliente1 = new Cliente("Brian", "Sandoval", "Rodriguez", "6441556880", "brian2@gmail.com");
        Cliente cliente2 = new Cliente("Maria Jose", "Valdez", "Iglesias", "6441556881", "maria@gmail.com");

        dao.guardarCliente(cliente1);
        dao.guardarCliente(cliente2);

        List<Cliente> clientes = dao.obtenerClientes();

        assertTrue(clientes.size() >= 2);
    }
}
