package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class ViewHandler {
    private Scene currentScene;
    private Stage primaryStage;

    private MainWindowViewController mainWindowViewController;
    private ProjectDetailsAndEditController projectViewAndEditController;
    private TaskListView taskListViewController;
    private TaskDetailsAndEditController taskDetailsAndEditController;
    private AddRequirementController addRequirementController;
    private AddTaskController addTaskController;
    private AddProjectController addProjectController;

    public ViewHandler(){
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
                root = loadMainWindowView("MainWindowView.fxml");
                break;

            case "projectDetails":
                root = loadProjectDetailsAndEditView("ProjectDetailsAndEditView.fxml");
                break;
            case "requirementDetails":
                root = loadTaskListView("TaskListView.fxml");
                break;

            case "taskDetails":
                root = loadTaskDetailsAndEditView("TaskDetailsAndEditView.fxml");
                break;

            case "addRequirement":
                root = loadAddRequirementView("AddRequirementView.fxml");
                break;

            case "addTask":
                root = loadAddTaskView("AddTaskView.fxml");
                break;

            case "addProject":
                root = loadAddProjectView("AddProjectView.fxml");
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

    private Region loadMainWindowView(String fxmlfile){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlfile));
            Region  root = loader.load();
            mainWindowViewController = loader.getController();
            mainWindowViewController.init(this, root);
        }catch (Exception e){
            e.printStackTrace();
        }
        return mainWindowViewController.getRoot();
    }

    private Region loadProjectDetailsAndEditView(String fxmlfile){

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlfile));
            Region  root = loader.load();
            projectViewAndEditController = loader.getController();
            projectViewAndEditController.init(this, root);

        }catch (Exception e){
            e.printStackTrace();
        }
        return projectViewAndEditController.getRoot();
    }

    private Region loadTaskListView(String fxmlfile){

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlfile));
            Region  root = loader.load();
            taskListViewController = loader.getController();
            taskListViewController.init(this, root);
        }catch (Exception e){
            e.printStackTrace();
        }
        return taskListViewController.getRoot();
    }

    private Region loadTaskDetailsAndEditView(String fxmlfile){

        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlfile));
            Region root = loader.load();
            taskDetailsAndEditController = loader.getController();
            taskDetailsAndEditController.init(this, root);
        }catch (Exception e){
            e.printStackTrace();
        }
        return taskDetailsAndEditController.getRoot();
    }

    private Region loadAddProjectView(String fxmlfile){

        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlfile));
            Region root = loader.load();
            addProjectController = loader.getController();
            addProjectController.init(this,root);
        }catch (Exception e){
            e.printStackTrace();
        }
        return addProjectController.getRoot();
    }

    private Region loadAddRequirementView(String fxmlfile){

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlfile));
            Region root = loader.load();
            addRequirementController = loader.getController();
            addRequirementController.init(this,root);
        }catch (Exception e){
            e.printStackTrace();
        }
        return addRequirementController.getRoot();
    }

    private Region loadAddTaskView(String fxmlfile){

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlfile));
            Region root = loader.load();
            addTaskController = loader.getController();
            addTaskController.init(this,root);
        }catch (Exception e){
            e.printStackTrace();
        }
        return addTaskController.getRoot();
    }
}
