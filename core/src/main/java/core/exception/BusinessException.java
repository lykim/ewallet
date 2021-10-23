package core.exception;

public class BusinessException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1253584035132461988L;
	private int code;
	public BusinessException(int code, String message) {
		super(message);
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
}
