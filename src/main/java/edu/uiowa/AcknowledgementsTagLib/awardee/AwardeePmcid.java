package edu.uiowa.AcknowledgementsTagLib.awardee;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class AwardeePmcid extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(AwardeePmcid.class);


	public int doStartTag() throws JspException {
		try {
			Awardee theAwardee = (Awardee)findAncestorWithClass(this, Awardee.class);
			if (!theAwardee.commitNeeded) {
				pageContext.getOut().print(theAwardee.getPmcid());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Awardee for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing Awardee for pmcid tag ");
		}
		return SKIP_BODY;
	}

	public int getPmcid() throws JspTagException {
		try {
			Awardee theAwardee = (Awardee)findAncestorWithClass(this, Awardee.class);
			return theAwardee.getPmcid();
		} catch (Exception e) {
			log.error(" Can't find enclosing Awardee for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing Awardee for pmcid tag ");
		}
	}

	public void setPmcid(int pmcid) throws JspTagException {
		try {
			Awardee theAwardee = (Awardee)findAncestorWithClass(this, Awardee.class);
			theAwardee.setPmcid(pmcid);
		} catch (Exception e) {
			log.error("Can't find enclosing Awardee for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing Awardee for pmcid tag ");
		}
	}

}
