
public class SintaticoException extends RuntimeException{
	
	public SintaticoException(String esperado, Token recebeu){
		super(" Erro!! " + "Esperado >> " + esperado + " | Porém recebeu >> "+ recebeu.getToken());
	}
	
}