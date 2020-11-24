package model;

import java.util.ArrayList;

public class RequirementList {

	private ArrayList<Requirement> requirementList;

	public RequirementList() {
		this.requirementList = new ArrayList<>();
	}

	public int getSize() {
		return requirementList.size();
	}

	public GeneralTemplate findById(String id) {
		for (Requirement req : requirementList) {
			if (req.getId().equals(id)) return req;
		}

		return null;
	}

	public GeneralTemplate findByTitle(String title) {
		for (Requirement req : requirementList) {
			if (req.getUserStory().equals(title)) return req;
		}

		return null;
	}

	public Requirement findByResponsiblePerson(TeamMember teamMember) {
		for (Requirement req : requirementList) {
			if (req.getResponsiblePerson().equals(teamMember)) return req;
		}

		return null;
	}

	public void add(Requirement requirement) {
		requirementList.add(requirement);
	}

	public void remove(Requirement requirement) {
		requirementList.remove(requirement);
	}

	public void generateId(Requirement requirement) {
		requirement.setId(String.valueOf(requirementList.size() + 1));
	}
}
