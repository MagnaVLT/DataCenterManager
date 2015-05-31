package magna.vlt;

import magna.vlt.ui.perspectives.database.conn.DBConnectorLegacy;
import magna.vlt.ui.perspectives.database.conn.DBConnectorNewDB;
import magna.vlt.ui.perspectives.database.conn.DBInfo;
import magna.vlt.ui.perspectives.database.migrator.tables.Clip_Info;
import magna.vlt.ui.perspectives.database.migrator.tables.mappingTable.Clip_Info_Map;

public class Starter {

	public static void main(String[] args) {
		DBConnectorLegacy.getConnection(new DBInfo("jdbc:mysql://localhost:3306/mazda_hil_annotation",  "root", "hil"));
		DBConnectorNewDB.getConnection(new DBInfo("jdbc:mysql://localhost:3306/fcm_gen3_authorization" ,  "root", "hil"));
		
//		new EyeQ_Event_Report(new EyeQ_Event_Report_Map(), "eyeq_event_report", "event_report").migrate();
//		new Clip_Info(new Clip_Info_Map(), "clip_info", "clip_info").migrate();
	}
}