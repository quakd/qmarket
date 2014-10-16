package com.quakd.web.spring;

import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.quakd.web.dao.EmailQueueDao;
import com.quakd.web.data.EmailBean;
import com.quakd.web.model.EmailQueue;

@Service("mailService")
public class EmailService {

	@Autowired
	private VelocityEngine velocityEngine;

	@Autowired
	private EmailQueueDao emailQueue;

	public void generateRegisterEmail(EmailBean bean) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("bean", bean);
		String text = VelocityEngineUtils.mergeTemplateIntoString(
				velocityEngine,
				"com/quakd/web/template/email/registration.html", "UTF-8",
				model);
		bean.setBodyText(text);
		bean.setSubject("Welcome to Quakd.com!");
		bean.setFrom("no-reply@quakd.com");
		EmailQueue queue = new EmailQueue(bean);
		emailQueue.saveEmail(queue);

	}

}
