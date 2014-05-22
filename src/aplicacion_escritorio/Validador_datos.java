/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aplicacion_escritorio;

/**
 *
 * @author nacho
 */
class Validador_datos {
    
 static public boolean DNI(String dni){
final String letra= "TRWAGMYFPDXBNJZSQVHLCKE";
Boolean res= false;
dni=dni.toUpperCase();
if(dni.length()==9){

for(int i=0;i<dni.length()-1;i++){
res= res&&Character.isDigit(((dni).toUpperCase()).charAt(i));
}
Integer valor= new Integer(dni.substring(0, 8));
int aux= valor%23;
char letraReal = dni.charAt(8);
char letraCalculada= letra.charAt(aux);
if(letraReal==letraCalculada){
res= true;

}

}
return res;
}
 static public void main(String[] arg)
 {
     System.out.println(""+Validador_datos.DNI("16601225d"));
 }
}


    

