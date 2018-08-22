package calc;

public class CurrencyUtils {
	private static RateTable m_rt = new RateTable().loadRates();
/****
 * Clears current rates table and loads new one from specified file.
 * 
 * @param fileName
 * @throws RateTableEcseption
 */
	public static void reLoadRates(String fileName) throws RateTableEcseption {
		m_rt.reLoadTable(fileName);
	}
	
/*****
 * 	 Returns new Currency object with type "typeTo" and appropriately converted value
 * 	
 * @param cur
 * @param typeTo
 * @return
 * @throws CurrencyException
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
 * 	
 * @param op1
 * @param op2
 * @return
 * @throws CurrencyException
 */
	public static Currency Add(Currency op1, Currency op2) throws CurrencyException {
		if(!op1.getType().equals(op2.getType()))
			throw new CurrencyException("Addition: different currency types!");
		
		Currency result = new Currency(op1.getType());

		return result.setValue(op1.getValue()+op2.getValue());
	}
	
/****
 * 	 Returns new Currency object that contains a result of subtraction
 * 	
 * @param op1
 * @param op2
 * @return
 * @throws CurrencyException
 */
	public static Currency Subtract(Currency op1, Currency op2) throws CurrencyException {
		if(!op1.getType().equals(op2.getType()))
			throw new CurrencyException("Subtraction: different currency types!");
		
		Currency result = new Currency(op1.getType());

		return result.setValue(op1.getValue()-op2.getValue());	
	}
		
}
