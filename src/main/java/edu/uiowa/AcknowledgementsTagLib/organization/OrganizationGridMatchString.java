package edu.uiowa.AcknowledgementsTagLib.organization;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class OrganizationGridMatchString extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(OrganizationGridMatchString.class);


	public int doStartTag() throws JspException {
		try {
			Organization theOrganization = (Organization)findAncestorWithClass(this, Organization.class);
			if (!theOrganization.commitNeeded) {
				pageContext.getOut().print(theOrganization.getGridMatchString());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Organization for gridMatchString tag ", e);
			throw new JspTagException("Error: Can't find enclosing Organization for gridMatchString tag ");
		}
		return SKIP_BODY;
	}

	public String getGridMatchString() throws JspTagException {
		try {
			Organization theOrganization = (Organization)findAncestorWithClass(this, Organization.class);
			return theOrganization.getGridMatchString();
		} catch (Exception e) {
			log.error(" Can't find enclosing Organization for gridMatchString tag ", e);
			throw new JspTagException("Error: Can't find enclosing Organization for gridMatchString tag ");
		}
	}

	public void setGridMatchString(String gridMatchString) throws JspTagException {
		try {
			Organization theOrganization = (Organization)findAncestorWithClass(this, Organization.class);
			theOrganization.setGridMatchString(gridMatchString);
		} catch (Exception e) {
			log.error("Can't find enclosing Organization for gridMatchString tag ", e);
			throw new JspTagException("Error: Can't find enclosing Organization for gridMatchString tag ");
		}
	}

}
