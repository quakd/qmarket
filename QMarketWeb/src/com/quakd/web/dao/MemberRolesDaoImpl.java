package com.quakd.web.dao;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.quakd.web.model.Member;
import com.quakd.web.model.MemberRoles;

@Repository 
public class MemberRolesDaoImpl implements MemberRolesDao {

	private static Logger log = Logger.getLogger(MemberRolesDaoImpl.class);	

    @Autowired
    SessionFactory sessionFactory;
 
    protected Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }
	
	@Autowired
	protected MemberDao memberDao;
	
    @Transactional
	@Override
	public List<MemberRoles> getRoles(String username) {
		Member member = memberDao.findMember(username);		
		if(member != null && member.getMemberId() != null) {
			Query query = getCurrentSession().createQuery("from MemberRoles as mr " +
					"where mr.member.memberId = :id");
			query.setParameter("id",  member.getMemberId());
			List<MemberRoles> list = query.list();					
			return list;
		}
		return null;
	}
    
    @Transactional
	@Override
	public List<MemberRoles> getRolesById(Long memberId) {
		if(memberId != null) {
			Query query = getCurrentSession().createQuery("from MemberRoles as mr " +
					"where mr.member.memberId = :id");
			query.setParameter("id",  memberId);
			List<MemberRoles> list = query.list();	
			return list;
		}
		return null;
	}

	@Transactional
	@Override
	public void save(MemberRoles memberRole) {
		if(memberRole.getRoleId() != null) {
			memberRole.setGrantedDt(Calendar.getInstance().getTime());
			getCurrentSession().update(memberRole);
		} else {
			memberRole.setGrantedDt(Calendar.getInstance().getTime());
			getCurrentSession().save(memberRole);
		}
	}

    @Transactional
	@Override
	public void delete(MemberRoles memberRole) {
		getCurrentSession().delete(memberRole);
	}

}
