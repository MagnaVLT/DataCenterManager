package magna.vlt.ui.perspectives.database.migrator.tables.mappingTable;

import java.util.Hashtable;


public class FCM_Event_Report_Map extends Map{
	
	protected void init()
	{
		addEventIdMap();
		addUserAnnotationMap();
		addPredefinedAnnotationIDMap();
	}

	private void addUserAnnotationMap() {
		maps.put("userannotation", new Hashtable<String, String>());
		maps.get("userannotation").put("S_MISSED", "");
		maps.get("userannotation").put("S_FALSE_DETECTED", "");
		maps.get("userannotation").put("S_HOST_REL", "");
		maps.get("userannotation").put("S_HOST_IRRREL", "");
		maps.get("userannotation").put("S_EBD_ONTRUCK", "");
		maps.get("userannotation").put("A_MISSED", "");
		maps.get("userannotation").put("A_FALSE_DETECTED", "");
		maps.get("userannotation").put("A_GEN_DETECTED", "");
		maps.get("userannotation").put("W_MISSED", "");
		maps.get("userannotation").put("W_FALSE_DETECTED", "");
		maps.get("userannotation").put("W_HOST_REL", "");
		maps.get("userannotation").put("W_HOST_IRRREL", "");
	}
	
	private void addPredefinedAnnotationIDMap() {
		maps.put("predefinedannotationid", new Hashtable<String, String>());
		maps.get("predefinedannotationid").put("S_MISSED", "1");
		maps.get("predefinedannotationid").put("S_FALSE_DETECTED", "2");
		maps.get("predefinedannotationid").put("S_HOST_REL", "3");
		maps.get("predefinedannotationid").put("S_HOST_IRRREL", "4");
		maps.get("predefinedannotationid").put("S_EBD_ONTRUCK", "5");
		maps.get("predefinedannotationid").put("A_MISSED", "6");
		maps.get("predefinedannotationid").put("A_FALSE_DETECTED", "7");
		maps.get("predefinedannotationid").put("A_GEN_DETECTED", "8");
		maps.get("predefinedannotationid").put("W_MISSED", "9");
		maps.get("predefinedannotationid").put("W_FALSE_DETECTED", "10");
		maps.get("predefinedannotationid").put("W_HOST_REL", "11");
		maps.get("predefinedannotationid").put("W_HOST_IRRREL", "12");
	}
	
	private void addEventIdMap() {
		maps.put("eventid", new Hashtable<String, String>());
		maps.get("eventid").put("FFFFFFFFFFFFFFFF", "0");
		maps.get("eventid").put("1010100", "1");
		maps.get("eventid").put("1010200", "2");
		maps.get("eventid").put("1020000", "223");
		maps.get("eventid").put("2010000", "224");
		maps.get("eventid").put("2010100", "3");
		maps.get("eventid").put("2010200", "4");
		maps.get("eventid").put("2010300", "5");
		maps.get("eventid").put("2010400", "6");
		maps.get("eventid").put("2010500", "7");
		maps.get("eventid").put("2020000", "225");
		maps.get("eventid").put("2030000", "226");
		maps.get("eventid").put("2040100", "18");
		maps.get("eventid").put("2040200", "19");
		maps.get("eventid").put("2040300", "20");
		maps.get("eventid").put("3010000", "227");
		maps.get("eventid").put("5010000", "231");
		maps.get("eventid").put("5010100", "213");
		maps.get("eventid").put("5010200", "214");
		maps.get("eventid").put("5020000", "246");
		maps.get("eventid").put("6000000", "232");
		maps.get("eventid").put("8000000", "249");
		maps.get("eventid").put("8010000", "215");
		maps.get("eventid").put("8010100", "216");
		maps.get("eventid").put("8010200", "217");
		maps.get("eventid").put("8010300", "218");
		maps.get("eventid").put("8010400", "219");
		maps.get("eventid").put("8010500", "220");
		maps.get("eventid").put("8010600", "221");
	}

}
