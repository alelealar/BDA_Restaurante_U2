/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

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
 *
 * @author Alejandra Leal Armenta, 262719
 */

public class IngredienteAdapter {
    public static IngredienteDTO entidadADTO(Ingrediente ingrediente) {
        if (ingrediente == null) {
            return null;
        }

        return new IngredienteDTO(
                ingrediente.getId(),
                ingrediente.getIdentificador(),
                ingrediente.getNombre(),
                UnidadDTO.valueOf(ingrediente.getUnidadMedida().name()),
                ingrediente.getStock(),
                ingrediente.getUrlImagen()
        );
    }
    
    
    public static Ingrediente nuevoDTOAEntidad(IngredienteNuevoDTO i) {
        if (i == null) return null;

        return new Ingrediente(
                null,
                null,
                i.getNombre(),
                Unidad.valueOf(i.getUnidadMedida().name()),
                i.getStockInicial(),
                i.getUrlImagen()
        );
    }
    
        
    public static Ingrediente dtoAEntidad(IngredienteDTO i) {
        if (i == null) return null;

        return new Ingrediente(
                i.getId(),
                i.getIdentificador(),
                i.getNombre(),
                Unidad.valueOf(i.getUnidadMedida().name()),
                i.getStock(),
                i.getUrlImagen()
        );
    }

    public static List<IngredienteDTO> listaEntidadADTO(List<Ingrediente> ingredientes) {
        List<IngredienteDTO> listaDtos = new ArrayList<>();

        for (Ingrediente i : ingredientes) {
            listaDtos.add(entidadADTO(i));
        }

        return listaDtos;
    }
}


