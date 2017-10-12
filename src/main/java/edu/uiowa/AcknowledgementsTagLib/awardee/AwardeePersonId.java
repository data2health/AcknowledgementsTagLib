package edu.uiowa.AcknowledgementsTagLib.awardee;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class AwardeePersonId extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(AwardeePersonId.class);


	public int doStartTag() throws JspException {
		try {
			Awardee theAwardee = (Awardee)findAncestorWithClass(this, Awardee.class);
			if (!theAwardee.commitNeeded) {
				pageContext.getOut().print(theAwardee.getPersonId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Awardee for personId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Awardee for personId tag ");
		}
		return SKIP_BODY;
	}

	public int getPersonId() throws JspTagException {
		try {
			Awardee theAwardee = (Awardee)findAncestorWithClass(this, Awardee.class);
			return theAwardee.getPersonId();
		} catch (Exception e) {
			log.error(" Can't find enclosing Awardee for personId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Awardee for personId tag ");
		}
	}

	public void setPersonId(int personId) throws JspTagException {
		try {
			Awardee theAwardee = (Awardee)findAncestorWithClass(this, Awardee.class);
			theAwardee.setPersonId(personId);
		} catch (Exception e) {
			log.error("Can't find enclosing Awardee for personId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Awardee for personId tag ");
		}
	}

}
