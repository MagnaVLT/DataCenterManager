package magna.vlt.ui.perspectives.user.data;

import java.util.ArrayList;

import magna.vlt.ui.common.Node;

public class Employee extends Node{
	
	private String name;
	private String id;
	private String privilege;
	private String privilegeType;
	private String location;
	private String password;
	private String online;
	private ArrayList<String> managerid;
	private ArrayList<String> managerName;
	private ArrayList<String> projectList = new ArrayList<String>();
	private ArrayList<String> featureList = new ArrayList<String>();
	
	public Employee(String name, String id, String privilege, String privilegeType,
			ArrayList<String> managerid, ArrayList<String> managerName, String location, String password, String online) {
		this.name = name;
		this.id = id;
		this.privilege = privilege;
		this.privilegeType = privilegeType;
		this.managerid = managerid;
		this.managerName = managerName;
		this.location = location;
		this.password = password;
		this.online = online;
	}
	

	public String getPrivilegeType() {
		return privilegeType;
	}


	public ArrayList<String> getProjectList() {
		return projectList;
	}

	public void setProjectList(ArrayList<String> projectList) {
		this.projectList = projectList;
	}

	public ArrayList<String> getFeatureList() {
		return featureList;
	}

	public void setFeatureList(ArrayList<String> featureList) {
		this.featureList = featureList;
	}

	public void setManagerid(ArrayList<String> managerid) {
		this.managerid = managerid;
	}

	public String getOnline() {
		return online;
	}

	public String getPassword() {
		return password;
	}

	
	public ArrayList<String> getFeatures() {
		return features;
	}


	public ArrayList<String> getProjects() {
		return projects;
	}


	public void addProject(String project){
		this.projects.add(project);
	}
	
	public void addFeature(String feature){
		this.features.add(feature);
	}
	
	
	public String getName() {
		return name;
	}


	public String getId() {
		return id;
	}


	public String getPrivilege() {
		return privilege;
	}


	public ArrayList<String> getManagerid() {
		return managerid;
	}


	public ArrayList<String> getManagerName() {
		return managerName;
	}


	public String getLocation() {
		return location;
	}


}