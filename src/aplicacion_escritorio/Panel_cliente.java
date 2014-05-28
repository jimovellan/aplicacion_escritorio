package aplicacion_escritorio;



import java.awt.Color;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTarget;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DropMode;
import javax.swing.JTabbedPane;
import javax.swing.TransferHandler;
import java.awt.dnd.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nacho
 */
public class Panel_cliente extends javax.swing.JPanel {
    String accion;
    JTabbedPane pestaña;
    Conectar_datos conector;
     String dni;
       String nombre;
       String apellido;
       String telefono;
       String provincia;
       String localidad;
       String cp;
       String direccion;
       String email;
       Panel_cliente pc;
       String id;
       String id_cliente;
       Color color;
       PFoto evento_arrastrar;
    public Panel_cliente(JTabbedPane pestaña, Conectar_datos conector) {
        this.pestaña=pestaña;
        initComponents();
        this.conector=conector;   
        evento_arrastrar= new PFoto(imagen);
        System.out.println(""+imagen.getIcon());    
   }
    public Panel_cliente(JTabbedPane pestaña, Conectar_datos conector, String id) {
        this(pestaña,conector);
        this.id_cliente=id;
        cargar_casillas(id_cliente);
        bloquear_cajas();
        boton_editar.setEnabled(true);
        boton_eliminar.setEnabled(true);
    }
     public Panel_cliente(JTabbedPane pestaña, Conectar_datos conector, Panel_cliente pc) {
         this(pestaña,conector);
         this.pc=pc;
          bloquear_cajas();
          boton_aceptar.setEnabled(true);
          boton_cancelar.setEnabled(true);
          desbloquear_cajas();
          accion="Añadir";       
     }
     public void modificar() throws SQLException 
    {
        if(datos())
       {  
       int resul=javax.swing.JOptionPane.showConfirmDialog(this, "¿Esta seguro de guardar los cambios?");
         
        if (resul==0)
        {   
            Blob foto=null;
            String sentencia_sql="";
            sentencia_sql="UPDATE  cliente set nombre='"+nombre+"', apellido='"+apellido+"', telefono='"+telefono+"', provincia='"+provincia+"', localidad='"+localidad
                     +"', direccion='"+direccion+"', email='"+email+"', cp='"+cp+"', foto = null where id='"+id_cliente+"';"; 
            System.out.println(sentencia_sql);
             conector.actualizar(sentencia_sql,"Actualizar");
             botones_despues_de_editar();
        }
       } 
    }      
     public void botones_despues_de_editar()
     {
                bloquear_botones();
             boton_editar.setEnabled(true);
             boton_eliminar.setEnabled(true);
             bloquear_cajas();
             boton_quitar_foto.setEnabled(false);
     }        
    public void añadir() throws SQLException
    {
        if (datos())
                {    
         File f=evento_arrastrar.devolver_ruta_imagen(); /* File de la ruta del icono del label donde esta la imagen "imagen"*/
         FileInputStream fis= null;
         if(imagen.getIcon()!=null)
         {    
            try {
                fis= new FileInputStream(f);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Panel_cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
         }   
        int resul=javax.swing.JOptionPane.showConfirmDialog(this, "Esta seguro de crear el Cliente");
        if (resul==0)
        {    
          
           
            String sentencia_sql="";
            switch (accion) {
                case "Añadir":
                    sentencia_sql="INSERT INTO cliente (dni, nombre, apellido, telefono, provincia,"
                            + " localidad, cp, direccion, email, foto) VALUES  ('" + Text_dni.getText()+"','"
                            + nombre+"','"+ apellido+"','"+ telefono
                            +"','"+ provincia+"','"
                            + localidad+"',"+ cp+",'"+ direccion+"'," + "'"+email+"'";
                    if(imagen.getIcon()!=null)
                    {    
                        sentencia_sql=sentencia_sql +", ?);";
                        conector.subir_imagen(sentencia_sql, f);
                    }
                    else
                    {    
                        sentencia_sql=sentencia_sql + ", null)";
                        conector.actualizar(sentencia_sql,"Añadir");
                        
                    }
                    borrar_contenido_cajas();
                    break;
                case "Editar":
                     if(imagen.getIcon()!=null)
                    {    
                        if(f!=null)
                        {    
                         sentencia_sql="UPDATE  cliente set nombre='"+nombre+"', apellido='"+apellido+"', telefono='"+telefono+"', provincia='"+provincia+"', localidad='"+localidad
                     +"', direccion='"+direccion+"', email='"+email+"', cp='"+cp+"', foto = ? where id='"+id_cliente+"';";
                        
                        conector.subir_imagen(sentencia_sql, f);
                        }
                        else
                        {
                            modificar();
                        }    
                    }
                    else
                    {    
                      modificar();
                    }
                   botones_despues_de_editar();
                     
                    break;
            }        
        }
       
        Text_dni.requestFocus();
        boton_eliminar.setEnabled(false);
       } 
    } 
public void devolver_id_cliente()
{
    
}        
    
 public void guardar_datos()
 {
     id_cliente=Text_id.getText();
      dni=Text_dni.getText();
      nombre=Text_nombre.getText();
      apellido=Text_apellido.getText();
      telefono=Text_telefono.getText();
      provincia=Text_provincia.getText();
      localidad=Text_localidad.getText();
      cp=text_cp.getText();
      direccion=Text_direccion.getText();
      email=Text_mail.getText();
 }        
 public void devolver_datos()
 {
     Text_id.setText(id_cliente);
     Text_dni.setText(dni);
     Text_nombre.setText(nombre);
     Text_apellido.setText(apellido);
     Text_telefono.setText(telefono);
     Text_provincia.setText(provincia);
     Text_localidad.setText(localidad);
     text_cp.setText(cp);
     Text_direccion.setText(direccion);
     Text_mail.setText(email);
 }
public void cargar_casillas(String id)
{
    String sentencia_sql="Select * FROM cliente WHERE Ltrim(Rtrim(id)) = '"+id_cliente+"'";
 
       try {
                    
                    Image im= null;
                    
                    ResultSet rst= conector.consulta(sentencia_sql);        
                    rst.next();
                    /*añadir imagen*/
                    Blob blob= rst.getBlob("foto");
                    if (blob!=null)
                    {    
                    byte[] datos= blob.getBytes(1, (int)blob.length());
                    BufferedImage img=null;
                    try {
                        Image imagenparaicono = ImageIO.read(new ByteArrayInputStream(datos));
                        ImageIcon ico1=(new ImageIcon(imagenparaicono));
                        imagen.setIcon(ico1);
                        
                    } catch (IOException ex) {
                        Logger.getLogger(Panel_cliente.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    }
                    /* fin añadir imagen*/
                    Text_id.setText(rst.getString("id"));
                    Text_dni.setText(rst.getString("dni"));
                    Text_nombre.setText(rst.getString("nombre"));
                    Text_apellido.setText(rst.getString("apellido"));
                    Text_telefono.setText(rst.getString("telefono"));
                    Text_localidad.setText(rst.getString("localidad"));
                    Text_provincia.setText(rst.getString("provincia"));
                    text_cp.setText(rst.getString("cp"));
                    Text_direccion.setText(rst.getString("direccion"));
                    Text_mail.setText(rst.getString("email"));
                    
                    //rst.close();
       } catch (SQLException ex) {
           System.out.println("error consulta sql");
           System.out.println(ex);
       }
       guardar_datos();
                    
                        
     
           
}        
private void cerrar_pestaña()
{
     borrar_contenido_cajas();
     pestaña.removeTabAt(pestaña.getSelectedIndex());
}        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton2 = new javax.swing.JButton();
        Text_telefono = new javax.swing.JTextField();
        text_cp = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        Text_apellido = new javax.swing.JTextField();
        Text_provincia = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        Text_nombre = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        Text_mail = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Text_dni = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        Text_direccion = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        Text_localidad = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        boton_eliminar = new javax.swing.JButton();
        boton_editar = new javax.swing.JButton();
        boton_aceptar = new javax.swing.JButton();
        boton_cancelar = new javax.swing.JButton();
        imagen = new javax.swing.JLabel();
        boton_quitar_foto = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        Text_id = new javax.swing.JTextField();

        jButton2.setText("jButton2");

        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                formKeyTyped(evt);
            }
        });

