
public class SintaticoException extends RuntimeException{
	
	public SintaticoException(String esperado, String recebeu){
		super(" Erro!! " + "Esperado: " + esperado + " | Porém recebeu:"+ recebeu);
	}
	
}