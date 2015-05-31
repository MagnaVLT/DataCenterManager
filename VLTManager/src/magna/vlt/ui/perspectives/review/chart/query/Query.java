package magna.vlt.ui.perspectives.review.chart.query;

import java.sql.SQLException;
import java.util.ArrayList;

import magna.vlt.db.retriever.DBWrapper;

public abstract class Query extends DBWrapper{

	protected ArrayList<String> name = new ArrayList<String>();
	protected ArrayList<Integer> value = new ArrayList<Integer>();
	
	public Query(){
		try {
			this.retrieveRecord();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	protected abstract void retrieveRecord() throws SQLException;
	
	public ArrayList<String> getName() {
		return name;
	}

	public ArrayList<Integer> getValue() {
		return value;
	}
	
	protected void setPair(String name, int value){
		this.name.add(name);
		this.value.add(value);
	}
}
