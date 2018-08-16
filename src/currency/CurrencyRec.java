package currency;

public class CurrencyRec {
	private String m_SrcCurr;
	private String m_DstCurr;
	private Double m_Rate;
	
	CurrencyRec(String srcCurr, String dstCurr, Double rate) {
		m_SrcCurr = srcCurr;
		m_DstCurr = dstCurr;
		m_Rate = rate;
	}
	

}
