package model;

public class Task extends TaskAndRequirementTemplate {

	private String title;
	private String id;

	public Task(String title, TeamMember responsiblePerson, String status, int estimatedTime, MyDate deadline) {
		super(status, responsiblePerson, estimatedTime, deadline);
		this.title = title;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public String getId() {
		return id;
	}
}
