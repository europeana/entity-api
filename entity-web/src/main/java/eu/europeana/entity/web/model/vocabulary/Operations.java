package eu.europeana.entity.web.model.vocabulary;

public interface Operations {

	//search is also retrieve
	//crud operations
	public static final String RETRIEVE = "retrieve";
	public static final String CREATE = "create";
	public static final String UPDATE = "update";
	public static final String DELETE = "delete";
	
	//feedback
	public static final String REPORT = "report";
	
	//admin
	public static final String ADMIN_ALL = "admin_all";
	public static final String ADMIN_UNLOCK = "admin_unlock";
	public static final String ADMIN_REINDEX = "admin_reindex"; 

	//user
	public static final String USER_CREATE = "user_create";
	public static final String USER_DELETE = "user_delete";
	
	//moderation
	public static final String MODERATION_ALL = "moderation_all";
	
}
