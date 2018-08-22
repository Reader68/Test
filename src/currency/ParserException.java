package currency;

public class ParserException extends Exception {
    private static final long serialVersionUID = 1L;
 
    public ParserException(String errStr) {
        super(errStr);
    }
}
