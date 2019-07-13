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

public class Teste4 {
    
    public static void testString(String string){
        string += " Boa Noite";
        System.out.println("\n\tFrase 1 : " + string);
    }
    
    public static void main(String[] args){
        String var = "Olá.";
        String nome = ";A";
        testString(var);
        System.out.println("\n\tFrase 2 : " + var + "\n\n\tTeste 2");
        //System.out.println("\n\t" + nome.substring(0,nome.length() - 2));
        System.out.println("\n\t" + nome);
        System.out.println("\n\t" + nome.substring(1, nome.length()));
        System.out.println("\n\tResposta >> " + "".isEmpty());
    }
    
}
