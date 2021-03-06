package com.quakd.web.model;

// Generated Jan 6, 2014 8:53:34 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Country generated by hbm2java
 */

@Entity
@Table(name = "country", catalog = "quser")
@Indexed
public class Country implements java.io.Serializable {

	@NotNull
	private Integer id;
	private String name;
	private Set<CompanyLocation> companyLocations = new HashSet<CompanyLocation>(
			0);
	private Set<Member> members = new HashSet<Member>(0);
	private Set<CompanyBilling> companyBillings = new HashSet<CompanyBilling>(0);

	public Country() {
	}

	public Country(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Country(Integer id, String name, Set<CompanyLocation> companyLocations,
			Set<Member> members, Set<CompanyBilling> companyBillings) {
		this.id = id;
		this.name = name;
		this.companyLocations = companyLocations;
		this.members = members;
		this.companyBillings = companyBillings;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "NAME", nullable = false, length = 100)
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "country")
	public Set<CompanyLocation> getCompanyLocations() {
		return this.companyLocations;
	}

	public void setCompanyLocations(Set<CompanyLocation> companyLocations) {
		this.companyLocations = companyLocations;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "country")
	public Set<Member> getMembers() {
		return this.members;
	}

	public void setMembers(Set<Member> members) {
		this.members = members;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "country")
	public Set<CompanyBilling> getCompanyBillings() {
		return this.companyBillings;
	}

	public void setCompanyBillings(Set<CompanyBilling> companyBillings) {
		this.companyBillings = companyBillings;
	}

}
