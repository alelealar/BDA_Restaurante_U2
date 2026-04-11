/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import dtos.ProductoDTO;
import dtos.ProductoIngredienteDTO;
import entidades.Producto;
import entidades.ProductoIngrediente;
import excepciones.NegocioException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase adaptadora encargada de convertir objetos entre la entidad ProductoIngredientes
 * y su correspondiente DTO (ProductoIngredienteDTO).
 *
 * Esta clase permite desacoplar la capa de persistencia de la capa de
 * presentación o lógica de negocio, facilitando el manejo de datos y evitando
 * exponer directamente las entidades.
 *
 * Proporciona métodos para convertir objetos individuales y listas de entidades
 * a DTOs.
 * 
 * 
 * @author Alejandra Leal Armenta - 262719
 * @author María José Valdez Iglesias - 262775
 */
public class ProductoIngredienteAdapter {
    
    /**
     * Método que convierte un objeto de tipo entidad a su versión DTO.
     * @param detalles objeto a transformar.
     * @return un objeto de tipo ProductoIngredienteDTO.
     */
    public static ProductoIngredienteDTO entidadADTO(ProductoIngrediente entidad){
        if(entidad == null){
            return null;
        }
        
        return new ProductoIngredienteDTO(
                entidad.getId(),
                IngredienteAdapter.entidadADTO(entidad.getIngrediente()),
                entidad.getCantidad()
        );
    }
    
    /**
     * Método que transforma los objetos de tipo DTO a Entidad.
     * @param dto DTO a transformar a su versión entidad.
     * @return El objeto de tipo entidad con toda su información.
     */
    public static ProductoIngrediente dtoAEntidad(ProductoIngredienteDTO dto, Producto producto) throws NegocioException {
        if(dto == null){
            return null;
        }
        
        return new ProductoIngrediente(
                dto.getId(), 
                dto.getCantidad(), 
                IngredienteAdapter.dtoAEntidad(dto.getIngrediente()), 
                producto
        );
    }
    
    /**
     * Método que transforma una lista de objetos de entidad a su versión DTO.
     * @param detalles Lista con objetos de la entidad producto.
     * @return Lista con objetos de ProductoIngredienteDTO.
     */
    public static List<ProductoIngredienteDTO> listaEntidadADTO(List<ProductoIngrediente> detalles){
        List<ProductoIngredienteDTO> listaDtos = new ArrayList<>();

        for (ProductoIngrediente d : detalles) {
            listaDtos.add(entidadADTO(d));
        }

        return listaDtos;
    }
}
