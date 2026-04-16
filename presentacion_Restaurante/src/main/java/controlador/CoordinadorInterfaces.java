package controlador;

import dtos.ClienteDTO;
import dtos.ClienteNuevoDTO;
import dtos.ProductoDTO;
import enumerators.TipoMovimiento;
import excepciones.NegocioException;
import java.util.List;
import pantallas.frmAgregarCliente;
import pantallas.frmClientes;
import pantallas.frmInicio;
import pantallas.moduloIngredientes.FrmIngredientes;
import pantallas.pantallasModuloReportes.FrmReporteClientes;
import pantallas.pantallasModuloReportes.FrmReporteComandas;
import pantallasProducto.FrmProductos;

/**
 * Clase coordinadora principal de interfaces del sistema.
 *
 * Se encarga de controlar la navegación entre pantallas y de comunicar la capa
 * de presentación con los distintos módulos y coordinadores de negocio.
 *
 * @author Brian Kaleb Sandoval Rodriguez - 00000262741
 * @author Alejandra Leal Armenta - 00000262719
 * @author Maria Jose Valdez Iglesias - 00000262775
 */
public class CoordinadorInterfaces {

    /**
     * Pantalla principal del sistema.
     */
    private frmInicio formInicio;

    /**
     * Pantalla de clientes.
     */
    private frmClientes formClientes;

    /**
     * Pantalla para agregar o editar clientes.
     */
    private frmAgregarCliente formAgregarClientes;

    /**
     * Pantalla de reporte de clientes.
     */
    private FrmReporteClientes frmReporteClientes;

    /**
     * Pantalla de reporte de comandas.
     */
    private FrmReporteComandas frmReporteComandas;

    /**
     * Pantalla del módulo de ingredientes.
     */
    private FrmIngredientes frmIngredientes;

    /**
     * Pantalla del módulo de productos.
     */
    private FrmProductos frmProductos;

    /**
     * Coordinador de la capa de negocio general.
     */
    private Coordinador coordinadorNegocio;

    /**
     * Coordinador del módulo de productos.
     */
    private Coordinador_ModuloProductos coordinadorProducto;

    /**
     * Coordinador del módulo de comandas.
     */
    private CoordinadorModuloComandas coordinadorComandas;
    
    /**
     * Coordinador del modulo de ingredientes.
     */
    private Coordinador_ModuloIngredientes coordinadorIngredientes;

    /**
     * Constructor que inicializa los coordinadores del sistema.
     */
    public CoordinadorInterfaces() {
        this.coordinadorNegocio = new Coordinador();
        this.coordinadorProducto = new Coordinador_ModuloProductos();
        this.coordinadorComandas = new CoordinadorModuloComandas();
        this.coordinadorIngredientes = new Coordinador_ModuloIngredientes();
        
        this.coordinadorComandas.setCoordinador(this);
        this.coordinadorIngredientes.setCoordinadorProductos(coordinadorProducto);
    }

    /**
     * Inicia el sistema mostrando la pantalla principal.
     */
    public void iniciarSistema() {
        if (formInicio == null) {
            formInicio = new frmInicio(this);
        }
        formInicio.setVisible(true);
    }

    /**
     * Muestra el formulario de clientes.
     */
    public void mostrarFormularioClientes() {
        if (formClientes == null) {
            formClientes = new frmClientes(this);
        }

        if (formAgregarClientes == null) {
            formAgregarClientes = new frmAgregarCliente(this);
        }

        formClientes.setVisible(true);
    }

    /**
     * Muestra la pantalla del módulo de ingredientes.
     */
    public void mostrarPantallaIngredientes() {
        if (frmIngredientes == null) {
            frmIngredientes = new FrmIngredientes();
            frmIngredientes.setCoordinadorIngredientes(coordinadorIngredientes);
        }

        if (formInicio != null) {
            formInicio.dispose();
        }

        frmIngredientes.desactivarModoProducto();
        frmIngredientes.setVisible(true);
    }

    /**
     * Muestra la pantalla del módulo de productos.
     */
    public void mostrarPantallaProductos() {
        if (frmProductos == null) {
            frmProductos = new FrmProductos(coordinadorProducto);
        }

        frmProductos.setVisible(true);
    }

    /**
     * Muestra el formulario para agregar clientes.
     */
    public void mostrarFormularioAgregarClientes() {
        if (formAgregarClientes == null) {
            formAgregarClientes = new frmAgregarCliente(this);
        }

        formClientes.setVisible(false);
        formAgregarClientes.setVisible(true);
    }

    /**
     * Regresa a la pantalla principal del sistema.
     */
    public void regresarInicio() {

        coordinadorComandas.cerrarPantallas();

        if (formClientes != null) {
            formClientes.dispose();
            formClientes = null;
        }

        if (formAgregarClientes != null) {
            formAgregarClientes.dispose();
            formAgregarClientes = null;
        }

        if (formInicio == null) {
            formInicio = new frmInicio(this);
        }

        formInicio.setVisible(true);
    }

