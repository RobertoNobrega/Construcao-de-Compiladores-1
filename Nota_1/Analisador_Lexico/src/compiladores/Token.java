package lexico;

public class Token {

	private String conteudo;
	private String tipo;
	private int linha;
	
	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getLinha() {
		return linha;
	}

	public void setLinha(int linha) {
		this.linha = linha;
	}

	public Token(String conteudo,String tipo, int linha) {
		this.conteudo = conteudo;
		this.tipo = tipo;
		this.linha = linha;
	}
	public Token(String conteudo) {
		this.conteudo = conteudo;
	}
	
}
