package currency;

import java.util.Hashtable;

public class RateTable {
	private Hashtable<String, Double> m_table;
	
	RateTable() {
		m_table = new Hashtable<String, Double>();
	}
	
	public RateTable loadRates() {
		putRate("$", "eur", 0.8);
		putRate("eur", "$", 1.5);
		
		return this;
	}
	
	public RateTable putRate(String src, String dst, double rate) {
		removeRate(src, dst);
		m_table.put(src + dst, rate);
		return this;
	}
	
	public double getRate(String src, String dst) {
		Double rate = m_table.get(src + dst);

		return ((rate != null)? rate.doubleValue() : 0);
	}

	public RateTable removeRate(String src, String dst) {
		m_table.remove(src + dst);
		
		return this;
	}
}
