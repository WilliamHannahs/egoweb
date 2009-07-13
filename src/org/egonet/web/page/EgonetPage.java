package org.egonet.web.page;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

public class EgonetPage extends WebPage
{

	private String title;

	public EgonetPage() {
		this("Egonet");
	}

    public EgonetPage(String title)
    {
    	this.title = title;
    
    	add(new Label("headTitle",getTitle()));
    	add(new Label("inlineTitle",getTitle()));
    }
    
    public String getTitle() {
    	return title;
    }
}

