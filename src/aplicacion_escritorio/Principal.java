package aplicacion_escritorio;


import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.EventListener;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import org.w3c.dom.events.MouseEvent;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nacho
 */
public class Principal extends JFrame {
    
   JMenuBar menu;
   JDesktopPane padre;
   static  String driver="com.mysql.jdbc.Driver";
   //static  String login="nacho";
   //static  String pass="16601225d";
   static String url="jdbc:mysql://172.245.214.34:3306/nacho_gestion";
   //static String url="jdbc:mysql://192.168.1.128:3306/test";
   //static  String driver="com.mysql.jdbc.Driver";
  
   //static  String driver="connect.microsoft.MicrosoftDriver";
   static  String login="nacho";
   static  String pass="16601225d";
   //static String url="jdbc:sqlserver://localhost;databaseName=GESTION";
   //static String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
   Conectar_datos conector;
    public Principal() throws IOException 
    {
        //cargar_tema();
        menu_creador menuPrincipal =new menu_creador("menu_principal.txt");
       
        menu = menuPrincipal.menu;
        añadir_menu(menuPrincipal.elementos);
        setJMenuBar(menu);
        setSize(1000, 1000);
        setLocation(new Point(300,100));
        setVisible(true);
        padre= new JDesktopPane();
        add(padre);
        conector = new Conectar_datos(padre, driver, url, login,pass);
        conector.conectar();
        addKeyListener(new Escucha_Eventos());
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                conector.cerrar();
                System.exit(0);
                
            }
            
});
            
    }  
   public void cargar_tema()
    {
         try {
           UIManager.setLookAndFeel(new NimbusLookAndFeel());
           new Principal();
        } catch (Exception ex) {
            Logger.getLogger(Aplicacion_escritorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }       
    public void añadir_menu(Vector items)
    {
      ActionListener escucha_menu= new ActionListener() {
          
          @Override
          public void actionPerformed(ActionEvent e) {
              switch (((JMenuItem)e.getSource()).getText()) {
                  case "Salir":
                      System.exit(0);
                      break;
                  case "Clientes":
              
                  Myventana hija= new Clientes("Clientes", padre,conector);
                  break;    
                      
                 
           
                 
              }
              
          }
      };
        for(int i=0;i<items.size();i++)
        {
           if(items.get(i) instanceof JMenuItem)
           {
               ((JMenuItem)items.get(i)).addActionListener(escucha_menu);
               
           }
            
        }
    }
    
  
  

   
   
    

   
   

  
   
   
   
    
}
