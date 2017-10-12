package edu.uiowa.AcknowledgementsTagLib.provider;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import edu.uiowa.AcknowledgementsTagLib.person.Person;
import edu.uiowa.AcknowledgementsTagLib.resource.Resource;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;
import edu.uiowa.AcknowledgementsTagLib.Sequence;

@SuppressWarnings("serial")
public class Provider extends AcknowledgementsTagLibTagSupport {

	static Provider currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log = LogFactory.getLog(Provider.class);

	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	int pmcid = 0;
	int personId = 0;
	int resourceId = 0;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {
			Person thePerson = (Person)findAncestorWithClass(this, Person.class);
			if (thePerson!= null)
				parentEntities.addElement(thePerson);
			Resource theResource = (Resource)findAncestorWithClass(this, Resource.class);
			if (theResource!= null)
				parentEntities.addElement(theResource);

			if (thePerson == null) {
			} else {
				personId = thePerson.getID();
			}
			if (theResource == null) {
			} else {
				resourceId = theResource.getID();
			}

			ProviderIterator theProviderIterator = (ProviderIterator)findAncestorWithClass(this, ProviderIterator.class);

			if (theProviderIterator != null) {
				pmcid = theProviderIterator.getPmcid();
				personId = theProviderIterator.getPersonId();
				resourceId = theProviderIterator.getResourceId();
			}

			if (theProviderIterator == null && thePerson == null && theResource == null && pmcid == 0) {
				// no pmcid was provided - the default is to assume that it is a new Provider and to generate a new pmcid
				pmcid = Sequence.generateID();
				insertEntity();
			} else if (theProviderIterator == null && thePerson != null && theResource == null) {
				// an pmcid was provided as an attribute - we need to load a Provider from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select pmcid,person_id,resource_id from pubmed_central_ack_stanford.provider where");
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if (pmcid == 0)
						pmcid = rs.getInt(1);
					if (personId == 0)
						personId = rs.getInt(2);
					if (resourceId == 0)
						resourceId = rs.getInt(3);
					found = true;
				}
				stmt.close();

				if (!found) {
					insertEntity();
				}
			} else if (theProviderIterator == null && thePerson == null && theResource != null) {
				// an pmcid was provided as an attribute - we need to load a Provider from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select pmcid,person_id,resource_id from pubmed_central_ack_stanford.provider where");
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if (pmcid == 0)
						pmcid = rs.getInt(1);
					if (personId == 0)
						personId = rs.getInt(2);
					if (resourceId == 0)
						resourceId = rs.getInt(3);
					found = true;
				}
				stmt.close();

				if (!found) {
					insertEntity();
				}
			} else {
				// an iterator or pmcid was provided as an attribute - we need to load a Provider from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select 1 from pubmed_central_ack_stanford.provider where pmcid = ? and person_id = ? and resource_id = ?");
				stmt.setInt(1,pmcid);
				stmt.setInt(2,personId);
				stmt.setInt(3,resourceId);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					found = true;
				}
				stmt.close();

				if (!found) {
					insertEntity();
				}
			}
		} catch (SQLException e) {
			log.error("JDBC error retrieving pmcid " + pmcid, e);
			throw new JspTagException("Error: JDBC error retrieving pmcid " + pmcid);
		} finally {
			freeConnection();
		}
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;
		try {
			if (commitNeeded) {
				PreparedStatement stmt = getConnection().prepareStatement("update pubmed_central_ack_stanford.provider set where pmcid = ? and person_id = ? and resource_id = ?");
				stmt.setInt(1,pmcid);
				stmt.setInt(2,personId);
				stmt.setInt(3,resourceId);
				stmt.executeUpdate();
				stmt.close();
			}
		} catch (SQLException e) {
			log.error("Error: IOException while writing to the user", e);
			throw new JspTagException("Error: IOException while writing to the user");
		} finally {
			clearServiceState();
			freeConnection();
		}
		return super.doEndTag();
	}

	public void insertEntity() throws JspException {
		try {
			if (pmcid == 0) {
				pmcid = Sequence.generateID();
				log.debug("generating new Provider " + pmcid);
			}

			if (personId == 0) {
				personId = Sequence.generateID();
				log.debug("generating new Provider " + personId);
			}

			if (resourceId == 0) {
				resourceId = Sequence.generateID();
				log.debug("generating new Provider " + resourceId);
			}

			PreparedStatement stmt = getConnection().prepareStatement("insert into pubmed_central_ack_stanford.provider(pmcid,person_id,resource_id) values (?,?,?)");
			stmt.setInt(1,pmcid);
			stmt.setInt(2,personId);
			stmt.setInt(3,resourceId);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			log.error("Error: IOException while writing to the user", e);
			throw new JspTagException("Error: IOException while writing to the user");
		} finally {
			freeConnection();
		}
	}

	public int getPmcid () {
		return pmcid;
	}

	public void setPmcid (int pmcid) {
		this.pmcid = pmcid;
	}

	public int getActualPmcid () {
		return pmcid;
	}

	public int getPersonId () {
		return personId;
	}

	public void setPersonId (int personId) {
		this.personId = personId;
	}

	public int getActualPersonId () {
		return personId;
	}

	public int getResourceId () {
		return resourceId;
	}

	public void setResourceId (int resourceId) {
		this.resourceId = resourceId;
	}

	public int getActualResourceId () {
		return resourceId;
	}

	public static Integer pmcidValue() throws JspException {
		try {
			return currentInstance.getPmcid();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function pmcidValue()");
		}
	}

	public static Integer personIdValue() throws JspException {
		try {
			return currentInstance.getPersonId();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function personIdValue()");
		}
	}

	public static Integer resourceIdValue() throws JspException {
		try {
			return currentInstance.getResourceId();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function resourceIdValue()");
		}
	}

	private void clearServiceState () {
		pmcid = 0;
		personId = 0;
		resourceId = 0;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	}

}
