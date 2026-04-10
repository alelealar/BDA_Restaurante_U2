package pantallas.moduloComandas;

import controlador.CoordinadorModuloComandas;
import dtos.ClienteDTO;
import dtos.ComandaDTO;
import dtos.MesaDTO;
import enumerators.EstadoComandaDTO;
import excepciones.NegocioException;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

/**
 *
 * @author Kaleb
 */
public class frmComandas extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(frmComandas.class.getName());

    private boolean creada = false;

    private CoordinadorModuloComandas coordinador;

    private ClienteDTO cliente;

    private ComandaDTO comanda;

    private MesaDTO mesa;

    /**
     * Creates new form frmMesas
     *
     * @param coordinador
     */
    public frmComandas(CoordinadorModuloComandas coordinador, MesaDTO mesa) {
        initComponents();
        this.coordinador = coordinador;
        this.mesa = mesa;
        cargarEstado();

    }

    public void cargarEstado() {
        try {
            this.creada = false;
            List<ComandaDTO> comandasRegistradas = coordinador.obtenerComandas();

            for (ComandaDTO c : comandasRegistradas) {
                if (c.getMesa() != null && c.getEstadoComanda() != null) {

                    if (c.getMesa().getId().equals(mesa.getId()) && c.getEstadoComanda().equals(EstadoComandaDTO.ABIERTA)) {
                        this.creada = true;
                        this.comanda = c;
                        break; // Detenemos la búsqueda porque ya la encontramos
                    }
                }
            }
        } catch (NegocioException ex) {
            System.getLogger(frmComandas.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        if (creada) {
            btnCrear.setText("+Crear Pedido");
            jblAviso.setVisible(false);
        } else {
            btnCrear.setText("+Crear Comanda");
            jblAviso.setVisible(true);
        }
        cargarComanda();
    }

    public void cargarComanda() {

    }

    public void agregarPedidos() {

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
        panContenedorPedidos = new javax.swing.JPanel();
        jblAviso = new javax.swing.JLabel();
        btnCrear = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setMinimumSize(new java.awt.Dimension(1029, 656));
        setResizable(false);

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
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        btnMesas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMesasMouseClicked(evt);
            }
        });

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

        panContenedorPedidos.setBackground(new java.awt.Color(255, 255, 255));

        jblAviso.setFont(new java.awt.Font("Verdana", 1, 36)); // NOI18N
        jblAviso.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jblAviso.setText("Sin comanda registrada");
        panContenedorPedidos.add(jblAviso);

        jScrollPane1.setViewportView(panContenedorPedidos);

        btnCrear.setBackground(new java.awt.Color(181, 245, 255));
        btnCrear.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        btnCrear.setForeground(new java.awt.Color(74, 68, 89));
        btnCrear.setText("+Crear Comanda");
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });

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
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCrear))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 805, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panEncabezado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCrear)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE))))
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

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed

        if (!creada) {
            int seleccion = JOptionPane.showConfirmDialog(null, "¿Desea abrir la comanda?", "Confirmar", JOptionPane.OK_CANCEL_OPTION);

            if (seleccion == JOptionPane.OK_OPTION) {
                ComandaDTO comanda = new ComandaDTO();
                comanda.setMesa(mesa);
                try {
                    coordinador.registrarComanda(comanda);
                } catch (NegocioException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
                this.comanda = comanda;
                creada = true;
                cargarEstado();
            }
        } else {
            final int[] eleccion = {-1};

            JButton boton1 = new JButton("Registrado");
            boton1.setBackground(Color.decode("#FFAE0B"));
            boton1.setForeground(Color.decode("#2C2C2C"));
            boton1.setOpaque(true);
            boton1.setContentAreaFilled(true);
            boton1.setBorderPainted(false);

            JButton boton2 = new JButton("Sin Registro");
            boton2.setBackground(Color.decode("#2C2C2C"));
            boton2.setForeground(Color.decode("#F5F5F5"));
            boton2.setOpaque(true);
            boton2.setContentAreaFilled(true);
            boton2.setBorderPainted(false);

            Object[] opciones = {boton1, boton2};
            JLabel mensajeCentrado = new JLabel("Seleccionar cliente", SwingConstants.CENTER);
            mensajeCentrado.setFont(new Font("Arial", Font.PLAIN, 14));

            JOptionPane pane = new JOptionPane(
                    mensajeCentrado,
                    JOptionPane.PLAIN_MESSAGE,
                    JOptionPane.DEFAULT_OPTION,
                    null,
                    opciones,
                    opciones[0]
            );

            JDialog dialog = pane.createDialog("Tipo de Cliente");

            BufferedImage imagenVacia = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            dialog.setIconImage(imagenVacia);

            boton1.addActionListener(e -> {
                eleccion[0] = 0;
                dialog.dispose();
            });

            boton2.addActionListener(e -> {
                eleccion[0] = 1;
                dialog.dispose();
            });

            dialog.setSize(350, 150);

            dialog.setLocationRelativeTo(null);

            dialog.setVisible(true);

            switch (eleccion[0]) {
                case 0:

                    break;
                case 1:
                    coordinador.mostrarPantallaCrearPedido(mesa);
                    this.comanda.setCliente(cliente);
                    break;
                default:
                    break;
            }
            cargarEstado();
        }


    }//GEN-LAST:event_btnCrearActionPerformed

    private void btnMesasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMesasMouseClicked
        coordinador.mostrarPantallaMesas();
    }//GEN-LAST:event_btnMesasMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnComandas;
    private javax.swing.JButton btnCrear;
    private javax.swing.JPanel btnMesas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jblAviso;
    private javax.swing.JPanel panContenedorPedidos;
    private javax.swing.JPanel panEncabezado;
    private javax.swing.JPanel panMenu;
    // End of variables declaration//GEN-END:variables
}
