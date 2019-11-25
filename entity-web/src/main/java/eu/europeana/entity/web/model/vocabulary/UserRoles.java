package eu.europeana.entity.web.model.vocabulary;

import eu.europeana.api.commons.definitions.vocabulary.Role;

public enum UserRoles implements eu.europeana.api.commons.definitions.vocabulary.Role {

	ANONYMOUS(new String[]{Operations.RETRIEVE}), 
	USER(new String[]{Operations.RETRIEVE, Operations.CREATE, Operations.DELETE, Operations.UPDATE}), 
	TESTER(new String[]{Operations.RETRIEVE, Operations.CREATE, Operations.DELETE, Operations.UPDATE}), 
	ADMIN(new String[]{Operations.RETRIEVE, Operations.CREATE, Operations.DELETE, Operations.UPDATE, Operations.ADMIN_ALL, Operations.ADMIN_UNLOCK, Operations.ADMIN_REINDEX, Operations.USER_CREATE, Operations.USER_DELETE}), 
	MODERATOR(new String[]{Operations.MODERATION_ALL});
	
	String[] operations;
	
	UserRoles (String[] operations){
		this.operations = operations;
	}
	
	public String[] getOperations() {
		return operations;
	}
	
	public void setOperations(String[] operations) {
		this.operations = operations;
	}

	/**
	 * This method returns the api specific Role for the given role name
	 * 
	 * @param name the name of user role 
	 * @return the user role
	 */
	public static Role getRoleByName(String name) {
	    Role userRole = null;
	    for(UserRoles role : UserRoles.values()) {
		if(role.name().toLowerCase().equals(name)) {
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
