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

public class IngredienteDTO {
    private Long id;
    private String identificador;
    private String nombre;
    private UnidadDTO unidadMedida;
    private Integer stock;
    private String urlImagen;

    public IngredienteDTO(Long id, String identificador, String nombre, UnidadDTO unidadMedida, Integer stock, String urlImagen) {
        this.id = id;
        this.identificador = identificador;
        this.nombre = nombre;
        this.unidadMedida = unidadMedida;
        this.stock = stock;
        this.urlImagen = urlImagen;
    }

    public IngredienteDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
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

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    @Override
    public String toString() {
        return identificador;
    }

}
