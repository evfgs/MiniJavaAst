grammar miniJavaGrammar;

goal : mainClass ( classDeclaration )* EOF;
mainClass : 'class' Identifier '{' 'public' 'static' 'void' 'main' '(' 'String' '[' ']' 
		Identifier ')' '{' statement '}' '}';
	
classDeclaration :  'class' Identifier ('extends' Identifier)? '{' (varDeclaration)*
	( methodDeclaration )* '}' ;

varDeclaration : type Identifier ';' ;

methodDeclaration : 'public' type Identifier '(' formalList ')'
	'{' (varDeclaration)* (statement)* 'return' expression ';' '}' ;
	
formalList: type Identifier (formalRest)*

;
formalRest: ',' type Identifier
;

type : 'int' '[' ']'
	| 'boolean'
	| 'int'
	| Identifier ;
	

statement : '{' (statement)* '}'
	| 'if' '(' expression ')' statement 'else' statement
	| 'while' '(' expression ')' statement
	| 'System.out.println' '(' expression ')'';'
	| Identifier '=' expression ';'
	| Identifier '[' expression ']' '=' expression ';'
	;

expression : 
| expression ( '&&' | '<' | '+' | '-' | '*' ) expression
| expression '[' expression ']'
| expression '.' 'length'
| expression '.' Identifier '(' ( expression ( ',' expression )* )? ')'
| INTEGER_LITERAL
| 'true'
| 'false'
| Identifier
| 'this'
| 'new' 'int' '[' expression ']'
| 'new' Identifier '(' ')'
| '!' expression
| '(' expression ')' 
;

Identifier : [a-zA-Z0-9_]+;
INTEGER_LITERAL : [0-9]+;
ESPACOS: [' \r\n\t //']+ ->skip;
//espa�os e comentarios -> skip
