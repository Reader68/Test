package currency;

public class CurrencyUtils {
	private static RateTable m_rt = new RateTable().loadRates();

/****
 * 	 Returns new Currency object with type "typeTo" and appropriately converted value
 */
	public static Currency convert(Currency cur, String typeTo) throws CurrencyException {
		Currency.checkType(typeTo);
		
		Currency result = new Currency(typeTo);
		if(cur.getType().equals(typeTo)) {
			throw new CurrencyException("Can not convert currency " + typeTo + " to itself.");
		}
		
		return result.setValue(cur.getValue() * m_rt.getRate(cur.getType(), typeTo));
	}
	
	/****
	 * 	 Returns new Currency object that contains a sum of 2 currency objects
	 */
	public static Currency Add(Currency op1, Currency op2) throws CurrencyException {
		if(!op1.getType().equals(op2.getType()))
			throw new CurrencyException("Addition: different currency types!");
		
		Currency result = new Currency(op1.getType());

		return result.setValue(op1.getValue()+op2.getValue());
	}
	
	/****
	 * 	 Returns new Currency object that contains a result of subtraction
	 */
	public static Currency Subtract(Currency op1, Currency op2) throws CurrencyException {
		if(!op1.getType().equals(op2.getType()))
			throw new CurrencyException("Subtraction: different currency types!");
		
		Currency result = new Currency(op1.getType());

		return result.setValue(op1.getValue()-op2.getValue());	
	}
		
}
