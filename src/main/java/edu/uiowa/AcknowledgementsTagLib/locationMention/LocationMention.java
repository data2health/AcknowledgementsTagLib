package edu.uiowa.AcknowledgementsTagLib.locationMention;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import edu.uiowa.AcknowledgementsTagLib.location.Location;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;
import edu.uiowa.AcknowledgementsTagLib.Sequence;

@SuppressWarnings("serial")
public class LocationMention extends AcknowledgementsTagLibTagSupport {

	static LocationMention currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log = LogFactory.getLog(LocationMention.class);

	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	int locationId = 0;
	int pmcid = 0;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {
			Location theLocation = (Location)findAncestorWithClass(this, Location.class);
			if (theLocation!= null)
				parentEntities.addElement(theLocation);

			if (theLocation == null) {
			} else {
				locationId = theLocation.getID();
			}

			LocationMentionIterator theLocationMentionIterator = (LocationMentionIterator)findAncestorWithClass(this, LocationMentionIterator.class);

			if (theLocationMentionIterator != null) {
				locationId = theLocationMentionIterator.getLocationId();
				pmcid = theLocationMentionIterator.getPmcid();
			}

			if (theLocationMentionIterator == null && theLocation == null && locationId == 0) {
				// no locationId was provided - the default is to assume that it is a new LocationMention and to generate a new locationId
				locationId = Sequence.generateID();
				insertEntity();
			} else {
				// an iterator or locationId was provided as an attribute - we need to load a LocationMention from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select 1 from pubmed_central_ack_stanford.location_mention where location_id = ? and pmcid = ?");
				stmt.setInt(1,locationId);
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
			log.error("JDBC error retrieving locationId " + locationId, e);
			throw new JspTagException("Error: JDBC error retrieving locationId " + locationId);
		} finally {
			freeConnection();
		}
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;
		try {
			if (commitNeeded) {
				PreparedStatement stmt = getConnection().prepareStatement("update pubmed_central_ack_stanford.location_mention set where location_id = ? and pmcid = ?");
				stmt.setInt(1,locationId);
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
			if (locationId == 0) {
				locationId = Sequence.generateID();
				log.debug("generating new LocationMention " + locationId);
			}

			if (pmcid == 0) {
				pmcid = Sequence.generateID();
				log.debug("generating new LocationMention " + pmcid);
			}

			PreparedStatement stmt = getConnection().prepareStatement("insert into pubmed_central_ack_stanford.location_mention(location_id,pmcid) values (?,?)");
			stmt.setInt(1,locationId);
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

	public int getLocationId () {
		return locationId;
	}

	public void setLocationId (int locationId) {
		this.locationId = locationId;
	}

	public int getActualLocationId () {
		return locationId;
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

	public static Integer locationIdValue() throws JspException {
		try {
			return currentInstance.getLocationId();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function locationIdValue()");
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
		locationId = 0;
		pmcid = 0;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	}

}
