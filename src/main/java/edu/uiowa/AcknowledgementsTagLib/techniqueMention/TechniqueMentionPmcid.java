package edu.uiowa.AcknowledgementsTagLib.techniqueMention;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class TechniqueMentionPmcid extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(TechniqueMentionPmcid.class);


	public int doStartTag() throws JspException {
		try {
			TechniqueMention theTechniqueMention = (TechniqueMention)findAncestorWithClass(this, TechniqueMention.class);
			if (!theTechniqueMention.commitNeeded) {
				pageContext.getOut().print(theTechniqueMention.getPmcid());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing TechniqueMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing TechniqueMention for pmcid tag ");
		}
		return SKIP_BODY;
	}

	public int getPmcid() throws JspTagException {
		try {
			TechniqueMention theTechniqueMention = (TechniqueMention)findAncestorWithClass(this, TechniqueMention.class);
			return theTechniqueMention.getPmcid();
		} catch (Exception e) {
			log.error(" Can't find enclosing TechniqueMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing TechniqueMention for pmcid tag ");
		}
	}

	public void setPmcid(int pmcid) throws JspTagException {
		try {
			TechniqueMention theTechniqueMention = (TechniqueMention)findAncestorWithClass(this, TechniqueMention.class);
			theTechniqueMention.setPmcid(pmcid);
		} catch (Exception e) {
			log.error("Can't find enclosing TechniqueMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing TechniqueMention for pmcid tag ");
		}
	}

}
