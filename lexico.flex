%%

%byaccj


%{
  private Parser yyparser;

  public Yylex(java.io.Reader r, Parser yyparser) {
    this(r);
    this.yyparser = yyparser;
  }
%}


NL = \n | \r | \r\n


%%


"$TRACE_ON"  { yyparser.setDebug(true);  }
"$TRACE_OFF" { yyparser.setDebug(false); }


"if"      { return Parser.IF; }
"else"    { return Parser.ELSE; }
"while"   { return Parser.WHILE; }

"int"     { return Parser.INT; }
"double"  { return Parser.DOUBLE; }
"boolean" { return Parser.BOOLEAN; }
"void"    { return Parser.VOID; }

"&&"      { return Parser.AND; }


[0-9]+ {
    return Parser.NUM;
}

[a-zA-Z_][a-zA-Z0-9_]* {
    return Parser.IDENT;
}


/* Simbolos */

"{" |
"}" |
"=" |
"(" |
")" |
";" |
"," |
"*" |
"/" |
"+" |
">" {
    return (int) yycharat(0);
}


/* Espacos em branco */

[ \t]+ {
}


/* Quebras de linha */

{NL}+ {
}


/* Qualquer caractere nao reconhecido */

. {
    System.err.println(
        "Error: unexpected character '" + yytext() + "'"
    );

    return YYEOF;
}