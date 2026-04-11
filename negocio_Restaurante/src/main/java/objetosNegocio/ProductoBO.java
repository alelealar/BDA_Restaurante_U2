/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
 * Además, maneja excepciones de negocio y registra eventos mediante Logger.
 * 
 * @author María José Valdez Iglesias - 262775
 */
public class ProductoBO implements IProductoBO {

    /**
     * Atributo instancia que la clase regresa para ser utilizado.
     */
    private static ProductoBO instance;
    
    /**
     * Logger para registrar eventos y errores del sistema.
     */
    private static final Logger LOG = Logger.getLogger(ProductoBO.class.getName());
    
    /**
     * Objeto DAO utilizado para interactuar con la base de datos.
     */
    private final IProductoDAO productoDAO = ProductoDAO.getInstance();
    
    /**
     * Constructor vacío y privado, para que no se puedan crear instancias de 
     * la clase.
     */
    private ProductoBO(){}
    
    /**
     * Método estático para regresar la instancia ya creada de la clase.
     * @return El ProductoBO de la clase.
     */
    public static ProductoBO getInstance(){
        if(instance == null){
            instance = new ProductoBO();
        }
        return instance;
    }
    
    /**
     * Método encargado de manejar objeto DTO, transformar el objeto y pasarlo
     * a la DAO para agregarlo.
     * @param producto ProductoDTO que se quiere agregar.
     * @throws NegocioException Si ocurre un error al agregar el producto
     * llamando a la DAO.
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
            LOG.warning(() -> "Ocurrió un error al querer agregar el producto: " + producto.getNombre().toUpperCase());
            throw new NegocioException("No fue posible agregar el producto.", ex);
        }
    }

    /**
     * Método encargado de manejar objeto DTO, tranformar el objeto y pasarlo
     * a la DAO para actualizarlo.
     * @param producto ProductoDTO que se quiere actualizar.
     * @throws NegocioException Si ocurre un error al actualizar el producto
     * llamando a la DAO.
     */
    @Override
    public void actualizarProducto(ProductoDTO producto) throws NegocioException {
        try{
            validarDatos(producto);
            Producto p = ProductoAdapter.dtoAEntidad(producto);
            productoDAO.actualizarProducto(p);
            LOG.info(() -> "Se actualizó el producto: " + p.getNombre().toUpperCase());
        } catch(PersistenciaException ex){
            LOG.warning(() -> "Ocurrió un error al querer actualizar el producto: " + producto.getNombre().toUpperCase());
            throw new NegocioException("No fue posible actualizar el producto.", ex);
        }
    }

    /**
     * Método que consulta todos los productos registrados por medio de la DAO.
     * @return Una lista de ProductoDTO con todos los registros.
     * @throws NegocioException Si ocurre un error al consultar todos los
     * productos llamando a la DAO.
     */
    @Override
    public List<ProductoDTO> consultarTodosProductos() throws NegocioException {
        try{
            List<Producto> productos = productoDAO.consultarTodos();
            
            return ProductoAdapter.listaEntidadADTO(productos);
        } catch(PersistenciaException ex){
            LOG.warning(() -> "Ocurrió un error al querer consultar todos los productos.");
            throw new NegocioException("No fue posible consultar todos los productos.", ex);
        }
    }
    
    /**
     * Método que válida los datos de entrada en los parámetros.
     * @param dto Objeto de tipo DTO a validar.
     * @throws NegocioException Si exite un error con los datos del producto.
     */
    private void validarDatos(Object dto) throws NegocioException {
        if(dto == null){
            throw new NegocioException("El producto no puede ser nulo.");
        }
        
        if(dto instanceof ProductoNuevoDTO p){
            if(p.getNombre() == null || p.getNombre().isEmpty() || p.getNombre().isBlank()){
                throw new NegocioException("El producto debe tener un nombre.");
            } else {
                p.setNombre(p.getNombre().trim());
            }
            
            if(p.getDescripcion() == null || p.getDescripcion().isEmpty() || p.getDescripcion().isBlank()){
                throw new NegocioException("El producto debe contar con una descripción.");
            } else {
                p.setDescripcion(p.getDescripcion().trim());
            }
            
            if(p.getPrecio() == null){
                throw new NegocioException("El producto debe contar con un precio.");
            }
            
            if(p.getTipo() == null){
                throw new NegocioException("El producto debe tener un tipo asignado.");
            }
            
            if(p.getEstado() == null){
                throw new NegocioException("El producto debe venir con un estado.");
            }
            
            if(p.getUrlImagen() == null || p.getUrlImagen().isEmpty() || p.getUrlImagen().isBlank()){
                p.setUrlImagen(null);
            } else if(!p.getUrlImagen().matches("(?i).*\\.(jpg|jpeg|png)$")){
                throw new NegocioException("La imagen solo puede ser JPG o PNG.");
            } else {
                p.setUrlImagen(p.getUrlImagen().trim());
            }
            
        }
        
        if(dto instanceof ProductoDTO p){
            if(p.getId() == null){
                throw new NegocioException("El producto debe contar con un ID.");
            } else if(p.getId() < 1) {
                throw new NegocioException("ID inválido.");
            } 
            
            if(p.getIdentificador() == null || p.getIdentificador().isEmpty() || p.getIdentificador().isBlank()){
                throw new NegocioException("El producto debe tener un identificador.");
            } else {
                p.setIdentificador(p.getIdentificador().trim());
            }
            
            if(p.getNombre() == null || p.getNombre().isEmpty() || p.getNombre().isBlank()){
                throw new NegocioException("El producto debe tener un nombre.");
            } else {
                p.setNombre(p.getNombre().trim());
            }
            
            if(p.getDescripcion() == null || p.getDescripcion().isEmpty() || p.getDescripcion().isBlank()){
                throw new NegocioException("El producto debe contar con una descripción.");
            } else {
                p.setDescripcion(p.getDescripcion().trim());
            }
            
            if(p.getPrecio() == null){
                throw new NegocioException("El producto debe contar con un precio.");
            }
            
            if(p.getTipo() == null){
                throw new NegocioException("El producto debe tener un tipo asignado.");
            }
            
            if(p.getEstado() == null){
                throw new NegocioException("El producto debe venir con un estado.");
            }
            
            if(p.getUrlImagen() == null || p.getUrlImagen().isEmpty() || p.getUrlImagen().isBlank()){
                p.setUrlImagen(null);
            } else if(!p.getUrlImagen().matches("(?i).*\\.(jpg|jpeg|png)$")){
                throw new NegocioException("La imagen solo puede ser JPG o PNG.");
            } else {
                p.setUrlImagen(p.getUrlImagen().trim());
            }
        }
    }
    
