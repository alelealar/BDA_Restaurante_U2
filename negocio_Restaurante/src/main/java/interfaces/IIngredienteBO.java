/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package interfaces;

import dtos.IngredienteNuevoDTO;
import dtos.*;
import enumerators.TipoMovimiento;
import excepciones.NegocioException;
import java.util.List;

/**
 *
 * @author Alejandra Leal Armenta, 262719
 */
public interface IIngredienteBO {

    public void agregarIngrediente(IngredienteNuevoDTO ingrediente) throws NegocioException;
    
    public void actualizarStock(Long idIngrediente, int cantidad, TipoMovimiento tipo) throws NegocioException;
    
    public List<IngredienteDTO> obtenerIngredientes() throws NegocioException;
}
