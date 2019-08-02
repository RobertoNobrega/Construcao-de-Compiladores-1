
public class Token {
	
	private String token, classificacao, linha;
	
	public Token(String token, String classificacao, String linha) {
		this.token = token;
		this.classificacao = classificacao;
		this.linha = linha;
	}
	
	public String getToken(){
		return this.token;
	}
	
	public String getClassificacao(){
		return this.classificacao;
	}
	
	public String getLinha(){
		return this.linha;
	}
	
}
