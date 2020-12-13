package view.controller;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import mediator.ProjectManagementSystemModel;
import view.ViewHandler;
import view.ViewState;
import view.viewModel.ProjectListViewModel;
import view.viewModel.ProjectViewModel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;

public class MainWindowViewController {
    private Region root;
    private ViewHandler viewHandler;
    private ProjectManagementSystemModel model;
    private ViewState state;
    private ProjectListViewModel viewModel;
    private boolean isDarkMode;
    @FXML
    private TableView<ProjectViewModel> projectListTable;
    @FXML
    private TableColumn<ProjectViewModel, String> idColumn;
    @FXML
    private TableColumn<ProjectViewModel, String> titleColumn;
    @FXML
    private TableColumn<ProjectViewModel, String> statusColumn;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField SearchBar;
    @FXML
    private ImageView toggleImage;

    public MainWindowViewController() {

    }

    public void init(ViewHandler viewHandler, Region root,
                     ProjectManagementSystemModel model, ViewState state) {
        this.viewHandler = viewHandler;
        this.root = root;
        this.model = model;
        this.state = state;
        this.viewModel = new ProjectListViewModel(model);
        this.isDarkMode = false;
        setToggleImageEvent();
        initTable();
        search();
    }

    // set dark mode toggle image
    private void setToggleImage(boolean isDarkMode) {
        String filepath = (isDarkMode) ? "src/stylesheets/light-toggle.png" : "src/stylesheets/dark-toggle.png";
        try {
            FileInputStream f = new FileInputStream(filepath);
            toggleImage.setImage(new Image(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // switch between images
    private void setToggleImageEvent() {
        EventHandler<MouseEvent> eventHandler = mouseEvent -> {
            isDarkMode = !isDarkMode;
            setToggleImage(isDarkMode);
            viewHandler.setCSS(isDarkMode);
        };

        toggleImage.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
    }

    private void initTable() {
        idColumn.setCellValueFactory(
                cellData -> cellData.getValue().idPropertyProperty());
        titleColumn.setCellValueFactory(
                cellData -> cellData.getValue().titlePropertyProperty());
        statusColumn.setCellValueFactory(
                cellData -> cellData.getValue().statusPropertyProperty());
        projectListTable.setItems(viewModel.getList());
    }

    public Region getRoot() {
        return root;
    }

    private void search() {
        // create filtered list
        FilteredList<ProjectViewModel> filteredList = new FilteredList<>(
                viewModel.getList(), b -> true);

        // add listener to searchbar
        SearchBar.textProperty()
                .addListener(((observableValue, oldValue, newValue) -> {
                    filteredList.setPredicate(project -> {
                        if (newValue == null || newValue.isEmpty())
                            return true;

                        String lowerCaseFilter = newValue.toLowerCase();
                        // if found, show project with corresponding name or id
                        return project.getTitleProperty().toLowerCase()
                                .contains(lowerCaseFilter) || project.getIdProperty()
                                .toLowerCase().contains(lowerCaseFilter);
                    });
                }));

        SortedList<ProjectViewModel> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(projectListTable.comparatorProperty());
        projectListTable.setItems(sortedList);
    }

    public void reset() {
        errorLabel.setText("");
        viewModel.update();
    }

    @FXML
    private void handleOpenProjectButton() {
        // set state of project and try to open it
        try {
            state.setSelectedProjectID(
                    projectListTable.getSelectionModel().getSelectedItem()
                            .getIdProperty());
            viewHandler.openView("reqList");
        } catch (Exception e) {
            errorLabel.setText("Choose project to open");
        }
    }

    @FXML
    private void handleAddProjectButton() {
        // determine the id of the newly created Project according to table size
        state.setSelectedProjectID(
                Integer.toString(projectListTable.getItems().size() + 1));
        viewHandler.openView("addProject");
    }

    // show list of all TeamMembers
    @FXML
    private void handleEmployeeButton() {
        viewHandler.openView("teamView");
    }

    @FXML
    private void handleRemoveProjectButton() {
        errorLabel.setText("");
        try {
            // selection of Project to remove
            ProjectViewModel selectedItem = projectListTable.getSelectionModel()
                    .getSelectedItem();
            // removing from list
            boolean remove = confirmation();
            if (remove) {
                String projectId = selectedItem.idPropertyProperty().get();
                model.removeProject(state.getSelectedProjectID());
                viewModel.remove(projectId);
                projectListTable.getItems().remove(selectedItem);
            }
        } catch (Exception e) {
            errorLabel.setText("Choose project to remove");
        }
    }

    private boolean confirmation() {
        int index = projectListTable.getSelectionModel().getSelectedIndex();
        ProjectViewModel selectedItem = projectListTable.getItems().get(index);

        if (index >= projectListTable.getItems().size()) return false;
        // create alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        // set alert message
        alert.setHeaderText(
                "Removing project [" + selectedItem.getIdProperty() + "] "
                        + selectedItem.getTitleProperty() + ": " + selectedItem
                        .getStatusProperty());
        Optional<ButtonType> result = alert.showAndWait();
        return (result.isPresent()) && (result.get() == ButtonType.OK);
    }
}
