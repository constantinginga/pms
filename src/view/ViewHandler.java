package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import mediator.ProjectManagementSystemModel;
import view.controller.*;

public class ViewHandler {
    private Scene currentScene;
    private Stage primaryStage;
    private ProjectManagementSystemModel model;
    private ViewState state;

    private MainWindowViewController mainWindowViewController;
    private AddRequirementViewController addRequirementViewController;
    private AddTaskViewController addTaskViewController;
    private AddProjectViewController addProjectViewController;
    private RequirementListViewController requirementListViewController;
    private TaskListViewController taskListViewController;
    private TaskViewController taskViewController;
    private TeamMemberListViewController teamMemberListViewController;

    public ViewHandler(ProjectManagementSystemModel model){
        this.model = model;
        this.state = new ViewState();
        currentScene = new Scene(new Region());
    }

    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        openView("mainWindow");
    }

    public void openView(String id){
        Region root = null;
        switch (id){
            case "mainWindow":
                root = loadMainWindowView("MainWindowView.fxml", state);
                break;

            case "reqList":
                root = loadRequirementListView("RequirementListView.fxml", state);
                break;
            case "taskList":
                root = loadTaskListView("TaskListView.fxml",state);
                break;

            case "taskView":
                root = loadTaskView("TaskView.fxml", state);
                break;

            case "addReq":
                root = loadAddRequirementView("AddRequirementView.fxml",state);
                break;

            case "addTask":
                root = loadAddTaskView("AddTaskView.fxml",state);
                break;

            case "addProject":
                root = loadAddProjectView("AddProjectView.fxml",state);
                break;

            case "teamView":
                root = loadTeamMemberListView("TeamMemberListView.fxml",state);
                break;
        }
        currentScene.setRoot(root);
        String title = "";
        if(root.getUserData() != null){
            title += root.getUserData();
        }

        primaryStage.setTitle(title);
        primaryStage.setScene(currentScene);
        primaryStage.setHeight(root.getPrefHeight());
        primaryStage.setWidth(root.getPrefWidth());
        primaryStage.show();
    }

    public void closeView(){
        primaryStage.close();
    }

    private Region loadMainWindowView(String fxmlfile, ViewState state){
        if(mainWindowViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlfile));
                Region root = loader.load();
                mainWindowViewController = loader.getController();
                mainWindowViewController.init(this, root, model, state);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            mainWindowViewController.reset();
        }
        return mainWindowViewController.getRoot();
    }

    private Region loadRequirementListView(String fxmlfile, ViewState state){
        if(requirementListViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlfile));
                Region root = loader.load();
                requirementListViewController = loader.getController();
                requirementListViewController.init(this, root, model, state);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            requirementListViewController.reset();
        }
        return requirementListViewController.getRoot();
    }

    private Region loadTaskListView(String fxmlfile, ViewState state){
        if (taskListViewController == null){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlfile));
            Region  root = loader.load();
            taskListViewController = loader.getController();
            taskListViewController.init(this, root, model, state);
        }catch (Exception e){
            e.printStackTrace();
        }
        }else {
            taskListViewController.reset();
        }
        return taskListViewController.getRoot();
    }

    private Region loadTaskView(String fxmlfile, ViewState state){
        if(taskViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlfile));
                Region root = loader.load();
                taskViewController = loader.getController();
                taskViewController.init(this, root, model, state);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            taskViewController.reset();
        }

        return taskViewController.getRoot();
    }

    private Region loadAddProjectView(String fxmlfile, ViewState state){
        if(addProjectViewController == null){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlfile));
            Region root = loader.load();
            addProjectViewController= loader.getController();
            addProjectViewController.init(this, root, model, state);
        }catch (Exception e){
            e.printStackTrace();
        }
        }else {
            addProjectViewController.reset();
        }
        return addProjectViewController.getRoot();
    }

    private Region loadAddRequirementView(String fxmlfile, ViewState state){
        if(addRequirementViewController == null){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlfile));
            Region root = loader.load();
            addRequirementViewController = loader.getController();
            addRequirementViewController.init(this,  root, model, state);
        }catch (Exception e){
            e.printStackTrace();
        }
        }else {
            addRequirementViewController.reset();
        }
        return addRequirementViewController.getRoot();
    }

    private Region loadAddTaskView(String fxmlfile, ViewState state){
        if(addTaskViewController == null){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlfile));
            Region root = loader.load();
            addTaskViewController = loader.getController();
            addTaskViewController.init(this,root, model,state);
        }catch (Exception e){
            e.printStackTrace();
        }
        }else {
            addTaskViewController.reset();
        }
        return addTaskViewController.getRoot();
    }

    private Region loadTeamMemberListView(String fxmlfile, ViewState state){
        if(teamMemberListViewController == null) {

            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlfile));
                Region root = loader.load();
                teamMemberListViewController = loader.getController();
                teamMemberListViewController.init(this, root, model, state);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            teamMemberListViewController.reset();
        }
        return teamMemberListViewController.getRoot();
    }
}
