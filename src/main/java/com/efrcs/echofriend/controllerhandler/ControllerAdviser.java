package com.efrcs.echofriend.controllerhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.efrcs.echofriend.exceptions.OperationNotAllowedException;
import com.efrcs.echofriend.exceptions.RecordAlreadyExistsException;
import com.efrcs.echofriend.exceptions.RecordNotFoundException;
import com.efrcs.echofriend.models.baseresponse.EcoFriendlyBaseResponse;

@ControllerAdvice
public class ControllerAdviser extends ResponseEntityExceptionHandler{
    
    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<Object> hanleRecordNofoundException(RecordNotFoundException ex){
        EcoFriendlyBaseResponse resp = new EcoFriendlyBaseResponse();
        resp.setResponseMessage(ex.getMessage());
        return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RecordAlreadyExistsException.class)
    public ResponseEntity<Object> recordAllreadyExistsHanlder(RecordAlreadyExistsException ex){
        EcoFriendlyBaseResponse resp = new EcoFriendlyBaseResponse();
        resp.setResponseMessage(ex.getMessage());
        return new ResponseEntity<>(resp, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(OperationNotAllowedException.class)
    public ResponseEntity<Object> operationNotAllowedhanlder(OperationNotAllowedException ex){
        EcoFriendlyBaseResponse resp = new EcoFriendlyBaseResponse();
        resp.setResponseMessage(ex.getMessage());
        return new ResponseEntity<>(resp, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> nullPointerExceptionHandler(NullPointerException ex){
        EcoFriendlyBaseResponse resp = new EcoFriendlyBaseResponse();
        resp.setResponseMessage(ex.getMessage());
        return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
