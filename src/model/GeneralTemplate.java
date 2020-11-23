package model;

public abstract class GeneralTemplate {

	private String status;
	private String id;
	private int actualTime;
	private int estimatedTime;
	private TeamMember responsiblePerson;
	public final String STATUS_STARTED = "Started";
	public final String STATUS_ENDED = "Ended";
	public final String STATUS_NOT_STARTED = "Not Started";
	public final String STATUS_APPROVED = "Approved";
	public final String STATUS_REJECTED = "Rejected";

	public GeneralTemplate(String status, TeamMember responsiblePerson) {
		this.status = status;
		this.responsiblePerson = responsiblePerson;
	}

	public double getEstimatedTime() {
		return estimatedTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public abstract String generateId();
}
