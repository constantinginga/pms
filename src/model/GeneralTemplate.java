package model;

public abstract class GeneralTemplate
{

	private String status;
	private String id;
	private TeamMemberList members;
	public final String STATUS_STARTED = "Started";
	public final String STATUS_ENDED = "Ended";
	public final String STATUS_NOT_STARTED = "Not Started";
	public final String STATUS_APPROVED = "Approved";
	public final String STATUS_REJECTED = "Rejected";

	public GeneralTemplate(String status)
	{
		this.status = status;
		members = new TeamMemberList();
	}


	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public void setMembers(TeamMemberList members)
	{
		this.members = members;
	}
}