    /**
     * Muestra la pantalla de inicio de sesión de meseros.
     */
    public void mostrarISMeseros() {
        if (formInicio != null) {
            formInicio.dispose();
        }

        coordinadorComandas.mostrarPantallaISMesero();
    }

    /**
     * Muestra la pantalla principal del módulo de comandas.
     */
    public void mostrarPantallaComandas() {
        if (formClientes != null) {
            formClientes.dispose();
        }

        coordinadorComandas.mostrarPantallaMesas();
    }

    /**
     * Muestra la pantalla de reporte de clientes.
     */
    public void mostrarPantallaReporteClientes() {
        if (frmReporteClientes == null) {
            frmReporteClientes = new FrmReporteClientes();
        }

        frmReporteClientes.setVisible(true);
    }

    /**
     * Muestra la pantalla de reporte de comandas.
     */
    public void mostrarPantallaReporteComandas() {
        if (frmReporteComandas == null) {
            frmReporteComandas = new FrmReporteComandas();
        }

        frmReporteComandas.setVisible(true);
    }

    /**
     * Agrega un cliente nuevo en el sistema.
     *
     * @param cliente datos del cliente a registrar
     * @throws NegocioException si ocurre un error en negocio
     */
    public void agregarCliente(ClienteNuevoDTO cliente) throws NegocioException {
        coordinadorNegocio.agregarClienteFrecuente(cliente);
    }

    /**
     * Obtiene la lista de clientes registrados.
     *
     * @return lista de clientes
     * @throws NegocioException si ocurre un error en negocio
     */
    public List<ClienteDTO> obtenerClientes() throws NegocioException {
        return coordinadorNegocio.obtenerClientes();
    }

    /**
     * Refresca la tabla de clientes en pantalla.
     */
    public void refrescarTableFormClientes() {
        formClientes.cargarTable();
    }

    /**
     * Actualiza la información de un cliente.
     *
     * @param clienteDTO cliente con datos actualizados
     * @throws NegocioException si ocurre un error en negocio
     */
    public void actualizarCliente(ClienteDTO clienteDTO) throws NegocioException {
        coordinadorNegocio.actualizarCliente(clienteDTO);
    }

    /**
     * Muestra el formulario para editar un cliente.
     *
     * @param cliente cliente a editar
     */
    public void mostrarFormularioEditarCliente(ClienteDTO cliente) {
        frmAgregarCliente frm = new frmAgregarCliente(this);
        frm.setClienteParaEditar(cliente);
        frm.setVisible(true);
        formClientes.dispose();
    }

    /**
     * Busca un cliente por su identificador.
     *
     * @param id identificador del cliente
     * @return cliente encontrado
     * @throws NegocioException si ocurre un error en negocio
     */
    public ClienteDTO buscarClientePorId(Long id) throws NegocioException {
        return coordinadorNegocio.buscarClientePorId(id);
    }

    /**
     * Elimina un cliente del sistema.
     *
     * @param cliente cliente a eliminar
     * @throws NegocioException si ocurre un error en negocio
     */
    public void eliminarCliente(ClienteDTO cliente) throws NegocioException {
        coordinadorNegocio.eliminarCliente(cliente);
    }

    /**
     * Abre el módulo de productos.
     */
    public void abrirProductos() {
        coordinadorProducto.abrirFrmProductos();
    }

    /**
     * Muestra la pantalla de clientes para seleccionar uno en una comanda.
     */
    public void mostrarClientesParaComanda() {
        if (formClientes == null) {
            formClientes = new frmClientes(this);
        }

        formClientes.registrarClienteComanda();
        formClientes.setVisible(true);
    }

    /**
     * Envía el cliente seleccionado al módulo de comandas.
     *
     * @param cliente cliente seleccionado
     */
    public void enviarClienteComandas(ClienteDTO cliente) {
        coordinadorComandas.recibirClienteComanda(cliente);

        if (formClientes != null) {
            formClientes.dispose();
            formClientes = null;
        }
    }

    /**
     * Obtiene productos filtrados por nombre.
     *
     * @param nombre nombre a buscar
     * @return lista de productos encontrados
     * @throws NegocioException si ocurre un error en negocio
     */
    public List<ProductoDTO> productosFiltrados(String nombre)
            throws NegocioException {
        return coordinadorNegocio.obtenerProductosFiltrados(nombre);
    }

    /**
     * Actualiza el inventario de ingredientes.
     *
     * @param ingrediente identificador del ingrediente
     * @param cantidad cantidad a modificar
     * @param tipo tipo de movimiento
     * @throws NegocioException si ocurre un error en negocio
     */
    public void actualizarIngredientes(Long ingrediente, Integer cantidad,
            TipoMovimiento tipo) throws NegocioException {
        coordinadorNegocio.actualizarIngredientes(ingrediente, cantidad, tipo);
    }
}
