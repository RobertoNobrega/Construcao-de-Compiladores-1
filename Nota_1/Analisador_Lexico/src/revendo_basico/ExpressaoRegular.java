/*
 * To change this license header, choose License Headers in Project Properties.
 * and open the template in the editor.
 * To change this template file, choose Tools | Templates
 */
package revendo_basico;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author roberto
 */

public class ExpressaoRegular {
    
    /** 
     *   Padrão de Pesquisa, no Formato String. Validar Ocorrência,
     *   em forma de texto.
     *   
    */
    
    public static void main(String[] args){
        /*
            EX : Temos um texto, com a palavra Java.
                 Qual é a regex que valida essa ocorrência de texto ?
        
        */
        String padrao = "Java", texto = "Java";
        // Realizando a validação, com o método matches, que pertence a
        // classe String. Recebe como parâmetro a regex que faz essa validação.
        // O método matches retorna true caso ocorra a validação do padrão.
        boolean b = texto.matches(padrao);
        System.out.println("\tResultado 1 > " + b);   // true. Então, a ocorrência é válida.
        
        /** OBS : Expressões Regulares são sensíveis a letras maiúsculas e minúsculas. */
        b = "Java".matches("java");
        System.out.println("\tResultado 2 > " + b);   // false. J e j são letras distintas em uma Expressão Regular.
        
        /*
                        Modificadores
            (?i) >  Ignora maiúscula e minúscula.
            (?x) >  Comentários.
            (?m) >  Multilinhas.
            (?s) >  Dottal.
        */
        
        b = "Java".matches("(?i)java");  // (?i)java  >> Vai avaliar a palavra java, se foi escrita ou não com letras maiúsculas e minúsculas.
        System.out.println("\tResultado 3 > " + b); // true.
        String t = "Java", p = "java";
        b = t.matches("(?im)" + p);   // OBS : Pode agrupar os modificadores na mesma instrução, como ocorre aqui.
        System.out.println("\tResultado 4 > " + b); // true.
        
        /*
                        Metacaracteres
               São caracteres especiais, em que indicam a ocorréncia de número, letra e outros caracteres, presentes
               no texto.
        
               .    Qualquer Caracter (digitos, letras, símbolos)  >> A Expressão Regular vai procurar qualquer caracter.
               \\d   dígitos             [0-9]
               \\D   Não é dígito        [^0-9]
               \\s   espaços             [\t\n\x0B\f\r]
               \\S   Não é espaço        [^\s]
               \\w   letra               [a-zA-Z_0-9]    >> Inclui também os dígitos, que vai de 0 a 9
               \\W   Não é letra, nem dígito         >> Ou seja, que não é \w
        */
        
        // Exemplo. Procurando a ocorrência de um caracter qualquer. No exemplo, é o @
        b = "@".matches(".");   // Usando o metacaracter ponto , em que vai procurar a ocorrência de qualquer caracter. No nosso exemplo,
        // será o caracter @
        System.out.println("\tResultado 5 > " + b); // true.
        b = "2".matches("\\d");  // Procurando a ocorrência de apenas um dígito, entre 0 a 9 .
        System.out.println("\tResultado 6 > " + b); // true.
        b = "4".matches("\\D");  // Procurando ocorrência que não seja dígito, entre 0 a 9 . Como é um dígito, vai retornar false.
        System.out.println("\tResultado 7 > " + b); // false.
        b = "k".matches("\\w");  // Procurando ocorrência, que seja dígito entre 0 a 9 , como letra minúscula e maiúscula.
        System.out.println("\tResultado 8 > " + b); // true.
        b = " ".matches("\\s");  // Procurando ocorrência de espaço em branco.
        System.out.println("\tResultado 9 > " + b); // true.
        /** Nos Exemplos acima de Metacaracteres, para cada caso, apenas procura um único caracter da String.*/
        
        /** Vamos fazer para mais de um caracter do texto. */
        b = "Pi".matches("..");   // Usando dois metacaracteres ponto , vai procurar dois caracteres quaisquer. 
        System.out.println("\tResultado 10 > " + b); // true.
        b = "20".matches("\\d\\d");  // Procurando a ocorrência de dois dígitos, entre 0 a 9 .
        System.out.println("\tResultado 11 > " + b); // true.
        
        //          Exercício : Validar CEP.    >>  xxxxx-xxx
        b = "58070-410".matches("\\d\\d\\d\\d\\d-\\d\\d\\d");
        System.out.println("\tResultado 12 > " + b); // true.
        
        
        /*
                        Uso de Quantificadores
        
            X{n}        X, exatamente n vezes. X representa Metacaractere.
            X{n,}       X, pelo menos n vezes.
            X{n, m}     X, pelo menos n mas não mais do que m vezes.
            X?          X, 0 ou uma vez.
            X*          X, 0 ou mais vezes.
            X+          X, 1 ou mais vezes.
        
        */
        
        b = "25".matches("\\d{2}");  // Procurando a ocorrência de dois dígitos, entre 0 a 9 .
        System.out.println("\tResultado 13 > " + b); // true.
        b = "2019".matches("\\d{2,}");  // Procurando a ocorrência de, pelo menos, dois dígitos, entre 0 a 9 . Como 2019 tem 4 dígitos
        // , então será true.
        System.out.println("\tResultado 14 > " + b); // true.
        b = "2019".matches("\\d{2,4}");  // Procurando a ocorrência de, pelo menos dois dígitos e não mais do que 4 dígitos, entre 0 a 9 .
        System.out.println("\tResultado 15 > " + b); // true.
        b = "".matches(".?");  // Procurando a ocorrência de qualquer caractere, sendo que será 0 ou 1 vez de ocorrência.
        System.out.println("\tResultado 16 > " + b); // true.
        b = "aa".matches(".?");  // Procurando a ocorrência do caractere a, sendo que será 0 ou 1 vez de ocorrência. Como temos 2 ocorrências
        // de a , então retorna false.
        System.out.println("\tResultado 17 > " + b);
        b = "aaa".matches(".*");  // Procurando a ocorrência do caractere a, sendo que será 0 ou mais vezes de ocorrências. Como temos 3 ocorrências
        // de a , então retorna true.
        System.out.println("\tResultado 18 > " + b);
        b = "aa".matches(".+");  // Procurando a ocorrência do caractere a, sendo que será 1 ou mais vezes de ocorrências. Como temos 2 ocorrências
        // de a , então retorna true.
        System.out.println("\tResultado 19 > " + b);
        
        // Exercício 2 : Validar Cep Novamente, usando os Quantificadores.
        b = "58070-410".matches("\\d{5}-\\d{3}");
        System.out.println("\tResultado 20 > " + b);
        
        // Validar Data.
        b = "29/06/2019".matches("\\d{2}/\\d{2}/\\d{4}");
        System.out.println("\tResultado 21 > " + b);
        
        /*
                  Metacaracter de Fronteira
        
            ^       inicia
            $       finaliza
            |       ou
        */
        
        /** No exemplo abaixo, queremos ver se o texto começa com a palavra Pier, seguido
            de nenhum, um ou mais caracteres.
        */
        
        b = "Pier21".matches("^Pier.*");
        System.out.println("\tResultado 22 > " + b);
        
        b = "Pier21".matches("^.*21$");   // Inicia com nenhum, um ou mais caracteres, em que deve terminar obrigatoriamente
        // com o valor 21
        System.out.println("\tResultado 23 > " + b);
        
        b = "Tem java aqui.".matches(".*java.*");   // Verifica, se no "meio" do texto, existe a palavra java.
        System.out.println("\tResultado 24 > " + b);
    
        b = "Tem java aqui".matches("^Tem.*aqui$");   // Palavra começa com Tem , seguido de outros caracteres, porém tem que terminar
        // com a palavra   aqui .
        System.out.println("\tResultado 25 > " + b);
        
        b = "Limão".matches("Banana|Limão");   // Verifica se na String existe a palavra Banana ou Limão. Se tiver uma das duas, retorna true.
        System.out.println("\tResultado 26 > " + b);
        
        /*
                    Agrupadores  : Agrupam conjunto de caracteres
                [...]           Agrupamento  >> Pode definir um conjunto de letras.
                        EX :  [abc]   >> Pode ter a ou b ou c
                [a-z]           Alcance    >> Nesse caso-exemplo, procura todos os caracteres minúsculos, entre a e z
                [a-e][i-u]      União  >> Nesse exemplo, procura todos os caracteres minúsculos entre a e e , juntamente entre i e u
                [a-z&&[aeiou]]  Interseção  >>  Nesse exemplo, procura todos os caracteres minúsculos entre a e z , juntamente com as vogais aeiou
                [^abc]          Exceção    >> Considera todos os caracteres, com exceção de abc
                [a-z&&[^m-p]]   Subtração  >> 
                \x              Fuga Literal
        */
        
        b = "x".matches("[a-z]");
        System.out.println("\tResultado 27 > " + b);
        
        b = "7".matches("[0-9]");
        System.out.println("\tResultado 28 > " + b);
        
        b = "true".matches("[tT]rue");   // Nesse caso, vai retorna true  quando a palavra true foi iniciado com letra
        // maiúscula ou minúscula.
        System.out.println("\tResultado 29 > " + b);
        
        b = "yes".matches("[tT]rue|[yY]es");   // Retorna verdadeiro quando foi digitada a palavra true ou yes,
        // iniciando ou não com letra maiúscula ou minúscula.
        System.out.println("\tResultado 30 > " + b);
        
        // Exercício : Validação de Nome Próprio de Pessoa.
        b = "Clauirton".matches("[A-Z][a-z]+");
        System.out.println("\tResultado 31 > " + b);
        
        b = "olho".matches("[^abc]lho");   // A String não pode começar nem com a, nem com b, nem com c, porém
        // tem que terminar com a string  lho  .
        System.out.println("\tResultado 32 > " + b);
        
        b = "moça".matches("moç[ao]");   // Vai reconhecer a string moço ou moça .
        System.out.println("\tResultado 33 > " + b);
        
        b = "rh@xti.com".matches("\\w+@\\w+\\.\\w{2,3}");   // Exemplo, para reconhecer E-Mail. Existem
        // outros padrões para reconhecimento de E-Mail.
        System.out.println("\tResultado 34 > " + b);
        
        String doce = "Qual é o Doce mais doCe que o doce ?";
        
        /*
                Java apresenta classes que servem para a manipulação de expressões regulares.
                Uma dessas classes é a classe Pattern
        
                        O método matches é mais usado para coisas mais simples, pois ele vai pegar a
                String que representa a expressão regular, para converter em um algoritmo válido em tempo de
                execução. Ele é menos complicado de se usar do que a classe Pattern.
        */
        
        //Pattern.compile("(?i)doce");  //  (?i)doce > Vai ignorar letras maiúsculas e minúsculas, quando for encontrar
        // as ocorrências da string doce.   Na linha 213, está pedindo para preparar a expressão regular, pois ela será
        // usada no programa, talvez mais de uma vez.
        Matcher m = Pattern.compile("(?i)doce").matcher(doce);   // Pattern.compile("(?i)doce") > Quando retornar o objeto Matcher, pode usar
        // o método matcher, recebendo a String que se deseja realizar a operação com a expressão regular. No caso da linha 217, encontrar todas
        // as ocorrências da palavra doce, não importando se foi escrito em letra maiúscula ou minúscula.
        while(m.find()){ // A cada ocorrência encontrada, retorna true.
            System.out.println("\t\t" + m.group());
        }
        
                /** Substituições */
        String r = doce.replaceAll("(?i)doce","DOCINHO");   // Todas as ocorrências da string doce, independentemente
        // se foi escrito em maiúscula ou minúscula, todas serão substituídas pela string DOCINHO.
        System.out.println("\n\n" + r);
        
        String rato = "O rato roeu a roupa do rei de Roma";
        r = rato.replaceAll("r[aeiou]", "XX");   // Na frase da variável rato, as strings ra , re , ri , ro e ru serão substituídas
        // por XX .
        System.out.println("\n\n" + r);
        
        // System.out.println("\t\tEstamos\\sTestando");   //    Estamos\sTestando
        r = "\tTabular este texto".replaceAll("\\s","\t");  // Na String, quando encontrar espaço em branco,
        // substitui por tabulação.
        System.out.println("\n\n" + r);
        
        String url = "www.xti.com.br/clientes-2011.html";
        String re = "www.xti.com.br/\\w{2,}-\\d{4}.html";
        b = url.matches(re);
        System.out.println("\tResultado 35 > " + b);
        
        re = "(www.xti.com.br)/(\\w{2,})-(\\d{4}).html";   // O que está entre parênteses é uma variável.
        // (www.xti.com.br) é a primeira variável .  (\\w{2,}) é a segunda variável. (\\d{4}) é a terceira variável.
        
        //  http://www.xti.com.br/2011/clientes.jsp
        //  "http://$1/$3/$2.jsp";   // $1 > Primeira Variável de re, no caso www.xti.com.br
        //   $3 > Terceira Variável de re
        //   $2 > Segunda Variável de re
        r = url.replaceAll(re,"http://$1/$3/$2.jsp");
        System.out.println("\t" + r);
        
        b = "a_12".matches("^[a-zA-Z]\\w*");   // Indica que a string, deve começar, obrigatoriamente, entre as letras minúsculas
        // ou maiúsculas do alfabeto, seguido de nenhum, um ou mais caractere(s) do alfabeto, ou nenhum, um ou mais dígito(s) entre 0 a
        // 9 ou nenhum, um ou mais sublinha(s).
        System.out.println("\tResultado 36 > " + b);
        
        b = " ".matches("[^\\w]");      //    [^\\w]   >> Desconsidera todo o alfabeto da língua portuguesa, os dígitos numéricos e
        // o sublinha.    Os outros símbolos são reconhecidos.
        System.out.println("\tResultado 37 > " + b);
        
        b = "01".matches("\\d+");   // Aqui, espera-se que apareça um ou mais dígitos inteiros na String.
        System.out.println("\tResultado 38 > " + b);
        
        b = "1.0".matches("\\d+.\\d*");   // Aqui, espera-se que apareça um ou mais dígitos, seguido de um ponto,
        // seguido de nenhum, um ou mais dígitos na String. Ou seja, para inserir um valor real.
        System.out.println("\tResultado 39 > " + b);
        
        b = ",".matches("[;:(),]");  // Reconhece apenas ; : ( ) ,
        System.out.println("\tResultado 40 > " + b);
        
        b = " ".matches("=|<|>|<=|>=|<>|and|or|/|\t|\\s");  // Reconhece = < > <= >= <>
        System.out.println("\tResultado 41 > " + b);
        
        // "[\\s\t{\n]"
        b = "{".matches("[\\s\t{\n]");
        System.out.println("\tResultado 42 > " + b);
        
        b = "-".matches("[+-]|or");
        System.out.println("\tResultado 43 > " + b);
        
        b = "+12.22".matches("[-+]{0,1}\\d+\\.{0,1}\\d*");
        System.out.println("\tResultado 44 > " + b);
        
        b = "A".matches("[^0-9]");
        System.out.println("\tResultado 45 > " + b);
        
        // caracter.matches("[+-/*<>=.;:(),]")
        b = ";".matches("[+-/*<>=.;:(),]");
        System.out.println("\tResultado 46 > " + b);
    }
    
}
