package eu.europeana.entity.web.service.authorization;

import eu.europeana.entity.config.EntityConfiguration;

public interface AuthorizationService extends eu.europeana.api.commons.service.authorization.AuthorizationService{

	EntityConfiguration getConfiguration();
	
}
