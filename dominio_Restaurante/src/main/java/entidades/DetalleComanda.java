package entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Representa un detalle dentro de una comanda.
 *
 * Cada detalle corresponde a un producto específico solicitado por el cliente,
 * incluyendo la cantidad, el precio unitario aplicado al momento de la compra y
 * un posible comentario con indicaciones especiales.
 *
 * Esta entidad mantiene una relación ManyToOne con la entidad Comanda,
 * indicando a qué comanda pertenece el detalle, y también con Producto,
 * permitiendo identificar el producto vendido.
 *
 * El precio unitario se almacena de manera independiente del producto para
 * conservar el valor histórico al momento de la venta.
 *
 * El atributo nombreCliente es temporal y no se persiste en la base de datos.
 *
 * @author Brian Kaleb Sandoval Rodríguez - 00000262741
 */
@Entity
@Table(name = "detalle_comanda")
public class DetalleComanda {

    /**
     * Identificador único del detalle de la comanda.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_comanda")
    private Long id;

    /**
     * Comanda a la que pertenece este detalle.
     */
    @ManyToOne
    @JoinColumn(name = "id_comanda", nullable = false)
    private Comanda comanda;

    /**
     * Producto asociado al detalle de la comanda.
     */
    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    /**
     * Cantidad de productos solicitados.
     */
    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    /**
     * Precio unitario del producto al momento de la compra.
     */
    @Column(name = "precio_unitario", nullable = false)
    private double precioUnitario;

    /**
     * Comentario adicional del cliente (ej. sin cebolla, término medio).
     */
    @Column(name = "comentario", length = 200)
    private String comentario;

    /**
     * Nombre del cliente asociado al detalle (no persistente).
     */
    @Transient
    private String nombreCliente;

    /**
     * Constructor vacío requerido por JPA.
     */
    public DetalleComanda() {
    }

    /**
     * Constructor que inicializa el detalle con un identificador.
     *
     * @param id Identificador del detalle.
     */
    public DetalleComanda(Long id) {
        this.id = id;
    }

    /**
     * Constructor completo del detalle de comanda.
     *
     * @param id Identificador del detalle.
     * @param comanda Comanda asociada.
     * @param producto Producto asociado.
     * @param cantidad Cantidad solicitada.
     * @param precioUnitario Precio unitario aplicado.
     * @param comentario Comentario adicional.
     * @param nombreCliente Nombre del cliente (temporal).
     */
    public DetalleComanda(Long id, Comanda comanda, Producto producto,
            int cantidad, double precioUnitario, String comentario, String nombreCliente) {
        this.id = id;
        this.comanda = comanda;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.comentario = comentario;
        this.nombreCliente = nombreCliente;
    }

    /**
     * Obtiene el identificador del detalle.
     *
     * @return id del detalle.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador del detalle.
     *
     * @param id Nuevo identificador.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene la comanda asociada.
     *
     * @return comanda.
     */
    public Comanda getComanda() {
        return comanda;
    }

    /**
     * Establece la comanda asociada.
     *
     * @param comanda Nueva comanda.
     */
    public void setComanda(Comanda comanda) {
        this.comanda = comanda;
    }

    /**
     * Obtiene el producto asociado.
     *
     * @return producto.
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * Establece el producto asociado.
     *
     * @param producto Producto.
     */
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    /**
     * Obtiene la cantidad de productos.
     *
     * @return cantidad.
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad de productos.
     *
     * @param cantidad Nueva cantidad.
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el precio unitario del producto.
     *
     * @return precio unitario.
     */
    public double getPrecioUnitario() {
        return precioUnitario;
    }

    /**
     * Establece el precio unitario del producto.
     *
     * @param precioUnitario Nuevo precio.
     */
    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    /**
     * Obtiene el comentario asociado al producto.
     *
     * @return comentario.
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * Establece un comentario para el producto.
     *
     * @param comentario Nuevo comentario.
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    /**
     * Obtiene el nombre del cliente (no persistente).
     *
     * @return nombre del cliente.
     */
    public String getNombreCliente() {
        return nombreCliente;
    }

    /**
     * Establece el nombre del cliente (temporal).
     *
     * @param nombreCliente Nombre del cliente.
     */
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    /**
     * Calcula el subtotal del detalle multiplicando la cantidad por el precio
     * unitario.
     *
     * @return subtotal del detalle.
     */
    public double getSubtotal() {
        return cantidad * precioUnitario;
    }
}
