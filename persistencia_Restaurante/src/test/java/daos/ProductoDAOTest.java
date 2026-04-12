/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package daos;

import conexion.ConexionBD;
import entidades.Producto;
import enumerators.DisponibilidadProducto;
import enumerators.EstadoProducto;
import enumerators.TipoProducto;
import excepciones.PersistenciaException;
import interfaces.IProductoDAO;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas unitarias para validar el comportamiento de ProductoDAO.
 * 
 * Se encarga de probar las operaciones CRUD y reglas de negocio de persistencia.
 * 
 * @author María José Valdez Iglesias - 262775
 */
public class ProductoDAOTest {

    /**
     * Instancia de la interfaz DAO para realizar las operaciones.
     */
    IProductoDAO instance = ProductoDAO.getInstance();

    /**
     * Limpia las tablas de la base de datos después de cada prueba.
     * Esto asegura que cada test sea independiente y no existan conflictos de datos.
     */
    @AfterEach
    public void limpiar() {
        EntityManager em = ConexionBD.crearConexion();
        try {
            em.getTransaction().begin();
            /*
            aunque en estas pruebas no se hace el uso de relaciones ProductoIngrediente,
            en caso de que existan otros registros independientes  alos de esta clase de 
            prueba, se eliminan los registros de esta tabla también.
            */
            em.createQuery("delete from ProductoIngrediente").executeUpdate();
            em.createQuery("delete from Producto").executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
        }
    }

    /**
     * Método auxiliar para generar un objeto Producto con datos predeterminados.
     * @return Un nuevo objeto Producto listo para ser persistido.
     */
    private Producto productoPrueba() {
        Producto p = new Producto(null, "PR-TEST-001", "Producto Test", TipoProducto.BEBIDA, 
                                 "Prueba de producto.", 100.0, EstadoProducto.ACTIVO, 
                                 DisponibilidadProducto.DISPONIBLE, null);
        return p;
    }

    /**
     * Prueba que un producto válido se agregue correctamente a la base de datos.
     */
    @Test
    public void agregarProductoExito() {
        Producto p = productoPrueba();

        assertDoesNotThrow(() -> {
            Producto agregado = instance.agregarProducto(p);
            assertNotNull(agregado.getId(), "Se debe generar un ID para el producto agregado.");
            assertEquals("Producto Test", agregado.getNombre());
        });
    }

    /**
     * Prueba los casos de error al agregar un producto:
     * 1. Objeto nulo.
     * 2. Nombre repetido.
     * 3. Identificador repetido.
     */
    @Test
    public void agregarProductoFallo() {
        // agregamos un producto.
        assertDoesNotThrow(() -> instance.agregarProducto(productoPrueba()));

        /*
        agregamos productos con valores repetidos.
        para el nombre repetido modifico su identificador para que falle en el
        nombre, y viceversa con el identificador repetido.
        */
        Producto pNombreRepetido = productoPrueba();
        pNombreRepetido.setIdentificador("PR-TEST-002"); // identificador distinto, pero nombre igual.

        Producto pIdentificadorRepetido = productoPrueba();
        pIdentificadorRepetido.setNombre("Produto Test 2"); // nombre distinto, pero identificador igual.

        assertAll(
            () -> assertThrows(PersistenciaException.class, () -> instance.agregarProducto(pNombreRepetido)),
            () -> assertThrows(PersistenciaException.class, () -> instance.agregarProducto(pIdentificadorRepetido))
        );
    }

    /**
     * Verifica que los cambios realizados en un producto existente se guarden correctamente.
     */
    @Test
    public void actualizarProductoExito() {
        assertDoesNotThrow(() -> {
            Producto p = instance.agregarProducto(productoPrueba());

            // lo modificamos
            p.setPrecio(200.0);
            p.setDescripcion("Nueva descripción.");

            Producto actualizado = instance.actualizarProducto(p);

            // validamos que se hayan aplicado los cambios.
            assertEquals(200.0, actualizado.getPrecio());
            assertEquals("Nueva descripción.", actualizado.getDescripcion());
        });
    }

    /**
     * Verifica que intentar actualizar un producto con un ID inexistente lance una excepción.
     */
    @Test
    public void actualizarProductoFallo() {
        Producto productoInexistente = productoPrueba();
        productoInexistente.setId(666L); // id que no existe en la BD.

        assertThrows(PersistenciaException.class, () -> {
            instance.actualizarProducto(productoInexistente);
        });
    }

