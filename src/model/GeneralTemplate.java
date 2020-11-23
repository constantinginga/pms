package model;

public class GeneralTemplate {

	private String status;
	private String Id;
	private int actualTime;
	private int estimatedTime;
	private TeamMember responsiblePerson;
	public final String STATUS_STARTED = "Started";
	public final String STATUS_ENDED = "Ended";
	public final String STATUS_NOT_STARTED = "Not Started";
	public final String STATUS_APPROVED = "Approved";
	public final String STATUS_REJECTED = "Rejected";
	private TeamMember teamMember;
	private Deadline deadline;

	private String status;

	private Id id;

	private TeamMember[] teamMember;

	private Deadline deadline;

	private Id id;

	public Template(String status, TeamMember responsiblePerson) {

	}

	public double getEstimatedTime() {
		return 0;
	}

	public String getStatus() {
		return null;
	}

	public void setStatus(String status) {

	}

}
