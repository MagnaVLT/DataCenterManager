package magna.vlt.ui.perspectives.database.migrator.tables;

import java.util.Hashtable;
import java.util.Vector;

import magna.vlt.ui.perspectives.database.migrator.Migrator;
import magna.vlt.ui.perspectives.database.migrator.tables.mappingTable.Map;

import org.eclipse.swt.widgets.Composite;

public class FCM_Event_Report extends Migrator{

	private String eventCategoryId;
	public FCM_Event_Report(Composite parent, Map map, String tableNameInLegacyDB, String tableNameInNewDB, String projectid, String eventCategoryId) {
		super(parent, "FCM_Event_Report", map, tableNameInLegacyDB, tableNameInNewDB, projectid);
		this.eventCategoryId = eventCategoryId;
	}

	protected Vector<String> getFieldsOfLegacy()
	{
		Vector<String> fields = new Vector<String>();
		fields.add("reportid");
		fields.add("vin");
		fields.add("clipid");
		fields.add("eventid");
		fields.add("eventstatus");
		fields.add("localpctime");
		fields.add("eventadtftime");
		fields.add("userannotation");
		fields.add("gpslongitude");
		fields.add("gpslatitude");
		
		return fields;
	}
	
	protected void addNewField(Hashtable<String, Vector<String>> data)
	{
		addData(data, "companyid", String.valueOf(super.projectid));
		addData(data, "eventcategoryid", this.eventCategoryId);
		addData(data, "predefinedannotationid", super.getChangedData().get("userannotation"));
	}



	@Override
	protected Vector<Integer> getFieldTypesOfNewDB(Vector<String> field) {
		Vector<Integer> types = new Vector<Integer>();
		types.add(Migrator._BIGINT);
		types.add(Migrator._STRING);
		types.add(Migrator._BIGINT);
		types.add(Migrator._INTEGER);
		types.add(Migrator._INTEGER);
		types.add(Migrator._BIGINT);
		types.add(Migrator._BIGINT);
		types.add(Migrator._STRING);
		types.add(Migrator._DOUBLE);
		types.add(Migrator._DOUBLE);
		types.add(Migrator._INTEGER);
		types.add(Migrator._INTEGER);
		types.add(Migrator._INTEGER);
		
		return types;
	}

	protected void initSelectQuery()
	{
		selectQuery = "select reportid, vin, clipid, conv(eventid, 10, 16) eventid, eventstatus, localpctime, eventadtftime, userannotation, gpslongitude, gpslatitude from fcm_event_report";
		
	}
	
	protected void initInsertQuery()
	{
		insertQuery = "insert into event_report (reportid, vin, clipid, eventid, eventstatusid, localpctime, adtftime, userannotation, gpslongitude, gpslatitude, companyid, eventcategoryid, predifinedannotationid) "
				+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	}

	@Override
	protected void addNewField() {
		fields.addElement("companyid");
		fields.addElement("eventcategoryid");
		fields.addElement("predefinedannotationid");
	}
}
