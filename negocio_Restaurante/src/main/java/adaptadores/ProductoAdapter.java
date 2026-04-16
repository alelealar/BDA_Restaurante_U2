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
 * Adaptador encargado de convertir entre la entidad Producto y su DTO correspondiente.
 *
 * Esta clase desacopla la capa de persistencia de la capa de negocio/presentación,
 * permitiendo la transformación de entidades complejas a objetos DTO.
 *
 * Incluye conversiones individuales y de listas, así como manejo de ingredientes
 * asociados a un producto.
 *
 * @author María José Valdez Iglesias - 262775
 */
public class ProductoAdapter {

    /**
     * Constructor vacio.
     */
    public ProductoAdapter() {
    }

    /**
     * Convierte una entidad Producto a su representación ProductoDTO.
     *
     * @param producto entidad Producto a convertir
     * @return objeto ProductoDTO equivalente o null si la entrada es null
     */
    public static ProductoDTO entidadADTO(Producto producto) {
        if (producto == null) {
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
     * Convierte un DTO (ProductoDTO o ProductoNuevoDTO) a entidad Producto.
     *
     * Soporta ambos tipos de DTO para creación y actualización de productos.
     *
     * @param dto objeto ProductoDTO o ProductoNuevoDTO a convertir
     * @return entidad Producto equivalente
     * @throws NegocioException si el DTO no es válido
     */
    public static Producto dtoAEntidad(Object dto) throws NegocioException {
        if (dto == null) {
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

            if (p.getIngredientes() != null) {
                for (ProductoIngredienteDTO detalle : p.getIngredientes()) {
                    ProductoIngrediente detalleEntidad =
                            ProductoIngredienteAdapter.dtoAEntidad(detalle, null);
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

            if (p.getIngredientes() != null) {
                for (ProductoIngredienteDTO detalle : p.getIngredientes()) {
                    ProductoIngrediente detalleEntidad =
                            ProductoIngredienteAdapter.dtoAEntidad(detalle, pRegistrado);
                    pRegistrado.agregarProductoIngrediente(detalleEntidad);
                }
            }

            return pRegistrado;
        }

        throw new NegocioException("DTO inválido: " + dto.getClass().getSimpleName());
    }

    /**
     * Convierte una lista de entidades Producto a una lista de ProductoDTO.
     *
     * @param productos lista de entidades Producto
     * @return lista de objetos ProductoDTO (nunca null)
     */
    public static List<ProductoDTO> listaEntidadADTO(List<Producto> productos) {
        List<ProductoDTO> listaDtos = new ArrayList<>();

        if (productos == null) {
            return listaDtos;
        }

        for (Producto p : productos) {
            if (p != null) {
                listaDtos.add(entidadADTO(p));
            }
        }

        return listaDtos;
    }
}