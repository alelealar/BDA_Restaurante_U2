/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package entidades;

import enumerators.Unidad;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


/**
 *
 * @author Alejandra Leal Armenta, 262719
 */

@Entity
@Table(
    name = "ingredientes",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nombre_Ingrediente", "unidad_medida"}),
        @UniqueConstraint(columnNames = {"identificador"})
    }
)
public class Ingrediente implements Serializable {

    /**
     * Identificador único para este registro del ingrediente
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ingrediente")
    private Long id;
    
    /**
     * Identificador del ingrediente. Diferente al id generado
     * 
     * cada indentificador es unico y es obligatorio
     */
    @Column(name = "identificador", nullable = false, length = 6, unique = true)
    private String identificador;
    
    /**
     * Nombre del ingrediente.
     * 
     * La unicidad se define con la unidad de medida, o sea, no se pueden repetir
     * ingredientes nombre-unidadMedida
     */
    @Column(name = "nombre_Ingrediente", nullable = false, length = 100)
    private String nombre;
    
    /**
     * Unidad de medida del ingrediente(Kg, mg, gr, L, etc).
     * 
     * La unicidad se define con el nombre, o sea, no se pueden repetir
     * ingredientes nombre-unidadMedida
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "unidad_medida", nullable = false)
    private Unidad unidadMedida;
    
    /**
     * Cantidad disponible del ingrediente en inventario.
     * 
     * Se mide según su unidad (gramos, piezas, ml, etc.).
     */
    @Column(name = "stockActual", nullable = false)
    private Integer stockActual;
    
    
    @Column(name = "stockMinimo", nullable = false)
    private Integer stockMinimo;
    
    /**
     * Ruta para extraer su imagen.
     * 
     * Es la ubicacion de donde esta la imagen
     */
    @Column(name = "urlImagen", nullable = true)
    private String urlImagen;
    
    /**
     * Relación con los productos donde se utiliza este ingrediente.
     * 
     * Representa las cantidades del ingrediente dentro de cada producto.
     * 
     * "mappedBy = ingrediente" indica que la relación se maneja desde ProductoIngrediente.
     * 
     * Cascade PERSIST y MERGE permite que al guardar o actualizar un ingrediente,
     * también se guarden o actualicen automáticamente sus relaciones con productos.
     */
    @OneToMany(
            mappedBy = "ingrediente",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    private List<ProductoIngrediente> productosAsociados = new ArrayList<>();

    public Ingrediente(Long id, String identificador, String nombre, Unidad unidadMedida, Integer stockActual, Integer stockMinimo, String urlImagen) {
        this.id = id;
        this.identificador = identificador;
        this.nombre = nombre;
        this.unidadMedida = unidadMedida;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
        this.urlImagen = urlImagen;
    }

    public Ingrediente(String identificador, String nombre, Unidad unidadMedida, Integer stockActual, Integer stockMinimo, String urlImagen) {
        this.identificador = identificador;
        this.nombre = nombre;
        this.unidadMedida = unidadMedida;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
        this.urlImagen = urlImagen;
    }

    

    /**
     * Constructor vacio requerido por JPA.
     */
    public Ingrediente() {
    }
    
    /**
     * Devuelve el id generado automaticamente del ingrediente.
     * @return id del registro del ingrediente
     */
    public Long getId() {
        return id;
    }

    /**
     * Asigna un valor Long al id del registro del ingrediente
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Devuelve el identificador del ingrediente.
     * @return identificador del ingrediente
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Asigna el identificador del ingrediente.
     * @param identificador código único del ingrediente
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Devuelve el nombre del ingrediente.
     * @return nombre del ingrediente
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna el nombre del ingrediente.
     * @param nombre nombre del ingrediente
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve la unidad de medida del ingrediente.
     * @return unidad de medida
     */
    public Unidad getUnidadMedida() {
        return unidadMedida;
    }

    /**
     * Asigna la unidad de medida del ingrediente.
     * @param unidadMedida unidad en la que se mide el ingrediente
     */
    public void setUnidadMedida(Unidad unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    /**
     * Devuelve el stock disponible del ingrediente.
     * @return cantidad en inventario
     */
    public Integer getStockActual() {
        return stockActual;
    }

    /**
     * Asigna el stock del ingrediente.
     * @param stock cantidad disponible en inventario
     */
    public void setStockActual(Integer stock) {
        this.stockActual = stock;
    }

    public Integer getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(Integer stockMinimo) {
        this.stockMinimo = stockMinimo;
    }
    
    

    /**
     * Devuelve la URL de la imagen del ingrediente.
     * @return ruta de la imagen
     */
    public String getUrlImagen() {
        return urlImagen;
    }

    /**
     * Asigna la URL de la imagen del ingrediente.
     * @param urlImagen ruta donde se encuentra la imagen
     */
    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    /**
     * Devuelve la lista de productos donde se usa este ingrediente.
     * @return relaciones producto-ingrediente
     */
    public List<ProductoIngrediente> getProductoAsociados() {
        return productosAsociados;
    }

    /**
     * Asigna las relaciones con productos.
     */
    public void setProductosAsociados(List<ProductoIngrediente> productosAsociados) {
        this.productosAsociados = productosAsociados;
    }
    
    
}
