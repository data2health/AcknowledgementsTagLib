package edu.uiowa.AcknowledgementsTagLib.funder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import edu.uiowa.AcknowledgementsTagLib.organization.Organization;
import edu.uiowa.AcknowledgementsTagLib.award.Award;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;
import edu.uiowa.AcknowledgementsTagLib.Sequence;

@SuppressWarnings("serial")
public class Funder extends AcknowledgementsTagLibTagSupport {

	static Funder currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log = LogFactory.getLog(Funder.class);

	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	int pmcid = 0;
	int organizationId = 0;
	int awardId = 0;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {
			Organization theOrganization = (Organization)findAncestorWithClass(this, Organization.class);
			if (theOrganization!= null)
				parentEntities.addElement(theOrganization);
			Award theAward = (Award)findAncestorWithClass(this, Award.class);
			if (theAward!= null)
				parentEntities.addElement(theAward);

			if (theOrganization == null) {
			} else {
				organizationId = theOrganization.getID();
			}
			if (theAward == null) {
			} else {
				awardId = theAward.getID();
			}

			FunderIterator theFunderIterator = (FunderIterator)findAncestorWithClass(this, FunderIterator.class);

			if (theFunderIterator != null) {
				pmcid = theFunderIterator.getPmcid();
				organizationId = theFunderIterator.getOrganizationId();
				awardId = theFunderIterator.getAwardId();
			}

			if (theFunderIterator == null && theOrganization == null && theAward == null && pmcid == 0) {
				// no pmcid was provided - the default is to assume that it is a new Funder and to generate a new pmcid
				pmcid = Sequence.generateID();
				insertEntity();
			} else if (theFunderIterator == null && theOrganization != null && theAward == null) {
				// an pmcid was provided as an attribute - we need to load a Funder from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select pmcid,organization_id,award_id from pubmed_central_ack_stanford.funder where");
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if (pmcid == 0)
						pmcid = rs.getInt(1);
					if (organizationId == 0)
						organizationId = rs.getInt(2);
					if (awardId == 0)
						awardId = rs.getInt(3);
					found = true;
				}
				stmt.close();

				if (!found) {
					insertEntity();
				}
			} else if (theFunderIterator == null && theOrganization == null && theAward != null) {
				// an pmcid was provided as an attribute - we need to load a Funder from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select pmcid,organization_id,award_id from pubmed_central_ack_stanford.funder where");
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if (pmcid == 0)
						pmcid = rs.getInt(1);
					if (organizationId == 0)
						organizationId = rs.getInt(2);
					if (awardId == 0)
						awardId = rs.getInt(3);
					found = true;
				}
				stmt.close();

				if (!found) {
					insertEntity();
				}
			} else {
				// an iterator or pmcid was provided as an attribute - we need to load a Funder from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select 1 from pubmed_central_ack_stanford.funder where pmcid = ? and organization_id = ? and award_id = ?");
				stmt.setInt(1,pmcid);
				stmt.setInt(2,organizationId);
				stmt.setInt(3,awardId);
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
				PreparedStatement stmt = getConnection().prepareStatement("update pubmed_central_ack_stanford.funder set where pmcid = ? and organization_id = ? and award_id = ?");
				stmt.setInt(1,pmcid);
				stmt.setInt(2,organizationId);
				stmt.setInt(3,awardId);
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
				log.debug("generating new Funder " + pmcid);
			}

			if (organizationId == 0) {
				organizationId = Sequence.generateID();
				log.debug("generating new Funder " + organizationId);
			}

			if (awardId == 0) {
				awardId = Sequence.generateID();
				log.debug("generating new Funder " + awardId);
			}

			PreparedStatement stmt = getConnection().prepareStatement("insert into pubmed_central_ack_stanford.funder(pmcid,organization_id,award_id) values (?,?,?)");
			stmt.setInt(1,pmcid);
			stmt.setInt(2,organizationId);
			stmt.setInt(3,awardId);
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

	public int getAwardId () {
		return awardId;
	}

	public void setAwardId (int awardId) {
		this.awardId = awardId;
	}

	public int getActualAwardId () {
		return awardId;
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

	public static Integer awardIdValue() throws JspException {
		try {
			return currentInstance.getAwardId();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function awardIdValue()");
		}
	}

	private void clearServiceState () {
		pmcid = 0;
		organizationId = 0;
		awardId = 0;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	}

}
