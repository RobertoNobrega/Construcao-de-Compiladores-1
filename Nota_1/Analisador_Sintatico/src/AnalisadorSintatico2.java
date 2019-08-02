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
	public void checarProgram(String token) {	//program(palavra reservada) identificador e ;
		if(token.contentEquals("program")) {
			//ler o proximo
			if(token.contentEquals("Identificador")) {
				//ler o proximo(classificação do token) segunda string do buffer, ou classifica novamente o string?
				if(token.contentEquals(";")) {
					//ler o proximo
				}
			}
		}			
		
		//passou os 3 ifs, metodoProgram está CORRETO, caso falhe em algum já lança erro
	}
	public void checarDeclaracaoVar(Token token) {
		if((token.getToken()).contentEquals("var")) {
			checarListaDecVar(token);
		}else {
			//nenhuma variavel declarada -> continue
		}
	}
	private void checarListaDecVar(Token token) {
		final List<String> Lista_tipos = Arrays.asList("integer","real","boolean");//checarTipo()
		if((token.getClassificacao()).contentEquals("Identificador")){
			
			if((token.token).contentEquals(":"))  {//throw
				if(Lista_tipos.contains(token)) {	//Tipo = integer|real|boolean
					
				}
			}else {
				//era esperado ":" Erro!!
			}
				
		}else {
			//erro, tem que ter no minimo 1 ID
		}
	}
	public void checarListaID(Token token) {
		//chamar checarID() e checarListaId
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
		if( !(Lista_tipos.contains(token.getToken())) ) throw new SintaticoException("Tipo",token);
			System.out.println("Tipo ok!");
	}
	public static void checarID(Token token) {
		if( !(token.getClassificacao()).contentEquals("Identificador") ) throw new SintaticoException("Identificador",token);
			System.out.println("Identificador ok!");
	}

	
}
