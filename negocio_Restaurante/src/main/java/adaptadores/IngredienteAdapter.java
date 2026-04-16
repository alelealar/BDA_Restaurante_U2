package adaptadores;

import dtos.IngredienteDTO;
import dtos.IngredienteNuevoDTO;
import entidades.Ingrediente;
import enumerators.Unidad;
import enumerators.UnidadDTO;
import java.util.ArrayList;
import java.util.List;

/**
 * Adaptador encargado de convertir entre la entidad Ingrediente y sus
 * diferentes DTOs.
 *
 * Esta clase centraliza las transformaciones entre capas del sistema,
 * asegurando el correcto mapeo de atributos y la conversión de enumeradores
 * entre entidad y DTO.
 *
 * @author Alejandra Leal Armenta, 262719
 */
public class IngredienteAdapter {

    /**
     * Constructor vacio.
     */
    public IngredienteAdapter() {
    }

    /**
     * Convierte una entidad Ingrediente a su representación IngredienteDTO.
     *
     * @param ingrediente entidad Ingrediente a convertir
     * @return objeto IngredienteDTO equivalente o null si la entrada es null
     */
    public static IngredienteDTO entidadADTO(Ingrediente ingrediente) {
        if (ingrediente == null) {
            return null;
        }

        return new IngredienteDTO(
                ingrediente.getId(),
                ingrediente.getIdentificador(),
                ingrediente.getNombre(),
                UnidadDTO.valueOf(ingrediente.getUnidadMedida().name()),
                ingrediente.getStockActual(),
                ingrediente.getStockMinimo(),
                ingrediente.getUrlImagen()
        );
    }

    /**
     * Convierte un IngredienteNuevoDTO a entidad Ingrediente.
     *
     * Se utiliza para la creación de nuevos registros en el sistema.
     *
     * @param i objeto IngredienteNuevoDTO a convertir
     * @return entidad Ingrediente equivalente o null si la entrada es null
     */
    public static Ingrediente nuevoDTOAEntidad(IngredienteNuevoDTO i) {
        if (i == null) {
            return null;
        }

        return new Ingrediente(
                null,
                null,
                i.getNombre(),
                Unidad.valueOf(i.getUnidadMedida().name()),
                i.getStockInicial(),
                i.getStockMinimo(),
                i.getUrlImagen()
        );
    }

    /**
     * Convierte un IngredienteDTO a entidad Ingrediente.
     *
     * Se utiliza principalmente para operaciones de actualización.
     *
     * @param i objeto IngredienteDTO a convertir
     * @return entidad Ingrediente equivalente o null si la entrada es null
     */
    public static Ingrediente dtoAEntidad(IngredienteDTO i) {
        if (i == null) {
            return null;
        }

        return new Ingrediente(
                i.getId(),
                i.getIdentificador(),
                i.getNombre(),
                Unidad.valueOf(i.getUnidadMedida().name()),
                i.getStockActual(),
                i.getStockMinimo(),
                i.getUrlImagen()
        );
    }

    /**
     * Convierte una lista de entidades Ingrediente a una lista de IngredienteDTO.
     *
     * Este método ignora valores nulos dentro de la lista para evitar errores
     * durante la transformación.
     *
     * @param ingredientes lista de entidades Ingrediente
     * @return lista de objetos IngredienteDTO (nunca null)
     */
    public static List<IngredienteDTO> listaEntidadADTO(List<Ingrediente> ingredientes) {
        List<IngredienteDTO> listaDtos = new ArrayList<>();

        if (ingredientes == null) {
            return listaDtos;
        }

        for (Ingrediente i : ingredientes) {
            if (i != null) {
                listaDtos.add(entidadADTO(i));
            }
        }

        return listaDtos;
    }
}