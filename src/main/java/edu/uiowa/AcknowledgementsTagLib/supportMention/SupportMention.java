package edu.uiowa.AcknowledgementsTagLib.supportMention;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import edu.uiowa.AcknowledgementsTagLib.support.Support;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;
import edu.uiowa.AcknowledgementsTagLib.Sequence;

@SuppressWarnings("serial")
public class SupportMention extends AcknowledgementsTagLibTagSupport {

	static SupportMention currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log = LogFactory.getLog(SupportMention.class);

	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	int supportId = 0;
	int pmcid = 0;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {
			Support theSupport = (Support)findAncestorWithClass(this, Support.class);
			if (theSupport!= null)
				parentEntities.addElement(theSupport);

			if (theSupport == null) {
			} else {
				supportId = theSupport.getID();
			}

			SupportMentionIterator theSupportMentionIterator = (SupportMentionIterator)findAncestorWithClass(this, SupportMentionIterator.class);

			if (theSupportMentionIterator != null) {
				supportId = theSupportMentionIterator.getSupportId();
				pmcid = theSupportMentionIterator.getPmcid();
			}

			if (theSupportMentionIterator == null && theSupport == null && supportId == 0) {
				// no supportId was provided - the default is to assume that it is a new SupportMention and to generate a new supportId
				supportId = Sequence.generateID();
				insertEntity();
			} else {
				// an iterator or supportId was provided as an attribute - we need to load a SupportMention from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select 1 from pubmed_central_ack_stanford.support_mention where support_id = ? and pmcid = ?");
				stmt.setInt(1,supportId);
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
			log.error("JDBC error retrieving supportId " + supportId, e);
			throw new JspTagException("Error: JDBC error retrieving supportId " + supportId);
		} finally {
			freeConnection();
		}
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;
		try {
			if (commitNeeded) {
				PreparedStatement stmt = getConnection().prepareStatement("update pubmed_central_ack_stanford.support_mention set where support_id = ? and pmcid = ?");
				stmt.setInt(1,supportId);
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
			if (supportId == 0) {
				supportId = Sequence.generateID();
				log.debug("generating new SupportMention " + supportId);
			}

			if (pmcid == 0) {
				pmcid = Sequence.generateID();
				log.debug("generating new SupportMention " + pmcid);
			}

			PreparedStatement stmt = getConnection().prepareStatement("insert into pubmed_central_ack_stanford.support_mention(support_id,pmcid) values (?,?)");
			stmt.setInt(1,supportId);
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

	public int getSupportId () {
		return supportId;
	}

	public void setSupportId (int supportId) {
		this.supportId = supportId;
	}

	public int getActualSupportId () {
		return supportId;
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

	public static Integer supportIdValue() throws JspException {
		try {
			return currentInstance.getSupportId();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function supportIdValue()");
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
		supportId = 0;
		pmcid = 0;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	}

}
