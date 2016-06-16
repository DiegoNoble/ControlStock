/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import com.Beans.Pedido;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author dnoble
 */
public class DropListener implements DropTargetListener {

    private DropTarget dt;
    private JInternalFrame frame;
    private File archivo;

    public DropListener(JInternalFrame frame) {
        this.frame = frame;
        dt = new DropTarget(frame, this);
    }

    @Override
    public void dragEnter(DropTargetDragEvent dtde) {
    }

    @Override
    public void dragExit(DropTargetEvent dte) {
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) {
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {
    }

    @Override
    public void drop(DropTargetDropEvent dtde) {
        try {
            /* proporciona datos para operaciones de transferencia en swing */
            Transferable tr = dtde.getTransferable();
            /* Devuelve una array de objetos DataFlavor */
            DataFlavor[] flavors = tr.getTransferDataFlavors();
            if (flavors.length > 0) {
                /* Si existe una lista de objetos de archivo */
                if (flavors[0].isFlavorJavaFileListType()) {
                    dtde.acceptDrop(DnDConstants.ACTION_COPY);
                    /* obtiene un List con los archivos arrastrados al componente */
                    java.util.List list = (java.util.List) tr.getTransferData(flavors[0]);
                    if (!list.isEmpty()) { /* abre el primer archivo */

                        File file = new File(list.get(0).toString());
                        if (file.exists()) {
                            /* SI el archivo corresponde a un archivo excel */
                            if (file.getName().endsWith("xml")) {
                               // this.setArchivo(file);
                                //JOptionPane.showMessageDialog(null, "Archivo correcto", "Error", JOptionPane.ERROR_MESSAGE);
//readXLS(file);
                            } else {
                                JOptionPane.showMessageDialog(null, "No es un archivo *.xml valido", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            System.err.println("error archivo no existe ");
                        }
                    }
                    dtde.dropComplete(true);
                    return;
                }
            }
            System.err.println("Drop failed: " + dtde);
            dtde.rejectDrop();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            dtde.rejectDrop();
        }
    }

    public DropTarget getDropTarget() {
        return this.dt;
    }

   

}
