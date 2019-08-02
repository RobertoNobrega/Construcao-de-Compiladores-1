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
		if((token.getToken()).contentEquals("program")) throw new SintaticoException("program", token);
			//ler o proximo
			if((token.getClassificacao()).contentEquals("Identificador"));
				//ler o proximo(classificação do token) segunda string do buffer, ou classifica novamente o string?
				if((token.getToken()).contentEquals(";"));
		}			//passou os 3 ifs, metodoProgram está CORRETO, caso falhe em algum já lança erro
		
	public void checarDeclaracaoVar(Token token) {
		if((token.getToken()).contentEquals("var")) {
			checarListaDecVar(token);
		}else {
			//nenhuma variavel declarada -> continue
		}
	}
	private void checarListaDecVar(Token token) {//  valor2: integer;
												//   begin
		checarListaID(token);
		if( !(token.getToken()).contentEquals(":") ) throw new SintaticoException(":", token);
			checarTipo(token);
			if( !(token.getToken()).contentEquals(";") ) throw new SintaticoException(";", token);
				//pode ser um ID ou pode passar pra proxima parte ->Se token = ID então checarListaDecVar; se não continui
				if( (token.getClassificacao()).contentEquals("Identificador") ) {
					checarListaDecVar(token);
				}else {
					//Continue
				}
	}
	public void checarListaID(Token token) {
		//chamar checarID() e checarListaID
		checarID(token);
		if( (token.token).contentEquals(",") ) { //mais de um identificador
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

	
}
