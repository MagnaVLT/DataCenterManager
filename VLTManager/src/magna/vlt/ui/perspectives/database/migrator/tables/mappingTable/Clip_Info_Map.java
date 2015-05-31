package magna.vlt.ui.perspectives.database.migrator.tables.mappingTable;

import java.util.Hashtable;

public class Clip_Info_Map extends Map{
	
	public void init()
	{
		addDayTypeIDMap();
		addWeatherTypeIDMap();
		addRoadTypeIDMap();
	}

	private void addDayTypeIDMap() {
		maps.put("daytypeid", new Hashtable<String, String>());
		maps.get("daytypeid").put("D", "1");
		maps.get("daytypeid").put("U", "2");
		maps.get("daytypeid").put("N", "3");
	}
	
	private void addWeatherTypeIDMap() {
		maps.put("weathertypeid", new Hashtable<String, String>());
		maps.get("weathertypeid").put("CL", "1");
		maps.get("weathertypeid").put("RN", "2");
		maps.get("weathertypeid").put("OC", "3");
		maps.get("weathertypeid").put("SN", "4");
	}

	private void addRoadTypeIDMap() {
		maps.put("roadtypeid", new Hashtable<String, String>());
		maps.get("roadtypeid").put("HW", "1");
		maps.get("roadtypeid").put("UR", "2");
		maps.get("roadtypeid").put("MX", "3");
	}

}
