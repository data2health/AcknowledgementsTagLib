package edu.uiowa.AcknowledgementsTagLib.organicChemicalMention;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import edu.uiowa.AcknowledgementsTagLib.organicChemical.OrganicChemical;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;
import edu.uiowa.AcknowledgementsTagLib.Sequence;

@SuppressWarnings("serial")
public class OrganicChemicalMention extends AcknowledgementsTagLibTagSupport {

	static OrganicChemicalMention currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log = LogFactory.getLog(OrganicChemicalMention.class);

	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	int organicChemicalId = 0;
	int pmcid = 0;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {
			OrganicChemical theOrganicChemical = (OrganicChemical)findAncestorWithClass(this, OrganicChemical.class);
			if (theOrganicChemical!= null)
				parentEntities.addElement(theOrganicChemical);

			if (theOrganicChemical == null) {
			} else {
				organicChemicalId = theOrganicChemical.getID();
			}

			OrganicChemicalMentionIterator theOrganicChemicalMentionIterator = (OrganicChemicalMentionIterator)findAncestorWithClass(this, OrganicChemicalMentionIterator.class);

			if (theOrganicChemicalMentionIterator != null) {
				organicChemicalId = theOrganicChemicalMentionIterator.getOrganicChemicalId();
				pmcid = theOrganicChemicalMentionIterator.getPmcid();
			}

			if (theOrganicChemicalMentionIterator == null && theOrganicChemical == null && organicChemicalId == 0) {
				// no organicChemicalId was provided - the default is to assume that it is a new OrganicChemicalMention and to generate a new organicChemicalId
				organicChemicalId = Sequence.generateID();
				insertEntity();
			} else {
				// an iterator or organicChemicalId was provided as an attribute - we need to load a OrganicChemicalMention from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select 1 from pubmed_central_ack_stanford.organic_chemical_mention where organic_chemical_id = ? and pmcid = ?");
				stmt.setInt(1,organicChemicalId);
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
			log.error("JDBC error retrieving organicChemicalId " + organicChemicalId, e);
			throw new JspTagException("Error: JDBC error retrieving organicChemicalId " + organicChemicalId);
		} finally {
			freeConnection();
		}
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;
		try {
			if (commitNeeded) {
				PreparedStatement stmt = getConnection().prepareStatement("update pubmed_central_ack_stanford.organic_chemical_mention set where organic_chemical_id = ? and pmcid = ?");
				stmt.setInt(1,organicChemicalId);
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
			if (organicChemicalId == 0) {
				organicChemicalId = Sequence.generateID();
				log.debug("generating new OrganicChemicalMention " + organicChemicalId);
			}

			if (pmcid == 0) {
				pmcid = Sequence.generateID();
				log.debug("generating new OrganicChemicalMention " + pmcid);
			}

			PreparedStatement stmt = getConnection().prepareStatement("insert into pubmed_central_ack_stanford.organic_chemical_mention(organic_chemical_id,pmcid) values (?,?)");
			stmt.setInt(1,organicChemicalId);
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

	public int getOrganicChemicalId () {
		return organicChemicalId;
	}

	public void setOrganicChemicalId (int organicChemicalId) {
		this.organicChemicalId = organicChemicalId;
	}

	public int getActualOrganicChemicalId () {
		return organicChemicalId;
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

	public static Integer organicChemicalIdValue() throws JspException {
		try {
			return currentInstance.getOrganicChemicalId();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function organicChemicalIdValue()");
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
		organicChemicalId = 0;
		pmcid = 0;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	}

}
