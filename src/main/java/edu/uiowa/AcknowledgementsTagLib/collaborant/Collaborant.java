package edu.uiowa.AcknowledgementsTagLib.collaborant;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import edu.uiowa.AcknowledgementsTagLib.organization.Organization;
import edu.uiowa.AcknowledgementsTagLib.collaboration.Collaboration;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;
import edu.uiowa.AcknowledgementsTagLib.Sequence;

@SuppressWarnings("serial")
public class Collaborant extends AcknowledgementsTagLibTagSupport {

	static Collaborant currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log = LogFactory.getLog(Collaborant.class);

	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	int pmcid = 0;
	int organizationId = 0;
	int collaborationId = 0;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {
			Organization theOrganization = (Organization)findAncestorWithClass(this, Organization.class);
			if (theOrganization!= null)
				parentEntities.addElement(theOrganization);
			Collaboration theCollaboration = (Collaboration)findAncestorWithClass(this, Collaboration.class);
			if (theCollaboration!= null)
				parentEntities.addElement(theCollaboration);

			if (theOrganization == null) {
			} else {
				organizationId = theOrganization.getID();
			}
			if (theCollaboration == null) {
			} else {
				collaborationId = theCollaboration.getID();
			}

			CollaborantIterator theCollaborantIterator = (CollaborantIterator)findAncestorWithClass(this, CollaborantIterator.class);

			if (theCollaborantIterator != null) {
				pmcid = theCollaborantIterator.getPmcid();
				organizationId = theCollaborantIterator.getOrganizationId();
				collaborationId = theCollaborantIterator.getCollaborationId();
			}

			if (theCollaborantIterator == null && theOrganization == null && theCollaboration == null && pmcid == 0) {
				// no pmcid was provided - the default is to assume that it is a new Collaborant and to generate a new pmcid
				pmcid = Sequence.generateID();
				insertEntity();
			} else if (theCollaborantIterator == null && theOrganization != null && theCollaboration == null) {
				// an pmcid was provided as an attribute - we need to load a Collaborant from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select pmcid,organization_id,collaboration_id from pubmed_central_ack_stanford.collaborant where");
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if (pmcid == 0)
						pmcid = rs.getInt(1);
					if (organizationId == 0)
						organizationId = rs.getInt(2);
					if (collaborationId == 0)
						collaborationId = rs.getInt(3);
					found = true;
				}
				stmt.close();

				if (!found) {
					insertEntity();
				}
			} else if (theCollaborantIterator == null && theOrganization == null && theCollaboration != null) {
				// an pmcid was provided as an attribute - we need to load a Collaborant from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select pmcid,organization_id,collaboration_id from pubmed_central_ack_stanford.collaborant where");
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if (pmcid == 0)
						pmcid = rs.getInt(1);
					if (organizationId == 0)
						organizationId = rs.getInt(2);
					if (collaborationId == 0)
						collaborationId = rs.getInt(3);
					found = true;
				}
				stmt.close();

				if (!found) {
					insertEntity();
				}
			} else {
				// an iterator or pmcid was provided as an attribute - we need to load a Collaborant from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select 1 from pubmed_central_ack_stanford.collaborant where pmcid = ? and organization_id = ? and collaboration_id = ?");
				stmt.setInt(1,pmcid);
				stmt.setInt(2,organizationId);
				stmt.setInt(3,collaborationId);
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
				PreparedStatement stmt = getConnection().prepareStatement("update pubmed_central_ack_stanford.collaborant set where pmcid = ? and organization_id = ? and collaboration_id = ?");
				stmt.setInt(1,pmcid);
				stmt.setInt(2,organizationId);
				stmt.setInt(3,collaborationId);
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
				log.debug("generating new Collaborant " + pmcid);
			}

			if (organizationId == 0) {
				organizationId = Sequence.generateID();
				log.debug("generating new Collaborant " + organizationId);
			}

			if (collaborationId == 0) {
				collaborationId = Sequence.generateID();
				log.debug("generating new Collaborant " + collaborationId);
			}

			PreparedStatement stmt = getConnection().prepareStatement("insert into pubmed_central_ack_stanford.collaborant(pmcid,organization_id,collaboration_id) values (?,?,?)");
			stmt.setInt(1,pmcid);
			stmt.setInt(2,organizationId);
			stmt.setInt(3,collaborationId);
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

	public int getOrganizationId () {
		return organizationId;
	}

	public void setOrganizationId (int organizationId) {
		this.organizationId = organizationId;
	}

	public int getActualOrganizationId () {
		return organizationId;
	}

	public int getCollaborationId () {
		return collaborationId;
	}

	public void setCollaborationId (int collaborationId) {
		this.collaborationId = collaborationId;
	}

	public int getActualCollaborationId () {
		return collaborationId;
	}

	public static Integer pmcidValue() throws JspException {
		try {
			return currentInstance.getPmcid();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function pmcidValue()");
		}
	}

	public static Integer organizationIdValue() throws JspException {
		try {
			return currentInstance.getOrganizationId();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function organizationIdValue()");
		}
	}

	public static Integer collaborationIdValue() throws JspException {
		try {
			return currentInstance.getCollaborationId();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function collaborationIdValue()");
		}
	}

	private void clearServiceState () {
		pmcid = 0;
		organizationId = 0;
		collaborationId = 0;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	}

}
