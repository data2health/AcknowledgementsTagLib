package edu.uiowa.AcknowledgementsTagLib.organismMention;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import edu.uiowa.AcknowledgementsTagLib.organism.Organism;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;
import edu.uiowa.AcknowledgementsTagLib.Sequence;

@SuppressWarnings("serial")
public class OrganismMention extends AcknowledgementsTagLibTagSupport {

	static OrganismMention currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log = LogFactory.getLog(OrganismMention.class);

	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	int organismId = 0;
	int pmcid = 0;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {
			Organism theOrganism = (Organism)findAncestorWithClass(this, Organism.class);
			if (theOrganism!= null)
				parentEntities.addElement(theOrganism);

			if (theOrganism == null) {
			} else {
				organismId = theOrganism.getID();
			}

			OrganismMentionIterator theOrganismMentionIterator = (OrganismMentionIterator)findAncestorWithClass(this, OrganismMentionIterator.class);

			if (theOrganismMentionIterator != null) {
				organismId = theOrganismMentionIterator.getOrganismId();
				pmcid = theOrganismMentionIterator.getPmcid();
			}

			if (theOrganismMentionIterator == null && theOrganism == null && organismId == 0) {
				// no organismId was provided - the default is to assume that it is a new OrganismMention and to generate a new organismId
				organismId = Sequence.generateID();
				insertEntity();
			} else {
				// an iterator or organismId was provided as an attribute - we need to load a OrganismMention from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select 1 from pubmed_central_ack_stanford.organism_mention where organism_id = ? and pmcid = ?");
				stmt.setInt(1,organismId);
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
			log.error("JDBC error retrieving organismId " + organismId, e);
			throw new JspTagException("Error: JDBC error retrieving organismId " + organismId);
		} finally {
			freeConnection();
		}
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;
		try {
			if (commitNeeded) {
				PreparedStatement stmt = getConnection().prepareStatement("update pubmed_central_ack_stanford.organism_mention set where organism_id = ? and pmcid = ?");
				stmt.setInt(1,organismId);
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
			if (organismId == 0) {
				organismId = Sequence.generateID();
				log.debug("generating new OrganismMention " + organismId);
			}

			if (pmcid == 0) {
				pmcid = Sequence.generateID();
				log.debug("generating new OrganismMention " + pmcid);
			}

			PreparedStatement stmt = getConnection().prepareStatement("insert into pubmed_central_ack_stanford.organism_mention(organism_id,pmcid) values (?,?)");
			stmt.setInt(1,organismId);
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

	public int getOrganismId () {
		return organismId;
	}

	public void setOrganismId (int organismId) {
		this.organismId = organismId;
	}

	public int getActualOrganismId () {
		return organismId;
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

	public static Integer organismIdValue() throws JspException {
		try {
			return currentInstance.getOrganismId();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function organismIdValue()");
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
		organismId = 0;
		pmcid = 0;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	}

}
