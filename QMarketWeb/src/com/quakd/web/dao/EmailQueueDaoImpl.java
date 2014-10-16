package com.quakd.web.dao;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.quakd.web.model.EmailQueue;

@Repository
public class EmailQueueDaoImpl implements EmailQueueDao {

	private static Logger log = Logger.getLogger(EmailQueueDaoImpl.class);	

    @Autowired
    SessionFactory sessionFactory;
 
    protected Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }
    
    @Transactional
	@Override
	public void saveEmail(EmailQueue queue) {		
		if(queue.getEmailCreatedDt() == null) {
			queue.setEmailCreatedDt(Calendar.getInstance().getTime());
		}
		if(queue.getProcessed() == null) {
			queue.setProcessed("N");
		}
		
		if(queue.getEmailId() == null) {
			getCurrentSession().save(queue);
		} else {
			getCurrentSession().update(queue);
		}
	}
    
    @Transactional
	@Override
	public void deleteEmail(EmailQueue queue) {
		getCurrentSession().delete(queue);
	}
    
    @Transactional
	@Override
	public void deleteEmailByID(Long emailId) {
		EmailQueue email = findByEmailId(emailId);
		if(email != null) {
			deleteEmail(email);
		}
	}
    
    @Transactional
	@Override
	public EmailQueue findByEmailId(Long emailId) {
		Query query = getCurrentSession().createQuery("from EmailQueue where emailId = :id");
		query.setParameter("id", emailId);
		List<EmailQueue> list = query.list();
		return list != null && list.size() > 0 ? list.get(0) : null;
	}
    
    @Transactional
	@Override
	public List<EmailQueue> findAndProcess() {	
		Query query = getCurrentSession().createQuery("from EmailQueue where processed = 'N'");	
		query.setMaxResults(100);
		List<EmailQueue> list = query.list();
		if(list != null && list.size() > 0) {
			for(EmailQueue queue : list) {
				queue.setProcessed("Y");
				saveEmail(queue);
			}		
		}		
		//set back to jdbc default
		query.setMaxResults(0);
		return list;
	}

	
}
