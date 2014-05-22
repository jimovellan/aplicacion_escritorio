package aplicacion_escritorio;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.*;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import java.sql.*;
/**
 *
 * @author Casa
 */
public class Myventana extends JInternalFrame{
static int openFrameCount = 0;
static final int xOffset = 30, yOffset = 30;
String driver;
protected JDesktopPane padre;
protected String titulo;
public Myventana(String titulo, JDesktopPane padre)
 {
       
    super(titulo, true,true,true,true);
    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    padre.add(this);
    setLocation(30,30);    
    setSize(400, 400);
     setVisible(true);
     this.titulo=titulo;
    this.padre=padre;
    
     
 }
    

}