        Text_telefono.setFont(new java.awt.Font("VTC Letterer Pro", 0, 14)); // NOI18N
        Text_telefono.setNextFocusableComponent(Text_provincia);
        Text_telefono.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Text_telefonoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                Text_telefonoFocusLost(evt);
            }
        });
        Text_telefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Text_telefonoKeyPressed(evt);
            }
        });

        text_cp.setFont(new java.awt.Font("VTC Letterer Pro", 0, 14)); // NOI18N
        text_cp.setNextFocusableComponent(Text_direccion);
        text_cp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                text_cpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                text_cpFocusLost(evt);
            }
        });
        text_cp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_cpKeyPressed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("VTC Letterer Pro", 1, 14)); // NOI18N
        jLabel2.setText("Apellido");

        jLabel9.setFont(new java.awt.Font("VTC Letterer Pro", 1, 14)); // NOI18N
        jLabel9.setText("E-mail");

        Text_apellido.setFont(new java.awt.Font("VTC Letterer Pro", 0, 14)); // NOI18N
        Text_apellido.setNextFocusableComponent(Text_telefono);
        Text_apellido.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Text_apellidoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                Text_apellidoFocusLost(evt);
            }
        });
        Text_apellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Text_apellidoKeyPressed(evt);
            }
        });

        Text_provincia.setFont(new java.awt.Font("VTC Letterer Pro", 0, 14)); // NOI18N
        Text_provincia.setNextFocusableComponent(Text_localidad);
        Text_provincia.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Text_provinciaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                Text_provinciaFocusLost(evt);
            }
        });
        Text_provincia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Text_provinciaKeyPressed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("VTC Letterer Pro", 1, 14)); // NOI18N
        jLabel8.setText("CP");

        Text_nombre.setFont(new java.awt.Font("VTC Letterer Pro", 0, 14)); // NOI18N
        Text_nombre.setToolTipText("");
        Text_nombre.setNextFocusableComponent(Text_apellido);
        Text_nombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Text_nombreActionPerformed(evt);
            }
        });
        Text_nombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Text_nombreFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                Text_nombreFocusLost(evt);
            }
        });
        Text_nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Text_nombreKeyPressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("VTC Letterer Pro", 1, 14)); // NOI18N
        jLabel1.setText("Nombre");

        Text_mail.setFont(new java.awt.Font("VTC Letterer Pro", 0, 14)); // NOI18N
        Text_mail.setNextFocusableComponent(boton_aceptar);
        Text_mail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Text_mailFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                Text_mailFocusLost(evt);
            }
        });
        Text_mail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Text_mailKeyPressed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("VTC Letterer Pro", 1, 14)); // NOI18N
        jLabel4.setText("D.N.I.");

        jLabel3.setFont(new java.awt.Font("VTC Letterer Pro", 1, 14)); // NOI18N
        jLabel3.setText("Telefono");

        Text_dni.setFont(new java.awt.Font("VTC Letterer Pro", 0, 14)); // NOI18N
        Text_dni.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        Text_dni.setNextFocusableComponent(Text_apellido);
        Text_dni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Text_dniActionPerformed(evt);
            }
        });
        Text_dni.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Text_dniFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                Text_dniFocusLost(evt);
            }
        });
        Text_dni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Text_dniKeyPressed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("VTC Letterer Pro", 1, 14)); // NOI18N
        jLabel5.setText("Direccion");

        Text_direccion.setFont(new java.awt.Font("VTC Letterer Pro", 0, 14)); // NOI18N
        Text_direccion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Text_direccionFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                Text_direccionFocusLost(evt);
            }
        });
        Text_direccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Text_direccionKeyPressed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("VTC Letterer Pro", 1, 14)); // NOI18N
        jLabel6.setText("Localidad");

        jLabel7.setFont(new java.awt.Font("VTC Letterer Pro", 1, 14)); // NOI18N
        jLabel7.setText("Provincia");

        Text_localidad.setFont(new java.awt.Font("VTC Letterer Pro", 0, 14)); // NOI18N
        Text_localidad.setNextFocusableComponent(text_cp);
        Text_localidad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Text_localidadFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                Text_localidadFocusLost(evt);
            }
        });
        Text_localidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Text_localidadKeyPressed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Flaticon_4158.png"))); // NOI18N
        jButton1.setToolTipText("Salir pestaña");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        boton_eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/1399586945_Black_Trash.png"))); // NOI18N
        boton_eliminar.setToolTipText("Eliminar usuario");
        boton_eliminar.setEnabled(false);
        boton_eliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                boton_eliminarMouseClicked(evt);
            }
        });

        boton_editar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/1399646585_new-24.png"))); // NOI18N
        boton_editar.setToolTipText("Editar usuario");
        boton_editar.setEnabled(false);
        boton_editar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                boton_editarMouseClicked(evt);
            }
        });

        boton_aceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/1399588440_Check.png"))); // NOI18N
        boton_aceptar.setToolTipText("Confirmar operación");
        boton_aceptar.setEnabled(false);
        boton_aceptar.setNextFocusableComponent(boton_cancelar);
        boton_aceptar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                boton_aceptarMouseClicked(evt);
            }
        });

        boton_cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/1399588429_Delete.png"))); // NOI18N
        boton_cancelar.setToolTipText("Rechazar operación");
        boton_cancelar.setEnabled(false);
        boton_cancelar.setNextFocusableComponent(Text_dni);
        boton_cancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                boton_cancelarMouseClicked(evt);
            }
        });

        imagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imagen.setText("No imagen");
        imagen.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        imagen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                imagenMouseClicked(evt);
            }
        });

        boton_quitar_foto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/1401137841_trash_16x16.gif"))); // NOI18N
        boton_quitar_foto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_quitar_fotoActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("VTC Letterer Pro", 1, 14)); // NOI18N
        jLabel10.setText("Cod.");

        Text_id.setFont(new java.awt.Font("VTC Letterer Pro", 0, 12)); // NOI18N
        Text_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Text_idActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(boton_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(boton_editar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(boton_aceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(boton_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGap(12, 12, 12)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel7)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel1)
                                            .addComponent(jLabel10)))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(Text_provincia)
                                        .addComponent(Text_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jLabel2)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(Text_apellido, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(22, 22, 22)
                                            .addComponent(jLabel6)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(Text_localidad, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel9)
                                            .addGap(43, 43, 43))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel5)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(Text_mail)
                                        .addComponent(Text_direccion, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(85, 85, 85)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(Text_id)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(1, 1, 1)
                                    .addComponent(Text_dni, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(text_cp, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addGap(12, 12, 12)
                        .addComponent(Text_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(imagen, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(boton_quitar_foto, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(boton_cancelar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boton_aceptar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boton_editar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boton_eliminar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(Text_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Text_dni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Text_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(Text_apellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(Text_provincia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel7))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6)
                                .addComponent(Text_localidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(text_cp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Text_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Text_direccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Text_mail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(boton_quitar_foto)
                            .addComponent(imagen, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(27, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void Text_telefonoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Text_telefonoFocusGained
        color=Text_telefono.getBackground();
        Text_telefono.setBackground(Color.cyan);
    }//GEN-LAST:event_Text_telefonoFocusGained

    private void Text_telefonoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Text_telefonoFocusLost
        Text_telefono.setBackground(color);
    }//GEN-LAST:event_Text_telefonoFocusLost

    private void Text_telefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Text_telefonoKeyPressed
 if(evt.getKeyCode()==KeyEvent.VK_ENTER)
        {
            Text_provincia.requestFocus();
        }        
        
    }//GEN-LAST:event_Text_telefonoKeyPressed

    private void text_cpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_text_cpFocusGained
        color=text_cp.getBackground();
        text_cp.setBackground(Color.cyan);
    }//GEN-LAST:event_text_cpFocusGained

    private void text_cpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_text_cpFocusLost
        text_cp.setBackground(color);
    }//GEN-LAST:event_text_cpFocusLost

    private void text_cpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_cpKeyPressed
 if(evt.getKeyCode()==KeyEvent.VK_ENTER)
        {
           Text_direccion.requestFocus();
        }      

    }//GEN-LAST:event_text_cpKeyPressed

    private void Text_apellidoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Text_apellidoFocusGained
       color=Text_apellido.getBackground();
        Text_apellido.setBackground(Color.cyan);
    }//GEN-LAST:event_Text_apellidoFocusGained

    private void Text_apellidoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Text_apellidoFocusLost
        
        Text_apellido.setBackground(color);
    }//GEN-LAST:event_Text_apellidoFocusLost

    private void Text_apellidoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Text_apellidoKeyPressed
 if(evt.getKeyCode()==KeyEvent.VK_ENTER)
        {
           Text_telefono.requestFocus();
        }        
    }//GEN-LAST:event_Text_apellidoKeyPressed

    private void Text_provinciaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Text_provinciaFocusGained
        color=Text_provincia.getBackground();
        Text_provincia.setBackground(Color.cyan);
    }//GEN-LAST:event_Text_provinciaFocusGained

    private void Text_provinciaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Text_provinciaFocusLost
        Text_provincia.setBackground(color);
    }//GEN-LAST:event_Text_provinciaFocusLost

    private void Text_provinciaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Text_provinciaKeyPressed
 if(evt.getKeyCode()==KeyEvent.VK_ENTER)
        {
           Text_localidad.requestFocus();
        }      

    }//GEN-LAST:event_Text_provinciaKeyPressed

    private void Text_nombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Text_nombreActionPerformed

    }//GEN-LAST:event_Text_nombreActionPerformed

    private void Text_nombreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Text_nombreFocusGained
        color=Text_nombre.getBackground();
        Text_nombre.setBackground(Color.cyan);
    }//GEN-LAST:event_Text_nombreFocusGained

    private void Text_nombreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Text_nombreFocusLost
        Text_nombre.setBackground(color);
    }//GEN-LAST:event_Text_nombreFocusLost

    private void Text_nombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Text_nombreKeyPressed
 if(evt.getKeyCode()==KeyEvent.VK_ENTER)
        {
            Text_apellido.requestFocus();
        }      

    }//GEN-LAST:event_Text_nombreKeyPressed

    private void Text_mailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Text_mailFocusGained
        color=Text_mail.getBackground();
        Text_mail.setBackground(Color.cyan);
    }//GEN-LAST:event_Text_mailFocusGained

    private void Text_mailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Text_mailFocusLost
        Text_mail.setBackground(color);
    }//GEN-LAST:event_Text_mailFocusLost

    private void Text_mailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Text_mailKeyPressed
 if(evt.getKeyCode()==KeyEvent.VK_ENTER)
        {
          if(!Text_dni.isEnabled())
          {
              Text_nombre.requestFocus();
          } 
          else
          {
              Text_dni.requestFocus();
          }    
        }        
       

    }//GEN-LAST:event_Text_mailKeyPressed

    private void Text_dniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Text_dniActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Text_dniActionPerformed

    private void Text_dniFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Text_dniFocusGained
        color=Text_dni.getBackground();
        Text_dni.setBackground(Color.CYAN);
    }//GEN-LAST:event_Text_dniFocusGained

    private void Text_dniFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Text_dniFocusLost
        Text_dni.setBackground(color);
    }//GEN-LAST:event_Text_dniFocusLost

    private void Text_dniKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Text_dniKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER)
        {
         Text_nombre.requestFocus();
        }

    }//GEN-LAST:event_Text_dniKeyPressed

    private void Text_direccionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Text_direccionFocusGained
        color=Text_direccion.getBackground();
        Text_direccion.setBackground(Color.cyan);
    }//GEN-LAST:event_Text_direccionFocusGained

    private void Text_direccionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Text_direccionFocusLost
        Text_direccion.setBackground(color);
    }//GEN-LAST:event_Text_direccionFocusLost

    private void Text_direccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Text_direccionKeyPressed
 if(evt.getKeyCode()==KeyEvent.VK_ENTER)
        {
          Text_mail.requestFocus();
        }      

    }//GEN-LAST:event_Text_direccionKeyPressed

    private void Text_localidadFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Text_localidadFocusGained
        color=Text_localidad.getBackground();
        Text_localidad.setBackground(Color.cyan);
    }//GEN-LAST:event_Text_localidadFocusGained

    private void Text_localidadFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Text_localidadFocusLost
        Text_localidad.setBackground(color);
    }//GEN-LAST:event_Text_localidadFocusLost

    private void Text_localidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Text_localidadKeyPressed
 if(evt.getKeyCode()==KeyEvent.VK_ENTER)
        {
            text_cp.requestFocus();
        }      

    }//GEN-LAST:event_Text_localidadKeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        imagen.setIcon(null);
        cerrar_pestaña();
     
     
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyTyped
         
    }//GEN-LAST:event_formKeyTyped
