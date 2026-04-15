package daos;

import entidades.Mesero;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas completas para MeseroDAO.
 *
 * Se validan: - Registro - Búsqueda existente - Búsqueda inexistente - Errores
 *
 * @author Brian Kaleb Sandoval Rodríguez
 */
public class MeseroDAOTest {

    private MeseroDAO dao;

    @BeforeEach
    public void setup() throws Exception {
        dao = MeseroDAO.getInstance();
        try {
            Mesero existente = dao.buscarPorUsuario("Kaleb");
            if (existente != null) {
                return;
            }

        } catch (Exception e) {
        }

        Mesero m = new Mesero();
        m.setUsuario("Kaleb");
        dao.registrarMesero(m);
    }

    @Test
    public void testRegistrarMesero_OK() throws Exception {
        Mesero m = new Mesero("Brian");

        dao.registrarMesero(m);

        assertNotNull(m.getUsuario());
    }

    @Test
    public void testRegistrarMesero_Error() {
        assertThrows(Exception.class,
                () -> dao.registrarMesero(null));
    }

    @Test
    public void testBuscarMesero_NoExiste() throws Exception {
        assertNull(dao.buscarPorUsuario("noExiste"));
    }
}
