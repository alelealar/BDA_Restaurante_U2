/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reportes;

import java.awt.Frame;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.SwingWorker;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import notificaciones.DlgNotificacion;
import notificaciones.TipoNotificacion;

/**
 *
 * @author Alejandra Leal Armenta - 262719
 * @author Brian Kaleb Sandoval Rodriguez - 262741
 * @author María José Valdez Iglesias - 262775
 */
public class GenerarReportes {

    public static void generarReporte(String rutaReporte, Map<String, Object> parametros, List<?> datos, JButton llamador, Frame frame) {
        if (datos == null || datos.isEmpty()) {
            DlgNotificacion.mostrarNotificacion(frame, "No hay datos para mostrar en el reporte.", TipoNotificacion.MENSAJE);
            return;
        }
        /*
        Esto es un hilo en segundo plano ya que se puede dar que al momento de empezar
        a generar el reporte y estar consultando y extrayendo la info de la BD este 
        proceso sea largo y para que el usuario pueda seguir trabajando sin que se 
        congele el sistema hasta no terminar la generación del reporte.
         */
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            String textoOriginalBoton = llamador.getText();
            @Override
            protected Void doInBackground() throws Exception {
                llamador.setEnabled(false);
                llamador.setText("Generando Reporte...");
                try {
                    JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(datos);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(rutaReporte, parametros, dataSource);
                    JasperViewer.viewReport(jasperPrint, false);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    DlgNotificacion.mostrarNotificacion(frame, "Error al generar el reporte: " + ex.getMessage(), TipoNotificacion.ERROR);
                }
                return null;
            }
            @Override
            protected void done() {
                llamador.setEnabled(true);
                llamador.setText(textoOriginalBoton);
            }
        };
        worker.execute();
    }

}
