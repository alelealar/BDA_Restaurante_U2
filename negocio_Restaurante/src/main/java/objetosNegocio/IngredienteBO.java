/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package objetosNegocio;

import adaptadores.IngredienteAdapter;
import daos.IngredienteDAO;
import dtos.IngredienteDTO;
import dtos.IngredienteNuevoDTO;
import dtos.IngredienteStockDTO;
import entidades.Ingrediente;
import enumerators.TipoMovimiento;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import interfaces.IIngredienteBO;
import interfaces.IIngredienteDAO;
import java.util.List;
import java.util.logging.Logger;


/**
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
    
    private static final Logger LOG = Logger.getLogger(ClienteBO.class.getName());
    
    @Override
    public void agregarIngrediente(IngredienteNuevoDTO ingredienteDTO) throws NegocioException {
        try{
            validarDatosNuevoDTO(ingredienteDTO);
        
            Ingrediente entidad = IngredienteAdapter.nuevoDTOAEntidad(ingredienteDTO);
            
            String identificador = generarIdentificador();
            System.out.println("IDENTIFICADOR: "+identificador);
            entidad.setIdentificador(identificador);

            ingredienteDAO.agregarIngrediente(entidad);
        } catch (PersistenciaException ex) {
            LOG.warning(() -> "No fue posible agregar el ingrediente: " + ingredienteDTO.toString());
            throw new NegocioException("No fue posible agregar el ingrediente", ex);
        }
        
    }

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
            
            int stockActual = ingrediente.getStock();
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
            
            ingrediente.setStock(nuevoStock);
            ingredienteDAO.actualizarStock(ingrediente);
            
        } catch (PersistenciaException ex) {
            LOG.warning(() -> "No se pudieron obtener los ingredientes");
            throw new NegocioException("No fue posible consultar los ingredientes", ex);
        }
    }

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

        if (ingredienteDto.getStock() == null) {
            throw new NegocioException("El stock es obligatorio.");
        }

        if (ingredienteDto.getStock() < 0) {
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
