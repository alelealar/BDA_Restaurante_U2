/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import dtos.ProductoDTO;
import dtos.ProductoIngredienteDTO;
import dtos.ProductoNuevoDTO;
import entidades.Producto;
import entidades.ProductoIngrediente;
import enumerators.DisponibilidadProducto;
import enumerators.EstadoProducto;
import enumerators.EstadoProductoDTO;
import enumerators.TipoProducto;
import enumerators.TipoProductoDTO;
import excepciones.NegocioException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase adaptadora encargada de convertir los objetos de tipo Producto a su(s) 
 * clase(s) DTO correspondiente.  
 * 
 * @author María José Valdez Iglesias - 262775
 */
public class ProductoAdapter {
    
    /**
     * Método que convierte un objeto de tipo entidad a su versión DTO.
     * @param producto objeto a transformar.
     * @return un objeto de tipo ProductoDTO.
     */
    public static ProductoDTO entidadADTO(Producto producto){
        if(producto == null){
            return null;
        }
      
        return new ProductoDTO(
                producto.getId(),
                producto.getIdentificador(),
                producto.getNombre(),
                TipoProductoDTO.valueOf(producto.getTipo().name()),
                producto.getDescripcion(),
                producto.getPrecio(),
                EstadoProductoDTO.valueOf(producto.getEstado().name()),
                producto.getDisponibilidad().toString(),
                ProductoIngredienteAdapter.listaEntidadADTO(producto.getDetallesIngredientes()),
                producto.getUrlImagen()
        );
    }
    
    /**
     * Método que transforma los objetos de tipo DTO a Entidad, ya sean objetos
     * ProductoDTO o ProductoNuevoDTO.
     * @param dto DTO a transformar a su versión entidad.
     * @return El objeto de tipo entidad con toda su información.
     * @throws NegocioException Salta excepción si la clase del objeto del 
     * parámetro no es adecuada (ProductoDTO/ProductoNuevoDTO).
     */
    public static Producto dtoAEntidad(Object dto) throws NegocioException {
        if(dto == null){
            return null;
        }
        
        if (dto instanceof ProductoNuevoDTO p) {
            Producto pNuevo = new Producto(
                    null,
                    null,
                    p.getNombre(),
                    TipoProducto.valueOf(p.getTipo().name()),
                    p.getDescripcion(),
                    p.getPrecio(),
                    EstadoProducto.valueOf(p.getEstado().name()),
                    DisponibilidadProducto.DISPONIBLE,
                    p.getUrlImagen()
            );
            
            if(p.getIngredientes() != null){
                for(ProductoIngredienteDTO detalle : p.getIngredientes()){
                    ProductoIngrediente detalleEntidad = ProductoIngredienteAdapter.dtoAEntidad(detalle, null);
                    pNuevo.agregarProductoIngrediente(detalleEntidad);
                }
            }
            
            return pNuevo;
        }
        
        if (dto instanceof ProductoDTO p) {
            Producto pRegistrado = new Producto(
                    p.getId(),
                    p.getIdentificador(),
                    p.getNombre(),
                    TipoProducto.valueOf(p.getTipo().name()),
                    p.getDescripcion(),
                    p.getPrecio(),
                    EstadoProducto.valueOf(p.getEstado().name()),
                    DisponibilidadProducto.valueOf(p.getDisponibilidad()),
                    p.getUrlImagen()
            );
            
            if(p.getIngredientes() != null){
                for(ProductoIngredienteDTO detalle : p.getIngredientes()){
                    ProductoIngrediente detalleEntidad = ProductoIngredienteAdapter.dtoAEntidad(detalle, pRegistrado);
                    pRegistrado.agregarProductoIngrediente(detalleEntidad);
                }
            }
            
            return pRegistrado;
        }
        
        throw new NegocioException("DTO inválido: " + dto.getClass());
    }
    
    /**
     * Método que transforma una lista de objetos de entidad a su versión DTO.
     * @param productos Lista con objetos de la entidad producto.
     * @return Lista con objetos de ProductoDTO.
     */
    public static List<ProductoDTO> listaEntidadADTO(List<Producto> productos){
        List<ProductoDTO> listaDtos = new ArrayList<>();

        for (Producto p : productos) {
            listaDtos.add(entidadADTO(p));
        }

        return listaDtos;
    }
}
