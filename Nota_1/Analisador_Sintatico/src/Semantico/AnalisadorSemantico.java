package Semantico;

public class AnalisadorSemantico {

	
	public void abrirEscopo() {
		//Iniciar a pilha das variaveis locais
	}
	public void fecharEscopo() {
		//desempilhar todas as variaveis locais
	}
	public void tokenIguais() {
		//verificar se os nomes dos token s�o iguais, caso sim, lan�ar excess�o semantica
	}
	public void tokenExistente() {
		//verificar se o token usado j� est� declarado na lista de variaveis do escopo
	}
	public void empilhar() {
		//empilhar at� aparecer um ";"
	}
	public void desempilhar() {
		//desempilhar ate pilha vazia = -1
	}
	public void verificarAtribui��o() { //  ("integer","real") ->	("inteiro")
										//	("boolean") -> ("true", "false")
		//boolean recebendo numero...
		//Usar pilha 
		//Primeiro a sair da pilha vai ser um delimitador ;
		//depois o valor passado pra variavel, guardar a classifica��o de -> tokenValor 
		//o proximo � uma atribui��o :=
		//o ultimo � ( token.getClassificacao().equals("identificador") ) -> tokenVariavel
		//se (tokenValor.getClassificacao() )
		
	}	
	public void verificarOperacao() {
		//Usar pilha 
		//Primeiro a sair da pilha vai ser um delimitador ;
		//pegar as 3 checar os tipos e chamar verificarOperador() para saber qual o tipo de operador e saber quais as variaveis 
	}
	public void verificarOperador() {//precisa?
		//pegar o operador e checar as variaveis
	}
	
	
	
	
	
	
	
}