    /**
     * Método que genera identificadores/códigos para los productos antes de almacenarlos
     * en la base de datos.
     * @param tipo El tipo del producto.
     * @return El identificador generado.
     * @throws NegocioException Si el parámetro es nulo o si no se reconoce al 
     * tipo de producto.
     */
    private String generarIdentificador(TipoProductoDTO tipo) throws NegocioException {
        if(tipo == null){
            throw new NegocioException("El producto debe contar con un tipo para generar su identificador.");
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
            throw new NegocioException("Error al generar el identificador único.", ex);
        }
    }

    /**
     * Método encargado de activar un producto por medio de la DAO.
     * @param idProducto ID del producto que se quiere activar.
     * @throws NegocioException Si ocurre un error al activar el producto por 
     * medio de la DAO.
     */
    @Override
    public void activarProducto(Long idProducto) throws NegocioException {
        try{
            productoDAO.activarProducto(idProducto);
            LOG.info(() -> "Se activó el producto con ID=" + idProducto);
        } catch(PersistenciaException ex){
            LOG.warning(() -> "Ocurrió un error al querer activar el producto con ID=" + idProducto);
            throw new NegocioException("No fue posible activar el producto.", ex);
        }
    }

    /**
     * Método encargado de desactivar un producto por medio de la DAO.
     * @param idProducto ID del producto que se quiere desactivar.
     * @throws NegocioException Si ocurre un error al activar el producto por 
     * medio de la DAO.
     */
    @Override
    public void desactivarProducto(Long idProducto) throws NegocioException {
        try{
            productoDAO.desactivarProducto(idProducto);
            LOG.info(() -> "Se desactivó el producto con ID=" + idProducto);
        } catch(PersistenciaException ex){
            LOG.warning(() -> "Ocurrió un error al querer desactivar el producto con ID=" + idProducto);
            throw new NegocioException("No fue posible desactivar el producto.", ex);
        }
    }

    /**
     * Método encargado de buscar un producto por medio de su ID con la DAO.
     * @param idProducto ID del producto que se desea buscar.
     * @return Un ProductoDTO con la información del producto encontrado.
     * @throws NegocioException Si ocurre un error al buscar el producto por 
     * medio de la DAO.
     */
    @Override
    public ProductoDTO consultarProductoPorID(Long idProducto) throws NegocioException {
        try{
            Producto p = productoDAO.consultarProductoPorID(idProducto);
            LOG.info(() -> "Se encontró el producto con ID=" + idProducto);
            return ProductoAdapter.entidadADTO(p);
        } catch(PersistenciaException ex){
            LOG.warning(() -> "Ocurrió un error al querer buscar el producto con ID=" + idProducto);
            throw new NegocioException("No fue posible buscar el producto.", ex);
        }
    }

     /**
     * Método que consulta todos los productos activos registrados por medio 
     * de la DAO.
     * @return Una lista de ProductoDTO con todos los registros de estado activo.
     * @throws NegocioException Si ocurre un error al consultar todos los
     * productos llamando a la DAO.
     */
    @Override
    public List<ProductoDTO> consultarProductosActivos() throws NegocioException {
        try{
            List<Producto> activos = productoDAO.consultarProductosActivos();
            LOG.info(() -> "Se consultaron los productos activos.");
            return ProductoAdapter.listaEntidadADTO(activos);
        } catch(PersistenciaException ex){
            LOG.warning(() -> "Ocurrió un error al consultar los productos activos.");
            throw new NegocioException("No fue posible consultar los productos activos.", ex);
        }
    }
    
}
