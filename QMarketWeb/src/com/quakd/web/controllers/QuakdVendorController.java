package com.quakd.web.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.quakd.web.dao.CompanyDao;
import com.quakd.web.dao.QuaksDao;
import com.quakd.web.data.User;
import com.quakd.web.model.CompanyInformation;
import com.quakd.web.model.CompanyQponds;
import com.quakd.web.model.MemberQidRedeem;
import com.quakd.web.model.MemberQuaks;
import com.quakd.web.utils.UTMCoord;

@Controller
@Scope("request")
public class QuakdVendorController extends BaseController {
	
	private static Logger log = Logger.getLogger(QuakdVendorController.class);
	
	@Autowired
	protected CompanyDao companyDao;
	
	@Autowired
	protected  QuaksDao quaksDao;

	@RequestMapping(value = "/secure/vendor/qpond", method = RequestMethod.GET)
	public ResponseEntity qpondImage(@RequestParam(value="id") Long id) {	
		byte[] imgBytes = new byte[0];
		String imgType = "text/html";
		log.info("Attempting to load company image " + id);
		
		if(id != null) {
			CompanyQponds qponds = quaksDao.getCompanyQpond(id);	
			if(qponds != null && qponds.getImg().length > 0)
			{
				imgBytes = qponds.getImg();
				imgType = qponds.getImgType();
			}				
		}

	    HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.setContentType(MediaType.parseMediaType(imgType));
	    responseHeaders.setContentLength(imgBytes.length);			
		
	    return new ResponseEntity<byte[]>(imgBytes,
                responseHeaders, HttpStatus.OK);	
	}	

	@RequestMapping(value = "/secure/vendor/image", method = RequestMethod.GET)
	public ResponseEntity companyImage(@RequestParam(value="id") Long id) {	
		byte[] imgBytes = new byte[0];
		String imgType = "text/html";
		log.info("Attempting to load vendor image " + id);

		if(id != null) {
			CompanyInformation info = companyDao.companyById(id);	
			if(info != null && info.getCompanyLogo().length > 0)
			{
				imgBytes = info.getCompanyLogo();
				imgType = info.getCompanyLogoType();
			}				
		}

	    HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.setContentType(MediaType.parseMediaType(imgType));
	    responseHeaders.setContentLength(imgBytes.length);			
		
	    return new ResponseEntity<byte[]>(imgBytes,
                responseHeaders, HttpStatus.OK);	
	}	
	
	@RequestMapping(value = "/secure/search/bbox", method = RequestMethod.GET)
	public ResponseEntity searchByBbox(@RequestParam(value="xmin") Double xmin, @RequestParam(value="ymin")  Double ymin, @RequestParam(value="xmax") Double xmax, @RequestParam(value="ymax")  Double ymax) {
	    HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.setContentType(MediaType.parseMediaType("application/json"));
	    log.info(" xmin = " + xmin  + " ymin = " + ymin + " xmax = " + xmax + " ymax = " + ymax);
	    double[] c1 = UTMCoord.ConvertToUTM(ymin ,xmin);
	    double[] c2 = UTMCoord.ConvertToUTM(ymax, xmax);
	    return new ResponseEntity<String>(companyDao.searchByEnvelopeJSON(c1[0], c1[1], c2[0], c2[1]), responseHeaders, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/secure/search/location", method = RequestMethod.GET)
	public ResponseEntity searchByLocation(@RequestParam(value="x") Double x, @RequestParam(value="y")  Double y, @RequestParam(value="miles") Integer miles) {
	    HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.setContentType(MediaType.parseMediaType("application/json"));
	    log.info(" x = " + x  + " y = " + y + " miles = " + miles);
	    if(x != null && y != null && miles != null)
	    {
	    	double[] converted = UTMCoord.ConvertToUTM(y ,x);
	    	return new ResponseEntity<String>(companyDao.searchByLocationJSON(converted[0], converted[1], miles), responseHeaders, HttpStatus.OK);
	    }
	    else
	    {
	    	return new ResponseEntity<String>("{}", responseHeaders, HttpStatus.OK);
	    }
	}

	@RequestMapping(value = "/secure/search/name", method = RequestMethod.GET)
	public ResponseEntity searchByName(@RequestParam(value="name") String name) {
	    HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.setContentType(MediaType.parseMediaType("application/json"));
	    log.info(" name = " + name);
	    if(name != null)
	    {
	    	name = name.replace("&apos;", "'");
		    log.info(" name = " + name);
	    	return new ResponseEntity<String>(companyDao.searchByNameJSON(name), responseHeaders, HttpStatus.OK);
	    }
	    else
	    {
	    	return new ResponseEntity<String>("{}", responseHeaders, HttpStatus.OK);
	    }
	}	

	@RequestMapping(value = "/secure/vendor/qp", method = RequestMethod.GET)
	public ModelAndView addVendorQp(@RequestParam(value="qid") Long qid) {
	    log.info("Fetching quak with code qid = " + qid);
	    //ask user if they would like to have this qpond
		getRequest().setAttribute("navigation", "qponds_nav");
	    ModelAndView view = new ModelAndView("add_qponds");
		HttpSession session = getRequest().getSession(true);
		User user = (User) session.getAttribute("user_id");		
		
	    if(qid != null) {
	    	MemberQuaks quaks = quaksDao.getQuaks(qid);
	    	Long qpnid = quaks.getCompanyQponds().getQid();
	    	log.info("Found the qpond ID for this quak " + qpnid);
	    	MemberQidRedeem exists = quaksDao.checkRedeems(user.getId(), qid);
	    	if(exists != null) {
	    		List<MemberQidRedeem> list = quaksDao.getRedeem(user.getId());
	    		view.setViewName("view_favorites");
	    		view.addObject("qponds", list);	   
		    	return view;
	    	}	    	
	    	view.addObject("qpond", quaks);	    	
	    }
		return view;	    
	}		

	@RequestMapping(value = "/secure/vendor/add", method = RequestMethod.GET)
	public ModelAndView vendorQPadd(@RequestParam(value="qid") Long qid, @RequestParam(value="resp") String resp) {
	    log.info("Fetching quak with code qid = " + qid);
		getRequest().setAttribute("navigation", "home_nav");
		HttpSession session = getRequest().getSession(true);
		User user = (User) session.getAttribute("user_id");		
	    //ask user if they would like to have this qpond
	    ModelAndView view = new ModelAndView("home");
	    if(qid != null && resp != null)
	    {
	    	
	    	MemberQuaks quaks = quaksDao.getQuaks(qid);
	    	if(quaks != null)
	    	{
			    switch(resp.toLowerCase()) {
				    case "yes":
				    	quaksDao.saveReedem(quaks, user.getId(), quaks.getCompanyQponds().getQid());
						getRequest().setAttribute("navigation", "qponds_nav");
				    	view.setViewName("view_qponds");
				    	break;
				   default:
				    	break;	
			    }
	    	}
	    }
	   
		return view;	    
	}	
	
} 
