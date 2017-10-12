package edu.uiowa.AcknowledgementsTagLib.organization;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class OrganizationGridId extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(OrganizationGridId.class);


	public int doStartTag() throws JspException {
		try {
			Organization theOrganization = (Organization)findAncestorWithClass(this, Organization.class);
			if (!theOrganization.commitNeeded) {
				pageContext.getOut().print(theOrganization.getGridId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Organization for gridId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Organization for gridId tag ");
		}
		return SKIP_BODY;
	}

	public String getGridId() throws JspTagException {
		try {
			Organization theOrganization = (Organization)findAncestorWithClass(this, Organization.class);
			return theOrganization.getGridId();
		} catch (Exception e) {
			log.error(" Can't find enclosing Organization for gridId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Organization for gridId tag ");
		}
	}

	public void setGridId(String gridId) throws JspTagException {
		try {
			Organization theOrganization = (Organization)findAncestorWithClass(this, Organization.class);
			theOrganization.setGridId(gridId);
		} catch (Exception e) {
			log.error("Can't find enclosing Organization for gridId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Organization for gridId tag ");
		}
	}

}
