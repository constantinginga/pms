package model;

import javax.print.DocFlavor;

public class Requirement extends TaskAndRequirementTemplate {

    private String userStory;
    private String id;
    private boolean isFunctional;
    private TaskList taskList;

    public Requirement(String userStory, TeamMember responsiblePerson, String status, int estimatedTime, MyDate deadline, boolean isFunctional) {
        super(status, responsiblePerson, estimatedTime, deadline);
        setTitle(userStory);
        setFunctional(isFunctional);
        this.taskList = new TaskList();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        try {
            int intId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid id");
        }
        this.id = id;
    }

    public boolean isFunctional() {
        return isFunctional;
    }

    public void setFunctional(boolean isFunctional) {
        this.isFunctional = isFunctional;
    }


    public String getUserStory() {
        return userStory;
    }
    @Override  public int getActualTime(){
        if (taskList.getSize()>0){
            return taskList.getActualTimeForAllTasks();
        }
        return 0;
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
        if (userStory.equals("")) throw new IllegalArgumentException("User story can't be Empty");
        this.userStory = userStory;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Requirement)) return false;

        Requirement other = (Requirement) obj;
        return super.equals(other) &&
                userStory != null &&
                taskList != null &&
                userStory.equals(other.userStory) &&
                taskList.equals(other.taskList) &&
                (id == null && other.id == null || id != null && id.equals(other.id));
    }
}
