package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import currency.CurrencyException;
import currency.CurrencyUtils;
import currency.Parser;
import currency.ParserException;
import currency.RateTableEcseption;

public class Main {

	public static void main(String[] args) throws IOException, ParserException, CurrencyException {
		
		String expr;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Parser p = new Parser();
		
		try {
			CurrencyUtils.reLoadRates("rates.csv");
		}	
		catch (RateTableEcseption e) {
			System.out.println(e.getLocalizedMessage());
		}
		
		for(;;) {
			System.out.println("Input expression: ");
			expr = br.readLine();
			if(expr.isEmpty()) break;
			try {
				System.out.println("Result: " + p.evaluate(expr) + "\n");
			}
			catch (ParserException ex) {
//				System.out.println(ex.getLocalizedMessage());
				ex.printStackTrace();
			}
			catch (CurrencyException ex) {
//				System.out.println(ex.getLocalizedMessage());
				ex.printStackTrace();
			}
		}
	}
}
