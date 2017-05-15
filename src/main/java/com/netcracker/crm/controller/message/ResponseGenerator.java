package com.netcracker.crm.controller.message;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by bpogo on 5/6/2017.
 */
@Component
public class ResponseGenerator<T> {
    @Resource
    private Environment env;

    public ResponseEntity<T> getHttpResponse(HttpStatus httpStatus) {
        return new ResponseEntity<>(httpStatus);
    }

    public ResponseEntity<T> getHttpResponse(T obj, HttpStatus httpStatus) {
        return new ResponseEntity<>(obj, httpStatus);
    }


    public ResponseEntity<T> getHttpResponse(MessageHeader header, String messageProperty, HttpStatus httpStatus) {
        if (env.containsProperty(messageProperty)) {
            HttpHeaders headers = new HttpHeaders();
            headers.set(header.getHeaderName(), env.getProperty(messageProperty));
            return new ResponseEntity<>(headers, httpStatus);
        }
        return getHttpResponse(httpStatus);
    }
    
    public ResponseEntity<T> getHttpResponse(T obj, MessageHeader header, String messageProperty, HttpStatus httpStatus) {
        if (env.containsProperty(messageProperty)) {
            HttpHeaders headers = new HttpHeaders();
            headers.set(header.getHeaderName(), env.getProperty(messageProperty));
            return new ResponseEntity<>(obj, headers, httpStatus);
        }
        return getHttpResponse(obj, httpStatus);
    }

    public ResponseEntity<Long> getHttpResponse(Long objectId, MessageHeader header, String messageProperty, HttpStatus httpStatus) {
        if (env.containsProperty(messageProperty)) {
            HttpHeaders headers = new HttpHeaders();
            headers.set(header.getHeaderName(), env.getProperty(messageProperty));
            return new ResponseEntity<>(objectId, headers, httpStatus);
        }
        return new ResponseEntity<>(objectId, httpStatus);
    }
}
