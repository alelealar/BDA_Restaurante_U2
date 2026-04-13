package adaptadores;

import dtos.IngredienteDTO;
import dtos.IngredienteNuevoDTO;
import dtos.IngredienteStockDTO;
import entidades.Ingrediente;
import enumerators.Unidad;
import enumerators.UnidadDTO;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de convertir entre la entidad Ingrediente
 * y sus diferentes DTOs.
 * 
 * Centraliza las transformaciones entre capas del sistema.
 * 
 * @author Alejandra Leal Armenta, 262719
 * 
 */

public class IngredienteAdapter {

    /**
     * Convierte una entidad Ingrediente a IngredienteDTO.
     * 
     * @param ingrediente entidad a convertir
     * @return DTO con los datos del ingrediente o null si es null
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
     * @param i DTO de creación
     * @return entidad nueva o null si el DTO es null
     */
    public static Ingrediente nuevoDTOAEntidad(IngredienteNuevoDTO i) {
        if (i == null) return null;

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
     * @param i DTO a convertir
     * @return entidad con los datos del DTO o null si es null
     */
    public static Ingrediente dtoAEntidad(IngredienteDTO i) {
        if (i == null) return null;

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
     * Convierte una lista de entidades a lista de DTOs.
     * 
     * @param ingredientes lista de entidades
     * @return lista de DTOs (vacía si no hay elementos)
     */
    public static List<IngredienteDTO> listaEntidadADTO(List<Ingrediente> ingredientes) {
        List<IngredienteDTO> listaDtos = new ArrayList<>();

        for (Ingrediente i : ingredientes) {
            listaDtos.add(entidadADTO(i));
        }

        return listaDtos;
    }
}