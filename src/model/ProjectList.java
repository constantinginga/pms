package model;

import java.util.ArrayList;

public class ProjectList {

	private Project project;
	private ArrayList<Project> projectList;

	public ProjectList() {
		projectList= new ArrayList<>();
	}
	public void generateId(Project project){
		project.setId(String.valueOf(projectList.size()));
	}

	public void add(Project project) {
	projectList.add(project);
	generateId(project);
	}

	public void remove(Project project) {
	projectList.remove(project);
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

}
