package model;

public abstract  class TaskAndRequirementTemplate extends GeneralTemplate {

	private MyDate deadline;
	private int actualTime;
	private int estimatedTime;
	private TeamMember responsiblePerson;
	public TaskAndRequirementTemplate(String status,TeamMember responsiblePerson,TeamMemberList members, int actualTime, int estimatedTime, MyDate deadline) {
		super(status);

		this.deadline = deadline;
		responsiblePerson = new TeamMember(null);

	}

	public abstract void setTitle(String title);


	public void setDeadline(MyDate deadline) {
		this.deadline = deadline;
	}


	public void set(String status, TeamMember responsible, int deadline, int estimatedTime, int actualTime, TeamMemberList members) {
		super.setStatus(status);
	}

	public MyDate getDeadline() {
		return deadline;
	}


	public void setEstimatedTime(int estimatedTime)
	{
		this.estimatedTime = estimatedTime;
	}

	public void setActualTime(int actualTime)
	{
		this.actualTime = actualTime;
	}
	public void setResponsiblePerson(TeamMember responsible)
	{
		this.responsiblePerson = responsible;
	}
}
