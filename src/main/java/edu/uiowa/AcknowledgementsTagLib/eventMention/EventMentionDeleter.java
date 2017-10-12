package edu.uiowa.AcknowledgementsTagLib.eventMention;


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
import edu.uiowa.AcknowledgementsTagLib.event.Event;

@SuppressWarnings("serial")
public class EventMentionDeleter extends AcknowledgementsTagLibBodyTagSupport {
    int eventId = 0;
    String pmcid = null;
	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	private static final Log log = LogFactory.getLog(EventMentionDeleter.class);


    ResultSet rs = null;
    String var = null;
    int rsCount = 0;

    public int doStartTag() throws JspException {
		Event theEvent = (Event)findAncestorWithClass(this, Event.class);
		if (theEvent!= null)
			parentEntities.addElement(theEvent);

		if (theEvent == null) {
		} else {
			eventId = theEvent.getID();
		}


        PreparedStatement stat;
        try {
            int webapp_keySeq = 1;
            stat = getConnection().prepareStatement("DELETE from pubmed_central_ack_stanford.event_mention where 1=1"
                                                        + (eventId == 0 ? "" : " and event_id = ? ")
                                                        + (pmcid == null ? "" : " and pmcid = ? "));
            if (eventId != 0) stat.setInt(webapp_keySeq++, eventId);
            if (pmcid != null) stat.setString(webapp_keySeq++, pmcid);
            stat.execute();

			webapp_keySeq = 1;
        } catch (SQLException e) {
            log.error("JDBC error generating EventMention deleter", e);
            clearServiceState();
            throw new JspTagException("Error: JDBC error generating EventMention deleter");
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
        eventId = 0;
        pmcid = null;
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



	public int getEventId () {
		return eventId;
	}

	public void setEventId (int eventId) {
		this.eventId = eventId;
	}

	public int getActualEventId () {
		return eventId;
	}

	public String getPmcid () {
		return pmcid;
	}

	public void setPmcid (String pmcid) {
		this.pmcid = pmcid;
	}

	public String getActualPmcid () {
		return pmcid;
	}
}
