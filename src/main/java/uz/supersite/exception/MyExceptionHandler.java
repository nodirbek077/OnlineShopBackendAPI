package uz.supersite.exception;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import uz.supersite.ErrorDTO;
import uz.supersite.ResponseEntity;

import java.util.Date;
import java.util.List;

@ControllerAdvice	
public class MyExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler({ CustomPropertyValueException.class,ItemNotFoundException.class })
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public HttpEntity<?> handleAccessDeniedException(Exception ex) {
		return new HttpEntity<>(new ResponseEntity(-1,ex.getMessage()));
    }

	@Override
    protected org.springframework.http.ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setTimestamp(new Date());
        errorDTO.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDTO.setPath(((ServletWebRequest) request).getRequest().getServletPath());

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        fieldErrors.forEach(fieldError -> {
            errorDTO.addError(fieldError.getDefaultMessage());
        });

        return new org.springframework.http.ResponseEntity<>(errorDTO,headers,status);
    }
}
