package edu.uiowa.AcknowledgementsTagLib.location;

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
public class Location extends AcknowledgementsTagLibTagSupport {

	static Location currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log = LogFactory.getLog(Location.class);

	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	int ID = 0;
	String location = null;
	int geonamesId = 0;
	String geonamesMatchString = null;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {


			LocationIterator theLocationIterator = (LocationIterator)findAncestorWithClass(this, LocationIterator.class);

			if (theLocationIterator != null) {
				ID = theLocationIterator.getID();
			}

			if (theLocationIterator == null && ID == 0) {
				// no ID was provided - the default is to assume that it is a new Location and to generate a new ID
				ID = Sequence.generateID();
				insertEntity();
			} else {
				// an iterator or ID was provided as an attribute - we need to load a Location from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select location,geonames_id,geonames_match_string from pubmed_central_ack_stanford.location where id = ?");
				stmt.setInt(1,ID);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if (location == null)
						location = rs.getString(1);
					if (geonamesId == 0)
						geonamesId = rs.getInt(2);
					if (geonamesMatchString == null)
						geonamesMatchString = rs.getString(3);
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
				PreparedStatement stmt = getConnection().prepareStatement("update pubmed_central_ack_stanford.location set location = ?, geonames_id = ?, geonames_match_string = ? where id = ?");
				stmt.setString(1,location);
				stmt.setInt(2,geonamesId);
				stmt.setString(3,geonamesMatchString);
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
				log.debug("generating new Location " + ID);
			}

			if (location == null)
				location = "";
			if (geonamesMatchString == null)
				geonamesMatchString = "";
			PreparedStatement stmt = getConnection().prepareStatement("insert into pubmed_central_ack_stanford.location(id,location,geonames_id,geonames_match_string) values (?,?,?,?)");
			stmt.setInt(1,ID);
			stmt.setString(2,location);
			stmt.setInt(3,geonamesId);
			stmt.setString(4,geonamesMatchString);
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

	public String getLocation () {
		if (commitNeeded)
			return "";
		else
			return location;
	}

	public void setLocation (String location) {
		this.location = location;
		commitNeeded = true;
	}

	public String getActualLocation () {
		return location;
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

	public static String locationValue() throws JspException {
		try {
			return currentInstance.getLocation();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function locationValue()");
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
		location = null;
		geonamesId = 0;
		geonamesMatchString = null;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	}

}
