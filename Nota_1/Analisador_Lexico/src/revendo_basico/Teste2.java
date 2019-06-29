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

public class Teste2 {
    public static void main(String[] args){
        String instrucao = "program teste;";
        ArrayList<Character> chars = new ArrayList<Character>();
        for (char c : instrucao.toCharArray()) {
            chars.add(c);
        }
        for(char d : chars)
            System.out.print(d);
        System.out.print("\n");
    }
}
