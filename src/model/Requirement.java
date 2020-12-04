package model;

import javax.print.DocFlavor;

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

    public Task getTask(String TaskID) {
        for (int i = 0; i < taskList.getSize(); i++) {
            if (taskList.getTask(i).getId().equals(TaskID)) {
                return taskList.getTask(i);
            }
        }
        return null;
    }

    public void addTask(Task task) {
        taskList.add(task);
    }

    public void removeTask(Task task) {
        for (int i = 0; i < taskList.getSize(); i++) {
            if (taskList.getTask(i).equals(task)) {
                taskList.remove(task);
            }
        }
    }

    public void setStatusForRequirement(String Status) {
        setStatus(Status);
        if (Status.equals(STATUS_ENDED)) {
            taskList.FinishAllTasks();
        }
    }

    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
    }

    @Override
    public void setTitle(String userStory) {
        this.userStory = userStory;
    }

    @Override public boolean equals(Object obj) {
    	if (!(obj instanceof Requirement)) return false;

    	Requirement other = (Requirement) obj;
    	return super.equals(other) &&
				userStory != null &&
				id != null &&
				taskList != null &&
				userStory.equals(other.userStory) &&
				id.equals(other.id) &&
				taskList.equals(other.taskList);
	}
}
