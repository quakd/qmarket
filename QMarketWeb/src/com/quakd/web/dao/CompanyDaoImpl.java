package com.quakd.web.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.SearchFactory;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.spatial.jts.EnvelopeAdapter;
import org.infinispan.commands.remote.recovery.TxCompletionNotificationCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.quakd.web.model.CompanyInformation;
import com.quakd.web.model.CompanyLocation;
import com.quakd.web.model.Member;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.util.GeometricShapeFactory;

import flexjson.JSONSerializer;

@Repository
public class CompanyDaoImpl implements CompanyDao {

	@Autowired
	SessionFactory sessionFactory;

	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Transactional	
	@Override
	public CompanyLocation companyLocationById(Long id) {
		Query query = getCurrentSession().createQuery("from CompanyLocation where locId = :id");
		query.setParameter("id", id);
		List<CompanyLocation> list = query.list();		
		CompanyLocation loc = (list != null && list.size() > 0 ? list.get(0) : null);
		return loc;		
	}

	
	@Transactional	
	@Override
	public CompanyInformation companyById(Long id) {
		Query query = getCurrentSession().createQuery("from CompanyInformation where companyCd = :cd");
		query.setParameter("cd", id);
		List<CompanyInformation> list = query.list();		
		CompanyInformation info = (list != null && list.size() > 0 ? list.get(0) : null);
		return info;		
	}



	@Transactional
	@Override
	public String searchByNameJSON(String companyName) {
		List<CompanyLocation> locations = searchByName(companyName);
		if(locations != null) {
		    String json = new JSONSerializer().exclude("*.class").exclude("geoPt").serialize(locations);
		    return json;
		}		
		return "";
	}	

	@Transactional
	@Override
	public List<CompanyLocation> searchByName(String companyName) {
		FullTextSession fullTextSession = Search.getFullTextSession(getCurrentSession());
		
		QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(CompanyLocation.class).get();
		
		org.apache.lucene.search.Query createQuery = qb.keyword().onFields("companyInformation.companyName", "city", "zip", "usStates.name", "usStates.code").matching(companyName).createQuery();
		
		FullTextQuery hibQuery = fullTextSession.createFullTextQuery(createQuery, CompanyLocation.class);
		List<CompanyLocation> locations = hibQuery.list();
		
		return locations;
	}
	
	@Transactional
	@Override
	public String searchByLocationJSON(double centerx,
			double centery, int miles) {
		List<CompanyLocation> locations = searchByLocation(centerx, centery, miles);
		if(locations != null) {
		    String json = new JSONSerializer().exclude("*.class").exclude("geoPt").serialize(locations);
		    return json;
		}
		return "";
	}	
	
	@Transactional
	@Override
	public List<CompanyLocation> searchByLocation(double centerx,
			double centery, int miles) {
		Geometry filter = createCircle(centerx, centery, miles);
		Session session = getCurrentSession();
		Query query = session
				.createQuery("from CompanyLocation where within(geoPt, :filter) = true");
		query.setParameter("filter", filter);
		List<CompanyLocation> list = query.list();
		return list;
	}
	
	
	@Transactional	
	@Override
	public String searchByEnvelopeJSON(double xmin, double ymin, double xmax,
			double ymax) {
		List<CompanyLocation> locations = searchByEnvelope(xmin, ymin, xmax, ymax);
		if(locations != null) {
		    String json = new JSONSerializer().exclude("*.class").exclude("geoPt").serialize(locations);
		    return json;
		}
		return "";
	}


	@Transactional
	@Override
	public List<CompanyLocation> searchByEnvelope(double xmin, double ymin,
			double xmax, double ymax) {
		Geometry filter = createEnvelope(xmin, ymin, xmax, ymax);
		Session session = getCurrentSession();
		Query query = session
				.createQuery("from CompanyLocation where within(geoPt, :filter) = true");
		query.setParameter("filter", filter);
		List<CompanyLocation> list = query.list();
		return list;
	}

	private Polygon createEnvelope(double xmin, double ymin, double xmax,
			double ymax) {
		Envelope envelope = new Envelope(xmin, xmax, ymin, ymax);
		return EnvelopeAdapter.toPolygon(envelope, 3857);
	}

	private Geometry createCircle(double x, double y, int miles) {
		Point point = createPoint(x, y);
		return point.buffer(miles * 1609.344);
	}

	private Point createPoint(double x, double y) {
		GeometryFactory geomFact = new GeometryFactory(new PrecisionModel(),
				3857);
		Coordinate coordinate = new Coordinate(x, y);
		Point aPoint = geomFact.createPoint(coordinate);
		return aPoint;
	}

}
