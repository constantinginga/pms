package model;

public class Requirement extends GeneralTemplate, TaskAndRequirementTemplate {

	private String userStory;
	private TeamMemberList teamMembers;
	private Task task;
	private RequirementList requirementList;
	private TaskList taskList;

	public Requirement(String userStory, TeamMember responsible) {

	}

	public String generateId() {
		return null;
	}

	public void set(String userStory) {

	}

}
