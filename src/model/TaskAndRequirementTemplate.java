package model;

public abstract  class TaskAndRequirementTemplate extends GeneralTemplate {

	private MyDate deadline;
	private int actualTime;
	private int estimatedTime;
	private TeamMember responsiblePerson;
	private TeamMemberList teamMemberList;

	public TaskAndRequirementTemplate(String status,TeamMember responsiblePerson, int estimatedTime, MyDate deadline) {
		super(status);
		this.responsiblePerson = responsiblePerson;
		this.estimatedTime = estimatedTime;
		this.deadline = deadline;
		this.teamMemberList = new TeamMemberList();
		this.actualTime = 0;
	}

	public abstract void setTitle(String title);

	public void setDeadline(MyDate deadline) {
		this.deadline = deadline;
	}

	public void setTeamMemberList(TeamMemberList teamMemberList) {
		this.teamMemberList = teamMemberList;
	}

	public TeamMemberList getTeamMemberList() {
		return teamMemberList;
	}

	public void set(String status, TeamMember responsible, int deadline, int estimatedTime, int actualTime, TeamMemberList members) {
		super.setStatus(status);
	}

	public MyDate getDeadline() {
		return deadline;
	}

	public void setEstimatedTime(int estimatedTime) {
		this.estimatedTime = estimatedTime;
	}

	public void setActualTime(int actualTime) {
		this.actualTime = actualTime;
	}

	public TeamMember getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(TeamMember responsible) {
		this.responsiblePerson = responsible;
	}
}
