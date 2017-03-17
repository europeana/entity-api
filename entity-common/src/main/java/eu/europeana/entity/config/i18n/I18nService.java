package eu.europeana.entity.config.i18n;

public interface I18nService {

	String getMessage(String key);

	String getMessage(String key, String args[]);
	
}
