import javafx.application.Application;
import javafx.stage.Stage;
import mediator.ProjectManagementSystemModelManager;
import view.ViewHandler;

public class ProjectManagementApplication extends Application {

    @Override
    public void start(Stage stage) {
        ViewHandler viewHandler = new ViewHandler(new ProjectManagementSystemModelManager());
        viewHandler.start(stage);
    }
}
