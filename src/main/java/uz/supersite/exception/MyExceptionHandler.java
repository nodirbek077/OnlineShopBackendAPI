package uz.supersite.exception;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import uz.supersite.ResponseEntity;

@ControllerAdvice	
public class MyExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler({ CustomPropertyValueException.class,ItemNotFoundException.class })
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public HttpEntity<?> handleAccessDeniedException(Exception ex) {
		return new HttpEntity<>(new ResponseEntity(-1,ex.getMessage()));
    }
}
