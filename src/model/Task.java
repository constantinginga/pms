package model;

public class Task extends GeneralTemplate, Requirement, TaskAndRequirementTemplate {

	private String title;

	private TeamMemberList teamMembers;

	private TaskList taskList;

	public Task(String title, TeamMember responsible) {

	}

	public String generateId() {
		return null;
	}

	public void set(String title) {

	}

}
