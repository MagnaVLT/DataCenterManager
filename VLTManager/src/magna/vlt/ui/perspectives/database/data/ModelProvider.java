package magna.vlt.ui.perspectives.database.data;

import java.util.List;

public class ModelProvider {
	private List<String> updatedRows;
	private List<String> notUpdatedRows;
	
	public List<String> getUpdatedRows() {
		return updatedRows;
	}
	public void setUpdatedRows(List<String> updatedRows) {
		this.updatedRows = updatedRows;
	}
	public List<String> getNotUpdatedRows() {
		return notUpdatedRows;
	}
	public void setNotUpdatedRows(List<String> notUpdatedRows) {
		this.notUpdatedRows = notUpdatedRows;
	}
	
}
