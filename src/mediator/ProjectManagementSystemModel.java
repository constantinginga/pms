package mediator;

import model.Project;
import model.TeamMember;
import model.Requirement;
import model.Task;
import model.GeneralTemplate;
import model.MyDate;
import model.TeamMemberList;
import model.TaskList;

public interface ProjectManagementSystemModel {

	public abstract void add(Project project);

	public abstract void remove(Project project);

	public abstract void add(TeamMember teammember);

	public abstract void remove(TeamMember teammember);

	public abstract void add(Requirement requirement);

	public abstract void remove(Requirement requirment);

	public abstract void add(Task task);

	public abstract void remove(Task task);

	public abstract GeneralTemplate findById(String id);

	public abstract GeneralTemplate findByTitle(String title);

	public abstract Requirement findByResponsiblePerson(TeamMember teamMember);

	public abstract Project getProject(int index);

	public abstract Requirement getRequirement(int index);

	public abstract Task getTask(int index);

	public abstract void setProjectCreator(TeamMember teamMember);

	public abstract void setScrumMaster(TeamMember teamMember);

	public abstract void setProductOwner(TeamMember teamMember);

	public abstract String getId();

	public abstract String getTitle();

	public abstract String getStatus();

	public abstract void setStatus(String status);

	public abstract MyDate getDeadline();

	public abstract void setDeadline(MyDate deadline);

	public abstract int getActualTime();

	public abstract void setActualTime(int actualTime);

	public abstract int getEstimatedTime();

	public abstract void setEstimatedTime(int estimatedTime);

	public abstract TeamMemberList getTeamMemberList();

	public abstract TeamMember getResponsiblePerson();

	public abstract void setResponsiblePerson(TeamMember teamMember);

	public abstract String getUserStory();

	public abstract TaskList getTaskList();

	public abstract String getName();

	public abstract void setName(String name);

}
