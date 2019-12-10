grammar JFTT;
@header {
    package parser;
}

BLANK:         [ \t\r\n] -> skip;
COMMENT:       '['.*?']' -> skip;
PIDENTIFIER:   [_a-z]+;
NUM:           '-'?[0-9]+;

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

command       : identifier 'ASSIGN' expression';'
              | 'IF' condition 'THEN' commands 'ELSE' commands 'ENDIF'
              | 'IF' condition 'THEN' commands 'ENDIF'
              | 'WHILE' condition 'DO' commands 'ENDWHILE'
              | 'DO' commands 'WHILE' condition 'ENDDO'
              | upfor
              | downfor
              | 'READ' identifier';'
              | 'WRITE' value';'
              ;

upfor:        'FOR' PIDENTIFIER 'FROM' value 'TO' value 'DO' commands 'ENDFOR';
downfor:      'FOR' PIDENTIFIER 'FROM' value 'DOWNTO' value 'DO' commands 'ENDFOR';

expression    : value
              | value 'PLUS' value
              | value 'MINUS' value
              | value 'TIMES' value
              | value 'DIV' value
              | value 'MOD' value
              ;

condition     : value 'EQ' value
              | value 'NEQ' value
              | value 'LE' value
              | value 'GE' value
              | value 'LEQ' value
              | value 'GEQ' value;

value         : NUM
              | identifier
              ;

identifier    : PIDENTIFIER
              | PIDENTIFIER'('PIDENTIFIER')'
              | PIDENTIFIER'('NUM')'
              ;



