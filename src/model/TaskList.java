package model;

import java.util.ArrayList;

public class TaskList {

    private ArrayList<Task> taskList;
    private int idIndex;

    public TaskList() {
        taskList = new ArrayList<Task>();
        idIndex = 0;
    }

    public GeneralTemplate findByTitle(String title) {
        for (Task e : taskList) {
            if (e.getTitle().equals(title)) {
                return e;
            }
        }
        return null;
    }

    public GeneralTemplate findById(String id) {
        for (Task e : taskList) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    public Task getTask(int index) {
        return taskList.get(index);
    }

    public void add(Task task) {
        taskList.add(task);
        this.idIndex++;
        generateId(task);
    }

    public void remove(Task task) {
        taskList.remove(task);
        // get the index of last element in taskList + 1
        this.idIndex = Integer.parseInt(taskList.get(taskList.size() - 1).getId() + 1);
    }

    public int getSize() {
        return taskList.size();
    }

    public void generateId(Task task) {
        task.setId(String.valueOf(idIndex));
    }
}
