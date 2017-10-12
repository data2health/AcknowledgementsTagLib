package edu.uiowa.AcknowledgementsTagLib.supporter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class SupporterPmcid extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(SupporterPmcid.class);


	public int doStartTag() throws JspException {
		try {
			Supporter theSupporter = (Supporter)findAncestorWithClass(this, Supporter.class);
			if (!theSupporter.commitNeeded) {
				pageContext.getOut().print(theSupporter.getPmcid());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Supporter for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing Supporter for pmcid tag ");
		}
		return SKIP_BODY;
	}

	public int getPmcid() throws JspTagException {
		try {
			Supporter theSupporter = (Supporter)findAncestorWithClass(this, Supporter.class);
			return theSupporter.getPmcid();
		} catch (Exception e) {
			log.error(" Can't find enclosing Supporter for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing Supporter for pmcid tag ");
		}
	}

	public void setPmcid(int pmcid) throws JspTagException {
		try {
			Supporter theSupporter = (Supporter)findAncestorWithClass(this, Supporter.class);
			theSupporter.setPmcid(pmcid);
		} catch (Exception e) {
			log.error("Can't find enclosing Supporter for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing Supporter for pmcid tag ");
		}
	}

}