public void bloquear_cajas()
    {
        Text_id.setEditable(false);
        Text_dni.setEditable(false);
        Text_apellido.setEditable(false);
        Text_nombre.setEditable(false);
        text_cp.setEditable(false);
        Text_localidad.setEditable(false);
        Text_provincia.setEditable(false);
        Text_direccion.setEditable(false);
        Text_mail.setEditable(false);
        Text_telefono.setEditable(false);
         boton_quitar_foto.setEnabled(false);
        evento_arrastrar.no_arrastrable();
        /*********************************************************/
        Text_id.setBorder(null);
        Text_dni.setBorder(null);
        Text_apellido.setBorder(null);
        Text_nombre.setBorder(null);
        text_cp.setBorder(null);
        Text_localidad.setBorder(null);
        Text_provincia.setBorder(null);
        Text_direccion.setBorder(null);
        Text_mail.setBorder(null);
        Text_telefono.setBorder(null);
        Text_id.setBackground(Color.white);
        Text_dni.setBackground(Color.white);
        Text_apellido.setBackground(Color.white);
        Text_nombre.setBackground(Color.white);
        text_cp.setBackground(Color.white);
        Text_localidad.setBackground(Color.white);
        Text_provincia.setBackground(Color.white);
        Text_direccion.setBackground(Color.white);
        Text_mail.setBackground(Color.white);
        Text_telefono.setBackground(Color.white);
          
    }   
    public void desbloquear_cajas()
    {
        Text_dni.setEditable(true);
        Text_apellido.setEditable(true);
        Text_nombre.setEditable(true);
        text_cp.setEditable(true);
        Text_localidad.setEditable(true);
        Text_provincia.setEditable(true);
        Text_direccion.setEditable(true);
        Text_mail.setEditable(true);
        Text_telefono.setEditable(true);
        evento_arrastrar.arrastrable();
        
        
        Text_dni.setBackground(Color.yellow);
        Text_apellido.setBackground(Color.yellow);
        Text_nombre.setBackground(Color.yellow);
        text_cp.setBackground(Color.yellow);
        Text_localidad.setBackground(Color.yellow);
        Text_provincia.setBackground(Color.yellow);
        Text_direccion.setBackground(Color.yellow);
        Text_mail.setBackground(Color.yellow);
        Text_telefono.setBackground(Color.yellow);
        boton_quitar_foto.setEnabled(true);
        
        
        
          
    }  
       public void eliminar() throws SQLException
    {
        int resul=javax.swing.JOptionPane.showConfirmDialog(this, "Esta seguro de eliminar eliminar el Cliente");
        if (resul==0)
        {    
           
            
            String sentencia_sql="";
            sentencia_sql="DELETE FROM cliente where id='"+Text_id.getText()+"'"; 
             conector.actualizar(sentencia_sql,"Eliminar");
             borrar_contenido_cajas();
            //conector.cerrar();
             cerrar_pestaña();
        }
       
        boton_editar.setEnabled(false);
        boton_eliminar.setEnabled(false);
    }     
    public void borrar_contenido_cajas()
    {
        Text_id.setText("");
        Text_dni.setText("");
        Text_apellido.setText("");
        Text_nombre.setText("");
        text_cp.setText("");
        Text_localidad.setText("");
        Text_provincia.setText("");
        Text_direccion.setText("");
        Text_mail.setText("");
        Text_telefono.setText("");
        imagen.setIcon(null);
    }        
    private void boton_eliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_boton_eliminarMouseClicked
        try {
            eliminar();
        } catch (SQLException ex) {
            Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_boton_eliminarMouseClicked
 public void bloquear_botones()
    {
     
        boton_editar.setEnabled(false);
        boton_cancelar.setEnabled(false);
        boton_eliminar.setEnabled(false);
      
        boton_editar.setEnabled(false);
        boton_aceptar.setEnabled(false);
      
        
    }
    private void boton_editarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_boton_editarMouseClicked
        accion="Editar";
        bloquear_botones();
        boton_aceptar.setEnabled(true);
        boton_cancelar.setEnabled(true);

        desbloquear_cajas();
        Text_dni.setEditable(false);
        Text_dni.setBackground(Color.WHITE);
        Text_nombre.requestFocus();

    }//GEN-LAST:event_boton_editarMouseClicked
   
    
      public boolean datos() /*compruebo de las cajas estan los campos obligatorios antes de grabar sino se descarta */
    {
        boolean confirmar=true;
        dni=Text_dni.getText();
        if(dni.equals("")) /* comprobar dni*/
        {
            javax.swing.JOptionPane.showMessageDialog(this, "Debes introducir un dni obligatoriamente");
            Text_dni.requestFocus();
            confirmar=false;
        }    
        nombre=Text_nombre.getText();
        if(nombre.equals(""))
        {
            javax.swing.JOptionPane.showMessageDialog(this, "Debes introducir un nombre");
            Text_nombre.requestFocus();
            confirmar=false;
        }    
        apellido=Text_apellido.getText();
        if(apellido.equals(""))
        {
          javax.swing.JOptionPane.showMessageDialog(this, "Debes introducir un apellido");
            Text_apellido.requestFocus();   
            confirmar=false;
        }    
       telefono=Text_telefono.getText();
        provincia=Text_provincia.getText();
       localidad=Text_localidad.getText();
        cp=text_cp.getText();
       direccion= Text_direccion.getText();
       email= Text_mail.getText();
       if (cp.equals(""))
       {
           cp="00000";
       }    
           return confirmar;
    }        
    private void boton_aceptarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_boton_aceptarMouseClicked
        try {
            switch (accion) {
                case "Añadir":

                añadir();
                break;
                case "Eliminar":
                eliminar();
                break;
                case "Editar":
                añadir();
                break;
                case "Buscar":
                break;

            }
        } catch (SQLException ex) {
            Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_boton_aceptarMouseClicked

    private void boton_cancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_boton_cancelarMouseClicked
        
        bloquear_cajas();
        bloquear_botones();
        cargar_casillas(id);
        accion="";

    }//GEN-LAST:event_boton_cancelarMouseClicked

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
       
    }//GEN-LAST:event_formFocusGained

    private void imagenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imagenMouseClicked
        
        if (evt.getClickCount() == 2) {
          JFileChooser jf = new JFileChooser();
          
            int resultado=jf.showOpenDialog(null);// TODO add your handling code here:
            if(resultado == JFileChooser.APPROVE_OPTION)
            {
                ImageIcon im = new  ImageIcon(jf.getSelectedFile().toString());
                imagen.setIcon(im);
            }    
       }
       
    }//GEN-LAST:event_imagenMouseClicked

    private void boton_quitar_fotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_quitar_fotoActionPerformed
       imagen.setIcon(null);
    }//GEN-LAST:event_boton_quitar_fotoActionPerformed

    private void Text_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Text_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Text_idActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Text_apellido;
    private javax.swing.JTextField Text_direccion;
    private javax.swing.JTextField Text_dni;
    private javax.swing.JTextField Text_id;
    private javax.swing.JTextField Text_localidad;
    private javax.swing.JTextField Text_mail;
    private javax.swing.JTextField Text_nombre;
    private javax.swing.JTextField Text_provincia;
    private javax.swing.JTextField Text_telefono;
    private javax.swing.JButton boton_aceptar;
    private javax.swing.JButton boton_cancelar;
    private javax.swing.JButton boton_editar;
    private javax.swing.JButton boton_eliminar;
    private javax.swing.JButton boton_quitar_foto;
    private javax.swing.JLabel imagen;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField text_cp;
    // End of variables declaration//GEN-END:variables

    
   
}
