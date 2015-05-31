package magna.vlt.ui.perspectives.user.data;

public class Log {

	private String userid;
	private String userName;
	private String startTime;
	private String endTime;
	private String duration = "";
	
	
	public Log(String userid, String userName, String startTime, String endTime) {
		this.userid = userid;
		this.userName = userName;
		this.startTime = startTime;
		this.endTime = endTime;
	}


	public String getUserid() {
		return userid;
	}


	public String getUserName() {
		return userName;
	}


	public String getStartTime() {
		return startTime;
	}


	public String getEndTime() {
		return endTime;
	}


	public String getDuration() {
		return duration;
	}
	
	
	
}
