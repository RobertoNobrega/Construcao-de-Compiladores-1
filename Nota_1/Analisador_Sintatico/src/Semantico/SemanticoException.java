package Semantico;
import Sintatico.Token;

public class SemanticoException extends RuntimeException {
	
	public SemanticoException(Token token) { // variavel daclarada com o mesmo nome
		super("J� exite variavel com esse nome: " + token.getToken());
	}

}
