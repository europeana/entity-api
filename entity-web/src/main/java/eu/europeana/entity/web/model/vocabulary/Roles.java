package eu.europeana.entity.web.model.vocabulary;

public enum Roles {

	ANONYMOUS(new String[]{Operations.RETRIEVE}), 
	USER(new String[]{Operations.RETRIEVE, Operations.CREATE, Operations.DELETE, Operations.UPDATE, Operations.REPORT}), 
	TESTER(new String[]{Operations.RETRIEVE, Operations.CREATE, Operations.DELETE, Operations.UPDATE, Operations.REPORT}), 
	ADMIN(new String[]{Operations.RETRIEVE, Operations.CREATE, Operations.DELETE, Operations.UPDATE, Operations.REPORT, Operations.ADMIN_ALL, Operations.ADMIN_UNLOCK, Operations.ADMIN_REINDEX, Operations.USER_CREATE, Operations.USER_DELETE}), 
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
	
}
