package aplicacion_escritorio;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nacho
 */
public class menu_creador implements ActionListener{
    
    Vector itemsmenu;
    Vector elementos;
    String ruta;
    JMenuBar menu;
    ActionListener escucha;
    public menu_creador(String ruta) throws IOException
    {
     this.ruta=ruta;
     itemsmenu= new Vector();
     elementos= new Vector();
     leer_fichero();
     menu();
        System.out.println("terminado");
     
    }
    public void leer_fichero() throws IOException 
    {
        
        try {
            FileReader fr= new FileReader(ruta);
            BufferedReader br = new BufferedReader(fr);
            String linea= "";
            while((linea=br.readLine())!=null)
            { 
               añadir_vector(linea);
            }    
        } catch (FileNotFoundException ex) {
            System.out.println("error lectura de fichero menu_creador.leer_fichero");
        }
    } 
    public void añadir_vector(String linea)
    {
        int nivel;
        String nombre;
        nivel=profundidad(linea);
        nombre=linea.replace(".", "");
        System.out.println(nombre+" "+ nivel);
        itemsmenu.add(new itms(nivel,nombre));
        
    }
    public int profundidad(String linea)
    {   
        int profundidad;
        profundidad=linea.length();
        linea=linea.replace(".", "");
        
        profundidad=profundidad-linea.length();
        return profundidad;
    }        

    @Override
    public void actionPerformed(ActionEvent e) {
        
        System.out.println("..");
        
        
    }
    public class itms
    {
        int nivel;
        String nombre;
        String ejecuta;
        public itms()
        {
            nivel=0;
            nombre="";
            ejecuta="";
        }
        public itms(int nivel, String nombre)
        {
            this.nivel=nivel;
            this.nombre=nombre;
        }
    }
    
    public void crear_menu2(itms item)
    {
        
    }        
    public void crear_menu()
    {
              menu= new JMenuBar();
             JMenu itempadre= new JMenu();
             JMenu itempadre2= new JMenu();
             Mimenuitem itemhijo=new Mimenuitem();
             Mimenuitem itemhijo2=new Mimenuitem();
             Mimenuitem itemhijo3=new Mimenuitem();
             
             int total_elementos=itemsmenu.size()-1;
             for(int i=0; i<itemsmenu.size(); i++) 
             {
                
                 if(i+1 <= total_elementos)    /*compruebo que hay siguiente elemento para poder comparar*/
                 {
                     if(((itms)(itemsmenu.get(i))).nivel==1) /* si es un elemento del menu princial*/
                     {
                         itempadre=new JMenu(((itms)(itemsmenu.get(i))).nombre);
                         elementos.add(itempadre);
                         itempadre.addActionListener(escucha);
                         menu.add(itempadre);
                         
                     } 
                     else
                     {
                         if(((itms)(itemsmenu.get(i+1))).nivel>((itms)(itemsmenu.get(i))).nivel)
                         {
                         itempadre2=new JMenu(((itms)(itemsmenu.get(i))).nombre);
                         elementos.add(itempadre);
                         itempadre.addActionListener(escucha);
                         itempadre.add(itempadre2);
                         }    
                         else
                         {
                                if(((itms)(itemsmenu.get(i-1))).nivel<=((itms)(itemsmenu.get(i))).nivel)
                                {    
                                itemhijo= new Mimenuitem((((itms)(itemsmenu.get(i))).nombre),"accion");
                                elementos.add(itemhijo);
                                itemhijo.addActionListener(escucha);
                                itempadre2.add(itemhijo);
                                }
                                else
                                {
                                   itemhijo= new Mimenuitem((((itms)(itemsmenu.get(i))).nombre),"accion");
                                    elementos.add(itemhijo);
                                    itemhijo.addActionListener(escucha);
                                    itempadre.add(itemhijo); 
                                }    
                                 
                         }    
                     }    
                    
                 }
                 else
                 {
                     if(((itms)(itemsmenu.get(i))).nivel==1)
                     {
                         itempadre=new JMenu(((itms)(itemsmenu.get(i))).nombre);
                         elementos.add(itempadre);
                         itempadre.addActionListener(escucha);
                         menu.add(itempadre);
                         
                     } 
                     else
                     {
                         itemhijo= new Mimenuitem((((itms)(itemsmenu.get(i))).nombre),"accion");
                         elementos.add(itemhijo);
                         itemhijo.addActionListener(escucha);
                         itempadre.add(itemhijo);  
                     }    
                     
                 }
                 
             }    
             
    }
    public void menu()
    {
              menu= new JMenuBar();
             JMenu itempadre= new JMenu();
             Mimenuitem itemhijo=new Mimenuitem();
             Mimenuitem itemhijo2=new Mimenuitem();
             Mimenuitem itemhijo3=new Mimenuitem();
             int aux=0;
             for(int i=0; i<itemsmenu.size(); i++)
             {
                 switch (((itms)(itemsmenu.get(i))).nivel) {
                     case 1:
                         itempadre=new JMenu(((itms)(itemsmenu.get(i))).nombre);
                         elementos.add(itempadre);
                         itempadre.addActionListener(escucha);
                         menu.add(itempadre);
                         aux=1;
                         break;
                     case 2:
                         itemhijo= new Mimenuitem((((itms)(itemsmenu.get(i))).nombre),"accion");
                         elementos.add(itemhijo);
                         itemhijo.addActionListener(escucha);
                         itempadre.add(itemhijo);
                         break;
                     case 3:
                         itemhijo2= new Mimenuitem((((itms)(itemsmenu.get(i))).nombre),"accion");
                         elementos.add(itemhijo2);
                         itemhijo2.addActionListener(escucha);
                         itemhijo.add(itemhijo2);
                         break;
                 }
                 }
                 
                 
                 
                 
             }   
    
    public void escuchador()
    {
        escucha=new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("pulsado"+e.getSource());
            }
        };
        
    }

        
    }
    


