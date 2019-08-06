import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class ASintatico{
	
	private ArrayList<Token> buffer;
	private FileReader arquivo;
	private BufferedReader arquivo_leitura;
	private boolean flagArquivo;
	
	public ASintatico(String caminho){
	   try{
	      arquivo = new FileReader(new File(caminho));
	      arquivo_leitura = new BufferedReader(arquivo);
	      flagArquivo = true;
	      buffer = new ArrayList<Token>();
	      arquivo_leitura.readLine();
	      preencherBuffer();
	   }catch(FileNotFoundException e){
		  System.out.println("\n\t\tErro 1 >> " + e);
	   }catch(IOException e) {
		  System.out.println("\n\t\tErro 2 >> " + e); 
	   }
	}
	
	public void preencherBuffer() throws IOException{
	    String[] strings = {"","",""};
	    int caracter, posicao = 0;
	    boolean semaforo = false;
	    while((caracter = arquivo_leitura.read()) != -1){  // Lendo até o Fim do Arquivo.
	       if((((char)caracter) != '\t') && ((char)caracter) != '\n' && ((char)caracter) != ' '){
	          strings[posicao] += (char)caracter;
	          if(!semaforo)
	             semaforo = true;
	       }else if(semaforo){
	          if(posicao < strings.length - 1){
	             ++posicao;
	          }else{
	        	 posicao = 0;
	        	 buffer.add(new Token(strings[0],strings[1],strings[2]));
	        	 strings[0] = "";
	        	 strings[1] = "";
	        	 strings[2] = "";
	          }
	          semaforo = false;
	       }
	    }
	}
	
	public int verificacao(String entrada, boolean opcao){
		if(buffer.isEmpty())
		   return 2;   // Buffer Vazio.
		if(opcao){    // Verificando o Token.
		  if(buffer.get(0).getToken().equals(entrada))
		    return 1;  // Verdade >>  Os Tokens são iguais.
		}else if(buffer.get(0).getClassificacao().equals(entrada))
		  return 1;  // Verdade >>  As Classificações são iguais.
		return 0;  // Diferentes.
	}
	
									/* ORDEM ALFABÉTICA DOS MÉTODOS DAS GRAMÁTICAS. */
	
	/************************************************************************************************************/
	// 			Métodos que iniciam com a letra   a
	
	public int argumentos() throws SintaticoException{
		int op = verificacao("(", true);
		if(op == 1){
		   // Recebeu (
		   buffer.remove(0);
		   lista_de_parametros();  //   <<<<<<<   OBS : Falta Checar este método.
		   // Recebeu lista_de_parametros
		   op = verificacao(")", true);
		   if(op == 1){
			  // Recebeu )     >>   OK para (lista_de_parametros)
			  buffer.remove(0);
			  return 1;  // Recebeu (lista_de_parametros)
		   }else if(op == 2){
			  System.out.println("\n\tO Buffer está Vazio no método argumentos.");
			  throw new SintaticoException(")", "um buffer vazio");
		   }
		   throw new SintaticoException(")", buffer.get(0).getToken());
		}else if(op == 2){
		   System.out.println("\n\tO Buffer está Vazio no método argumentos.");
		   return 2;
		}
		return 3;  // Recebeu epsilon (qualquer coisa, exceto abre parênteses).
	}
	
	/************************************************************************************************************/
	// 			Métodos que iniciam com a letra   c
	
	public void comando() throws SintaticoException{
		if(!buffer.isEmpty()){
		   if(variavel()){
			  if(buffer.size() > 1){
				 //buffer.remove(0);
				 if(buffer.get(1).getToken().equals(":=")){
				    buffer.remove(0);
				    buffer.remove(0);
				    expressao();
				 }else{
					ativacao_procedimento();
				 }
			  }else{
				buffer.remove(0);
			  }
		   }else if(buffer.get(0).getToken().equals("begin")){
			  comando_composto();
		   }else if(buffer.get(0).getToken().equals("if") || buffer.get(0).getToken().equals("while")){
			  buffer.remove(0);
			  expressao();
		   }else{
			  throw new SintaticoException("Identificador ou begin ou if ou while", buffer.get(0).getToken());
		   }
		}else{
		   System.out.println("\n\tO Buffer está Vazio no método comando .");
		}
	}
	
	public void comando_composto() throws SintaticoException{
		if(!buffer.isEmpty() && buffer.get(0).getToken().equals("begin")){
		   buffer.remove(0);
		   comandos_opcionais();
		   if(!buffer.isEmpty() && buffer.get(0).getToken().equals("end")){
			  buffer.remove(0);
		   }else if(buffer.isEmpty()){
			 System.out.println("\n\tO Buffer está Vazio no método comando_composto.");
		   }else{
			 throw new SintaticoException("end", buffer.get(0).getToken());
		   }
		}else if(buffer.isEmpty()){
		   System.out.println("\n\tO Buffer está Vazio no método comando_composto.");
		}else{
		   throw new SintaticoException("begin", buffer.get(0).getToken());
		}
	}
	
	public void comandos_opcionais() throws SintaticoException{
		lista_de_comandos();
	}
	
	/************************************************************************************************************/
	// 			Métodos que iniciam com a letra   d
	
	public int declaracoes_variaveis() throws SintaticoException{
		/*
		if(!buffer.isEmpty() && buffer.get(0).getToken().equals("var")){
		   buffer.remove(0);
		   // Recebeu aqui    var
		   return lista_declaracoes_variaveis();
		}else if(buffer.isEmpty()){
		   System.out.println("\n\tO Buffer está vazio no método declaracoes_variaveis.");
		   return 2;   // 2  >> Indica Buffer Vazio.
		}
		return 3; //  3 >> Indica Epsilon (qualquer coisa, exceto  var no método declaracoes_variaveis).
		*/
		int op = verificacao("var", true);
		if(op == 1){
		   // Recebeu var
		   buffer.remove(0);
		   return lista_declaracoes_variaveis();
		}else if(op == 2)
		   throw new SintaticoException("var ou epsilon", "buffer vazio");
		return 3; // epsilon (Qualquer coisa, exceto var ou buffer vazio).
	}
	
	public int declaracao_de_subprograma() throws SintaticoException{
		int op;
		if(!buffer.isEmpty() && buffer.get(0).getToken().equals("procedure")){
		   buffer.remove(0);
		   if(!buffer.isEmpty() && buffer.get(0).getClassificacao().equals("Identificador")){
			  buffer.remove(0);
			  // Recebeu procedure id
			  if((op = argumentos()) == 3){  // Recebeu epsilon. Nesse ponto, não aceite.
				 System.out.println("\n\tRecebeu procedure id , porém não recebeu argumentos.");
				 throw new SintaticoException("argumentos", "epsilon");
			  }
			  if(!buffer.isEmpty() && buffer.get(0).getToken().equals(";")){
				 // Recebeu procedure id argumentos;
				 buffer.remove(0);
				 //   >> Ajeitar neste if
				 declaracoes_variaveis();
				 declaracoes_de_subprogramas();
				 comando_composto();
			  }else if(buffer.isEmpty()){
				 System.out.println("\n\tO Buffer é vazio no método declaracao_de_subprograma.");
				 throw new SintaticoException(";", "um buffer vazio");
			  }else{
				 throw new SintaticoException(";", buffer.get(0).getToken());
			  }
		   }else if(buffer.isEmpty()){
			  System.out.println("\n\tO Buffer é Vazio no método declaracao_de_subprograma.");
			  //return 2;
			  throw new SintaticoException("Identificador", "um buffer vazio");
		   }else{
			  throw new SintaticoException("Identificador", buffer.get(0).getClassificacao());
		   }
		}else if(buffer.isEmpty()){
		   System.out.println("\n\tO Buffer é vazio no método declaracao_de_subprograma.");
		   //return 2;   // Buffer Vazio.
		   throw new SintaticoException("procedure", "um Buffer Vazio");
		}
	    return 4;  // ERRO.   Nesse caso, o buffer não é vazio e não recebeu   procedure  .
	}
	
	public int declaracoes_de_subprogramas() throws SintaticoException{
		int op = declaracao_de_subprograma();
		if(op == 4)
		  throw new SintaticoException("procedure", buffer.get(0).getToken()); // ERRO. O Buffer não é vazio e não recebeu   procedure  .
		if(!buffer.isEmpty() && buffer.get(0).getToken().equals(";")){
			
		}else if()
	}
	
	public void declaracao_de_subprogramas_linha() throws SintaticoException{
		if(!buffer.isEmpty() && buffer.get(0).getToken().equals("procedure")){
			declaracao_de_subprograma();
			if(!buffer.isEmpty() && buffer.get(0).getToken().equals(";")){
			   buffer.remove(0);
			   declaracao_de_subprogramas_linha();
			}else if(buffer.isEmpty()){
			   System.out.println("\n\tO Buffer é Vazio no método declaracao_de_subprogramas_linha.");
			}else{
			   throw new SintaticoException(";", buffer.get(0).getToken());
			}
		}else if(buffer.isEmpty()){
		  System.out.println("\n\tO Buffer é Vazio no método declaracao_de_subprogramas_linha.");
		}
	}
	
	/************************************************************************************************************/
	// 			Métodos que iniciam com a letra   l	
	
	public void lista_de_comandos() throws SintaticoException{
		if(!buffer.isEmpty()){
		   comando();
		   c
		}
	}
	
	public int lista_de_identificadores_linha() throws SintaticoException{
		//int opcao;
		/*
		if(!buffer.isEmpty() && buffer.get(0).getToken().equals(",")){
			buffer.remove(0);
			if(!buffer.isEmpty() && buffer.get(0).getClassificacao().equals("Identificador")){
			   buffer.remove(0);
			   // Recebeu    ,id
			   lista_de_identificadores_linha();
			   return 1; //  1 >> Indica que recebeu    ,id
			}else if(buffer.isEmpty()){
				System.out.println("\n\tO Buffer está vazio no método lista_de_identificadores_linha."
				+ "Porém, leu vírgula sem ser seguido de Identificador.");
				return 4;  // 4  >> Indica Buffer Vazio e não leu identificador após a vírgula.
		    }else{
			   throw new SintaticoException("Identificador", buffer.get(0).getClassificacao());
			}
		}else if(buffer.isEmpty()){
			System.out.println("\n\tO Buffer está vazio no método lista_de_identificadores_linha.");
			return 2; // 2  >> Indica Buffer Vazio.
		}
		return 3; //  3 >> Indica Epsilon (qualquer coisa, exceto vírgula).
		*/
		int op = verificacao(",", true);
		if(op == 1){
		   // Recebeu ,
		   buffer.remove(0);
		   op = verificacao("Identificador", false);
		   if(op == 1){
			 // OK.   Recebeu  ,id
			 buffer.remove(0);
			 lista_de_identificadores_linha();
			 return 1;   // OK.   Recebeu  ,id
		   }else if(op == 2)  // Buffer Vazio
			 throw new SintaticoException("Identificador", "buffer vazio");
		   throw new SintaticoException("Identificador", buffer.get(0).getClassificacao());
		}else if(op == 2){ // Buffer Vazio.
		   throw new SintaticoException(", ou epsilon", "buffer vazio");
		}
		return 3;  //  3 >> Indica Epsilon (qualquer coisa, exceto vírgula ou buffer vazio).
	}
	
	public int lista_de_identificadores() throws SintaticoException{
		/*
		//int opcao;
		if(!buffer.isEmpty() && buffer.get(0).getClassificacao().equals("Identificador")){
		   buffer.remove(0);
		   // Recebeu   id
		   //opcao = lista_de_identificadores_linha();
		   //lista_de_identificadores_linha();
		   return lista_de_identificadores_linha();   // OBS : Se nesta chamada retornar 3 , então será id concatenado com 
		   //epsilon.  2 para Buffer Vazio.
		}else if(buffer.isEmpty()){
			System.out.println("\n\tO Buffer está vazio no método lista_de_identificadores."
			+ "\n\tEsperado um Identificador.");
			return 2; // 2  >> Indica Buffer Vazio. 
	    }
		System.out.print("\n\tEsperado um Identificador em lista_de_identificadores.");
		return 4;
		*/
		int op = verificacao("Identificador", false);
		if(op == 1){
		   // Recebeu id
		   buffer.remove(0);
		   return lista_de_identificadores_linha();    // Vai retornar 1 ou 3
		}else if(op == 2){  // Buffer Vazio
		   throw new SintaticoException("Identificador", "buffer vazio");
		}
		throw new SintaticoException("Identificador", buffer.get(0).getClassificacao());
	}
	
	public int lista_declaracoes_variaveis_linha() throws SintaticoException{
		int op = lista_de_identificadores();
		if(op == 3){    // id epsilon  ou id, id, id,... epsilon
		   op = verificacao(":", true);
		   if(op == 1){  // Recebeu dois pontos
			  buffer.remove(0);
			  if(!buffer.isEmpty() && tipo(buffer.get(0).getToken())){
				buffer.remove(0);
				if(!buffer.isEmpty() && buffer.get(0).getToken().equals(";")){
				   //    OK.
				   buffer.remove(0);
				   return lista_declaracoes_variaveis_linha();  <<< 
				}else if(buffer.isEmpty()){
				   //System.out.println("\n\tO Buffer está Vazio no método lista_declaracoes_variaveis_linha ."
				   //+ "\n\tEsperado  ; ");
				   throw new SintaticoException(";", "buffer vazio");
				}
				throw new SintaticoException(";", buffer.get(0).getToken());
			  }else if(buffer.isEmpty()){
				//System.out.println("\n\tO Buffer está Vazio no método lista_declaracoes_variaveis_linha ."
				//+ "\n\tEsperado integer ou real ou boolean.");
				throw new SintaticoException("integer ou real ou boolean", "buffer vazio");
			  }
			  throw new SintaticoException("integer ou real ou boolean", buffer.get(0).getToken());
		   }
		   throw new SintaticoException(":", buffer.get(0).getToken());
		}
		/*
		int op = lista_de_identificadores();
		if(op == 4 || op == 2)
		   return op;   // ERRO ou Buffer Vazio.
		if(!buffer.isEmpty() && buffer.get(0).getToken().equals(":")){
		   buffer.remove(0);
		   if(!buffer.isEmpty() && tipo(buffer.get(0).getToken())){
		      buffer.remove(0);
		      if(!buffer.isEmpty() && buffer.get(0).getToken().equals(";")){
		    	 buffer.remove(0);
		    	 return lista_declaracoes_variaveis_linha();
		      }else if(buffer.isEmpty()){
		    	System.out.println("\n\tO Buffer está Vazio no método lista_declaracoes_variaveis_linha ."
		    	+ "\n\tEsperado  ; ");
		      }
		   }else if(buffer.isEmpty()){
			 System.out.println("\n\tO Buffer está Vazio no método lista_declaracoes_variaveis_linha ."
			 + "\n\tEsperado integer ou real ou boolean.");
		   }
		}else if(buffer.isEmpty()){
			System.out.println("\n\tO Buffer está vazio no método lista_declaracoes_variaveis_linha ."
			+ "\n\tEsperado  : ");
		}
		return 4;   // ERRO.
		*/
	}
	
	public void lista_de_parametros() throws SintaticoException{
		   lista_de_identificadores();
		   if(!buffer.isEmpty() && buffer.get(0).getToken().equals(":")){
			  buffer.remove(0);
			  if(!buffer.isEmpty() && tipo(buffer.get(0).getToken())){
				buffer.remove(0);
				///        Continuar a Implementar  Aqui.
				if(!buffer.isEmpty() && buffer.get(0).getToken().equals(";")){
				   lista_de_parametros_linha();
				}else if(buffer.isEmpty()){
				  System.out.println("\n\tO Buffer está vazio no método lista_de_parametros.");	
				}else{
				   throw new SintaticoException(";", buffer.get(0).getToken());
				}
			  }else if(buffer.isEmpty()){
				System.out.println("\n\tO Buffer está vazio no método lista_de_parametros.");
			  }else{
				throw new SintaticoException("integer ou real ou boolean", buffer.get(0).getToken());
			  }
		   }else if(buffer.isEmpty()){
			  System.out.println("\n\tO Buffer está vazio no método lista_de_parametros.");
		   }else{
			  throw new SintaticoException(":", buffer.get(0).getToken());
		   }
	}
	
	public int lista_declaracoes_variaveis() throws SintaticoException{
		/*
		if(lista_de_identificadores() == 2){
		   System.out.println("\n\tO Buffer está Vazio no método lista_declaracoes_variaveis.");
		   return 2;
		}
		*/
		int op;
		lista_de_identificadores();
		op = verificacao(":", true);
		if(op == 1){
			// Recebeu :
			buffer.remove(0);
			if(!buffer.isEmpty() && tipo(buffer.get(0).getToken())){
			  buffer.remove(0);
			  // Recebeu integer ou real ou boolean
			  /*
			  if(!buffer.isEmpty() && buffer.get(0).getToken().equals(";")){
				 buffer.remove(0);
			     return lista_declaracoes_variaveis_linha();
			  }else if(buffer.isEmpty()){
				 System.out.println("\n\tO Buffer está vazio no método lista_declaracoes_variaveis."
				 + "\n\tEsperava-se ; ");
				 return 4;
			  }else{
				throw new SintaticoException(";", buffer.get(0).getToken()); 
			  }
			  */
			  op = verificacao(";", true);
			  if(op == 1){
				 // OK.
				return lista_declaracoes_variaveis_linha();   <<<
			  }else if(op == 2){
				 throw new SintaticoException(";", "buffer vazio");
			  }
			  throw new SintaticoException(";", buffer.get(0).getToken());
			}else if(buffer.isEmpty()){
			  throw new SintaticoException("integer ou real ou boolean", "buffer vazio");
			}else{
			  throw new SintaticoException("integer ou real ou boolean", buffer.get(0).getToken());
			}
		}else if(op == 2) // Buffer Vazio.
		   throw new SintaticoException(":", "buffer vazio");
		throw new SintaticoException(":", buffer.get(0).getToken());
		/*
		if(!buffer.isEmpty() && buffer.get(0).getToken().equals(":")){
			buffer.remove(0);
			if(!buffer.isEmpty() && tipo(buffer.get(0).getToken())){
			   buffer.remove(0);
			   if(!buffer.isEmpty() && buffer.get(0).getToken().equals(";")){
				  buffer.remove(0);
				  return lista_declaracoes_variaveis_linha();
			   }else if(buffer.isEmpty()){
				  System.out.println("\n\tO Buffer está vazio no método lista_declaracoes_variaveis."
				  + "\n\tEsperava-se ; ");
				  return 4;
			   }else{
				  throw new SintaticoException(";", buffer.get(0).getToken()); 
			   }
			}else if(buffer.isEmpty()){
			   System.out.println("\n\tO Buffer está Vazio no método lista_declaracoes_variaveis."
			   + "\n\tEsperava-se  tipo .");
			   return 4;
			}else{
			   throw new SintaticoException("integer ou real ou boolean", buffer.get(0).getToken());
			}
		}else if(buffer.isEmpty()){
			System.out.println("\n\tO Buffer está Vazio no método lista_declaracoes_variaveis."
		    + "\n\tEstava esperando :");
			//return 4; // Também indica ERRO após Buffer Vazio.
		}else{
		   System.out.println("\n\tEstava esperando :");
		}
		return 4;   // ERRO .
		*/
	}
	
	/************************************************************************************************************/
	// 			Métodos que iniciam com a letra   o
	
	public boolean op_multiplicativo(String token){
		if(token.equals("*") || token.equals("/") || token.equals("and"))
		   return true;
		return false;
	}
	
	public boolean op_aditivo(String token){
		if(token.equals("+") || token.equals("-") || token.equals("or"))
		   return true;
		return false;
	}
	
	public boolean op_relacional(String token){
		if(token.equals("=") || token.equals("<") || token.equals(">") || token.equals("<=") || token.equals(">=") || token.equals("<>"))
		   return true;
		return false;
	}
	
	/************************************************************************************************************/
	// 			Métodos que iniciam com a letra   p
	
	public void programa(){
		int op = verificacao("program", true);
		try{
		  if(op == 1){
		    // Recebeu program
		    buffer.remove(0);
		    op = verificacao("Identificador", false);   // false >> Indica que quer checar a Classificação do Token.
		    if(op == 1){
		       // Recebeu  id
		       buffer.remove(0);
		       op = verificacao(";", true);
		       if(op == 1){
		    	  //   OK >>   Recebeu program id;
		    	  buffer.remove(0);
		    	  op = declaracoes_variaveis();
		       }else if(op == 2)
		    	 throw new SintaticoException(";", "buffer vazio");
		       throw new SintaticoException(";", buffer.get(0).getToken());
		    }else if(op == 2)
		      throw new SintaticoException("Identificador", "buffer vazio");
		    throw new SintaticoException("Identificador", buffer.get(0).getClassificacao());
		  }else if(op == 2){
		     System.out.print("\n\t\tO buffer está vazio no método programa .");
		     return;
		  }
		  throw new SintaticoException("program", buffer.get(0).getToken());
	    }catch(SintaticoException e){
	       System.err.println("\n\t\t" + e);
	    }
	}
	
	/*
	public void programa(){
		int op;
		   try{
			if(!buffer.isEmpty() && buffer.get(0).getToken().equals("program")){
			   buffer.remove(0);
			   if(!buffer.isEmpty() && buffer.get(0).getClassificacao().equals("Identificador")){
				  buffer.remove(0);
				  if(!buffer.isEmpty() && buffer.get(0).getToken().equals(";")){
				     // OK.  Recebeu program id;
					 buffer.remove(0);
					 op = declaracoes_variaveis();
					 if(op == 4){
						System.out.print("\n\top >> 4 . Rejeita");
						return;
					 }else if(op == 2)
					    return;
					 op = declaracoes_de_subprogramas();  // Segundo Método.
					 
				  }else if(buffer.isEmpty()){
					 System.out.println("\n\tO Buffer está Vazio no método programa.");
			      }else{
					throw new SintaticoException(";", buffer.get(0).getToken());
				  }
			   }else if(buffer.isEmpty()){
				   System.out.println("\n\tO Buffer está Vazio no método programa.");
			   }else{
				 throw new SintaticoException("Identificador", buffer.get(0).getClassificacao());
			   }
			}else if(buffer.isEmpty()){
				System.out.println("\n\tO Buffer está Vazio no método programa.");
		    }else{
			  throw new SintaticoException("program", buffer.get(0).getToken());
			}
		   }catch(SintaticoException e){
		      System.out.println("\t" + e);
		   }
		}
	*/
	
	/************************************************************************************************************/
	// 			Métodos que iniciam com a letra   s
	
	public boolean sinal(String token){
		if(token.equals("+") || token.equals("-"))
			return true;
		return false;
	}
	
	/************************************************************************************************************/
	// 			Métodos que iniciam com a letra   t
	
	public boolean tipo(String token){
	   if(token.equals("integer") || token.equals("real") || token.equals("boolean"))
		  return true;
	   return false;
	}
	
	
	/************************************************************************************************************/
	
	//				EXECUTANDO PROGRAMA.
	
	public void iniciaAnalisadorSintatico(){
		if(!flagArquivo)
		   return;
		programa();
	}
	
	public void exibindoObjetosBuffer(){
		for(Token obj : buffer)
		   System.out.println(obj.getToken() + " | " + obj.getClassificacao() + " | " + obj.getLinha());
		System.out.println("\n\tQuantidade de Objetos >> " + buffer.size());
	}
	
	public static void main(String[] args){
		ASintatico obj = new ASintatico("src/Tabela.txt");
		//obj.exibindoObjetosBuffer();   // OK.
		obj.iniciaAnalisadorSintatico();
	}
}
