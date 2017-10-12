package edu.uiowa.AcknowledgementsTagLib.organization;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class OrganizationGeonamesId extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(OrganizationGeonamesId.class);


	public int doStartTag() throws JspException {
		try {
			Organization theOrganization = (Organization)findAncestorWithClass(this, Organization.class);
			if (!theOrganization.commitNeeded) {
				pageContext.getOut().print(theOrganization.getGeonamesId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Organization for geonamesId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Organization for geonamesId tag ");
		}
		return SKIP_BODY;
	}

	public int getGeonamesId() throws JspTagException {
		try {
			Organization theOrganization = (Organization)findAncestorWithClass(this, Organization.class);
			return theOrganization.getGeonamesId();
		} catch (Exception e) {
			log.error(" Can't find enclosing Organization for geonamesId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Organization for geonamesId tag ");
		}
	}

	public void setGeonamesId(int geonamesId) throws JspTagException {
		try {
			Organization theOrganization = (Organization)findAncestorWithClass(this, Organization.class);
			theOrganization.setGeonamesId(geonamesId);
		} catch (Exception e) {
			log.error("Can't find enclosing Organization for geonamesId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Organization for geonamesId tag ");
		}
	}

}
