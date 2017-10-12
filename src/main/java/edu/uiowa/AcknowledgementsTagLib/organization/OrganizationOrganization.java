package edu.uiowa.AcknowledgementsTagLib.organization;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class OrganizationOrganization extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(OrganizationOrganization.class);


	public int doStartTag() throws JspException {
		try {
			Organization theOrganization = (Organization)findAncestorWithClass(this, Organization.class);
			if (!theOrganization.commitNeeded) {
				pageContext.getOut().print(theOrganization.getOrganization());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Organization for organization tag ", e);
			throw new JspTagException("Error: Can't find enclosing Organization for organization tag ");
		}
		return SKIP_BODY;
	}

	public String getOrganization() throws JspTagException {
		try {
			Organization theOrganization = (Organization)findAncestorWithClass(this, Organization.class);
			return theOrganization.getOrganization();
		} catch (Exception e) {
			log.error(" Can't find enclosing Organization for organization tag ", e);
			throw new JspTagException("Error: Can't find enclosing Organization for organization tag ");
		}
	}

	public void setOrganization(String organization) throws JspTagException {
		try {
			Organization theOrganization = (Organization)findAncestorWithClass(this, Organization.class);
			theOrganization.setOrganization(organization);
		} catch (Exception e) {
			log.error("Can't find enclosing Organization for organization tag ", e);
			throw new JspTagException("Error: Can't find enclosing Organization for organization tag ");
		}
	}

}
