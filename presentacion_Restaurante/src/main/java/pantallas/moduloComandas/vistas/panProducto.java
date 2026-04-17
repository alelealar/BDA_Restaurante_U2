package pantallas.moduloComandas.vistas;

import controlador.CoordinadorModuloComandas;
import dtos.DetalleComandaDTO;
import dtos.ProductoDTO;
import dtos.ProductoIngredienteDTO;
import excepciones.NegocioException;
import java.awt.Image;
import java.net.URL;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import pantallas.moduloComandas.frmPedidos;

/**
 * Panel visual que representa un producto dentro de una comanda.
 *
 * Permite: - Visualizar información del producto - Aumentar/disminuir cantidad
 * - Eliminar el producto de la comanda
 *
 * Sincroniza los cambios con la base de datos mediante el coordinador.
 *
 * @author Brian Kaleb
 */
public class panProducto extends javax.swing.JPanel {

    private final DetalleComandaDTO detalle;
    private final CoordinadorModuloComandas coordinador;
    private frmPedidos frame;

    /**
     * Constructor del panel producto.
     *
     * @param detalle detalle asociado
     * @param coordinador coordinador del módulo
     */
    public panProducto(DetalleComandaDTO detalle, CoordinadorModuloComandas coordinador, frmPedidos frame) {
        initComponents();
        this.detalle = detalle;
        this.coordinador = coordinador;
        this.frame = frame;
        configurarPanel();
        cargarDatos();
        actualizarBotonDisminuir();
    }

    /**
     * Obtiene el detalle asociado al panel.
     */
    public DetalleComandaDTO getDetalle() {
        return detalle;
    }

    /**
     * Agrega una unidad al producto.
     */
    public void agregarOtraUnidad() {
        aumentarCantidad();
    }

    /**
     * Configura dimensiones del panel.
     */
    private void configurarPanel() {
        setPreferredSize(new java.awt.Dimension(334, 163));
        setMinimumSize(new java.awt.Dimension(334, 163));
        setMaximumSize(new java.awt.Dimension(334, 163));
    }

    /**
     * Carga los datos del producto en la interfaz.
     */
    private void cargarDatos() {
        try {
            ProductoDTO producto = coordinador.obtenerProducto(detalle.getIdProducto());

            cargarImagen(producto.getUrlImagen());
            lblNombre.setText(producto.getNombre());
            lblTipo.setText(producto.getTipo().name());
            lblPrecio.setText("$" + detalle.getPrecioUnitario());
            lblCantidad.setText(String.valueOf(detalle.getCantidad()));

        } catch (NegocioException ex) {
            mostrarError(ex.getMessage());
        }
    }

    /**
     * Carga la imagen del producto.
     */
    private void cargarImagen(String ruta) {
        URL url = getClass().getResource(ruta);

        if (url == null) {
            return;
        }

        ImageIcon icono = new ImageIcon(url);
        Image imagen = icono.getImage().getScaledInstance(112, 110, Image.SCALE_SMOOTH);

        lblImagen.setIcon(new ImageIcon(imagen));
        lblImagen.setText("");
    }

    /**
     * Aumenta la cantidad del producto.
     */
    private void aumentarCantidad() {
        try{
            ProductoDTO producto = coordinador.obtenerProducto(detalle.getIdProducto());
            int nuevaCantidad = detalle.getCantidad() + 1;
            List<ProductoIngredienteDTO> ingredientes = producto.getIngredientes();
            for(ProductoIngredienteDTO pi: ingredientes){
                int requerido = pi.getCantidad() * nuevaCantidad;
                if(pi.getIngrediente().getStockActual() < requerido){
                    JOptionPane.showMessageDialog( this, "No hay suficiente stock para aumentar este producto", "Sin stock", JOptionPane.WARNING_MESSAGE ); return;
                }
            }
            detalle.setCantidad(detalle.getCantidad() + 1);
            lblCantidad.setText(String.valueOf(detalle.getCantidad()));
            coordinador.actualizarTotal(detalle.getPrecioUnitario());
            actualizarBotonDisminuir();
        } catch (NegocioException ex) {
            mostrarError(ex.getMessage());
        }
    }

    /**
     * Disminuye la cantidad del producto.
     */
    private void disminuirCantidad() {
        if (detalle.getCantidad() <= 1) {
            return;
        }
        detalle.setCantidad(detalle.getCantidad() - 1);
        lblCantidad.setText(String.valueOf(detalle.getCantidad()));
        coordinador.actualizarTotal(-detalle.getPrecioUnitario());
        actualizarBotonDisminuir();
    }

    /**
     * Activa o desactiva el botón de disminuir.
     */
    private void actualizarBotonDisminuir() {
        boolean activo = detalle.getCantidad() > 1;

        btnDisminuir.setEnabled(activo);
        btnDisminuir.setOpaque(activo);
        btnDisminuir.setContentAreaFilled(activo);
        btnDisminuir.setBorderPainted(false);
    }

    /**
     * Muestra mensaje de error.
     */
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblImagen = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblTipo = new javax.swing.JLabel();
        lblPrecio = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        btnIncrementar = new javax.swing.JButton();
        btnDisminuir = new javax.swing.JButton();
        lblCantidad = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        lblImagen.setText("jLabel1");

        lblNombre.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblNombre.setForeground(new java.awt.Color(0, 0, 0));
        lblNombre.setText("jLabel2");

        lblTipo.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblTipo.setForeground(new java.awt.Color(0, 0, 0));
        lblTipo.setText("jLabel2");

        lblPrecio.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblPrecio.setForeground(new java.awt.Color(0, 0, 0));
        lblPrecio.setText("jLabel2");

        btnCancelar.setBackground(new java.awt.Color(255, 0, 0));
        btnCancelar.setForeground(new java.awt.Color(0, 0, 0));
        btnCancelar.setText("Cancelar");
        btnCancelar.setBorderPainted(false);
        btnCancelar.setContentAreaFilled(false);
        btnCancelar.setOpaque(true);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnIncrementar.setText("+");
        btnIncrementar.setContentAreaFilled(false);
        btnIncrementar.setOpaque(true);
        btnIncrementar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIncrementarActionPerformed(evt);
            }
        });

        btnDisminuir.setText("-");
        btnDisminuir.setContentAreaFilled(false);
        btnDisminuir.setOpaque(true);
        btnDisminuir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisminuirActionPerformed(evt);
            }
        });

        lblCantidad.setForeground(new java.awt.Color(0, 0, 0));
        lblCantidad.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCantidad.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNombre)
                    .addComponent(lblTipo)
                    .addComponent(lblPrecio))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(119, 119, 119)
                .addComponent(btnCancelar)
                .addGap(28, 28, 28)
                .addComponent(btnDisminuir)
                .addGap(18, 18, 18)
                .addComponent(lblCantidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(23, 23, 23)
                .addComponent(btnIncrementar)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblNombre)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTipo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblPrecio))
                    .addComponent(lblImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnIncrementar)
                    .addComponent(btnDisminuir)
                    .addComponent(lblCantidad))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnIncrementarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncrementarActionPerformed
        aumentarCantidad();
    }//GEN-LAST:event_btnIncrementarActionPerformed

    private void btnDisminuirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisminuirActionPerformed

        disminuirCantidad();
    }//GEN-LAST:event_btnDisminuirActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        frame.eliminarDetalleTemporal(detalle);
    }//GEN-LAST:event_btnCancelarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnDisminuir;
    private javax.swing.JButton btnIncrementar;
    private javax.swing.JLabel lblCantidad;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JLabel lblTipo;
    // End of variables declaration//GEN-END:variables
}
