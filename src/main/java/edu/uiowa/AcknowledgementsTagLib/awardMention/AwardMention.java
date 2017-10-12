package edu.uiowa.AcknowledgementsTagLib.awardMention;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import edu.uiowa.AcknowledgementsTagLib.award.Award;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;
import edu.uiowa.AcknowledgementsTagLib.Sequence;

@SuppressWarnings("serial")
public class AwardMention extends AcknowledgementsTagLibTagSupport {

	static AwardMention currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log = LogFactory.getLog(AwardMention.class);

	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	int awardId = 0;
	int pmcid = 0;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {
			Award theAward = (Award)findAncestorWithClass(this, Award.class);
			if (theAward!= null)
				parentEntities.addElement(theAward);

			if (theAward == null) {
			} else {
				awardId = theAward.getID();
			}

			AwardMentionIterator theAwardMentionIterator = (AwardMentionIterator)findAncestorWithClass(this, AwardMentionIterator.class);

			if (theAwardMentionIterator != null) {
				awardId = theAwardMentionIterator.getAwardId();
				pmcid = theAwardMentionIterator.getPmcid();
			}

			if (theAwardMentionIterator == null && theAward == null && awardId == 0) {
				// no awardId was provided - the default is to assume that it is a new AwardMention and to generate a new awardId
				awardId = Sequence.generateID();
				insertEntity();
			} else {
				// an iterator or awardId was provided as an attribute - we need to load a AwardMention from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select 1 from pubmed_central_ack_stanford.award_mention where award_id = ? and pmcid = ?");
				stmt.setInt(1,awardId);
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
			log.error("JDBC error retrieving awardId " + awardId, e);
			throw new JspTagException("Error: JDBC error retrieving awardId " + awardId);
		} finally {
			freeConnection();
		}
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;
		try {
			if (commitNeeded) {
				PreparedStatement stmt = getConnection().prepareStatement("update pubmed_central_ack_stanford.award_mention set where award_id = ? and pmcid = ?");
				stmt.setInt(1,awardId);
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
			if (awardId == 0) {
				awardId = Sequence.generateID();
				log.debug("generating new AwardMention " + awardId);
			}

			if (pmcid == 0) {
				pmcid = Sequence.generateID();
				log.debug("generating new AwardMention " + pmcid);
			}

			PreparedStatement stmt = getConnection().prepareStatement("insert into pubmed_central_ack_stanford.award_mention(award_id,pmcid) values (?,?)");
			stmt.setInt(1,awardId);
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

	public int getAwardId () {
		return awardId;
	}

	public void setAwardId (int awardId) {
		this.awardId = awardId;
	}

	public int getActualAwardId () {
		return awardId;
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

	public static Integer awardIdValue() throws JspException {
		try {
			return currentInstance.getAwardId();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function awardIdValue()");
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
		awardId = 0;
		pmcid = 0;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	}

}
