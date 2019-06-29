/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladores;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
//import java.io.FileInputStream;
import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author roberto
 */

public class Compilador {
    
    private String caminho;
    private ArrayList<Character> buffer;
    private ArrayList<String> lista_palavras_chaves;
    private FileReader arquivoEntrada;
    private BufferedReader arquivoLeitura;
    private boolean flag1;
    //private FileOutputStream arquivoOt;
    //private BufferedWriter arquivoOutput;
    
    public Compilador(){
        String caminho = "src/compiladores/programa1Pascal.txt";
        buffer = new ArrayList<Character>();
        lista_palavras_chaves = new ArrayList<String>();
        try{
            arquivoEntrada = new FileReader(new File(caminho));
            arquivoLeitura = new BufferedReader(arquivoEntrada);
            flag1 = true;
            lista_palavras_chaves.add("program");
            lista_palavras_chaves.add("var");
            lista_palavras_chaves.add("integer");
            lista_palavras_chaves.add("real");
            lista_palavras_chaves.add("boolean");
            lista_palavras_chaves.add("procedure");
            lista_palavras_chaves.add("begin");
            lista_palavras_chaves.add("end");
            lista_palavras_chaves.add("if");
            lista_palavras_chaves.add("then");
            lista_palavras_chaves.add("else");
            lista_palavras_chaves.add("while");
            lista_palavras_chaves.add("do");
            lista_palavras_chaves.add("not");
        }catch(FileNotFoundException ex1){
            System.out.print("\n\tErro 1 . Arquivo Não Localizado.");
        }
    }
    
    public void funcao1(){
        String linha_arquivo;
        int numero_linha = 1;
        if(!flag1){
            System.out.println("\n\tInfelizmente o Arquivo não foi Lido.");
            return;
        }
        try{
           while((linha_arquivo = arquivoLeitura.readLine()) != null){  // Lendo uma linha por inteiro do arquivo.
               //++numero_linha;
               for(char caractere : linha_arquivo.toCharArray())
                   buffer.add(caractere);
               
           }
        }catch(IOException ex2){
            System.out.print("\n\tErro 2 .");
        }
    }
}
