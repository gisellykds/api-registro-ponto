package br.com.av.empresa.utils.exceptions;

import lombok.Getter;

@Getter
public class ObjInexistenteException extends RuntimeException{
    private Integer code = 1;

    public ObjInexistenteException(String message) {
        super(message);
    }
}
