package edu.uiowa.AcknowledgementsTagLib.clinicalTrial;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;
import edu.uiowa.AcknowledgementsTagLib.Sequence;

@SuppressWarnings("serial")
public class ClinicalTrial extends AcknowledgementsTagLibTagSupport {

	static ClinicalTrial currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log = LogFactory.getLog(ClinicalTrial.class);

	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	int pmcid = 0;
	String prefix = null;
	String ID = null;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {


			ClinicalTrialIterator theClinicalTrialIterator = (ClinicalTrialIterator)findAncestorWithClass(this, ClinicalTrialIterator.class);

			if (theClinicalTrialIterator != null) {
				pmcid = theClinicalTrialIterator.getPmcid();
				prefix = theClinicalTrialIterator.getPrefix();
				ID = theClinicalTrialIterator.getID();
			}

			if (theClinicalTrialIterator == null && pmcid == 0) {
				// no pmcid was provided - the default is to assume that it is a new ClinicalTrial and to generate a new pmcid
				pmcid = Sequence.generateID();
				insertEntity();
			} else {
				// an iterator or pmcid was provided as an attribute - we need to load a ClinicalTrial from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select 1 from pubmed_central_ack_stanford.clinical_trial where pmcid = ? and prefix = ? and id = ?");
				stmt.setInt(1,pmcid);
				stmt.setString(2,prefix);
				stmt.setString(3,ID);
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
				PreparedStatement stmt = getConnection().prepareStatement("update pubmed_central_ack_stanford.clinical_trial set where pmcid = ? and prefix = ? and id = ?");
				stmt.setInt(1,pmcid);
				stmt.setString(2,prefix);
				stmt.setString(3,ID);
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
				log.debug("generating new ClinicalTrial " + pmcid);
			}

			PreparedStatement stmt = getConnection().prepareStatement("insert into pubmed_central_ack_stanford.clinical_trial(pmcid,prefix,id) values (?,?,?)");
			stmt.setInt(1,pmcid);
			stmt.setString(2,prefix);
			stmt.setString(3,ID);
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

	public String getPrefix () {
		if (commitNeeded)
			return "";
		else
			return prefix;
	}

	public void setPrefix (String prefix) {
		this.prefix = prefix;
	}

	public String getActualPrefix () {
		return prefix;
	}

	public String getID () {
		if (commitNeeded)
			return "";
		else
			return ID;
	}

	public void setID (String ID) {
		this.ID = ID;
	}

	public String getActualID () {
		return ID;
	}

	public static Integer pmcidValue() throws JspException {
		try {
			return currentInstance.getPmcid();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function pmcidValue()");
		}
	}

	public static String prefixValue() throws JspException {
		try {
			return currentInstance.getPrefix();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function prefixValue()");
		}
	}

	public static String IDValue() throws JspException {
		try {
			return currentInstance.getID();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function IDValue()");
		}
	}

	private void clearServiceState () {
		pmcid = 0;
		prefix = null;
		ID = null;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	}

}
