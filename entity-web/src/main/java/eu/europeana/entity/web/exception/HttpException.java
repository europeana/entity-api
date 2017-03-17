package eu.europeana.entity.web.exception;

import org.springframework.http.HttpStatus;

/**
 * TODO move to api-common
 * @author GordeaS
 *
 */
public class HttpException extends Exception{

	private HttpStatus status;
	
	private String i18nKey;
	
	private String[] i18nParams;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HttpException(String message, String i18nKey, HttpStatus status){
		super(message);
		this.status = status;
		this.i18nKey = i18nKey;
	}
	
	public HttpException(String message, String i18nKey, String[] i18nParams, HttpStatus status){
		super(message);
		this.status = status;
		this.i18nKey = i18nKey;
		this.i18nParams = i18nParams;
	}
	
	public HttpException(String message, String i18nKey, String[] i18nParams, HttpStatus status, Throwable th){
		super(message, th);
		this.status = status;
		this.i18nKey = i18nKey;
		this.i18nParams = i18nParams;
	}

	public HttpStatus getStatus() {
		return status;
	}

	protected void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getI18nKey() {
		return i18nKey;
	}

	void setI18nKey(String i18nKey) {
		this.i18nKey = i18nKey;
	}

	public String[] getI18nParams() {
		return i18nParams;
	}

	void setI18nParams(String[] i18nParams) {
		this.i18nParams = i18nParams;
	}
}
