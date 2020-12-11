package model;

import java.util.ArrayList;

public class ProjectList {

    private ArrayList<Project> projectList;
    private int idIndex;

    public ProjectList() {
        projectList = new ArrayList<>();
        this.idIndex = 0;
    }

    public void generateId(Project project) {
        if (project == null) throw new IllegalArgumentException("Invalid project");
        project.setId(String.valueOf(idIndex));
    }

    public int getSize() {
        return projectList.size();
    }

    public void add(Project project) {
        projectList.add(project);
        this.idIndex++;
        generateId(project);
    }

    public void remove(Project project) {
        projectList.remove(project);
    }

    public Project getProject(int index) {
        return projectList.get(index);
    }

    public GeneralTemplate findById(String id) {
        GeneralTemplate template = null;
        for (int i = 0; i < projectList.size(); i++) {
            if (projectList.get(i).getId().equals(id)) {
                template = projectList.get(i);
            }
        }
        return template;
    }

    public GeneralTemplate findByTitle(String title) {
        GeneralTemplate template = null;
        for (int i = 0; i < projectList.size(); i++) {
            if (projectList.get(i).getTitle().equals(title)) {
                template = projectList.get(i);
            }
        }
        return template;
    }

    public Project getProject(String ID) {
        for (Project project : projectList) {
            if (project.getId().equals(ID)) {
                return project;
            }
        }
        return null;
    }

    @Override public boolean equals(Object obj) {
        if (!(obj instanceof ProjectList)) return false;

        ProjectList other = (ProjectList) obj;
        if (idIndex != other.idIndex || projectList == null || projectList.size() != other.getSize()) return false;
        for (int i = 0; i < projectList.size(); i++) {
            if (!projectList.get(i).equals(other.getProject(i))) return false;
        }

        return true;
    }

    @Override
    public String toString() {
        String s = "";
        for (Project project : projectList) {
            s += project.getId() + "    " + project.getTitle() + "    " + project.getStatus() + "\n";
            RequirementList requirementList = project.getRequirementList();
            s += "-------------REQUIREMENT LIST-------------------\n";
            for (int i = 0; i < requirementList.getSize(); i++) {
                Requirement req = requirementList.getRequirement(i);
                s += req.getId() + "   " + req.getUserStory() + "   " + req.getDeadline() + "   " + req.getResponsiblePerson().getName() + "   " + req.getStatus() + "\n";
                TaskList taskList = req.getTaskList();
                s += "-------------TASK LIST-------------------\n";
                for (int j = 0; j < taskList.getSize(); j++) {
                    Task task = taskList.getTask(j);
                    s += task.getId() + "   " + task.getTitle() + "   " + task.getDeadline() + "   " + task.getResponsiblePerson().getName() + "    " + task.getStatus() + "\n";
                }
            }
        }

        return s;
    }
}
