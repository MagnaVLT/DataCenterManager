package magna.vlt.ui.common;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Vector;

import magna.vlt.db.retriever.DBWrapper;
import magna.vlt.util.Tokenizer;

public class GUIInfoRetriever extends DBWrapper {
	
	public Hashtable<Integer, String> getIdNamePairList(String id, String name, String table){
		Hashtable<Integer, String> result = new Hashtable<Integer, String>();
		String query = "select " + id + ", " + name + " from " + table + " order by " + id + " desc";
		ResultSet rs = super.getResultSet(query);

		try{
			while(rs.next()){
				int rsID = rs.getInt(id);
				String rsName = rs.getString(name);
				result.put(rsID, rsName);
			}
		}catch (SQLException e){
			
		}
		
		return result;
	}
	
	public Hashtable<String, String> getPairedList(String query, String field1, String field2){
		Hashtable<String, String> result = new Hashtable<String, String>();
		ResultSet rs = super.getResultSet(query);

		try{
			while(rs.next()){
				String f1 = rs.getString(field1);
				String f2 = rs.getString(field2);
				result.put(f1, f2);
			}
		}catch (SQLException e){
			
		}
		
		return result;
	}
	
	public Hashtable<Integer, String> getSeveralList(String query, String id, Vector<String> fieldList){
		Hashtable<Integer, String> result = new Hashtable<Integer, String>();
		ResultSet rs = super.getResultSet(query);

		try{
			while(rs.next()){
				String value = "";
				Integer f1 = rs.getInt(id);
				if(fieldList.size()>0){
					value += rs.getString(fieldList.get(0));
					for(int i = 1; i < fieldList.size(); i++){
						value += "-" + rs.getString(fieldList.get(i));
					}
				}
				result.put(f1, value);
			}
		}catch (SQLException e){
			
		}
		
		return result;
	}
	
	public Hashtable<Integer, String> getTripledList(String query, String field1, String field2, String field3){
		Hashtable<Integer, String> result = new Hashtable<Integer, String>();
		ResultSet rs = super.getResultSet(query);

		try{
			while(rs.next()){
				Integer f1 = rs.getInt(field1);
				String f2 = rs.getString(field2);
				String f3 = rs.getString(field3);
				String value = Tokenizer.concatWithDelim("-", f2, f3);
				result.put(f1, value);
			}
		}catch (SQLException e){
			
		}
		
		return result;
	}
	
	
	public String getName(String query){
		String name = "";
		ResultSet rs = super.getResultSet(query);

		try{
			if(rs.next())
				name = rs.getString("name");
		}catch (SQLException e){
			
		}
		
		return name;
	}
	
	public String[] getNames(String query){
		String[] name = null;
		ResultSet rs = super.getResultSet(query);

		try{
			rs.last();
			name = new String[rs.getRow()];
			rs.beforeFirst();
			for(int i = 0 ; i < name.length ; i++){
				if(rs.next()){
					name[i] = rs.getString("name");
				}
			}
				
		}catch (SQLException e){
			
		}
		
		return name;
	}
	
	public boolean checkId(String id){
		
		String query = "select count(*) count from users where id = '" + id + "';";
		ResultSet rs = super.getResultSet(query);
		int cnt = 0;
		try{
			if(rs.next()){
				cnt = rs.getInt("count");
			}
			
		}catch (SQLException e){
			
		}
		
		return cnt!=0;
	}
	
}
