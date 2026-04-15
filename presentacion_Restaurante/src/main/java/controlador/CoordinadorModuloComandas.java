package controlador;

import dtos.ClienteDTO;
import dtos.ComandaDTO;
import dtos.MesaDTO;
import dtos.ProductoDTO;
import enumerators.EstadoMesaDTO;
import enumerators.TipoMovimiento;
import excepciones.NegocioException;
import interfaces.IComandaBO;
import interfaces.IMesaBO;
import interfaces.IMeseroBO;
import interfaces.IProductoBO;
import java.util.List;
import objetosNegocio.ComandaBO;
import objetosNegocio.MesaBO;
import objetosNegocio.MeseroBO;
import objetosNegocio.ProductoBO;
import pantallas.moduloComandas.frmComandas;
import pantallas.moduloComandas.frmISMesero;
import pantallas.moduloComandas.frmMesas;
import pantallas.moduloComandas.frmPedidos;
import pantallas.moduloComandas.vistas.frmResumen;

/**
 * Clase coordinadora del módulo de comandas.
 *
 * Se encarga de comunicar la capa de presentación con la capa de negocio,
 * además de controlar la navegación entre las distintas pantallas del módulo.
 *
 * @author Brian Kaleb Sandoval Rodríguez - 00000262741
 */
public class CoordinadorModuloComandas {

    /**
     * Pantalla de inicio de sesión del mesero.
     */
    private frmISMesero isMesero;

    /**
     * Pantalla de mesas.
     */
    private frmMesas mesas;

    /**
     * Pantalla principal de comandas.
     */
    private frmComandas comandas;

    /**
     * Pantalla para registrar pedidos.
     */
    private frmPedidos pedidos;

    /**
     * Pantalla de resumen de comanda.
     */
    private frmResumen resumen;

    /**
     * Objeto de negocio para comandas.
     */
    private final IComandaBO comandaBO;

    /**
     * Objeto de negocio para meseros.
     */
    private final IMeseroBO meseroBO;

    /**
     * Objeto de negocio para mesas.
     */
    private final IMesaBO mesaBO;

    /**
     * Objeto de negocio para productos.
     */
    private final IProductoBO productoBO;

    /**
     * Coordinador principal del sistema.
     */
    private CoordinadorInterfaces coordinador;

    /**
     * Constructor que inicializa los objetos de negocio del módulo.
     */
    public CoordinadorModuloComandas() {
        comandaBO = ComandaBO.getInstance();
        meseroBO = MeseroBO.getInstance();
        mesaBO = MesaBO.getInstance();
        productoBO = ProductoBO.getInstance();
    }

    /**
     * Establece el coordinador principal del sistema.
     *
     * @param coordinador coordinador general de interfaces
     */
    public void setCoordinador(CoordinadorInterfaces coordinador) {
        this.coordinador = coordinador;
    }

    /**
     * Regresa a la pantalla principal del sistema.
     */
    public void volverInicio() {
        if (coordinador != null) {
            coordinador.regresarInicio();
        }
    }

    /**
     * Cierra todas las pantallas del módulo abiertas actualmente.
     */
    public void cerrarPantallas() {

        if (isMesero != null) {
            isMesero.dispose();
            isMesero = null;
        }

        if (mesas != null) {
            mesas.dispose();
            mesas = null;
        }

        if (comandas != null) {
            comandas.dispose();
            comandas = null;
        }

        if (pedidos != null) {
            pedidos.dispose();
            pedidos = null;
        }
    }

    /**
     * Muestra la pantalla de inicio de sesión del mesero.
     */
    public void mostrarPantallaISMesero() {
        if (isMesero == null) {
            isMesero = new frmISMesero(this);
        }
        isMesero.setVisible(true);
    }

    /**
     * Muestra la pantalla para crear pedidos.
     *
     * @param mesa mesa asociada
     * @param comanda comanda actual
     */
    public void mostrarPantallaCrearPedido(MesaDTO mesa, ComandaDTO comanda) {
        if (pedidos != null) {
            pedidos.dispose();
            pedidos = null;
        }

        if (comandas != null) {
            comandas.setVisible(false);
        }

        pedidos = new frmPedidos(this, mesa, comanda);
        pedidos.setVisible(true);
    }

    /**
     * Muestra la pantalla de comandas para una mesa específica.
     *
     * @param mesa mesa seleccionada
     */
    public void mostrarPantallaComandas(MesaDTO mesa) {
        if (comandas != null) {
            comandas.dispose();
        }

        if (pedidos != null) {
            pedidos.dispose();
        }

        comandas = new frmComandas(this, mesa);

        if (mesas != null) {
            mesas.setVisible(false);
        }

        comandas.setVisible(true);
    }

    /**
     * Muestra la pantalla de mesas.
     */
    public void mostrarPantallaMesas() {
        if (mesas != null) {
            mesas.dispose();
        }

        if (comandas != null) {
            comandas.dispose();
            comandas = null;
        }

        if (pedidos != null) {
            pedidos.dispose();
            pedidos = null;
        }

        mesas = new frmMesas(this);
        mesas.setVisible(true);

        if (isMesero != null) {
            isMesero.dispose();
        }
    }

    /**
     * Abre el módulo de clientes para seleccionar uno en la comanda.
     */
    public void abrirClientesParaComanda() {
        if (comandas != null) {
            comandas.setVisible(false);
        }

        if (coordinador != null) {
            coordinador.mostrarClientesParaComanda();
        }
    }

