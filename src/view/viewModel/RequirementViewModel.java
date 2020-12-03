package view.viewModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Requirement;

public class RequirementViewModel {
    private StringProperty userStoryProperty;
    private StringProperty IdProperty;
    private StringProperty statusProperty;
    private StringProperty deadLineProperty;

    public RequirementViewModel (Requirement requirement){
        userStoryProperty = new SimpleStringProperty(requirement.getUserStory());
        IdProperty = new SimpleStringProperty(requirement.getId());
        statusProperty = new SimpleStringProperty(requirement.getStatus());
        deadLineProperty = new SimpleStringProperty(requirement.getDeadline().toString());
    }


   public StringProperty getUserStoryProperty(){
        return userStoryProperty;
   }

   public StringProperty getIdProperty(){
        return IdProperty;
   }

   public StringProperty getStatusProperty(){
        return statusProperty;
   }

   public StringProperty getDeadLineProperty(){
        return deadLineProperty;
   }

}
