package controlador;

import dtos.ClienteDTO;
import dtos.ComandaDTO;
import dtos.MesaDTO;
import dtos.ProductoDTO;
import enumerators.EstadoMesaDTO;
import enumerators.TipoMovimiento;
import excepciones.NegocioException;
import interfaces.IClienteBO;
import interfaces.IComandaBO;
import interfaces.IMesaBO;
import interfaces.IMeseroBO;
import interfaces.IProductoBO;
import java.util.List;
import objetosNegocio.ClienteBO;
import objetosNegocio.ComandaBO;
import objetosNegocio.MesaBO;
import objetosNegocio.MeseroBO;
import objetosNegocio.ProductoBO;
import pantallas.frmInicio;
import pantallas.moduloComandas.frmComandas;
import pantallas.moduloComandas.frmISMesero;
import pantallas.moduloComandas.frmMesas;
import pantallas.moduloComandas.frmPedidos;

/**
 * Clase coordinadora del módulo de comandas. Se encarga de gestionar la
 * comunicación entre la capa de presentación (interfaces gráficas) y la lógica
 * de negocio (BO).
 *
 * Controla la navegación entre pantallas y delega operaciones al negocio.
 *
 * @author Brian Kaleb Sandoval Rodríguez - 00000262741
 */
public class CoordinadorModuloComandas {

    /**
     * Pantalla de inicio de sesión del mesero
     */
    private frmISMesero isMesero;

    /**
     * Pantalla de mesas
     */
    private frmMesas mesas;

    /**
     * Pantalla de comandas
     */
    private frmComandas comandas;

    /**
     * Pantalla de pedidos
     */
    private frmPedidos pedidos;

    /**
     * Pantalla inicio del sistema
     */
    private frmInicio inicio;

    /**
     * Objeto de negocio para comandas
     */
    private final IComandaBO comandaBO;

    /**
     * Objeto de negocio para meseros
     */
    private final IMeseroBO meseroBO;

    /**
     * Objeto de negocio para mesas
     */
    private final IMesaBO mesaBO;

    /**
     * Objeto de negocio para productos
     */
    private final IProductoBO productoBO;

    /**
     * Coordinador principal del sistema
     */
    private CoordinadorInterfaces coordinador;

    /**
     * Constructor que inicializa las instancias de negocio mediante patrón
     * Singleton.
     */
    public CoordinadorModuloComandas() {
        comandaBO = ComandaBO.getInstance();
        meseroBO = MeseroBO.getInstance();
        mesaBO = MesaBO.getInstance();
        productoBO = ProductoBO.getInstance();
    }

    /**
     * Establecer el coordinador para permitirnos movernos por el sistema desde
     * principal.
     *
     * @param coordinador coordinador de interfaces principal.
     */
    public void setCoordinador(CoordinadorInterfaces coordinador) {
        this.coordinador = coordinador;
    }

    public void volverInicio() {
        if (coordinador != null) {
            coordinador.regresarInicio();
        }
    }

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
     * Muestra la pantalla para crear pedidos de una comanda.
     *
     * @param mesa Mesa asociada al pedido
     * @param comanda Comanda actual
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
     * @param mesa Mesa seleccionada
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
     * Muestra la pantalla de mesas. Cierra cualquier otra pantalla abierta.
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

    public void abrirClientesParaComanda() {
        if (comandas != null) {
            comandas.setVisible(false);
        }
        if (coordinador != null) {
            coordinador.mostrarClientesParaComanda();
        }

    }

    public void recibirClienteComanda(ClienteDTO cliente) {
        if (comandas != null) {
            comandas.recibirClienteFrecuente(cliente);
            comandas.setVisible(true);
        }
    }

    /**
     * Refresca la información de la pantalla de comandas.
     */
    public void refrescarComandas() {
        if (comandas != null) {
            comandas.agregarPedidos();
        }
    }

    /**
     * Registra una nueva comanda en el sistema.
     *
     * @param comanda Comanda a registrar
     * @return Comanda registrada
     * @throws NegocioException Si ocurre un error en la operación
     */
    public ComandaDTO registrarComanda(ComandaDTO comanda) throws NegocioException {
        return comandaBO.guardarComanda(comanda);
    }

