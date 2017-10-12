package edu.uiowa.AcknowledgementsTagLib.organizationMention;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.AcknowledgementsTagLib.AcknowledgementsTagLibTagSupport;

@SuppressWarnings("serial")
public class OrganizationMentionPmcid extends AcknowledgementsTagLibTagSupport {
	private static final Log log = LogFactory.getLog(OrganizationMentionPmcid.class);


	public int doStartTag() throws JspException {
		try {
			OrganizationMention theOrganizationMention = (OrganizationMention)findAncestorWithClass(this, OrganizationMention.class);
			if (!theOrganizationMention.commitNeeded) {
				pageContext.getOut().print(theOrganizationMention.getPmcid());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing OrganizationMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing OrganizationMention for pmcid tag ");
		}
		return SKIP_BODY;
	}

	public int getPmcid() throws JspTagException {
		try {
			OrganizationMention theOrganizationMention = (OrganizationMention)findAncestorWithClass(this, OrganizationMention.class);
			return theOrganizationMention.getPmcid();
		} catch (Exception e) {
			log.error(" Can't find enclosing OrganizationMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing OrganizationMention for pmcid tag ");
		}
	}

	public void setPmcid(int pmcid) throws JspTagException {
		try {
			OrganizationMention theOrganizationMention = (OrganizationMention)findAncestorWithClass(this, OrganizationMention.class);
			theOrganizationMention.setPmcid(pmcid);
		} catch (Exception e) {
			log.error("Can't find enclosing OrganizationMention for pmcid tag ", e);
			throw new JspTagException("Error: Can't find enclosing OrganizationMention for pmcid tag ");
		}
	}

}
