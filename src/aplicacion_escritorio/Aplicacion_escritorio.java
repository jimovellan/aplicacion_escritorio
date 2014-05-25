/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion_escritorio;

import com.sun.java.swing.plaf.motif.MotifLookAndFeel;
import com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicLookAndFeel;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.multi.MultiLookAndFeel;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.plaf.synth.SynthLookAndFeel;

/**
 *
 * @author Casa
 */
public class Aplicacion_escritorio {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
         
         try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
            new Principal();
        } catch (Exception ex) {
            Logger.getLogger(Aplicacion_escritorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
