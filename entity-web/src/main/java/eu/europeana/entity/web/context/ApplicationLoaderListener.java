package eu.europeana.entity.web.context;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;

import eu.europeana.api.commons.web.context.BaseApplicationLoaderListener;

public class ApplicationLoaderListener extends BaseApplicationLoaderListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent>{

	public ApplicationLoaderListener(){
		super();
		onApplicationEvent(null);
	}
	
	
	@Override
	public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
		registerSocksProxy();
	}

	protected String getAppConfigFile() {
		return "config/entity.properties";
	}
		
}
