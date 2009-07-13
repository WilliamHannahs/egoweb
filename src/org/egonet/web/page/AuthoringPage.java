package org.egonet.web.page;

import java.util.ArrayList;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import org.egonet.web.Main;

import org.hibernate.*;

import org.egonet.web.model.Study;

//import persistence.*;

public class AuthoringPage extends EgonetPage
{
	private Label study;
	private TextField studyField;
	private Model studyActiveModel;
	//private ArrayList<Study> studies;
	
    public AuthoringPage()
    {
    	super("Authoring");
        
        add(new FeedbackPanel("feedback"));
        
        Form form = new Form("form");
        studyField = new TextField("studyNameField", new Model(""));
        studyField.setRequired(true);
        form.add(studyField);
        
        ArrayList<String> studyActiveOptions = new ArrayList<String>();
        studyActiveOptions.add("Active");
        studyActiveOptions.add("Inactive");
        studyActiveModel = new Model(studyActiveOptions.get(0)); // Could also leave this null.
        form.add(new DropDownChoice("studyActiveField",studyActiveModel,studyActiveOptions));
        
        form.add(
        	new Button("createStudy") {
        		@Override
        		public void onSubmit() {
        			String name = (String) studyField.getModelObject();
        			String active = (String) studyActiveModel.getObject();
        			study.setModelObject(name+" ("+active+")");
        			studyField.setModelObject("");
        			//studies.add(new Study(name));
        			saveStudy(new Study(name));
        		}
        	});
        add(form);
        
        add(study = new Label("study", new Model("")));
        
        //studies = new ArrayList<Study>();
        ListView studyView = new ListView("studies",getStudies()) { // TODO: need a property model that will call getStudies() on each request
        	protected void populateItem(ListItem item) {
        		final Study s = (Study) item.getModelObject();
        		Link studyLink = new Link("studyLink") {
        			public void onClick() {
        				setResponsePage(new EditStudyPage(s));
        			}
        		};
        		studyLink.add(new Label("name", s.getName()));
        		item.add(studyLink);
        		item.add(new Label("active", s.isActive() ? "ACTIVE" : "INACTIVE"));
        	}
        };
        add(studyView);
    }
    
    private ArrayList<Study> getStudies() {
    	Session session = Main.getDBSessionFactory().openSession();
    	Transaction tx = session.beginTransaction();
    	
    	ArrayList<Study> studies = new ArrayList<Study>(session.createQuery("from Study s order by s.name").list());
    	
    	tx.commit();
    	session.close();
    	
    	return studies;
    }
    
    private void saveStudy(Study study) {
    	Session session = Main.getDBSessionFactory().openSession();
    	Transaction tx = session.beginTransaction();
    	
    	Long studyId = (Long) session.save(study);
    	
    	tx.commit();
    	session.close();
    }
}

