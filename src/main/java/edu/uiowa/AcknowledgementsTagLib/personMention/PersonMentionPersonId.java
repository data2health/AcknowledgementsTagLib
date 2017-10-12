package edu.uiowa.AcknowledgementsTagLib.personMention;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class PersonMentionPersonId extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(PersonMentionPersonId.class);


	public int doStartTag() throws JspException {
		try {
			PersonMention thePersonMention = (PersonMention)findAncestorWithClass(this, PersonMention.class);
			if (!thePersonMention.commitNeeded) {
				pageContext.getOut().print(thePersonMention.getPersonId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing PersonMention for personId tag ", e);
			throw new JspTagException("Error: Can't find enclosing PersonMention for personId tag ");
		}
		return SKIP_BODY;
	}

	public int getPersonId() throws JspTagException {
		try {
			PersonMention thePersonMention = (PersonMention)findAncestorWithClass(this, PersonMention.class);
			return thePersonMention.getPersonId();
		} catch (Exception e) {
			log.error(" Can't find enclosing PersonMention for personId tag ", e);
			throw new JspTagException("Error: Can't find enclosing PersonMention for personId tag ");
		}
	}

	public void setPersonId(int personId) throws JspTagException {
		try {
			PersonMention thePersonMention = (PersonMention)findAncestorWithClass(this, PersonMention.class);
			thePersonMention.setPersonId(personId);
		} catch (Exception e) {
			log.error("Can't find enclosing PersonMention for personId tag ", e);
			throw new JspTagException("Error: Can't find enclosing PersonMention for personId tag ");
		}
	}

}