    /**
     * Recibe el cliente seleccionado para asociarlo a la comanda.
     *
     * @param cliente cliente seleccionado
     */
    public void recibirClienteComanda(ClienteDTO cliente) {
        if (comandas != null) {
            comandas.recibirClienteFrecuente(cliente);
            comandas.setVisible(true);
        }
    }

    /**
     * Muestra la ventana de resumen de la comanda.
     *
     * @param comanda comanda a visualizar
     */
    public void abrirResumen(ComandaDTO comanda) {
        if (resumen == null) {
            resumen = new frmResumen(comanda, this);
        }
        resumen.setVisible(true);
    }

    /**
     * Refresca la información mostrada en la pantalla de comandas.
     */
    public void refrescarComandas() {
        if (comandas != null) {
            comandas.agregarPedidos();
        }
    }

    /**
     * Registra una nueva comanda.
     *
     * @param comanda comanda a registrar
     * @return comanda registrada
     * @throws NegocioException si ocurre un error
     */
    public ComandaDTO registrarComanda(ComandaDTO comanda) throws NegocioException {
        return comandaBO.guardarComanda(comanda);
    }

    /**
     * Actualiza una comanda existente.
     *
     * @param comandaDTO comanda actualizada
     * @return comanda actualizada
     * @throws NegocioException si ocurre un error
     */
    public ComandaDTO actualizarComanda(ComandaDTO comandaDTO) throws NegocioException {
        return comandaBO.actualizarComanda(comandaDTO);
    }

    /**
     * Actualiza los registros de un cliente frecuente.
     *
     * @param comanda comanda relacionada
     * @throws NegocioException si ocurre un error
     */
    public void actualizarRegistrosClienteFrecuente(ComandaDTO comanda) throws NegocioException {
        comandaBO.actualizarClienteFrecuente(comanda);
    }

    /**
     * Elimina una comanda por su identificador.
     *
     * @param idComanda identificador de la comanda
     * @throws NegocioException si ocurre un error
     */
    public void eliminarComanda(Long idComanda) throws NegocioException {
        comandaBO.eliminarComanda(idComanda);
    }

    /**
     * Obtiene todas las comandas registradas.
     *
     * @return lista de comandas
     * @throws NegocioException si ocurre un error
     */
    public List<ComandaDTO> obtenerComandas() throws NegocioException {
        return comandaBO.obtenerComandas();
    }

    /**
     * Obtiene una comanda por su identificador.
     *
     * @param id identificador de la comanda
     * @return comanda encontrada
     * @throws NegocioException si ocurre un error
     */
    public ComandaDTO obtenerComanda(Long id) throws NegocioException {
        return comandaBO.buscarComandaPorId(id);
    }

    /**
     * Valida si un mesero existe en el sistema.
     *
     * @param usuario nombre del usuario
     * @return true si existe, false en caso contrario
     * @throws NegocioException si ocurre un error
     */
    public boolean validarMesero(String usuario) throws NegocioException {
        return meseroBO.existeMesero(usuario);
    }

    /**
     * Registra un nuevo mesero.
     *
     * @param usuario nombre del mesero
     * @throws NegocioException si ocurre un error
     */
    public void registrarMesero(String usuario) throws NegocioException {
        meseroBO.registrarMesero(usuario);
    }

    /**
     * Obtiene la lista de mesas registradas.
     *
     * @return lista de mesas
     * @throws NegocioException si ocurre un error
     */
    public List<MesaDTO> obtenerMesas() throws NegocioException {
        return mesaBO.obtenerMesas();
    }

    /**
     * Cambia el estado de una mesa.
     *
     * @param idMesa identificador de la mesa
     * @param estado nuevo estado
     * @throws NegocioException si ocurre un error
     */
    public void cambiarEstadoMesa(Long idMesa, EstadoMesaDTO estado) throws NegocioException {
        mesaBO.cambiarEstadoMesa(idMesa, estado.name());
    }

    /**
     * Consulta todos los productos disponibles.
     *
     * @return lista de productos
     * @throws NegocioException si ocurre un error
     */
    public List<ProductoDTO> consultarProductos() throws NegocioException {
        return productoBO.consultarTodosProductos();
    }

    /**
     * Obtiene un producto por su identificador.
     *
     * @param idProducto identificador del producto
     * @return producto encontrado
     * @throws NegocioException si ocurre un error
     */
    public ProductoDTO obtenerProducto(Long idProducto) throws NegocioException {
        return productoBO.consultarProductoPorID(idProducto);
    }

    /**
     * Agrega un producto a la interfaz de pedidos.
     *
     * @param producto producto a agregar
     */
    public void agregarProductoInterfaz(ProductoDTO producto) {
        pedidos.agregarProductosComanda(producto);
    }

    /**
     * Actualiza el total mostrado en la interfaz de pedidos.
     *
     * @param monto monto a sumar
     */
    public void actualizarTotal(Double monto) {
        pedidos.actualizarTotal(monto);
    }

    /**
     * Consulta productos filtrados por nombre.
     *
     * @param nombre nombre a buscar
     * @return lista de productos coincidentes
     * @throws NegocioException si ocurre un error
     */
    public List<ProductoDTO> consultarProductosFiltrados(String nombre) throws NegocioException {
        return coordinador.productosFiltrados(nombre);
    }

    /**
     * Actualiza el inventario de ingredientes.
     *
     * @param ingrediente identificador del ingrediente
     * @param cantidad cantidad a modificar
     * @param tipo tipo de movimiento
     * @throws NegocioException si ocurre un error
     */
    public void actualizarIngredientes(Long ingrediente, Integer cantidad,
            TipoMovimiento tipo) throws NegocioException {
        coordinador.actualizarIngredientes(ingrediente, cantidad, tipo);
    }
}
