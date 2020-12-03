package model;

import java.util.ArrayList;

public class RequirementList {

	private ArrayList<Requirement> requirementList;
	private int idIndex;

	public RequirementList() {
		this.requirementList = new ArrayList<>();
		this.idIndex = 0;
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

	public Requirement getRequirement(int index) {
		return requirementList.get(index);
	}

	public Requirement findByResponsiblePerson(TeamMember teamMember) {
		for (Requirement req : requirementList) {
			if (req.getResponsiblePerson().equals(teamMember)) return req;
		}

		return null;
	}

	public void add(Requirement requirement) {
		requirementList.add(requirement);
		this.idIndex++;
		generateId(requirement);
	}

	public void remove(Requirement requirement) {
		requirementList.remove(requirement);
		// get the index of last element in requirementList + 1
		this.idIndex = Integer.parseInt(requirementList.get(requirementList.size() - 1).getId() + 1);
	}

	public void generateId(Requirement requirement) {
		requirement.setId(String.valueOf(idIndex));
	}
}
