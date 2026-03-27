/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dtos.ClienteDTO;
import dtos.ClienteNuevoDTO;
import excepciones.NegocioException;
import interfaces.IClienteBO;
import java.util.List;
import objetosNegocio.ClienteBO;
import pantallas.frmAgregarCliente;

/**
 * Implementación del Coordinador de operaciones de las clases Bussiones Object
 * (BOs). 
 * 
 * Aquí se maneja la lógica del negocio y navega entre las operaciones y su 
 * flujo.
 * 
 * @author Brian Kaleb Sandoval Rodriguez - 00000262741
 * @author Alejandra Leal Armenta - 00000262719
 * @author Maria Jose Valdez Iglesias - 00000262775
 */
public class Coordinador {
    
    // Capa de Negocio BOs
    private final IClienteBO clienteBO;
    
    public Coordinador(){
        this.clienteBO = ClienteBO.getInstance();
    }
    
    /**
     * Metodo que llama al BO (capa de negocio) para agregar un cliente.
     * @param cliente DTO con la información del cliente a registrar
 *   * @throws NegocioException si ocurre un error en la capa de negocio
     */
    public void agregarCliente(ClienteNuevoDTO cliente) throws NegocioException{
        clienteBO.registrarCliente(cliente);
    }
    
    /**
     * Metodo que llama al BO (capa de negocio) para pedirle los datos de negocio y mostrarlos
     * en la tabla.
     * @return 
     * @throws NegocioException 
     */
    public List<ClienteDTO> obtenerClientes() throws NegocioException {
        return clienteBO.obtenerClientes();
    }
    
    public void actualizarCliente(ClienteDTO clienteDTO) throws NegocioException{
        clienteBO.actualizarCliente(clienteDTO);
    }
    
    public ClienteDTO buscarClientePorId(Long id) throws NegocioException {
        return clienteBO.buscarClientePorId(id);
    }
    
    public void eliminarCliente(ClienteDTO c) throws NegocioException{
         clienteBO.eliminarCliente(c.getId());
    }
    
}
