/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

/**
 * Clase DTO para los ProductoIngrediente usados en el atributo ingredientes
 * de ProductoDTO y ProductoNuevoDTO.
 * 
 * Solicita su ID, el IngredienteDTO y la cantidad del ingrediente.
 * 
 * @author Alejandra Leal Armenta - 262719
 * @author María José Valdez Iglesias - 262775
 */
public class ProductoIngredienteDTO {
    
    /**
     * Atributo que representa el ID del objeto.
     */
    private Long id;
    
    /**
     * Atributo que representa el ingrediente de la relación.
     */
    private IngredienteDTO ingrediente;
    
    /**
     * Atributo que representa la cantidad del ingrediente.
     */
    private Integer cantidad;

    /**
     * Constructor vacío de la clase.
     */
    public ProductoIngredienteDTO() {}
    
    /**
     * Constructor con los atributos de la clase menos el ID.
     * @param ingrediente Un IngredienteDTO para el ingrediente del 
     * ProductoIngredienteDTO.
     * @param cantidad Un valor entero para la cantidad del ProductoIngredienteDTO.
     */
    public ProductoIngredienteDTO(IngredienteDTO ingrediente, Integer cantidad){
        this.ingrediente = ingrediente;
        this.cantidad = cantidad;
    }
    
    /**
     * Constructor con todos los atributos de la clase. 
     * @param id Un valor long que representa el ID del ProductoIngredienteDTO.
     * @param ingrediente Un IngredienteDTO para el ingrediente del 
     * ProductoIngredienteDTO.
     * @param cantidad Un valor entero para la cantidad del ProductoIngredienteDTO.
     */
    public ProductoIngredienteDTO(Long id, IngredienteDTO ingrediente, Integer cantidad) {
        this.id = id;
        this.ingrediente = ingrediente;
        this.cantidad = cantidad;
    }

    /**
     * Método para obtener el ID del ProductoIngredienteDTO.
     * @return El atributo 'ID'. 
     */
    public Long getId() {
        return id;
    }

    /**
     * Método para establecer el ID del ProductoIngredienteDTO.
     * @param id Un valor long para el ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Método para obtener el ingrediente del ProductoIngredienteDTO.
     * @return El atributo 'ingrediente'.  
     */
    public IngredienteDTO getIngrediente() {
        return ingrediente;
    }

    /**
     * Método para establecer el ingrediente del ProductoIngredienteDTO.
     * @param ingrediente Un IngredienteDTO para el ingrediente.
     */
    public void setIngrediente(IngredienteDTO ingrediente) {
        this.ingrediente = ingrediente;
    }

    /**
     * Método para obtener la cantidad del ProductoIngredienteDTO.
     * @return El atributo 'cantidad'. 
     */
    public Integer getCantidad() {
        return cantidad;
    }

    /**
     * Método para establecer la cantidad del ProductoIngredienteDTO.
     * @param cantidad Un valor entero para la cantidad.
     */
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    
    /**
     * Método toString para regresar la información del ProductoIngredienteDTO.
     * @return Cadena de texto con la información.
     */
    @Override
    public String toString(){
        return ingrediente.getNombre() + " x" + cantidad + " " + ingrediente.getUnidadMedida();
    }
}