    /**
     * Verifica que el método consultarTodos retorne al menos los productos agregados.
     */
    @Test
    public void consultarTodosProductos() {
        assertDoesNotThrow(() -> {
            instance.agregarProducto(productoPrueba());
            
            Producto p2 = productoPrueba();
            p2.setNombre("Producto Test 2");
            p2.setIdentificador("PR-TEST-002");
            instance.agregarProducto(p2);

            List<Producto> lista = instance.consultarTodos();
            assertTrue(lista.size() >= 2, "La lista debe contener al menos 2 elementos.");
        });
    }

    /**
     * Prueba el cambio de estado de un producto de INACTIVO a ACTIVO.
     */
    @Test
    public void activarProductoExito() {
        assertDoesNotThrow(() -> {
            Producto p = productoPrueba();
            p.setEstado(EstadoProducto.INACTIVO);
            Producto agregado = instance.agregarProducto(p);

            boolean resultado = instance.activarProducto(agregado.getId());
            assertTrue(resultado);

            // verificamos que la info esté correcta en la BD.
            Producto activado = instance.consultarProductoPorID(agregado.getId());
            assertEquals(EstadoProducto.ACTIVO, activado.getEstado());
        });
    }

    /**
     * Verifica que el método de activación retorne false si el ID no existe.
     */
    @Test
    public void activarProductoFallo() {
        assertDoesNotThrow(() -> {
            assertFalse(instance.activarProducto(666L));
        });
    }

    /**
     * Prueba el cambio de estado de un producto de ACTIVO a INACTIVO (desactivación).
     */
    @Test
    public void desactivarProductoExito() {
        assertDoesNotThrow(() -> {
            Producto p = productoPrueba();
            Producto agregado = instance.agregarProducto(p);

            assertTrue(instance.desactivarProducto(agregado.getId()));

            Producto desactivado = instance.consultarProductoPorID(agregado.getId());
            assertEquals(EstadoProducto.INACTIVO, desactivado.getEstado());
        });
    }

    /**
     * Verifica que el método de desactivación retorne false si el ID no existe.
     */
    @Test
    public void desactivarProductoFallo() {
        assertDoesNotThrow(() -> {
            assertFalse(instance.desactivarProducto(666L));
        });
    }

    /**
     * Prueba la recuperación exitosa de un producto mediante su ID.
     */
    @Test
    public void consultarPorIDExito() {
        assertDoesNotThrow(() -> {
            Producto agregado = instance.agregarProducto(productoPrueba());

            Producto consultado = instance.consultarProductoPorID(agregado.getId());
            assertNotNull(consultado);
            assertEquals(agregado.getId(), consultado.getId());
        });
    }

    /**
     * Verifica que la consulta por ID falle con valores nulos o retorne null con IDs inexistentes.
     */
    @Test
    public void consultarPorIDFallo() {
        assertThrows(PersistenciaException.class, () -> {
            instance.consultarProductoPorID(null);
        });

        // cuando no existe solo nos regresa null.
        assertDoesNotThrow(() ->
            assertNull(instance.consultarProductoPorID(666L))
        );
    }

    /**
     * Prueba que se recupere correctamente el último identificador de un tipo específico (ej. PLATILLO).
     */
    @Test
    public void ultimoIdentificadorExiste() {
        assertDoesNotThrow(() -> {
            Producto p1 = productoPrueba();
            p1.setIdentificador("PL-001");
            p1.setTipo(TipoProducto.PLATILLO);
            instance.agregarProducto(p1);

            Producto p2 = productoPrueba();
            p2.setIdentificador("PL-002");
            p2.setTipo(TipoProducto.PLATILLO);
            p2.setNombre("Producto Test 2");
            instance.agregarProducto(p2);

            String ultimo = instance.obtenerUltimoIdentificador(TipoProducto.PLATILLO);
            assertEquals("PL-002", ultimo);
        });
    }

    /**
     * Verifica que si no hay productos de un tipo, el último identificador sea null.
     */
    @Test
    public void ultimoIdentificadorNoExiste() {
        assertDoesNotThrow(() -> {
            /*
            como la base está vacía (ya que el método afterEach limpia todo después
            de cada test, no debería haber ni un identificador registrado y nos
            debería regresar null.
            */
            String ultimo = instance.obtenerUltimoIdentificador(TipoProducto.BEBIDA);
            assertNull(ultimo);
        });
    }

    /**
     * Verifica que la consulta filtrada solo retorne productos con estado ACTIVO.
     */
    @Test
    public void consultarProductoActivosExito() {
        assertDoesNotThrow(() -> {
            Producto p1 = productoPrueba();
            instance.agregarProducto(p1);

            List<Producto> activos = instance.consultarProductosActivos();
            assertFalse(activos.isEmpty(), "La lista no debería estar vacía.");
            assertEquals(EstadoProducto.ACTIVO, activos.get(0).getEstado());
        });
    }
}