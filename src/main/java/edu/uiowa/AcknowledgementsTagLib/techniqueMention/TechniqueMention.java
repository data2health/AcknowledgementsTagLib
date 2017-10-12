package edu.uiowa.AcknowledgementsTagLib.techniqueMention;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import edu.uiowa.AcknowledgementsTagLib.technique.Technique;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;
import edu.uiowa.AcknowledgementsTagLib.Sequence;

@SuppressWarnings("serial")
public class TechniqueMention extends AcknowledgementsTagLibTagSupport {

	static TechniqueMention currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log = LogFactory.getLog(TechniqueMention.class);

	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	int techniqueId = 0;
	int pmcid = 0;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {
			Technique theTechnique = (Technique)findAncestorWithClass(this, Technique.class);
			if (theTechnique!= null)
				parentEntities.addElement(theTechnique);

			if (theTechnique == null) {
			} else {
				techniqueId = theTechnique.getID();
			}

			TechniqueMentionIterator theTechniqueMentionIterator = (TechniqueMentionIterator)findAncestorWithClass(this, TechniqueMentionIterator.class);

			if (theTechniqueMentionIterator != null) {
				techniqueId = theTechniqueMentionIterator.getTechniqueId();
				pmcid = theTechniqueMentionIterator.getPmcid();
			}

			if (theTechniqueMentionIterator == null && theTechnique == null && techniqueId == 0) {
				// no techniqueId was provided - the default is to assume that it is a new TechniqueMention and to generate a new techniqueId
				techniqueId = Sequence.generateID();
				insertEntity();
			} else {
				// an iterator or techniqueId was provided as an attribute - we need to load a TechniqueMention from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select 1 from pubmed_central_ack_stanford.technique_mention where technique_id = ? and pmcid = ?");
				stmt.setInt(1,techniqueId);
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
			log.error("JDBC error retrieving techniqueId " + techniqueId, e);
			throw new JspTagException("Error: JDBC error retrieving techniqueId " + techniqueId);
		} finally {
			freeConnection();
		}
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;
		try {
			if (commitNeeded) {
				PreparedStatement stmt = getConnection().prepareStatement("update pubmed_central_ack_stanford.technique_mention set where technique_id = ? and pmcid = ?");
				stmt.setInt(1,techniqueId);
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
			if (techniqueId == 0) {
				techniqueId = Sequence.generateID();
				log.debug("generating new TechniqueMention " + techniqueId);
			}

			if (pmcid == 0) {
				pmcid = Sequence.generateID();
				log.debug("generating new TechniqueMention " + pmcid);
			}

			PreparedStatement stmt = getConnection().prepareStatement("insert into pubmed_central_ack_stanford.technique_mention(technique_id,pmcid) values (?,?)");
			stmt.setInt(1,techniqueId);
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

	public int getTechniqueId () {
		return techniqueId;
	}

	public void setTechniqueId (int techniqueId) {
		this.techniqueId = techniqueId;
	}

	public int getActualTechniqueId () {
		return techniqueId;
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

	public static Integer techniqueIdValue() throws JspException {
		try {
			return currentInstance.getTechniqueId();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function techniqueIdValue()");
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
		techniqueId = 0;
		pmcid = 0;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	}

}
