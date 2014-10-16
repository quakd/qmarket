package com.quakd.web.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.quakd.web.model.UsStates;

@Repository
public class UsStatesDaoImpl implements UsStatesDao {

    @Autowired
    SessionFactory sessionFactory;
 
    protected Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }
    
    @Transactional
	public List<UsStates> findAll() {		
    	List list = getCurrentSession().createQuery("from UsStates order by name").list();
		return list; 
	}

}
