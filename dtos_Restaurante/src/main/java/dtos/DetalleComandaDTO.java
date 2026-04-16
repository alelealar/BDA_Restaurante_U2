package dtos;

/**
 * DTO que representa un detalle de comanda.
 *
 * Contiene la información de un producto solicitado dentro de una comanda,
 * incluyendo cantidad, precio, comentarios y el nombre del cliente.
 *
 * Utiliza identificadores en lugar de referencias completas para evitar ciclos
 * de serialización y reducir el acoplamiento.
 *
 * @author Brian Kaleb Sandoval Rodríguez - 00000262741
 */
public class DetalleComandaDTO {

    /**
     * Identificador único del detalle de la comanda.
     */
    private Long id;

    /**
     * Identificador de la comanda a la que pertenece.
     */
    private Long idComanda;

    /**
     * Identificador del producto asociado.
     */
    private Long idProducto;

    /**
     * Cantidad de productos solicitados.
     */
    private int cantidad;

    /**
     * Precio unitario del producto al momento de la compra.
     */
    private double precioUnitario;

    /**
     * Comentario adicional del cliente.
     */
    private String comentario;

    /**
     * Nombre del cliente asociado al detalle.
     */
    private String nombreCliente;

    public DetalleComandaDTO() {
    }

    public DetalleComandaDTO(Long id) {
        this.id = id;
    }

    public DetalleComandaDTO(Long id, Long idComanda, Long idProducto,
            int cantidad, double precioUnitario,
            String comentario, String nombreCliente) {

        this.id = id;
        this.idComanda = idComanda;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.comentario = comentario;
        this.nombreCliente = nombreCliente;
    }

    /**
     * @return identificador del detalle
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id nuevo identificador
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return id de la comanda
     */
    public Long getIdComanda() {
        return idComanda;
    }

    /**
     * @param idComanda identificador de la comanda
     */
    public void setIdComanda(Long idComanda) {
        this.idComanda = idComanda;
    }

    /**
     * @return id del producto
     */
    public Long getIdProducto() {
        return idProducto;
    }

    /**
     * @param idProducto identificador del producto
     */
    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * @return cantidad
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad nueva cantidad
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return precio unitario
     */
    public double getPrecioUnitario() {
        return precioUnitario;
    }

    /**
     * @param precioUnitario nuevo precio
     */
    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    /**
     * @return comentario
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * @param comentario nuevo comentario
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    /**
     * @return nombre del cliente
     */
    public String getNombreCliente() {
        return nombreCliente;
    }

    /**
     * @param nombreCliente nuevo nombre
     */
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }
    
    @Override
    public String toString(){
        return this.idProducto + " x" + this.cantidad + ", Comentario: '" + this.comentario + "'."; 
    }
}
