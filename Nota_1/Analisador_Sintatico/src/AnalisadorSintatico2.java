package Sintatico;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnalisadorSintatico2 {
	
	public String token1, token2; //ler um par de string token e classificação
	public Token token;
	//metodo para ler e verificar se a primeiro token e "program" e depois identificador e ;
	private ArrayList<Token> buffer;
	private FileReader arquivo;
	private BufferedReader arquivo_leitura;
	private boolean flagArquivo;
	
	public AnalisadorSintatico2(String caminho) {
	 try{
	      arquivo = new FileReader(new File(caminho));
	      arquivo_leitura = new BufferedReader(arquivo);
	      flagArquivo = true;
	      buffer = new ArrayList<Token>();
	      arquivo_leitura.readLine();
	      preencherBuffer();
	      checarProgram(buffer);
	      checarComandoComp(buffer);
	   }catch(FileNotFoundException e){
		  System.out.println("\n\t\tErro 1 >> " + e);
	   }catch(IOException e) {
		  System.out.println("\n\t\tErro 2 >> " + e); 
	   }
	}
	
	public void preencherBuffer() throws IOException{
	    String[] strings = {"","",""};
	    int caracter, posicao = 0;
	    boolean semaforo = false;
	    while((caracter = arquivo_leitura.read()) != -1){  // Lendo ate o Fim do Arquivo.
	       if((((char)caracter) != '\t') && ((char)caracter) != '\n' && ((char)caracter) != ' '){
	          strings[posicao] += (char)caracter;
	          if(!semaforo)
	             semaforo = true;
	       }else if(semaforo){
	          if(posicao < strings.length - 1){
	             ++posicao;
	          }else{
	        	 posicao = 0;
	        	 buffer.add(new Token(strings[0],strings[1],strings[2]));
	        	 strings[0] = "";
	        	 strings[1] = "";
	        	 strings[2] = "";
	          }
	          semaforo = false;
	       }
	    }
	}
	
	
	
	public void checarProgram(ArrayList<Token> buffer) {	//program(palavra reservada) identificador e ;
		if( !( (buffer.get(0).getToken()) ).contentEquals("program") ) throw new SintaticoException("program", buffer.get(0));
			//ler o proximo buffer+1
			buffer.remove(0);
			System.out.println("program lido!");
			checarID(buffer);
			//ler o proximo(classificação do token) segunda string do buffer, ou classifica novamente o string?
			buffer.remove(0);
			if( !((buffer.get(0)).getToken()).contentEquals(";") ) throw new SintaticoException(";", buffer.get(0));
			buffer.remove(0);
			checarDeclaracaoVar(buffer);
				//checarDecSubprograma()
				//checarComandoComp()
			if( ((buffer.get(0)).getToken()).contentEquals("begin") ) {
				System.out.println("Fim primeira parte");
			}
			if( !((buffer.get(0)).getToken()).contentEquals(".") ) throw new SintaticoException(".",buffer.get(0));	//Final do arquivo
	}			//passou os 3 ifs, metodoProgram está CORRETO, caso falhe em algum já lança erro
		
	public void checarDeclaracaoVar(ArrayList<Token> buffer) {
		if(( (buffer.get(0)).getToken()).contentEquals("var")) {
			System.out.println("var encontrado!");
			buffer.remove(0);
			checarListaDecVar(buffer);
		}else {
			System.out.println("sem var!");
			//nenhuma variavel declarada -> continue
		}
	}
	private void checarListaDecVar(ArrayList<Token> buffer) {
		checarListaID(buffer);
		buffer.remove(0);
		if( !((buffer.get(0)).getToken()).contentEquals(":") ) throw new SintaticoException(":", token);
			buffer.remove(0);
			checarTipo(buffer);
			buffer.remove(0);
			if( !((buffer.get(0)).getToken()).contentEquals(";") ) throw new SintaticoException(";", token);
				//pode ser um ID ou pode passar pra proxima parte ->Se token = ID então checarListaDecVar; se não continui
				buffer.remove(0);
				if( ((buffer.get(0)).getClassificacao()).contentEquals("Identificador") ) { //pode ou nao ter outro ID
					checarListaDecVar(buffer);
				}else {
					//Continue
					System.out.println("Else checarListaDecVar linha 102");
				}
	}
	public void checarListaID(ArrayList<Token> buffer) {
		//chamar checarID() e checarListaID
		checarID(buffer);
		if( ((buffer.get(0)).getToken()).contentEquals(",") ) { //mais de um identificador
			//proximo armazeneTokenBuffer()
			checarListaID(buffer);
		}else {
			//continue apenas um ID;
		}
	}
	public static void checarTipo(ArrayList<Token> buffer) {
		final List<String> Lista_tipos = Arrays.asList("integer","real","boolean");
		if( !(Lista_tipos.contains((buffer.get(0)).getToken())) ) throw new SintaticoException("integer || real || boolean",buffer.get(0));
			System.out.println("Tipo ok!");
			//armazeneTokenBuffer()
	}
	public static void checarID(ArrayList<Token> buffer) {
		if( !((buffer.get(0)).getClassificacao()).contentEquals("Identificador") ) throw new SintaticoException("Identificador",buffer.get(0));
			System.out.println("Identificador ok!");
			//armazeneTokenBuffer();
	}
	public void checarDeclaracaoSubprogramas(ArrayList<Token> buffer) {//certo?
		if( (buffer.get(0)).getToken().contentEquals("procedure") ) {
			buffer.remove(0);
			checarDeclaracaoSubprograma(buffer.get(0)); //tem o primeiro procedure obrigatorio
			if( !((buffer.get(0)).getToken()).contentEquals(";") ) throw new SintaticoException(";", token);
				checarDeclaracaoSubprogramas(buffer);
		}else {
			//continue
		}
		
	}
	public void checarDeclaracaoSubprograma(Token token) {
		if( !(token.getToken()).contentEquals("procedure") ) throw new SintaticoException("procedure", token);//tirar excecao,se nao for procedure, será vazio
			checarID(buffer);
			checarArgumentos(token);
			if( !(token.getToken()).contentEquals(";") ) throw new SintaticoException(";", token);
				//checarDeclaracaoVar(token);
				//
	}
	public void checarArgumentos(Token token) {
		if( !(token.getToken()).contentEquals("(") ) throw new SintaticoException("(", token);
		//
	}
	public void checharListaParametros() {
		checarListaID(buffer);
		//
	}
	public void checarComandoComp(ArrayList<Token> buffer) {
		if( !((buffer.get(0)).getToken()).contentEquals("begin") ) throw new SintaticoException("begin", token);
			buffer.remove(0);
			checarComandosOpc(buffer);
	}

	public void checarComandosOpc(ArrayList<Token> buffer2) {
		checarListaComandos();
		
	}

	private void checarListaComandos() {
		// TODO Auto-generated method stub
		
	}

	
}
