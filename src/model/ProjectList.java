package model;

import java.util.ArrayList;

public class ProjectList {

	private ArrayList<Project> projectList;
	private int idIndex;

	public ProjectList() {
		projectList= new ArrayList<>();
		this.idIndex = 0;
	}

	public void generateId(Project project){
		project.setId(String.valueOf(idIndex));
	}

	public void add(Project project) {
	projectList.add(project);
	this.idIndex++;
	generateId(project);
	}

	public void remove(Project project) {
		projectList.remove(project);
		// get the index of last element in projectList + 1
		this.idIndex = Integer.parseInt(projectList.get(projectList.size() - 1).getId() + 1);
	}

	public Project getProject(int index) {
		return projectList.get(index);
	}

	public GeneralTemplate findById(String id) {
		GeneralTemplate template= null;
		for (int i= 0; i < projectList.size(); i++){
			if (projectList.get(i).getId().equals(id)){
			template = projectList.get(i);
			}
		}
		return template;
	}

	public GeneralTemplate findByTitle(String title) {
		GeneralTemplate template= null;
		for (int i= 0; i < projectList.size(); i++){
			if (projectList.get(i).getTitle().equals(title)){
				template = projectList.get(i);
			}
		}
		return template;
	}

	@Override
	public String toString() {
		String s = "";
		for (Project project : projectList) {
			s += project.getId() + "    " + project.getTitle() + "    " +project.getStatus() + "\n";
			RequirementList requirementList = project.getRequirementList();
			s += "-------------REQUIREMENT LIST-------------------\n";
			for (int i = 0; i < requirementList.getSize(); i++) {
				Requirement req = requirementList.getRequirement(i);
				s += req.getId() + "   " + req.getUserStory() + "   " + req.getDeadline() + "   " + req.getResponsiblePerson().getName() + "   " + req.getStatus() + "\n";
				TaskList taskList = req.getTaskList();
				s += "-------------TASK LIST-------------------\n";
				for (int j = 0; j < taskList.getSize(); j++) {
					Task task = taskList.getTask(j);
					s+= task.getId() + "   " + task.getTitle() + "   " + task.getDeadline() + "   " + task.getResponsiblePerson().getName() + "    " + task.getStatus() + "\n";
				}
			}
		}

		return s;
	}
}
