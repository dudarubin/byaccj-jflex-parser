%{
  import java.io.*;
%}

%token IF ELSE WHILE
%token INT DOUBLE BOOLEAN VOID
%token AND
%token NUM IDENT


%right '='
%left AND
%left '>'
%left '+'
%left '*' '/'


%%


Prog
    : Inicio
    ;


Inicio
    : /* vazio */
    | Tipo IDENT RestoGlobal
    | VOID IDENT '(' ListaParametrosOuVazio ')' Bloco ListaFuncoes
    ;


RestoGlobal
    : RestoLId ';' Inicio
    | '(' ListaParametrosOuVazio ')' Bloco ListaFuncoes
    ;


RestoLId
    : ',' IDENT RestoLId
    | /* vazio */
    ;

Tipo
    : INT
    | DOUBLE
    | BOOLEAN
    ;

ListaFuncoes
    : ListaFuncoes Funcao
    | /* vazio */
    ;


Funcao
    : TipoOuVoid IDENT '(' ListaParametrosOuVazio ')' Bloco
    ;



TipoOuVoid
    : VOID
    | Tipo
    ;


ListaParametrosOuVazio
    : ListaParametros
    | /* vazio */
    ;


ListaParametros
    : Tipo IDENT
    | Tipo IDENT ',' ListaParametros
    ;


Bloco
    : '{' LCmd '}'
    ;


LCmd
    : Cmd LCmd
    | /* vazio */
    ;


Cmd
    : Bloco
    | IF '(' E ')' Cmd
    | IF '(' E ')' Cmd ELSE Cmd
    | WHILE '(' E ')' Cmd
    | E ';'
    ;


E
    : E '=' E
    | E '+' E
    | E '*' E
    | E '/' E
    | E '>' E
    | E AND E
    | NUM
    | IDENT
    | '(' E ')'
    ;


%%


private Yylex lexer;


private int yylex() {

    int yyl_return = -1;

    try {

        yylval = new ParserVal(0);

        yyl_return = lexer.yylex();

    }
    catch (IOException e) {

        System.err.println(
            "IO error: " + e.getMessage()
        );

    }

    return yyl_return;
}


public void yyerror(String error) {

    System.err.println(
        "Error: " + error
    );

}

public Parser(Reader r) {

    lexer = new Yylex(r, this);

}


static boolean interactive;


public void setDebug(boolean debug) {

    yydebug = debug;

}

public static void main(String args[])
    throws IOException {

    System.out.println("");

    Parser yyparser;


    if (args.length > 0) {

        yyparser =
            new Parser(
                new FileReader(args[0])
            );

    }


    else {

        System.out.println(
            "[Quit with CTRL-D]"
        );

        System.out.print("> ");

        interactive = true;

        yyparser =
            new Parser(
                new InputStreamReader(System.in)
            );

    }


    int resultado =
        yyparser.yyparse();



    if (resultado == 0) {

        System.out.println();

        System.out.println(
            "Entrada aceita!"
        );

    }

}