package edu.uiowa.AcknowledgementsTagLib.organizationMention;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class OrganizationMentionOrganizationId extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(OrganizationMentionOrganizationId.class);


	public int doStartTag() throws JspException {
		try {
			OrganizationMention theOrganizationMention = (OrganizationMention)findAncestorWithClass(this, OrganizationMention.class);
			if (!theOrganizationMention.commitNeeded) {
				pageContext.getOut().print(theOrganizationMention.getOrganizationId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing OrganizationMention for organizationId tag ", e);
			throw new JspTagException("Error: Can't find enclosing OrganizationMention for organizationId tag ");
		}
		return SKIP_BODY;
	}

	public int getOrganizationId() throws JspTagException {
		try {
			OrganizationMention theOrganizationMention = (OrganizationMention)findAncestorWithClass(this, OrganizationMention.class);
			return theOrganizationMention.getOrganizationId();
		} catch (Exception e) {
			log.error(" Can't find enclosing OrganizationMention for organizationId tag ", e);
			throw new JspTagException("Error: Can't find enclosing OrganizationMention for organizationId tag ");
		}
	}

	public void setOrganizationId(int organizationId) throws JspTagException {
		try {
			OrganizationMention theOrganizationMention = (OrganizationMention)findAncestorWithClass(this, OrganizationMention.class);
			theOrganizationMention.setOrganizationId(organizationId);
		} catch (Exception e) {
			log.error("Can't find enclosing OrganizationMention for organizationId tag ", e);
			throw new JspTagException("Error: Can't find enclosing OrganizationMention for organizationId tag ");
		}
	}

}
