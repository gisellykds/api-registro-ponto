package br.com.av.funcionario.utils.exceptions;

import lombok.Getter;

@Getter
public class ObjInexistenteException extends RuntimeException{
    private Integer code = 1;

    public ObjInexistenteException(String message) {
        super(message);
    }
}