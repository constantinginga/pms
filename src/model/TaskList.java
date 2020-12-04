package model;

import java.util.ArrayList;

public class TaskList {

    private ArrayList<Task> taskList;
    private int idIndex;

    public TaskList() {
        taskList = new ArrayList<>();
        idIndex = 0;
    }

    public GeneralTemplate findByTitle(String title) {
        if (title == null || title.equals("")) throw new IllegalArgumentException("Title is invalid");
        for (Task e : taskList) {
            if (e.getTitle().equals(title)) {
                return e;
            }
        }
        return null;
    }
    public int getIndex(Task task){
        for (int i = 0; i< taskList.size(); i++){
            if (taskList.get(i).equals(task)){
                return i;
            }
        }
        return -1;
    }
    public void FinishAllTasks(){
        for (Task e :taskList){
            e.setStatus(GeneralTemplate.STATUS_ENDED);
        }
    }
    public GeneralTemplate findById(String id) {
        try {
            int idNum = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid id");
        }

        for (Task e : taskList) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    public Task getTask(int index) {
        try {
            return taskList.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Index out of bounds");
        }
    }

    public void add(Task task) {
        if (task == null) throw new IllegalArgumentException("Invalid task");
        taskList.add(task);
        this.idIndex++;
        generateId(task);
    }

    public void remove(Task task) {
        boolean wasRemoved = taskList.remove(task);
        // get the index of last element in taskList + 1
        if (wasRemoved) this.idIndex = Integer.parseInt(taskList.get(taskList.size() - 1).getId() + 1);
    }

    public int getSize() {
        return taskList.size();
    }
    public int getActualTimeForAllTasks(){
        int atcualTime= 0;
        for (Task e : taskList){
            atcualTime+= e.getActualTime();
        }
        return atcualTime;
    }
    public void generateId(Task task) {
        if (task == null) throw new IllegalArgumentException("Invalid task");
        task.setId(String.valueOf(idIndex));
    }
}
