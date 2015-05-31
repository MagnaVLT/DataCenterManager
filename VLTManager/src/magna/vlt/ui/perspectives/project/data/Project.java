package magna.vlt.ui.perspectives.project.data;

import java.sql.Date;

public class Project{
	private int id;
	private String name;
	private String oem;
	private int generation;
	private String strGeneration;
	private String ownerid;
	private Date start;
	private Date end;
	private String strStart;
	private String strEnd;
	private int isOpen;
	private String[] featureList;
	private String[] featureNameList;
	private String[] featureDescription;
	private String[] eventList;
	private String[] eventNameList;
	private String[] eventDescription;
	private String[] eventCategoryList;
	
	public Project(int id, String name, String ownerid){
		this.id = id;
		this.name = name;
		this.ownerid = ownerid;
	}
	
	public Project(int id, String name, String oem, int generation, String ownerid, Date start, Date end, int isOpen) {
		this.id = id;
		this.name = name;
		this.oem = oem;
		this.generation = generation;
		this.ownerid = ownerid;
		this.start = start;
		this.end = end;
		this.isOpen = isOpen;
	}
	
	public Project(String name, String companyid, String ownerid, String generation, String start, String end, int isOpen, 
			String[] feature,  String[] featureName, String[] featureDescription, String[] event, 
			String[] eventNanme, String[] eventDescription, String[] eventCategoryList) {
	
		this.name = name;
		this.oem = companyid;
		this.strGeneration = generation;
		this.ownerid = ownerid;
		this.strStart = start;
		this.strEnd = end;
		this.featureList = feature;
		this.featureNameList = featureName;
		this.featureDescription = featureDescription;
		this.eventList = event;
		this.eventDescription = eventDescription;
		this.eventNameList = eventNanme;
		this.eventCategoryList = eventCategoryList;
		this.isOpen = isOpen;
	}
	
	
	public int getIsOpen() {
		return isOpen;
	}

	public void setFeatureList(String[] featureList) {
		this.featureList = featureList;
	}

	public void setFeatureNameList(String[] featureNameList) {
		this.featureNameList = featureNameList;
	}

	public void setFeatureDescription(String[] featureDescription) {
		this.featureDescription = featureDescription;
	}

	public void setEventList(String[] eventList) {
		this.eventList = eventList;
	}

	public void setEventNameList(String[] eventNameList) {
		this.eventNameList = eventNameList;
	}

	public void setEventDescription(String[] eventDescription) {
		this.eventDescription = eventDescription;
	}

	public String[] getEventCategoryList() {
		return eventCategoryList;
	}

	public void setEventCategoryList(String[] eventCategoryList) {
		this.eventCategoryList = eventCategoryList;
	}

	public String[] getFeatureNameList() {
		return featureNameList;
	}

	public String[] getEventNameList() {
		return eventNameList;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStrGeneration() {
		return strGeneration;
	}

	public String getStrStart() {
		return strStart;
	}

	public String getStrEnd() {
		return strEnd;
	}

	public String[] getFeatureList() {
		return featureList;
	}

	public String[] getFeatureDescription() {
		return featureDescription;
	}

	public String[] getEventList() {
		return eventList;
	}

	public String[] getEventDescription() {
		return eventDescription;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getOem() {
		return oem;
	}

	public int getGeneration() {
		return generation;
	}

	public String getOwnerid() {
		return ownerid;
	}

	public Date getStart() {
		return start;
	}

	public Date getEnd() {
		return end;
	}

} 