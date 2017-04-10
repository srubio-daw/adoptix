package tk.srubio.adoptix.web.util;

import java.io.Serializable;

public class AdoptixResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String message;
	private Boolean success;
	private Object data;

	public AdoptixResponse() {
	}

	public AdoptixResponse(String message, Boolean success, Object data) {
		this.message = message;
		this.success = success;
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
