package magna.vlt.ui.perspectives.project.data;

public class Event {
	private int featureId;
	private int id;
	private String name;
	private String description;
	private int eventCategory;
	private String categoryName;
	private int subFeatureCategoryId;
	private String requestor;
	
	
	public Event(int featureId, int id, String name, String description, int eventCategory, String categoryName) {
		this.featureId = featureId;
		this.id = id;
		this.name = name;
		this.description = description;
		this.eventCategory = eventCategory;
		this.categoryName = categoryName;
	}
	
	public Event(int featureId, String name, String description, int eventCategory, int subFeatureCategoryId, String requestor) {
		this.featureId = featureId;
		this.name = name;
		this.description = description;
		this.eventCategory = eventCategory;
		this.subFeatureCategoryId = subFeatureCategoryId; 
		this.requestor = requestor;
	}
	
	
	public void setId(int id) {
		this.id = id;
	}

	public int getSubFeatureCategoryId() {
		return subFeatureCategoryId;
	}

	public String getRequestor() {
		return requestor;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public void setEventCategory(int eventCategory) {
		this.eventCategory = eventCategory;
	}

	public int getEventCategory() {
		return eventCategory;
	}

	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	public int getFeatureId() {
		return featureId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
