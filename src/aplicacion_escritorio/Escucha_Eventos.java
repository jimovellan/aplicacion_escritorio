/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aplicacion_escritorio;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.xml.bind.Marshaller.Listener;

/**
 *
 * @author nacho
 */
public class Escucha_Eventos implements KeyListener{

    @Override
    public void keyTyped(KeyEvent ke) {
        System.out.println("tecla typed");
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        System.out.println("tecla pressed");
    }

    @Override
    public void keyReleased(KeyEvent ke) {
          System.out.println("tecla released");
    }
    
    
}
