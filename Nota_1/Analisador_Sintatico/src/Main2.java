package Sintatico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Main2 {
	
	/*private static ArrayList<Token> lista_tokens;
	private FileReader arquivo;
	private BufferedReader arquivo_leitura;
	
	public void comeco(){
		String caminho = "C:\\Users\\Alisson\\eclipse-workspace\\Analisador_Sintatico\\src\\Tabela.txt";
		try{
		   arquivo = new FileReader(new File(caminho));
		   arquivo_leitura = new BufferedReader(arquivo);
		   arquivo_leitura.readLine();
		   lista_tokens = new ArrayList<Token>();
		   preencherBuffer();
		}catch(FileNotFoundException e){
			System.out.print("\n\tArquivo N�o Achado.");
		}catch(IOException e){
			System.out.print("\n\tErro no Arquivo de Leitura.");
		}
	}*/
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	/*  Token token = new Token("njw", "Identificador","5");
		Token token2 = new Token("integer", "Palavra-chave","4");
		Token token3 = new Token("token3", "Identificador","5");
		Token token4 = new Token("token4", "Identificador","5");
		Token token1 = new Token(",", "Delimitador", "5");
		*/
		//checarTipo(token);
		//checarID(token);
		//checarID(token2);
		
		//checarProgram()
		/*
		Token token1 = new Token("program", "Palavra-chave","0");
		Token token2 = new Token("testarPrograma", "Identificador","0");
		Token token3 = new Token(";", "Delimitador","0");
		Token token4 = new Token("var", "Palavra-chave","1");
		Token token5 = new Token("valor1", "Identificador","1");
		Token token6 = new Token(":", "Delimitador","1");
		Token token7 = new Token("Integer", "Palavra-chave","1");
		Token token8 = new Token(";", "Delimitador","1");
		Token token9 = new Token(".", "Delimitador","2");
		
		ArrayList<Token> lista_tokens = new ArrayList<Token>();
		lista_tokens.add(token1);
		lista_tokens.add(token2);
		lista_tokens.add(token3);
		lista_tokens.add(token4);
		lista_tokens.add(token5);
		lista_tokens.add(token6);
		lista_tokens.add(token7);
		lista_tokens.add(token8);
		lista_tokens.add(token9);
		*/
	/*	Main2 main2 = new Main2();
		main2.comeco();
		Iterator<Token> lista_I_tokens = lista_tokens.iterator();
		while(lista_I_tokens.hasNext()) {
			Token prox = lista_I_tokens.next();
			System.out.println("Token: "+ prox.getToken());
			System.out.println("Classificação: "+ prox.getClassificacao());
		}*/
	/*	for(int i=0;i<lista_tokens.size();i++) {
			System.out.println("mostrar Token: " + (lista_tokens.get(i)).getToken() );
			System.out.println("mostrar Classifica��o: " + (lista_tokens.get(i)).getClassificacao() );
			System.out.println("mostrar linha: " + (lista_tokens.get(i)).getLinha() );
		}*/
		AnalisadorSintatico2 sintatico = new AnalisadorSintatico2("C:\\\\Users\\\\Alisson\\\\eclipse-workspace\\\\Analisador_Sintatico\\\\src\\\\Tabela.txt");
		
		//ChecarClassificaoAndTipo(token2);
		System.out.println("Fim!");
		
	}
	
	/*public void preencherBuffer() throws IOException{
	    String[] strings = {"","",""};
	    int caracter, posicao = 0;
	    boolean semaforo = false;
	    while((caracter = arquivo_leitura.read()) != -1){  // Lendo até o Fim do Arquivo.
	       if((((char)caracter) != '\t') && ((char)caracter) != '\n' && ((char)caracter) != ' '){
	          strings[posicao] += (char)caracter;
	          if(!semaforo)
	             semaforo = true;
	       }else if(semaforo){
	          if(posicao < strings.length - 1){
	             ++posicao;
	          }else{
	        	 posicao = 0;
	        	 lista_tokens.add(new Token(strings[0],strings[1],strings[2]));
	        	 strings[0] = "";
	        	 strings[1] = "";
	        	 strings[2] = "";
	          }
	          semaforo = false;
	       }
	    }
	    arquivo.close();
	    arquivo_leitura.close();
	}
	
	/*public static Token proximoToken(ArrayList<Token> list) { //Recebe a lista de token ; retorna o proximo token da lista
		//int index = list.;
		Token proxToken = list.get(index);
		index++;
		return proxToken; 
	}*/
	/*public void checarListaID(Token token) { //ID, ID, ID,
		checarID(token);
		if( (token.token).contentEquals(",") ) { //mais de um identificador
			checarListaID(token);
		}else {
			//continue apenas um ID;
		}
	}
	public static void ChecarClassificaoAndTipo(Token token) {
		if( !(token.getClassificacao().contentEquals("Palavra-chave")) ) throw new SintaticoException("Palavra-chave", token);
			checarTipo(token);
			System.out.println("Classificao e Tipo OK!");
		
	}
	public static void checarID(Token token) {
		if( !(token.getClassificacao()).contentEquals("Identificador") ) throw new SintaticoException("Identificador",token);
			System.out.println("Identificador ok!");
			//funcaoProximo()
	}
	
	public static void checarTipo(Token token) {
		final List<String> Lista_tipos = Arrays.asList("integer","real","boolean");
		if( !(Lista_tipos.contains(token.getToken())) ) throw new SintaticoException("Tipo",token);
			System.out.println("Tipo ok!");
	}*/
	// Teste checarProgram();
	/*
	public void checarProgram(Token token){	//program(palavra reservada) identificador e ;
	   try{
		if( !(token.getToken()).contentEquals("program") ) throw new SintaticoException("program", token);
			//ler o proximo
			System.out.println("program lido!");
			checarID(token);
			//ler o proximo(classifica��o do token) segunda string do buffer, ou classifica novamente o string?
			if( !(token.getToken()).contentEquals(";") ) throw new SintaticoException(";", token);
				checarDeclaracaoVar(token);
				//checarDecSubprograma()
				//checarComandoComp()
				if( !(token.getToken()).contentEquals(".") ) throw new SintaticoException(".",token);	//Final do arquivo
	   }catch(SintaticoException e){
		   System.out.println(e);
	   }
	}			//passou os 3 ifs, metodoProgram est� CORRETO, caso falhe em algum j� lan�a erro
		
	public void checarDeclaracaoVar(Token token) {
		if((token.getToken()).contentEquals("var")) {
			System.out.println("var encontrado!");
			checarListaDecVar(token);
		}else {
			System.out.println("sem var!");
			//nenhuma variavel declarada -> continue
		}
	}
	
	private void checarListaDecVar(Token token){
		checarListaID(token);
	   try{
		if( !(token.getToken()).contentEquals(":") ) throw new SintaticoException(":", token);
			checarTipo(token);
			if( !(token.getToken()).contentEquals(";") ) throw new SintaticoException(";", token);
				//pode ser um ID ou pode passar pra proxima parte ->Se token = ID ent�o checarListaDecVar; se n�o continui
				if( (token.getClassificacao()).contentEquals("Identificador") ) {
					checarListaDecVar(token);
				}else {
					//Continue
				}
	   }catch(SintaticoException e) {
	       System.out.println(e);
	   }
	}
	
	public void checarListaID(Token token){
		//chamar checarID() e checarListaID
		checarID(token);
		if( (token.getToken()).contentEquals(",") ) { //mais de um identificador
			//proximo armazeneTokenBuffer()
			checarListaID(token);
		}else {
			//continue apenas um ID;
		}
	}
	
	public static void checarTipo(Token token){
		final List<String> Lista_tipos = Arrays.asList("integer","real","boolean");
	   try{
		if( !(Lista_tipos.contains(token.getToken())) ) throw new SintaticoException("integer || real || boolean",token);
			System.out.println("Tipo ok!");
			//armazeneTokenBuffer()
	   }catch(SintaticoException e){
	       System.out.println(e);
	   }
	}
	
	public static void checarID(Token token){
	   try{
		if( !(token.getClassificacao()).contentEquals("Identificador") ) throw new SintaticoException("Identificador",token);
			System.out.println("Identificador ok!");
			//armazeneTokenBuffer();
	   }catch(SintaticoException e){
	       System.out.println(e);
	   }
	}*/
	
}
