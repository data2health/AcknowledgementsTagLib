package edu.uiowa.AcknowledgementsTagLib.locationMention;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;
import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibBodyTagSupport;
import edu.uiowa.AcknowledgementsTagLib.location.Location;

@SuppressWarnings("serial")
public class LocationMentionDeleter extends AcknowledgementsTagLibBodyTagSupport {
    int locationId = 0;
    int pmcid = 0;
	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	private static final Log log = LogFactory.getLog(LocationMentionDeleter.class);


    ResultSet rs = null;
    String var = null;
    int rsCount = 0;

    public int doStartTag() throws JspException {
		Location theLocation = (Location)findAncestorWithClass(this, Location.class);
		if (theLocation!= null)
			parentEntities.addElement(theLocation);

		if (theLocation == null) {
		} else {
			locationId = theLocation.getID();
		}


        PreparedStatement stat;
        try {
            int webapp_keySeq = 1;
            stat = getConnection().prepareStatement("DELETE from pubmed_central_ack_stanford.location_mention where 1=1"
                                                        + (locationId == 0 ? "" : " and location_id = ? ")
                                                        + (pmcid == 0 ? "" : " and pmcid = ? "));
            if (locationId != 0) stat.setInt(webapp_keySeq++, locationId);
            if (pmcid != 0) stat.setInt(webapp_keySeq++, pmcid);
            stat.execute();

			webapp_keySeq = 1;
        } catch (SQLException e) {
            log.error("JDBC error generating LocationMention deleter", e);
            clearServiceState();
            throw new JspTagException("Error: JDBC error generating LocationMention deleter");
        } finally {
            freeConnection();
        }

        return SKIP_BODY;
    }

	public int doEndTag() throws JspException {
		clearServiceState();
		return super.doEndTag();
	}

    private void clearServiceState() {
        locationId = 0;
        pmcid = 0;
        parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

        this.rs = null;
        this.var = null;
        this.rsCount = 0;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
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
}
