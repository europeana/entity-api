package eu.europeana.entity.solr.view;

import eu.europeana.entity.web.model.view.TimeSpanPreview;

public class TimeSpanPreviewImpl extends EntityPreviewImpl implements TimeSpanPreview {

	public String begin;
	public String end;
	
	@Override
	public String getBegin() {
		return begin;
	}
	@Override
	public void setBegin(String begin) {
		this.begin = begin;
	}
	@Override
	public String getEnd() {
		return end;
	}
	@Override
	public void setEnd(String end) {
		this.end = end;
	}
}
