package model;

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> bf26fd5bbd9b7f32762c55ea6c6d6750908bf378
public class Task extends TaskAndRequirementTemplate
{

  private String title;
  private String id;

	public Task(String title, TeamMember responsiblePerson, int estimatedTime, MyDate deadline) {
		super(STATUS_NOT_STARTED, responsiblePerson, estimatedTime, deadline);
		setTitle(title);
	}

  public void setId(String id)
  {

    // check if id is a number, is not null and is not empty string
    try
    {
      int idInt = Integer.parseInt(id);
    }
    catch (NumberFormatException e)
    {
      throw new IllegalArgumentException("Invalid id");
    }
    this.id = id;
  }

  @Override public void setTitle(String title)
  {
    if (title == null || title.equals("") || title.length() < 3)
      throw new IllegalArgumentException("Invalid title");
    this.title = title;
  }

  public String getTitle()
  {
    return title;
  }

  public String getId()
  {
    return id;
  }
<<<<<<< HEAD
=======
public class Task extends TaskAndRequirementTemplate {

	private String title;
	private String id;

	public Task(String title, TeamMember responsiblePerson, int estimatedTime, MyDate deadline) {
		super(STATUS_NOT_STARTED, responsiblePerson, estimatedTime, deadline);
		setTitle(title);
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
>>>>>>> 0ca929e954d0d966d833495d6642ae9c78df3f62
=======
>>>>>>> bf26fd5bbd9b7f32762c55ea6c6d6750908bf378
}
