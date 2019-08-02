package Testes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;

public class Teste2{
	
	public static ArrayList<String> b = new ArrayList<String>();
	public static FileReader arquivo;
	public static BufferedReader arquivo_leitura;
	
	public static void armazeneTokenBuffer(){
		String string_temp[] = {"","",""};
		int caracter, posicao = 0;
		boolean semaforo = false;
		try{
		   while(((caracter = arquivo_leitura.read()) != -1) && ((char)caracter != '\n')){
		      if((char)caracter != '\t'){
		         string_temp[posicao] += (char)caracter;
		         if(!semaforo)
		           semaforo = true;
		      }else if(semaforo){
		    	 ++posicao;
		    	 semaforo = false;
		      }
		   }
		}catch(IOException d){
			
		}
		System.out.println(string_temp[0]);
		System.out.println(string_temp[1]);
		System.out.println(string_temp[2]);
	}
		
	public static void main(String[] args){
		try {
			  arquivo = new FileReader(new File("src/Tabela.txt"));
			  arquivo_leitura = new BufferedReader(arquivo);
			  arquivo_leitura.readLine();
			  armazeneTokenBuffer();
		   }catch(IOException e) {
			   
		   }
	}
}
