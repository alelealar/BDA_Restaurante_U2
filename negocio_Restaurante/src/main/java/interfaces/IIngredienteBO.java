/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package interfaces;

import dtos.IngredienteNuevoDTO;
import dtos.*;
import enumerators.TipoMovimiento;
import enumerators.Unidad;
import enumerators.UnidadDTO;
import excepciones.NegocioException;
import java.util.List;

/**
 * Define las operaciones de negocio para la gestión de ingredientes.
 * 
 * Establece los métodos que deben ser implementados para manejar
 * los ingredientes dentro del sistema.
 * 
 * @author Alejandra Leal Armenta, 262719
 */
public interface IIngredienteBO {

    /**
     * Agrega un nuevo ingrediente al sistema.
     * Valida que la información sea correcta y que no exista previamente.
     * @param ingrediente
     * @throws NegocioException 
     */
    public void agregarIngrediente(IngredienteNuevoDTO ingrediente) throws NegocioException;
    
    /**
     * Modifica el stock de un ingrediente según el tipo de movimiento.
     * Permite aumentar o disminuir la cantidad disponible, validando que la operación sea válida.
     * @param idIngrediente
     * @param cantidad
     * @param tipo
     * @throws NegocioException 
     */
    public void actualizarStock(Long idIngrediente, int cantidad, TipoMovimiento tipo) throws NegocioException;
    
    /**
     * Elimina un ingrediente del sistema.
     * Verifica que no esté en uso antes de realizar la eliminación.
     * @param ingrediente
     * @throws NegocioException 
     */
    public void eliminarIngrediente(IngredienteDTO ingrediente) throws NegocioException;
    
    /**
     * Actualiza la información de un ingrediente existente.
     * Valida los datos y aplica los cambios correspondientes.
     * @param ingrediente
     * @throws NegocioException 
     */
    public void actualizarIngrediente(IngredienteDTO ingrediente) throws NegocioException;
    
    /**
     * Obtiene la lista de ingredientes registrados en el sistema.
     * Proporciona la información necesaria para su uso en la aplicación.
     * @return
     * @throws NegocioException 
     */
    public List<IngredienteDTO> obtenerIngredientes() throws NegocioException;
    
    public List<IngredienteDTO> buscarIngredientes(String nombre, UnidadDTO unidad) throws NegocioException;
}
