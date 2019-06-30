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

public class Concatenacao {
    
    /**     Concatenação de Strings.    */
    
    public static void main(String[] args){
        
        String curso = "Curso";
        String java = "Java";
        String cursoJava = curso = java;  // Concatenando duas Strings.
        
        String numero = String.valueOf('1');   // Transforma um elemento que herda de Object em uma String e atribui a
        // variável numero .
        System.out.println("\n\tResultado 1 > " + numero);
        
        numero += String.valueOf('9');   // Estamos transformando um char em uma String. Depois concatenando com a String
        // numero e atribuindo a concatenação a mesma variável número.
        System.out.println("\n\tResultado 2 > " + numero);
        
        numero += String.valueOf('0');
        System.out.println("\n\tResultado 3 > " + numero);
        
        numero = numero.substring(0, numero.length() - 1);  // Nesse caso, sempre estamos apagando o último caracter da String numero.
        System.out.println("\n\tResultado 4 > " + numero);
        
        numero = numero.substring(0, numero.length() - 1);
        System.out.println("\n\tResultado 5 > " + numero);
        
        numero = numero.substring(0, numero.length() - 1);
        System.out.println("\n\tResultado 6 > " + numero);
        
        if(numero.isEmpty())
           System.out.println("\n\tA variável numero esta vazia.");
        
    }
    
}
