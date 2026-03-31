/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 *
 * @author Alejandra Leal Armenta, 262719
 * @author Maria Jose Valdez Iglesias - 262775
 */

@Entity
@Table(name = "productos_ingredientes")
public class ProductoIngrediente implements Serializable{

    /**
     * Identificador único para este registro del productoIngrediente(receta)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto_ingrediente")
    private Long id;
    
    /**
     * Cantidad del ingrediente para el producto.
     * 
     * Es la cantidad de un ingrediente que necesita cada producto. 
     * Por ejemplo: Una hamburguesa necesita 2 del ingrediente tomate
     */
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;
    
    /**
     * Relación muchos a uno con Ingrediente.
     * 
     * Muchos registros de ProductoIngrediente pueden estar asociados al mismo ingrediente.
     * Cada registro representa la cantidad de ingrediente usada en un producto o receta.
     */
    @ManyToOne
    @JoinColumn(name = "id_ingrediente", nullable = false)
    private Ingrediente ingrediente;
    
    //Ale: majojojo aqui le pones tu mapeo

    //Ale: majojojo actualizas en constructor cuando le pongas tu mapeo
    
    /**
     * Constructor de ProductoIngrediente.
     * Crea una relación entre un producto y un ingrediente con su cantidad correspondiente.
     * 
     * @param id
     * @param cantidad
     * @param ingrediente 
     */
    public ProductoIngrediente(Long id, Integer cantidad, Ingrediente ingrediente) {
        this.id = id;
        this.cantidad = cantidad;
        this.ingrediente = ingrediente;
    }

    /**
     * Constructor vacio requerido por JPA.
     */
    public ProductoIngrediente() {
    }
    
    /** 
     * Devuelve el id del registro ProductoIngrediente.
     * @return id del registro
     */
    public Long getId() {
        return id;
    }

    /**
     * Asigna el id del registro ProductoIngrediente.
     * @param id identificador del registro
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Devuelve la cantidad de ingrediente usada en el producto.
     * @return cantidad del ingrediente
     */
    public Integer getCantidad() {
        return cantidad;
    }

    /**
     * Asigna la cantidad de ingrediente.
     * @param cantidad cantidad utilizada en la receta o producto
     */
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Devuelve el ingrediente asociado a esta relación.
     * @return ingrediente relacionado
     */
    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    /**
     * Asigna el ingrediente a la relación ProductoIngrediente.
     * @param ingrediente ingrediente que se relaciona con el producto
     */
    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    } 
    
}
