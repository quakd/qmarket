package com.quakd.web.controllers;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

@ControllerAdvice
public class MControllerAdvice {

	private static Logger log = Logger.getLogger(MControllerAdvice.class);

	@ExceptionHandler({NoSuchRequestHandlingMethodException.class})
	public ModelAndView  handleException(NoSuchRequestHandlingMethodException ex) {
		log.error("Error happened", ex);
	    ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("error");
		return modelAndView;
	}

}
