grammar JFTT;
@header {
    package parser;
}


BLANK:         [ \t\r\n] -> skip;
COMMENT:       '['.*?']' -> skip;
PIDENTIFIER:   [_a-z]+;
NUM:           '-'?[0-9]+;
READ:           'READ';
WRITE:          'WRITE';

ASSIGN:         'ASSIGN';
PLUS:           'PLUS';
MINUS:          'MINUS';
TIMES:          'TIMES';
DIV:            'DIV';
MOD:            'MOD';
FOR:            'FOR';
EQ:             'EQ';
NEQ:            'NEQ';
LE:             'LE';
GE:             'GE';
LEQ:            'LEQ';
GEQ:            'GEQ';
IF:             'IF';
THEN:           'THEN';
ELSE:           'ELSE';
ENDIF:          'ENDIF';
WHILE:          'WHILE';
DO:             'DO';
ENDWHILE:       'ENDWHILE';
ENDDO:          'ENDDO';


program       : 'DECLARE' declarations 'BEGIN' commands 'END'
              | 'BEGIN' commands 'END'
              ;

declarations  : declarations',' PIDENTIFIER
              | declarations',' PIDENTIFIER'('NUM':'NUM')'
              | PIDENTIFIER
              | PIDENTIFIER'('NUM':'NUM')'
              ;

commands      : commands command
              | command
              ;

command       : identifier ASSIGN expression';'
              | IF condition THEN commands ELSE commands ENDIF
              | IF condition THEN commands ENDIF
              | WHILE condition DO commands ENDWHILE
              | DO commands WHILE condition ENDDO
              | FOR PIDENTIFIER 'FROM' value 'TO' value 'DO' commands 'ENDFOR'
              | FOR PIDENTIFIER 'FROM' value 'DOWNTO' value 'DO' commands 'ENDFOR'
              | READ identifier';'
              | WRITE value';'
              ;

expression    : value
              | value PLUS value
              | value MINUS value
              | value TIMES value
              | value DIV value
              | value MOD value
              ;

condition     : value EQ value
              | value NEQ value
              | value LE value
              | value GE value
              | value LEQ value
              | value GEQ value;

value         : NUM
              | identifier
              ;

identifier    : PIDENTIFIER
              | PIDENTIFIER'('PIDENTIFIER')'
              | PIDENTIFIER'('NUM')'
              ;



