package edu.uiowa.AcknowledgementsTagLib.eventMention;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class EventMentionPmcid extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(EventMentionPmcid.class);


	public int doStartTag() throws JspException {
		try {
			EventMention theEventMention = (EventMention)findAncestorWithClass(this, EventMention.class);
			if (!theEventMention.commitNeeded) {
				pageContext.getOut().print(theEventMention.getPmcid());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing EventMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing EventMention for pmcid tag ");
		}
		return SKIP_BODY;
	}

	public String getPmcid() throws JspTagException {
		try {
			EventMention theEventMention = (EventMention)findAncestorWithClass(this, EventMention.class);
			return theEventMention.getPmcid();
		} catch (Exception e) {
			log.error(" Can't find enclosing EventMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing EventMention for pmcid tag ");
		}
	}

	public void setPmcid(String pmcid) throws JspTagException {
		try {
			EventMention theEventMention = (EventMention)findAncestorWithClass(this, EventMention.class);
			theEventMention.setPmcid(pmcid);
		} catch (Exception e) {
			log.error("Can't find enclosing EventMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing EventMention for pmcid tag ");
		}
	}

}
