package edu.uiowa.AcknowledgementsTagLib.award;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class AwardAward extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(AwardAward.class);


	public int doStartTag() throws JspException {
		try {
			Award theAward = (Award)findAncestorWithClass(this, Award.class);
			if (!theAward.commitNeeded) {
				pageContext.getOut().print(theAward.getAward());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Award for award tag ", e);
			throw new JspTagException("Error: Can't find enclosing Award for award tag ");
		}
		return SKIP_BODY;
	}

	public String getAward() throws JspTagException {
		try {
			Award theAward = (Award)findAncestorWithClass(this, Award.class);
			return theAward.getAward();
		} catch (Exception e) {
			log.error(" Can't find enclosing Award for award tag ", e);
			throw new JspTagException("Error: Can't find enclosing Award for award tag ");
		}
	}

	public void setAward(String award) throws JspTagException {
		try {
			Award theAward = (Award)findAncestorWithClass(this, Award.class);
			theAward.setAward(award);
		} catch (Exception e) {
			log.error("Can't find enclosing Award for award tag ", e);
			throw new JspTagException("Error: Can't find enclosing Award for award tag ");
		}
	}

}
