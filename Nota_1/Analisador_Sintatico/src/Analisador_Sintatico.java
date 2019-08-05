import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Analisador_Sintatico{
	
	private ArrayList<Token> buffer;
	private FileReader arquivo;
	private BufferedReader arquivo_leitura;
	private boolean flagArquivo;
	
	public Analisador_Sintatico(String caminho){
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
	
	public void exibindoObjetosBuffer(){
		for(Token obj : buffer)
		   System.out.println(obj.getToken() + " | " + obj.getClassificacao() + " | " + obj.getLinha());
		System.out.println("\n\tQuantidade de Objetos >> " + buffer.size());
	}
	
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
	
	public boolean sinal(String token){
		if(token.equals("+") || token.equals("-"))
			return true;
		return false;
	}
	
	public void lista_de_parametros_linha() throws SintaticoException{
	   if(!buffer.isEmpty() && buffer.get(0).getToken().equals(";")){
		  buffer.remove(0);
		  if(!buffer.isEmpty() && buffer.get(0).getClassificacao().equals("Identificador")){
			 lista_de_identificadores();
			 if(!buffer.isEmpty() && buffer.get(0).getToken().equals(":")){
				buffer.remove(0);
				/////    Continuar Aqui.   Verificar no método tipo()  .   <<<
				if(!buffer.isEmpty() && tipo(buffer.get(0).getToken())){
				   buffer.remove(0);
				   if(!buffer.isEmpty() && buffer.get(0).getToken().equals(";")){
					  lista_de_parametros_linha();
				   }else if(!buffer.isEmpty()){
					  System.out.println("\n\tO Buffer está vazio no método lista_de_parametros_linha.");
				   }
				}else if(buffer.isEmpty()){
				   System.out.println("\n\tO Buffer está vazio no método lista_de_parametros_linha.");
				}else{
				   throw new SintaticoException("integer ou real ou boolean", buffer.get(0).getToken());
				}
			 }else if(buffer.isEmpty()){
			    System.out.println("\n\tO Buffer está Vazio no método lista_parametros_linha.");
			 }else{
				throw new SintaticoException(":", buffer.get(0).getToken());
			 }
		  }else if(buffer.isEmpty()){
			 System.out.println("\n\tO Buffer está Vazio no método lista_de_parametros_linha.");
		  }
	   }else if(buffer.isEmpty()){
		  System.out.println("\n\tO Buffer está Vazio no método lista_de_parametros_linha.");
	   }
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
	
	public void argumentos() throws SintaticoException{
	   if(!buffer.isEmpty() && buffer.get(0).getToken().equals("(")){
		  buffer.remove(0);
		  lista_de_parametros();
		  if(!buffer.isEmpty() && buffer.get(0).getToken().equals(")")){
			 buffer.remove(0);
		  }else if(buffer.isEmpty()){
			 System.out.println("\n\tO Buffer está Vazio no método argumentos.");
		  }else{
			 throw new SintaticoException(")",buffer.get(0).getToken());
		  }
	   }else if(buffer.isEmpty()){
		  System.out.println("\n\tO Buffer está Vazio no método argumentos.");
	   }
	}
	
	public boolean variavel(){
		if(buffer.get(0).getClassificacao().equals("Identificador"))
		  return true;
		return false;
	}
	
	public void fator() throws SintaticoException{
		if(!buffer.isEmpty()){
		   if(buffer.get(0).getClassificacao().equals("Identificador")){
			  buffer.remove(0);
			  if(!buffer.isEmpty() && buffer.get(0).getClassificacao().equals("(")){
				buffer.remove(0);
				lista_de_expressoes();
				if(!buffer.isEmpty() && buffer.get(0).getClassificacao().equals(")")){
				   
				}
			  }
		   }else if(buffer.get(0).getClassificacao().equals("Número Inteiro") || buffer.get(0).getClassificacao().equals("Número Real")
			 || buffer.get(0).getClassificacao().equals("true") || buffer.get(0).getClassificacao().equals("false")){
			   
		   }else if(buffer.get(0).getToken().equals("(")){
			   
		   }else if(buffer.get(0).getToken().equals("not")){
			   
		   }
		}
	}
	
	public void termo() throws SintaticoException{
		fator();
	}
	
	public void expressao_simples() throws SintaticoException{
		termo();
	}
	
	public void expressao() throws SintaticoException{
		expressao_simples();
	}
	
	public void lista_de_expressoes() throws SintaticoException{
	   expressao();
	}
	
	public void metodo_C() throws SintaticoException{
	   if(!buffer.isEmpty() && buffer.get(0).getToken().equals("(")){
		  buffer.remove(0);
		  lista_de_expressoes();
		  
	   }else if(buffer.isEmpty()){
		  System.out.println("\n\tO Buffer está Vazio no método_C.");
	   }
	}
	
	public void ativacao_procedimento() throws SintaticoException{
		if(!buffer.isEmpty() && buffer.get(0).getClassificacao().equals("Identificador")){
		   buffer.remove(0);
		   metodo_C();
		}else if(buffer.isEmpty()){
		   System.out.println("\n\tO Buffer está Vazio no método ativacao_procedimento.");
		}else{
		   throw new SintaticoException("Identificador", buffer.get(0).getClassificacao());
		}
	}
	
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
	
	public void lista_de_comandos() throws SintaticoException{
		if(!buffer.isEmpty()){
		   comando();
		   c
		}
	}
	
	public void comandos_opcionais() throws SintaticoException{
		lista_de_comandos();
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
	
	public void declaracao_de_subprograma() throws SintaticoException{
		if(!buffer.isEmpty() && buffer.get(0).getToken().equals("procedure")){
		   buffer.remove(0);
		   if(!buffer.isEmpty() && buffer.get(0).getClassificacao().equals("Identificador")){
			  buffer.remove(0);
			  // Verificar o "método argumentos".
			  argumentos();
			  if(!buffer.isEmpty() && buffer.get(0).getToken().equals(";")){
				 buffer.remove(0);
				 declaracoes_variaveis();
				 declaracoes_de_subprogramas();
				 comando_composto();
			  }else if(buffer.isEmpty()){
				 System.out.println("\n\tO Buffer é Vazio no método declaracao_de_subprograma.");
			  }else{
				 throw new SintaticoException(";",buffer.get(0).getToken());
			  }
		   }else if(buffer.isEmpty()){
			  System.out.println("\n\tO Buffer é Vazio no método declaracao_de_subprograma.");
		   }else{
			  throw new SintaticoException("Identificador", buffer.get(0).getClassificacao());
		   }
		}else if(buffer.isEmpty()){
		   System.out.println("\n\tO Buffer é Vazio no método declaracao_de_subprograma.");
		}else{
		   throw new SintaticoException("procedure", buffer.get(0).getToken());
		}
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
	
	public void declaracoes_de_subprogramas() throws SintaticoException{
		if(!buffer.isEmpty() && buffer.get(0).getToken().equals("procedure"))
		   declaracao_de_subprograma();
		else if(!buffer.isEmpty() && buffer.get(0).getToken().equals(";")){
		   buffer.remove(0);
		   declaracao_de_subprogramas_linha();   // <<<<<<
		}else if(buffer.isEmpty()){
		   System.out.println("\n\tO Buffer é Vazio no método declaracoes_de_subprogramas.");
		}else{
		   throw new SintaticoException(";", buffer.get(0).getToken());
		}
	}
	
	public void lista_de_identificadores_linha() throws SintaticoException{
		if(!buffer.isEmpty() && buffer.get(0).getToken().equals(",")){
			buffer.remove(0);
			if(!buffer.isEmpty() && buffer.get(0).getClassificacao().equals("Identificador")){
			   buffer.remove(0);
			   lista_de_identificadores_linha();
			}else if(buffer.isEmpty()){
				System.out.println("\n\tO Buffer está Vazio no método lista_de_identificadores_linha.");
		    }else{
			   throw new SintaticoException("Identificador", buffer.get(0).getClassificacao());
			}
		}else if(buffer.isEmpty()){
			System.out.println("\n\tO Buffer está Vazio no método lista_de_identificadores_linha.");
		}
	}
	
	public void lista_de_identificadores() throws SintaticoException{
		if(!buffer.isEmpty() && buffer.get(0).getClassificacao().equals("Identificador")){
		   buffer.remove(0);
		   lista_de_identificadores_linha();
		}else if(buffer.isEmpty()){
			System.out.println("\n\tO Buffer está Vazio no método lista_de_identificadores.");
	    }else{
		   throw new SintaticoException("Identificador", buffer.get(0).getClassificacao());
		}
	}
	
	public void lista_declaracoes_variaveis_linha() throws SintaticoException{		
		lista_de_identificadores();
		if(!buffer.isEmpty() && buffer.get(0).getToken().equals(":")){
		   buffer.remove(0);
		   if(!buffer.isEmpty() && tipo(buffer.get(0).getToken())){
		      buffer.remove(0);
		      if(!buffer.isEmpty() && buffer.get(0).getToken().equals(";")){
		    	 buffer.remove(0);
		    	 if(!buffer.isEmpty() && buffer.get(0).getClassificacao().equals("Identificador")){
		    	    lista_declaracoes_variaveis_linha();
		    	 }else if(buffer.isEmpty()){
		    		System.out.println("\n\tO Buffer está Vazio no método lista_declaracoes_variaveis_linha .");
		    	 }
		      }else if(buffer.isEmpty()){
		    	System.out.println("\n\tO Buffer está Vazio no método lista_declaracoes_variaveis_linha .");
		      }else{
		    	throw new SintaticoException(";", buffer.get(0).getToken());
		      }
		   }else if(buffer.isEmpty()){
			 System.out.println("\n\tO Buffer está Vazio no método lista_declaracoes_variaveis_linha .");
		   }else{
			 throw new SintaticoException("integer ou real ou boolean", buffer.get(0).getToken());
		   }
		}else if(buffer.isEmpty()){
			System.out.println("\n\tO Buffer está Vazio no método lista_declaracoes_variaveis_linha .");
		}else{
			throw new SintaticoException(":", buffer.get(0).getToken());
		}
	}
	
	public boolean tipo(String token){
	   if(token.equals("integer") || token.equals("real") || token.equals("boolean"))
	      return true;
	   return false;
	}
	
	public void lista_declaracoes_variaveis() throws SintaticoException{
		lista_de_identificadores();
		if(!buffer.isEmpty() && buffer.get(0).getToken().equals(":")){
			buffer.remove(0);
			if(!buffer.isEmpty() && tipo(buffer.get(0).getToken())){
			   buffer.remove(0);
			   if(!buffer.isEmpty() && buffer.get(0).getToken().equals(";")){
				  buffer.remove(0);
				  lista_declaracoes_variaveis_linha();
			   }else if(buffer.isEmpty()){
				  System.out.println("\n\tO Buffer está Vazio no método lista_declaracoes_variaveis.");  
			   }else{
				  throw new SintaticoException(";", buffer.get(0).getToken()); 
			   }
			}else if(buffer.isEmpty()){
			   System.out.println("\n\tO Buffer está Vazio no método lista_declaracoes_variaveis.");
			}else{
			   throw new SintaticoException("integer ou real ou boolean", buffer.get(0).getToken());
			}
		}else if(buffer.isEmpty()){
			System.out.println("\n\tO Buffer está Vazio no método lista_declaracoes_variaveis.");
		}else{
			throw new SintaticoException(":", buffer.get(0).getToken());
		}
	}
	
	public void declaracoes_variaveis() throws SintaticoException{
		if(!buffer.isEmpty() && buffer.get(0).getToken().equals("var")){
		   //System.out.print("OK");
		   buffer.remove(0);
		   lista_declaracoes_variaveis();
		}else if(buffer.isEmpty()){
		   System.out.println("\n\tO Buffer está Vazio no método declaracoes_variaveis.");
		}
	}
	
	public void programa(){
	   try{
		if(!buffer.isEmpty() && buffer.get(0).getToken().equals("program")){
		   buffer.remove(0);
		   if(!buffer.isEmpty() && buffer.get(0).getClassificacao().equals("Identificador")){
			  buffer.remove(0);
			  if(!buffer.isEmpty() && buffer.get(0).getToken().equals(";")){
			     // OK.
				 buffer.remove(0);
				 declaracoes_variaveis();   // Primeiro Método.
				 declaracoes_de_subprogramas();  // Segundo Método.
				 
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
	
	public void iniciaAnalisadorSintatico(){
		if(!flagArquivo)
		   return;
		programa();
	}
	
	public static void main(String[] args){
		ASintatico obj = new ASintatico("src/Tabela.txt");
		obj.exibindoObjetosBuffer();   // OK.
		obj.iniciaAnalisadorSintatico();
	}
}
