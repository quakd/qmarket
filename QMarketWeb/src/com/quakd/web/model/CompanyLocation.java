package com.quakd.web.model;

// Generated Jan 6, 2014 8:21:11 PM by Hibernate Tools 3.4.0.CR1

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.apache.commons.io.filefilter.FalseFileFilter;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

import com.vividsolutions.jts.geom.Point;

/**
 * CompanyLocation generated by hbm2java
 */
@Entity
@Table(name = "company_location", catalog = "quser", uniqueConstraints = @UniqueConstraint(columnNames = "COMPANY_CD"))
@Indexed
public class CompanyLocation implements java.io.Serializable {

	private Long locId;
	private CompanyInformation companyInformation;
	private Country country;
	private UsStates usStates;
	private double lat;
	private double lon;
	private String address;
	private String city;
	private String zip;
	private Date modifiedDt;
	private Set<MemberRoles> memberRoleses = new HashSet<MemberRoles>(0);
	private Set<MemberFavorites> memberFavoriteses = new HashSet<MemberFavorites>(
			0);
	private Set<MemberQuaks> memberQuakses = new HashSet<MemberQuaks>(0);	

	private Point geoPt;	

	public CompanyLocation() {
	}

	public CompanyLocation(CompanyInformation companyInformation,
			Country country, double lat, double lon, String address) {
		this.companyInformation = companyInformation;
		this.country = country;
		this.lat = lat;
		this.lon = lon;
		this.address = address;
	}

	public CompanyLocation(CompanyInformation companyInformation,
			Country country, UsStates usStates, double lat, double lon,
			String address, String city, String zip,
			Date modifiedDt, Set<MemberRoles> memberRoleses) {
		this.companyInformation = companyInformation;
		this.country = country;
		this.usStates = usStates;
		this.lat = lat;
		this.lon = lon;
		this.address = address;
		this.city = city;
		this.zip = zip;
		this.modifiedDt = modifiedDt;
		this.memberRoleses = memberRoleses;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "LOC_ID", unique = true, nullable = false)
	public Long getLocId() {
		return this.locId;
	}

	public void setLocId(Long locId) {
		this.locId = locId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_CD", unique = false, nullable = false)	
	@IndexedEmbedded	
	public CompanyInformation getCompanyInformation() {
		return this.companyInformation;
	}

	public void setCompanyInformation(CompanyInformation companyInformation) {
		this.companyInformation = companyInformation;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_CD", nullable = false)
	@IndexedEmbedded
	public Country getCountry() {
		return this.country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STATE_CD")
	@IndexedEmbedded
	public UsStates getUsStates() {
		return this.usStates;
	}

	public void setUsStates(UsStates usStates) {
		this.usStates = usStates;
	}

	@Column(name = "LAT", nullable = false, precision = 22, scale = 0)
	public double getLat() {
		return this.lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	@Column(name = "LON", nullable = false, precision = 22, scale = 0)
	public double getLon() {
		return this.lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	@Column(name = "ADDRESS", nullable = false, length = 200)
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "CITY", length = 100)
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "ZIP", length = 10)
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFIED_DT", length = 19)
	public Date getModifiedDt() {
		return this.modifiedDt;
	}

	public void setModifiedDt(Date modifiedDt) {
		this.modifiedDt = modifiedDt;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "companyLocation", cascade = CascadeType.ALL)
	public Set<MemberRoles> getMemberRoleses() {
		return this.memberRoleses;
	}

	public void setMemberRoleses(Set<MemberRoles> memberRoleses) {
		this.memberRoleses = memberRoleses;
	}

	@Column(name = "GEO_PT")
	@Type(type="org.hibernate.spatial.GeometryType")
	public Point getGeoPt() {
		return geoPt;
	}

	public void setGeoPt(Point geoPt) {
		this.geoPt = geoPt;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "companyLocation")
	public Set<MemberFavorites> getMemberFavoriteses() {
		return this.memberFavoriteses;
	}

	public void setMemberFavoriteses(Set<MemberFavorites> memberFavoriteses) {
		this.memberFavoriteses = memberFavoriteses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "companyLocation")
	public Set<MemberQuaks> getMemberQuakses() {
		return this.memberQuakses;
	}

	public void setMemberQuakses(Set<MemberQuaks> memberQuakses) {
		this.memberQuakses = memberQuakses;
	}	

}
