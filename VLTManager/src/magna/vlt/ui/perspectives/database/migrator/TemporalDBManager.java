package magna.vlt.ui.perspectives.database.migrator;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

public class TemporalDBManager implements IRunnableWithProgress{

	private String sqlFileName;
	private Connection conn;
	public TemporalDBManager(Connection conn, String dbName, String sqlFileName){
		this.conn = conn;
		this.sqlFileName = sqlFileName;
	}
	
	public static void create(Connection conn, String dbName){
		try {
			Statement stmt = conn.createStatement();
			stmt.execute("create database " + dbName + ";");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void setData(String dbName, String sqlFileName){
		this.sqlFileName = sqlFileName;
	}
	
	public static void drop(Connection conn, String dbName){
		try {
			Statement stmt = conn.createStatement();
			stmt.execute("DROP database IF EXISTS " + dbName + ";");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		monitor.beginTask("inserting data to temporal database", 100);
		executeSqlScript(conn, new File(sqlFileName));
	    monitor.done();
	}
	
	
	private void executeSqlScript(Connection conn, File inputFile) {

	    // Delimiter
	    String delimiter = ";";

	    // Create scanner
	    Scanner scanner;
	    try {
	        scanner = new Scanner(inputFile).useDelimiter(delimiter);
	    } catch (FileNotFoundException e1) {
	        e1.printStackTrace();
	        return;
	    }

	    // Loop through the SQL file statements 
	    Statement currentStatement = null;
	    while(scanner.hasNext()) {

	        // Get statement 
	        String rawStatement = scanner.next() + delimiter;
	        try {
	            // Execute statement
	            currentStatement = conn.createStatement();
	            currentStatement.execute(rawStatement);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            // Release resources
	            if (currentStatement != null) {
	                try {
	                    currentStatement.close();
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }
	            }
	            currentStatement = null;
	        }
	    }
	}
	
}
