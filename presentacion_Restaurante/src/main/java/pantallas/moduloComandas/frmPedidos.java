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
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
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
        SwingUtilities.invokeLater(() -> jPanel1.requestFocusInWindow());

        panContenedorProductos.setLayout(new java.awt.GridLayout(0, 3, 15, 15));
        panContenedorProductos.setPreferredSize(null);
        panProductosPedidos.setLayout(new BoxLayout(panProductosPedidos, BoxLayout.Y_AXIS));
    }

    public javax.swing.JPanel getPanContenedorProductosPedidos() {
        return panProductosPedidos;
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
            if (d.getIdProducto().equals(producto.getId())
                    && normalizar(d.getComentario()).equals(normalizar(comentario))) {

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

        actualizarTotal(detalle.getPrecioUnitario());
        refrescarPanelPedidos();
    }

    private String normalizar(String c) {
        return (c == null) ? "" : c.trim().toLowerCase();
    }

    /**
     * Método para refrescar UI
     */
    private void refrescarPanelPedidos() {
        panProductosPedidos.removeAll();

        for (DetalleComandaDTO d : detallesTemporales) {
            panProducto panel = new panProducto(d, coordinador, this);

            panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
            panel.setAlignmentX(LEFT_ALIGNMENT);

            panProductosPedidos.add(panel);
        }

        actualizarPanel(panProductosPedidos);
    }

    public void eliminarDetalleTemporal(DetalleComandaDTO detalle) {
        try {
            detallesTemporales.remove(detalle);
            total = calcularTotal(detallesTemporales);
            lblTotal.setText(String.format("Total: $%.2f", total));
            ProductoDTO productoDetalle = coordinador.obtenerProducto(detalle.getIdProducto());
            List<ProductoIngredienteDTO> ingredientesDetalle = productoDetalle.getIngredientes();
            for (ProductoIngredienteDTO productoIngredienteDTO : ingredientesDetalle) {
             coordinador.actualizarIngredientes(productoIngredienteDTO.getIngrediente().getId(), detalle.getCantidad(), TipoMovimiento.ENTRADA);   
            }
            refrescarPanelPedidos();
        } catch (NegocioException ex) {
            System.getLogger(frmPedidos.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
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
        panBuscador = new javax.swing.JPanel();
        txtBuscador = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        panProductosPedidos = new javax.swing.JPanel();
        btnOrdenar = new javax.swing.JButton();
        btnAtras = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lblTotal = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        panContenedorProductos = new javax.swing.JPanel();

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

        javax.swing.GroupLayout panMenuLayout = new javax.swing.GroupLayout(panMenu);
        panMenu.setLayout(panMenuLayout);
        panMenuLayout.setHorizontalGroup(
            panMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnComandas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panMenuLayout.setVerticalGroup(
            panMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panMenuLayout.createSequentialGroup()
                .addComponent(btnComandas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

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
        btnOrdenar.setContentAreaFilled(false);
        btnOrdenar.setOpaque(true);
        btnOrdenar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrdenarActionPerformed(evt);
            }
        });

        btnAtras.setBackground(new java.awt.Color(44, 44, 44));
        btnAtras.setForeground(new java.awt.Color(255, 255, 255));
        btnAtras.setText("Atras");
        btnAtras.setContentAreaFilled(false);
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

        jScrollPane3.setBorder(null);
        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panContenedorProductos.setBackground(new java.awt.Color(255, 255, 255));
        panContenedorProductos.setMaximumSize(new java.awt.Dimension(612, 505));
        panContenedorProductos.setMinimumSize(new java.awt.Dimension(612, 505));

        javax.swing.GroupLayout panContenedorProductosLayout = new javax.swing.GroupLayout(panContenedorProductos);
        panContenedorProductos.setLayout(panContenedorProductosLayout);
        panContenedorProductosLayout.setHorizontalGroup(
            panContenedorProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 612, Short.MAX_VALUE)
        );
        panContenedorProductosLayout.setVerticalGroup(
            panContenedorProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 511, Short.MAX_VALUE)
        );

        jScrollPane3.setViewportView(panContenedorProductos);

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
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
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
                        .addGap(3, 3, 3)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(panBuscador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jScrollPane3))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
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
    private javax.swing.JButton btnOrdenar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JPanel panBuscador;
    private javax.swing.JPanel panContenedorProductos;
    private javax.swing.JPanel panEncabezado;
    private javax.swing.JPanel panMenu;
    private javax.swing.JPanel panProductosPedidos;
    private javax.swing.JTextField txtBuscador;
    // End of variables declaration//GEN-END:variables
}
