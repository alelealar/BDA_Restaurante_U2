package controlador;

import dtos.ClienteDTO;
import dtos.ClienteNuevoDTO;
import excepciones.NegocioException;
import java.util.List;
import objetosNegocio.ClienteBO;
import pantallas.frmAgregarCliente;
import pantallas.frmClientes;
import pantallas.frmInicio;

/**
 *
 * @author Brian Kaleb Sandoval Rodriguez - 262741
 * @author Alejandra Leal Armenta - 262719
 * @author Maria Jose Valdez Iglesias - 262775
 */
public class CoordinadorInterfaces {

    private frmInicio formInicio;
    private frmClientes formClientes;
    private frmAgregarCliente formAgregarClientes;
    private Coordinador coordinadorNegocio;
    
    private ClienteBO clienteBO;

    public CoordinadorInterfaces() {
        this.coordinadorNegocio = new Coordinador();
    }

    public void iniciarSistema() {
        if (formInicio == null) {
            formInicio = new frmInicio(this);
        }
        formInicio.setVisible(true);
    }

    /**
     * Muestra el formulario de los clientes
     */
    public void mostrarFormularioClientes() {
        if (formClientes == null) {
            formClientes = new frmClientes(this);
        }
        if (formAgregarClientes == null) {
            formAgregarClientes = new frmAgregarCliente(this);
        }
        formClientes.setVisible(true);
        formInicio.dispose();
        formAgregarClientes.dispose();
        
    }

    /**
     * mustra el formulario de agregar clientes
     */
    public void mostrarFormularioAgregarClientes() {
        if (formAgregarClientes == null) {
            formAgregarClientes = new frmAgregarCliente(this);
        }
        formClientes.setVisible(false);
        formAgregarClientes.setVisible(true);
    }
    
    /**
     * Metodo que llama al BO (capa de negocio) para agregar un cliente.
     * 
     * Este metodo llama al coordinador de negocio
     * @param cliente DTO con la información del cliente a registrar
 *   * @throws NegocioException si ocurre un error en la capa de negocio
     */
    public void agregarCliente(ClienteNuevoDTO cliente) throws NegocioException{
        coordinadorNegocio.agregarCliente(cliente);
    }
    
    /**
     * Metodo que llama al BO (capa de negocio) para pedirle los datos de negocio y mostrarlos
     * en la tabla.
     * 
     * Este metodo llama al coordinador de negocio
     * @return 
     * @throws NegocioException 
     */
    public List<ClienteDTO> obtenerClientes() throws NegocioException {
        return coordinadorNegocio.obtenerClientes();
    }
    
    /**
     * Hace que la tabla se puedo actualizar en el momento en el que
     * se agrega un cliente desde el frmAgregarCliente
     */
    public void refrescarTableFormClientes(){
        formClientes.cargarTable();
    }
    
    public void actualizarCliente(ClienteDTO clienteDTO) throws NegocioException{
        coordinadorNegocio.actualizarCliente(clienteDTO);
    }
    
    public void mostrarFormularioEditarCliente(ClienteDTO cliente) {
        frmAgregarCliente frm = new frmAgregarCliente(this);
        frm.setClienteParaEditar(cliente);
        frm.setVisible(true);
        formClientes.dispose();
    }
    
    public ClienteDTO buscarClientePorId(Long id) throws NegocioException{
        return coordinadorNegocio.buscarClientePorId(id);
    }
    
    public void eliminarCliente(ClienteDTO cliente) throws NegocioException{
        coordinadorNegocio.eliminarCliente(cliente);
    }

}
