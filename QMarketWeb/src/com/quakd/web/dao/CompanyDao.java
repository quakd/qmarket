package com.quakd.web.dao;

import java.util.List;

import com.quakd.web.model.CompanyInformation;
import com.quakd.web.model.CompanyLocation;

public interface CompanyDao {

	public List<CompanyLocation> searchByName(String companyName);

	public String searchByNameJSON(String companyName);
	
	public CompanyLocation companyLocationById(Long id);
	
	public CompanyInformation companyById(Long id);

	public String searchByLocationJSON(double centerx, double centery, int miles);

	public List<CompanyLocation> searchByLocation(double centerx,
			double centery, int miles);

	public String searchByEnvelopeJSON(double xmin, double ymin,
			double xmax, double ymax);

	public List<CompanyLocation> searchByEnvelope(double xmin, double ymin,
			double xmax, double ymax);
}
