package model;

public class Project extends GeneralTemplate {

    private String id;
    private String title;
    private String note;
    private TeamMember projectCreator;
    private TeamMember scrumMaster;
    private TeamMember productOwner;
    private RequirementList requirementList;

    public Project(String title, String status) {
        super(status);
        set(title);
        this.id = null;
        this.note = null;
        this.productOwner = null;
        this.scrumMaster = null;
        this.projectCreator = null;
        this.requirementList = new RequirementList();
    }

    public void setId(String id) {
        this.id = id;
    }

    public void set(String title) {
        if (title == null || title.equals("") || title.length() < 3)
            throw new IllegalArgumentException("Invalid title");
        this.title = title;
    }

    public Requirement getRequirement(String ID) {
        for (int i = 0; i < requirementList.getSize(); i++) {
            if (requirementList.getRequirement(i).getId().equals(ID)) {
                return requirementList.getRequirement(i);
            }
        }
        return null;
    }

    //get requirement by index
    public Requirement getRequirement(int index) {
        return requirementList.getRequirement(index);
    }


    public void addRequirement(Requirement requirement) {
        requirementList.add(requirement);
    }

    public void removeRequirement(Requirement requirement) {
        for (int i = 0; i < requirementList.getSize(); i++) {
            if (requirementList.getRequirement(i).equals(requirement)) {
                requirementList.remove(requirement);
            }
        }
    }

    //remove requirement by id
    public void removeRequirement(String id) {
        for (int i = 0; i < requirementList.getSize(); i++) {
            if (requirementList.getRequirement(i).getId().equals(id)) {
                Requirement requirement = requirementList.getRequirement(i);
                requirementList.remove(requirement);
            }
        }
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public RequirementList getRequirementList() {
        return requirementList;
    }

    public void setRequirementList(RequirementList requirementList) {
        this.requirementList = requirementList;
    }

    public void setProjectCreator(TeamMember teamMember) {
        projectCreator = teamMember;
    }

    public void setScrumMaster(TeamMember teamMember) {
        scrumMaster = teamMember;
    }

    public void setProductOwner(TeamMember teamMember) {
        productOwner = teamMember;
    }

    public void setPosition(String position, TeamMember teamMember) {
        switch (position.toLowerCase()) {
            case "scrum master":
                scrumMaster = teamMember;
                break;
            case "project creator":
                projectCreator = teamMember;
                break;
            case "product owner":
                productOwner = teamMember;
        }
    }

    public void setStatusForProject(String Status) {
        setStatus(Status);
        if (Status.equals(STATUS_ENDED)) {
            requirementList.FinishAllRequiremnts();
        }
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Project)) return false;

        Project other = (Project) obj;
        return super.equals(other) &&
                title != null &&
                requirementList != null &&
                title.equals(other.title) &&
                requirementList.equals(other.requirementList) &&
                (note == null && other.note == null || note != null && note.equals(other.note)) &&
                (id == null && other.id == null || id != null && id.equals(other.id));
    }

    public TeamMember getProjectCreator() {
        return projectCreator;
    }

    public TeamMember getScrumMaster() {
        return scrumMaster;
    }

    public TeamMember getProductOwner() {
        return productOwner;
    }
}
