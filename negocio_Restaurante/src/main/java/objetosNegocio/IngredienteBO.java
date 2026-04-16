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
import static enumerators.TipoMovimiento.ENTRADA;
import static enumerators.TipoMovimiento.SALIDA;
import enumerators.Unidad;
import enumerators.UnidadDTO;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import interfaces.IIngredienteBO;
import interfaces.IIngredienteDAO;
import interfaces.IProductoIngredienteDAO;
import java.util.List;
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
public class IngredienteBO implements IIngredienteBO {

    /**
     * Instancia única de la clase (Singleton).
     */
    private static IngredienteBO instancia;

    /**
     * Constructor público de la clase.
     */
    public IngredienteBO() {
    }

    /**
     * Obtiene la instancia única de IngredienteBO.
     *
     * @return instancia única de IngredienteBO
     */
    public static IngredienteBO getInstance() {
        if (instancia == null) {
            instancia = new IngredienteBO();
        }
        return instancia;
    }

    private final IIngredienteDAO ingredienteDAO = IngredienteDAO.getInstance();
    private final IProductoIngredienteDAO piDAO = ProductoIngredienteDAO.getInstance();

    private static final Logger LOG = Logger.getLogger(ClienteBO.class.getName());

    /**
     * Agrega un nuevo ingrediente al sistema.
     *
     * @param ingredienteDTO datos del ingrediente a registrar
     * @throws NegocioException si los datos son inválidos o ocurre un error en persistencia
     */
    @Override
    public void agregarIngrediente(IngredienteNuevoDTO ingredienteDTO) throws NegocioException {
        try {
            validarDatosNuevoDTO(ingredienteDTO);

            Ingrediente entidad = IngredienteAdapter.nuevoDTOAEntidad(ingredienteDTO);

            String identificador = generarIdentificador();
            entidad.setIdentificador(identificador);

            ingredienteDAO.agregarIngrediente(entidad);

        } catch (PersistenciaException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    /**
     * Actualiza el stock de un ingrediente.
     *
     * @param idIngrediente id del ingrediente
     * @param cantidad cantidad a mover
     * @param tipo tipo de movimiento (ENTRADA o SALIDA)
     * @throws NegocioException si el stock es insuficiente o datos inválidos
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

        try {
            Ingrediente ingrediente = ingredienteDAO.buscarPorId(idIngrediente);

            if (ingrediente == null) {
                throw new NegocioException("No existe el ingrediente con id: " + idIngrediente);
            }

            int stockActual = ingrediente.getStockActual();
            int nuevoStock;

            switch (tipo) {
                case ENTRADA:
                    nuevoStock = stockActual + cantidad;
                    break;

                case SALIDA:
                    if (stockActual < cantidad) {
                        throw new NegocioException("Stock insuficiente");
                    }
                    nuevoStock = stockActual - cantidad;
                    break;

                default:
                    throw new NegocioException("Tipo de movimiento no soportado");
            }

            ingrediente.setStockActual(nuevoStock);
            ingredienteDAO.actualizarStock(ingrediente);

        } catch (PersistenciaException ex) {
            throw new NegocioException("No fue posible consultar los ingredientes", ex);
        }
    }

    /**
     * Elimina un ingrediente del sistema.
     *
     * @param ingrediente ingrediente a eliminar
     * @throws NegocioException si está en uso o no existe
     */
    @Override
    public void eliminarIngrediente(IngredienteDTO ingrediente) throws NegocioException {
        validarDatosDTO(ingrediente);

        try {
            Ingrediente entidad = ingredienteDAO.buscarPorId(ingrediente.getId());

            if (entidad == null) {
                throw new NegocioException("El ingrediente no existe");
            }

            boolean enUso = piDAO.existeIngredienteEnRecetas(entidad.getId());

            if (enUso) {
                throw new NegocioException("No se puede eliminar, está en uso");
            }

            ingredienteDAO.eliminarIngrediente(entidad);

        } catch (PersistenciaException ex) {
            throw new NegocioException("No fue posible eliminar el ingrediente", ex);
        }
    }

    /**
     * Actualiza un ingrediente existente.
     *
     * @param ingrediente ingrediente con nuevos datos
     * @throws NegocioException si el ingrediente no existe
     */
    @Override
    public void actualizarIngrediente(IngredienteDTO ingrediente) throws NegocioException {
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
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    /**
     * Obtiene todos los ingredientes.
     *
     * @return lista de ingredientes
     * @throws NegocioException si ocurre un error de consulta
     */
    @Override
    public List<IngredienteDTO> obtenerIngredientes() throws NegocioException {
        try {
            List<Ingrediente> ingredientes = ingredienteDAO.obtenerIngredientes();
            return IngredienteAdapter.listaEntidadADTO(ingredientes);
        } catch (PersistenciaException ex) {
            throw new NegocioException("No fue posible consultar los ingredientes", ex);
        }
    }

    /**
     * Busca ingredientes por nombre y unidad.
     *
     * @param nombre nombre o parte del nombre
     * @param unidadDTO unidad de medida
     * @return lista de ingredientes encontrados
     * @throws NegocioException si ocurre un error en la búsqueda
     */
    @Override
    public List<IngredienteDTO> buscarIngredientes(String nombre, UnidadDTO unidadDTO) throws NegocioException {
        try {
            Unidad unidad = null;

            if (unidadDTO != null) {
                unidad = Unidad.valueOf(unidadDTO.name());
            }

            List<Ingrediente> ingredientes = ingredienteDAO.buscarIngredientes(nombre, unidad);

            return IngredienteAdapter.listaEntidadADTO(ingredientes);

        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al buscar ingredientes", ex);
        }
    }

    /**
     * Valida datos de ingrediente existente.
     *
     * @param ingredienteDto objeto a validar
     * @throws NegocioException si los datos no son válidos
     */
    private void validarDatosDTO(IngredienteDTO ingredienteDto) throws NegocioException {
        if (ingredienteDto == null) {
            throw new NegocioException("El ingrediente no puede ser nulo.");
        }
    }

    /**
     * Valida datos de nuevo ingrediente.
     *
     * @param ingredienteNuevoDto objeto a validar
     * @throws NegocioException si los datos no son válidos
     */
    private void validarDatosNuevoDTO(IngredienteNuevoDTO ingredienteNuevoDto) throws NegocioException {
        if (ingredienteNuevoDto == null) {
            throw new NegocioException("El ingrediente no puede ser nulo.");
        }
    }

    /**
     * Valida datos de stock.
     *
     * @param ingredienteStock datos de stock
     * @throws NegocioException si los datos son inválidos
     */
    private void validarDatosStockDTO(IngredienteStockDTO ingredienteStock) throws NegocioException {
        if (ingredienteStock == null) {
            throw new NegocioException("El ingrediente no puede ser nulo.");
        }
    }

    /**
     * Genera identificador único.
     *
     * @return identificador generado
     * @throws NegocioException si falla la generación
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