package edu.uiowa.AcknowledgementsTagLib.personMention;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class PersonMentionPmcid extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(PersonMentionPmcid.class);


	public int doStartTag() throws JspException {
		try {
			PersonMention thePersonMention = (PersonMention)findAncestorWithClass(this, PersonMention.class);
			if (!thePersonMention.commitNeeded) {
				pageContext.getOut().print(thePersonMention.getPmcid());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing PersonMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing PersonMention for pmcid tag ");
		}
		return SKIP_BODY;
	}

	public int getPmcid() throws JspTagException {
		try {
			PersonMention thePersonMention = (PersonMention)findAncestorWithClass(this, PersonMention.class);
			return thePersonMention.getPmcid();
		} catch (Exception e) {
			log.error(" Can't find enclosing PersonMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing PersonMention for pmcid tag ");
		}
	}

	public void setPmcid(int pmcid) throws JspTagException {
		try {
			PersonMention thePersonMention = (PersonMention)findAncestorWithClass(this, PersonMention.class);
			thePersonMention.setPmcid(pmcid);
		} catch (Exception e) {
			log.error("Can't find enclosing PersonMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing PersonMention for pmcid tag ");
		}
	}

}
