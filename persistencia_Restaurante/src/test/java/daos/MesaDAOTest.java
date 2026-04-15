package daos;

import entidades.Mesa;
import enumerators.EstadoMesa;
import excepciones.PersistenciaException;
import java.util.List;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas completas para MesaDAO.
 *
 * Se validan: - Registro - Consulta - Búsqueda - Cambio de estado - Errores
 *
 * Se inicializan 20 mesas en cada prueba.
 *
 * @author Brian Kaleb Sandoval Rodríguez
 */
public class MesaDAOTest {

    private MesaDAO dao;

    @BeforeEach
    public void setup() throws PersistenciaException {
        dao = MesaDAO.getInstance();

        List<Mesa> mesas = dao.obtenerMesas();
        if (mesas != null && !mesas.isEmpty()) {
            return;
        }

        for (int i = 1; i <= 20; i++) {
            Mesa m = new Mesa();
            m.setNumero(i);
            m.setEstado(EstadoMesa.DISPONIBLE);
            dao.registrarMesa(m);
        }
    }

    @Test
    public void testRegistrarMesa_OK() throws Exception {
        Mesa m = new Mesa();
        m.setNumero(21);
        m.setEstado(EstadoMesa.DISPONIBLE);

        dao.registrarMesa(m);

        assertNotNull(m.getId());
    }

    @Test
    public void testRegistrarMesa_Error() {
        assertThrows(PersistenciaException.class,
                () -> dao.registrarMesa(null));
    }

    @Test
    public void testObtenerMesas() throws Exception {
        List<Mesa> mesas = dao.obtenerMesas();

        assertNotNull(mesas);
    }

    @Test
    public void testBuscarMesa_OK() throws Exception {
        Mesa m = dao.obtenerMesas().get(0);

        assertNotNull(dao.buscarMesaPorId(m.getId()));
    }

    @Test
    public void testBuscarMesa_NoExiste() throws Exception {
        assertNull(dao.buscarMesaPorId(999L));
    }

    @Test
    public void testCambiarEstadoMesa_OK() throws Exception {
        Mesa m = dao.obtenerMesas().get(0);

        dao.cambiarEstadoMesa(m.getId(), EstadoMesa.OCUPADA);

        Mesa actualizada = dao.buscarMesaPorId(m.getId());

        assertEquals(EstadoMesa.OCUPADA, actualizada.getEstado());
    }

    @Test
    public void testCambiarEstadoMesa_Error() {
        assertThrows(PersistenciaException.class,
                () -> dao.cambiarEstadoMesa(999L, EstadoMesa.OCUPADA));
    }
}
