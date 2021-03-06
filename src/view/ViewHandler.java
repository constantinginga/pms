package view;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
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
    private String darkMode;
    private String lightMode;
    private String currentMode;

    public ViewHandler(ProjectManagementSystemModel model) {
        this.model = model;
        this.state = new ViewState();
        currentScene = new Scene(new Region());
    }

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.darkMode = "stylesheets/dark-mode.css";
        this.lightMode = "stylesheets/light-mode.css";
        setCSS(false);
        primaryStage.setResizable(false);
        openView("mainWindow");
    }


    public void openView(String id) {
        Region root = switch (id) {
            case "mainWindow" -> loadMainWindowView("MainWindowView.fxml", state);
            case "reqList" -> loadRequirementListView("RequirementListView.fxml", state);
            case "taskList" -> loadTaskListView("TaskListView.fxml", state);
            case "taskView" -> loadTaskView("TaskView.fxml", state);
            case "addReq" -> loadAddRequirementView("AddRequirementView.fxml", state);
            case "addTask" -> loadAddTaskView("AddTaskView.fxml", state);
            case "addProject" -> loadAddProjectView("AddProjectView.fxml", state);
            case "teamView" -> loadTeamMemberListView("TeamMemberListView.fxml", state);
            default -> null;
        };
        currentScene.setRoot(root);
        String title = "";
        if (root.getUserData() != null) {
            title += root.getUserData();
        }
        primaryStage.setTitle(title);
        primaryStage.setScene(currentScene);
        primaryStage.setHeight(root.getPrefHeight());
        primaryStage.setWidth(root.getPrefWidth());
        primaryStage.show();
    }

    public void setCSS(boolean isDarkMode) {
        System.out.println(currentScene.getStylesheets());
        if (isDarkMode) {
            currentScene.getStylesheets().remove(lightMode);
            currentScene.getStylesheets().add(darkMode);
            currentMode = darkMode;
        } else {
            currentScene.getStylesheets().remove(darkMode);
            currentScene.getStylesheets().add(lightMode);
            currentMode = lightMode;
        }
    }

    public String getCSS() {
        return currentMode;
    }

    public void closeView() {
        primaryStage.close();
    }

    private Region loadMainWindowView(String fxmlfile, ViewState state) {
        if (mainWindowViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlfile));
                Region root = loader.load();
                mainWindowViewController = loader.getController();
                mainWindowViewController.init(this, root, model, state);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            mainWindowViewController.reset();
        }
        return mainWindowViewController.getRoot();
    }

    private Region loadRequirementListView(String fxmlfile, ViewState state) {
        if (requirementListViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlfile));
                Region root = loader.load();
                requirementListViewController = loader.getController();
                requirementListViewController.init(this, root, model, state);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            requirementListViewController.reset();
        }
        return requirementListViewController.getRoot();
    }

    private Region loadTaskListView(String fxmlfile, ViewState state) {
        if (taskListViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlfile));
                Region root = loader.load();
                taskListViewController = loader.getController();
                taskListViewController.init(this, root, model, state);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            taskListViewController.reset();
        }
        return taskListViewController.getRoot();
    }

    private Region loadTaskView(String fxmlfile, ViewState state) {
        if (taskViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlfile));
                Region root = loader.load();
                taskViewController = loader.getController();
                taskViewController.init(this, root, model, state);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            taskViewController.reset();
        }

        return taskViewController.getRoot();
    }

    private Region loadAddProjectView(String fxmlfile, ViewState state) {
        if (addProjectViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlfile));
                Region root = loader.load();
                addProjectViewController = loader.getController();
                addProjectViewController.init(this, root, model, state);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            addProjectViewController.reset();
        }
        return addProjectViewController.getRoot();
    }

    private Region loadAddRequirementView(String fxmlfile, ViewState state) {
        if (addRequirementViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlfile));
                Region root = loader.load();
                addRequirementViewController = loader.getController();
                addRequirementViewController.init(this, root, model, state);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            addRequirementViewController.reset();
        }
        return addRequirementViewController.getRoot();
    }

    private Region loadAddTaskView(String fxmlfile, ViewState state) {
        if (addTaskViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlfile));
                Region root = loader.load();
                addTaskViewController = loader.getController();
                addTaskViewController.init(this, root, model, state);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            addTaskViewController.reset();
        }
        return addTaskViewController.getRoot();
    }

    private Region loadTeamMemberListView(String fxmlfile, ViewState state) {
        if (teamMemberListViewController == null) {

            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlfile));
                Region root = loader.load();
                teamMemberListViewController = loader.getController();
                teamMemberListViewController.init(this, root, model, state);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            teamMemberListViewController.reset();
        }
        return teamMemberListViewController.getRoot();
    }
   /* private Region loadProjectTeamMemberListView(String fxmlfile, ViewState state){
        if (proTeamMemberListViewController == null){
            try{
                FXMLLoader loader= new FXMLLoader();
                loader.setLocation((getClass().getResource(fxmlfile)));
                Region root = loader.load();
                proTeamMemberListViewController = loader.getController();
                proTeamMemberListViewController.init(this, root, model, state);
            } catch( Exception e){
                e.printStackTrace();
            }
        } else {
            proTeamMemberListViewController.reset();
        }
        return proTeamMemberListViewController.getRoot();
    }
*/

}
