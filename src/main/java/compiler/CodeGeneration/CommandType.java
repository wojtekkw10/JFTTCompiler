package compiler.CodeGeneration;

public enum CommandType {
    GET,
    PUT,

    LOAD,
    STORE,
    LOADI,
    STOREI,

    ADD,
    SUB,
    SHIFT,
    INC,
    DEC,

    JUMP,
    JPOS,
    JZERO,
    JNEG,

    HALT
}
