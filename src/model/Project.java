package model;

public class Project extends GeneralTemplate {

	private String title;

	private String note;

	private TeamMember projectCreator;

	private TeamMember scrumMaster;

	private TeamMember productOwner;

	private TeamMemberList teamMembers;

	private Requirement requirement;

	private Requirement[] requirement;

	private ProjectList projectList;

	private RequirementList[] requirementList;

	public Project(String title) {

	}

	public String generateId() {
		return null;
	}

	public void set(String title) {

	}

	public void setNote() {

	}

	public void setProjectCreator(TeamMember teamMember) {

	}

	public void setScrumMaster(TeamMember teamMember) {

	}

	public void setProductOwner(TeamMember teamMember) {

	}

	public void setPosition(String position) {

	}

}
