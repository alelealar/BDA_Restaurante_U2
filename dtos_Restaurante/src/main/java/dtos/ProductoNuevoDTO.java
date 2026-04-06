/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import enumerators.EstadoProductoDTO;
import enumerators.TipoProductoDTO;

/**
 *
 * @author María José Valdez Iglesias - 262775
 */
public class ProductoNuevoDTO {
    
    private String nombre;
    
    private TipoProductoDTO tipo;
    
    private String descripcion;
    
    private Double precio;
    
    private EstadoProductoDTO estado;
    
    private String urlImagen;
    
    //private List<ProductoIngredienteDTO> ingredientes;

    public ProductoNuevoDTO() {
    }

    public ProductoNuevoDTO(String nombre, TipoProductoDTO tipo, String descripcion, Double precio, EstadoProductoDTO estado, String urlImagen) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.estado = estado;
        this.urlImagen = urlImagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoProductoDTO getTipo() {
        return tipo;
    }

    public void setTipo(TipoProductoDTO tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public EstadoProductoDTO getEstado() {
        return estado;
    }

    public void setEstado(EstadoProductoDTO estado) {
        this.estado = estado;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }
    
}
