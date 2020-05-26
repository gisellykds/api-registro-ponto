package br.com.av.gateway.error;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharStreams;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.*;
import org.springframework.stereotype.Component;
import java.io.*;

@Component
public class CustomErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder errorDecoder = new Default();
    @Override
    public ObjInexistenteException decode(String s, Response response) {
        String message = null;
        Reader reader = null;
        ObjInexistenteException objInexistenteException = null;
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try {
            reader = response.body().asReader();
            String result = CharStreams.toString(reader);
            objInexistenteException = mapper.readValue(result,
                    ObjInexistenteException.class);
            message = objInexistenteException.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return objInexistenteException;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class ObjInexistenteException extends RuntimeException{
        private Integer code = 1;
        public ObjInexistenteException(String message) {
            super(message);
        }
    }
}