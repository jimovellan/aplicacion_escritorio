package aplicacion_escritorio;


import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.table.DefaultTableModel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Casa
 */
public class Conectar_datos {
     Statement stmt=null;
     Connection con=null;
     private JInternalFrame VentanaInterna;
     private JDesktopPane JDpanel;
     private String url;
     String driver="";
     String login="";
     String pass="";
     String tipo;
     
    public Conectar_datos(JInternalFrame padre,String url)
    {
        String tipo="";
        this.VentanaInterna=padre;
        this.url=url;
        
    } 
    public Conectar_datos(JInternalFrame padre, String driver, String url,String login,String pass)
    {
        this(padre,url);
        this.url=url;
        this.login=login;
        this.pass=pass;
        this.driver=driver;
    }  
    public Conectar_datos(JDesktopPane padre, String driver, String url,String login,String pass)
    {
        this.JDpanel=padre;
        this.url=url;
        this.login=login;
        this.pass=pass;
        this.driver=driver;
    }  
    public void mensaje(String mensaje)
    {
        if(VentanaInterna==null)
        {
             javax.swing.JOptionPane. showMessageDialog (VentanaInterna, mensaje);
        }
        else
        {
             javax.swing.JOptionPane. showMessageDialog (VentanaInterna, mensaje);
        }
    } 
  public void subir_imagen(String sql,File f)
   {
    
    FileInputStream fis = null;
    PreparedStatement ps = null;
    try {
        con.setAutoCommit(false);
        fis = new FileInputStream(f);
        ps = con.prepareStatement(sql);
        ps.setBinaryStream(1,fis,(int)f.length());
        
        ps.executeUpdate();
        con.commit();
        
    } catch (Exception ex) {
        System.out.println("error imagen");//Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
    }finally{
        try {
            ps.close();
            fis.close();
        } catch (Exception ex) {
            System.out.println("error imagen"); // Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }        
    
       
   }       
   public void cerrar()
   {
       
        try {
            if(con!=null)
            {
                
                    con.close();
                    System.out.println("conectado");
            }    
             } catch (SQLException ex) {
                    mensaje ("no se puede cerrar conexion con base de datos");
                     System.out.println("no puede conectar");
                }
   }
    public ResultSet consulta(String sentencia_sql) 
    {
        this.tipo=tipo;
         try {
             if (con.isClosed())conectar();
        
        
           ResultSet rs=stmt.executeQuery(sentencia_sql);
            
           return rs;
         } catch (Exception ex) {
             Logger.getLogger(Conectar_datos.class.getName()).log(Level.SEVERE, null, ex);
              mensaje( ex.toString());
         }
         return null;
    }
    
    public void actualizar(String sentencia_sql, String tipo) throws SQLException
    {
        String mensaje="";
        switch (tipo) {
            case "Añadir":
                mensaje="Cliente añadido correctamente";
                break;
            case "Eliminar":
                mensaje="Cliente eliminado Correctamente";
                break;
            case "Actualizar":
                mensaje="Cliente actualizado correctamente";
                break;
        }
        if (con.isClosed())conectar();
          try {
          stmt.executeUpdate(sentencia_sql);
          //stmt.close();
          mensaje(mensaje); 
              System.out.println(sentencia_sql); 
        } catch (SQLException ex) {
            
              switch (ex.getErrorCode()) {
                  case 1062:
                      mensaje("Ha introducido un DNI existente en la base de datos");
                      break;
                  
              }
           mensaje(ex.toString());
           
        }
        finally
        {
           
                
            
        }
    }
    public void conectar()
    {
        Avisos aviso= new Avisos("Cargando base de datos...............",JDpanel,"cargando");
        try {
         
         
        
          try {
             
              Class.forName(driver);
              System.out.println("driver cargado");
          } catch (ClassNotFoundException ex) {
              System.out.println("error al cargar driver mysql "+ ex);
          }
          //String url="jdbc:mysql://192.168.230.128:3306/test";
          //String url="jdbc:mysql://mysql.hostinger.es:3306/u652843817_proye";
          
          try {
               //con=DriverManager.getConnection(url);
              con=DriverManager.getConnection(url,login,pass);
              System.out.println("conectado a base de datos");
          } catch (SQLException ex) {
              mensaje(ex.toString());
              System.out.println(ex);
              
          }
          
                 stmt =con.createStatement();
                 System.out.println("conectado");
                 
     } catch (SQLException ex) {
             mensaje(ex.toString());
         
     }
     finally{
            aviso.settitulo("Base de datos cargada");
            aviso.cerrar();
        }
            
    
    
    

 
    }       
    
    
    
}
