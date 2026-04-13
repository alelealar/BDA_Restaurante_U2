/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package objetosNegocio;

import adaptadores.IngredienteAdapter;
import daos.IngredienteDAO;
import daos.ProductoIngredienteDAO;
import dtos.IngredienteDTO;
import dtos.IngredienteNuevoDTO;
import dtos.IngredienteStockDTO;
import entidades.Ingrediente;
import enumerators.TipoMovimiento;
import enumerators.Unidad;
import enumerators.UnidadDTO;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import interfaces.IIngredienteBO;
import interfaces.IIngredienteDAO;
import interfaces.IProductoIngredienteDAO;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Objeto de negocio encargado de la gestión de ingredientes.
 * 
 * Se encarga de aplicar las reglas de negocio, validar datos y
 * coordinar las operaciones relacionadas con ingredientes antes
 * de su persistencia.
 * 
 * @author Alejandra Leal Armenta, 262719
 */

public class IngredienteBO implements IIngredienteBO{

    private static IngredienteBO instancia;
    
    public IngredienteBO(){
    }
    public static IngredienteBO getInstance(){
        if(instancia == null){
            instancia = new IngredienteBO();
        }
        return instancia;
    }
    
    private final IIngredienteDAO ingredienteDAO = IngredienteDAO.getInstance();
    private final IProductoIngredienteDAO piDAO = ProductoIngredienteDAO.getInstance();
    
