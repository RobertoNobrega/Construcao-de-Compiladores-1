/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladores;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 * @author roberto
 * Vamos Conseguir a Aprovação na Disciplina de Construção de Compiladores 1 .
 */

public class Compilador {
    
    private String caminho;
    private char[] buffer;
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
    
    public Compilador(){
        String caminho1 = "src/compiladores/programa1Pascal.txt";
        String caminho2 = "src/compiladores/Tabela.txt";
        try{
            arquivoEntrada = new FileReader(new File(caminho1));
            arquivoLeitura = new BufferedReader(arquivoEntrada);
            File arq = new File(caminho2);
            if(arq.exists())
                arq.delete();  // Apagando o arquivo da Tabela, pois a Tabela será reescrita (Será Atualizada).
            arquivoSaida = new FileWriter(caminho2,true);
            arquivoEscrita = new BufferedWriter(arquivoSaida);
            arquivoEscrita.write("       Token               Classificação               Linha");  // Escrevendo no arquivo, esta String.
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
        if(token.matches("[-]{0,1}\\d+"))
            return "Número Inteiro";
        else if(token.matches("[-]{0,1}\\d+[.]\\d*"))
            return "Número Real";
        else if(token.matches("[;:(),._]"))
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
            arquivoEscrita.write(token + "\t\t\t" + classificacao + "\t\t\t" + numero_linha);   // Escrevendo no arquivo Tabela, as informações
            // a respeito do token, classificação e a linha onde se encontra.
            arquivoEscrita.newLine(); // Após escrever as informações, vai ocorrer a quebra de linha.
            arquivoEscrita.flush(); // Precisa deste comando, para poder "autorizar" a escrita no arquivo.
        }catch(IOException ex3){
            System.out.print("\n\t\tProblema ex3 >> " + ex3);
        }
    }
    
    public String popCaracterToken(String token){  /** OK. Este Método retira o último caracter do token. Similar ao que ocorre
        na Estrutura de Dados Pilha. */
        return token.substring(0, token.length() - 1);
    }
    
    public void tokenTest(String token){
        String token_atual = token;
        String descricao_atual = "";
        int contador = 0;
        for(int tokenLength = token.length(); tokenLength > 0; ){
           if((descricao_atual = consultarToken(token)).equals("Token Desconhecido")){
               ++contador;
               popCaracterToken(token);
           }else{   // Token Conhecido.
               registrarTabela(token, descricao_atual);
               token = token_atual.substring(tokenLength - contador, token_atual.length());
               tokenLength = contador;
               contador = 0;
           } 
        }
    }
    
    public boolean concatToken(String token, int pos){  /* Método que Verifica se deve ou não continuar a Concatenação do Token. */
        if(!(consultarToken(token + Character.toString(buffer[pos + 1])).equals("Token Desconhecido")))
           return true;   // Vai Continuar a Concatenação.
        return false;
    }
    
