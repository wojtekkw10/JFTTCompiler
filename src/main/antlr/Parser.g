grammar Parser;
@header {
    package parser;
}
main: 'Hello ' name '!';
name: ANY+;
ANY: .;


