package model;

public abstract class GeneralTemplate {

    private String status;
    private TeamMemberList members;
    public final String STATUS_STARTED = "Started";
    public final String STATUS_ENDED = "Ended";
    public final String STATUS_NOT_STARTED = "Not Started";
    public final String STATUS_APPROVED = "Approved";
    public final String STATUS_REJECTED = "Rejected";

    public GeneralTemplate(String status) {
        setStatus(status);
        members = new TeamMemberList();
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (!status.equals(STATUS_STARTED) &&
                !status.equals(STATUS_ENDED) &&
                !status.equals(STATUS_NOT_STARTED) &&
                !status.equals(STATUS_APPROVED) &&
                !status.equals(STATUS_REJECTED)) throw new IllegalArgumentException("Invalid status");
        this.status = status;
    }

    public void setMembers(TeamMemberList members) {
        if (members == null) throw new IllegalArgumentException("Invalid team member list");
        this.members = members;
    }

    public TeamMemberList getMembers() {
        return members;
    }
}