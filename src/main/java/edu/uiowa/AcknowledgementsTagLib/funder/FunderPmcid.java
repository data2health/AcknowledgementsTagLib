package edu.uiowa.AcknowledgementsTagLib.funder;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class FunderPmcid extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(FunderPmcid.class);


	public int doStartTag() throws JspException {
		try {
			Funder theFunder = (Funder)findAncestorWithClass(this, Funder.class);
			if (!theFunder.commitNeeded) {
				pageContext.getOut().print(theFunder.getPmcid());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Funder for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing Funder for pmcid tag ");
		}
		return SKIP_BODY;
	}

	public int getPmcid() throws JspTagException {
		try {
			Funder theFunder = (Funder)findAncestorWithClass(this, Funder.class);
			return theFunder.getPmcid();
		} catch (Exception e) {
			log.error(" Can't find enclosing Funder for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing Funder for pmcid tag ");
		}
	}

	public void setPmcid(int pmcid) throws JspTagException {
		try {
			Funder theFunder = (Funder)findAncestorWithClass(this, Funder.class);
			theFunder.setPmcid(pmcid);
		} catch (Exception e) {
			log.error("Can't find enclosing Funder for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing Funder for pmcid tag ");
		}
	}

}
