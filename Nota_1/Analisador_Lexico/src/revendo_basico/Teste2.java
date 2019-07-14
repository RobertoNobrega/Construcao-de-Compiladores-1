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
    
    public static String consulta(String[] lista_palavras_chaves, String token){
        for(String tk : lista_palavras_chaves){   // Usando o for-each.
            ;
        }
        return "";
    }
    
    public static void main(String[] args){
        String instrucao = "program teste;";
        String[] lista_palavras_chaves = {"program","var","integer","real","boolean","procedure","begin",
        "end","if","then","else","while","do","not"};
        String estado = "";
        String token = "";
        ArrayList<Character> chars = new ArrayList<Character>();
        for (char c : instrucao.toCharArray()) {
            chars.add(c);
        }
        for(char d : chars)
            System.out.print(d);
        System.out.println("\n\tTamanho : " + instrucao.length() + " " + chars.size());
        //boolean b = "a".matches("[a-zA-Z]");   // Vai reconhecer letras maiúsculas e minúsculas.    Funcionou.
        //System.out.println("\t\tResultado 1 > " + b);
        String letra;
        char[] letras = {'p','r','o','g','r','a','m',' ','t','e','s','t','e',';'};
        for(int i = 0; i < letras.length; ++i){
            letra = Character.toString(letras[i]);   // Convertendo um char para uma String.
            if(letra.matches("[a-zA-Z]")){  // Se o char for uma letra minúscula ou maiúscula.
                if(!estado.equalsIgnoreCase("variável"))
                    estado = "variável";
                token += String.valueOf(letra);   // Concatenando cada caracter, para formar uma String.
                if(((i + 1) < letras.length) && ((letras[i + 1] == ' ') || (letras[i + 1] == '\n') || (letras[i + 1] == '\t'))){
                    /** 
                     *   Quando for uma posição válida do 'buffer', em que o caracter dessa posição é um espaço em branco
                     *   ou quebra de linha ou tabulação.
                    */
                    consulta(lista_palavras_chaves, token);
                }
            }
        }
        System.out.println("\t\tEstado >> " + estado + "\n\t\tToken >> " + token);
    }
}
