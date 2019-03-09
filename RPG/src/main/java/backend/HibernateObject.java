package backend;

public abstract class HibernateObject {

	private Integer id;

	public final Integer getId() {
		return id;
	}

	public final void setId(Integer id) {
		this.id = id;
	}

}
