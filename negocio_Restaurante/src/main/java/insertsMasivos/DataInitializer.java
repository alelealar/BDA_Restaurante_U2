package insertsMasivos;

import dtos.*;
import enumerators.*;
import excepciones.NegocioException;
import interfaces.*;
import java.util.*;
import objetosNegocio.*;

public class DataInitializer {

    public static void ejecutar() throws NegocioException {

        IClienteBO clienteBO = ClienteBO.getInstance();
        IMeseroBO meseroBO = MeseroBO.getInstance();
        IIngredienteBO ingredienteBO = IngredienteBO.getInstance();
        IProductoBO productoBO = ProductoBO.getInstance();
        IMesaBO mesaBO = MesaBO.getInstance();

        // =========================
        // CLIENTES
        // =========================
        if (clienteBO.obtenerClientes().isEmpty()) {

            clienteBO.registrarClienteFrecuente(
                    new ClienteNuevoDTO("Ana Sofía", "Martínez", "López", "6441000001", "a1@gmail.com"));

            clienteBO.registrarClienteFrecuente(
                    new ClienteNuevoDTO("Carlos Eduardo", "Hernández", "García", "6441000002", "a2@gmail.com"));

            clienteBO.registrarClienteFrecuente(
                    new ClienteNuevoDTO("María Fernanda", "Luna", "Ramírez", "6441000003", "a3@gmail.com"));

            clienteBO.registrarClienteFrecuente(
                    new ClienteNuevoDTO("Luis Ángel", "Soto", "Castro", "6441000004", "a4@gmail.com"));

            clienteBO.registrarClienteFrecuente(
                    new ClienteNuevoDTO("José Manuel", "Vega", "Torres", "6441000005", "a5@gmail.com"));
        }

        // =========================
        // MESAS (CORREGIDO)
        // =========================
        List<MesaDTO> mesasExistentes = mesaBO.obtenerMesas();

        for (MesaDTO mesa : mesasExistentes) {
            mesaBO.eliminarMesa(mesa.getId());
        }

        mesaBO.inicializarMesas(20);

        // =========================
        // MESEROS
        // =========================
        List<String> usuarios = Arrays.asList(
                "mario", "juan", "pedro", "luis", "carlos",
                "david", "alex", "jorge", "ricardo", "fernando"
        );

        for (String u : usuarios) {
            if (!meseroBO.existeMesero(u)) {
                meseroBO.registrarMesero(u);
            }
        }

        // =========================
        // INGREDIENTES
        // =========================
        if (ingredienteBO.obtenerIngredientes().isEmpty()) {

            ingredienteBO.agregarIngrediente(new IngredienteNuevoDTO("Pan Hamburguesa", UnidadDTO.PIEZA, 50, 10, null));
            ingredienteBO.agregarIngrediente(new IngredienteNuevoDTO("Carne Res", UnidadDTO.PORCION, 50, 10, null));
            ingredienteBO.agregarIngrediente(new IngredienteNuevoDTO("Queso", UnidadDTO.PIEZA, 80, 15, null));
            ingredienteBO.agregarIngrediente(new IngredienteNuevoDTO("Lechuga", UnidadDTO.PIEZA, 100, 20, null));
            ingredienteBO.agregarIngrediente(new IngredienteNuevoDTO("Tomate", UnidadDTO.PIEZA, 100, 20, null));
            ingredienteBO.agregarIngrediente(new IngredienteNuevoDTO("Tortilla", UnidadDTO.PIEZA, 200, 50, null));
            ingredienteBO.agregarIngrediente(new IngredienteNuevoDTO("Carne Asada", UnidadDTO.PORCION, 80, 20, null));
            ingredienteBO.agregarIngrediente(new IngredienteNuevoDTO("Papa", UnidadDTO.PORCION, 100, 20, null));
            ingredienteBO.agregarIngrediente(new IngredienteNuevoDTO("Refresco Cola", UnidadDTO.PIEZA, 60, 10, null));
            ingredienteBO.agregarIngrediente(new IngredienteNuevoDTO("Limon", UnidadDTO.PIEZA, 80, 15, null));
            ingredienteBO.agregarIngrediente(new IngredienteNuevoDTO("Agua", UnidadDTO.ML, 10000, 1000, null));
            ingredienteBO.agregarIngrediente(new IngredienteNuevoDTO("Cafe Molido", UnidadDTO.GR, 5000, 500, null));
            ingredienteBO.agregarIngrediente(new IngredienteNuevoDTO("Chocolate", UnidadDTO.GR, 3000, 300, null));
            ingredienteBO.agregarIngrediente(new IngredienteNuevoDTO("Harina", UnidadDTO.GR, 5000, 500, null));
            ingredienteBO.agregarIngrediente(new IngredienteNuevoDTO("Helado Vainilla", UnidadDTO.PORCION, 50, 10, null));
            ingredienteBO.agregarIngrediente(new IngredienteNuevoDTO("Pepperoni", UnidadDTO.PIEZA, 100, 20, null));
            ingredienteBO.agregarIngrediente(new IngredienteNuevoDTO("Masa Pizza", UnidadDTO.PIEZA, 40, 10, null));
            ingredienteBO.agregarIngrediente(new IngredienteNuevoDTO("Aderezo Cesar", UnidadDTO.ML, 3000, 300, null));
            ingredienteBO.agregarIngrediente(new IngredienteNuevoDTO("Crutones", UnidadDTO.GR, 2000, 200, null));
        }

        List<IngredienteDTO> ingredientesDB = ingredienteBO.obtenerIngredientes();

        Map<String, IngredienteDTO> map = new HashMap<>();
        for (IngredienteDTO i : ingredientesDB) {
            map.put(i.getNombre().toLowerCase(), i);
        }

        IngredienteDTO pan = map.get("pan hamburguesa");
        IngredienteDTO carne = map.get("carne res");
        IngredienteDTO queso = map.get("queso");
        IngredienteDTO lechuga = map.get("lechuga");
        IngredienteDTO tomate = map.get("tomate");
        IngredienteDTO tortilla = map.get("tortilla");
        IngredienteDTO carneAsada = map.get("carne asada");
        IngredienteDTO papa = map.get("papa");
        IngredienteDTO refresco = map.get("refresco cola");
        IngredienteDTO limon = map.get("limon");
        IngredienteDTO agua = map.get("agua");
        IngredienteDTO cafe = map.get("cafe molido");
        IngredienteDTO chocolate = map.get("chocolate");
        IngredienteDTO harina = map.get("harina");
        IngredienteDTO helado = map.get("helado vainilla");
        IngredienteDTO pepperoni = map.get("pepperoni");
        IngredienteDTO masa = map.get("masa pizza");
        IngredienteDTO aderezo = map.get("aderezo cesar");
        IngredienteDTO crutones = map.get("crutones");

        // =========================
        // PRODUCTOS
        // =========================
        if (!productoBO.consultarTodosProductos().isEmpty()) {
            return;
        }

        ProductoNuevoDTO hamburguesa = new ProductoNuevoDTO(
                "Hamburguesa Especial", TipoProductoDTO.PLATILLO,
                "Hamburguesa completa", 120.0,
                EstadoProductoDTO.ACTIVO, "/img/hamburguesa.jpg"
        );
        hamburguesa.setIngredientes(Arrays.asList(
                new ProductoIngredienteDTO(pan, 2),
                new ProductoIngredienteDTO(carne, 1),
                new ProductoIngredienteDTO(queso, 1),
                new ProductoIngredienteDTO(lechuga, 1),
                new ProductoIngredienteDTO(tomate, 1)
        ));

        ProductoNuevoDTO tacos = new ProductoNuevoDTO(
                "Tacos de Carne Asada", TipoProductoDTO.PLATILLO,
                "Tacos", 90.0,
                EstadoProductoDTO.ACTIVO, "/img/taco.png"
        );
        tacos.setIngredientes(Arrays.asList(
                new ProductoIngredienteDTO(tortilla, 3),
                new ProductoIngredienteDTO(carneAsada, 1)
        ));

        ProductoNuevoDTO pizza = new ProductoNuevoDTO(
                "Pizza Pepperoni", TipoProductoDTO.PLATILLO,
                "Pizza", 150.0,
                EstadoProductoDTO.ACTIVO, "/img/pizza.png"
        );
        pizza.setIngredientes(Arrays.asList(
                new ProductoIngredienteDTO(masa, 1),
                new ProductoIngredienteDTO(queso, 2),
                new ProductoIngredienteDTO(pepperoni, 1)
        ));

        ProductoNuevoDTO ensalada = new ProductoNuevoDTO(
                "Ensalada César", TipoProductoDTO.PLATILLO,
                "Ensalada", 80.0,
                EstadoProductoDTO.ACTIVO, "/img/ensalada.jpg"
        );
        ensalada.setIngredientes(Arrays.asList(
                new ProductoIngredienteDTO(lechuga, 2),
                new ProductoIngredienteDTO(aderezo, 50),
                new ProductoIngredienteDTO(crutones, 30)
        ));

        ProductoNuevoDTO papas = new ProductoNuevoDTO(
                "Papas Fritas", TipoProductoDTO.PLATILLO,
                "Papas", 60.0,
                EstadoProductoDTO.ACTIVO, "/img/papas.png"
        );
        papas.setIngredientes(List.of(
                new ProductoIngredienteDTO(papa, 3)
        ));

        productoBO.agregarProducto(hamburguesa);
        productoBO.agregarProducto(tacos);
        productoBO.agregarProducto(pizza);
        productoBO.agregarProducto(ensalada);
        productoBO.agregarProducto(papas);

        System.out.println("✔ Inserts masivos ejecutados correctamente");
    }
}
