package edu.uiowa.AcknowledgementsTagLib.organization;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class OrganizationGeonamesMatchString extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(OrganizationGeonamesMatchString.class);


	public int doStartTag() throws JspException {
		try {
			Organization theOrganization = (Organization)findAncestorWithClass(this, Organization.class);
			if (!theOrganization.commitNeeded) {
				pageContext.getOut().print(theOrganization.getGeonamesMatchString());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Organization for geonamesMatchString tag ", e);
			throw new JspTagException("Error: Can't find enclosing Organization for geonamesMatchString tag ");
		}
		return SKIP_BODY;
	}

	public String getGeonamesMatchString() throws JspTagException {
		try {
			Organization theOrganization = (Organization)findAncestorWithClass(this, Organization.class);
			return theOrganization.getGeonamesMatchString();
		} catch (Exception e) {
			log.error(" Can't find enclosing Organization for geonamesMatchString tag ", e);
			throw new JspTagException("Error: Can't find enclosing Organization for geonamesMatchString tag ");
		}
	}

	public void setGeonamesMatchString(String geonamesMatchString) throws JspTagException {
		try {
			Organization theOrganization = (Organization)findAncestorWithClass(this, Organization.class);
			theOrganization.setGeonamesMatchString(geonamesMatchString);
		} catch (Exception e) {
			log.error("Can't find enclosing Organization for geonamesMatchString tag ", e);
			throw new JspTagException("Error: Can't find enclosing Organization for geonamesMatchString tag ");
		}
	}

}
