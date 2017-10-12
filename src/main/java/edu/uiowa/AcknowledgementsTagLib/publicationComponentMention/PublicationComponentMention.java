package edu.uiowa.AcknowledgementsTagLib.publicationComponentMention;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import edu.uiowa.AcknowledgementsTagLib.publicationComponent.PublicationComponent;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;
import edu.uiowa.AcknowledgementsTagLib.Sequence;

@SuppressWarnings("serial")
public class PublicationComponentMention extends AcknowledgementsTagLibTagSupport {

	static PublicationComponentMention currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log = LogFactory.getLog(PublicationComponentMention.class);

	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	int publicationComponentId = 0;
	int pmcid = 0;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {
			PublicationComponent thePublicationComponent = (PublicationComponent)findAncestorWithClass(this, PublicationComponent.class);
			if (thePublicationComponent!= null)
				parentEntities.addElement(thePublicationComponent);

			if (thePublicationComponent == null) {
			} else {
				publicationComponentId = thePublicationComponent.getID();
			}

			PublicationComponentMentionIterator thePublicationComponentMentionIterator = (PublicationComponentMentionIterator)findAncestorWithClass(this, PublicationComponentMentionIterator.class);

			if (thePublicationComponentMentionIterator != null) {
				publicationComponentId = thePublicationComponentMentionIterator.getPublicationComponentId();
				pmcid = thePublicationComponentMentionIterator.getPmcid();
			}

			if (thePublicationComponentMentionIterator == null && thePublicationComponent == null && publicationComponentId == 0) {
				// no publicationComponentId was provided - the default is to assume that it is a new PublicationComponentMention and to generate a new publicationComponentId
				publicationComponentId = Sequence.generateID();
				insertEntity();
			} else {
				// an iterator or publicationComponentId was provided as an attribute - we need to load a PublicationComponentMention from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select 1 from pubmed_central_ack_stanford.publication_component_mention where publication_component_id = ? and pmcid = ?");
				stmt.setInt(1,publicationComponentId);
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
			log.error("JDBC error retrieving publicationComponentId " + publicationComponentId, e);
			throw new JspTagException("Error: JDBC error retrieving publicationComponentId " + publicationComponentId);
		} finally {
			freeConnection();
		}
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;
		try {
			if (commitNeeded) {
				PreparedStatement stmt = getConnection().prepareStatement("update pubmed_central_ack_stanford.publication_component_mention set where publication_component_id = ? and pmcid = ?");
				stmt.setInt(1,publicationComponentId);
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
			if (publicationComponentId == 0) {
				publicationComponentId = Sequence.generateID();
				log.debug("generating new PublicationComponentMention " + publicationComponentId);
			}

			if (pmcid == 0) {
				pmcid = Sequence.generateID();
				log.debug("generating new PublicationComponentMention " + pmcid);
			}

			PreparedStatement stmt = getConnection().prepareStatement("insert into pubmed_central_ack_stanford.publication_component_mention(publication_component_id,pmcid) values (?,?)");
			stmt.setInt(1,publicationComponentId);
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

	public int getPublicationComponentId () {
		return publicationComponentId;
	}

	public void setPublicationComponentId (int publicationComponentId) {
		this.publicationComponentId = publicationComponentId;
	}

	public int getActualPublicationComponentId () {
		return publicationComponentId;
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

	public static Integer publicationComponentIdValue() throws JspException {
		try {
			return currentInstance.getPublicationComponentId();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function publicationComponentIdValue()");
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
		publicationComponentId = 0;
		pmcid = 0;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	}

}
