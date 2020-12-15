import com.sun.javafx.css.StyleManager;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import mediator.ProjectManagementSystemModelManager;
import view.ViewHandler;



public class ProjectManagementApplication extends Application {

    @Override
    public void start(Stage stage) {
        ViewHandler viewHandler = new ViewHandler(new ProjectManagementSystemModelManager());
        StyleManager.getInstance().addUserAgentStylesheet(viewHandler.getCSS());
        Image image = new Image("logo/logopms.png");
        stage.getIcons().add(image);
        Application.setUserAgentStylesheet(viewHandler.getCSS());
        viewHandler.start(stage);
    }
}
