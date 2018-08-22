package currency;

public class CurrencyException extends Exception {
	private static final long serialVersionUID = 2L;

    public CurrencyException(String errStr) {
        super(errStr);
    }
}
