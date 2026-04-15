package pantallas.moduloComandas;

import controlador.CoordinadorModuloComandas;
import dtos.ComandaDTO;
import dtos.DetalleComandaDTO;
import dtos.IngredienteDTO;
import dtos.MesaDTO;
import dtos.ProductoDTO;
import dtos.ProductoIngredienteDTO;
import enumerators.TipoMovimiento;
import excepciones.NegocioException;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import pantallas.moduloComandas.vistas.panProducto;
import pantallas.moduloComandas.vistas.panProductoMenu;

/**
 * Pantalla para gestionar los pedidos de una comanda. Permite agregar
 * productos, visualizar pedidos y calcular el total.
 *
 * @author Kaleb
 */
public class frmPedidos extends javax.swing.JFrame {

    private List<DetalleComandaDTO> detallesTemporales = new ArrayList<>();
    private final CoordinadorModuloComandas coordinador;
    private final MesaDTO mesa;
    private double total = 0.0;
    private ComandaDTO comanda;

    /**
     * Constructor de la pantalla de pedidos.
     *
     * @param coordinador Coordinador del módulo
     * @param mesa Mesa asociada
     * @param comanda Comanda actual
     */
    public frmPedidos(CoordinadorModuloComandas coordinador, MesaDTO mesa, ComandaDTO comanda) {
        initComponents();
        this.coordinador = coordinador;
        this.mesa = mesa;
        this.comanda = comanda;

        configurarPantalla();
        cargarProductos();
    }

    /**
     * Configura layouts y enfoque inicial.
     */
    private void configurarPantalla() {
        jScrollPane1.getViewport().setLayout(new BorderLayout());
        SwingUtilities.invokeLater(() -> jPanel1.requestFocusInWindow());

        panContenedorProductos.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
        panProductosPedidos.setLayout(new BoxLayout(panProductosPedidos, BoxLayout.Y_AXIS));
    }

