package core.responseModel;

public class ResponseModel<T> {
	public int code;
	public String message;
	public T content;
	public String token;
}
