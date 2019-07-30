package Testes;

import java.util.ArrayList;

public class Teste1 {
	public static void main(String[] args){
		int pos = 0;
		ArrayList<String> buffer = new ArrayList<String>();
		char buf[];
		String linha_lida = "\t" + "program" + "\t\t\t" + "Palavra-Chave" + "\t\t\t" + "1";
		String string_concatenada = "";
		buf = linha_lida.toCharArray();
		for(int i = 0; i < buf.length; ++i){
			if(buf[i] == '\t'){
			   continue;
			}else{
			   string_concatenada += Character.toString(buf[i]);
			   if(i + 1 == buf.length){   // Última POsição do Buffer.
				  buffer.add(string_concatenada);
			   }else if(buf[i + 1] == '\t'){ // Qualquer Posição, exceto a última.
				   buffer.add(string_concatenada);
				   string_concatenada = "";
			   }
			}
		}
		for(String obj : buffer)
		   System.out.print(" " + obj);
	}
}
