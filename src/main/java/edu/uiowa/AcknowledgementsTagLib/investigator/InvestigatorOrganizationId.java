package edu.uiowa.AcknowledgementsTagLib.investigator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class InvestigatorOrganizationId extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(InvestigatorOrganizationId.class);


	public int doStartTag() throws JspException {
		try {
			Investigator theInvestigator = (Investigator)findAncestorWithClass(this, Investigator.class);
			if (!theInvestigator.commitNeeded) {
				pageContext.getOut().print(theInvestigator.getOrganizationId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Investigator for organizationId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Investigator for organizationId tag ");
		}
		return SKIP_BODY;
	}

	public int getOrganizationId() throws JspTagException {
		try {
			Investigator theInvestigator = (Investigator)findAncestorWithClass(this, Investigator.class);
			return theInvestigator.getOrganizationId();
		} catch (Exception e) {
			log.error(" Can't find enclosing Investigator for organizationId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Investigator for organizationId tag ");
		}
	}

	public void setOrganizationId(int organizationId) throws JspTagException {
		try {
			Investigator theInvestigator = (Investigator)findAncestorWithClass(this, Investigator.class);
			theInvestigator.setOrganizationId(organizationId);
		} catch (Exception e) {
			log.error("Can't find enclosing Investigator for organizationId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Investigator for organizationId tag ");
		}
	}

}
