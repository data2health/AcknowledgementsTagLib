package edu.uiowa.AcknowledgementsTagLib.disease;

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
public class Disease extends AcknowledgementsTagLibTagSupport {

	static Disease currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log = LogFactory.getLog(Disease.class);

	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	int ID = 0;
	String disease = null;
	String umlsId = null;
	String umlsMatchString = null;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {


			DiseaseIterator theDiseaseIterator = (DiseaseIterator)findAncestorWithClass(this, DiseaseIterator.class);

			if (theDiseaseIterator != null) {
				ID = theDiseaseIterator.getID();
			}

			if (theDiseaseIterator == null && ID == 0) {
				// no ID was provided - the default is to assume that it is a new Disease and to generate a new ID
				ID = Sequence.generateID();
				insertEntity();
			} else {
				// an iterator or ID was provided as an attribute - we need to load a Disease from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select disease,umls_id,umls_match_string from pubmed_central_ack_stanford.disease where id = ?");
				stmt.setInt(1,ID);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if (disease == null)
						disease = rs.getString(1);
					if (umlsId == null)
						umlsId = rs.getString(2);
					if (umlsMatchString == null)
						umlsMatchString = rs.getString(3);
					found = true;
				}
				stmt.close();

				if (!found) {
					insertEntity();
				}
			}
		} catch (SQLException e) {
			log.error("JDBC error retrieving ID " + ID, e);
			throw new JspTagException("Error: JDBC error retrieving ID " + ID);
		} finally {
			freeConnection();
		}
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;
		try {
			if (commitNeeded) {
				PreparedStatement stmt = getConnection().prepareStatement("update pubmed_central_ack_stanford.disease set disease = ?, umls_id = ?, umls_match_string = ? where id = ?");
				stmt.setString(1,disease);
				stmt.setString(2,umlsId);
				stmt.setString(3,umlsMatchString);
				stmt.setInt(4,ID);
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
			if (ID == 0) {
				ID = Sequence.generateID();
				log.debug("generating new Disease " + ID);
			}

			if (disease == null)
				disease = "";
			if (umlsId == null)
				umlsId = "";
			if (umlsMatchString == null)
				umlsMatchString = "";
			PreparedStatement stmt = getConnection().prepareStatement("insert into pubmed_central_ack_stanford.disease(id,disease,umls_id,umls_match_string) values (?,?,?,?)");
			stmt.setInt(1,ID);
			stmt.setString(2,disease);
			stmt.setString(3,umlsId);
			stmt.setString(4,umlsMatchString);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			log.error("Error: IOException while writing to the user", e);
			throw new JspTagException("Error: IOException while writing to the user");
		} finally {
			freeConnection();
		}
	}

	public int getID () {
		return ID;
	}

	public void setID (int ID) {
		this.ID = ID;
	}

	public int getActualID () {
		return ID;
	}

	public String getDisease () {
		if (commitNeeded)
			return "";
		else
			return disease;
	}

	public void setDisease (String disease) {
		this.disease = disease;
		commitNeeded = true;
	}

	public String getActualDisease () {
		return disease;
	}

	public String getUmlsId () {
		if (commitNeeded)
			return "";
		else
			return umlsId;
	}

	public void setUmlsId (String umlsId) {
		this.umlsId = umlsId;
		commitNeeded = true;
	}

	public String getActualUmlsId () {
		return umlsId;
	}

	public String getUmlsMatchString () {
		if (commitNeeded)
			return "";
		else
			return umlsMatchString;
	}

	public void setUmlsMatchString (String umlsMatchString) {
		this.umlsMatchString = umlsMatchString;
		commitNeeded = true;
	}

	public String getActualUmlsMatchString () {
		return umlsMatchString;
	}

	public static Integer IDValue() throws JspException {
		try {
			return currentInstance.getID();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function IDValue()");
		}
	}

	public static String diseaseValue() throws JspException {
		try {
			return currentInstance.getDisease();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function diseaseValue()");
		}
	}

	public static String umlsIdValue() throws JspException {
		try {
			return currentInstance.getUmlsId();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function umlsIdValue()");
		}
	}

	public static String umlsMatchStringValue() throws JspException {
		try {
			return currentInstance.getUmlsMatchString();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function umlsMatchStringValue()");
		}
	}

	private void clearServiceState () {
		ID = 0;
		disease = null;
		umlsId = null;
		umlsMatchString = null;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	}

}
