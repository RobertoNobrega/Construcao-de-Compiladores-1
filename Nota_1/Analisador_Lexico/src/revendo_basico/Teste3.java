/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package revendo_basico;

/**
 *
 * @author roberto
 */

public class Teste3 {
    
    public static void mensagem(String n, String m){
        System.out.println(m + n);
    }
    
    public static void main(String[] args){
        char caracter = '\t';
        if(caracter == '\t')
            System.out.println("\n\tTabulação.");
        String st = "98";
        boolean b = st.matches("[0-9][^0-9]");
        System.out.println("\n\t\tResultado >> " + b);
        mensagem("dia","Bom");
    }
    
}
