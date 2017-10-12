package edu.uiowa.AcknowledgementsTagLib.organization;

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
public class Organization extends AcknowledgementsTagLibTagSupport {

	static Organization currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log = LogFactory.getLog(Organization.class);

	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	int ID = 0;
	String organization = null;
	String gridId = null;
	String gridMatchString = null;
	int geonamesId = 0;
	String geonamesMatchString = null;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {


			OrganizationIterator theOrganizationIterator = (OrganizationIterator)findAncestorWithClass(this, OrganizationIterator.class);

			if (theOrganizationIterator != null) {
				ID = theOrganizationIterator.getID();
			}

			if (theOrganizationIterator == null && ID == 0) {
				// no ID was provided - the default is to assume that it is a new Organization and to generate a new ID
				ID = Sequence.generateID();
				insertEntity();
			} else {
				// an iterator or ID was provided as an attribute - we need to load a Organization from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select organization,grid_id,grid_match_string,geonames_id,geonames_match_string from pubmed_central_ack_stanford.organization where id = ?");
				stmt.setInt(1,ID);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if (organization == null)
						organization = rs.getString(1);
					if (gridId == null)
						gridId = rs.getString(2);
					if (gridMatchString == null)
						gridMatchString = rs.getString(3);
					if (geonamesId == 0)
						geonamesId = rs.getInt(4);
					if (geonamesMatchString == null)
						geonamesMatchString = rs.getString(5);
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
				PreparedStatement stmt = getConnection().prepareStatement("update pubmed_central_ack_stanford.organization set organization = ?, grid_id = ?, grid_match_string = ?, geonames_id = ?, geonames_match_string = ? where id = ?");
				stmt.setString(1,organization);
				stmt.setString(2,gridId);
				stmt.setString(3,gridMatchString);
				stmt.setInt(4,geonamesId);
				stmt.setString(5,geonamesMatchString);
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
				log.debug("generating new Organization " + ID);
			}

			if (organization == null)
				organization = "";
			if (gridId == null)
				gridId = "";
			if (gridMatchString == null)
				gridMatchString = "";
			if (geonamesMatchString == null)
				geonamesMatchString = "";
			PreparedStatement stmt = getConnection().prepareStatement("insert into pubmed_central_ack_stanford.organization(id,organization,grid_id,grid_match_string,geonames_id,geonames_match_string) values (?,?,?,?,?,?)");
			stmt.setInt(1,ID);
			stmt.setString(2,organization);
			stmt.setString(3,gridId);
			stmt.setString(4,gridMatchString);
			stmt.setInt(5,geonamesId);
			stmt.setString(6,geonamesMatchString);
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

	public String getOrganization () {
		if (commitNeeded)
			return "";
		else
			return organization;
	}

	public void setOrganization (String organization) {
		this.organization = organization;
		commitNeeded = true;
	}

	public String getActualOrganization () {
		return organization;
	}

	public String getGridId () {
		if (commitNeeded)
			return "";
		else
			return gridId;
	}

	public void setGridId (String gridId) {
		this.gridId = gridId;
		commitNeeded = true;
	}

	public String getActualGridId () {
		return gridId;
	}

	public String getGridMatchString () {
		if (commitNeeded)
			return "";
		else
			return gridMatchString;
	}

	public void setGridMatchString (String gridMatchString) {
		this.gridMatchString = gridMatchString;
		commitNeeded = true;
	}

	public String getActualGridMatchString () {
		return gridMatchString;
	}

	public int getGeonamesId () {
		return geonamesId;
	}

	public void setGeonamesId (int geonamesId) {
		this.geonamesId = geonamesId;
		commitNeeded = true;
	}

	public int getActualGeonamesId () {
		return geonamesId;
	}

	public String getGeonamesMatchString () {
		if (commitNeeded)
			return "";
		else
			return geonamesMatchString;
	}

	public void setGeonamesMatchString (String geonamesMatchString) {
		this.geonamesMatchString = geonamesMatchString;
		commitNeeded = true;
	}

	public String getActualGeonamesMatchString () {
		return geonamesMatchString;
	}

	public static Integer IDValue() throws JspException {
		try {
			return currentInstance.getID();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function IDValue()");
		}
	}

	public static String organizationValue() throws JspException {
		try {
			return currentInstance.getOrganization();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function organizationValue()");
		}
	}

	public static String gridIdValue() throws JspException {
		try {
			return currentInstance.getGridId();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function gridIdValue()");
		}
	}

	public static String gridMatchStringValue() throws JspException {
		try {
			return currentInstance.getGridMatchString();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function gridMatchStringValue()");
		}
	}

	public static Integer geonamesIdValue() throws JspException {
		try {
			return currentInstance.getGeonamesId();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function geonamesIdValue()");
		}
	}

	public static String geonamesMatchStringValue() throws JspException {
		try {
			return currentInstance.getGeonamesMatchString();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function geonamesMatchStringValue()");
		}
	}

	private void clearServiceState () {
		ID = 0;
		organization = null;
		gridId = null;
		gridMatchString = null;
		geonamesId = 0;
		geonamesMatchString = null;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	}

}
