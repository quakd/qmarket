package com.quakd.web.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.quakd.web.model.Country;

@Repository
public class CountryDaoImpl implements CountryDao {

    @Autowired
    SessionFactory sessionFactory;
 
    protected Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }
	
    @Transactional
	public List<Country> findAll() {
		List list = getCurrentSession().createQuery("from Country order by name").list();
		return list; 
	}

}
