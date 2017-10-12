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
public class EventMentionIterator extends AcknowledgementsTagLibBodyTagSupport {
    int eventId = 0;
    String pmcid = null;
	Vector<AcknowledgementsTagLibTagSupport> parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

	private static final Log log = LogFactory.getLog(EventMentionIterator.class);


    PreparedStatement stat = null;
    ResultSet rs = null;
    String sortCriteria = null;
    int limitCriteria = 0;
    String var = null;
    int rsCount = 0;

	public static String eventMentionCountByEvent(String ID) throws JspTagException {
		int count = 0;
		EventMentionIterator theIterator = new EventMentionIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from pubmed_central_ack_stanford.event_mention where 1=1"
						+ " and id = ?"
						);

			stat.setInt(1,Integer.parseInt(ID));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating EventMention iterator", e);
			throw new JspTagException("Error: JDBC error generating EventMention iterator");
		} finally {
			theIterator.freeConnection();
		}
		return "" + count;
	}

	public static Boolean eventHasEventMention(String ID) throws JspTagException {
		return ! eventMentionCountByEvent(ID).equals("0");
	}

	public static Boolean eventMentionExists (String eventId, String pmcid) throws JspTagException {
		int count = 0;
		EventMentionIterator theIterator = new EventMentionIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from pubmed_central_ack_stanford.event_mention where 1=1"
						+ " and event_id = ?"
						+ " and pmcid = ?"
						);

			stat.setInt(1,Integer.parseInt(eventId));
			stat.setString(2,pmcid);
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating EventMention iterator", e);
			throw new JspTagException("Error: JDBC error generating EventMention iterator");
		} finally {
			theIterator.freeConnection();
		}
		return count > 0;
	}

    public int doStartTag() throws JspException {
		Event theEvent = (Event)findAncestorWithClass(this, Event.class);
		if (theEvent!= null)
			parentEntities.addElement(theEvent);

		if (theEvent == null) {
		} else {
			eventId = theEvent.getID();
		}


      try {
            //run count query  
            int webapp_keySeq = 1;
            stat = getConnection().prepareStatement("SELECT count(*) from " + generateFromClause() + " where 1=1"
                                                        + generateJoinCriteria()
                                                        + (eventId == 0 ? "" : " and event_id = ?")
                                                        +  generateLimitCriteria());
            if (eventId != 0) stat.setInt(webapp_keySeq++, eventId);
            rs = stat.executeQuery();

            if (rs.next()) {
                pageContext.setAttribute(var+"Total", rs.getInt(1));
            }


            //run select id query  
            webapp_keySeq = 1;
            stat = getConnection().prepareStatement("SELECT pubmed_central_ack_stanford.event_mention.event_id, pubmed_central_ack_stanford.event_mention.pmcid from " + generateFromClause() + " where 1=1"
                                                        + generateJoinCriteria()
                                                        + (eventId == 0 ? "" : " and event_id = ?")
                                                        + " order by " + generateSortCriteria() + generateLimitCriteria());
            if (eventId != 0) stat.setInt(webapp_keySeq++, eventId);
            rs = stat.executeQuery();

            if (rs.next()) {
                eventId = rs.getInt(1);
                pmcid = rs.getString(2);
                pageContext.setAttribute(var, ++rsCount);
                return EVAL_BODY_INCLUDE;
            }
        } catch (SQLException e) {
            log.error("JDBC error generating EventMention iterator: " + stat.toString(), e);
            clearServiceState();
            freeConnection();
            throw new JspTagException("Error: JDBC error generating EventMention iterator: " + stat.toString());
        }

        return SKIP_BODY;
    }

    private String generateFromClause() {
       StringBuffer theBuffer = new StringBuffer("pubmed_central_ack_stanford.event_mention");
      return theBuffer.toString();
    }

    private String generateJoinCriteria() {
       StringBuffer theBuffer = new StringBuffer();
      return theBuffer.toString();
    }

    private String generateSortCriteria() {
        if (sortCriteria != null) {
            return sortCriteria;
        } else {
            return "event_id,pmcid";
        }
    }

    private String generateLimitCriteria() {
        if (limitCriteria > 0) {
            return " limit " + limitCriteria;
        } else {
            return "";
        }
    }

    public int doAfterBody() throws JspTagException {
        try {
            if (rs.next()) {
                eventId = rs.getInt(1);
                pmcid = rs.getString(2);
                pageContext.setAttribute(var, ++rsCount);
                return EVAL_BODY_AGAIN;
            }
        } catch (SQLException e) {
            log.error("JDBC error iterating across EventMention", e);
            clearServiceState();
            freeConnection();
            throw new JspTagException("Error: JDBC error iterating across EventMention");
        }
        return SKIP_BODY;
    }

    public int doEndTag() throws JspTagException, JspException {
        try {
            rs.close();
            stat.close();
        } catch (SQLException e) {
            log.error("JDBC error ending EventMention iterator",e);
            throw new JspTagException("Error: JDBC error ending EventMention iterator");
        } finally {
            clearServiceState();
            freeConnection();
        }
        return super.doEndTag();
    }

    private void clearServiceState() {
        eventId = 0;
        pmcid = null;
        parentEntities = new Vector<AcknowledgementsTagLibTagSupport>();

        this.rs = null;
        this.stat = null;
        this.sortCriteria = null;
        this.var = null;
        this.rsCount = 0;
    }

    public String getSortCriteria() {
        return sortCriteria;
    }

    public void setSortCriteria(String sortCriteria) {
        this.sortCriteria = sortCriteria;
    }

    public int getLimitCriteria() {
        return limitCriteria;
    }

    public void setLimitCriteria(int limitCriteria) {
        this.limitCriteria = limitCriteria;
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
