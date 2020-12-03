package model;

public abstract  class TaskAndRequirementTemplate extends GeneralTemplate {

	private MyDate deadline;
	private int actualTime;
	private int estimatedTime;
	private TeamMember responsiblePerson;

	public TaskAndRequirementTemplate(String status, TeamMember responsiblePerson, int estimatedTime, MyDate deadline) {
		super(status);
		super.setMembers(new TeamMemberList());
		setResponsiblePerson(responsiblePerson);
		setEstimatedTime(estimatedTime);
		setDeadline(deadline);
		this.actualTime = 0;
	}

	public abstract void setTitle(String title);

	public void setDeadline(MyDate deadline) {
		if (deadline == null || deadline.isBefore(new MyDate())) throw new IllegalArgumentException("Invalid deadline");
		this.deadline = deadline;
	}

	public void set(String status, TeamMember responsible, MyDate deadline, int estimatedTime, TeamMemberList members) {
		super.setStatus(status);
		super.setMembers(members);
		setResponsiblePerson(responsible);
		setDeadline(deadline);
		setEstimatedTime(estimatedTime);
	}

	public MyDate getDeadline() {
		return deadline;
	}

	public void setEstimatedTime(int estimatedTime) {
		if (estimatedTime <= 0) throw new IllegalArgumentException("Invalid estimated time");
		this.estimatedTime = estimatedTime;
	}

	public int getEstimatedTime() {
		return estimatedTime;
	}

	public void setActualTime(int actualTime) {
		if (actualTime < 0) throw new IllegalArgumentException("Invalid actual time");
		this.actualTime = actualTime;
	}

	public int getActualTime() {
		return actualTime;
	}

	public TeamMember getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(TeamMember responsible) {
		if (responsible == null) throw new IllegalArgumentException("Invalid responsible person");
		this.responsiblePerson = responsible;
	}
}
