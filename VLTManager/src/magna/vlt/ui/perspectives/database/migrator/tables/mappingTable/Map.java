package magna.vlt.ui.perspectives.database.migrator.tables.mappingTable;

import java.util.Hashtable;
import java.util.Vector;

public abstract class Map {
	Hashtable<String, Hashtable<String, String>> maps = new Hashtable<String, Hashtable<String, String>>();
	Vector<String> changedField = new Vector<String>();
	
	public Map()
	{
		this.init();
	}
	
	protected abstract void init();
	
	public String get(String key, String value)
	{
		String newID = "";
		if(maps.containsKey(key) && maps.get(key).containsKey(value))
		{
			newID = maps.get(key).get(value);
		}else
		{
			newID = value;
		}
		
		return newID;
	}
	
	public String getValueFromOtherField(String newField, String otherField, String delim, int loc, boolean needMapping)
	{
		if(needMapping){
			return maps.get(newField).get(this.extractValue(otherField, delim, loc));
		}else{
			return this.extractValue(otherField, delim, loc);
		}
		
	}
	
	protected String extractValue(String str, String delim, int loc) {
		String token[] = str.split(delim);
		return token[loc];
	}
	
}
