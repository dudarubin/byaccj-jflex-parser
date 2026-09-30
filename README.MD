# Analisador Léxico e Sintático com JFlex e BYaccJ

Projeto desenvolvido para a disciplina de **Construção de Compiladores**.

A atividade consiste em adaptar o exemplo apresentado em aula, alterando as especificações léxica (`flex`) e sintática (`yacc`) para reconhecer uma nova gramática.

O analisador foi desenvolvido utilizando:

- JFlex para análise léxica
- BYaccJ para análise sintática
- Java para compilação e execução

## Objetivo

A especificação deve:

- reconhecer declarações de variáveis;
- reconhecer funções;
- reconhecer parâmetros;
- reconhecer blocos e comandos;
- reconhecer `if`, `if-else` e `while`;
- reconhecer expressões aritméticas, relacionais e lógicas;
- utilizar precedência e associatividade semelhantes às linguagens C/Java;
- compilar sem conflitos, com exceção do conflito esperado do `if-else`;
- aceitar programas válidos e rejeitar programas sintaticamente inválidos.

## Principais alterações

### Analisador léxico

O arquivo `lexico.flex` foi adaptado para reconhecer as palavras reservadas:

```text
int
double
boolean
void
if
else
while
```

Também são reconhecidos os tokens:

```text
NUM
IDENT
AND
```

O operador lógico `AND` foi representado no código por:

```text
&&
```

Além disso, são reconhecidos os símbolos:

```text
{ } ( ) ; , = + * / >
```

## Analisador sintático

O arquivo `exerc.y` foi adaptado para reconhecer:

- declarações de variáveis;
- listas de identificadores;
- tipos `int`, `double` e `boolean`;
- funções com retorno;
- funções `void`;
- funções com e sem parâmetros;
- blocos;
- listas de comandos;
- `if`;
- `if-else`;
- `while`;
- atribuições;
- expressões aritméticas;
- expressões relacionais;
- expressões lógicas.

## Precedência e associatividade

Foram utilizadas as seguintes regras:

```yacc
%right '='
%left AND
%left '>'
%left '+'
%left '*' '/'
```

A precedência é definida da menor para a maior:

```text
=
&&
>
+
* /
```

Por exemplo:

```c
a = 2 + 3 * 4;
```

A multiplicação possui maior precedência que a soma, e a soma possui maior precedência que a atribuição.

A atribuição utiliza associatividade à direita, permitindo construções como:

```c
a = b = 10;
```

interpretadas como:

```text
a = (b = 10)
```

## Fatoração entre declaração e função

Durante a implementação foi necessário realizar uma fatoração na parte inicial da gramática.

Uma declaração:

```c
int a;
```

e uma função:

```c
int soma() {
}
```

começam com o mesmo prefixo:

```text
Tipo IDENT
```

Para evitar conflitos adicionais no parser, esse prefixo foi fatorado.

Após reconhecer:

```text
Tipo IDENT
```

o parser verifica o símbolo seguinte.

Se aparecer:

```text
;
```

ou:

```text
,
```

a construção é uma declaração.

Se aparecer:

```text
(
```

a construção é uma função.

A regra utilizada foi:

```yacc
Inicio
    : /* vazio */
    | Tipo IDENT RestoGlobal
    | VOID IDENT '(' ListaParametrosOuVazio ')' Bloco ListaFuncoes
    ;
```

E a decisão é feita em:

```yacc
RestoGlobal
    : RestoLId ';' Inicio
    | '(' ListaParametrosOuVazio ')' Bloco ListaFuncoes
    ;
```

Com isso, os conflitos adicionais foram eliminados.

## Conflito do if-else

Após a geração do parser, o BYaccJ apresenta:

```text
1 shift/reduce conflict
```

Esse conflito é esperado e corresponde ao problema clássico conhecido como **dangling else**.

Ele ocorre devido às regras:

```yacc
Cmd
    : IF '(' E ')' Cmd
    | IF '(' E ')' Cmd ELSE Cmd
```

Por exemplo:

```c
if (a > 0)
    if (b > 0)
        a = 1;
    else
        a = 2;
```

O `else` é associado ao `if` mais próximo.

Esse é o único conflito presente na especificação e é permitido pelo enunciado da atividade.

## Geração do analisador

### Gerar o analisador léxico

```bash
java -jar JFlex.jar lexico.flex
```

Esse comando gera:

```text
Yylex.java
```

### Gerar o analisador sintático

No Windows:

```powershell
.\yacc.exe -tv -J exerc.y
```

No linux:

```powershell
./yacc.linux -tv -J exerc.y
```

O resultado esperado é:

```text
1 shift/reduce conflict.
```

O comando gera, entre outros arquivos:

```text
Parser.java
y.output
```

### Compilar

```bash
javac *.java
```

ou

```bash
javac --release 21 *.java
```

### Executar

```bash
java Parser programa_certo.txt
```

```bash
java Parser programa_erro.txt
```

## Programa de teste válido

```c
int a, b;
double resultado;
boolean ativo;

int soma(int x, int y) {
    resultado = x + y * 2;
}

void main() {
    a = 10;
    b = 5;

    while (a > b && ativo) {
        a = a / 2;
    }

    if (a > b)
        resultado = a;
    else
        resultado = b;
}
```

Resultado:

```text
Entrada aceita!
```

Esse programa testa:

- declarações de variáveis;
- listas de identificadores;
- `int`;
- `double`;
- `boolean`;
- funções;
- parâmetros;
- `void`;
- atribuição;
- soma;
- multiplicação;
- divisão;
- comparação;
- operador lógico `AND`;
- `while`;
- `if-else`;
- blocos.

## Programa de teste inválido

```c
int a

void main() {
    a = 10;
}
```

Nesse caso, falta o `;` após a declaração:

```c
int a;
```

Resultado:

```text
Erro: syntax error
```

Dessa forma, foi possível verificar que o analisador aceita programas sintaticamente válidos e rejeita programas que não seguem a gramática.

## Resultado

A implementação final:

- reconhece os tokens definidos pela linguagem;
- reconhece declarações e funções;
- suporta funções com e sem parâmetros;
- reconhece blocos e listas de comandos;
- suporta `if`, `if-else` e `while`;
- reconhece expressões aritméticas, relacionais e lógicas;
- utiliza precedência e associatividade semelhantes às linguagens C/Java;
- aceita programas sintaticamente válidos;
- rejeita programas sintaticamente inválidos;
- apresenta apenas o conflito esperado de `if-else`.

## Tecnologias utilizadas

- Java
- JFlex
- BYaccJ
- LALR(1)