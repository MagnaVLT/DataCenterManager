package magna.vlt.ui.perspectives.database.migrator.tables;

import java.util.Hashtable;
import java.util.Vector;

import org.eclipse.swt.widgets.Composite;

import magna.vlt.ui.perspectives.database.migrator.Migrator;
import magna.vlt.ui.perspectives.database.migrator.tables.mappingTable.Map;

public class Clip_Info extends Migrator{

	
	public Clip_Info(Composite parent, Map map, String tableNameInLegacyDB, String tableNameInNewDB, String projectid) {
		super(parent, "Clip_Info", map, tableNameInLegacyDB, tableNameInNewDB, projectid);
	}

	protected Vector<String> getFieldsOfLegacy()
	{
		Vector<String> fields = new Vector<String>();
		fields.add("clipid");
		fields.add("vin");
		fields.add("clipname");
		fields.add("starttime");
		fields.add("stoptime");
		fields.add("localpctime");
		fields.add("fcmswver");
		fields.add("mestver");
		fields.add("adtfver");
		fields.add("drivername");
		fields.add("gpstimestamp");
		fields.add("gpslatitude");
		fields.add("gpslongitude");
		fields.add("gpsspeed");
		fields.add("gpsheading");
		fields.add("gpsinvalidcounter");
		fields.add("gpselevation");
		
		return fields;
	}

	@Override
	protected Vector<Integer> getFieldTypesOfNewDB(Vector<String> field) {
		Vector<Integer> types = new Vector<Integer>();
		
		types.add(Migrator._BIGINT); //clipid	1
		types.add(Migrator._STRING); //vin	2
		types.add(Migrator._STRING); //clipname	3
		types.add(Migrator._BIGINT); //adtfstarttime	4
		types.add(Migrator._BIGINT); //adtfstoptime	5
		types.add(Migrator._BIGINT); //localpctime	6
		types.add(Migrator._STRING); //fcmswver	7
		types.add(Migrator._STRING); //mestver	8
		types.add(Migrator._STRING); //adtfver	9
		types.add(Migrator._STRING); //drivername	10
		types.add(Migrator._STRING); //gpstimestamp	11
		types.add(Migrator._DOUBLE); //gpslatitude	12
		types.add(Migrator._DOUBLE); //gpslongitude	13
		types.add(Migrator._DOUBLE); //gpsspeed	14
		types.add(Migrator._DOUBLE); //gpsheading	15
		types.add(Migrator._INTEGER); //gpsinvalidcounter	16
		types.add(Migrator._DOUBLE); //gpselevation	17
		
		types.add(Migrator._STRING); //countrycodeid	18
		types.add(Migrator._INTEGER); //daytypeid	19
		types.add(Migrator._INTEGER); //weathertypeid	20
		types.add(Migrator._INTEGER); //roadtypeid	21
		types.add(Migrator._STRING); //recordingpurpose	22
		
		return types;
	}

	@Override
	protected void addNewField(Hashtable<String, Vector<String>> data) {
		addData(data, "countrycodeid", "AUS");
		addData(data, "daytypeid", "clipname", "_", 2, true);
		addData(data, "weathertypeid", "clipname", "_", 3, true);
		addData(data, "roadtypeid", "clipname", "_", 4, true);
		addData(data, "recordingpurpose", "");
//		addData(data, "recordingpurpose", "clipname", "_", 5, false);
	}
	
	@Override
	protected void initSelectQuery() {
		// TODO Auto-generated method stub
		selectQuery = "select clipid, vin, clipname, starttime, stoptime, localpctime, fcmswver, mestver, adtfver, drivername, gpstimestamp, gpslatitude, gpslongitude, gpsspeed, gpsheading, gpsinvalidcounter, gpselevation "
				+ "from clip_info;";
	}

	@Override
	protected void initInsertQuery() {
		// TODO Auto-generated method stub
		insertQuery = "insert into clip_info (clipid, vin, clipname, adtfstarttime, adtfstoptime, localpctime, fcmswver, mestver, adtfver, drivername, gpstimestamp, gpslatitude, gpslongitude, gpsspeed, gpsheading, gpsinvalidcounter, gpselevation, countrycodeid, daytypeid, weathertypeid, roadtypeid, recordingpurpose)"
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	}

	@Override
	protected void addNewField() {
		fields.addElement("countrycodeid");
		fields.addElement("daytypeid");
		fields.addElement("weathertypeid");
		fields.addElement("roadtypeid");
		fields.addElement("recordingpurpose");
	}


}