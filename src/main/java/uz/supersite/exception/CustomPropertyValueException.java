package uz.supersite.exception;

public class CustomPropertyValueException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public CustomPropertyValueException(String message) {
		super(message);
	}
}
