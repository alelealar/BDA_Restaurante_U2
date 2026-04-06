/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import enumerators.DisponibilidadProducto;
import enumerators.EstadoProducto;
import enumerators.TipoProducto;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Clase de la entidad Producto.
 * @author María José Valdez Iglesias - 262775
 */
@Entity
@Table(
    name = "productos",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nombre_producto"}),
        @UniqueConstraint(columnNames = {"identificador_producto"})
    }
)
public class Producto implements Serializable {
    
    /**
     * Identificador único para cada registro de la tabla.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long id;
    
    /**
     * Un identificador único para cada producto y para que pueda ser desplegado
     * en la interfaz gráfica del sistema.
     */
    @Column(name = "identificador_producto", nullable=false)
    private String identificador;
    
    /**
     * Nombre del producto, este debe ser único para cada registro.
     */
    @Column(name = "nombre_producto", nullable=false)
    private String nombre;
    
    /**
     * Tipo del producto (postre, bebida, platillo)
     */
    @Column(name = "tipo_producto", nullable=false)
    private TipoProducto tipo;
    
    /**
     * Descripción del platillo obligatoria.
     */
    @Column(name = "descripcion", nullable=false, length=150)
    private String descripcion;
    
    /**
     * Costo del platillo.
     * 
     * No es calculado y se determina de forma manual.
     */
    @Column(name="precio", nullable=false)
    private Double precio;
    
    /**
     * Estado actual del platillo en el menú.
     * 
     * Solo tiene dos estados: activo o inactivo.
     */
    @Column(name="estado", nullable=false)
    private EstadoProducto estado;
    
    /**
     * Estado de disponibilidad de un producto, si se cuenta con lo ingredientes
     * suficientes para realizarlo o no.
     * 
     * Solo tiene dos tipos de disponibildad: disponible y no-disponible.
     */
    @Column(name="disponibilidad", nullable=false)
    private DisponibilidadProducto disponibilidad;
    
    /**
     * Ubicación de la imagen referencial del producto para su extracción.
     * 
     * No es obligatoria de tener.
     */
    @Column(name="urlImagen", nullable=true)
    private String urlImagen;
    
    /**
     * Relación con los ingredientes que se relacionan con este producto.
     * 
     * Es una representación de la lista de ingredientes que ocupa el producto 
     * para su elaboración.
     * 
     * "mappedBy = producto" indica que la relación se maneja desde ProductoIngrediente.
     * 
     * Cascade PERSIST y MERGE permite que al guardar o actualizar un producto,
     * también se guarden o actualicen automáticamente sus relaciones con ingredientes.
     */
    @OneToMany (
            mappedBy = "producto",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    private List<ProductoIngrediente> productos;
    
    /**
     * Constructor vacío por default, requerido por JPA.
     */
    public Producto(){}

    /**
     * Constructor con los atributos de la entidad excepto la lista de ingredientes.
     */
    public Producto(Long id, String identificador, String nombre, TipoProducto tipo, String descripcion, Double precio, EstadoProducto estado, DisponibilidadProducto disponibilidad, String urlImagen) {
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
    
    /**
     * Constructor con todos los atributos de la entidad.
     */
    public Producto(Long id, String identificador, String nombre, TipoProducto tipo, String descripcion, Double precio, EstadoProducto estado, DisponibilidadProducto disponibilidad, String urlImagen, List<ProductoIngrediente> productos) {
        this.id = id;
        this.identificador = identificador;
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.estado = estado;
        this.disponibilidad = disponibilidad;
        this.urlImagen = urlImagen;
        this.productos = productos;
    }

    /**
     * Regresa el ID del producto.
     * @return id del producto.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el ID del producto.
     * @param id id a establecer al producto.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Regresa el identificador del producto.
     * @return identificador del producto.
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Establece el identificador del producto.
     * @param identificador identificador a establecer al producto.
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Regresa el nombre del producto.
     * @return nombre del producto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del producto.
     * @param nombre nombre a establecer al producto.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Regresa el tipo del producto.
     * @return tipo del producto.
     */
    public TipoProducto getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo del producto.
     * @param tipo tipo a establecer al producto.
     */
    public void setTipo(TipoProducto tipo) {
        this.tipo = tipo;
    }

    /**
     * Regresa la descripción del producto.
     * @return descripción del producto.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del producto.
     * @param descripcion descripción a establecer al producto.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Regresa el precio del producto.
     * @return precio del producto.
     */
    public Double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio del producto.
     * @param precio precio a establecer al producto.
     */
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    /**
     * Regresa el estado del producto.
     * @return estado del producto.
     */
    public EstadoProducto getEstado() {
        return estado;
    }

    /**
     * Establece el estado del producto.
     * @param estado estado a establecer al producto.
     */
    public void setEstado(EstadoProducto estado) {
        this.estado = estado;
    }

    /**
     * Regresa la disponibilidad del producto.
     * @return disponibilidad del producto.
     */
    public DisponibilidadProducto getDisponibilidad() {
        return disponibilidad;
    }

    /**
     * Establece la disponibilidad del producto.
     * @param disponibilidad disponibilidad a establecer al producto.
     */
    public void setDisponibilidad(DisponibilidadProducto disponibilidad) {
        this.disponibilidad = disponibilidad;
    }
    
    /**
     * Regresa la dirección de la imagen referencial del producto.
     * @return dirección de la imagen referencial del producto.
     */
    public String getUrlImagen() {
        return urlImagen;
    }

    /**
     * Establece la ruta a la imagen referencial del producto.
     * @param urlImagen ruta a establecer a la imagen del producto.
     */
    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    /**
     * Regresa la lista con los ingredientes relacionados al producto.
     * @return lista con los ingredientes que están relacionados al producto.
     */
    public List<ProductoIngrediente> getProductos() {
        return productos;
    }

    /**
     * Establece la lista de ingredientes relacionados al producto.
     * @param productos lista de ingredientes a establecer al producto.
     */
    public void setProductos(List<ProductoIngrediente> productos) {
        this.productos = productos;
    }
   
}
