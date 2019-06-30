package lexico;
import java.util.Arrays;
import java.util.List;

public class padrao {

	public static List<String> PalavrasReservadas = Arrays.asList("program", "var", "integer", "real", "boolean",
			"procedure", "begin", "end", "if", "then", "else", "while", "do", "not");
	
	static String identificador = "[a-zA-Z]*_*[0-9]*";
	static String inteiro = "[0-9]*";
	static String real = "[0-9]+.[0-9]*";
	public static List<String> Delimitadores = Arrays.asList(";", ".", ":", ",", "()");
	static String parenteses = "[(][a-zA-Z]*[)]";
	public static List<String> Atribuição = Arrays.asList(":=");
	public static List<String> OperadoresRelacionais = Arrays.asList("=", "<", ">", "<=", ">=", "<>");
	public static List<String> OperadoresAditivos = Arrays.asList("+", "-", "or");
	public static List<String> OperadoresMultiplicativos = Arrays.asList("*", "/", "and");
	static String ComentarioAberto = "{";
	static String ComentarioFechado= "}";
	
	
}