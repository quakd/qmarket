package com.quakd.web.spring;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;


@Component
public class EmailScheduler {
	
	private static Logger log = Logger.getLogger(EmailScheduler.class);	
	
    @Resource
    ThreadPoolTaskScheduler threadPoolTaskScheduler;
        
    @Resource
    EmailerThread emailer;
        
    @Value("${email_service.polltime}")
    String servicePollTime;
        
    /**
     * Schedule the run of the worker
    */
    public void schedule() {
    	Long pollTime = 0L;
    	try {
    		pollTime = Long.parseLong(servicePollTime);
    	} catch (Exception ex) {
    		pollTime = 30000L;
    	}
    	log.info("Starting up the email scheduler with the parameter = " + pollTime);
        threadPoolTaskScheduler.scheduleWithFixedDelay(emailer, pollTime);
    }
}
