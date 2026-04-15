/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dtos.ClienteDTO;
import dtos.ClienteNuevoDTO;
import dtos.ProductoDTO;
import enumerators.TipoMovimiento;
import excepciones.NegocioException;
import interfaces.IClienteBO;
import interfaces.IIngredienteBO;
import interfaces.IProductoBO;
import java.util.List;
import objetosNegocio.ClienteBO;
import objetosNegocio.IngredienteBO;
import objetosNegocio.ProductoBO;

/**
 * Implementación del coordinador de operaciones de la capa de negocio.
 *
 * Esta clase centraliza la comunicación con los Business Objects (BO),
 * permitiendo ejecutar operaciones relacionadas con clientes, productos e
 * ingredientes.
 *
 * @author Brian Kaleb Sandoval Rodriguez - 00000262741
 * @author Alejandra Leal Armenta - 00000262719
 * @author Maria Jose Valdez Iglesias - 00000262775
 */
public class Coordinador {

    /**
     * Objeto de negocio para clientes.
     */
    private final IClienteBO clienteBO;

    /**
     * Objeto de negocio para productos.
     */
    private final IProductoBO productoBO;

    /**
     * Objeto de negocio para ingredientes.
     */
    private final IIngredienteBO ingredienteBO;

    /**
     * Constructor que inicializa los objetos de negocio.
     */
    public Coordinador() {
        this.clienteBO = ClienteBO.getInstance();
        this.productoBO = ProductoBO.getInstance();
        this.ingredienteBO = IngredienteBO.getInstance();
    }

    /**
     * Registra un nuevo cliente en el sistema.
     *
     * @param cliente datos del cliente a registrar
     * @throws NegocioException si ocurre un error en la capa de negocio
     */
    public void agregarCliente(ClienteNuevoDTO cliente) throws NegocioException {
        clienteBO.registrarCliente(cliente);
    }

    /**
     * Registra un nuevo cliente frecuente en el sistema.
     *
     * @param cliente datos del cliente a registrar
     * @throws NegocioException si ocurre un error en la capa de negocio
     */
    public void agregarClienteFrecuente(ClienteNuevoDTO cliente) throws NegocioException {
        clienteBO.registrarClienteFrecuente(cliente);
    }

    /**
     * Obtiene o registra el cliente general del sistema.
     *
     * @throws NegocioException si ocurre un error en la capa de negocio
     */
    public void agregarClienteGeneral() throws NegocioException {
        clienteBO.obtenerClienteGeneral();
    }

    /**
     * Obtiene la lista de clientes registrados.
     *
     * @return lista de clientes
     * @throws NegocioException si ocurre un error en la capa de negocio
     */
    public List<ClienteDTO> obtenerClientes() throws NegocioException {
        return clienteBO.obtenerClientes();
    }

    /**
     * Actualiza la información de un cliente existente.
     *
     * @param clienteDTO cliente con datos actualizados
     * @throws NegocioException si ocurre un error en la capa de negocio
     */
    public void actualizarCliente(ClienteDTO clienteDTO)
            throws NegocioException {
        clienteBO.actualizarCliente(clienteDTO);
    }

    /**
     * Busca un cliente por su identificador.
     *
     * @param id identificador del cliente
     * @return cliente encontrado
     * @throws NegocioException si ocurre un error en la capa de negocio
     */
    public ClienteDTO buscarClientePorId(Long id) throws NegocioException {
        return clienteBO.buscarClientePorId(id);
    }

    /**
     * Elimina un cliente del sistema.
     *
     * @param c cliente a eliminar
     * @throws NegocioException si ocurre un error en la capa de negocio
     */
    public void eliminarCliente(ClienteDTO c) throws NegocioException {
        clienteBO.eliminarCliente(c.getId());
    }

    /**
     * Obtiene productos activos filtrados por nombre.
     *
     * @param nombre nombre a buscar
     * @return lista de productos encontrados
     * @throws NegocioException si ocurre un error en la capa de negocio
     */
    public List<ProductoDTO> obtenerProductosFiltrados(String nombre)
            throws NegocioException {
        return productoBO.buscarProductosActivos(nombre, null);
    }

    /**
     * Actualiza el stock de ingredientes.
     *
     * @param ingrediente identificador del ingrediente
     * @param cantidad cantidad a modificar
     * @param tipo tipo de movimiento realizado
     * @throws NegocioException si ocurre un error en la capa de negocio
     */
    public void actualizarIngredientes(Long ingrediente, Integer cantidad,
            TipoMovimiento tipo) throws NegocioException {
        ingredienteBO.actualizarStock(ingrediente, cantidad, tipo);
    }
}
