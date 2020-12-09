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
        if (name == null || name.equals("") || name.length() < 3) throw new IllegalArgumentException("Invalid name");
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        try {
            int idInt = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid ID");
        }
        this.id = id;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TeamMember)) return false;

        TeamMember other = (TeamMember) obj;
        return name != null &&
            name.equals(other.name) &&
            (id == null && other.id == null || id != null && id.equals(other.id));
    }

    @Override public String toString()
    {
        return String.format("[%s] %s", id, name);
    }
}
