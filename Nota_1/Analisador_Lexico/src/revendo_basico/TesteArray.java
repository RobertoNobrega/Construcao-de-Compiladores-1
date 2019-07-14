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

public class TesteArray {
    
    public static void exibaArray(char[] ar){
        for(int i = 0; i < ar.length; ++i)
          System.out.print(ar[i]);
    }
    
    public static void main(String[] args){
        char[] buffer;
        buffer = "Teste de Código".toCharArray();
        exibaArray(buffer);
        System.out.println("\nTamanho do buffer : " + buffer.length);
        buffer = "Olá Mundo".toCharArray();
        exibaArray(buffer);
        System.out.println("\nTamanho do buffer : " + buffer.length);
        buffer = null;
        if(buffer == null)
            System.out.println("\nTamanho do buffer : " + null);
        char t = '\t';
        System.out.println(t + "Oi");
    }
    
}
