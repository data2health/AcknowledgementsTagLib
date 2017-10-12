package edu.uiowa.AcknowledgementsTagLib.awardee;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class AwardeeAwardId extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(AwardeeAwardId.class);


	public int doStartTag() throws JspException {
		try {
			Awardee theAwardee = (Awardee)findAncestorWithClass(this, Awardee.class);
			if (!theAwardee.commitNeeded) {
				pageContext.getOut().print(theAwardee.getAwardId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Awardee for awardId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Awardee for awardId tag ");
		}
		return SKIP_BODY;
	}

	public int getAwardId() throws JspTagException {
		try {
			Awardee theAwardee = (Awardee)findAncestorWithClass(this, Awardee.class);
			return theAwardee.getAwardId();
		} catch (Exception e) {
			log.error(" Can't find enclosing Awardee for awardId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Awardee for awardId tag ");
		}
	}

	public void setAwardId(int awardId) throws JspTagException {
		try {
			Awardee theAwardee = (Awardee)findAncestorWithClass(this, Awardee.class);
			theAwardee.setAwardId(awardId);
		} catch (Exception e) {
			log.error("Can't find enclosing Awardee for awardId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Awardee for awardId tag ");
		}
	}

}
