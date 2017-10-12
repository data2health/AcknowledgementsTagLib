package edu.uiowa.AcknowledgementsTagLib.eventMention;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import edu.uiowa.AcknowledgementsTagLib.event.Event;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;
import edu.uiowa.AcknowledgementsTagLib.Sequence;

@SuppressWarnings("serial")
public class EventMention extends AcknowledgementsTagLibTagSupport {

	static EventMention currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log = LogFactory.getLog(EventMention.class);

	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	int eventId = 0;
	String pmcid = null;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {
			Event theEvent = (Event)findAncestorWithClass(this, Event.class);
			if (theEvent!= null)
				parentEntities.addElement(theEvent);

			if (theEvent == null) {
			} else {
				eventId = theEvent.getID();
			}

			EventMentionIterator theEventMentionIterator = (EventMentionIterator)findAncestorWithClass(this, EventMentionIterator.class);

			if (theEventMentionIterator != null) {
				eventId = theEventMentionIterator.getEventId();
				pmcid = theEventMentionIterator.getPmcid();
			}

			if (theEventMentionIterator == null && theEvent == null && eventId == 0) {
				// no eventId was provided - the default is to assume that it is a new EventMention and to generate a new eventId
				eventId = Sequence.generateID();
				insertEntity();
			} else {
				// an iterator or eventId was provided as an attribute - we need to load a EventMention from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select 1 from pubmed_central_ack_stanford.event_mention where event_id = ? and pmcid = ?");
				stmt.setInt(1,eventId);
				stmt.setString(2,pmcid);
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
			log.error("JDBC error retrieving eventId " + eventId, e);
			throw new JspTagException("Error: JDBC error retrieving eventId " + eventId);
		} finally {
			freeConnection();
		}
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;
		try {
			if (commitNeeded) {
				PreparedStatement stmt = getConnection().prepareStatement("update pubmed_central_ack_stanford.event_mention set where event_id = ? and pmcid = ?");
				stmt.setInt(1,eventId);
				stmt.setString(2,pmcid);
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
			if (eventId == 0) {
				eventId = Sequence.generateID();
				log.debug("generating new EventMention " + eventId);
			}

			PreparedStatement stmt = getConnection().prepareStatement("insert into pubmed_central_ack_stanford.event_mention(event_id,pmcid) values (?,?)");
			stmt.setInt(1,eventId);
			stmt.setString(2,pmcid);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			log.error("Error: IOException while writing to the user", e);
			throw new JspTagException("Error: IOException while writing to the user");
		} finally {
			freeConnection();
		}
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
		if (commitNeeded)
			return "";
		else
			return pmcid;
	}

	public void setPmcid (String pmcid) {
		this.pmcid = pmcid;
	}

	public String getActualPmcid () {
		return pmcid;
	}

	public static Integer eventIdValue() throws JspException {
		try {
			return currentInstance.getEventId();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function eventIdValue()");
		}
	}

	public static String pmcidValue() throws JspException {
		try {
			return currentInstance.getPmcid();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function pmcidValue()");
		}
	}

	private void clearServiceState () {
		eventId = 0;
		pmcid = null;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	}

}
