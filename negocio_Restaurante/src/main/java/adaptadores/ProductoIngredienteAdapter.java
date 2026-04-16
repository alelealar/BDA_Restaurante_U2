package adaptadores;

import dtos.ProductoDTO;
import dtos.ProductoIngredienteDTO;
import entidades.Producto;
import entidades.ProductoIngrediente;
import excepciones.NegocioException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de convertir entre la entidad ProductoIngrediente
 * y su correspondiente DTO.
 *
 * Se usa para evitar que las entidades de base de datos se usen directamente
 * en capas superiores como negocio o presentación.
 *
 * Aquí se manejan conversiones individuales y listas completas.
 * 
 * @author Alejandra Leal Armenta - 262719
 * @author María José Valdez Iglesias - 262775
 */
public class ProductoIngredienteAdapter {

    /**
     * constructor vacio.
     */
    public ProductoIngredienteAdapter() {
    }
    
    /**
     * Convierte una entidad ProductoIngrediente a su DTO.
     *
     * @param entidad objeto de base de datos a convertir
     * @return DTO equivalente o null si la entidad es null
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
     * Convierte un DTO a entidad ProductoIngrediente.
     *
     * @param dto información del detalle del producto
     * @param producto producto al que pertenece el ingrediente
     * @return entidad lista para persistencia
     * @throws NegocioException si ocurre algún problema en la conversión
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
     * Convierte una lista de entidades ProductoIngrediente a DTOs.
     *
     * @param detalles lista de entidades
     * @return lista de DTOs (vacía si no hay datos)
     */
    public static List<ProductoIngredienteDTO> listaEntidadADTO(List<ProductoIngrediente> detalles){
        List<ProductoIngredienteDTO> listaDtos = new ArrayList<>();

        for (ProductoIngrediente d : detalles) {
            listaDtos.add(entidadADTO(d));
        }

        return listaDtos;
    }
}