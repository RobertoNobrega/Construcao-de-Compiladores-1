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
    private boolean abreComentario, fechaComentario;
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
            File arq = new File(caminho2);
            if(arq.exists())
                arq.delete();  // Apagando o arquivo da Tabela, pois a Tabela será reescrita (Será Atualizada).
            arquivoSaida = new FileWriter(caminho2,true);
            arquivoEscrita = new BufferedWriter(arquivoSaida);
            arquivoEscrita.write("Token               Classificação               Linha");  // Escrevendo no arquivo, esta String.
            arquivoEscrita.newLine();  // Quebra de Linha no Arquivo de Escrita.
            arquivoEscrita.flush(); // Precisa deste comando, para poder "autorizar" a escrita no arquivo.
            flag1 = true;   // Arquivo já está referenciado. Já pode ler/escrever nele.
            abreComentario = false;
            fechaComentario = false;
            estado = "";    // Por enquanto, o estado ainda não foi definido.
            numero_linha = 0;
        }catch(FileNotFoundException ex1){
            System.out.print("\n\tErro 1 . Arquivo Não Localizado." + ex1);
        }catch(IOException ex2){  // Caso ocorra algum problema na parte de escrita do arquivo.
            System.out.print("\n\t\tProblema ex2 >> " + ex2);
        }
    }
    
    public String consultarToken(String token){
        if(token.matches("\\d+"))
            return "Número Inteiro";
        else if(token.matches("\\d+.\\d*"))
            return "Número Real";
        else if(token.matches("[;:(),.]"))
            return "Delimitador";
        else if(token.matches("=|<|>|<=|>=|<>"))
            return "Operador Relacional";
        else if(token.equals(":="))
            return "Operador de Atribuição";
        else if(token.matches("[+-]|or"))
            return "Operador Aditivo";
        else if(token.matches("[*/]|and"))
            return "Operador Multiplicativo";
        for(int i = 0; i < lista_palavras_chaves.length; ++i)   // Verificando se o token é uma palavra-chave da Linguagem Pascal.
           if(token.equals(lista_palavras_chaves[i]))
               return "Palavra-Chave";
        if(token.matches("^[a-zA-Z]\\w*"))
            return "Identificador";
        return "Token Desconhecido";
    }
    
    public void registrarTabela(String token, String classificacao){ /** OK. */
        try{
            arquivoEscrita.write(token + "\t\t" + classificacao + "\t\t" + numero_linha);   // Escrevendo no arquivo Tabela, as informações
            // a respeito do token, classificação e a linha onde se encontra.
            arquivoEscrita.newLine(); // Após escrever as informações, vai ocorrer a quebra de linha.
            arquivoEscrita.flush(); // Precisa deste comando, para poder "autorizar" a escrita no arquivo.
        }catch(IOException ex3){
            System.out.print("\n\t\tProblema ex3 >> " + ex3);
        }
    }
    
//    public String esvaziarToken(String token){   /** OK. */
//        while(token.length() > 0)
//            token = token.substring(0, token.length() - 1);
//        return token;
//    }
    
    public String popCaracterToken(String token){  /** OK. Este Método retira o último caracter do token. Similar ao que ocorre
        na Estrutura de Dados Pilha. */
        return token.substring(0, token.length() - 1);
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
                   if((caracter.matches("[\\s\t{\n]"))){    // Caracter que não deve ser considerado no código fonte.
                       // Quando entrar neste if, o caracter não pode ser considerado.
                       if(caracter.matches("[\\s\t\n]"))
                           continue;
                        if(!abreComentario)
                          abreComentario = true;
                        while(true){
                           // Só sai deste while, quando encontrar o caracter  }
                           if((pos < buffer.size()) && (buffer.get(pos) != '}')){
                               ++pos;
                               continue;
                           }else if((pos == buffer.size()) && (buffer.get(pos - 1) != '}')){  // Chegou no último caracter e não é }
                               buffer.clear();
                               //break;
                           }else{
                               fechaComentario = true;
                               if(pos == buffer.size())
                                  buffer.clear();
                           }
                           break;
                        }
                        if(buffer.isEmpty())   // Buffer Vazio. Tem que fazer uma nova leitura do código fonte.
                            break;
                        continue;
                   }
                   if((caracter.matches("\\w")) || (caracter.matches("[^\\w]"))){    // Quando o caracter pertence ou não ao alfabeto de \\w
                       token += String.valueOf(caracter);  // Concatenando cada caracter, para formar uma String.
                       estado = consultarToken(token);
                       if(((pos + 1) < buffer.size()) && (Character.toString(buffer.get(pos + 1)).matches("[^\\w]"))){
                           // Entra neste if quando existe um caracter sucessor e não pertence ao alfabeto \\w .
                           registrarTabela(token, estado);
                           token = "";
                           estado = "";
                       }else if(((pos + 1) == buffer.size())){
                           // Quando está no último caractere.
                           registrarTabela(token, estado);
                           token = "";
                           estado = "";
                           buffer.clear();   // Apaga o Buffer dos caracteres.
                       }
                   }
               }
            }
            arquivoEntrada.close();
            arquivoLeitura.close();
            arquivoSaida.close();
            arquivoEscrita.close();
        }catch(IOException ex2){
            System.out.print("\n\tErro 2 .");
        }
    }
}
