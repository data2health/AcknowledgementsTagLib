package edu.uiowa.AcknowledgementsTagLib.diseaseMention;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import edu.uiowa.AcknowledgementsTagLib.disease.Disease;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;
import edu.uiowa.AcknowledgementsTagLib.Sequence;

@SuppressWarnings("serial")
public class DiseaseMention extends AcknowledgementsTagLibTagSupport {

	static DiseaseMention currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log = LogFactory.getLog(DiseaseMention.class);

	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	int diseaseId = 0;
	int pmcid = 0;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {
			Disease theDisease = (Disease)findAncestorWithClass(this, Disease.class);
			if (theDisease!= null)
				parentEntities.addElement(theDisease);

			if (theDisease == null) {
			} else {
				diseaseId = theDisease.getID();
			}

			DiseaseMentionIterator theDiseaseMentionIterator = (DiseaseMentionIterator)findAncestorWithClass(this, DiseaseMentionIterator.class);

			if (theDiseaseMentionIterator != null) {
				diseaseId = theDiseaseMentionIterator.getDiseaseId();
				pmcid = theDiseaseMentionIterator.getPmcid();
			}

			if (theDiseaseMentionIterator == null && theDisease == null && diseaseId == 0) {
				// no diseaseId was provided - the default is to assume that it is a new DiseaseMention and to generate a new diseaseId
				diseaseId = Sequence.generateID();
				insertEntity();
			} else {
				// an iterator or diseaseId was provided as an attribute - we need to load a DiseaseMention from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select 1 from pubmed_central_ack_stanford.disease_mention where disease_id = ? and pmcid = ?");
				stmt.setInt(1,diseaseId);
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
			log.error("JDBC error retrieving diseaseId " + diseaseId, e);
			throw new JspTagException("Error: JDBC error retrieving diseaseId " + diseaseId);
		} finally {
			freeConnection();
		}
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;
		try {
			if (commitNeeded) {
				PreparedStatement stmt = getConnection().prepareStatement("update pubmed_central_ack_stanford.disease_mention set where disease_id = ? and pmcid = ?");
				stmt.setInt(1,diseaseId);
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
			if (diseaseId == 0) {
				diseaseId = Sequence.generateID();
				log.debug("generating new DiseaseMention " + diseaseId);
			}

			if (pmcid == 0) {
				pmcid = Sequence.generateID();
				log.debug("generating new DiseaseMention " + pmcid);
			}

			PreparedStatement stmt = getConnection().prepareStatement("insert into pubmed_central_ack_stanford.disease_mention(disease_id,pmcid) values (?,?)");
			stmt.setInt(1,diseaseId);
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

	public int getDiseaseId () {
		return diseaseId;
	}

	public void setDiseaseId (int diseaseId) {
		this.diseaseId = diseaseId;
	}

	public int getActualDiseaseId () {
		return diseaseId;
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

	public static Integer diseaseIdValue() throws JspException {
		try {
			return currentInstance.getDiseaseId();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function diseaseIdValue()");
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
		diseaseId = 0;
		pmcid = 0;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	}

}
