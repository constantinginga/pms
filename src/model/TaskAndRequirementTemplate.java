package model;

public class TaskAndRequirementTemplate extends GeneralTemplate {

	private MyDate deadline;
	private TeamMemberList members;
	private TeamMemberList teamMemberList;
	private MyDate myDate;

	public TaskAndRequirementTemplate(TeamMemberList members, int actualTime, int estimatedTime, int deadline) {
		this.members = members;
		this
	}

	public void set(String title) {

	}

	public void set(TeamMember responsible) {

	}

	public void set(MyDate deadline) {

	}

	public void set(int estimatedTime) {

	}

	public void set(int actualTime) {

	}

	public void set(TeamMemberList members) {

	}

	public void set(String title, String status, TeamMember responsible, int deadline, int estimatedTime, int actualTime, TeamMemberList members) {

	}

	public int getDeadline() {
		return 0;
	}

}
