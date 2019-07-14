/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package revendo_basico;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author roberto
 */

public class Escrita_Arquivo {
    
    public static void main(String[] args){
        try{
           File arquivo = new File("src/revendo_basico/Tabela.txt");
           if(arquivo.exists())
               arquivo.delete();
           FileWriter fwr = new FileWriter("src/revendo_basico/Tabela.txt",true);
           BufferedWriter bfwr = new BufferedWriter(fwr);  // Com o objeto bfwr já instanciada, podemos escrever
           // em arquivo.
           bfwr.write("Token               Classificação               Linha"); 
           //bfwr.append(u.getLogin() + "|" + u.getNome() + "|" + u.getSenha());
           bfwr.newLine(); // Depois de escrever na linha do arquivo, faz uma quebra de linha.
           bfwr.flush(); // Precisa deste comando, para poder "autorizar" a escrita no arquivo.
           fwr.close(); // Para fechar o arquivo.
           bfwr.close(); // Para fechar o arquivo.
        }catch(IOException ex){
                
        }
    }
    
}
