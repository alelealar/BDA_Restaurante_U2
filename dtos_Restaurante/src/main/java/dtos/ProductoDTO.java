/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import enumerators.EstadoProductoDTO;
import enumerators.TipoProductoDTO;
import java.util.List;

/**
 *
 * @author María José Valdez Iglesias - 262775
 */
public class ProductoDTO {
    
    private Long id;
    
    private String identificador;
    
    private String nombre;
    
    private TipoProductoDTO tipo;
    
    private String descripcion;
    
    private Double precio;
    
    private EstadoProductoDTO estado;
    
    private String disponibilidad;
    
    private List<ProductoIngredienteDTO> ingredientes;
    
    private String urlImagen;

    public ProductoDTO() {}

    public ProductoDTO(Long id, String identificador, String nombre, TipoProductoDTO tipo, String descripcion, Double precio, EstadoProductoDTO estado, String disponibilidad, List<ProductoIngredienteDTO> ingredientes, String urlImagen) {
        this.id = id;
        this.identificador = identificador;
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.estado = estado;
        this.disponibilidad = disponibilidad;
        this.ingredientes = ingredientes;
        this.urlImagen = urlImagen;
    }

    public ProductoDTO(Long id, String identificador, String nombre, TipoProductoDTO tipo, String descripcion, Double precio, EstadoProductoDTO estado, String disponibilidad, String urlImagen) {
        this.id = id;
        this.identificador = identificador;
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.estado = estado;
        this.disponibilidad = disponibilidad;
        this.urlImagen = urlImagen;
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

    public String getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(String disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public List<ProductoIngredienteDTO> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<ProductoIngredienteDTO> ingredientes) {
        this.ingredientes = ingredientes;
    }
    
    @Override
    public String toString(){
        return this.identificador;
    }
}
