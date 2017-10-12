package edu.uiowa.AcknowledgementsTagLib.organizationMention;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import edu.uiowa.AcknowledgementsTagLib.organization.Organization;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;
import edu.uiowa.AcknowledgementsTagLib.Sequence;

@SuppressWarnings("serial")
public class OrganizationMention extends AcknowledgementsTagLibTagSupport {

	static OrganizationMention currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log = LogFactory.getLog(OrganizationMention.class);

	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	int organizationId = 0;
	int pmcid = 0;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {
			Organization theOrganization = (Organization)findAncestorWithClass(this, Organization.class);
			if (theOrganization!= null)
				parentEntities.addElement(theOrganization);

			if (theOrganization == null) {
			} else {
				organizationId = theOrganization.getID();
			}

			OrganizationMentionIterator theOrganizationMentionIterator = (OrganizationMentionIterator)findAncestorWithClass(this, OrganizationMentionIterator.class);

			if (theOrganizationMentionIterator != null) {
				organizationId = theOrganizationMentionIterator.getOrganizationId();
			}

			if (theOrganizationMentionIterator == null && theOrganization == null && organizationId == 0) {
				// no organizationId was provided - the default is to assume that it is a new OrganizationMention and to generate a new organizationId
				organizationId = Sequence.generateID();
				insertEntity();
			} else {
				// an iterator or organizationId was provided as an attribute - we need to load a OrganizationMention from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select pmcid from pubmed_central_ack_stanford.organization_mention where organization_id = ?");
				stmt.setInt(1,organizationId);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if (pmcid == 0)
						pmcid = rs.getInt(1);
					found = true;
				}
				stmt.close();

				if (!found) {
					insertEntity();
				}
			}
		} catch (SQLException e) {
			log.error("JDBC error retrieving organizationId " + organizationId, e);
			throw new JspTagException("Error: JDBC error retrieving organizationId " + organizationId);
		} finally {
			freeConnection();
		}
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;
		try {
			if (commitNeeded) {
				PreparedStatement stmt = getConnection().prepareStatement("update pubmed_central_ack_stanford.organization_mention set pmcid = ? where organization_id = ?");
				stmt.setInt(1,pmcid);
				stmt.setInt(2,organizationId);
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
			if (organizationId == 0) {
				organizationId = Sequence.generateID();
				log.debug("generating new OrganizationMention " + organizationId);
			}

			PreparedStatement stmt = getConnection().prepareStatement("insert into pubmed_central_ack_stanford.organization_mention(organization_id,pmcid) values (?,?)");
			stmt.setInt(1,organizationId);
			stmt.setInt(2,pmcid);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			log.error("Error: IOException while writing to the user", e);
			throw new JspTagException("Error: IOException while writing to the user");
		} finally {
			freeConnection();
		}
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

	public int getPmcid () {
		return pmcid;
	}

	public void setPmcid (int pmcid) {
		this.pmcid = pmcid;
		commitNeeded = true;
	}

	public int getActualPmcid () {
		return pmcid;
	}

	public static Integer organizationIdValue() throws JspException {
		try {
			return currentInstance.getOrganizationId();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function organizationIdValue()");
		}
	}

	public static Integer pmcidValue() throws JspException {
		try {
			return currentInstance.getPmcid();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function pmcidValue()");
		}
	}

	private void clearServiceState () {
		organizationId = 0;
		pmcid = 0;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	}

}
