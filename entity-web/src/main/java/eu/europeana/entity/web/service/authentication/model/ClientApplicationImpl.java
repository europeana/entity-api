package eu.europeana.entity.web.service.authentication.model;

import java.util.HashMap;
import java.util.Map;

import eu.europeana.entity.definitions.model.Agent;
import eu.europeana.entity.definitions.model.authentication.Application;

public class ClientApplicationImpl implements Application {

	private String apiKey;
	private String name;
	private Agent anonymousUser;
	private Agent adminUser;
	private Map<String, Agent> authenticatedUsers = new HashMap<String, Agent>();
	
	@Override
	public String getApiKey() {
		return apiKey;
	}

	@Override
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Agent getAnonymousUser() {
		return anonymousUser;
	}

	@Override
	public void setAnonymousUser(Agent anonymousUser) {
		this.anonymousUser = anonymousUser;
	}

	@Override
	public Agent getAdminUser() {
		return adminUser;
	}

	@Override
	public void setAdminUser(Agent adminUser) {
		this.adminUser = adminUser;
	}

	@Override
	public Map<String, Agent> getAuthenticatedUsers() {
		return authenticatedUsers;
	}

	@Override
	public void addAuthenticatedUser(String token, Agent user) {
		getAuthenticatedUsers().put(token, user);
	}
	
	

}
