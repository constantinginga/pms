import model.*;

public class Start {
    public static void main(String[] args) {
        ProjectList projectList = new ProjectList();
        RequirementList requirementList = new RequirementList();
        TaskList taskList = new TaskList();
        TeamMember member = new TeamMember("Bob");
        TeamMember member2 = new TeamMember("Steve");
        TeamMember member3 = new TeamMember("Da ba dee");
        TeamMemberList teamMemberList = new TeamMemberList();
        teamMemberList.add(member);
        teamMemberList.add(member2);
        teamMemberList.add(member3);

        projectList.add(new Project("Test project", "Not Started"));
        requirementList.add(new Requirement("As a user, blablabla", member, "Started", 200, new MyDate()));
        projectList.getProject(0).setRequirementList(requirementList);
        requirementList.getRequirement(0).setTaskList(taskList);
        taskList.add(new Task("Some random task", member2, "Finished", 10, new MyDate()));
        taskList.add(new Task("Another random task", member, "Not Started", 2, new MyDate()));

        taskList.generateId(taskList.getTask(0));
        requirementList.generateId(requirementList.getRequirement(0));
        projectList.generateId(projectList.getProject(0));

        requirementList.getRequirement(0).setMembers(teamMemberList);

        System.out.println(projectList.getProject(0).getId());
        System.out.println(projectList.findById("1"));
        System.out.println(projectList);

        projectList.getProject(0).setMembers(teamMemberList);
        System.out.println(projectList.getProject(0).getMembers().getTeamMember(0).getName());
        projectList.getProject(0).getMembers().add(new TeamMember("TEST"));
        System.out.println(projectList.getProject(0).getMembers().getTeamMember(3).getName());
    }
}
