package calc;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
	
	public RateTable loadRates(String fileName) throws RateTableEcseption {
		String line;
		String params[] = null;
		
		try(BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
			while((line = br.readLine()) != null) {
				params = line.split(":");
				putRate(params[0], params[1], Double.parseDouble(params[2]));
			}
		}
		catch(NumberFormatException e) {
			throw new RateTableEcseption("Error parsing " + params[2] + ".\n"
					+ e.getLocalizedMessage() + "\nRates table loaded uncompletely." );
		}
		catch (Exception e) {
			
			this.clearTable().loadRates();
			throw new RateTableEcseption("Error reading rates file " + fileName + ". Default rates were loaded."  );
		}
		
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
	
	public RateTable reLoadTable(String fileName) throws RateTableEcseption {

		clearTable().loadRates(fileName);
		
		return this;
	}
	
	private RateTable clearTable() {
		m_table.clear();
		
		return this;
	}
	
}
