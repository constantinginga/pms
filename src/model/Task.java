package model;

public class Task extends TaskAndRequirementTemplate {

	private String title;
	private String id;

	public Task(String title, TeamMember responsiblePerson, int estimatedTime, MyDate deadline) {
		super(STATUS_NOT_STARTED, responsiblePerson, estimatedTime, deadline);
		setTitle(title);
		this.id = null;
	}

	public void setId(String id) {

		// check if id is a number, is not null and is not empty string
		try {
			int idInt = Integer.parseInt(id);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid id");
		}
		this.id = id;
	}

	@Override public void setTitle(String title) {
		if (title == null || title.equals("") || title.length() < 3) throw new IllegalArgumentException("Invalid title");
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public String getId() {
		return id;
	}

	@Override public boolean equals(Object obj) {
		if (!(obj instanceof Task)) return false;

		Task other = (Task) obj;
		return super.equals(other) &&
				title != null &&
				title.equals(other.title) &&
				(id == null && other.id == null || id != null && id.equals(other.id));
	}
}
