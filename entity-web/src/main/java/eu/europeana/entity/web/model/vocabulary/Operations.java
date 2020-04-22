package eu.europeana.entity.web.model.vocabulary;

public interface Operations extends eu.europeana.api.commons.web.model.vocabulary.Operations {
	
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
