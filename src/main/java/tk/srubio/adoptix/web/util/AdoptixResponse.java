package tk.srubio.adoptix.web.util;

import java.io.Serializable;

public class AdoptixResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String message;
	private Boolean success;
	private Object data;
	private Long totalRecords;

	public AdoptixResponse() {
	}

	public AdoptixResponse(String message, Boolean success, Object data, Long totalRecords) {
		this.message = message;
		this.success = success;
		this.data = data;
		this.totalRecords = totalRecords;
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

	public Long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords;
	}

}
