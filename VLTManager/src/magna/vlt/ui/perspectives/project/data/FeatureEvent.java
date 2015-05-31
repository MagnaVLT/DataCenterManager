package magna.vlt.ui.perspectives.project.data;

import java.util.ArrayList;

import magna.vlt.ui.common.Node;

public class FeatureEvent extends Node{
	public final static String project = "project"; 
	public final static String feature = "feature";
	public final static String subFeature = "subFeature";
	public final static String event = "event";
	private ArrayList<Object> roots = new ArrayList<Object>();
	private String type;
	private String id;
	private String name;
	private String description;
	private String categoryId;
	private String categoryName;
	
	public FeatureEvent(String viewId, String type, String id, String name, String description) {
		super(viewId);
		this.type = type;
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
	public FeatureEvent(String viewId, String type, String id, String name,
			String description, String categoryId, String categoryName) {
		super(viewId);
		this.type = type;
		this.id = id;
		this.name = name;
		this.description = description;
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}



	public String getCategoryId() {
		return categoryId;
	}



	public String getCategoryName() {
		return categoryName;
	}



	public String getType() {
		return type;
	}

	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
}
