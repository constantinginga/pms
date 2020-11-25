package model;

public class Requirement extends TaskAndRequirementTemplate {

	private String userStory;
	private String id;
	private TaskList taskList;

	public Requirement(String userStory, TeamMember responsiblePerson, String status, int estimatedTime, MyDate deadline) {
		super(status, responsiblePerson, estimatedTime, deadline);
		this.userStory = userStory;
		this.taskList = new TaskList();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserStory() {
		return userStory;
	}

	public TaskList getTaskList() {
		return taskList;
	}

	public void setTaskList(TaskList taskList) {
		this.taskList = taskList;
	}

	@Override public void setTitle(String userStory) {
		this.userStory = userStory;
	}
}
