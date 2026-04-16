package adaptadores;

import dtos.MesaDTO;
import entidades.Mesa;
import enumerators.EstadoMesa;
import enumerators.EstadoMesaDTO;
import java.util.ArrayList;
import java.util.List;

/**
 * Adaptador encargado de convertir entre la entidad Mesa y su DTO MesaDTO.
 *
 * Permite transformar objetos entre la capa de persistencia y la capa de
 * presentación o negocio, evitando el acoplamiento directo con entidades.
 *
 * Incluye métodos para conversiones individuales y de listas, asegurando la
 * consistencia entre entidades y DTOs.
 *
 * @author Brian Kaleb Sandoval Rodríguez - 00000262741
 */
public class MesaAdapter {

    /**
     * Constructor vacio.
     */
    public MesaAdapter() {
    }

    /**
     * Convierte una entidad Mesa a su representación MesaDTO.
     *
     * @param mesa entidad Mesa a convertir
     * @return objeto MesaDTO equivalente o null si la entrada es null
     */
    public static MesaDTO entidadADTO(Mesa mesa) {
        if (mesa == null) {
            return null;
        }

        return new MesaDTO(
                mesa.getId(),
                mesa.getNumero(),
                mesa.getEstado() != null
                        ? EstadoMesaDTO.valueOf(mesa.getEstado().name())
                        : null
        );
    }

    /**
     * Convierte un objeto MesaDTO a su entidad Mesa correspondiente.
     *
     * @param dto objeto MesaDTO a convertir
     * @return entidad Mesa equivalente o null si la entrada es null
     */
    public static Mesa dtoAEntidad(MesaDTO dto) {
        if (dto == null) {
            return null;
        }

        return new Mesa(
                dto.getId(),
                dto.getNumero(),
                dto.getEstado() != null
                        ? EstadoMesa.valueOf(dto.getEstado().name())
                        : null
        );
    }

    /**
     * Convierte una lista de entidades Mesa a una lista de MesaDTO.
     *
     * @param mesas lista de entidades Mesa
     * @return lista de objetos MesaDTO (nunca null)
     */
    public static List<MesaDTO> listaEntidadADTO(List<Mesa> mesas) {
        List<MesaDTO> lista = new ArrayList<>();

        if (mesas == null) {
            return lista;
        }

        for (Mesa m : mesas) {
            if (m != null) {
                lista.add(entidadADTO(m));
            }
        }

        return lista;
    }

    /**
     * Convierte una lista de DTOs MesaDTO a entidades Mesa.
     *
     * @param dtos lista de objetos MesaDTO
     * @return lista de entidades Mesa (nunca null)
     */
    public static List<Mesa> listaDTOAEntidad(List<MesaDTO> dtos) {
        List<Mesa> lista = new ArrayList<>();

        if (dtos == null) {
            return lista;
        }

        for (MesaDTO d : dtos) {
            if (d != null) {
                lista.add(dtoAEntidad(d));
            }
        }

        return lista;
    }
}