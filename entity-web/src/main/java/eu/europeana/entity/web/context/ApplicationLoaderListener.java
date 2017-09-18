package eu.europeana.entity.web.context;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import eu.europeana.api.commons.net.socks.SocksProxyConfig;
import eu.europeana.entity.config.EntityConfiguration;

public class ApplicationLoaderListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent>{

	Properties props = new Properties();
	Logger logger = Logger.getLogger(getClass());
	
	public Properties getProps() {
		return props;
	}

	public ApplicationLoaderListener(){
		System.out.println("instantiation of ApplicationLoaderListener");
		onApplicationEvent(null);
	}
	
	
	@Override
	public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
		
		loadProperties();
		registerSocksProxy();
		
	}

	private void registerSocksProxy(){
		String socksProxyUrl = getProps().getProperty(EntityConfiguration.SOCKS_PROXY_URL);
		if(StringUtils.isEmpty(socksProxyUrl))
			return;
		try {
			SocksProxyConfig socksProxy;
			socksProxy = new SocksProxyConfig(socksProxyUrl);
			socksProxy.init();
		} catch (URISyntaxException e) {
			logger.error("Cannot register socks proxy. The application might not be correctly initialized! URL: " + socksProxyUrl, e);
		}
		
	}

	void loadProperties(){
		String propsLocation = "config/entity.properties";
		try {
			EncodedResource propsResource = new EncodedResource( new ClassPathResource(propsLocation), "UTF-8");
			PropertiesLoaderUtils.fillProperties(getProps(), propsResource);
		} catch (IOException e) {
			logger.warn("Cannot read properties from classath: " + propsLocation, e);		
		}
	}

		
}
