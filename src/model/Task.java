package model;

public class Task extends TaskAndRequirementTemplate {

	private String title;
	private String Id;

	public Task(String title, TeamMember responsiblePerson, String status, int estimatedTime, MyDate deadline) {
		super(status, responsiblePerson, estimatedTime, deadline);
		this.title = title;
	}

	public void setId(String Id) {
		this.Id = Id;
	}

	@Override public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public String getId() {
		return Id;
	}
}
