package aplicacion_escritorio;


import javax.swing.JMenuItem;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nacho
 */
public class Mimenuitem extends JMenuItem{
    String accion;
    String nombre;
    Mimenuitem()
    {
        super();
    }
    Mimenuitem(String nombre, String accion)
    {
        super(nombre);
        this.accion=accion;
    }        
    
}
