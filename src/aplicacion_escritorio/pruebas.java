/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aplicacion_escritorio;

import com.github.sarxos.webcam.Webcam;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author nacho
 */
public class pruebas {
    static public void main(String[] arg) throws IOException
    {
        Webcam webcam = Webcam.getDefault();
        webcam.open();
//ImageIO.write(webcam.getImage(), "PNG", new File("hello-world.png"));
    }       
    
}
