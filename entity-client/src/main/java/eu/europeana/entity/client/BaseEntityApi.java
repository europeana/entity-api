package eu.europeana.entity.client;

import eu.europeana.entity.client.config.ClientConfiguration;
import eu.europeana.entity.client.connection.EntityApiConnection;

public abstract class BaseEntityApi {

	private final ClientConfiguration configuration;
	protected final EntityApiConnection apiConnection;

	public BaseEntityApi(ClientConfiguration configuration,
			EntityApiConnection apiConnection) {
		this.configuration = configuration;
		this.apiConnection = apiConnection;
	}

	public BaseEntityApi() {
		this.configuration = ClientConfiguration.getInstance();
		this.apiConnection = new EntityApiConnection(
				getConfiguration().getServiceUri(), getConfiguration().getApiKey());
	}

	public EntityApiConnection getApiConnection() {
		return apiConnection;
	}

	public ClientConfiguration getConfiguration() {
		return configuration;
	}

}
