package business.exceptions;

public class EShoppingRuntimeException extends RuntimeException {
	public EShoppingRuntimeException(String s) {
		super(s);
	}
	public EShoppingRuntimeException(Exception e) {
		super(e);
	}
	private static final long serialVersionUID = 2566282094218081403L;
}
