package adaptadores;

import dtos.MesaDTO;
import entidades.Mesa;
import enumerators.EstadoMesa;
import enumerators.EstadoMesaDTO;
import java.util.ArrayList;
import java.util.List;

/**
 * Adaptador para convertir entre la entidad Mesa y su DTO MesaDTO.
 *
 * Permite transformar objetos entre la capa de persistencia y la capa de
 * presentación o negocio, evitando el acoplamiento directo con entidades.
 *
 * Incluye métodos para conversiones individuales y de listas.
 *
 * @author Brian Kaleb Sandoval Rodríguez - 00000262741
 */
public class MesaAdapter {

    /**
     * Convierte una entidad Mesa a un DTO MesaDTO.
     *
     * @param mesa Entidad Mesa.
     * @return MesaDTO equivalente o null si la entrada es null.
     */
    public static MesaDTO entidadADTO(Mesa mesa) {
        if (mesa == null) {
            return null;
        }

        return new MesaDTO(
                mesa.getId(),
                mesa.getNumero(),
                EstadoMesaDTO.valueOf(mesa.getEstado().name())
        );
    }

    /**
     * Convierte un DTO MesaDTO a una entidad Mesa.
     *
     * @param dto DTO de Mesa.
     * @return Entidad Mesa equivalente o null si la entrada es null.
     */
    public static Mesa dtoAEntidad(MesaDTO dto) {
        if (dto == null) {
            return null;
        }

        return new Mesa(
                dto.getId(),
                dto.getNumero(),
                EstadoMesa.valueOf(dto.getEstado().name())
        );
    }

    /**
     * Convierte una lista de entidades Mesa a una lista de DTOs.
     *
     * @param mesas Lista de entidades.
     * @return Lista de DTOs.
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
     * Convierte una lista de DTOs a una lista de entidades.
     *
     * @param dtos Lista de DTOs.
     * @return Lista de entidades.
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
