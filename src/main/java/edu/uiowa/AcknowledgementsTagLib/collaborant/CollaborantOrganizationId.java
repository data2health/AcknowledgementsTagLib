package edu.uiowa.AcknowledgementsTagLib.collaborant;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class CollaborantOrganizationId extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(CollaborantOrganizationId.class);


	public int doStartTag() throws JspException {
		try {
			Collaborant theCollaborant = (Collaborant)findAncestorWithClass(this, Collaborant.class);
			if (!theCollaborant.commitNeeded) {
				pageContext.getOut().print(theCollaborant.getOrganizationId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Collaborant for organizationId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Collaborant for organizationId tag ");
		}
		return SKIP_BODY;
	}

	public int getOrganizationId() throws JspTagException {
		try {
			Collaborant theCollaborant = (Collaborant)findAncestorWithClass(this, Collaborant.class);
			return theCollaborant.getOrganizationId();
		} catch (Exception e) {
			log.error(" Can't find enclosing Collaborant for organizationId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Collaborant for organizationId tag ");
		}
	}

	public void setOrganizationId(int organizationId) throws JspTagException {
		try {
			Collaborant theCollaborant = (Collaborant)findAncestorWithClass(this, Collaborant.class);
			theCollaborant.setOrganizationId(organizationId);
		} catch (Exception e) {
			log.error("Can't find enclosing Collaborant for organizationId tag ", e);
			throw new JspTagException("Error: Can't find enclosing Collaborant for organizationId tag ");
		}
	}

}
