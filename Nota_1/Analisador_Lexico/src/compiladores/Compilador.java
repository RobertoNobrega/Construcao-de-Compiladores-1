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
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author roberto
 * Teste.
 */

public class Compilador {
    
    private String caminho;
    private ArrayList<Character> buffer;
    private String[] lista_palavras_chaves = {"program","var","integer","real","boolean","procedure","begin",
    "end","if","then","else","while","do","not"};
    private FileReader arquivoEntrada;
    private BufferedReader arquivoLeitura;
    private FileWriter arquivoSaida;
    private BufferedWriter arquivoEscrita;
    private boolean flag1;
    private String estado;
    private int numero_linha;
    //private FileOutputStream arquivoOt;
    //private BufferedWriter arquivoOutput;
    
    public Compilador(){
        String caminho1 = "src/compiladores/programa1Pascal.txt";
        String caminho2 = "src/compiladores/Tabela.txt";
        buffer = new ArrayList<Character>();
        //lista_palavras_chaves = new ArrayList<String>();
        try{
            arquivoEntrada = new FileReader(new File(caminho1));
            arquivoLeitura = new BufferedReader(arquivoEntrada);
            arquivoSaida = new FileWriter(caminho2,true);
            arquivoEscrita = new BufferedWriter(arquivoSaida);
            arquivoEscrita.write("Token               Classificação               Linha");  // Escrevendo no arquivo, esta String.
            arquivoEscrita.newLine();  // Quebra de Linha no Arquivo de Escrita.
            arquivoEscrita.flush(); // Precisa deste comando, para poder "autorizar" a escrita no arquivo.
            flag1 = true;   // Arquivo já está referenciado. Já pode ler/escrever nele.
            estado = "";    // Por enquanto, o estado ainda não foi definido.
            numero_linha = 0;
        }catch(FileNotFoundException ex1){
            System.out.print("\n\tErro 1 . Arquivo Não Localizado.");
        }catch(IOException ex2){  // Caso ocorra algum problema na parte de escrita do arquivo.
            
        }
    }
    
    public String consulta(String token){
        for(int i = 0; i < lista_palavras_chaves.length; ++i){   // Verificando se o token é uma palavra-chave da Linguagem Pascal.
            if(token.equals(lista_palavras_chaves[i]))
                return "Palavra-Chave";
        }
        
    }
    
    public void funcao1(){
        String linha_arquivo;
        String caracter;
        String token = "";
        if(!flag1){
            System.out.println("\n\tInfelizmente o Arquivo não foi Lido.");
            return;
        }
        try{
           while((linha_arquivo = arquivoLeitura.readLine()) != null){  // Lendo uma linha inteira do arquivo.
               ++numero_linha;
               for(char caractere : linha_arquivo.toCharArray())
                   buffer.add(caractere); // Adicionando no buffer, cada componente fundamental da String.
               for(int pos = 0; pos < buffer.size(); ++pos){
                   caracter = Character.toString(buffer.get(pos));  // Pegando um caracter, de uma posição específica do
                   // buffer, em que vai ser convertida em uma String e armazena em uma variável.
                   if(caracter.matches("[a-zA-Z]")){  // Neste if, só entra o caracter que está no alfabeto de a até z ou de A até Z
                       if(!estado.equalsIgnoreCase("variável"))
                          estado = "variável";
                       token += String.valueOf(caracter);   // Concatenando cada caracter, para formar uma String.
                       if(((pos + 1) < buffer.size()) && ((buffer.get(pos + 1) == ' ') || (buffer.get(pos + 1) == '\n') || (buffer.get(pos + 1) == '\t'))){
                         /** 
                         *   Quando for uma posição válida do 'buffer', em que o caracter dessa posição é um espaço em branco
                         *   ou quebra de linha ou tabulação.
                         */
                         estado = consulta(token);   // Realizando a consulta do token e retorna o estado desse token.
                         
                       }
                   }
               }
           }
        }catch(IOException ex2){
            System.out.print("\n\tErro 2 .");
        }
    }
}
