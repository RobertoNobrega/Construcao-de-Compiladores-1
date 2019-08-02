
public class SintaticoException extends RuntimeException{

	
	public SintaticoException(String esperado, Token token) {
		super("Erro!! " + "Esperado: " + esperado+ " Recebeu:"+ token.getToken());
	}
}
