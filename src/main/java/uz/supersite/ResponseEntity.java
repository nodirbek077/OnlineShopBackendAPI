package uz.supersite;

import com.fasterxml.jackson.annotation.JsonInclude;

//@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseEntity {
	private int code;
	private String message;
	private Object object;
	
	public ResponseEntity(Object object) {
		this.object = object;
	}

	public ResponseEntity(String message, Object object) {
		this.message = message;
		this.object = object;
	}
	
	public ResponseEntity(int code, String message) {
		this.message = message;
		this.setCode(code);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public ResponseEntity(int code, String message, Object object) {
		this.code = code;
		this.message = message;
		this.object = object;
	}
	
	
}
