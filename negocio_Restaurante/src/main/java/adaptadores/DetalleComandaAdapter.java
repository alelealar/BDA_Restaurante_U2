package adaptadores;

import dtos.DetalleComandaDTO;
import entidades.Comanda;
import entidades.DetalleComanda;
import entidades.Producto;
import excepciones.NegocioException;
import java.util.ArrayList;
import java.util.List;

/**
 * Adaptador para convertir entre DetalleComanda y DetalleComandaDTO.
 *
 * Maneja la transformación entre entidades y DTOs utilizando identificadores en
 * lugar de relaciones completas.
 *
 * @author Brian Kaleb Sandoval Rodríguez
 */
public class DetalleComandaAdapter {

    /**
     * Convierte una entidad a DTO.
     */
    public static DetalleComandaDTO entidadADTO(DetalleComanda detalle) {
        if (detalle == null) {
            return null;
        }

        return new DetalleComandaDTO(
                detalle.getId(),
                detalle.getComanda() != null ? detalle.getComanda().getId() : null,
                detalle.getProducto() != null ? detalle.getProducto().getId() : null,
                detalle.getCantidad(),
                detalle.getPrecioUnitario(),
                detalle.getComentario(),
                detalle.getNombreCliente()
        );
    }

    /**
     * Convierte un DTO a entidad.
     */
    public static DetalleComanda dtoAEntidad(DetalleComandaDTO dto) throws NegocioException {
        if (dto == null) {
            return null;
        }

        Comanda comanda = null;
        if (dto.getIdComanda() != null) {
            comanda = new Comanda(dto.getIdComanda());
        }

        Producto producto = null;
        if (dto.getIdProducto() != null) {
            producto = new Producto();
            producto.setId(dto.getIdProducto());
        }

        return new DetalleComanda(
                dto.getId(),
                comanda,
                producto,
                dto.getCantidad(),
                dto.getPrecioUnitario(),
                dto.getComentario(),
                dto.getNombreCliente()
        );
    }

    /**
     * Convierte lista de entidades a DTOs.
     */
    public static List<DetalleComandaDTO> listaEntidadADTO(List<DetalleComanda> detalles) {
        List<DetalleComandaDTO> lista = new ArrayList<>();

        if (detalles == null) {
            return lista;
        }

        for (DetalleComanda d : detalles) {
            if (d != null) {
                lista.add(entidadADTO(d));
            }
        }

        return lista;
    }

    /**
     * Convierte lista de DTOs a entidades.
     */
    public static List<DetalleComanda> listaDTOAEntidad(List<DetalleComandaDTO> dtos) throws NegocioException {
        List<DetalleComanda> lista = new ArrayList<>();

        if (dtos == null) {
            return lista;
        }

        for (DetalleComandaDTO d : dtos) {
            if (d != null) {
                lista.add(dtoAEntidad(d));
            }
        }

        return lista;
    }
}
