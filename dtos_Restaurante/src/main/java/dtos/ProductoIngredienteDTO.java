/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

/**
 *
 * @author Alejandra Leal Armenta - 262719
 * @author María José Valdez Iglesias - 262775
 */
public class ProductoIngredienteDTO {
    
    private Long id;
    private IngredienteDTO ingrediente;
    private Integer cantidad;

    public ProductoIngredienteDTO() {}
    
    public ProductoIngredienteDTO(IngredienteDTO ingrediente, Integer cantidad){
        this.ingrediente = ingrediente;
        this.cantidad = cantidad;
    }
    
    public ProductoIngredienteDTO(Long id, IngredienteDTO ingrediente, Integer cantidad) {
        this.id = id;
        this.ingrediente = ingrediente;
        this.cantidad = cantidad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IngredienteDTO getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(IngredienteDTO ingrediente) {
        this.ingrediente = ingrediente;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    
    @Override
    public String toString(){
        return ingrediente.getNombre() + " x" + cantidad + " " + ingrediente.getUnidadMedida();
    }
}