    private static final Logger LOG = Logger.getLogger(ClienteBO.class.getName());
    /**
     * Agrega un nuevo ingrediente al sistema.Valida que la información sea correcta y que no exista previamente.
     * @param ingredienteDTO
     * @throws NegocioException 
     */
    @Override
    public void agregarIngrediente(IngredienteNuevoDTO ingredienteDTO) throws NegocioException {
        try{
            validarDatosNuevoDTO(ingredienteDTO);
        
            Ingrediente entidad = IngredienteAdapter.nuevoDTOAEntidad(ingredienteDTO);
            
            String identificador = generarIdentificador();
            entidad.setIdentificador(identificador);
 
            ingredienteDAO.agregarIngrediente(entidad);
        } catch (PersistenciaException ex) {
            //LOG.warning(() -> "No fue posible agregar el ingrediente: " + ingredienteDTO.toString());
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    /**
     * Modifica el stock de un ingrediente según el tipo de movimiento.
     * Permite aumentar o disminuir la cantidad disponible, validando que la operación sea válida.
     * @param idIngrediente
     * @param cantidad
     * @param tipo
     * @throws NegocioException 
     */
    @Override
    public void actualizarStock(Long idIngrediente, int cantidad, TipoMovimiento tipo) throws NegocioException {
        if (idIngrediente == null || idIngrediente <= 0) {
            throw new NegocioException("El id del ingrediente no es válido");
        }

        if (cantidad <= 0) {
            throw new NegocioException("La cantidad debe ser mayor a 0");
        }

        if (tipo == null) {
            throw new NegocioException("El tipo de movimiento no puede ser nulo");
        }
        try{
            Ingrediente ingrediente = ingredienteDAO.buscarPorId(idIngrediente);
            
            if (ingrediente == null) {
                throw new NegocioException("No existe el ingrediente con id: " + idIngrediente);
            }
            
            int stockActual = ingrediente.getStockActual();
            int nuevoStock;
            
            switch(tipo){
                case ENTRADA:
                    nuevoStock = stockActual + cantidad;
                    break;
                    
                case SALIDA:
                    if(stockActual < cantidad){
                        throw new NegocioException("Stock insuficiente. Disponible: " + stockActual + ", solicitado: " + cantidad);
                    }
                    nuevoStock = stockActual - cantidad;
                    break;
                    
                default:
                    throw new NegocioException("Tipo de movimiento no soportado");
            }            
            
            ingrediente.setStockActual(nuevoStock);
            ingredienteDAO.actualizarStock(ingrediente);
            
        } catch (PersistenciaException ex) {
            LOG.warning(() -> "No se pudieron obtener los ingredientes");
            throw new NegocioException("No fue posible consultar los ingredientes", ex);
        }
    }
    
    /**
     * Elimina un ingrediente del sistema.
     * Verifica que no esté en uso antes de realizar la eliminación.
     * @param ingrediente
     * @throws NegocioException 
     */
    @Override
    public void eliminarIngrediente(IngredienteDTO ingrediente) throws NegocioException{
        validarDatosDTO(ingrediente);
        
        try {
            Ingrediente entidad = ingredienteDAO.buscarPorId(ingrediente.getId());

            if (entidad == null) {
                throw new NegocioException("El ingrediente no existe");
            }
            
            boolean enUso = piDAO.existeIngredienteEnRecetas(entidad.getId());
            
            if (enUso) {
                throw new NegocioException("No se puede eliminar, el ingrediente está en uso en recetas");
            }
            
            String urlImagen = entidad.getUrlImagen();
                       
            ingredienteDAO.eliminarIngrediente(entidad);
            
            if (urlImagen != null && !urlImagen.isEmpty()) {
                File archivo = new File(urlImagen);
                if(archivo.exists()){
                    archivo.delete();
                }
            }
            
        } catch (PersistenciaException ex) {
            LOG.warning(() -> "No fue posible eliminar el ingrediente: " + ingrediente.toString());
            throw new NegocioException("No fue posible eliminar el ingrediente", ex);
        }
    }
    
    /**
     * Actualiza la información de un ingrediente existente.
     * Valida los datos y aplica los cambios correspondientes.
     * @param ingrediente
     * @throws NegocioException 
     */
    @Override
    public void actualizarIngrediente(IngredienteDTO ingrediente) throws NegocioException{
        validarDatosDTO(ingrediente);
        
        try {
            Ingrediente entidad = ingredienteDAO.buscarPorId(ingrediente.getId());
            
            if (entidad == null) {
                throw new NegocioException("El ingrediente no existe");
            }
            
                  
            entidad.setNombre(ingrediente.getNombre());
            entidad.setUnidadMedida(Unidad.valueOf(ingrediente.getUnidadMedida().name()));
            entidad.setStockActual(ingrediente.getStockActual());
            entidad.setStockMinimo(ingrediente.getStockMinimo());
            entidad.setUrlImagen(ingrediente.getUrlImagen());
            
            ingredienteDAO.actualizarIngrediente(entidad);
            
        } catch (PersistenciaException ex) {
            LOG.warning(() -> "No fue posible actualizar el ingrediente: " + ingrediente.toString());
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    /**
     * Obtiene la lista de ingredientes registrados en el sistema.
     * Proporciona la información necesaria para su uso en la aplicación.
     * @return
     * @throws NegocioException 
     */
    @Override
    public List<IngredienteDTO> obtenerIngredientes() throws NegocioException{
        try{           
            List<Ingrediente> ingredientes = ingredienteDAO.obtenerIngredientes();
            
            return IngredienteAdapter.listaEntidadADTO(ingredientes);
        } catch (PersistenciaException ex) {
            LOG.warning(() -> "No se pudieron obtener los ingredientes");
            throw new NegocioException("No fue posible consultar los ingredientes", ex);
        }
    } 
    
    /**
     * Busca ingredientes según nombre y unidad de medida.
     * Convierte los resultados a DTO para su uso en la aplicación.
     * 
     * @param nombre nombre o parte del nombre del ingrediente
     * @param unidadDTO unidad de medida del ingrediente
     * @return lista de ingredientes encontrados en formato DTO
     * @throws NegocioException si ocurre un error en la búsqueda
     */
    @Override
    public List<IngredienteDTO> buscarIngredientes(String nombre, UnidadDTO unidadDTO) throws NegocioException{
        try{
            Unidad unidad = null;

            if (unidadDTO != null) {
                unidad = Unidad.valueOf(unidadDTO.name());
            }
            
            List<Ingrediente> ingredientes = ingredienteDAO.buscarIngredientes(nombre, unidad);
            
            return IngredienteAdapter.listaEntidadADTO(ingredientes);
        } catch (PersistenciaException ex) {
            LOG.warning(() -> "No se pudieron obtener los ingredientes");
            throw new NegocioException("Error al buscar ingredientes", ex);
        }
    }
    
    /**
     * Valida los datos de un ingrediente para actualización.
     * Verifica campos obligatorios, valores válidos y normaliza datos como nombre e imagen.
     * @param ingredienteDto
     * @throws NegocioException 
     */
    private void validarDatosDTO(IngredienteDTO ingredienteDto) throws NegocioException {
        
        if (ingredienteDto == null) {
            throw new NegocioException("El ingrediente no puede ser nulo.");
        }

        if (ingredienteDto.getNombre() == null || ingredienteDto.getNombre().trim().isEmpty()) {
            throw new NegocioException("El nombre del ingrediente es obligatorio.");
        }

        if (ingredienteDto.getUnidadMedida() == null) {
            throw new NegocioException("La unidad de medida es obligatoria.");
        }

        if (ingredienteDto.getStockActual() == null) {
            throw new NegocioException("El stock es obligatorio.");
        }

        if (ingredienteDto.getStockActual() < 0) {
            throw new NegocioException("El stock no puede ser negativo.");
        }

        ingredienteDto.setNombre(ingredienteDto.getNombre().trim());

        if (ingredienteDto.getUrlImagen() != null) {

            String url = ingredienteDto.getUrlImagen().trim();

            // Normalizar
            if (url.isEmpty()) {
                ingredienteDto.setUrlImagen(null);
            } else {
                if (!url.matches("(?i).*\\.(jpg|jpeg|png)$")) {
                    throw new NegocioException("La imagen debe ser formato JPG o PNG.");
                }
                ingredienteDto.setUrlImagen(url);
            }
        }
    }
    
    /**
     * Valida los datos de un nuevo ingrediente.
     * Verifica campos obligatorios, stock inicial válido y normaliza datos.
     * @param ingredienteNuevoDto
     * @throws NegocioException 
     */
    private void validarDatosNuevoDTO(IngredienteNuevoDTO ingredienteNuevoDto) throws NegocioException{
        if (ingredienteNuevoDto == null) {
            throw new NegocioException("El ingrediente no puede ser nulo.");
        }

        if (ingredienteNuevoDto.getNombre() == null || ingredienteNuevoDto.getNombre().trim().isEmpty()) {
            throw new NegocioException("El nombre del ingrediente es obligatorio.");
        }

        if (ingredienteNuevoDto.getUnidadMedida() == null) {
            throw new NegocioException("La unidad de medida es obligatoria.");
        }

        if (ingredienteNuevoDto.getStockInicial()== null) {
            throw new NegocioException("El stock es obligatorio.");
        }

        if (ingredienteNuevoDto.getStockInicial()< 0) {
            throw new NegocioException("El stock no puede ser negativo.");
        }

        ingredienteNuevoDto.setNombre(ingredienteNuevoDto.getNombre().trim());

        if (ingredienteNuevoDto.getUrlImagen() != null) {

            String url = ingredienteNuevoDto.getUrlImagen().trim();

            // Normalizar
            if (url.isEmpty()) {
                ingredienteNuevoDto.setUrlImagen(null);
            } else {
                if (!url.matches("(?i).*\\.(jpg|jpeg|png)$")) {
                    throw new NegocioException("La imagen debe ser formato JPG o PNG.");
                }
                ingredienteNuevoDto.setUrlImagen(url);
            }
        }
    }
    
    /**
     * Valida los datos necesarios para modificar el stock de un ingrediente.
     * Verifica cantidad y tipo de movimiento válidos.
     * @param ingredienteStock
     * @throws NegocioException 
     */
    private void validarDatosStockDTO(IngredienteStockDTO ingredienteStock) throws NegocioException{
        if (ingredienteStock == null){
            throw new NegocioException("El ingrediente no puede ser nulo.");
        }
        
        if (ingredienteStock.getCantidad() == null){
            throw new NegocioException("La cantidad no puede ser nula.");
        }
        
        if (ingredienteStock.getCantidad() < 0){
            throw new NegocioException("La cantidad no puede ser negativa");
        }
        
        if (ingredienteStock.getTipoMovimiento() == null){
            throw new NegocioException("El movimiento debe ser ENTRADA o SALIDA");
        }
    }
    
    /**
     * Genera un identificador único para un ingrediente.
     * Se basa en el último identificador registrado para generar el siguiente consecutivo.
     * @return Identificador generado.
     * @throws NegocioException 
     */
    private String generarIdentificador() throws NegocioException {
        try {
            String ultimo = ingredienteDAO.obtenerUltimoIdentificador();

            if (ultimo == null) {
                return "IN-001";
            }

            int numero = Integer.parseInt(ultimo.trim().substring(3));
            numero++;

            return String.format("IN-%03d", numero);

        } catch (Exception e) {
            throw new NegocioException("No se pudo generar el identificador", e);
        }
    }
}
