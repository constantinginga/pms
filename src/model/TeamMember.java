package model;

public class TeamMember {
    private String name;
    private String id;

    public TeamMember(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TeamMember)) return false;

        TeamMember other = (TeamMember) obj;
        return this.name.equals(other.name);
    }
}
