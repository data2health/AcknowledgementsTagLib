package edu.uiowa.AcknowledgementsTagLib.event;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class EventEvent extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(EventEvent.class);


	public int doStartTag() throws JspException {
		try {
			Event theEvent = (Event)findAncestorWithClass(this, Event.class);
			if (!theEvent.commitNeeded) {
				pageContext.getOut().print(theEvent.getEvent());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Event for event tag ", e);
			throw new JspTagException("Error: Can't find enclosing Event for event tag ");
		}
		return SKIP_BODY;
	}

	public String getEvent() throws JspTagException {
		try {
			Event theEvent = (Event)findAncestorWithClass(this, Event.class);
			return theEvent.getEvent();
		} catch (Exception e) {
			log.error(" Can't find enclosing Event for event tag ", e);
			throw new JspTagException("Error: Can't find enclosing Event for event tag ");
		}
	}

	public void setEvent(String event) throws JspTagException {
		try {
			Event theEvent = (Event)findAncestorWithClass(this, Event.class);
			theEvent.setEvent(event);
		} catch (Exception e) {
			log.error("Can't find enclosing Event for event tag ", e);
			throw new JspTagException("Error: Can't find enclosing Event for event tag ");
		}
	}

}
