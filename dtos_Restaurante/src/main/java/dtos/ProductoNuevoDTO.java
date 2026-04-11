/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import enumerators.EstadoProductoDTO;
import enumerators.TipoProductoDTO;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DTO para aquellos productos que van a ser registrados por primera vez.
 * 
 * Simplemente solicita aquellos datos necesarios para registrar un producto.
 * 
 * @author María José Valdez Iglesias - 262775
 */
public class ProductoNuevoDTO {
    
    /**
     * Atributo que representa el nombre del producto.
     */
    private String nombre;
    
    /**
     * Atributo que representa el tipo de producto.
     */
    private TipoProductoDTO tipo;
    
    /**
     * Atributo que representa la descripción del producto.
     */
    private String descripcion;
    
    /**
     * Atributo que representa el precio del producto.
     */
    private Double precio;
    
    /**
     * Atributo que representa el estado del producto.
     */
    private EstadoProductoDTO estado;
    
    /**
     * Atributo que representa la ruta de la imagen referencial del producto.
     * 
     * Este atributo puede ser nulo.
     */
    private String urlImagen;
    
    /**
     * Atributo que representa la lista de los ingredientes que requiere el
     * producto.
     * 
     * Contiene el objeto y la cantidad que se ocupa.
     */
    private List<ProductoIngredienteDTO> ingredientes;

    /**
     * Constructor vacío de la clase.
     */
    public ProductoNuevoDTO() {}

    /**
     * Constructor con todos los atributos de la clase. 
     * @param nombre Una cadena de texto para el nombre del producto.
     * @param tipo Un enum TipoProductoDTO para el tipo del producto.
     * @param descripcion Una cadena de texto para la descripción del producto.
     * @param precio Un valor double para el precio del producto.
     * @param estado Un enum EstadoProductoDTO para el estado del producto.
     * @param urlImagen Una cadena de texto para la ruta de la imagen del
     * producto.
     */
    public ProductoNuevoDTO(String nombre, TipoProductoDTO tipo, String descripcion, Double precio, EstadoProductoDTO estado, String urlImagen) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.estado = estado;
        /*
        Se crea vacía para posteriormente establecer el atributo.
        */
        this.ingredientes = new ArrayList<>();
        this.urlImagen = urlImagen;
    }

    /**
     * Método para obtener el nombre del producto.
     * @return El atributo 'nombre'.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método para establecer el nombre del producto.
     * @param nombre Una cadena de texto para el nombre.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Método para obtener el tipo del producto.
     * @return El atributo 'tipo'.
     */
    public TipoProductoDTO getTipo() {
        return tipo;
    }

    /**
     * Método para establecer el tipo del producto.
     * @param tipo Un enum TipoProductoDTO para el tipo.
     */
    public void setTipo(TipoProductoDTO tipo) {
        this.tipo = tipo;
    }

    /**
     * Método para obtener la descripción del producto.
     * @return El atributo 'descripción'.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Método para establecer la descripción del producto.
     * @param descripcion Una cadena de texto para la descripción.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Método para obtener el precio del producto.
     * @return El atributo 'precio'.
     */
    public Double getPrecio() {
        return precio;
    }

    /**
     * Método para establecer el precio del producto.
     * @param precio Un valor double para el precio.
     */
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    /**
     * Método para obtener el estado del producto.
     * @return El atributo 'estado'.
     */
    public EstadoProductoDTO getEstado() {
        return estado;
    }

    /**
     * Método para establecer el estado del producto.
     * @param estado Un enum EstadoProductoDTO para el estado.
     */
    public void setEstado(EstadoProductoDTO estado) {
        this.estado = estado;
    }

    /**
     * Método para obtener la ruta de la imagen del producto.
     * @return El atributo 'urlImagen'.
     */
    public String getUrlImagen() {
        return urlImagen;
    }

    /**
     * Método para establecer la urlImagen del producto.
     * @param urlImagen Una cadena de texto para la urlImagen.
     */
    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }
    
    /**
     * Método para obtener los ingredientes del producto.
     * @return El atributo 'ingredientes'.
     */
    public List<ProductoIngredienteDTO> getIngredientes(){
        return ingredientes;
    }
    
    /**
     * Método para establecer los ingredientes del producto.
     * @param ingredientes Una lista con ProductoIngredienteDTO para los ingredientes.
     */
    public void setIngredientes(List<ProductoIngredienteDTO> ingredientes){
        this.ingredientes = ingredientes;
    }
    
}
