package com.quakd.web.spring;

import java.util.Calendar;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.quakd.web.dao.EmailQueueDao;
import com.quakd.web.model.EmailQueue;

@Component
public class EmailerThread implements Runnable {

	private static Logger log = Logger.getLogger(EmailerThread.class);	
	
	@Autowired
    private JavaMailSender mailSender;
	
	@Autowired
	private EmailQueueDao emailQueue;
	
	@Override
	public void run() {
		log.debug("Running the email thread, check for emails to send...");
		List<EmailQueue> processList = null;
		try {
			processList = emailQueue.findAndProcess();
			
			if(processList != null) {
				MimeMessage message = mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(message);
							
				for(EmailQueue queue : processList) {
					helper.setFrom(queue.getEmailFrom());
					helper.setTo(queue.getEmailTo());
					helper.setSubject(queue.getEmailSubject());					
			        helper.setText(new String(queue.getEmailTxt(), "UTF-8"), true);
					mailSender.send(message);
					queue.setEmailSentDt(Calendar.getInstance().getTime());
					emailQueue.saveEmail(queue);
				}
				
			}
		} catch (Exception e) {
			log.debug("Unable to send list of provided emails, please see exception for details.", e);
		} finally {
			if(processList != null) {
				for(EmailQueue queue : processList) {
					if(queue.getEmailSentDt() == null) {
						queue.setProcessed("N");
						emailQueue.saveEmail(queue);
					}
				}
			}
		}
		
	}

}
