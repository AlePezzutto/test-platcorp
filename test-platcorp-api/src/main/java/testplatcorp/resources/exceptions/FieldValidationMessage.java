package testplatcorp.resources.exceptions;

import java.io.Serializable;

public class FieldValidationMessage  implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String fieldName;
	private String message;
	
	public FieldValidationMessage() {
	}

	public FieldValidationMessage(String fieldName, String message) {
		super();
		this.fieldName = fieldName;
		this.message = message;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
		
}
