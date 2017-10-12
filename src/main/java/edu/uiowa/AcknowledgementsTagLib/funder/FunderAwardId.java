package edu.uiowa.AcknowledgementsTagLib.funder;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class FunderAwardId extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(FunderAwardId.class);


	public int doStartTag() throws JspException {
		try {
			Funder theFunder = (Funder)findAncestorWithClass(this, Funder.class);
			if (!theFunder.commitNeeded) {
				pageContext.getOut().print(theFunder.getAwardId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Funder for awardId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Funder for awardId tag ");
		}
		return SKIP_BODY;
	}

	public int getAwardId() throws JspTagException {
		try {
			Funder theFunder = (Funder)findAncestorWithClass(this, Funder.class);
			return theFunder.getAwardId();
		} catch (Exception e) {
			log.error(" Can't find enclosing Funder for awardId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Funder for awardId tag ");
		}
	}

	public void setAwardId(int awardId) throws JspTagException {
		try {
			Funder theFunder = (Funder)findAncestorWithClass(this, Funder.class);
			theFunder.setAwardId(awardId);
		} catch (Exception e) {
			log.error("Can't find enclosing Funder for awardId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Funder for awardId tag ");
		}
	}

}
