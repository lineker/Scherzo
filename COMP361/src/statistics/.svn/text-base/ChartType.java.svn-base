package statistics;

import globalAccess.Global;

/**
 * Enum created for allowable chart types.
 * @author Alicia Bendz
 *
 */
public enum ChartType {
	LINE, 
	BAR, 
	PIE, 
	TEXT;
	
	@Override
	public String toString(){
		switch(this){
		case LINE:
			return Global.getLOCInstance().getLocalizedString("StatsChart.line");
		case BAR:
			return Global.getLOCInstance().getLocalizedString("StatsChart.bar");
		case PIE:
			return Global.getLOCInstance().getLocalizedString("StatsChart.pie");
		case TEXT:
			return Global.getLOCInstance().getLocalizedString("StatsChart.text");
		default:
			return "";
		}
	}
}
