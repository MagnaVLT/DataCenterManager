package magna.vlt.ui.perspectives.database.migrator;

import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;
import java.util.Vector;

import magna.vlt.ui.perspectives.database.conn.DBConnectorLegacy;
import magna.vlt.ui.perspectives.database.conn.DBConnectorNewDB;
import magna.vlt.ui.perspectives.database.migrator.tables.mappingTable.Map;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.widgets.Composite;

public abstract class Migrator {
	public static final int _STRING = 0;
	public static final int _INTEGER = 1;
	public static final int _DOUBLE = 2;
	public static final int _DATE = 3;
	public static final int _BIGINT = 4;
	
	private Selector selector = new Selector();
	private Updator updator = new Updator();
	private Map map;
	protected Vector<String> fields = new Vector<String>();
	protected String selectQuery;
	protected String insertQuery;
	private Composite parent;
	private String tableName;
	protected String projectid;
	
	public Migrator(Composite parent, String tableName, Map map, String tableNameInLegacyDB, String tableNameInNewDB, String projectid)
	{
		this.tableName = tableNameInNewDB;
		this.parent = parent;
		this.map = map;
		this.projectid = projectid;
//		this.truncate(tableNameInNewDB);
	}
	
	
	public Updator getUpdator() {
		return updator;
	}


	@SuppressWarnings("unused")
	private void truncate(String tableNameInNewDB)
	{
		new Truncator().truncate(DBConnectorNewDB.getConn(), tableNameInNewDB);
	}
	
	protected Hashtable<String, Hashtable<Integer, String>> getChangedData() {
		return selector.getChangedData();
	}
	
	protected Hashtable<String, Hashtable<Integer, String>> getAllData() {
		return selector.getAllData();
	}
	
	private Hashtable<String, Vector<String>> getData(String query)
	{
		Hashtable<String, Vector<String>> data = selector.getTableData(DBConnectorLegacy.getConn(), query, fields, map);
		return data;
	}
	
	public void migrate()
	{

		try {
			this.initSelectQuery();
			this.initInsertQuery();
			fields = this.getFieldsOfLegacy();
			Hashtable<String, Vector<String>> data = this.getData(selectQuery);
			this.addNewField(data);
			updator.setData(DBConnectorNewDB.getConn(), tableName, insertQuery, data, fields, selector.getIdList(), this.getFieldTypesOfNewDB(fields));
			new ProgressMonitorDialog(parent.getShell()).run(true, true, updator);
		} catch (InvocationTargetException e) {
			MessageDialog.openError(parent.getShell(), "Error", e.getMessage());
		} catch (InterruptedException e) {
			MessageDialog.openInformation(parent.getShell(), "Cancelled", e.getMessage());
		}
		
	}
	
	protected void addData(Hashtable<String, Vector<String>> data, String field, String value) 
	{
		this.fields.add(field);
		Vector<String> newData = new Vector<String>();
		int size = this.getSize(data);
		for(int i = 0; i < size ; i++) newData.add(value);
			data.put(field, newData);
	}
	
	protected void addData(Hashtable<String, Vector<String>> data, String field, Hashtable<Integer, String> values) 
	{
		this.fields.add(field);
		Vector<String> newData = new Vector<String>();
		int size = this.getSize(data);
		for(int i = 0; i < size ; i++) {
			if(values.containsKey(i)){
				if(field.equals("predefinedannotationid")){
					System.out.println(values.get(i));
					newData.add(map.get(field, "0"));
				}else{
					newData.add(map.get(field, values.get(i)));	
				}
				
			}
			newData.add("");
		}
		data.put(field, newData);
	}
	
	protected void addData(Hashtable<String, Vector<String>> data, String field, String otherField, String delim, int loc, boolean needMapping) 
	{
		this.fields.add(field);
		Vector<String> newData = new Vector<String>();
		Vector<String> otherFieldData = data.get(otherField);
		
		int size = this.getSize(data);
		for(int i = 0; i < size ; i++) {
			newData.add(map.getValueFromOtherField(field, otherFieldData.get(i), delim, loc, needMapping));
		}
			data.put(field, newData);
	}

	protected int getSize(Hashtable<String, Vector<String>> data)
	{
		return data.get(this.fields.get(0)).size();
	}
	
	protected abstract Vector<String> getFieldsOfLegacy();
	protected abstract Vector<Integer> getFieldTypesOfNewDB(Vector<String> field);
	protected abstract void addNewField(Hashtable<String, Vector<String>> data);
	protected abstract void addNewField();
	protected abstract void initSelectQuery();
	protected abstract void initInsertQuery();
	
	@SuppressWarnings("unused")
	private void print(Hashtable<String, Vector<String>> data)
	{
		for(String key: data.keySet())
		{
			Vector<String> ds = data.get(key);
			for(String d: ds)
			{
				System.out.println(key + ":" + d + " --> " + this.map.get(key, d));
			}
		}
	}
	
	public void forceToInsert(String[] tokens) {
		this.initInsertQuery();
		fields = this.getFieldsOfLegacy();
		this.addNewField();
		updator.setData(DBConnectorNewDB.getConn(), tableName, insertQuery, fields, this.getFieldTypesOfNewDB(fields));
		updator.insert(tokens);
	}
}
