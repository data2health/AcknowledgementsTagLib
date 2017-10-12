package edu.uiowa.AcknowledgementsTagLib.fundingAgencyMention;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import edu.uiowa.AcknowledgementsTagLib.fundingAgency.FundingAgency;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;
import edu.uiowa.AcknowledgementsTagLib.Sequence;

@SuppressWarnings("serial")
public class FundingAgencyMention extends AcknowledgementsTagLibTagSupport {

	static FundingAgencyMention currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log = LogFactory.getLog(FundingAgencyMention.class);

	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	int fundingAgencyId = 0;
	int pmcid = 0;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {
			FundingAgency theFundingAgency = (FundingAgency)findAncestorWithClass(this, FundingAgency.class);
			if (theFundingAgency!= null)
				parentEntities.addElement(theFundingAgency);

			if (theFundingAgency == null) {
			} else {
				fundingAgencyId = theFundingAgency.getID();
			}

			FundingAgencyMentionIterator theFundingAgencyMentionIterator = (FundingAgencyMentionIterator)findAncestorWithClass(this, FundingAgencyMentionIterator.class);

			if (theFundingAgencyMentionIterator != null) {
				fundingAgencyId = theFundingAgencyMentionIterator.getFundingAgencyId();
				pmcid = theFundingAgencyMentionIterator.getPmcid();
			}

			if (theFundingAgencyMentionIterator == null && theFundingAgency == null && fundingAgencyId == 0) {
				// no fundingAgencyId was provided - the default is to assume that it is a new FundingAgencyMention and to generate a new fundingAgencyId
				fundingAgencyId = Sequence.generateID();
				insertEntity();
			} else {
				// an iterator or fundingAgencyId was provided as an attribute - we need to load a FundingAgencyMention from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select 1 from pubmed_central_ack_stanford.funding_agency_mention where funding_agency_id = ? and pmcid = ?");
				stmt.setInt(1,fundingAgencyId);
				stmt.setInt(2,pmcid);
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
			log.error("JDBC error retrieving fundingAgencyId " + fundingAgencyId, e);
			throw new JspTagException("Error: JDBC error retrieving fundingAgencyId " + fundingAgencyId);
		} finally {
			freeConnection();
		}
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;
		try {
			if (commitNeeded) {
				PreparedStatement stmt = getConnection().prepareStatement("update pubmed_central_ack_stanford.funding_agency_mention set where funding_agency_id = ? and pmcid = ?");
				stmt.setInt(1,fundingAgencyId);
				stmt.setInt(2,pmcid);
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
			if (fundingAgencyId == 0) {
				fundingAgencyId = Sequence.generateID();
				log.debug("generating new FundingAgencyMention " + fundingAgencyId);
			}

			if (pmcid == 0) {
				pmcid = Sequence.generateID();
				log.debug("generating new FundingAgencyMention " + pmcid);
			}

			PreparedStatement stmt = getConnection().prepareStatement("insert into pubmed_central_ack_stanford.funding_agency_mention(funding_agency_id,pmcid) values (?,?)");
			stmt.setInt(1,fundingAgencyId);
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

	public int getFundingAgencyId () {
		return fundingAgencyId;
	}

	public void setFundingAgencyId (int fundingAgencyId) {
		this.fundingAgencyId = fundingAgencyId;
	}

	public int getActualFundingAgencyId () {
		return fundingAgencyId;
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

	public static Integer fundingAgencyIdValue() throws JspException {
		try {
			return currentInstance.getFundingAgencyId();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function fundingAgencyIdValue()");
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
		fundingAgencyId = 0;
		pmcid = 0;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	}

}
