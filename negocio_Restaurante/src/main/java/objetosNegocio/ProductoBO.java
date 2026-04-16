package objetosNegocio;

import adaptadores.ProductoAdapter;
import daos.ProductoDAO;
import dtos.ProductoDTO;
import dtos.ProductoNuevoDTO;
import enumerators.TipoProductoDTO;
import enumerators.TipoProducto;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import entidades.Producto;
import interfaces.IProductoBO;
import interfaces.IProductoDAO;
import java.util.List;
import java.util.logging.Logger;

/**
 * Implementación de la interfaz IProductoBO que contiene la lógica de negocio
 * para la gestión de productos.
 *
 * Esta clase se encarga de validar los datos recibidos desde capas superiores,
 * convertir DTOs a entidades y delegar las operaciones a la capa de acceso a
 * datos (DAO).
 *
 * También maneja errores del sistema y registra eventos con Logger.
 * 
 * @author María José Valdez Iglesias - 262775
 */
public class ProductoBO implements IProductoBO {

    /**
     * Instancia única de la clase (patrón Singleton).
     */
    private static ProductoBO instance;

    /**
     * Logger para registrar eventos del sistema.
     */
    private static final Logger LOG = Logger.getLogger(ProductoBO.class.getName());

    /**
     * DAO utilizado para el acceso a datos de productos.
     */
    private final IProductoDAO productoDAO = ProductoDAO.getInstance();

    /**
     * Constructor privado para evitar instanciación externa.
     */
    private ProductoBO(){}

    /**
     * Obtiene la instancia única de ProductoBO.
     *
     * @return instancia de ProductoBO
     */
    public static ProductoBO getInstance(){
        if(instance == null){
            instance = new ProductoBO();
        }
        return instance;
    }

    /**
     * Registra un nuevo producto en el sistema.
     *
     * Convierte el DTO a entidad, genera su identificador y lo guarda en la base de datos.
     *
     * @param producto objeto ProductoNuevoDTO con la información del producto
     * @throws NegocioException si ocurre un error al registrar el producto
     */
    @Override
    public void agregarProducto(ProductoNuevoDTO producto) throws NegocioException {
        try{
            validarDatos(producto);
            Producto p = ProductoAdapter.dtoAEntidad(producto);
            p.setIdentificador(generarIdentificador(producto.getTipo()));
            productoDAO.agregarProducto(p);
            LOG.info(() -> "Se agregó el producto: " + p.getNombre().toUpperCase());
        } catch(PersistenciaException ex){
            LOG.warning(() -> "Error al agregar producto: " + producto.getNombre().toUpperCase());
            throw new NegocioException("No fue posible agregar el producto.", ex);
        }
    }

    /**
     * Actualiza un producto existente.
     *
     * Convierte el DTO a entidad y lo envía al DAO para su actualización.
     *
     * @param producto objeto ProductoDTO con los datos actualizados
     * @throws NegocioException si ocurre un error al actualizar el producto
     */
    @Override
    public void actualizarProducto(ProductoDTO producto) throws NegocioException {
        try{
            validarDatos(producto);
            Producto p = ProductoAdapter.dtoAEntidad(producto);
            productoDAO.actualizarProducto(p);
            LOG.info(() -> "Se actualizó el producto: " + p.getNombre().toUpperCase());
        } catch(PersistenciaException ex){
            LOG.warning(() -> "Error al actualizar producto: " + producto.getNombre().toUpperCase());
            throw new NegocioException("No fue posible actualizar el producto.", ex);
        }
    }

    /**
     * Consulta todos los productos registrados.
     *
     * @return lista de productos en formato DTO
     * @throws NegocioException si ocurre un error en la consulta
     */
    @Override
    public List<ProductoDTO> consultarTodosProductos() throws NegocioException {
        try{
            List<Producto> productos = productoDAO.consultarTodos();
            return ProductoAdapter.listaEntidadADTO(productos);
        } catch(PersistenciaException ex){
            LOG.warning("Error al consultar productos.");
            throw new NegocioException("No fue posible consultar todos los productos.", ex);
        }
    }

    /**
     * Valida los datos de un producto antes de procesarlo.
     *
     * @param dto objeto ProductoDTO o ProductoNuevoDTO
     * @throws NegocioException si los datos no cumplen con las reglas
     */
    private void validarDatos(Object dto) throws NegocioException {
        if(dto == null){
            throw new NegocioException("El producto no puede ser nulo.");
        }

        if(dto instanceof ProductoNuevoDTO p){
            if(p.getNombre() == null || p.getNombre().isBlank()){
                throw new NegocioException("El producto debe tener un nombre.");
            }

            if(p.getDescripcion() == null || p.getDescripcion().isBlank()){
                throw new NegocioException("El producto debe tener una descripción.");
            }

            if(p.getPrecio() == null){
                throw new NegocioException("El producto debe tener un precio.");
            }

            if(p.getTipo() == null){
                throw new NegocioException("El producto debe tener un tipo.");
            }

            if(p.getEstado() == null){
                throw new NegocioException("El producto debe tener un estado.");
            }

            if(p.getUrlImagen() != null && !p.getUrlImagen().isBlank()
                    && !p.getUrlImagen().matches("(?i).*\\.(jpg|jpeg|png)$")){
                throw new NegocioException("La imagen debe ser JPG o PNG.");
            }
        }

        if(dto instanceof ProductoDTO p){
            if(p.getId() == null || p.getId() < 1){
                throw new NegocioException("ID de producto inválido.");
            }

            if(p.getNombre() == null || p.getNombre().isBlank()){
                throw new NegocioException("El producto debe tener un nombre.");
            }

            if(p.getDescripcion() == null || p.getDescripcion().isBlank()){
                throw new NegocioException("El producto debe tener una descripción.");
            }

            if(p.getPrecio() == null){
                throw new NegocioException("El producto debe tener un precio.");
            }

            if(p.getTipo() == null){
                throw new NegocioException("El producto debe tener un tipo.");
            }

            if(p.getEstado() == null){
                throw new NegocioException("El producto debe tener un estado.");
            }
        }
    }

