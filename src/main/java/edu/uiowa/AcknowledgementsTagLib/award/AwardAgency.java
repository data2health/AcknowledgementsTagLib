package edu.uiowa.AcknowledgementsTagLib.award;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class AwardAgency extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(AwardAgency.class);


	public int doStartTag() throws JspException {
		try {
			Award theAward = (Award)findAncestorWithClass(this, Award.class);
			if (!theAward.commitNeeded) {
				pageContext.getOut().print(theAward.getAgency());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Award for agency tag ", e);
			throw new JspTagException("Error: Can't find enclosing Award for agency tag ");
		}
		return SKIP_BODY;
	}

	public String getAgency() throws JspTagException {
		try {
			Award theAward = (Award)findAncestorWithClass(this, Award.class);
			return theAward.getAgency();
		} catch (Exception e) {
			log.error(" Can't find enclosing Award for agency tag ", e);
			throw new JspTagException("Error: Can't find enclosing Award for agency tag ");
		}
	}

	public void setAgency(String agency) throws JspTagException {
		try {
			Award theAward = (Award)findAncestorWithClass(this, Award.class);
			theAward.setAgency(agency);
		} catch (Exception e) {
			log.error("Can't find enclosing Award for agency tag ", e);
			throw new JspTagException("Error: Can't find enclosing Award for agency tag ");
		}
	}

}
