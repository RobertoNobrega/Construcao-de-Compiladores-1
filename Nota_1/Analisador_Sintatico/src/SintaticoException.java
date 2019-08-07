package Sintatico;


public class SintaticoException extends RuntimeException{
	
	public SintaticoException(String esperado, Token recebeu){
		super( " Erro!! " + "Esperado >> " + esperado + " | Porem recebeu >> "+ recebeu.getToken()+ " linha: " + recebeu.getLinha());
	}
	
}