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
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.EventListener;
import java.util.Hashtable;
import java.util.StringTokenizer;
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
    
   private Hashtable<String,String> configuracion;
   JMenuBar menu;
   JDesktopPane padre;
   Clientes hija;
   static  String driver="com.mysql.jdbc.Driver";
   private String basesdedatos;
   private String puerto;
   private String ip;
   //static  String login="nacho";
   //static  String pass="16601225d";
   //static String url="jdbc:mysql://192.168.1.138:3306/Basededatos";
    private  String login;
   private  String pass;
   //static String url="jdbc:mysql://192.168.1.128:3306/test";
   //static  String driver="com.mysql.jdbc.Driver";
 
   //static  String driver="connect.microsoft.MicrosoftDriver";
    //static String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
   
   /*conexion servidor propio*/
   private String url;
   //static  String login="nacho";
   //static  String pass="16601225d";
   
     //static String url="jdbc:mysql://192.168.1.128:3306/test";
   //static String url="jdbc:sqlserver://localhost;databaseName=GESTION";
   //static String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
   Conectar_datos conector;
   
    public Principal() throws IOException 
    {
        //cargar_tema();
        
        menu_creador menuPrincipal =new menu_creador("menu_principal.txt");
        leer_fichero();
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
     public void leer_fichero() throws IOException 
    {
        configuracion= new Hashtable();
        String dato="";
        try {
            FileReader fr= new FileReader("configuracion.txt");
            BufferedReader br = new BufferedReader(fr);
            String linea= "";
            
            while((linea=br.readLine())!=null)
            { 
                linea=linea.replace(" ", "");
                
                StringTokenizer st=new StringTokenizer(linea,"=");
                linea=st.nextToken();
                if (st.countTokens()==0) dato="";
                else dato=st.nextToken();
                    
                    
               configuracion.put(linea, dato);
    
               
            }    
            System.out.println(configuracion.get("contraseña"));
            System.out.println(configuracion.get("usuario"));
            System.out.println(configuracion.get("driver"));
            System.out.println(configuracion.get("basededatos"));
        } catch (FileNotFoundException ex) {
            System.out.println("error lectura de fichero menu_creador.leer_fichero");
        }
        construir_datos_conexion();
     
    } 
     public void construir_datos_conexion()
     {
         login=configuracion.get("usuario");
         pass=configuracion.get("contraseña");
         basesdedatos=configuracion.get("basededatos");
         puerto=configuracion.get("puerto");
         driver=configuracion.get("driver");
         ip=configuracion.get("servidor");
         if(driver.equals("com.mysql.jdbc.Driver"))
         {    
           this.url="jdbc:mysql://"+ip+"";
           if (puerto=="") this.url=this.url+":"+puerto+"/";      
           else this.url=this.url+"/";
           this.url=this.url+basesdedatos;
         } 
                  
         System.out.println(url);
   //static  String login="nacho";
   //static  String pass="16601225d";
   
     //static String url="jdbc:mysql://192.168.1.128:3306/test";
   //static String url="jdbc:sqlserver://localhost;databaseName=GESTION";
         
         
     }        
    
    public void conexion_predeterminada()
    {
        conector.datos_nueva_conexion(driver, url, login, pass);
    }        
    public Conectar_datos get_conexion()
    {
        return conector;
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
              
                   hija= new Clientes("Clientes", padre,conector);
                  break;    
                  case "Base de datos":
                  new Conexiones("Base de datos", padre,conector);
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
    
  public void cerrar_ventana()
  {
      hija.cerrar();
  }        
  

   
   
    

   
   

  
   
   
   
    
}