    /**
     * Carga todos los productos disponibles en la interfaz.
     */
    private void cargarProductos() {
        try {
            List<ProductoDTO> productos = coordinador.consultarProductos();

            for (ProductoDTO producto : productos) {
                panContenedorProductos.add(new panProductoMenu(producto, coordinador));
            }

            actualizarPanel(panContenedorProductos);

        } catch (NegocioException ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void cargarProductosFiltrados(List<ProductoDTO> productos) {
        panContenedorProductos.removeAll();

        for (ProductoDTO producto : productos) {
            panContenedorProductos.add(new panProductoMenu(producto, coordinador));
        }

        actualizarPanel(panContenedorProductos);
    }

    /**
     * Agrega un producto a la comanda.
     *
     * @param producto Producto seleccionado
     */
    public void agregarProductosComanda(ProductoDTO producto) {

        String comentario = pedirComentario();

        for (DetalleComandaDTO d : detallesTemporales) {
            boolean mismoProducto = d.getIdProducto().equals(producto.getId());
            boolean mismoComentario = (d.getComentario() == null && comentario.isBlank())
                    || (d.getComentario() != null && d.getComentario().equalsIgnoreCase(comentario));

            if (mismoProducto && mismoComentario) {
                d.setCantidad(d.getCantidad() + 1);
                actualizarTotal(producto.getPrecio());
                refrescarPanelPedidos();
                return;
            }
        }

        DetalleComandaDTO detalle = new DetalleComandaDTO();
        detalle.setIdProducto(producto.getId());
        detalle.setCantidad(1);
        detalle.setPrecioUnitario(producto.getPrecio());
        detalle.setComentario(comentario);

        detallesTemporales.add(detalle);

        agregarPanelProducto(detalle);
        actualizarTotal(detalle.getPrecioUnitario());
    }

    /**
     * Método para refrescar UI
     */
    private void refrescarPanelPedidos() {
        panProductosPedidos.removeAll();

        for (DetalleComandaDTO d : detallesTemporales) {
            agregarPanelProducto(d);
        }

        actualizarPanel(panProductosPedidos);
    }

    /**
     * Actualiza la comanda en persistencia.
     *
     * @param detalle Detalle a agregar
     */
    private void actualizarComanda(DetalleComandaDTO detalle) {
        try {
            ComandaDTO actual = coordinador.obtenerComanda(comanda.getId());

            actual.getDetalles().add(detalle);
            actual.setTotal(calcularTotal(actual.getDetalles()));

            coordinador.actualizarComanda(actual);
            this.comanda = actual;

        } catch (NegocioException ex) {
            mostrarError(ex.getMessage());
        }
    }

    /**
     * Calcula el total de la comanda.
     *
     * @param detalles Lista de detalles
     * @return Total calculado
     */
    private double calcularTotal(List<DetalleComandaDTO> detalles) {
        double totalCalculado = 0.0;

        for (DetalleComandaDTO d : detalles) {
            totalCalculado += d.getCantidad() * d.getPrecioUnitario();
        }

        return totalCalculado;
    }

    /**
     * Agrega el panel visual de un producto.
     *
     * @param detalle Detalle a mostrar
     */
    private void agregarPanelProducto(DetalleComandaDTO detalle) {
        panProducto panel = new panProducto(detalle, coordinador, comanda);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

        panProductosPedidos.add(panel);
        panProductosPedidos.add(Box.createVerticalStrut(10));

        actualizarPanel(panProductosPedidos);
    }

    /**
     * Verifica si el producto ya existe sin comentario y suma cantidad.
     *
     * @param producto Producto a buscar
     * @return true si se sumó, false si no
     */
    private boolean sumarSiYaExiste(ProductoDTO producto, String comentarioNuevo) {

        for (Component comp : panProductosPedidos.getComponents()) {

            if (!(comp instanceof panProducto panel)) {
                continue;
            }

            try {
                ProductoDTO actual = coordinador.obtenerProducto(panel.getDetalle().getIdProducto());

                String comentarioExistente = panel.getDetalle().getComentario();

                boolean mismosComentarios
                        = sinComentario(panel) || (comentarioExistente != null && comentarioExistente.equalsIgnoreCase(comentarioNuevo));

                if (actual.getId().equals(producto.getId()) && mismosComentarios) {
                    panel.agregarOtraUnidad();
                    return true;
                }

            } catch (NegocioException ex) {
                mostrarError(ex.getMessage());
            }
        }
        return false;
    }

    /**
     * Verifica si un detalle no tiene comentario.
     *
     * @param panel Panel del producto
     * @return true si no tiene comentario
     */
    private boolean sinComentario(panProducto panel) {
        String comentario = panel.getDetalle().getComentario();
        return comentario == null || comentario.isBlank();
    }

    /**
     * Solicita un comentario al usuario.
     *
     * @return Comentario ingresado
     */
    private String pedirComentario() {
        String texto = JOptionPane.showInputDialog(this, "Ingrese un comentario", "Comentario", JOptionPane.PLAIN_MESSAGE);
        return (texto == null) ? "" : texto.trim();
    }

    /**
     * Actualiza el total mostrado.
     *
     * @param monto Monto a agregar
     */
    public void actualizarTotal(double monto) {
        total += monto;
        lblTotal.setText(String.format("Total: $%.2f", total));
    }

    /**
     * Refresca un panel.
     *
     * @param panel Panel a actualizar
     */
    private void actualizarPanel(JPanel panel) {
        panel.revalidate();
        panel.repaint();
    }

    /**
     * Compara comentarios ignorando mayúsculas/minúsculas.
     *
     * @param comentarioExistente Comentario del detalle actual
     * @return true si coincide con el nuevo comentario
     */
    private boolean mismoComentario(String comentarioExistente) {

        String nuevoComentario = pedirComentario();

        if ((comentarioExistente == null || comentarioExistente.isBlank())
                && nuevoComentario.isBlank()) {
            return true;
        }

        return comentarioExistente != null
                && comentarioExistente.equalsIgnoreCase(nuevoComentario);
    }

    /**
     * Muestra un mensaje de error.
     *
     * @param mensaje Mensaje a mostrar
     */
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panEncabezado = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        panMenu = new javax.swing.JPanel();
        btnComandas = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        btnMesas = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panContenedorProductos = new javax.swing.JPanel();
        panBuscador = new javax.swing.JPanel();
        txtBuscador = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        panProductosPedidos = new javax.swing.JPanel();
        btnOrdenar = new javax.swing.JButton();
        btnAtras = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lblTotal = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setMaximumSize(new java.awt.Dimension(1212, 677));
        setMinimumSize(new java.awt.Dimension(1212, 677));
        setResizable(false);
        setSize(new java.awt.Dimension(1212, 677));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        panEncabezado.setBackground(new java.awt.Color(255, 191, 71));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo_negro_pequenio.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Comandas");

        javax.swing.GroupLayout panEncabezadoLayout = new javax.swing.GroupLayout(panEncabezado);
        panEncabezado.setLayout(panEncabezadoLayout);
        panEncabezadoLayout.setHorizontalGroup(
            panEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panEncabezadoLayout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panEncabezadoLayout.setVerticalGroup(
            panEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panEncabezadoLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panEncabezadoLayout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        panMenu.setBackground(new java.awt.Color(255, 246, 222));

        btnComandas.setBackground(new java.awt.Color(255, 228, 132));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(74, 68, 89));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Comandas");

        javax.swing.GroupLayout btnComandasLayout = new javax.swing.GroupLayout(btnComandas);
        btnComandas.setLayout(btnComandasLayout);
        btnComandasLayout.setHorizontalGroup(
            btnComandasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnComandasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnComandasLayout.setVerticalGroup(
            btnComandasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnComandasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnMesas.setBackground(new java.awt.Color(255, 246, 222));

        jLabel4.setBackground(new java.awt.Color(74, 68, 89));
        jLabel4.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(74, 68, 89));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Mesas");

        javax.swing.GroupLayout btnMesasLayout = new javax.swing.GroupLayout(btnMesas);
        btnMesas.setLayout(btnMesasLayout);
        btnMesasLayout.setHorizontalGroup(
            btnMesasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnMesasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnMesasLayout.setVerticalGroup(
            btnMesasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnMesasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panMenuLayout = new javax.swing.GroupLayout(panMenu);
        panMenu.setLayout(panMenuLayout);
        panMenuLayout.setHorizontalGroup(
            panMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnComandas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnMesas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panMenuLayout.setVerticalGroup(
            panMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panMenuLayout.createSequentialGroup()
                .addComponent(btnComandas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnMesas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panContenedorProductos.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(panContenedorProductos);

        panBuscador.setBackground(new java.awt.Color(255, 255, 255));

        txtBuscador.setBackground(new java.awt.Color(230, 230, 230));
        txtBuscador.setForeground(new java.awt.Color(73, 69, 79));
        txtBuscador.setText("Buscar Producto");
        txtBuscador.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBuscadorFocusGained(evt);
            }
        });
        txtBuscador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscadorActionPerformed(evt);
            }
        });
        txtBuscador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscadorKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout panBuscadorLayout = new javax.swing.GroupLayout(panBuscador);
        panBuscador.setLayout(panBuscadorLayout);
        panBuscadorLayout.setHorizontalGroup(
            panBuscadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panBuscadorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtBuscador, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(425, Short.MAX_VALUE))
        );
        panBuscadorLayout.setVerticalGroup(
            panBuscadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panBuscadorLayout.createSequentialGroup()
                .addComponent(txtBuscador, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        jScrollPane2.setBorder(null);
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panProductosPedidos.setBackground(new java.awt.Color(234, 234, 234));

        javax.swing.GroupLayout panProductosPedidosLayout = new javax.swing.GroupLayout(panProductosPedidos);
        panProductosPedidos.setLayout(panProductosPedidosLayout);
        panProductosPedidosLayout.setHorizontalGroup(
            panProductosPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 358, Short.MAX_VALUE)
        );
        panProductosPedidosLayout.setVerticalGroup(
            panProductosPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 557, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(panProductosPedidos);

        btnOrdenar.setBackground(new java.awt.Color(44, 44, 44));
        btnOrdenar.setForeground(new java.awt.Color(255, 255, 255));
        btnOrdenar.setText("Ordenar");
        btnOrdenar.setBorderPainted(false);
        btnOrdenar.setOpaque(true);
        btnOrdenar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrdenarActionPerformed(evt);
            }
        });

        btnAtras.setBackground(new java.awt.Color(44, 44, 44));
        btnAtras.setForeground(new java.awt.Color(255, 255, 255));
        btnAtras.setText("Atras");
        btnAtras.setBorderPainted(false);
        btnAtras.setOpaque(true);
        btnAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        lblTotal.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(141, 134, 134));
        lblTotal.setText("Total:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTotal)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panEncabezado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panBuscador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 594, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnAtras, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnOrdenar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panEncabezado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 9, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(panBuscador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnOrdenar)
                            .addComponent(btnAtras))
                        .addContainerGap())))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnOrdenarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdenarActionPerformed

        int respuesta = JOptionPane.showConfirmDialog(
                null,
                "¿Desea confirmar los productos?",
                "Confirmación",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (respuesta == JOptionPane.OK_OPTION) {

            try {
                ComandaDTO actual = coordinador.obtenerComanda(comanda.getId());
                actual.getDetalles().addAll(detallesTemporales);

                actual.setTotal(calcularTotal(actual.getDetalles()));

                coordinador.actualizarComanda(actual);

                for (DetalleComandaDTO d : detallesTemporales) {

                    ProductoDTO producto = coordinador.obtenerProducto(d.getIdProducto());

                    List<ProductoIngredienteDTO> productosIngredientes = producto.getIngredientes();

                    for (ProductoIngredienteDTO pi : productosIngredientes) {

                        int cantidadTotal = pi.getCantidad() * d.getCantidad();

                        coordinador.actualizarIngredientes(
                                pi.getIngrediente().getId(),
                                cantidadTotal,
                                TipoMovimiento.SALIDA
                        );
                    }
                }

                this.comanda = actual;

                coordinador.mostrarPantallaComandas(mesa);

            } catch (NegocioException ex) {
                mostrarError(ex.getMessage());
            }
        }
    }//GEN-LAST:event_btnOrdenarActionPerformed

    private void btnAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasActionPerformed
        int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea continuar, se borraran los productos ingresados?", "Advertencia", JOptionPane.OK_CANCEL_OPTION);

        if (respuesta == JOptionPane.OK_OPTION) {
            coordinador.mostrarPantallaComandas(mesa);
        }
    }//GEN-LAST:event_btnAtrasActionPerformed

    private void txtBuscadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscadorActionPerformed

    }//GEN-LAST:event_txtBuscadorActionPerformed

    private void txtBuscadorFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscadorFocusGained
        if (txtBuscador.getText().equals("Buscar Producto")) {
            txtBuscador.setText("");
        }
    }//GEN-LAST:event_txtBuscadorFocusGained

    private void txtBuscadorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscadorKeyReleased
        String nombre = txtBuscador.getText().trim();

        try {
            if (nombre.isEmpty()) {
                panContenedorProductos.removeAll();
                cargarProductos();
            } else {
                List<ProductoDTO> productosFiltrados = coordinador.consultarProductosFiltrados(nombre);
                cargarProductosFiltrados(productosFiltrados);
            }
        } catch (NegocioException ex) {
            System.getLogger(frmPedidos.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }//GEN-LAST:event_txtBuscadorKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtras;
    private javax.swing.JPanel btnComandas;
    private javax.swing.JPanel btnMesas;
    private javax.swing.JButton btnOrdenar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JPanel panBuscador;
    private javax.swing.JPanel panContenedorProductos;
    private javax.swing.JPanel panEncabezado;
    private javax.swing.JPanel panMenu;
    private javax.swing.JPanel panProductosPedidos;
    private javax.swing.JTextField txtBuscador;
    // End of variables declaration//GEN-END:variables
}
