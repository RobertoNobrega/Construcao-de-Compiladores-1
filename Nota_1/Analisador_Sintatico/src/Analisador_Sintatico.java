import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Analisador_Sintatico{
	
	private ArrayList<String> buffer;
	private ArrayList<Integer> linha_token_classificacao;
	private FileReader arquivo;
	private BufferedReader arquivo_leitura;
	private boolean flagArquivo = true;
	
	public Analisador_Sintatico(){
		String caminho = "src/Tabela.txt"; // Caminho, onde se encontra o arquivo para ser lido.
		try {
			buffer = new ArrayList<String>();
			linha_token_classificacao = new ArrayList<Integer>();
			arquivo = new FileReader(new File(caminho));
			arquivo_leitura = new BufferedReader(arquivo);
			arquivo_leitura.readLine(); // A Primeira Linha do Arquivo não é para ser considerada. Vai para a próxima linha
			// do arquivo.
		}catch(IOException e) {
			System.out.println("\n\tErro ao tentar ler o arquivo >> " + e);
			flagArquivo = false;
		}
	}
	
	public int preencherBuffer(String linha_arquivo){
		/* Assumindo que linha_arquivo não é vazio. */
		String string_concatenada = "";
		char buf[] = linha_arquivo.toCharArray();
		for(int i = 0; i < buf.length; ++i){
			if(buf[i] == '\t'){
			   continue;
			}else{
			   string_concatenada += Character.toString(buf[i]);
			   if(i + 1 == buf.length){   // Última Posição do Buffer.
				  break; // Significa que não está armazenar no Buffer o valor da linha, onde se encontra o Token
				  // e sua Classificação.
			   }else if(buf[i + 1] == '\t'){ // Qualquer Posição, exceto a última.
				   buffer.add(string_concatenada);  // Adicionando sempre no fim do buffer.
				   string_concatenada = "";
			   }
			}
		}
		return Integer.parseInt(string_concatenada);  // Vai retornar o número da linha, onde se encontra o Token e sua Classificação.
	}
	
	public void metodo_Declaracao_variaveis(){
		
	}
	
	public void metodo_Programa(){ /* Este método segue a Especificação da Gramática do Prof. Clauirton Siebra. */
		String linha_arquivo;
		int valor_linha = 0;
		try{
		  while((linha_arquivo = arquivo_leitura.readLine()) != null){
		     //linha_arquivo = arquivo_leitura.readLine();// Lendo uma linha do Arquivo da Tabela do Analisador Léxico.
		     linha_token_classificacao.add(preencherBuffer(linha_arquivo));
		     if(linha_token_classificacao.size() == 1){ // Para o caso quando o valor_linha == 0 .
		    	valor_linha = linha_token_classificacao.get(0);
		    	continue;
		     }else if(valor_linha == linha_token_classificacao.get(linha_token_classificacao.size() - 1)){ // Como se trata 
		    	// da leitura em uma mesma linha do Código em Ling. Pascal, continua a leitura.
		    	continue;
		     }
		     // Se não entrar no último if acima, significa que foi lida um Token de uma outra linha.
		     //valor_linha = linha_token_classificacao.get(linha_token_classificacao.size() - 1);
		     if((buffer.size() - 2) == 6){ // Por enquanto, estamos indo bem.
		    	buffer.remove(1); // Eliminando o conteúdo chamado Palavra-Chave.
		    	buffer.remove(2); // Eliminando o conteúdo, que representa um nome para um Identificador.
		    	buffer.remove(5); // Eliminando o conteúdo, que representa um Delimitador.
		    	if(buffer.get(0).equals("program")){ // Especificado pela gramática do método programa.
		    	   buffer.remove(0);  // Quando remover a palavra program , significa que ela no Buffer foi "consumida".
		    	   if(buffer.get(0).equals("Identificador")){
		    		 buffer.remove(0);  // Consumido um Identificador no Buffer.
		    		 if(buffer.get(0).equals(";")) {
		    		   buffer.remove(0);  // Consumido um ; no Buffer.
		    		   // A Partir daqui, devemos tratar a parte de declarações de variáveis, de subprogramas, comando composto
		    		   // e o delimitador ponto final.
		    		   /** Terei que continuar nesse ponto. */
		    		   
		    		 }else{
		    		   System.out.print("\n\t\tErro. >> Esperado um ; ");
					   return; 
		    		 }
		    	   }else {
		    		  System.out.print("\n\t\tErro. >> Esperado um Identificador.");
					  return;
		    	   }
		    	}else{
		    	   System.out.print("\n\t\tErro. >> Esperado a palavra program .");
				   return;
		    	}
		     }else {
		        System.out.print("\n\t\tErro 2 >> Tamanho do Buffer não Confere (igual a seis).");
				return;
		     }
		  }
		  if(linha_arquivo == null && valor_linha == 0) {
			  System.out.print("\n\t\tErro >> Tabela do Analisador Léxico não está preenchida.");
			  return;
		  }
		}catch(IOException e){
		   System.out.println("\n\t\tErro no Arquivo >>> " + e);
	    }
	}
	
	public void comeceAnalisador_Sintatico(){
		String linha_arquivo; // Vai receber, para cada linha lida por inteiro, a String que corresponde ao conteúdo da linha
		// do Arquivo.
		int linhaAnterior = 0; // Iniciamos a linha com o valor 0 .
		if(!flagArquivo){
			System.out.println("\n\tProblema no Arquivo.");
			return;
		}
		
	}
	
	public void exibir() {
		for(String b : buffer)
		   System.out.print(" " + b);
		System.out.println("\n");
		for(int i = 0; i < linha_token_classificacao.size(); ++i)
		   System.out.print(" " + linha_token_classificacao.get(i));
	}
}
