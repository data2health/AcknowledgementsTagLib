package edu.uiowa.AcknowledgementsTagLib.eventMention;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class EventMentionEventId extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(EventMentionEventId.class);


	public int doStartTag() throws JspException {
		try {
			EventMention theEventMention = (EventMention)findAncestorWithClass(this, EventMention.class);
			if (!theEventMention.commitNeeded) {
				pageContext.getOut().print(theEventMention.getEventId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing EventMention for eventId tag ", e);
			throw new JspTagException("Error: Can't find enclosing EventMention for eventId tag ");
		}
		return SKIP_BODY;
	}

	public int getEventId() throws JspTagException {
		try {
			EventMention theEventMention = (EventMention)findAncestorWithClass(this, EventMention.class);
			return theEventMention.getEventId();
		} catch (Exception e) {
			log.error(" Can't find enclosing EventMention for eventId tag ", e);
			throw new JspTagException("Error: Can't find enclosing EventMention for eventId tag ");
		}
	}

	public void setEventId(int eventId) throws JspTagException {
		try {
			EventMention theEventMention = (EventMention)findAncestorWithClass(this, EventMention.class);
			theEventMention.setEventId(eventId);
		} catch (Exception e) {
			log.error("Can't find enclosing EventMention for eventId tag ", e);
			throw new JspTagException("Error: Can't find enclosing EventMention for eventId tag ");
		}
	}

}
