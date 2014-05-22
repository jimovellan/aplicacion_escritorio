package aplicacion_escritorio;
import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author juanks
 */
public class EventoSoltarImagen implements DropTargetListener {

    private JLabel campo;
    private ImageIcon icono;
   

    public EventoSoltarImagen(JLabel aTextField) {
        campo = aTextField;
    }

    public void dragEnter(DropTargetDragEvent event) {
        if (!isDragAcceptable(event)) {
            event.rejectDrag();
        }
    }

    public void dragExit(DropTargetEvent event) {
    }

    public void dragOver(DropTargetDragEvent event) {
    }

    public void dropActionChanged(DropTargetDragEvent event) {
        if (!isDragAcceptable(event)) {
            event.rejectDrag();
        }
    }

    @Override
    public void drop(DropTargetDropEvent event) {
        if (!isDropAcceptable(event)) {
            event.rejectDrop();
            return;
        }
        event.acceptDrop(DnDConstants.ACTION_COPY);

        Transferable transferable = event.getTransferable();
        DataFlavor[] flavors = transferable.getTransferDataFlavors();
        DataFlavor d = flavors[0];
        try {
            if (d.equals(DataFlavor.javaFileListFlavor)) {
                List<File> fileList = (List<File>) transferable.getTransferData(d);
                String ruta = fileList.get(0).toString();
                System.out.println("ruta " + ruta);
                icono = new ImageIcon(ruta);
                campo.setIcon(icono);
                int x = campo.getIcon().getIconWidth();
                int y = campo.getIcon().getIconHeight();
                campo.setPreferredSize(new Dimension(x + 10, y + 20));

            } else if (d.equals(DataFlavor.imageFlavor)) {
                String s = (String) transferable.getTransferData(d);
                System.out.println("si es imagen");
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            campo.setText(e.toString());
        }
        event.dropComplete(true);
    }

    public boolean isDragAcceptable(DropTargetDragEvent event) {
        return (event.getDropAction() & DnDConstants.ACTION_COPY_OR_MOVE) != 0;
    }

    public boolean isDropAcceptable(DropTargetDropEvent event) {
        return (event.getDropAction() & DnDConstants.ACTION_COPY_OR_MOVE) != 0;
    }
}
