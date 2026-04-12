/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package dtos;

import enumerators.UnidadDTO;


/**
 *
 * @author Alejandra Leal Armenta, 262719
 */

public class IngredienteNuevoDTO {
    private String nombre;
    private UnidadDTO unidadMedida;
    private Integer stockInicial;
    private Integer stockMinimo;
    private String urlImagen;

    public IngredienteNuevoDTO(String nombre, UnidadDTO unidadMedida, Integer stockInicial, Integer stockMinimo, String urlImagen) {
        this.nombre = nombre;
        this.unidadMedida = unidadMedida;
        this.stockInicial = stockInicial;
        this.stockMinimo = stockMinimo;
        this.urlImagen = urlImagen;
    }


    public IngredienteNuevoDTO() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public UnidadDTO getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(UnidadDTO unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public Integer getStockInicial() {
        return stockInicial;
    }

    public void setStockInicial(Integer stockInicial) {
        this.stockInicial = stockInicial;
    }

    public Integer getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(Integer stockMinimo) {
        this.stockMinimo = stockMinimo;
    }
    
    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }
    
    
}