    /**
     * Actualiza una comanda existente.
     *
     * @param comandaDTO Comanda con datos actualizados
     * @return Comanda actualizada
     * @throws NegocioException Si ocurre un error en la operación
     */
    public ComandaDTO actualizarComanda(ComandaDTO comandaDTO) throws NegocioException {
        return comandaBO.actualizarComanda(comandaDTO);
    }

    public void actualizarRegistrosClienteFrecuente(ComandaDTO comanda) throws NegocioException {
        comandaBO.actualizarClienteFrecuente(comanda);
    }

    /**
     * Elimina una comanda por su identificador.
     *
     * @param idComanda ID de la comanda
     * @throws NegocioException Si ocurre un error en la operación
     */
    public void eliminarComanda(Long idComanda) throws NegocioException {
        comandaBO.eliminarComanda(idComanda);
    }

    /**
     * Obtiene la lista de todas las comandas registradas.
     *
     * @return Lista de comandas
     * @throws NegocioException Si ocurre un error en la operación
     */
    public List<ComandaDTO> obtenerComandas() throws NegocioException {
        return comandaBO.obtenerComandas();
    }

    /**
     * Obtiene una comanda por su ID.
     *
     * @param id ID de la comanda
     * @return Comanda encontrada
     * @throws NegocioException Si ocurre un error en la operación
     */
    public ComandaDTO obtenerComanda(Long id) throws NegocioException {
        return comandaBO.buscarComandaPorId(id);
    }

    /**
     * Valida si un mesero existe en el sistema.
     *
     * @param usuario Nombre del usuario
     * @return true si existe, false en caso contrario
     * @throws NegocioException Si ocurre un error
     */
    public boolean validarMesero(String usuario) throws NegocioException {
        return meseroBO.existeMesero(usuario);
    }

    /**
     * Registra un nuevo mesero en el sistema.
     *
     * @param usuario Nombre del mesero
     * @throws NegocioException Si ocurre un error
     */
    public void registrarMesero(String usuario) throws NegocioException {
        meseroBO.registrarMesero(usuario);
    }

    /**
     * Obtiene la lista de mesas.
     *
     * @return Lista de mesas
     * @throws NegocioException Si ocurre un error
     */
    public List<MesaDTO> obtenerMesas() throws NegocioException {
        return mesaBO.obtenerMesas();
    }

    /**
     * Cambia el estado de una mesa.
     *
     * @param idMesa ID de la mesa
     * @param estado Nuevo estado
     * @throws NegocioException Si ocurre un error
     */
    public void cambiarEstadoMesa(Long idMesa, EstadoMesaDTO estado) throws NegocioException {
        mesaBO.cambiarEstadoMesa(idMesa, estado.name());
    }

    /**
     * Consulta todos los productos disponibles.
     *
     * @return Lista de productos
     * @throws NegocioException Si ocurre un error
     */
    public List<ProductoDTO> consultarProductos() throws NegocioException {
        return productoBO.consultarTodosProductos();
    }

    /**
     * Obtiene un producto por su ID.
     *
     * @param idProducto ID del producto
     * @return Producto encontrado
     * @throws NegocioException Si ocurre un error
     */
    public ProductoDTO obtenerProducto(Long idProducto) throws NegocioException {
        return productoBO.consultarProductoPorID(idProducto);
    }

    /**
     * Agrega un producto a la interfaz de pedidos.
     *
     * @param producto Producto a agregar
     */
    public void agregarProductoInterfaz(ProductoDTO producto) {
        this.pedidos.agregarProductosComanda(producto);
    }

    /**
     * Actualiza el total mostrado en la interfaz de pedidos.
     *
     * @param monto Monto a agregar al total
     */
    public void actualizarTotal(Double monto) {
        this.pedidos.actualizarTotal(monto);
    }

    public List<ProductoDTO> consultarProductosFiltrados(String nombre) throws NegocioException {
        return coordinador.productosFiltrados(nombre);
    }

    public void actualizarIngredientes(Long ingrediente, Integer cantidad, TipoMovimiento tipo) throws NegocioException {
        coordinador.actualizarIngredientes(ingrediente, cantidad, tipo);
    }
}