    /**
     * Genera un identificador único para el producto según su tipo.
     *
     * @param tipo tipo de producto
     * @return identificador generado
     * @throws NegocioException si el tipo es inválido
     */
    private String generarIdentificador(TipoProductoDTO tipo) throws NegocioException {
        if(tipo == null){
            throw new NegocioException("El tipo de producto no puede ser nulo.");
        }

        try {
            TipoProducto tipoEntidad = TipoProducto.valueOf(tipo.name());
            int numero = 1;

            String ultimo = productoDAO.obtenerUltimoIdentificador(tipoEntidad);

            if (ultimo != null && ultimo.contains("-")) {
                numero = Integer.parseInt(ultimo.trim().substring(3));
                numero++;
            }

            switch(tipo){
                case BEBIDA:
                    return String.format("BE-%03d", numero);
                case PLATILLO:
                    return String.format("PL-%03d", numero);
                case POSTRE:
                    return String.format("PO-%03d", numero);
                default:
                    throw new NegocioException("Tipo de producto no válido.");
            }
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al generar identificador.", ex);
        }
    }

    /**
     * Activa un producto en el sistema.
     *
     * @param idProducto identificador del producto
     * @throws NegocioException si ocurre un error al activarlo
     */
    @Override
    public void activarProducto(Long idProducto) throws NegocioException {
        try{
            productoDAO.activarProducto(idProducto);
        } catch(PersistenciaException ex){
            throw new NegocioException("No fue posible activar el producto.", ex);
        }
    }

    /**
     * Desactiva un producto en el sistema.
     *
     * @param idProducto identificador del producto
     * @throws NegocioException si ocurre un error al desactivarlo
     */
    @Override
    public void desactivarProducto(Long idProducto) throws NegocioException {
        try{
            productoDAO.desactivarProducto(idProducto);
        } catch(PersistenciaException ex){
            throw new NegocioException("No fue posible desactivar el producto.", ex);
        }
    }

    /**
     * Consulta un producto por su ID.
     *
     * @param idProducto identificador del producto
     * @return producto en formato DTO
     * @throws NegocioException si ocurre un error en la búsqueda
     */
    @Override
    public ProductoDTO consultarProductoPorID(Long idProducto) throws NegocioException {
        try{
            Producto p = productoDAO.consultarProductoPorID(idProducto);
            return ProductoAdapter.entidadADTO(p);
        } catch(PersistenciaException ex){
            throw new NegocioException("No fue posible buscar el producto.", ex);
        }
    }

    /**
     * Consulta productos activos.
     *
     * @return lista de productos activos
     * @throws NegocioException si ocurre un error en la consulta
     */
    @Override
    public List<ProductoDTO> consultarProductosActivos() throws NegocioException {
        try{
            List<Producto> activos = productoDAO.consultarProductosActivos();
            return ProductoAdapter.listaEntidadADTO(activos);
        } catch(PersistenciaException ex){
            throw new NegocioException("No fue posible consultar productos activos.", ex);
        }
    }

    /**
     * Busca productos por nombre y tipo.
     *
     * @param nombre nombre del producto
     * @param tipo tipo de producto
     * @return lista de coincidencias
     * @throws NegocioException si ocurre un error en la búsqueda
     */
    @Override
    public List<ProductoDTO> buscarProductos(String nombre, TipoProductoDTO tipo) throws NegocioException {
        try{
            TipoProducto tipoEntidad = null;
            if(tipo != null){
                tipoEntidad = TipoProducto.valueOf(tipo.name());
            }

            List<Producto> filtrados = productoDAO.buscarProductos(nombre, tipoEntidad);
            return ProductoAdapter.listaEntidadADTO(filtrados);
        } catch(PersistenciaException ex){
            throw new NegocioException("No fue posible buscar productos.", ex);
        }
    }

    /**
     * Busca productos activos filtrados por nombre y tipo.
     *
     * @param nombre nombre del producto
     * @param tipo tipo de producto
     * @return lista de productos activos
     * @throws NegocioException si ocurre un error en la búsqueda
     */
    @Override 
    public List<ProductoDTO> buscarProductosActivos(String nombre, TipoProductoDTO tipo) throws NegocioException {
        try{
            TipoProducto tipoEntidad = null;
            if(tipo != null){
                tipoEntidad = TipoProducto.valueOf(tipo.name());
            }

            List<Producto> filtrados = productoDAO.buscarProductos(nombre, tipoEntidad);
            return ProductoAdapter.listaEntidadADTO(filtrados);
        } catch(PersistenciaException ex){
            throw new NegocioException("No fue posible buscar productos activos.", ex);
        }
    }
}