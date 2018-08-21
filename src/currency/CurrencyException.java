package currency;

public class CurrencyException extends Exception {
	private static final long serialVersionUID = 2L;

    private String errStr;  
    
    public CurrencyException(String errStr) {
        super();
        this.errStr = errStr;
    }
    
    public String toString(){
        return this.errStr;
    }
}
