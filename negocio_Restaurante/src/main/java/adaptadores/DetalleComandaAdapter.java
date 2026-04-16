package adaptadores;

import dtos.DetalleComandaDTO;
import entidades.Comanda;
import entidades.DetalleComanda;
import entidades.Producto;
import excepciones.NegocioException;
import java.util.ArrayList;
import java.util.List;

/**
 * Adaptador encargado de convertir entre la entidad DetalleComanda y su DTO.
 *
 * Esta clase permite transformar objetos de persistencia en objetos DTO y
 * viceversa, utilizando identificadores en lugar de relaciones completas para
 * mantener el desacoplamiento entre capas.
 *
 * @author Brian Kaleb Sandoval Rodríguez
 */
public class DetalleComandaAdapter {

    /**
     * Constructor vacio.
     */
    public DetalleComandaAdapter() {
    }
    
    
    /**
     * Convierte una entidad DetalleComanda a su representación DTO.
     *
     * @param detalle entidad DetalleComanda a convertir
     * @return objeto DetalleComandaDTO equivalente, o null si la entrada es null
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
     * Convierte un objeto DetalleComandaDTO a su entidad correspondiente.
     *
     * @param dto objeto DetalleComandaDTO a convertir
     * @return entidad DetalleComanda equivalente, o null si la entrada es null
     * @throws NegocioException si ocurre un error durante la conversión o validación
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
     * Convierte una lista de entidades DetalleComanda a una lista de DTOs.
     *
     * @param detalles lista de entidades DetalleComanda
     * @return lista de objetos DetalleComandaDTO (nunca null)
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
     * Convierte una lista de DTOs a entidades DetalleComanda.
     *
     * @param dtos lista de objetos DetalleComandaDTO
     * @return lista de entidades DetalleComanda (nunca null)
     * @throws NegocioException si ocurre un error durante la conversión
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