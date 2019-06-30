package lexico;
import lexico.padrao;
import lexico.Token;

public class classificador {

	public static void main(String[] args) {
		
		
	}
	
	public static String tipoToken(String token) {
		if(padrao.PalavrasReservadas.contains(token)) {		//tipo Palavra reservada Lista
			return "Palavra Reservada";
		}else if(token.matches(padrao.identificador)) { 	//tipo Identificador expressao
			return "Identificador";
		}else if(token.matches(padrao.inteiro)) {			//tipo Numero Inteiro expressao
			return "Numero inteiro";	
		}else if(token.matches(padrao.real)) {					//tipo numero real Expressao
			return "Numero real";
		}else if(padrao.Delimitadores.contains(token)) {
			return "Delimitador";
		}else if(padrao.Atribuição.contains(token)) {
			return "Atribuinção";
		}else if(padrao.OperadoresRelacionais.contains(token)) {
			return "Operador relacional";
		}else if(padrao.OperadoresAditivos.contains(token)) {
			return "Operador Aditivo";
		}else if(padrao.OperadoresMultiplicativos.contains(token))
			return "Operador multiplicativo";
		return "desconhecido";
	}

}
