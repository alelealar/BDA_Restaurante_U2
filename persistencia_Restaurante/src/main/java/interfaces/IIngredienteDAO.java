/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package interfaces;

import entidades.Ingrediente;
import enumerators.Unidad;
import excepciones.PersistenciaException;
import java.util.List;

/**
 * Define las operaciones de acceso a datos para la gestión de ingredientes.
 * 
 * Establece los métodos necesarios para interactuar con la base de datos
 * en relación a los ingredientes.
 * 
 * @author Alejandra Leal Armenta, 262719
 */
public interface IIngredienteDAO {
    
    
    /**
     * Inserta un nuevo ingrediente en la base de datos.
     * Guarda la información del ingrediente proporcionado.
     * @param ingrediente
     * @return
     * @throws PersistenciaException 
     */
    public Ingrediente agregarIngrediente(Ingrediente ingrediente) throws PersistenciaException;
    
    /**
     * Actualiza el stock de un ingrediente en la base de datos.
     * Modifica la cantidad disponible registrada.
     * @param ingrediente
     * @return
     * @throws PersistenciaException 
     */
    public Ingrediente actualizarStock(Ingrediente ingrediente) throws PersistenciaException;
        
    /**
     * Busca un ingrediente por su identificador.
     * Recupera el ingrediente correspondiente si existe.
     * @param id
     * @return
     * @throws PersistenciaException 
     */
    public Ingrediente buscarPorId(Long id) throws PersistenciaException;
    
    /**
     * Obtiene todos los ingredientes registrados en la base de datos.
     * Devuelve la lista completa de ingredientes.
     * @return
     * @throws PersistenciaException 
     */
    public List<Ingrediente> obtenerIngredientes() throws PersistenciaException;
    
    /**
     * Obtiene el último identificador registrado.
     * Permite generar nuevos identificadores consecutivos.
     * @return
     * @throws PersistenciaException 
     */
    public String obtenerUltimoIdentificador() throws PersistenciaException;
    
    /**
     * Elimina un ingrediente de la base de datos.
     * Remueve el registro correspondiente.
     * @param ingrediente
     * @return
     * @throws PersistenciaException 
     */
    public Ingrediente eliminarIngrediente(Ingrediente ingrediente) throws PersistenciaException;
    
    /**
     * Actualiza la información de un ingrediente en la base de datos.
     * Aplica los cambios sobre el registro existente.
     * @param ingrediente
     * @return
     * @throws PersistenciaException 
     */
    public Ingrediente actualizarIngrediente(Ingrediente ingrediente) throws PersistenciaException;
    
    /**
     * Verifica si existe un ingrediente con el mismo nombre y unidad.
     * Permite evitar registros duplicados.
     * @param id
     * @param nombre
     * @param unidad
     * @return
     * @throws PersistenciaException 
     */
    public boolean existeIngredienteDuplicado(Long id, String nombre, Unidad unidad) throws PersistenciaException;
 
}
