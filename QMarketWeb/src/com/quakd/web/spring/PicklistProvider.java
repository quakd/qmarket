package com.quakd.web.spring;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.jpa.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ServletContextAware;

import com.quakd.web.dao.CountryDao;
import com.quakd.web.dao.UsStatesDao;
import com.quakd.web.data.PickList;
import com.quakd.web.model.Country;
import com.quakd.web.model.UsStates;

public class PicklistProvider implements ServletContextAware {
	
	private static Logger log = Logger.getLogger(PicklistProvider.class);	
	private static ServletContext context = null;
	
	@Autowired
	private UsStatesDao states;
	
	@Autowired
	private CountryDao country;
	
	public CountryDao getCountry() {
		return country;
	}

	public void setCountry(CountryDao country) {
		this.country = country;
	}

	@Autowired
	private EmailScheduler email;
	
	public void setServletContext(ServletContext context) {
		this.context = context;
	}
	
	public void init()
	{
		log.info("Initializing the picklists...");
	
		
		List<UsStates> list = states.findAll();	
		List<PickList> states = new ArrayList<PickList>();

		for(UsStates state : list)
		{
			PickList pl = new PickList();
			pl.setLabel(state.getName());
			pl.setValue(state.getId().toString());
			states.add(pl);
		}
		
		List<Country> list2 = country.findAll();
		List<PickList> country = new ArrayList<PickList>();
		for(Country cntry : list2)
		{
			PickList pl = new PickList();
			pl.setLabel(cntry.getName());
			pl.setValue(cntry.getId().toString());
			country.add(pl); 
		}		
		
		context.setAttribute("PL_COUNTRY", country);
		
		context.setAttribute("PL_STATES", states);
		
		context.setAttribute("MAIL_SERVER", "");
		
		email.schedule();
		
	
	}

	public ServletContext getServletContext() {
		return context;
	}

	public UsStatesDao getStates() {
		return states;
	}

	public void setStates(UsStatesDao states) {
		this.states = states;
	}

	public EmailScheduler getEmail() {
		return email;
	}

	public void setEmail(EmailScheduler email) {
		this.email = email;
	}
	
	
	
}
