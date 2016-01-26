package md.stomatology.model.type;

public enum AuthorityType {

	ROLE_ADMIN, ROLE_DIRECTOR, ROLE_ACCOUNTANT, ROLE_EMPLOYEE;

	private AuthorityType() {
	}

	public String getName() {
		return this.name();
	}

}
