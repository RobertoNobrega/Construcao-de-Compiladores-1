/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package revendo_basico;

import java.util.ArrayList;

/**
 *
 * @author roberto
 */

public class Teste {
    public static void main(String[] args){
        int pos = 0;
        ArrayList<Character> buffer = new ArrayList<Character>();
        buffer.add('a');
        buffer.add('b');
        buffer.add('C');
        buffer.add('D');
        buffer.add('1');
        //buffer.add('20');   // Errado. Pois, penso que isso é interpretado como uma String.
        buffer.add(' '); // Espaço em Branco.
        buffer.add('E');
        buffer.add('\n');
        buffer.add('\t');
        for(Character ch : buffer){
           System.out.println(ch + " >> Posição " + pos);
           ++pos;
           if(ch.equals('D'))
               System.out.println("\n\tCaracter D está na lista.");
           if(ch.equals(' '))
               System.out.println("\n\tEspaço em Branco na lista.");
           if(ch.equals('\n'))
               System.out.println("\n\tQuebra de Linha.");
           if(ch.equals('\t'))
               System.out.println("\n\tTabulação.");
        }
    }
}