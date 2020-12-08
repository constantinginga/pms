import mediator.ProjectManagementSystemModel;
import mediator.ProjectManagementSystemModelManager;
import model.*;

public class Start
{
  public static void main(String[] args)
  {
    ProjectManagementSystemModel model;
    model = new ProjectManagementSystemModelManager();
    System.out.println(model.getTeamMemberList().findById("2"));
    model.addProject(new Project("project1","Started"));
    System.out.println(model.getProject("1"));
   // model.addProject(new Project("PlsDontDelite","Started"));
    //System.out.println(model.getTeamMemberList().getSize());
    //model.addTeamMember(new TeamMember("ja"));
    //System.out.println(model.getTeamMemberList().getSize());
      //model.addTeamMember(new TeamMember("bob2"));


//    System.out.println(model.getTeamMemberList().findById("1"));
//    System.out.println(model.getTeamMemberList().getSize());
    //        System.out.println(model.getTeamMemberList().getSize());
    //        model.removeTeamMember("1");
    //        System.out.println(model.getTeamMemberList().getSize());
    //        System.out.println(model.getTeamMemberList().findById("2"));

    //        projectList.add(new Project("Test project", "Not Started"));
    //        requirementList.add(new Requirement("As a user, blablabla", member, "Started", 200, new MyDate()));
    //        projectList.getProject(0).setRequirementList(requirementList);
    //        requirementList.getRequirement(0).setTaskList(taskList);
    //        taskList.add(new Task("Some random task", member2,  10, new MyDate()));
    //        taskList.add(new Task("Another random task", member,  2, new MyDate()));
    //
    //        taskList.generateId(taskList.getTask(0));
    //        requirementList.generateId(requirementList.getRequirement(0));
    //        projectList.generateId(projectList.getProject(0));
    //
    //        requirementList.getRequirement(0).setMembers(teamMemberList);
    //
    //        System.out.println(projectList.getProject(0).getId());
    //        System.out.println(projectList.findById("1"));
    //        System.out.println(projectList);
    //
    //        projectList.getProject(0).setMembers(teamMemberList);
    //        System.out.println(projectList.getProject(0).getMembers().getTeamMember(0).getName());
    //        projectList.getProject(0).getMembers().add(new TeamMember("TEST"));
    //        System.out.println(projectList.getProject(0).getMembers().getTeamMember(3).getName());
  }
}
