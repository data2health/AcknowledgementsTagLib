package edu.uiowa.AcknowledgementsTagLib.collaborationMention;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import edu.uiowa.AcknowledgementsTagLib.collaboration.Collaboration;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;
import edu.uiowa.AcknowledgementsTagLib.Sequence;

@SuppressWarnings("serial")
public class CollaborationMention extends AcknowledgementsTagLibTagSupport {

	static CollaborationMention currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log = LogFactory.getLog(CollaborationMention.class);

	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	int collaborationId = 0;
	int pmcid = 0;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {
			Collaboration theCollaboration = (Collaboration)findAncestorWithClass(this, Collaboration.class);
			if (theCollaboration!= null)
				parentEntities.addElement(theCollaboration);

			if (theCollaboration == null) {
			} else {
				collaborationId = theCollaboration.getID();
			}

			CollaborationMentionIterator theCollaborationMentionIterator = (CollaborationMentionIterator)findAncestorWithClass(this, CollaborationMentionIterator.class);

			if (theCollaborationMentionIterator != null) {
				collaborationId = theCollaborationMentionIterator.getCollaborationId();
				pmcid = theCollaborationMentionIterator.getPmcid();
			}

			if (theCollaborationMentionIterator == null && theCollaboration == null && collaborationId == 0) {
				// no collaborationId was provided - the default is to assume that it is a new CollaborationMention and to generate a new collaborationId
				collaborationId = Sequence.generateID();
				insertEntity();
			} else {
				// an iterator or collaborationId was provided as an attribute - we need to load a CollaborationMention from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select 1 from pubmed_central_ack_stanford.collaboration_mention where collaboration_id = ? and pmcid = ?");
				stmt.setInt(1,collaborationId);
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
			log.error("JDBC error retrieving collaborationId " + collaborationId, e);
			throw new JspTagException("Error: JDBC error retrieving collaborationId " + collaborationId);
		} finally {
			freeConnection();
		}
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;
		try {
			if (commitNeeded) {
				PreparedStatement stmt = getConnection().prepareStatement("update pubmed_central_ack_stanford.collaboration_mention set where collaboration_id = ? and pmcid = ?");
				stmt.setInt(1,collaborationId);
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
			if (collaborationId == 0) {
				collaborationId = Sequence.generateID();
				log.debug("generating new CollaborationMention " + collaborationId);
			}

			if (pmcid == 0) {
				pmcid = Sequence.generateID();
				log.debug("generating new CollaborationMention " + pmcid);
			}

			PreparedStatement stmt = getConnection().prepareStatement("insert into pubmed_central_ack_stanford.collaboration_mention(collaboration_id,pmcid) values (?,?)");
			stmt.setInt(1,collaborationId);
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

	public int getCollaborationId () {
		return collaborationId;
	}

	public void setCollaborationId (int collaborationId) {
		this.collaborationId = collaborationId;
	}

	public int getActualCollaborationId () {
		return collaborationId;
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

	public static Integer collaborationIdValue() throws JspException {
		try {
			return currentInstance.getCollaborationId();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function collaborationIdValue()");
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
		collaborationId = 0;
		pmcid = 0;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	}

}
