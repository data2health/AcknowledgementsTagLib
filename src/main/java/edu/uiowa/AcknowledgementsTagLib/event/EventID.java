package edu.uiowa.AcknowledgementsTagLib.event;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class EventID extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(EventID.class);


	public int doStartTag() throws JspException {
		try {
			Event theEvent = (Event)findAncestorWithClass(this, Event.class);
			if (!theEvent.commitNeeded) {
				pageContext.getOut().print(theEvent.getID());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Event for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing Event for ID tag ");
		}
		return SKIP_BODY;
	}

	public int getID() throws JspTagException {
		try {
			Event theEvent = (Event)findAncestorWithClass(this, Event.class);
			return theEvent.getID();
		} catch (Exception e) {
			log.error(" Can't find enclosing Event for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing Event for ID tag ");
		}
	}

	public void setID(int ID) throws JspTagException {
		try {
			Event theEvent = (Event)findAncestorWithClass(this, Event.class);
			theEvent.setID(ID);
		} catch (Exception e) {
			log.error("Can't find enclosing Event for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing Event for ID tag ");
		}
	}

}
