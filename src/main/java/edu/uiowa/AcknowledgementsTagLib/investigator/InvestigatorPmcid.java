package edu.uiowa.AcknowledgementsTagLib.investigator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class InvestigatorPmcid extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(InvestigatorPmcid.class);


	public int doStartTag() throws JspException {
		try {
			Investigator theInvestigator = (Investigator)findAncestorWithClass(this, Investigator.class);
			if (!theInvestigator.commitNeeded) {
				pageContext.getOut().print(theInvestigator.getPmcid());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Investigator for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing Investigator for pmcid tag ");
		}
		return SKIP_BODY;
	}

	public int getPmcid() throws JspTagException {
		try {
			Investigator theInvestigator = (Investigator)findAncestorWithClass(this, Investigator.class);
			return theInvestigator.getPmcid();
		} catch (Exception e) {
			log.error(" Can't find enclosing Investigator for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing Investigator for pmcid tag ");
		}
	}

	public void setPmcid(int pmcid) throws JspTagException {
		try {
			Investigator theInvestigator = (Investigator)findAncestorWithClass(this, Investigator.class);
			theInvestigator.setPmcid(pmcid);
		} catch (Exception e) {
			log.error("Can't find enclosing Investigator for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing Investigator for pmcid tag ");
		}
	}

}
