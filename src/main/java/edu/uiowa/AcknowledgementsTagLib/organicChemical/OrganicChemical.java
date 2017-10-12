package edu.uiowa.AcknowledgementsTagLib.organicChemical;

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
public class OrganicChemical extends AcknowledgementsTagLibTagSupport {

	static OrganicChemical currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log = LogFactory.getLog(OrganicChemical.class);

	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	int ID = 0;
	String organicChemical = null;
	String umlsId = null;
	String umlsMatchString = null;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {


			OrganicChemicalIterator theOrganicChemicalIterator = (OrganicChemicalIterator)findAncestorWithClass(this, OrganicChemicalIterator.class);

			if (theOrganicChemicalIterator != null) {
				ID = theOrganicChemicalIterator.getID();
			}

			if (theOrganicChemicalIterator == null && ID == 0) {
				// no ID was provided - the default is to assume that it is a new OrganicChemical and to generate a new ID
				ID = Sequence.generateID();
				insertEntity();
			} else {
				// an iterator or ID was provided as an attribute - we need to load a OrganicChemical from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select organic_chemical,umls_id,umls_match_string from pubmed_central_ack_stanford.organic_chemical where id = ?");
				stmt.setInt(1,ID);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if (organicChemical == null)
						organicChemical = rs.getString(1);
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
				PreparedStatement stmt = getConnection().prepareStatement("update pubmed_central_ack_stanford.organic_chemical set organic_chemical = ?, umls_id = ?, umls_match_string = ? where id = ?");
				stmt.setString(1,organicChemical);
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
				log.debug("generating new OrganicChemical " + ID);
			}

			if (organicChemical == null)
				organicChemical = "";
			if (umlsId == null)
				umlsId = "";
			if (umlsMatchString == null)
				umlsMatchString = "";
			PreparedStatement stmt = getConnection().prepareStatement("insert into pubmed_central_ack_stanford.organic_chemical(id,organic_chemical,umls_id,umls_match_string) values (?,?,?,?)");
			stmt.setInt(1,ID);
			stmt.setString(2,organicChemical);
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

	public String getOrganicChemical () {
		if (commitNeeded)
			return "";
		else
			return organicChemical;
	}

	public void setOrganicChemical (String organicChemical) {
		this.organicChemical = organicChemical;
		commitNeeded = true;
	}

	public String getActualOrganicChemical () {
		return organicChemical;
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

	public static String organicChemicalValue() throws JspException {
		try {
			return currentInstance.getOrganicChemical();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function organicChemicalValue()");
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
		organicChemical = null;
		umlsId = null;
		umlsMatchString = null;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	}

}
