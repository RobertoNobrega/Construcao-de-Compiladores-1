package Sintatico;
import java.util.Arrays;
import java.util.List;

public class AnalisadorSintatico2 {
	
	public String token1, token2; //ler um par de string token e classificação
	public Token token;
	//metodo para ler e verificar se a primeiro token e "program" e depois identificador e ;
	
	public void proximo() {
		//pular para o proximo token
		//ler proxima linha
		//exemplos de linhas
		//program		Palavra-Chave			1
		//teste			Identificador			1
	}
	public void checarProgram(Token token) {	//program(palavra reservada) identificador e ;
		if( !(token.getToken()).contentEquals("program") ) throw new SintaticoException("program", token);
			//ler o proximo
			System.out.println("program lido!");
			checarID(token);
			//ler o proximo(classificação do token) segunda string do buffer, ou classifica novamente o string?
			if( !(token.getToken()).contentEquals(";") ) throw new SintaticoException(";", token);
				checarDeclaracaoVar(token);
				//checarDecSubprograma()
				//checarComandoComp()
				//if( !(token.getToken()).contentEquals(".") ) throw new SintaticoException(".",token);	//Final do arquivo
	}			//passou os 3 ifs, metodoProgram está CORRETO, caso falhe em algum já lança erro
		
	public void checarDeclaracaoVar(Token token) {
		if((token.getToken()).contentEquals("var")) {
			System.out.println("var encontrado!");
			checarListaDecVar(token);
		}else {
			System.out.println("sem var!");
			//nenhuma variavel declarada -> continue
		}
	}
	private void checarListaDecVar(Token token) {
		checarListaID(token);
		if( !(token.getToken()).contentEquals(":") ) throw new SintaticoException(":", token);
			checarTipo(token);
			if( !(token.getToken()).contentEquals(";") ) throw new SintaticoException(";", token);
				//pode ser um ID ou pode passar pra proxima parte ->Se token = ID então checarListaDecVar; se não continui
				if( (token.getClassificacao()).contentEquals("Identificador") ) { //pode ou nao ter outro ID
					checarListaDecVar(token);
				}else {
					//Continue
				}
	}
	public void checarListaID(Token token) {
		//chamar checarID() e checarListaID
		checarID(token);
		if( (token.getToken()).contentEquals(",") ) { //mais de um identificador
			//proximo armazeneTokenBuffer()
			checarListaID(token);
		}else {
			//continue apenas um ID;
		}
	}
	public static void checarTipo(Token token) {
		final List<String> Lista_tipos = Arrays.asList("integer","real","boolean");
		if( !(Lista_tipos.contains(token.getToken())) ) throw new SintaticoException("integer || real || boolean",token);
			System.out.println("Tipo ok!");
			//armazeneTokenBuffer()
	}
	public static void checarID(Token token) {
		if( !(token.getClassificacao()).contentEquals("Identificador") ) throw new SintaticoException("Identificador",token);
			System.out.println("Identificador ok!");
			//armazeneTokenBuffer();
	}
	public void checarDeclaracaoSubprogramas(Token token) {//certo?
		checarDeclaracaoSubprograma(token); //tem o primeiro procedure obrigatorio
		if( !(token.getToken()).contentEquals(";") ) throw new SintaticoException(";", token);
			checarDeclaracaoSubprogramas(token);
	}
	public void checarDeclaracaoSubprograma(Token token) {
		if( !(token.getToken()).contentEquals("procedure") ) throw new SintaticoException("procedure", token);//tirar excecao,se nao for procedure, será vazio
			checarID(token);
			checarArgumentos(token);
			if( !(token.getToken()).contentEquals(";") ) throw new SintaticoException(";", token);
				checarDeclaracaoVar(token);
				//
	}
	private void checarArgumentos(Token token) {
		if( !(token.getToken()).contentEquals("(") ) throw new SintaticoException("(", token);
		//
	}

	
}
