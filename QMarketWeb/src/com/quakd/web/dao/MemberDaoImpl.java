package com.quakd.web.dao;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.FinalizablePhantomReference;
import com.quakd.web.model.Country;
import com.quakd.web.model.EmailQueue;
import com.quakd.web.model.Member;
import com.quakd.web.model.MemberRoles;
import com.quakd.web.model.UsStates;
import com.quakd.web.utils.FileUtil;

@Repository
public class MemberDaoImpl implements MemberDao {
	
	private static Logger log = Logger.getLogger(MemberDaoImpl.class);	

    @Autowired
    SessionFactory sessionFactory;
 
    protected Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }
	
	@Autowired
	protected PasswordEncoder passwordEncoder;

	
	@Transactional
	public void setProfileImage(final Long id, final byte[] bytes, final String type) {
		Member stored = findMember(id);
		if(stored != null) {
			//junk data
			stored.setPassText("Test1Junk#");
			stored.setRepassText("Test1Junk#");			
			stored.setPhone1("865-2998999");
			
			stored.setMemberImgType(type);
			stored.setMemberImg(bytes);
			
			stored.setModifiedDt(Calendar.getInstance().getTime());	
			getCurrentSession().update(stored);
		}
	}	
	
	@Transactional
	public void updatePassword(final Long id, final String password) {
		Member stored = findMember(id);
		if(stored != null) {
			stored.setPassText(password);
			stored.setRepassText(password);
			stored.setPhone1("865-2998999");
			stored.setPassword(passwordEncoder.encode(password));
			stored.setModifiedDt(Calendar.getInstance().getTime());	
			getCurrentSession().update(stored);
		}
	}

	@Transactional
	public void saveMember(final Member member) {
		if(member.getMemberId() == null) {
			member.setCreatedDt(Calendar.getInstance().getTime());				
			String subscriber = member.getSubscriber();	
			MemberRoles memberRole = new MemberRoles();
			if("Y".equals(subscriber)) {
				memberRole.setRoleName("subscriber");
			} else {
				member.setSubscriber("N");
				memberRole.setRoleName("member");
			}
			
			FileUtil file = new FileUtil();
			member.setMemberImg(file.GetImage());
			member.setMemberImgType(FileUtil.PNG_TYPE);			
			
			member.setPassword(passwordEncoder.encode(member.getPassText()));				
			member.setActive("Y");		
			if(!"Y".equals(member.getEmailVerified())) {
				member.setEmailVerified("N");
			}
			if(member.getPhone1() != null) {
				member.setContactNumber(member.getPhone1().replace("-", ""));
			}
			getCurrentSession().save(member);
			
			memberRole.setMember(member);	
			memberRole.setGrantedDt(Calendar.getInstance().getTime());
			
			getCurrentSession().save(memberRole);
					
		} else {		
			Member stored = findMember(member.getMemberId());
			stored.setPassText("Test1Junk#");
			stored.setRepassText("Test1Junk#");		
			if(member.getPhone1() != null) {
				stored.setContactNumber(member.getPhone1().replace("-", ""));	
			}
			stored.setFirstName(member.getFirstName());
			stored.setLastName(member.getLastName());
			stored.setAddress(member.getAddress());
			stored.setCity(member.getCity());
			stored.setZip(member.getZip());
			stored.setCountry(member.getCountry());
			
			member.setModifiedDt(Calendar.getInstance().getTime());	
			getCurrentSession().update(stored);
		}
	}
	
    @Transactional
	public void deleteMember(final Member member) {
		getCurrentSession().delete(member);
	}

    @Transactional
	public Member findMember(final Long id) {
		Query query = getCurrentSession().createQuery("from Member where memberId = :id");
		query.setParameter("id", id);
		List<Member> list = query.list();		
		Member member = (list != null && list.size() > 0 ? list.get(0) : null);
		if(member != null) {
			String number = member.getContactNumber();
			member.setPhone1(number.substring(0, 3) + "-" + number.substring(3));
		}
		
		return member;
	}
    
    @Transactional
	public Member findMember(final String member) {
		Query query = getCurrentSession().createQuery("from Member where username = :member");
		query.setParameter("member", member);
		List<Member> list = query.list();			
		return list != null && list.size() > 0 ? list.get(0) : null;
	}
    
    @Transactional
	public boolean validPassword(final String username, final String password) {
		Member member = findMember(username);
		if(member != null) {	
			return passwordEncoder.matches(passwordEncoder.encode(password), member.getPassword().toString());
			/*
			byte[] pass = CipherUtil.decrypt(member.getPassword());
			if(pass != null) {
				String upassword = null;
				try {
					upassword = new String(pass, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					log.error("Failed to convert text to UTF-8.", e);
				}
				
				if(password.equals(upassword)) {
					return true;
				}
			}
			*/
		}
		return false;
	}

	@Override
	public void createSocialAccount(String username, String providerId,
			String firstName, String lastName) {
		Member member = new Member();
		
		member.setAddress("not provided");
		member.setCity("not provided");
		Country country = new Country();
		country.setId(1);			
		member.setCountry(country);
		String first = firstName != null ?  firstName : "not provided";
		String last = lastName != null ?  lastName : "not provided";
		member.setFirstName(first);
		member.setLastName(last);

		member.setActive("Y");
		member.setEmailVerified("Y");
		member.setUsername(username);
		
		member.setPassText("Not1RealPassword#");
		member.setRepassText("Not1RealPassword#");
		member.setZip("00000");
		UsStates states = new UsStates();
		states.setId(1);
		member.setUsStates(states);
		member.setPhone1("000-0000000");			
		member.setProvider(providerId);
		
		saveMember(member);
		
	}

    
}
