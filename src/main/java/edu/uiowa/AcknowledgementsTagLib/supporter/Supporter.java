package edu.uiowa.AcknowledgementsTagLib.supporter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import edu.uiowa.AcknowledgementsTagLib.support.Support;
import edu.uiowa.AcknowledgementsTagLib.organization.Organization;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;
import edu.uiowa.AcknowledgementsTagLib.Sequence;

@SuppressWarnings("serial")
public class Supporter extends AcknowledgementsTagLibTagSupport {

	static Supporter currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log = LogFactory.getLog(Supporter.class);

	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	int pmcid = 0;
	int supportId = 0;
	int organizationId = 0;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {
			Support theSupport = (Support)findAncestorWithClass(this, Support.class);
			if (theSupport!= null)
				parentEntities.addElement(theSupport);
			Organization theOrganization = (Organization)findAncestorWithClass(this, Organization.class);
			if (theOrganization!= null)
				parentEntities.addElement(theOrganization);

			if (theSupport == null) {
			} else {
				supportId = theSupport.getID();
			}
			if (theOrganization == null) {
			} else {
				organizationId = theOrganization.getID();
			}

			SupporterIterator theSupporterIterator = (SupporterIterator)findAncestorWithClass(this, SupporterIterator.class);

			if (theSupporterIterator != null) {
				pmcid = theSupporterIterator.getPmcid();
				supportId = theSupporterIterator.getSupportId();
				organizationId = theSupporterIterator.getOrganizationId();
			}

			if (theSupporterIterator == null && theSupport == null && theOrganization == null && pmcid == 0) {
				// no pmcid was provided - the default is to assume that it is a new Supporter and to generate a new pmcid
				pmcid = Sequence.generateID();
				insertEntity();
			} else if (theSupporterIterator == null && theSupport != null && theOrganization == null) {
				// an pmcid was provided as an attribute - we need to load a Supporter from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select pmcid,support_id,organization_id from pubmed_central_ack_stanford.supporter where");
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if (pmcid == 0)
						pmcid = rs.getInt(1);
					if (supportId == 0)
						supportId = rs.getInt(2);
					if (organizationId == 0)
						organizationId = rs.getInt(3);
					found = true;
				}
				stmt.close();

				if (!found) {
					insertEntity();
				}
			} else if (theSupporterIterator == null && theSupport == null && theOrganization != null) {
				// an pmcid was provided as an attribute - we need to load a Supporter from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select pmcid,support_id,organization_id from pubmed_central_ack_stanford.supporter where");
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if (pmcid == 0)
						pmcid = rs.getInt(1);
					if (supportId == 0)
						supportId = rs.getInt(2);
					if (organizationId == 0)
						organizationId = rs.getInt(3);
					found = true;
				}
				stmt.close();

				if (!found) {
					insertEntity();
				}
			} else {
				// an iterator or pmcid was provided as an attribute - we need to load a Supporter from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select 1 from pubmed_central_ack_stanford.supporter where pmcid = ? and support_id = ? and organization_id = ?");
				stmt.setInt(1,pmcid);
				stmt.setInt(2,supportId);
				stmt.setInt(3,organizationId);
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
				PreparedStatement stmt = getConnection().prepareStatement("update pubmed_central_ack_stanford.supporter set where pmcid = ? and support_id = ? and organization_id = ?");
				stmt.setInt(1,pmcid);
				stmt.setInt(2,supportId);
				stmt.setInt(3,organizationId);
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
				log.debug("generating new Supporter " + pmcid);
			}

			if (supportId == 0) {
				supportId = Sequence.generateID();
				log.debug("generating new Supporter " + supportId);
			}

			if (organizationId == 0) {
				organizationId = Sequence.generateID();
				log.debug("generating new Supporter " + organizationId);
			}

			PreparedStatement stmt = getConnection().prepareStatement("insert into pubmed_central_ack_stanford.supporter(pmcid,support_id,organization_id) values (?,?,?)");
			stmt.setInt(1,pmcid);
			stmt.setInt(2,supportId);
			stmt.setInt(3,organizationId);
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

	public int getSupportId () {
		return supportId;
	}

	public void setSupportId (int supportId) {
		this.supportId = supportId;
	}

	public int getActualSupportId () {
		return supportId;
	}

	public int getOrganizationId () {
		return organizationId;
	}

	public void setOrganizationId (int organizationId) {
		this.organizationId = organizationId;
	}

	public int getActualOrganizationId () {
		return organizationId;
	}

	public static Integer pmcidValue() throws JspException {
		try {
			return currentInstance.getPmcid();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function pmcidValue()");
		}
	}

	public static Integer supportIdValue() throws JspException {
		try {
			return currentInstance.getSupportId();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function supportIdValue()");
		}
	}

	public static Integer organizationIdValue() throws JspException {
		try {
			return currentInstance.getOrganizationId();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function organizationIdValue()");
		}
	}

	private void clearServiceState () {
		pmcid = 0;
		supportId = 0;
		organizationId = 0;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	}

}
