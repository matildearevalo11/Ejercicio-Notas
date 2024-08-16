package com.apinotas.notas.exceptions;
import com.apinotas.notas.services.dtos.response.MessageResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResponseEntity<Map<String, String>> handleNotFoundException(NotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", ""+e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<MessageResponseDTO> handleNotFoundArgumentNotValid(MethodArgumentNotValidException e){
        List<MessageResponseDTO> errors = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach(error -> errors.add(new MessageResponseDTO(error.getDefaultMessage())));
        return errors;
    }
}