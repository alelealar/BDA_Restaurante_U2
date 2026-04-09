package adaptadores;

import dtos.ComandaDTO;
import entidades.Comanda;
import entidades.DetalleComanda;
import enumerators.EstadoComanda;
import enumerators.EstadoComandaDTO;
import excepciones.NegocioException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase adaptadora encargada de convertir entre la entidad Comanda y su DTO
 * correspondiente.
 *
 * Proporciona métodos para transformar objetos individuales y listas completas,
 * asegurando la correcta conversión de atributos y relaciones.
 *
 * También gestiona la conversión de enumeradores y la relación bidireccional
 * entre Comanda y DetalleComanda.
 *
 * @author Brian Kaleb Sandoval Rodríguez - 00000262741
 */
public class ComandaAdapter {

    /**
     * Convierte una entidad Comanda a su representación DTO.
     *
     * @param comanda Entidad Comanda a convertir.
     * @return Objeto ComandaDTO equivalente o null si la entrada es null.
     */
    public static ComandaDTO entidadADTO(Comanda comanda) {
        if (comanda == null) {
            return null;
        }
        
        return new ComandaDTO(
                comanda.getId(),
                comanda.getEstadoComanda() != null ? EstadoComandaDTO.valueOf(comanda.getEstadoComanda().name()) : null,
                comanda.getFolio(),
                comanda.getTotal(),
                comanda.getFechaHora(),
                DetalleComandaAdapter.listaEntidadADTO(comanda.getDetalles()),
                MesaAdapter.entidadADTO(comanda.getMesa()),
                ClienteAdapter.entidadADTO(comanda.getCliente())
        );
    }

    /**
     * Convierte un objeto ComandaDTO a su entidad correspondiente.
     *
     * Este método también asegura la correcta asignación de la relación
     * bidireccional entre Comanda y DetalleComanda.
     *
     * @param dto Objeto ComandaDTO a convertir.
     * @return Entidad Comanda equivalente o null si la entrada es null.
     * @throws NegocioException Si ocurre un error en la conversión.
     */
    public static Comanda dtoAEntidad(ComandaDTO dto) throws NegocioException {
        if (dto == null) {
            return null;
        }
        
        Comanda comanda = new Comanda(
                dto.getId(),
                dto.getEstadoComanda() != null
                ? EstadoComanda.valueOf(dto.getEstadoComanda().name())
                : null,
                dto.getFolio(),
                dto.getTotal(),
                dto.getFechaHora(),
                dto.getMesa() != null ? MesaAdapter.dtoAEntidad(dto.getMesa()) : null,
                DetalleComandaAdapter.listaDTOAEntidad(dto.getDetalles()),
                ClienteAdapter.dtoAEntidad(dto.getCliente())
        );

        // 🔥 mantener consistencia bidireccional
        if (comanda.getDetalles() != null) {
            for (DetalleComanda d : comanda.getDetalles()) {
                d.setComanda(comanda);
            }
        }
        
        return comanda;
    }

    /**
     * Convierte una lista de entidades Comanda a una lista de DTOs.
     *
     * Ignora elementos nulos dentro de la lista.
     *
     * @param comandas Lista de entidades Comanda.
     * @return Lista de objetos ComandaDTO.
     */
    public static List<ComandaDTO> listaEntidadADTO(List<Comanda> comandas) {
        List<ComandaDTO> listaDtos = new ArrayList<>();
        
        if (comandas == null) {
            return listaDtos;
        }
        
        for (Comanda c : comandas) {
            if (c != null) {
                listaDtos.add(entidadADTO(c));
            }
        }
        
        return listaDtos;
    }
}
