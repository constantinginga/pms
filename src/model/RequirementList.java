package model;

import java.util.ArrayList;

public class RequirementList {

    private ArrayList<Requirement> requirementList;
    private int idIndex;

    public RequirementList() {
        this.requirementList = new ArrayList<>();
        this.idIndex = 0;
    }

    public int getSize() {
        return requirementList.size();
    }

    public GeneralTemplate findById(String id) {
        for (Requirement req : requirementList) {
            if (req.getId().equals(id)) return req;
        }

        return null;
    }

    public GeneralTemplate findByTitle(String title) {
        for (Requirement req : requirementList) {
            if (req.getUserStory().equals(title)) return req;
        }

        return null;
    }

    public int getIndex(Requirement requirement) {
        for (int i = 0; i < requirementList.size(); i++) {
            if (requirementList.get(i).equals(requirement)) {
                return i;
            }
        }
        return -1;
    }

    public void FinishAllRequiremnts() {
        for (Requirement e : requirementList) {
            e.setStatusForRequirement(GeneralTemplate.STATUS_ENDED);
            e.getTaskList().FinishAllTasks();
        }
    }

    public Requirement getRequirement(int index) {
        try {
            return requirementList.get(index);
        }catch (IndexOutOfBoundsException e){
            throw new IllegalArgumentException("Requirement Not found");
        }

    }

    public Requirement findByResponsiblePerson(TeamMember teamMember) {
        for (Requirement req : requirementList) {
            if (req.getResponsiblePerson().equals(teamMember)) return req;
        }

        return null;
    }

    public void add(Requirement requirement) {
        requirementList.add(requirement);
        this.idIndex++;
        generateId(requirement);
    }

    public void remove(Requirement requirement) {
        requirementList.remove(requirement);
        // get the index of last element in requirementList + 1
        //this.idIndex = Integer.parseInt(requirementList.get(requirementList.size() - 1).getId() + 1);
    }

    public void generateId(Requirement requirement) {
        requirement.setId(String.valueOf(idIndex));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RequirementList)) return false;

        RequirementList other = (RequirementList) obj;
        if (idIndex != other.idIndex || requirementList == null || requirementList.size() != other.getSize()) return false;
        for (int i = 0; i < requirementList.size(); i++) {
            if (!requirementList.get(i).equals(other.getRequirement(i))) return false;
        }

        return true;
    }

    public ArrayList<Requirement> getRequirementList()
    {
        return requirementList;
    }

    public void setRequirementList(ArrayList<Requirement> requirementList)
    {
        this.requirementList = requirementList;
    }

    public int getIdIndex()
    {
        return idIndex;
    }

    public void setIdIndex(int idIndex)
    {
        this.idIndex = idIndex;
    }
}
