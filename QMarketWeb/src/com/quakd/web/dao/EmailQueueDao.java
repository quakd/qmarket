package com.quakd.web.dao;

import java.util.List;

import com.quakd.web.model.EmailQueue;

public interface EmailQueueDao {
		
	public void saveEmail(final EmailQueue queue);
	
	public List<EmailQueue> findAndProcess();

	public void deleteEmail(final EmailQueue queue);
	
	public EmailQueue findByEmailId(final Long emailId);
	
	public void deleteEmailByID(final Long emailId);	
}
