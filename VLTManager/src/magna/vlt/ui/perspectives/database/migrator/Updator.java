package magna.vlt.ui.perspectives.database.migrator;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

public  class Updator  implements IRunnableWithProgress {
	private Connection conn;
	private String tableName;
	private String insert_query; 
	private Hashtable<String, Vector<String>> data; 
	private Vector<String> fields; 
	private Vector<Integer> types;
	private Vector<String> idList;
	private ArrayList<String> updatedRows = new ArrayList<String>();
	private ArrayList<String> notUpdatedRows = new ArrayList<String>();
	
	public ArrayList<String> getUpdatedRows() {
		return updatedRows;
	}
	
	public ArrayList<String> getNotUpdatedRows() {
		return notUpdatedRows;
	}
	
	protected void insert(String[] fields){
		try {
			delete(fields[0]);
			PreparedStatement pstmt = conn.prepareStatement(insert_query);
			for(int i = 0 ; i < fields.length ; i++){
				System.out.println(fields[i]);
				setStatement(pstmt, fields, i);
			}
			
			
			pstmt.executeUpdate();
			pstmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void delete(String key) throws SQLException{
		Statement stmt = conn.createStatement();
		stmt.execute("delete from " + tableName + " where " + fields.get(0) + " = " + key);
	}
	
	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		Vector<Vector<String>> rows = this.transform(data, fields);
		
		monitor.beginTask("Running inserting data to " + tableName + " operation", rows.size()*100);
		try {
			PreparedStatement pstmt = conn.prepareStatement(insert_query);
			int totalUploadedRows = 0;
			
			for (int total = 0; total < rows.size() && !monitor.isCanceled(); total += 1) 
			{
				
				if (monitor.isCanceled())
					throw new InterruptedException("The long running operation was cancelled");
				
				Vector<String> row = rows.get(total);
				String updatedRow = "";
				boolean isExistRow= false;
				
				for(int i = 0 ; i < row.size() ; i++)
				{
					boolean last = (i == row.size()-1);
					if(i==0)
					{
						if(this.isRow(this.idList.get(total), fields.get(0), types.get(i), tableName)) {
							isExistRow = true;

							String notUpdatedRow = "";
							for(int k = 0 ; k < row.size() ; k++) {
								notUpdatedRow = setStatement(row, notUpdatedRow, k, last);
							}
							this.notUpdatedRows.add(notUpdatedRow);
							break;
						} else {
							totalUploadedRows++; 
						}
					}
					updatedRow = setStatement(pstmt, updatedRow, row, i, last);
				}
				if(!updatedRow.trim().equals("")) this.updatedRows.add(updatedRow);
				monitor.worked(100);
				if(!isExistRow) pstmt.addBatch();
				isExistRow = false;
			}
			pstmt.executeBatch();
			pstmt.close();
			System.out.println(totalUploadedRows + " has been uploaded.");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		monitor.done();
	}
	private String setStatement(Vector<String> row, String updatedRow, int i, boolean last)throws SQLException {
		if(types.get(i)==Migrator._STRING)
		{
			if(!last) updatedRow += row.get(i) + "&";
			else updatedRow += row.get(i);
		}else if(types.get(i)==Migrator._INTEGER)
		{
			
			if(row.get(i) != null && !row.get(i).equals("")){
				if(!last) updatedRow += row.get(i) + "&";
				else updatedRow += row.get(i);
			}else{
				if(!last) updatedRow += " NULL:";
				else updatedRow += " NULL";
			}
				
		}else if(types.get(i)==Migrator._BIGINT)
		{
			if(!last) updatedRow += row.get(i) + "&";
			else updatedRow += row.get(i);
		}else if(types.get(i)==Migrator._DOUBLE)
		{
			if(!last) updatedRow += row.get(i) + "&";
			else updatedRow += row.get(i);
		}
		
		return updatedRow;
	}
	
	private String setStatement(PreparedStatement pstmt, String updatedRow, Vector<String> row, int i, boolean last)throws SQLException {
		if(types.get(i)==Migrator._STRING)
		{
			if(!last) updatedRow += row.get(i) + "&";
			else updatedRow += row.get(i);
			
			pstmt.setString(i+1, row.get(i));
		}else if(types.get(i)==Migrator._INTEGER)
		{
			
			if(row.get(i) != null && !row.get(i).equals("")){
				if(!last) updatedRow += row.get(i) + "&";
				else updatedRow += row.get(i);
				pstmt.setInt(i+1, Integer.valueOf(row.get(i)));
			}else{
				if(!last) updatedRow += " NULL:";
				else updatedRow += " NULL";
				pstmt.setNull(i+1, Types.NULL);
			}
				
		}else if(types.get(i)==Migrator._BIGINT)
		{
			if(!last) updatedRow += row.get(i) + "&";
			else updatedRow += row.get(i);
			pstmt.setLong(i+1, Long.valueOf(row.get(i)));
		}else if(types.get(i)==Migrator._DOUBLE)
		{
			if(!last) updatedRow += row.get(i) + "&";
			else updatedRow += row.get(i);
			pstmt.setDouble(i+1, Double.valueOf(row.get(i)));
		}
		
		return updatedRow;
	}
	
	
	public void setStatement(PreparedStatement pstmt, String[] row, int i)throws SQLException {
		if(types.get(i)==Migrator._STRING)
		{
			pstmt.setString(i+1, row[i]);
		}else if(types.get(i)==Migrator._INTEGER)
		{
			
			if(!row[i].trim().equals("NULL") && (row[i] != null && !row[i].equals(""))){
				pstmt.setInt(i+1, Integer.valueOf(row[i]));
			}else{
				pstmt.setNull(i+1, Types.NULL);
			}
				
		}else if(types.get(i)==Migrator._BIGINT)
		{
			pstmt.setLong(i+1, Long.valueOf(row[i]));
		}else if(types.get(i)==Migrator._DOUBLE)
		{
			pstmt.setDouble(i+1, Double.valueOf(row[i]));
		}
	}
	
	public void setData(Connection conn, String tableName, String insert_query, Vector<String> fields, Vector<Integer> types){
		this.conn = conn;
		this.tableName = tableName;
		this.insert_query = insert_query;
		this.fields = fields;
		this.types = types;
	}
	
	public void setData(Connection conn, String tableName, String insert_query, 
			Hashtable<String, Vector<String>> data, Vector<String> fields, Vector<String> idList, Vector<Integer> types){
		this.conn = conn;
		this.tableName = tableName;
		this.insert_query = insert_query;
		this.data = data;
		this.fields = fields;
		this.types = types;
		this.idList = idList;
	}
	
	private Vector<Vector<String>> transform(Hashtable<String, Vector<String>> data, Vector<String> fields)
	{
		Vector<Vector<String>> rows = new Vector<Vector<String>>();
		
		for(int k = 0 ; k < data.get(fields.get(0)).size() ; k++)
		{
			Vector<String> r = new Vector<String>();
			for(int i = 0 ; i < fields.size() ; i++)
			{
				String c = data.get(fields.get(i)).get(k);
				r.add(c);
			}
			rows.add(r);
		}

		return rows;
		
	}
	
	private boolean isRow(String id, String field, int idType, String table){
		int cnt = 0;
		try {
			
			Statement stmt = conn.createStatement();
			String query = "";
			if(idType==Migrator._STRING) query = "select count(*) as cnt from " + table + " where "+field+" = '" + id + "';";
			else query = "select count(*) as cnt from " + table + " where "+field+" = " + id + ";";
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) cnt = rs.getInt("cnt");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt>0;
	}
}

