package magna.vlt.ui.perspectives.database.migrator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Vector;

import magna.vlt.ui.perspectives.database.migrator.tables.mappingTable.Map;

public class Selector {

	private Hashtable<String, Hashtable<Integer, String>> changedData = new Hashtable<String, Hashtable<Integer, String>>();
	private Hashtable<String, Hashtable<Integer, String>> allData = new Hashtable<String, Hashtable<Integer, String>>();
	private Vector<String> idList = new Vector<String>();
	
	public Vector<String> getIdList() {
		return idList;
	}


	public Hashtable<String, Hashtable<Integer, String>> getChangedData() {
		return changedData;
	}

	
	public Hashtable<String, Hashtable<Integer, String>> getAllData() {
		return allData;
	}


	public Hashtable<String, Vector<String>> getTableData
		(Connection conn, String query, Vector<String> fields, Map map)
	{
		Hashtable<String, Vector<String>> data = new Hashtable<String, Vector<String>>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
				for(int i = 0 ; i < fields.size() ; i++)
				{
					Vector<String> rSets = new Vector<String>();
					changedData.put(fields.get(i), new Hashtable<Integer, String>());
					allData.put(fields.get(i), new Hashtable<Integer, String>());
					int row = 0;
					while(rs.next())
					{
						if(!(map.get(fields.get(i), rs.getString(fields.get(i))).equals(rs.getString(fields.get(i)))))
						{
							changedData.get(fields.get(i)).put(row, rs.getString(fields.get(i)));
						}
						
						allData.get(fields.get(i)).put(row, rs.getString(fields.get(i)));
						rSets.add(map.get(fields.get(i), rs.getString(fields.get(i))));
						if(i==0) idList.addElement(rs.getString(fields.get(i)));
						row++;
					}
					
					rs.beforeFirst();
					data.put(fields.get(i), rSets);
				}
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
}