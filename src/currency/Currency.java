package currency;

public class Currency {
	private String m_type;
	private double m_value;
	
	private static final String curTypes = "$;eur"; 
	
	Currency( String type) throws CurrencyException {
		checkType(type);
		m_type = type;
		m_value = 0;
	}

	Currency( String type, double value) throws CurrencyException {
		checkType(type);
		m_type = type;
		m_value = value;
	}

	public static void checkType(String type) throws CurrencyException{
		if(!curTypes.contains(type)) {
			throw new CurrencyException("Wrong currency type: " + type);
		}
	}

	public double getValue() {
		return m_value;
	}
	
	public Currency setValue(double value) {
		m_value = value;
		
		return this;
	}
	 
	public String getType() {
		return m_type;
	}
	
//	public Currency Add(Currency op) throws CurrencyException {
//		if(!m_type.equals(op.getType()))
//			throw new CurrencyException("Addition: different currency types!");
//		m_value += op.getValue();
//
//		return this;
//	}
//	
//	public Currency Subtract(Currency op) throws CurrencyException {
//		if(!m_type.equals(op.getType()))
//			throw new CurrencyException("Addition: different currency types!");
//		m_value -= op.getValue();
//
//		return this;
//	}
	
	@Override
	public String toString(){
		if(m_type.equals("$")) {
			return m_type + String.format("%1$.2f", m_value);
		} 
		else {
			return String.format("%1$.2f", m_value) + m_type;
		}
	}

}
