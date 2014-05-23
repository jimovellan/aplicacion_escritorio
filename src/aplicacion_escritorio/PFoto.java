/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion_escritorio;


import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author nacho
 */
public class PFoto implements DropTargetListener{
    private DropTarget dt;
    private JLabel label;
    private ImageIcon icono;
    private File f;
    private boolean arrastrable;
    public PFoto(JLabel label)
    {
     this.label=label;   
     dt = new DropTarget(label, this);
     arrastrable=false;
    }        
            
    public void no_arrastrable()
    {
        arrastrable=false;
    }   
    public void arrastrable()
    {
        arrastrable=true;
    }
    @Override
    public void dragEnter(DropTargetDragEvent dtde) {
        System.out.println("1");
    }
public File devolver_ruta_imagen()
{
    return f;
}            
    @Override
    public void dragOver(DropTargetDragEvent dtde) {
    if (!isDragAcceptable(dtde)) {
            dtde.rejectDrag();
        }
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {
        System.out.println("3");
    }

    @Override
    public void dragExit(DropTargetEvent dte) {
       System.out.println("4");
    }

    @Override
    public void drop(DropTargetDropEvent dtde) {
         if (!isDropAcceptable(dtde)) {
            dtde.rejectDrop();
            return;
        }
        if(arrastrable)
        {    
        dtde.acceptDrop(DnDConstants.ACTION_COPY);

        Transferable transferable = dtde.getTransferable();
        DataFlavor[] flavors = transferable.getTransferDataFlavors();
        DataFlavor d = flavors[0];
        try {
            if (d.equals(DataFlavor.javaFileListFlavor)) {
                List<File> fileList = (List<File>) transferable.getTransferData(d);
                String ruta = fileList.get(0).toString();
                this.f= new File(ruta);
                System.out.println("ruta " + ruta);
                icono = new ImageIcon(ruta);
                label.setIcon(icono);
                int x = label.getIcon().getIconWidth();
                int y = label.getIcon().getIconHeight();
                label.setPreferredSize(new Dimension(x , y ));

            } else if (d.equals(DataFlavor.imageFlavor)) {
                String s = (String) transferable.getTransferData(d);
                System.out.println("si es imagen");
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            
        }
        dtde.dropComplete(true);
        }
    }
     public boolean isDragAcceptable(DropTargetDragEvent event) {
       
        
        return (event.getDropAction() & DnDConstants.ACTION_COPY_OR_MOVE) != 0;
    }

    public boolean isDropAcceptable(DropTargetDropEvent event) {
        
        return (event.getDropAction() & DnDConstants.ACTION_COPY_OR_MOVE) != 0;
    }
    
}
