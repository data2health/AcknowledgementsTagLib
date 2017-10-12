package edu.uiowa.AcknowledgementsTagLib.investigator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class InvestigatorPersonId extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(InvestigatorPersonId.class);


	public int doStartTag() throws JspException {
		try {
			Investigator theInvestigator = (Investigator)findAncestorWithClass(this, Investigator.class);
			if (!theInvestigator.commitNeeded) {
				pageContext.getOut().print(theInvestigator.getPersonId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Investigator for personId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Investigator for personId tag ");
		}
		return SKIP_BODY;
	}

	public int getPersonId() throws JspTagException {
		try {
			Investigator theInvestigator = (Investigator)findAncestorWithClass(this, Investigator.class);
			return theInvestigator.getPersonId();
		} catch (Exception e) {
			log.error(" Can't find enclosing Investigator for personId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Investigator for personId tag ");
		}
	}

	public void setPersonId(int personId) throws JspTagException {
		try {
			Investigator theInvestigator = (Investigator)findAncestorWithClass(this, Investigator.class);
			theInvestigator.setPersonId(personId);
		} catch (Exception e) {
			log.error("Can't find enclosing Investigator for personId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Investigator for personId tag ");
		}
	}

}
