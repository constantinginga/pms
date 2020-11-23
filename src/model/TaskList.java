package model;

import java.util.ArrayList;

public class TaskList {

	private Task task;
	private Requirement requirement;
	private ArrayList<Task> taskList;

	public TaskList() {
	taskList = new ArrayList<Task>();
	}

	public GeneralTemplate findByTitle(String title) {
		for(Task e : taskList){
			if(e.getTitle().equals(title)){
				return e;
			}
		}
		return null;
	}

	public GeneralTemplate findById(String id) {
		for(Task e : taskList){
			if(e.getId().equals(id)){
				return e;
			}
		}
		return null;
	}

	public void add(Task task) {
		taskList.add(task);
		generateId(task);

	}

	public void remove(Task task) {

	}
	public void generateId(Task task) {

		task.setId(String.valueOf(taskList.size()));
	}


}
