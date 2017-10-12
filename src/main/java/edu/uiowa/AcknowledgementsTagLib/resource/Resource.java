package edu.uiowa.AcknowledgementsTagLib.resource;

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
public class Resource extends AcknowledgementsTagLibTagSupport {

	static Resource currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log = LogFactory.getLog(Resource.class);

	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	int ID = 0;
	String resource = null;
	String umlsId = null;
	String umlsMatchString = null;
	String altUmlsId = null;
	String altUmlsMatchString = null;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {


			ResourceIterator theResourceIterator = (ResourceIterator)findAncestorWithClass(this, ResourceIterator.class);

			if (theResourceIterator != null) {
				ID = theResourceIterator.getID();
			}

			if (theResourceIterator == null && ID == 0) {
				// no ID was provided - the default is to assume that it is a new Resource and to generate a new ID
				ID = Sequence.generateID();
				insertEntity();
			} else {
				// an iterator or ID was provided as an attribute - we need to load a Resource from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select resource,umls_id,umls_match_string,alt_umls_id,alt_umls_match_string from pubmed_central_ack_stanford.resource where id = ?");
				stmt.setInt(1,ID);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if (resource == null)
						resource = rs.getString(1);
					if (umlsId == null)
						umlsId = rs.getString(2);
					if (umlsMatchString == null)
						umlsMatchString = rs.getString(3);
					if (altUmlsId == null)
						altUmlsId = rs.getString(4);
					if (altUmlsMatchString == null)
						altUmlsMatchString = rs.getString(5);
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
				PreparedStatement stmt = getConnection().prepareStatement("update pubmed_central_ack_stanford.resource set resource = ?, umls_id = ?, umls_match_string = ?, alt_umls_id = ?, alt_umls_match_string = ? where id = ?");
				stmt.setString(1,resource);
				stmt.setString(2,umlsId);
				stmt.setString(3,umlsMatchString);
				stmt.setString(4,altUmlsId);
				stmt.setString(5,altUmlsMatchString);
				stmt.setInt(6,ID);
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
				log.debug("generating new Resource " + ID);
			}

			if (resource == null)
				resource = "";
			if (umlsId == null)
				umlsId = "";
			if (umlsMatchString == null)
				umlsMatchString = "";
			if (altUmlsId == null)
				altUmlsId = "";
			if (altUmlsMatchString == null)
				altUmlsMatchString = "";
			PreparedStatement stmt = getConnection().prepareStatement("insert into pubmed_central_ack_stanford.resource(id,resource,umls_id,umls_match_string,alt_umls_id,alt_umls_match_string) values (?,?,?,?,?,?)");
			stmt.setInt(1,ID);
			stmt.setString(2,resource);
			stmt.setString(3,umlsId);
			stmt.setString(4,umlsMatchString);
			stmt.setString(5,altUmlsId);
			stmt.setString(6,altUmlsMatchString);
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

	public String getResource () {
		if (commitNeeded)
			return "";
		else
			return resource;
	}

	public void setResource (String resource) {
		this.resource = resource;
		commitNeeded = true;
	}

	public String getActualResource () {
		return resource;
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

	public String getAltUmlsId () {
		if (commitNeeded)
			return "";
		else
			return altUmlsId;
	}

	public void setAltUmlsId (String altUmlsId) {
		this.altUmlsId = altUmlsId;
		commitNeeded = true;
	}

	public String getActualAltUmlsId () {
		return altUmlsId;
	}

	public String getAltUmlsMatchString () {
		if (commitNeeded)
			return "";
		else
			return altUmlsMatchString;
	}

	public void setAltUmlsMatchString (String altUmlsMatchString) {
		this.altUmlsMatchString = altUmlsMatchString;
		commitNeeded = true;
	}

	public String getActualAltUmlsMatchString () {
		return altUmlsMatchString;
	}

	public static Integer IDValue() throws JspException {
		try {
			return currentInstance.getID();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function IDValue()");
		}
	}

	public static String resourceValue() throws JspException {
		try {
			return currentInstance.getResource();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function resourceValue()");
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

	public static String altUmlsIdValue() throws JspException {
		try {
			return currentInstance.getAltUmlsId();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function altUmlsIdValue()");
		}
	}

	public static String altUmlsMatchStringValue() throws JspException {
		try {
			return currentInstance.getAltUmlsMatchString();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function altUmlsMatchStringValue()");
		}
	}

	private void clearServiceState () {
		ID = 0;
		resource = null;
		umlsId = null;
		umlsMatchString = null;
		altUmlsId = null;
		altUmlsMatchString = null;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	}

}
