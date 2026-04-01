/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package interfaces;

import entidades.Ingrediente;
import excepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author Alejandra Leal Armenta, 262719
 */
public interface IIngredienteDAO {
    
    
    public Ingrediente agregarIngrediente(Ingrediente ingrediente) throws PersistenciaException;
    
    public Ingrediente actualizarStock(Ingrediente ingrediente) throws PersistenciaException;
        
    public Ingrediente buscarPorId(Long id) throws PersistenciaException;
    
    public List<Ingrediente> obtenerIngredientes() throws PersistenciaException;
    
    public String obtenerUltimoIdentificador() throws PersistenciaException;
    
}
