//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "exerc.y"
  import java.io.*;
//#line 19 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short IF=257;
public final static short ELSE=258;
public final static short WHILE=259;
public final static short INT=260;
public final static short DOUBLE=261;
public final static short BOOLEAN=262;
public final static short VOID=263;
public final static short AND=264;
public final static short NUM=265;
public final static short IDENT=266;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    1,    3,    3,    7,    7,    2,    2,
    2,    6,    6,    8,    9,    9,    4,    4,   10,   10,
    5,   11,   11,   12,   12,   12,   12,   12,   13,   13,
   13,   13,   13,   13,   13,   13,   13,
};
final static short yylen[] = {                            2,
    1,    0,    3,    7,    3,    5,    3,    0,    1,    1,
    1,    2,    0,    6,    1,    1,    1,    0,    2,    4,
    3,    2,    0,    1,    5,    7,    5,    2,    3,    3,
    3,    3,    3,    3,    1,    1,    3,
};
final static short yydefred[] = {                         0,
    9,   10,   11,    0,    0,    1,    0,    0,    0,    0,
    0,    0,    3,    0,    0,    0,   17,    0,    0,    0,
    0,    0,    0,    7,    5,    0,    0,   13,   13,   20,
    0,    0,   35,   36,    0,   24,    0,    0,    0,    0,
    0,    0,    0,    0,   21,   22,    0,    0,    0,    0,
    0,    0,   28,   15,   16,   12,    0,    0,    0,   37,
    0,    0,    0,    0,   31,   32,    0,    0,    0,    0,
    0,   27,    0,    0,    0,   26,   14,
};
final static short yydgoto[] = {                          5,
    6,   15,   13,   16,   36,   40,   14,   56,   57,   17,
   37,   38,   39,
};
final static short yysindex[] = {                      -191,
    0,    0,    0, -264,    0,    0, -249,  -12,  -36, -171,
 -171, -235,    0,   -9, -214,   13,    0,   20,   34, -191,
   37,  -59,  -59,    0,    0, -171,  -37,    0,    0,    0,
   42,   44,    0,    0,  -35,    0,  -24,  -37,    4, -156,
 -156,  -35,  -35,  -32,    0,    0,  -35,  -35,  -35,  -35,
  -35,  -35,    0,    0,    0,    0, -166,  -29,  -22,    0,
   15,    6,   56,  -20,    0,    0,   62,  -37,  -37, -171,
 -150,    0,   68,  -37,  -59,    0,    0,
};
final static short yyrindex[] = {                       110,
    0,    0,    0,    0,    0,    0,    0,    0,   52,   71,
   71,    0,    0,    0,    0,    0,    0,    0,   52,  110,
   72,    0,    0,    0,    0,    0,  -11,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -11,    0,  115,
  116,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -18,   -3,  -25,  -17,    0,    0,    0,    0,    0,   71,
  -40,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   97,   35,    0,  -10,  -16,   89,  100,    0,    0,   94,
   83,    5,   45,
};
final static int YYTABLESIZE=270;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         25,
   18,    8,   35,   11,   35,   28,   29,   12,   60,   51,
   50,   68,   51,   50,   52,   33,    9,   52,   69,   51,
   50,   51,   34,   30,   52,   30,   52,   10,   48,   49,
   19,   48,   49,   33,    7,   33,   33,   29,   48,   49,
   34,   30,   34,   30,   30,   51,   50,   51,   50,   20,
   52,   21,   52,   22,    7,   29,   51,   50,   77,   73,
   23,   52,   53,   27,   48,   49,   48,   49,    1,    2,
    3,    4,   71,   72,   55,   55,   49,   12,   76,   44,
   26,   42,   25,   43,   25,   27,   58,   59,    1,    2,
    3,   61,   62,   63,   64,   65,   66,   51,   50,   67,
   45,   70,   52,    1,    2,    3,   54,   74,   75,    2,
    8,   18,   19,   23,    4,    6,   25,   41,   24,   30,
   46,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   25,    0,   25,   31,
    0,   32,    0,    0,   25,   25,    0,   33,   34,   33,
   34,   47,    0,    0,   47,    0,    0,    0,   33,    0,
    0,   47,    0,    0,    0,   34,   30,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   47,    0,   47,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   11,  266,   40,   40,   40,   22,   23,   44,   41,   42,
   43,   41,   42,   43,   47,   41,  266,   47,   41,   42,
   43,   42,   41,   41,   47,   43,   47,   40,   61,   62,
  266,   61,   62,   59,    0,   61,   62,   41,   61,   62,
   59,   59,   61,   61,   62,   42,   43,   42,   43,   59,
   47,  266,   47,   41,   20,   59,   42,   43,   75,   70,
   41,   47,   59,  123,   61,   62,   61,   62,  260,  261,
  262,  263,   68,   69,   40,   41,   62,   44,   74,   35,
   44,   40,  123,   40,  125,  123,   42,   43,  260,  261,
  262,   47,   48,   49,   50,   51,   52,   42,   43,  266,
  125,   40,   47,  260,  261,  262,  263,  258,   41,    0,
   59,   41,   41,  125,    0,    0,   20,   29,   19,   26,
   38,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  257,   -1,  259,  257,
   -1,  259,   -1,   -1,  265,  266,   -1,  265,  266,  265,
  266,  264,   -1,   -1,  264,   -1,   -1,   -1,  264,   -1,
   -1,  264,   -1,   -1,   -1,  264,  264,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  264,   -1,  264,
};
}
final static short YYFINAL=5;
final static short YYMAXTOKEN=266;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
null,null,"'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
null,"'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"IF","ELSE","WHILE","INT","DOUBLE","BOOLEAN",
"VOID","AND","NUM","IDENT",
};
final static String yyrule[] = {
"$accept : Prog",
"Prog : Inicio",
"Inicio :",
"Inicio : Tipo IDENT RestoGlobal",
"Inicio : VOID IDENT '(' ListaParametrosOuVazio ')' Bloco ListaFuncoes",
"RestoGlobal : RestoLId ';' Inicio",
"RestoGlobal : '(' ListaParametrosOuVazio ')' Bloco ListaFuncoes",
"RestoLId : ',' IDENT RestoLId",
"RestoLId :",
"Tipo : INT",
"Tipo : DOUBLE",
"Tipo : BOOLEAN",
"ListaFuncoes : ListaFuncoes Funcao",
"ListaFuncoes :",
"Funcao : TipoOuVoid IDENT '(' ListaParametrosOuVazio ')' Bloco",
"TipoOuVoid : VOID",
"TipoOuVoid : Tipo",
"ListaParametrosOuVazio : ListaParametros",
"ListaParametrosOuVazio :",
"ListaParametros : Tipo IDENT",
"ListaParametros : Tipo IDENT ',' ListaParametros",
"Bloco : '{' LCmd '}'",
"LCmd : Cmd LCmd",
"LCmd :",
"Cmd : Bloco",
"Cmd : IF '(' E ')' Cmd",
"Cmd : IF '(' E ')' Cmd ELSE Cmd",
"Cmd : WHILE '(' E ')' Cmd",
"Cmd : E ';'",
"E : E '=' E",
"E : E '+' E",
"E : E '*' E",
"E : E '/' E",
"E : E '>' E",
"E : E AND E",
"E : NUM",
"E : IDENT",
"E : '(' E ')'",
};

//#line 114 "exerc.y"


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
//#line 384 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
