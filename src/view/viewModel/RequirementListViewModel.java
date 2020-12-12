package view.viewModel;

import javafx.collections.*;
import mediator.ProjectManagementSystemModel;
import model.Requirement;


public class RequirementListViewModel {
    private ObservableList<RequirementViewModel> reqList;
    private ProjectManagementSystemModel model;
    private String  projectID;

    public RequirementListViewModel(ProjectManagementSystemModel model, String projectID){
        this.model = model;
        this.reqList = FXCollections.observableArrayList();
        this.projectID = projectID;
        update();
    }

    public ObservableList<RequirementViewModel> getReqList(){
        return reqList;
    }

    public void update(){
        reqList.clear();
        for (int i=0; i<model.getRequirementList(projectID).getSize(); i++){
            reqList.add(new RequirementViewModel(model.getRequirement(i , projectID)));
            System.out.println(model.getRequirement(i, projectID).getUserStory());
        }
    }

    public void add(Requirement req){
        reqList.add(new RequirementViewModel(req));
    }

    public void remove(String reqId){
        for (int i =0 ; i < reqList.size(); i++){
            if(reqList.get(i).getIdProperty().equals(reqId)){
                reqList.remove(i);
                break;
            }
        }
    }



}