    public void executeCompilador(){
        String linha_arquivo;
        String caracter;
        String token = "";
        int inicio_Comentario = 0;
        int pos, p, continuar_Posicao = 0;
        String consulte_token;
        boolean semaforo = true, registrar = false;
        if(!flag1){
            System.out.println("\n\tInfelizmente o Arquivo não foi Lido.");
            return;
        }
        try{
           while((linha_arquivo = arquivoLeitura.readLine()) != null){  // Lendo uma linha inteira do arquivo.
               ++numero_linha;
               //System.out.println("\n\tLinha >> " + numero_linha + "  Tamanho do token >> " + token.length());
//             for(char caractere : linha_arquivo.toCharArray())
//                buffer.add(caractere); // Adicionando no buffer, cada componente fundamental da String.
               buffer = linha_arquivo.toCharArray();   // A cada linha lida do arquivo, vai ser colocado em um array de char,
               // para representar o buffer com as informações lidas de cada linha do arquivo.
               for(pos = 0; pos < buffer.length; ++pos){
                   //if((buffer[pos] != '{') && (buffer[pos] != ' ') && (buffer[pos] != '\t'))
                   caracter = Character.toString(buffer[pos]);  // Pegando um caracter, de uma posição específica do
                   // buffer, em que vai ser convertida em uma String e armazena em uma variável.
                   if(abreComentario == true && fechaComentario == true){  // Um par de abre-chave e fecha-chave foi encontrado.
                       abreComentario = false;
                       fechaComentario = false;
                   } 
                   if((caracter.matches("[\\s\t{}]"))){    // Caracter que não deve ser considerado no código fonte.
                       // Quando entrar neste if, o caracter não pode ser considerado.
                        if(caracter.matches("[{]")){ // Caso encontre um abre chave, é um comentário. Entre aqui.
                           if(!abreComentario){
                              inicio_Comentario = numero_linha;
                              abreComentario = true;  // Colocado um { . Então, tem o começo de um comentário.
                           }
                           while(true){   // Só sairá do "while true" quando encontrar um fecha-chaves ou quando chegar na última
                              // posição do buffer, independente se for ou não um fecha-chaves.
                              if((pos + 1) == buffer.length){  // Última Posição do Buffer.
                                if(Character.toString(buffer[pos]).equals("}"))  // Última Posição do Buffer, terminando com }
                                    fechaComentario = true; 
                                break;  
                              }else if(Character.toString(buffer[pos]).equals("}")){  // Qualquer Posição, exceto a Última.
                                // Entrará no if quando encontrar um fecha-cheves.
                                fechaComentario = true;
                                break;
                              }
                              ++pos;
                           }
                        }else if(caracter.matches("[}]")){
                           if((abreComentario == true) && (fechaComentario == false))
                               fechaComentario = true;
                           else{
                               System.out.println("\n\tErro na linha " + numero_linha + " >> Token } não está casando"
                               + " com o token {");
                           }
                        }
                    }else if(caracter.matches("[+-/*<>=.;:(),]") || caracter.matches("\\w")){   //  "[^\\w]"
                       if((pos + 1) != buffer.length){  // Qualquer Posição do Buffer, exceto a última posição.
                          if(Character.toString(buffer[pos + 1]).matches("[^\\w]")){
                            if(token.isEmpty()){
                              if(consultarToken(caracter + Character.toString(buffer[pos + 1])).equals("Token Desconhecido")){
                                 if(!abreComentario)
                                   tokenTest(caracter);
                                 registrar = false;
                              }else if(!abreComentario){   // Quando o Token é Conhecido. Mas, deve-se gravar quando não está em Comentário.
                                 token += caracter;
                                 registrar = true;
                              }
                            }else{   // Token pode estar com um tamanho maior ou igual a 1.
                              token += caracter;
                              if(!(consultarToken(token).equals("Token Desconhecido"))){  // No momento, o Token é Conhecido.
                                if(!(concatToken(token, pos))){
                                // O Token checado após uma possível concatenação não é válida.
                                   if(registrar && !(abreComentario)){
                                      System.out.print("\n\tToken Formado >> " + token);
                                      tokenTest(token);
                                   }
                                   token = "";
                                   registrar = false;
                                }else if(!abreComentario){ // Token Conhecido.
                                   registrar = true;
                                   //continue;
                                }
                              }else{ // Token Desconhecido.
                                 if(caracter.matches("[\\w]")){
                                    System.out.println("\n\t\tLinha 209 >> Token " + token + " não confere.");
                                    registrar = false;
                                    token = "";
                                 }
                                 /** Possa ser que se deva colocar um else, para tratar aqui, se matches("[^\\w]") */
                              }
                            }
                          }else{  // Caracter pertence a \\w
                             token += caracter;
                             if(!(concatToken(token, pos))){
                                // O Token checado após uma possível concatenação não é válida.
                                if(Character.toString(buffer[pos + 1]).matches("[\\w]")){
                                    registrar = false;
                                }else if(!abreComentario){
                                    registrar = true;
                                } 
                             }else if(!abreComentario){   // Token é Válido e não está em um comentário.
                                registrar = true;
                             }
                          }
                       }else{    // Última Posição do Buffer.
                          //token += caracter;
                          //System.out.print("\n\t\tToken formado 1 >>> " + token);
                          //test = token + caracter;
                          if(consultarToken(token + caracter).equals("Token Desconhecido")){
                             token += caracter;
                             if(caracter.matches("[\\w]")){
                                 registrar = false;
                             }else if(consultarToken(token).equals("Token Desconhecido")){
                                 //tokenTest(popCaracterToken(caracter));
                                 registrar = false;
                             }
                          }else{ // Para o Token Conhecido.
                             if(!abreComentario){
                               if(token.isEmpty() || buffer.length == 1){
                                  tokenTest(caracter);
                                  registrar = false;
                               }else if(concatToken(token, pos - 1)){   // Token Válido.
                                  tokenTest(token + caracter);
                                  registrar = false;
                               }//else{ // Token Desconhecido.
                                  //d 
                             //}
                            }
                          }
                       }
                    }
                }
                if(!token.isEmpty()){  // Entrará neste if, quando, após o fim do Buffer, se por acaso tiver algum Token
                  // que restou, seja verificado, caso esse Token não for vazio.
                  if(registrar)
                     tokenTest(token);
                  else
                     System.out.println("\n\t\tToken " + token + " não confere.");
                  token = "";
                }
            }
            if(abreComentario == true && fechaComentario == false)
                System.out.println("Erro >> O comentário aberto na linha " + inicio_Comentario + " não foi fechado.");
            System.out.println("\n\tTabela.txt  criada com sucesso pelo Analisador Léxico.");
            arquivoEntrada.close();
            arquivoLeitura.close();
            arquivoSaida.close();
            arquivoEscrita.close();
        }catch(IOException ex2){
            System.out.print("\n\tErro 2 .");
        }
    }
}