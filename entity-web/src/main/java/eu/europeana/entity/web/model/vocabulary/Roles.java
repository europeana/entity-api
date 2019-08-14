package eu.europeana.entity.web.model.vocabulary;

public enum Roles implements eu.europeana.api.commons.definitions.vocabulary.Roles {

	ANONYMOUS(new String[]{Operations.RETRIEVE}), 
	USER(new String[]{Operations.RETRIEVE, Operations.CREATE, Operations.DELETE, Operations.UPDATE}), 
	TESTER(new String[]{Operations.RETRIEVE, Operations.CREATE, Operations.DELETE, Operations.UPDATE}), 
	ADMIN(new String[]{Operations.RETRIEVE, Operations.CREATE, Operations.DELETE, Operations.UPDATE, Operations.ADMIN_ALL, Operations.ADMIN_UNLOCK, Operations.ADMIN_REINDEX, Operations.USER_CREATE, Operations.USER_DELETE}), 
	MODERATOR(new String[]{Operations.MODERATION_ALL});
	
	String[] operations;
	
	Roles (String[] operations){
		this.operations = operations;
	}
	
	public String[] getOperations() {
		return operations;
	}
	
	public void setOperations(String[] operations) {
		this.operations = operations;
	}

//	@Override
	public static Roles getByName(String name) {
	    Roles userRole = null;
	    for(Roles role : Roles.values()) {
		if(role.name().equals(name)) {
		    userRole = role;
		    break;
		}
	    }
	    return userRole;
	}
	
	@Override
	public String getName() {
	    return this.name();
	}

	@Override
	public String[] getPermissions() {
	    return getOperations();
	}
	
}
