import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Analisador_Sintatico{
	
	private ArrayList<Token> buffer;
	private FileReader arquivo;
	private BufferedReader arquivo_leitura;
	private boolean flagArquivo = true;
	
	public Analisador_Sintatico(){
		String caminho = "src/Tabela.txt"; // Caminho, onde se encontra o arquivo para ser lido.
		try {
			buffer = new ArrayList<Token>();
			arquivo = new FileReader(new File(caminho));
			arquivo_leitura = new BufferedReader(arquivo);
			arquivo_leitura.readLine(); // A Primeira Linha do Arquivo não é para ser considerada. Vai para a próxima linha
			// do arquivo.
		}catch(IOException e) {
			System.out.println("\n\tErro na parte da leitura do arquivo da Tabela do Analisador Léxico >> " + e);
			flagArquivo = false;
		}
	}
	
	public void armazeneTokenBuffer(){
		String string_temp[] = {"","",""};
		int caracter, posicao = 0;
		boolean semaforo = false;
		try{
		   while(((caracter = arquivo_leitura.read()) != -1) && ((char)caracter != '\n')){
		      if(((char)caracter != '\t') && (posicao < 3)){
		         string_temp[posicao] += (char)caracter;
		         if(!semaforo)
		           semaforo = true;
		      }else if(semaforo){
		    	 ++posicao;
		    	 semaforo = false;
		      }
		   }
		   if(string_temp[0] != "" && string_temp[1] != "" && string_temp[2] != "")
		     buffer.add(new Token(string_temp[0],string_temp[1],string_temp[2]));  // Instanciando um objeto da classe Token e armazenando no Buffer
		   // para poder ser analisado.
		}catch(IOException d){
			System.out.print("\n\t\tErro ao tentar armazenar Token no Buffer.");
		}
	}
	
	public boolean lista_de_identificadores() {
		
	}
	
	public boolean lista_Declaracoes_Variaveis(){
		lista_de_identificadores();
	}
	
	public boolean declaracoes_Variaveis(){
		armazeneTokenBuffer();
		if((buffer.isEmpty() == false) && (buffer.get(3).getToken().equals("var"))){
			buffer.remove(0);
			lista_Declaracoes_Variaveis();
		}else{
		   return false;   // Possa ser que tenha recebido epsilon ou um token diferente de var. 
		}
		return true;  // Recebeu var  .
	}
	
	public void programa(){
		armazeneTokenBuffer();
		if((buffer.isEmpty() == false) && (buffer.get(0).getToken().equals("program"))){
		   // Objeto na posição zero do buffer é o program
		   buffer.remove(0);
		   armazeneTokenBuffer();
		   if((buffer.isEmpty() == false) && (buffer.get(1).getClassificacao().equals("Identificador"))){
			   buffer.remove(0);
			   armazeneTokenBuffer();
			   if((buffer.isEmpty() == false) && (buffer.get(2).getToken().equals(";"))){
			      // Recebeu    program id;
				  //System.out.println("\n\t\tPassou.");   // OK.
				  buffer.remove(0);
				  declaracoes_Variaveis();
			   }else{
			      System.out.print("\n\t\tErro 3 >> Estava esperando ;");
			   }
		   }else{
		      System.out.print("\n\t\tErro 2 >> Estava esperando um Identificador.");
		   }
		}else{
		   System.out.print("\n\t\tErro 1 >> Estava esperando o token program .");
		}
	}
	
	public void executeAnalisador(){
		if(!flagArquivo)
		   return;
		programa();
	}
}
